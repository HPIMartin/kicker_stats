package dev.schoenberg.kicker_stats.rest.filters;

import static dev.schoenberg.kicker_stats.rest.Roles.*;
import static java.util.Arrays.*;
import static javax.ws.rs.core.HttpHeaders.*;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.rest.ServerService;

public class AuthenticationFilter implements ContainerRequestFilter, ServerService {
	private final Function<String, Player> playerByAuth;

	private @Context ResourceInfo resourceInfo;

	public AuthenticationFilter(Function<String, Player> playerByAuth) {
		this.playerByAuth = playerByAuth;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();

		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
		}

		if (method.isAnnotationPresent(DenyAll.class)) {
			requestContext.abortWith(status(FORBIDDEN).entity("Access blocked for all users!").build());
			return;
		}

		if (!evaluateRole(getAuthHeaders(requestContext), method)) {
			requestContext.abortWith(status(UNAUTHORIZED).entity("You cannot access this resource").build());
		}

	}

	private List<String> getAuthHeaders(ContainerRequestContext requestContext) {
		return requestContext.getHeaders().get(AUTHORIZATION);
	}

	private boolean evaluateRole(List<String> authorization, Method method) {
		if (authorization == null || authorization.isEmpty()) {
			return false;
		}

		if (method.isAnnotationPresent(RolesAllowed.class)) {
			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
			Set<String> rolesSet = new HashSet<>(asList(rolesAnnotation.value()));
			if (!isUserAllowed(authorization.get(0), rolesSet)) {
				return false;
			}
		}

		return true;
	}

	private boolean isUserAllowed(String authValue, Set<String> rolesSet) {
		Player player = playerByAuth.apply(authValue);
		if (rolesSet.size() != 1) {
			return false;
		}
		switch (rolesSet.iterator().next()) {
		case ADMIN:
			return player.isAdmin();
		case USER:
			return true;
		default:
			return false;
		}
	}
}
