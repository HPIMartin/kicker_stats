package dev.schoenberg.kicker_stats.core;

import java.util.function.Function;

import dev.schoenberg.kicker_stats.core.domain.Credentials;

@FunctionalInterface
public interface PasswordHasher extends Function<Credentials, String> {
}
