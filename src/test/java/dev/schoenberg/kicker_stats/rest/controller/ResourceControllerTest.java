package dev.schoenberg.kicker_stats.rest.controller;

import static dev.schoenberg.kicker_stats.exceptionWrapper.ExceptionWrapper.*;
import static javax.ws.rs.core.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.rest.controller.ResourceController;
import dev.schoenberg.kicker_stats.rest.controller.ResourceController.ResourceLoader;

public class ResourceControllerTest {

	private ResourceController tested;
	private TestResourceLoader loader;

	@Before
	public void setUp() {
		loader = new TestResourceLoader();
		tested = new ResourceController(loader);
	}

	@Test
	public void returns404IfCouldNotBeLoaded() {
		loader.toThrow = new RuntimeException();

		ThrowingCallable act = () -> tested.version("iDoNotExist");

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(act);
	}

	@Test
	public void returnStatusIs200() throws IOException {
		setupFile("file", ".tmp");

		Response result = tested.version("file.tmp");

		assertThat(result.getStatus()).isEqualTo(200);
	}

	@Test
	public void mimeTypeForCss() throws IOException {
		setupFile("tmp", ".css");

		Response result = tested.version("tmp.css");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("text/css");
	}

	@Test
	public void mimeTypeForHtml() throws IOException {
		setupFile("tmp", ".html");

		Response result = tested.version("tmp.html");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("text/html");
	}

	@Test
	public void mimeTypeForJS() throws IOException {
		setupFile("tmp", ".js");

		Response result = tested.version("tmp.js");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("application/javascript");
	}

	private void setupFile(String prefix, String suffix) {
		loader.toReturn = silentThrow(() -> File.createTempFile(prefix, suffix));
		loader.toReturn.deleteOnExit();
	}

	private class TestResourceLoader implements ResourceLoader {
		private RuntimeException toThrow;
		private File toReturn;

		@Override
		public InputStream loadFile(String resourcePath) {
			if (toThrow != null) {
				throw toThrow;
			}
			return silentThrow(() -> new FileInputStream(toReturn));
		}
	}
}
