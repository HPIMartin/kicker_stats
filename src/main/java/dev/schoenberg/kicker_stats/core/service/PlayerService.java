package dev.schoenberg.kicker_stats.core.service;

import java.util.List;

import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.repository.PlayerRepository;

public class PlayerService {
	private final PlayerRepository repo;

	public PlayerService(PlayerRepository repo) {
		this.repo = repo;
	}

	public void createPlayer(String name, String email) {
		Player player = new Player(name, email);
		repo.create(player);
	}

	public List<Player> getPlayers() {
		return repo.getPlayers();
	}
}
