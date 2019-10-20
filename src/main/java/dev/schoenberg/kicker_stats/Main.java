package dev.schoenberg.kicker_stats;

import java.util.ArrayList;
import java.util.List;

import dev.schoenberg.kicker_stats.helper.SimpleResourceLoader;
import dev.schoenberg.kicker_stats.rest.JettyServer;
import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.controller.ResourceController;
import dev.schoenberg.kicker_stats.rest.controller.VersionController;

public class Main {
	private static List<ServerService> services = new ArrayList<>();

	public static ServerFactory serverFactory = JettyServer::new;
	public static int PORT = 8080;

	public static void main(String[] args) {
		// String url = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/temp.db";
		services.add(new VersionController());
		services.add(new ResourceController(new SimpleResourceLoader()::loadFromResources));

		try (JettyServer server = serverFactory.create(PORT, services)) {
			server.run();
		}
	}

	public interface ServerFactory {
		JettyServer create(int port, List<ServerService> services);
	}
}
