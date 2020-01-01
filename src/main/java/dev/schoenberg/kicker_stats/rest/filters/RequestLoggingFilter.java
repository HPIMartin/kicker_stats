package dev.schoenberg.kicker_stats.rest.filters;

import static org.glassfish.jersey.message.internal.ReaderWriter.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.filter.LoggingFilter;

import dev.schoenberg.kicker_stats.core.Logger;
import dev.schoenberg.kicker_stats.rest.ServerService;

public class RequestLoggingFilter extends LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter, ServerService {
	private final Logger logger;

	public RequestLoggingFilter(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		StringBuilder sb = new StringBuilder("HTTP REQUEST : ");
		sb.append("Path: [").append(requestContext.getMethod()).append("] ").append(requestContext.getUriInfo().getPath());
		logger.info(sb.toString());
		sb.append(" - Header: ").append(requestContext.getHeaders());
		sb.append(" - Entity: ").append(getEntityBody(requestContext));
		logger.debug(sb.toString());
	}

	private String getEntityBody(ContainerRequestContext requestContext) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = requestContext.getEntityStream();

		StringBuilder b = new StringBuilder();
		try {
			writeTo(in, out);

			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				b.append("");
			} else {
				b.append(new String(requestEntity));
			}
			requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

		} catch (IOException ex) {
		}

		return b.toString();
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		StringBuilder sb = new StringBuilder("HTTP RESPONSE : ");
		sb.append(responseContext.getStatus()).append(" >>> ");
		sb.append("Path: [").append(requestContext.getMethod()).append("] ").append(requestContext.getUriInfo().getPath());
		logger.info(sb.toString());
		sb.append(" - Header: ").append(responseContext.getHeaders());
		sb.append(" - Entity: ").append(responseContext.getEntity());
		logger.debug(sb.toString());
	}
}
