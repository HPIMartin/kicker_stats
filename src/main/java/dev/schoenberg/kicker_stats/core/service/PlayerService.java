package dev.schoenberg.kicker_stats.core.service;

import java.util.List;

import dev.schoenberg.kicker_stats.core.Mailer;
import dev.schoenberg.kicker_stats.core.Mailer.Mail;
import dev.schoenberg.kicker_stats.core.PasswordHasher;
import dev.schoenberg.kicker_stats.core.PlayerRepository;
import dev.schoenberg.kicker_stats.core.domain.NewPlayer;
import dev.schoenberg.kicker_stats.core.domain.Player;

public class PlayerService {
	private final PlayerRepository repo;
	private final PasswordHasher hasher;
	private final Mailer mailer;

	public PlayerService(PlayerRepository repo, PasswordHasher hasher, Mailer mailer) {
		this.repo = repo;
		this.hasher = hasher;
		this.mailer = mailer;
	}

	public void createPlayer(NewPlayer newPlayer) {
		Player player = storePlayer(newPlayer);
		try {
			mailer.send(new WelcomeMail(player.name, player.mail));
		} catch (Exception e) {
			repo.delete(player);
			throw new RuntimeException(e);
		}
	}

	private Player storePlayer(NewPlayer newPlayer) {
		String hashedPassword = hasher.apply(newPlayer.credentials);
		Player player = new Player(newPlayer.name, newPlayer.credentials.mail, hashedPassword);
		repo.create(player);
		return player;
	}

	public List<Player> getPlayers() {
		return repo.getPlayers();
	}

	public Player getByMail(String mail) {
		return repo.byMail(mail);
	}

	private class WelcomeMail extends Mail {
		private static final String WELCOME_SUBJECT = "Registration for Kickerstats";
		private static final String WELCOME_TEXT = "This address was registered for the kicker tracking system.";

		public WelcomeMail(String name, String address) {
			super(name, address, WELCOME_SUBJECT, WELCOME_TEXT);
		}
	}
}
