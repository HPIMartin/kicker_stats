package dev.schoenberg.kicker_stats.rest;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer implements Closeable {
	private final Server server;

	public JettyServer(int port, List<Service> services) {
		server = new Server(port);
		server.setHandler(createContext(services));
	}

	private ServletContextHandler createContext(List<Service> services) {
		ServletContextHandler result = new ServletContextHandler();
		result.setContextPath("");
		result.addServlet(new ServletHolder(new ServletContainer(createResourceConfig(services))), "/*");
		return result;
	}

	private ResourceConfig createResourceConfig(List<Service> services) {
		ResourceConfig result = new ResourceConfig();
		result.register(MoxyJsonFeature.class);
		for (Service s : services) {
			result.register(s);
		}
		return result;
	}

	public void run() {
		try {
			server.start();
			waitForTermination();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void waitForTermination() throws InterruptedException {
		server.join();
	}

	@Override
	public void close() throws IOException {
		try {
			server.stop();
		} catch (Exception e) {
			throw new IOException(e);
		}
		server.destroy();
	}
}
