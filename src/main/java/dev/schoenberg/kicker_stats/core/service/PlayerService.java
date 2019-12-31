package dev.schoenberg.kicker_stats.core.service;

import java.util.List;

import dev.schoenberg.kicker_stats.core.PasswordHasher;
import dev.schoenberg.kicker_stats.core.domain.NewPlayer;
import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.repository.PlayerRepository;

public class PlayerService {
	private final PlayerRepository repo;
	private final PasswordHasher hasher;

	public PlayerService(PlayerRepository repo, PasswordHasher hasher) {
		this.repo = repo;
		this.hasher = hasher;
	}

	public void createPlayer(NewPlayer newPlayer) {
		String hashedPassword = hasher.apply(newPlayer.credentials);
		Player player = new Player(newPlayer.name, newPlayer.credentials.mail, hashedPassword);
		repo.create(player);
	}

	public List<Player> getPlayers() {
		return repo.getPlayers();
	}

	public Player getByMail(String mail) {
		return repo.byMail(mail);
	}
}
