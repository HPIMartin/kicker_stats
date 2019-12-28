package dev.schoenberg.kicker_stats.rest.exceptionMapper;

import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.core.Response;

import org.junit.Test;

import dev.schoenberg.kicker_stats.core.exception.NotFoundException;

public class NotFoundExceptionWrapperTest {
	@Test
	public void statusCodeIs404() {
		NotFoundExceptionWrapper tested = new NotFoundExceptionWrapper();

		Response result = tested.toResponse(new NotFoundException());

		assertThat(result.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
	}
}
