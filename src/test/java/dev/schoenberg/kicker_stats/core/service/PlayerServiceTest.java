package dev.schoenberg.kicker_stats.core.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.domain.Credentials;
import dev.schoenberg.kicker_stats.core.domain.NewPlayer;
import dev.schoenberg.kicker_stats.core.domain.Player;

public class PlayerServiceTest {
	private PlayerService tested;
	private TestPlayerRepository repo;
	private Credentials credentials;
	private String hashedPassword;

	@Before
	public void setup() {
		repo = new TestPlayerRepository();
		tested = new PlayerService(repo, this::getHash);
	}

	private String getHash(Credentials credentials) {
		this.credentials = credentials;
		return hashedPassword;
	}

	@Test
	public void newPlayerIsCreated() {
		NewPlayer newPlayer = new NewPlayer("test", new Credentials("mail", ""));

		tested.createPlayer(newPlayer);

		Player result = repo.storedPlayer;
		assertThat(result.name).isEqualTo("test");
		assertThat(result.mail).isEqualTo("mail");
	}

	@Test
	public void passwordIsHashed() {
		NewPlayer newPlayer = new NewPlayer("", new Credentials("", ""));
		hashedPassword = "pwd";

		tested.createPlayer(newPlayer);

		assertThat(credentials).isEqualTo(newPlayer.credentials);
		assertThat(repo.storedPlayer.hashedPassword).isEqualTo("pwd");
	}
}
