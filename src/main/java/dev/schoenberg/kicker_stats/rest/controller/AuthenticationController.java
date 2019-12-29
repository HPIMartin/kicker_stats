package dev.schoenberg.kicker_stats.rest.controller;

import static javax.ws.rs.core.MediaType.*;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dev.schoenberg.kicker_stats.core.service.JWTAuthenticationService;
import dev.schoenberg.kicker_stats.rest.ServerService;
import dev.schoenberg.kicker_stats.rest.dto.LoginDto;

@Path("/login")
public class AuthenticationController implements ServerService {
	private final JWTAuthenticationService service;

	public AuthenticationController(JWTAuthenticationService service) {
		this.service = service;
	}

	@POST
	@PermitAll
	@Consumes(APPLICATION_JSON)
	@Produces(TEXT_PLAIN)
	public String login(LoginDto dto) {
		return service.login(dto.mail, dto.password);
	}
}
