package dev.schoenberg.kicker_stats.rest.controller;

import static javax.ws.rs.core.MediaType.*;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dev.schoenberg.kicker_stats.rest.ServerService;

@Path("/version")
public class VersionController implements ServerService {
	@GET
	@PermitAll
	@Produces(TEXT_PLAIN)
	public String version() {
		return String.class.getPackage().getImplementationVersion();
	}
}
