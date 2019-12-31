package dev.schoenberg.kicker_stats.core.helper;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.domain.Credentials;

public class Sha3BouncyCastlePasswordHasherTest {
	private static final String EMPTY_STRING_SHA3_HASH = "a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a";

	private Sha3BouncyCastlePasswordHasher tested;

	@Before
	public void setup() {
		tested = new Sha3BouncyCastlePasswordHasher();
	}

	@Test
	public void emptyInput() {
		Credentials input = new Credentials("", "");

		String result = tested.apply(input);

		assertThat(result).isEqualTo(EMPTY_STRING_SHA3_HASH);
	}

	@Test
	public void passwordIsHashed() {
		Credentials input = new Credentials("", "pwd");

		String result = tested.apply(input);

		assertThat(result).hasSize(64);
		assertThat(result).isNotEqualTo(EMPTY_STRING_SHA3_HASH);
	}

	@Test
	public void mailIsUsedAsSalt() {
		Credentials input = new Credentials("mail@gmail.com", "");

		String result = tested.apply(input);

		assertThat(result).hasSize(64);
		assertThat(result).isNotEqualTo(EMPTY_STRING_SHA3_HASH);
	}
}
