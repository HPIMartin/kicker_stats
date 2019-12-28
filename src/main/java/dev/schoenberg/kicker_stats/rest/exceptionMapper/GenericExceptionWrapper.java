package dev.schoenberg.kicker_stats.rest.exceptionMapper;

import static javax.ws.rs.core.Response.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import dev.schoenberg.kicker_stats.rest.ServerService;

public abstract class GenericExceptionWrapper<T extends Exception> implements ExceptionMapper<T>, ServerService {
	private final Status status;

	protected GenericExceptionWrapper(Status status) {
		this.status = status;
	}

	@Override
	public Response toResponse(T exception) {
		return status(status).entity(exception.getMessage()).type("text/plain").build();
	}
}
