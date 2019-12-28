package dev.schoenberg.kicker_stats.rest.exceptionMapper;

import static javax.ws.rs.core.Response.Status.*;

import dev.schoenberg.kicker_stats.core.exception.AuthenticationFailedException;

public class AuthenticationFailedExceptionWrapper extends GenericExceptionWrapper<AuthenticationFailedException> {
	public AuthenticationFailedExceptionWrapper() {
		super(UNAUTHORIZED);
	}
}
