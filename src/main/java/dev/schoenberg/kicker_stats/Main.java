package dev.schoenberg.kicker_stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.schoenberg.kicker_stats.rest.JettyServer;
import dev.schoenberg.kicker_stats.rest.Service;
import dev.schoenberg.kicker_stats.rest.service.VersionService;

public class Main {
	private static List<Service> services = new ArrayList<>();

	public static ServerFactory serverFactory = JettyServer::new;
	public static int PORT = 8080;

	public static void main(String[] args) {
		// String url = "jdbc:sqlite:" + System.getProperty("user.dir").replace("\\", "/") + "/temp.db";
		services.add(new VersionService());

		try (JettyServer server = serverFactory.create(PORT, services)) {
			server.run();
		} catch (IOException e) {
			new RuntimeException("Unable to close server", e);
		}
	}

	public interface ServerFactory {
		JettyServer create(int port, List<Service> services);
	}
}
