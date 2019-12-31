package dev.schoenberg.kicker_stats.core.helper;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.domain.Credentials;

public class Sha3BouncyCastlePasswordHasherTest {
	private Sha3BouncyCastlePasswordHasher tested;

	@Before
	public void setup() {
		tested = new Sha3BouncyCastlePasswordHasher();
	}

	@Test
	public void emptyInput() {
		Credentials input = new Credentials("", "");

		String result = tested.apply(input);

		assertThat(result).isEqualTo("763c38be0664691418d38f5ccde0162c9ff11fbda1b946d56476bdaa90fd13d6");
	}

	@Test
	public void passwordIsHashed() {
		Credentials input = new Credentials("", "pwd");

		String result = tested.apply(input);

		assertThat(result).isEqualTo("1486ffb0ab36e1e0cf13d4702e6b3a7d88bb8be326a6370296fd16b900ede931");
	}

	@Test
	public void mailIsUsedAsSalt() {
		Credentials input = new Credentials("mail@gmail.com", "");

		String result = tested.apply(input);

		assertThat(result).isEqualTo("cf98880052b044aeae8a75612d44d5d0b5b33e2f89dbb100081ef95d19eaa1ba");
	}
}
