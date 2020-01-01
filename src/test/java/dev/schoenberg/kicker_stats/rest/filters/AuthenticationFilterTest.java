package dev.schoenberg.kicker_stats.rest.filters;

import static dev.schoenberg.kicker_stats.rest.Roles.*;
import static java.util.Arrays.*;
import static javax.ws.rs.core.MediaType.*;
import static org.assertj.core.api.Assertions.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import dev.schoenberg.kicker_stats.ServerTest;
import dev.schoenberg.kicker_stats.core.domain.Admin;
import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.rest.HookedServer;
import dev.schoenberg.kicker_stats.rest.ServerService;
import kong.unirest.Unirest;

@Category(ServerTest.class)
public class AuthenticationFilterTest {
	private final static int PORT = 8080;

	private static HookedServer tested;
	private static Player player;

	@BeforeClass
	public static void setUp() throws Exception {
		tested = new HookedServer(PORT, asList(new AuthenticationFilter(AuthenticationFilterTest::assertAuth), new TestServlet()));
		tested.run();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		tested.close();
	}

	@Test
	public void allRequestsPermitted() throws Exception {
		int statusCode = Unirest.get("http://localhost:" + PORT + "/permitAll").asString().getStatus();
		assertThat(statusCode).isLessThan(300);
	}

	@Test
	public void allRequestsDenied() throws Exception {
		int statusCode = Unirest.get("http://localhost:" + PORT + "/denyAll").asString().getStatus();
		assertThat(statusCode).isEqualTo(403);
	}

	@Test
	public void allowsOptionRequests() {
		int statusCode = Unirest.options("http://localhost:" + PORT + "/denyAll").asString().getStatus();
		assertThat(statusCode).isLessThan(300);
	}

	@Test
	public void missingAuthParameter() throws Exception {
		int statusCode = Unirest.get("http://localhost:" + PORT + "/user").asString().getStatus();
		assertThat(statusCode).isEqualTo(401);
	}

	@Test
	public void adminRequestPermitted() throws Exception {
		String authValue = arrangeAuth(true);
		int statusCode = Unirest.get("http://localhost:" + PORT + "/admin").header("Authorization", authValue).asString().getStatus();
		assertThat(statusCode).isLessThan(300);
	}

	@Test
	public void userRequestPermitted() throws Exception {
		String authValue = arrangeAuth(false);
		int statusCode = Unirest.get("http://localhost:" + PORT + "/user").header("Authorization", authValue).asString().getStatus();
		assertThat(statusCode).isLessThan(300);
	}

	@Test
	public void undefinedRole() throws Exception {
		String authValue = arrangeAuth(false);
		int statusCode = Unirest.get("http://localhost:" + PORT + "/undefined").header("Authorization", authValue).asString().getStatus();
		assertThat(statusCode).isEqualTo(401);
	}

	@Test
	public void multipleRoles() throws Exception {
		String authValue = arrangeAuth(false);
		int statusCode = Unirest.get("http://localhost:" + PORT + "/multiple").header("Authorization", authValue).asString().getStatus();
		assertThat(statusCode).isEqualTo(401);
	}

	@Test
	public void nonAdminRequestDenied() throws Exception {
		String authValue = arrangeAuth(false);
		int statusCode = Unirest.get("http://localhost:" + PORT + "/admin").header("Authorization", authValue).asString().getStatus();
		assertThat(statusCode).isEqualTo(401);
	}

	private String arrangeAuth(boolean admin) {
		Player player = admin ? new Admin("", "", "") : new Player("", "", "");
		AuthenticationFilterTest.player = player;
		return player.toString();
	}

	private static Player assertAuth(String authValue) {
		assertThat(authValue).isEqualTo(player.toString());
		return player;
	}

	@Path("")
	public static class TestServlet implements ServerService {
		@GET
		@PermitAll
		@Path("/permitAll")
		@Produces(TEXT_PLAIN)
		public String permit() {
			return "permitted";
		}

		@GET
		@DenyAll
		@Path("/denyAll")
		@Produces(TEXT_PLAIN)
		public String deny() {
			return "denied";
		}

		@GET
		@Path("/user")
		@RolesAllowed(USER)
		@Produces(TEXT_PLAIN)
		public String user() {
			return "Hi user!";
		}

		@GET
		@Path("/admin")
		@RolesAllowed(ADMIN)
		@Produces(TEXT_PLAIN)
		public String admin() {
			return "Hi Admin!";
		}

		@GET
		@Path("/multiple")
		@RolesAllowed({ USER, ADMIN })
		@Produces(TEXT_PLAIN)
		public String multiple() {
			return "broken";
		}

		@GET
		@Path("/undefined")
		@RolesAllowed("undefined")
		@Produces(TEXT_PLAIN)
		public String undefined() {
			return "undefined";
		}
	}
}
