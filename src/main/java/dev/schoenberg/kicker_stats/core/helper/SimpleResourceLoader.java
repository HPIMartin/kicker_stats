package dev.schoenberg.kicker_stats.core.helper;

import java.io.InputStream;

public class SimpleResourceLoader {
	public InputStream loadFromResources(String filePath) {
		InputStream resource = getClass().getClassLoader().getResourceAsStream(filePath);

		if (resource == null) {
			throw new RuntimeException("Resource not found");
		}

		return resource;
	}
}
