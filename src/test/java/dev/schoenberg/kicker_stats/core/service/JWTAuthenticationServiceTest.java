package dev.schoenberg.kicker_stats.core.service;

import static java.time.ZoneOffset.*;
import static java.util.Base64.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.exception.AuthenticationFailedException;

public class JWTAuthenticationServiceTest {
	private static final String KEY_TYPE = "HmacSHA256";
	private static final String KEY = "S/JFNgGKpeQQ4p9fHvYG9Q9enNovojiyxJiOTJG0saY=";
	private static final String STORED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOi00NjE0NjIxMDAsInN1YiI6ImZvb0BnbWFpbC5jb20ifQ.ktoYF30Z9YQQh_wY850I0D2VNvyIuxKSt_cwgOwaNaM";

	private JWTAuthenticationService tested;
	private Instant time;
	private Player player;
	private String mail;

	@Before
	public void setup() {
		time = LocalDate.of(1955, 5, 19).atStartOfDay().toInstant(UTC);
		player = new Player("name", "foo@gmail.com", "");

		byte[] decodedKey = getDecoder().decode(KEY);
		SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, KEY_TYPE);
		tested = new JWTAuthenticationService(this::mailToPlayer, key, () -> time);
	}

	private Player mailToPlayer(String mail) {
		this.mail = mail;
		return player;
	}

	@Test
	public void generatesAuthToken() {
		String result = tested.login("foo@gmail.com", "pwd");

		assertThat(result).isEqualTo(STORED_TOKEN);
		assertThat(mail).isEqualTo("foo@gmail.com");
	}

	@Test
	public void expiredToken() {
		time = time.plus(6, ChronoUnit.MINUTES);

		assertThatExceptionOfType(AuthenticationFailedException.class).isThrownBy(() -> tested.checkAuth(STORED_TOKEN));
	}

	@Test
	public void tokenIsEvaluated() {
		Player result = tested.checkAuth(STORED_TOKEN);

		assertThat(result).isEqualTo(player);
		assertThat(mail).isEqualTo("foo@gmail.com");
	}
}
