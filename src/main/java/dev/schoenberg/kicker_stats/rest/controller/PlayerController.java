package dev.schoenberg.kicker_stats.rest.controller;

import static javax.ws.rs.core.MediaType.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.dto.NewPlayerDto;
import dev.schoenberg.kicker_stats.rest.dto.PlayersDto;

@Path("/matches")
public class PlayerController implements ServerService {
	@POST
	@Consumes(APPLICATION_JSON)
	public void createPlayer(NewPlayerDto newPlayerDto) {

	}

	@GET
	@Produces(APPLICATION_JSON)
	public PlayersDto getPlayers() {
		return new PlayersDto();
	}
}
