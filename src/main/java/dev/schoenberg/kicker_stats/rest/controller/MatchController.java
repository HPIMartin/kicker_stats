package dev.schoenberg.kicker_stats.rest.controller;

import static dev.schoenberg.kicker_stats.rest.Roles.*;
import static javax.ws.rs.core.MediaType.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.dto.MatchResultDto;

@Path("/matches")
public class MatchController implements ServerService {
	@POST
	@RolesAllowed(USER)
	@Consumes(APPLICATION_JSON)
	public void createMatch(MatchResultDto matchResult) {

	}
}
