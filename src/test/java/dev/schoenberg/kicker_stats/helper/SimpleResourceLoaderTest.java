package dev.schoenberg.kicker_stats.helper;

import static org.assertj.core.api.Assertions.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import dev.schoenberg.kicker_stats.core.helper.SimpleResourceLoader;

public class SimpleResourceLoaderTest {
	private SimpleResourceLoader tested;

	@Before
	public void setup() {
		tested = new SimpleResourceLoader();
	}

	@Test
	public void loadsResource() {
		InputStream result = tested.loadFromResources("tmp.txt");

		assertThat(result).hasContent("Hello, world!");
	}

	@Test
	public void throwsExceptionIfResourceDoesNotExist() {
		assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> tested.loadFromResources("iDoNotExist.file"));
	}
}
