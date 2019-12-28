package dev.schoenberg.kicker_stats.rest.controller;

import static dev.schoenberg.kicker_stats.core.helper.exceptionWrapper.ExceptionWrapper.*;
import static java.nio.file.Files.*;
import static javax.ws.rs.core.Response.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.tika.Tika;

import dev.schoenberg.kicker_stats.rest.ServerService;

@Path("/resources")
public class ResourceController implements ServerService {
	private static final String PATH_PARAM_RESOURCE_PATH = "path";

	private final ResourceLoader resourceLoader;
	private final Tika tika;

	private java.nio.file.Path tempFolder;

	public ResourceController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		tika = new Tika();
	}

	@GET
	@PermitAll
	@Path("/{" + PATH_PARAM_RESOURCE_PATH + " : (.+)?}")
	public Response getResource(@PathParam(PATH_PARAM_RESOURCE_PATH) String path) {
		// TODO: write test
		if (path.isEmpty()) {
			path = "index.html";
		}
		File file = fetchFile(path);
		String mimeType = silentThrow(() -> tika.detect(file));
		return ok(file, mimeType).build();
	}

	// TODO: Move to business logic class
	private File fetchFile(String path) {
		initTempFolder();
		return fetchFileFromResources(path);
	}

	private void initTempFolder() {
		if (tempFolder == null) {
			tempFolder = silentThrow(() -> createTempDirectory("kicker_stats"));
			tempFolder.toFile().deleteOnExit();
		}
	}

	private File fetchFileFromResources(String path) {
		java.nio.file.Path filePath = Paths.get(tempFolder.toString(), path);
		if (notExists(filePath)) {
			silentThrow(() -> {
				InputStream resource = fetchResource(path);
				createParentDirs(filePath);
				return copy(resource, filePath);
			});
			filePath.toFile().deleteOnExit();
		}
		return filePath.toFile();
	}

	private void createParentDirs(java.nio.file.Path filePath) throws IOException {
		java.nio.file.Path parent = filePath.getParent();
		createDirectories(parent);

		while (!tempFolder.toString().equals(parent.toString())) {
			parent.toFile().deleteOnExit();
			parent = parent.getParent();
		}
	}

	private InputStream fetchResource(String path) {
		try {
			return resourceLoader.loadFile(path);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	public interface ResourceLoader {
		InputStream loadFile(String resourcePath);
	}
}
