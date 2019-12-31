package dev.schoenberg.kicker_stats.core.domain;

public class Player {
	public final String name;
	public final String mail;
	public final String hashedPassword;

	public Player(String name, String mail, String hashedPassword) {
		this.name = name;
		this.mail = mail;
		this.hashedPassword = hashedPassword;
	}

	public boolean isAdmin() {
		return false;
	}
}
