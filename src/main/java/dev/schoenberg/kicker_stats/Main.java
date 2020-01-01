package dev.schoenberg.kicker_stats;

import static io.jsonwebtoken.SignatureAlgorithm.*;

import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import dev.schoenberg.kicker_stats.core.PasswordHasher;
import dev.schoenberg.kicker_stats.core.helper.Sha3BouncyCastlePasswordHasher;
import dev.schoenberg.kicker_stats.core.helper.SimpleResourceLoader;
import dev.schoenberg.kicker_stats.core.helper.logger.SystemOutLogger;
import dev.schoenberg.kicker_stats.core.service.JWTAuthenticationService;
import dev.schoenberg.kicker_stats.core.service.PlayerService;
import dev.schoenberg.kicker_stats.persistence.helper.DaoRepository;
import dev.schoenberg.kicker_stats.persistence.repository.PlayerOrmLiteRepository;
import dev.schoenberg.kicker_stats.rest.JettyServer;
import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.controller.AuthenticationController;
import dev.schoenberg.kicker_stats.rest.controller.PlayerController;
import dev.schoenberg.kicker_stats.rest.controller.ResourceController;
import dev.schoenberg.kicker_stats.rest.controller.VersionController;
import dev.schoenberg.kicker_stats.rest.exceptionMapper.AuthenticationFailedExceptionWrapper;
import dev.schoenberg.kicker_stats.rest.exceptionMapper.NotFoundExceptionWrapper;
import dev.schoenberg.kicker_stats.rest.filters.AuthenticationFilter;
import dev.schoenberg.kicker_stats.rest.filters.CORSFilter;
import dev.schoenberg.kicker_stats.rest.filters.RequestLoggingFilter;
import io.jsonwebtoken.security.Keys;

public class Main {
	private static List<ServerService> services = new ArrayList<>();

	public static ServerFactory serverFactory = JettyServer::new;
	public static int PORT = 8080;
	public static String url = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/kickerStats.db";

	public static void main(String[] args) throws IOException, SQLException {
		PasswordHasher hasher = new Sha3BouncyCastlePasswordHasher();
		PlayerService players = new PlayerService(new PlayerOrmLiteRepository(), hasher);
		SystemOutLogger logger = new SystemOutLogger();
		Key key = Keys.secretKeyFor(HS512);
		JWTAuthenticationService auth = new JWTAuthenticationService(players::getByMail, hasher, key, Instant::now);

		services.add(new CORSFilter());
		services.add(new AuthenticationFilter(auth::checkAuth));

		services.add(new NotFoundExceptionWrapper());
		services.add(new AuthenticationFailedExceptionWrapper());
		services.add(new RequestLoggingFilter(logger));

		services.add(new AuthenticationController(auth));
		services.add(new VersionController());
		services.add(new ResourceController(new SimpleResourceLoader()::loadFromResources));
		services.add(new PlayerController(players));

		try (JettyServer server = serverFactory.create(PORT, services); ConnectionSource connection = new JdbcConnectionSource(url)) {
			Flyway.configure().dataSource(url, null, null).load().migrate();
			DaoRepository.initDaos(connection);
			server.run();
		}
	}

	@FunctionalInterface
	public interface ServerFactory {
		JettyServer create(int port, List<ServerService> services);
	}
}
