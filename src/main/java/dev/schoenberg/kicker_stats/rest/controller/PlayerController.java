package dev.schoenberg.kicker_stats.rest.controller;

import static dev.schoenberg.kicker_stats.rest.Roles.*;
import static java.util.stream.Collectors.*;
import static javax.ws.rs.core.MediaType.*;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dev.schoenberg.kicker_stats.core.domain.Credentials;
import dev.schoenberg.kicker_stats.core.domain.NewPlayer;
import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.service.PlayerService;
import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.dto.NewPlayerDto;
import dev.schoenberg.kicker_stats.rest.dto.PlayerDto;
import dev.schoenberg.kicker_stats.rest.dto.PlayersDto;

@Path("/players")
public class PlayerController implements ServerService {
	private final PlayerService service;

	public PlayerController(PlayerService service) {
		this.service = service;
	}

	@POST
	@RolesAllowed(ADMIN)
	@Consumes(APPLICATION_JSON)
	public void createPlayer(NewPlayerDto dto) {
		service.createPlayer(new NewPlayer(dto.name, new Credentials(dto.mail, dto.password)));
	}

	@GET
	@RolesAllowed(USER)
	@Produces(APPLICATION_JSON)
	public PlayersDto getPlayers() {
		List<Player> players = service.getPlayers();
		return convert(players);
	}

	private PlayersDto convert(List<Player> players) {
		return new PlayersDto(players.stream().map(this::convert).collect(toList()));
	}

	private PlayerDto convert(Player player) {
		return new PlayerDto(player.name, player.mail);
	}
}
