package dev.schoenberg.kicker_stats.core;

import java.time.Instant;

public interface Clock {
	Instant now();
}
