package dev.schoenberg.kicker_stats.core;

import java.util.List;

import dev.schoenberg.kicker_stats.core.PlayerRepository;
import dev.schoenberg.kicker_stats.core.domain.Player;

public class TestPlayerRepository implements PlayerRepository {
	public Player storedPlayer;
	public Player deletedPlayer;

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

	@Override
	public void delete(Player player) {
		deletedPlayer = player;
	}
}
