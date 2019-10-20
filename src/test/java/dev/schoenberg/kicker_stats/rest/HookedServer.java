package dev.schoenberg.kicker_stats.rest;

import java.util.List;

public class HookedServer extends JettyServer {
	public boolean closeServer = true;

	private boolean started;
	private RuntimeException startUpProblem;
	private Thread serverThread;

	public HookedServer(int port, List<ServerService> services) {
		super(port, services);
		started = false;
	}

	@Override
	protected void waitForTermination() {
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
		} catch (InterruptedException e) {}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void close() {
		if (closeServer) {
			super.close();
			serverThread.stop();
		}
	}
}
