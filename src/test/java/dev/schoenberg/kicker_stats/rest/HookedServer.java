package dev.schoenberg.kicker_stats.rest;

import java.io.IOException;
import java.util.List;

import dev.schoenberg.kicker_stats.rest.JettyServer;
import dev.schoenberg.kicker_stats.rest.Service;

public class HookedServer extends JettyServer {
	public boolean closeServer = true;

	private boolean started;
	private RuntimeException startUpProblem;
	private Thread serverThread;

	public HookedServer(int port, List<Service> services) {
		super(port, services);
		started = false;
	}

	@Override
	protected void waitForTermination() throws InterruptedException {
		started = true;
		super.waitForTermination();
	}

	@Override
	public void run() {
		if (serverThread == null) {
			serverThread = new Thread(() -> {
				try {
					super.run();
				} catch (RuntimeException e) {
					startUpProblem = e;
				}
			});
			serverThread.start();
			awaitStartup();
		}
	}

	private void awaitStartup() {
		try {
			while (!started) {
				Thread.sleep(100);
				if (startUpProblem != null) {
					throw startUpProblem;
				}
			}
		} catch (InterruptedException e) {
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void close() throws IOException {
		if (closeServer) {
			super.close();
			serverThread.stop();
		}
	}
}
