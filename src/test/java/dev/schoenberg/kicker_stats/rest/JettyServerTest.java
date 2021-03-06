package dev.schoenberg.kicker_stats.rest;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import dev.schoenberg.kicker_stats.ServerTest;
import kong.unirest.Unirest;

@Category(ServerTest.class)
public class JettyServerTest {
	public final static int PORT = 8080;

	private HookedServer tested;

	@Before
	public void setUp() throws Exception {
		tested = new HookedServer(PORT, Arrays.asList(new TestServlet()));
		tested.run();
	}

	@After
	public void tearDown() throws Exception {
		tested.close();
	}

	@Test
	public void startsServlet() throws Exception {
		String content = Unirest.get("http://localhost:" + PORT + "/test").asString().getBody();
		assertThat(content).isEqualTo("Hello World");
	}

	@Path("")
	public static class TestServlet implements ServerService {
		@GET
		@Path("/test")
		@Produces(MediaType.TEXT_PLAIN)
		public String test() {
			return "Hello World";
		}
	}
}
