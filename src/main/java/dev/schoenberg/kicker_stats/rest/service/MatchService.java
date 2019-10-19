package dev.schoenberg.kicker_stats.rest.service;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import dev.schoenberg.kicker_stats.rest.Service;
import dev.schoenberg.kicker_stats.rest.dto.MatchResultDto;

@Path("/matches")
public class MatchService implements Service {
	@POST
	@Consumes(APPLICATION_JSON)
	public void createMatch(MatchResultDto matchResult) {

	}
}
