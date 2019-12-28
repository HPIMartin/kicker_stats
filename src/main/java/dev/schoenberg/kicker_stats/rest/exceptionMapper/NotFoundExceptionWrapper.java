package dev.schoenberg.kicker_stats.rest.exceptionMapper;

import static javax.ws.rs.core.Response.Status.*;

import dev.schoenberg.kicker_stats.core.exception.NotFoundException;

public class NotFoundExceptionWrapper extends GenericExceptionWrapper<NotFoundException> {
	public NotFoundExceptionWrapper() {
		super(NOT_FOUND);
	}
}
