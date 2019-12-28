package dev.schoenberg.kicker_stats.core.repository;

import java.util.List;

import dev.schoenberg.kicker_stats.core.domain.Player;

public interface PlayerRepository {
	void create(Player player);

	List<Player> getPlayers();

	Player byMail(String mail);
}
