package dev.schoenberg.kicker_stats.rest.controller;

import static javax.ws.rs.core.MediaType.*;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.dto.MatchResultDto;

@Path("/matches")
public class MatchController implements ServerService {
	@POST
	@PermitAll
	@Consumes(APPLICATION_JSON)
	public void createMatch(MatchResultDto matchResult) {

	}
}
