package dev.schoenberg.kicker_stats.core.service;

import java.util.List;

import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.repository.PlayerRepository;

public class TestPlayerRepository implements PlayerRepository {
	public Player storedPlayer;

	@Override
	public void create(Player player) {
		storedPlayer = player;
	}

	@Override
	public List<Player> getPlayers() {
		return null;
	}

	@Override
	public Player byMail(String mail) {
		return null;
	}
}
