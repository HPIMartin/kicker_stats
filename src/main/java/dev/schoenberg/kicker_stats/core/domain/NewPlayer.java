package dev.schoenberg.kicker_stats.core.domain;

public class NewPlayer {
	public final String name;
	public final Credentials credentials;

	public NewPlayer(String name, Credentials credentials) {
		this.name = name;
		this.credentials = credentials;
	}
}
