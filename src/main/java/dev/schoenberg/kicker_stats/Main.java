package dev.schoenberg.kicker_stats;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import dev.schoenberg.kicker_stats.core.helper.SimpleResourceLoader;
import dev.schoenberg.kicker_stats.core.service.PlayerService;
import dev.schoenberg.kicker_stats.persistence.helper.DaoRepository;
import dev.schoenberg.kicker_stats.persistence.repository.PlayerOrmLiteRepository;
import dev.schoenberg.kicker_stats.rest.JettyServer;
import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.controller.PlayerController;
import dev.schoenberg.kicker_stats.rest.controller.ResourceController;
import dev.schoenberg.kicker_stats.rest.controller.VersionController;
import dev.schoenberg.kicker_stats.rest.filters.CORSFilter;

public class Main {
	private static List<ServerService> services = new ArrayList<>();

	public static ServerFactory serverFactory = JettyServer::new;
	public static int PORT = 8080;
	public static String url = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/kickerStats.db";

	public static void main(String[] args) throws IOException, SQLException {
		services.add(new CORSFilter());
		services.add(new VersionController());
		services.add(new ResourceController(new SimpleResourceLoader()::loadFromResources));
		services.add(new PlayerController(new PlayerService(new PlayerOrmLiteRepository())));

		try (JettyServer server = serverFactory.create(PORT, services); ConnectionSource connection = new JdbcConnectionSource(url)) {
			Flyway flyway = Flyway.configure().dataSource(url, null, null).load();
			flyway.migrate();

			DaoRepository.initDaos(connection);
			server.run();
		}
	}

	public interface ServerFactory {
		JettyServer create(int port, List<ServerService> services);
	}
}
