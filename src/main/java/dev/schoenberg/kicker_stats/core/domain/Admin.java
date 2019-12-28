package dev.schoenberg.kicker_stats.core.domain;

public class Admin extends Player {
	public Admin(String name, String email) {
		super(name, email);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
}
