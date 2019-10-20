package dev.schoenberg.kicker_stats.rest.service;

import static javax.ws.rs.core.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResourceServiceTest {

	private ResourceService tested;
	private File toReturn;

	@Before
	public void setUp() {
		tested = new ResourceService(s -> toReturn);
	}

	@After
	public void tearDown() {
		toReturn.deleteOnExit();
	}

	@Test
	public void returns404IfFileDoesNotExist() {
		toReturn = new File("");

		ThrowingCallable act = () -> tested.version("");

		assertThatExceptionOfType(NotFoundException.class).isThrownBy(act);
	}

	@Test
	public void returnStatusIs200() throws IOException {
		toReturn = File.createTempFile("file", ".tmp");

		Response result = tested.version("");

		assertThat(result.getStatus()).isEqualTo(200);
	}

	@Test
	public void mimeTypeForCss() throws IOException {
		toReturn = File.createTempFile("tmp", ".css");

		Response result = tested.version("");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("text/css");
	}

	@Test
	public void mimeTypeForHtml() throws IOException {
		toReturn = File.createTempFile("tmp", ".html");

		Response result = tested.version("");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("text/html");
	}

	@Test
	public void mimeTypeForJS() throws IOException {
		toReturn = File.createTempFile("tmp", ".js");

		Response result = tested.version("");

		assertThat(result.getHeaderString(CONTENT_TYPE)).isEqualTo("application/javascript");
	}
}
