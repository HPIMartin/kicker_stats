package dev.schoenberg.applications.kicker_stats;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.schoenberg.applications.kicker_stats.rest.HookedServer;
import dev.schoenberg.applications.kicker_stats.rest.Service;
import kong.unirest.Unirest;

public class AcceptanceTest {
	private static HookedServer server;
	private static String baseUrl = "http://localhost:" + Main.PORT;

	@BeforeClass
	public static void setUp() throws Exception {
		Main.serverFactory = AcceptanceTest::createHooked;
		Main.main(new String[0]);
	}

	private static HookedServer createHooked(int p, List<Service> s) {
		server = new HookedServer(p, s);
		server.closeServer = false;
		server.run();
		return server;
	}

	@AfterClass
	public static void tearDown() throws IOException {
		server.closeServer = true;
		server.close();
	}

	@Test
	public void versionEndpointIsAvailable() {
		String content = Unirest.get(baseUrl + "/version").asString().getBody();
		assertThat(content).isNotBlank();
	}
}
