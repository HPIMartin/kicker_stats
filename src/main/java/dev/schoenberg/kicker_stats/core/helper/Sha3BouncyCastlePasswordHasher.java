package dev.schoenberg.kicker_stats.core.helper;

import static java.lang.String.*;

import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

import dev.schoenberg.kicker_stats.core.PasswordHasher;
import dev.schoenberg.kicker_stats.core.domain.Credentials;

public class Sha3BouncyCastlePasswordHasher implements PasswordHasher {
	@Override
	public String apply(Credentials credentials) {
		return hashToString(getHash(getSaltedPassword(credentials)));
	}

	private String getSaltedPassword(Credentials credentials) {
		String salt = credentials.mail;
		return credentials.password + salt;
	}

	private byte[] getHash(String input) {
		DigestSHA3 sha3 = new Digest256();
		sha3.update(input.getBytes());
		return sha3.digest();
	}

	private String hashToString(byte[] hash) {
		StringBuffer buff = new StringBuffer();
		for (byte b : hash) {
			buff.append(format("%02x", b & 0xFF));
		}
		return buff.toString();
	}
}
