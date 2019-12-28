package dev.schoenberg.kicker_stats.core.domain;

public class Player {
	public final String name;
	public final String mail;

	public Player(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}

	public boolean isAdmin() {
		return false;
	}
}
