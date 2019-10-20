package dev.schoenberg.kicker_stats.helper;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.Test;

public class SimpleResourceLoaderTest {
	@Test
	public void loadsResource() {
		SimpleResourceLoader tested = new SimpleResourceLoader();

		File result = tested.loadFromResources("tmp.txt");

		assertThat(result).exists();
		assertThat(result).hasContent("Hello, world!");
	}
}
