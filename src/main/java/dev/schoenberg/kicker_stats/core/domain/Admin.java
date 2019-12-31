package dev.schoenberg.kicker_stats.core.domain;

public class Admin extends Player {
	public Admin(String name, String email, String hashedPassword) {
		super(name, email, hashedPassword);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
}
