package dev.schoenberg.kicker_stats.rest.exceptionMapper;

import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.core.Response;

import org.junit.Test;

import dev.schoenberg.kicker_stats.core.exception.AuthenticationFailedException;

public class AuthenticationFailedExceptionWrapperTest {
	@Test
	public void statusCodeIs404() {
		AuthenticationFailedExceptionWrapper tested = new AuthenticationFailedExceptionWrapper();

		Response result = tested.toResponse(new AuthenticationFailedException());

		assertThat(result.getStatus()).isEqualTo(UNAUTHORIZED.getStatusCode());
	}
}
