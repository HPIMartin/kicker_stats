package dev.schoenberg.kicker_stats.rest.service;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dev.schoenberg.kicker_stats.rest.Service;

@Path("/version")
public class VersionService implements Service {
	@GET
	@Produces(TEXT_PLAIN)
	public String version() {
		return String.class.getPackage().getImplementationVersion();
	}
}
