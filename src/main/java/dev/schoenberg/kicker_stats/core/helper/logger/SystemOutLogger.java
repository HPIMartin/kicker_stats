package dev.schoenberg.kicker_stats.core.helper.logger;

import dev.schoenberg.kicker_stats.core.Logger;

public class SystemOutLogger implements Logger {
	@Override
	public void info(String log) {
		System.out.println("[INFO] " + log);
	}

	@Override
	public void debug(String log) {
		System.out.println("[DEBUG] " + log);
	}
}
