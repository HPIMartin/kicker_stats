package dev.schoenberg.kicker_stats.rest;

import static dev.schoenberg.kicker_stats.exceptionWrapper.ExceptionWrapper.*;

import java.io.Closeable;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer implements Closeable {
	private final Server server;

	public JettyServer(int port, List<ServerService> services) {
		server = new Server(port);
		server.setHandler(createContext(services));
	}

	private ServletContextHandler createContext(List<ServerService> services) {
		ServletContextHandler result = new ServletContextHandler();
		result.setContextPath("");
		result.addServlet(new ServletHolder(new ServletContainer(createResourceConfig(services))), "/*");
		return result;
	}

	private ResourceConfig createResourceConfig(List<ServerService> services) {
		ResourceConfig result = new ResourceConfig();
		result.register(MoxyJsonFeature.class);
		for (ServerService s : services) {
			result.register(s);
		}
		return result;
	}

	public void run() {
		silentThrow(server::start);
		this.waitForTermination();
	}

	protected void waitForTermination() {
		silentThrow(server::join);
	}

	@Override
	public void close() {
		silentThrow(server::stop);
		server.destroy();
	}
}
