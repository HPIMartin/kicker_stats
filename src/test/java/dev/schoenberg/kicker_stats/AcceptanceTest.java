package dev.schoenberg.kicker_stats;

import static java.lang.System.*;
import static java.nio.file.Files.*;
import static javax.ws.rs.core.HttpHeaders.*;
import static javax.ws.rs.core.MediaType.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
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
import dev.schoenberg.kicker_stats.rest.ServerService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Category({ ServerTest.class, PersistenceTest.class })
public class AcceptanceTest {
	private static HookedServer server;
	private static String baseUrl = "http://localhost:" + Main.PORT;
	private static Path tempDb;

	@BeforeClass
	public static void setUp() throws Exception {
		Main.serverFactory = AcceptanceTest::createHooked;
		tempDb = createTempDatabase();
		Main.url = "jdbc:sqlite:" + tempDb.toString().replace("\\", "/");
		Main.main(new String[0]);
	}

	private static Path createTempDatabase() throws IOException {
		Path tempDb = Files.createTempFile("kickerStats", ".db");
		// Also cleaning remaining files from previous runs
		list(tempDb.getParent()).filter(AcceptanceTest::isDbFile).map(Path::toFile).forEach(File::deleteOnExit);
		return tempDb;
	}

	private static boolean isDbFile(Path filePath) {
		String fileName = filePath.getFileName().toString();
		return fileName.startsWith("kickerStats") && fileName.endsWith(".db");
	}

	private static HookedServer createHooked(int p, List<ServerService> s) {
		server = new HookedServer(p, s);
		server.closeServer = false;
		return server;
	}

	@AfterClass
	public static void tearDown() throws IOException {
		server.closeServer = true;
		server.close();
	}

	@Test
	public void versionEndpointIsAvailable() {
		String content = get("/version");
		assertThat(content).isNotBlank();
	}

	@Test
	public void resourceCanBeRequested() throws IOException, URISyntaxException {
		String expected = loadResourceFile("subfolder/fileInSubfolder.css");

		String content = get("/resources/subfolder/fileInSubfolder.css");

		assertThat(content).isEqualTo(expected);
	}

	@Test
	public void playerIsCreated() {
		String body = "{\"name\":\"Aaron Rodgers\", \"email\":\"houdini@packers.com\"}";
		post("/players", body);

		String response = get("/players");
		assertThat(response).isEqualTo("{\"players\":[{\"name\":\"Aaron Rodgers\",\"email\":\"houdini@packers.com\"}]}");
	}

	private String loadResourceFile(String resourcePath) throws URISyntaxException, IOException {
		Path path = Paths.get(getClass().getClassLoader().getResource(resourcePath).toURI());
		return String.join(lineSeparator(), Files.readAllLines(path));
	}

	private String get(String subUrl) {
		HttpResponse<String> response = Unirest.get(baseUrl + subUrl).asString();
		assertThat(response.getStatus()).isEqualTo(200);
		return response.getBody();
	}

	private String post(String subUrl, String body) {
		HttpResponse<String> response = Unirest.post(baseUrl + "/players").body(body).header(CONTENT_TYPE, APPLICATION_JSON).asString();
		assertThat(response.getStatus()).isLessThan(300);
		return response.getBody();
	}
}
