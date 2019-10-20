package dev.schoenberg.kicker_stats;

import static java.lang.System.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import dev.schoenberg.kicker_stats.rest.HookedServer;
import dev.schoenberg.kicker_stats.rest.Service;
import kong.unirest.Unirest;

@Category({ ServerTest.class, PersistenceTest.class })
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
