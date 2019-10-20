package dev.schoenberg.kicker_stats.helper;

import java.io.File;

import dev.schoenberg.kicker_stats.Main;

public class SimpleResourceLoader {
	public File loadFromResources(String filePath) {
		return new File(Main.class.getClassLoader().getResource(filePath).getFile());
	}
}
