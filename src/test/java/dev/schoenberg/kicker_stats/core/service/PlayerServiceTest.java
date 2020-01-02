package dev.schoenberg.kicker_stats.core.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.Mailer.Mail;
import dev.schoenberg.kicker_stats.core.TestMailer;
import dev.schoenberg.kicker_stats.core.TestPlayerRepository;
import dev.schoenberg.kicker_stats.core.domain.Credentials;
import dev.schoenberg.kicker_stats.core.domain.NewPlayer;
import dev.schoenberg.kicker_stats.core.domain.Player;

public class PlayerServiceTest {
	private PlayerService tested;
	private TestPlayerRepository repo;
	private Credentials credentials;
	private String hashedPassword;
	private TestMailer mailer;

	@Before
	public void setup() {
		repo = new TestPlayerRepository();
		mailer = new TestMailer();
		tested = new PlayerService(repo, this::getHash, mailer);
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

	@Test
	public void mailIsSend() {
		NewPlayer newPlayer = new NewPlayer("Mike", new Credentials("hello@world.de", ""));

		tested.createPlayer(newPlayer);

		Mail result = mailer.sendMail;
		assertThat(result.name).isEqualTo("Mike");
		assertThat(result.address).isEqualTo("hello@world.de");
	}

	@Test
	public void deletesPlayerIfMailIsNotSend() {
		NewPlayer newPlayer = new NewPlayer("Mailman", new Credentials("", ""));
		mailer.failOnSend = true;

		ThrowingCallable act = () -> tested.createPlayer(newPlayer);

		assertThatExceptionOfType(RuntimeException.class).isThrownBy(act);
		assertThat(repo.deletedPlayer.name).isEqualTo("Mailman");
	}
}
