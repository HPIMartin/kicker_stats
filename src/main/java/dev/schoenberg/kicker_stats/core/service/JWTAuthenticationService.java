package dev.schoenberg.kicker_stats.core.service;

import static java.time.temporal.ChronoUnit.*;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import dev.schoenberg.kicker_stats.core.Clock;
import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.exception.AuthenticationFailedException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthenticationService {
	private static final long EXPIRY_OFFSET_IN_MINUTES = 5;

	private final Function<String, Player> mailToPlayer;
	private final Key key;
	private final Clock clock;

	public JWTAuthenticationService(Function<String, Player> mailToPlayer, Key key, Clock clock) {
		this.mailToPlayer = mailToPlayer;
		this.key = key;
		this.clock = clock;
	}

	public String login(String mail, String password) {
		Player player = mailToPlayer.apply(mail);
		// TODO: check password
		return getNewJWT(player);
	}

	public Player checkAuth(String authValue) {
		String mail = checkJWT(authValue);
		return mailToPlayer.apply(mail);
	}

	private String getNewJWT(Player player) {
		return Jwts.builder().setExpiration(getExpiry()).setSubject(player.mail).signWith(key).compact();
	}

	private Date getExpiry() {
		return Date.from(clock.now().plus(EXPIRY_OFFSET_IN_MINUTES, MINUTES));
	}

	private String checkJWT(String authValue) {
		try {
			io.jsonwebtoken.Clock jwtClock = () -> Date.from(clock.now());
			return Jwts.parser().setClock(jwtClock).setSigningKey(key).parseClaimsJws(authValue).getBody().getSubject();
		} catch (JwtException e) {
			throw new AuthenticationFailedException();
		}
	}
}
