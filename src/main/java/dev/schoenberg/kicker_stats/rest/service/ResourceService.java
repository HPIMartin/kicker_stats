package dev.schoenberg.kicker_stats.rest.service;

import static dev.schoenberg.kicker_stats.exceptionWrapper.ExceptionWrapper.*;
import static javax.ws.rs.core.Response.*;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.tika.Tika;

import dev.schoenberg.kicker_stats.rest.Service;

@Path("/resources")
public class ResourceService implements Service {
	private static final String PATH_PARAM_RESOURCE_PATH = "path";

	private final ResourceLoader resourceLoader;
	private final Tika tika;

	public ResourceService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		tika = new Tika();
	}

	@GET
	@Path("/{" + PATH_PARAM_RESOURCE_PATH + " : (.+)?}")
	public Response version(@PathParam(PATH_PARAM_RESOURCE_PATH) String path) {
		File file = resourceLoader.loadFile(path);

		if (!file.exists()) {
			throw new NotFoundException();
		}

		String mimeType = silentThrow(() -> tika.detect(file));
		return ok(file, mimeType).build();
	}

	public interface ResourceLoader {
		File loadFile(String resourcePath);
	}
}
