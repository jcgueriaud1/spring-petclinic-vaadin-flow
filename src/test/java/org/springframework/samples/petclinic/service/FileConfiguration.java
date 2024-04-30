package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConfiguration {

	static final String DATA_PATH_PROPERTY = "a11y.data";

	/**
	 * The default path of the file to store kit's data in the project root.
	 */
	static final String DEFAULT_DATA_FILE_PATH = "a11y/data";
	private Path filePathForResult;

	/**
	 * Gets the data-file path.
	 *
	 * @return the data-file path, not {@code null}
	 */
	public Path getFilePath(String pageName) {
		if (filePathForResult == null) {
			String propertyPath = System.getProperty(DATA_PATH_PROPERTY,
				DEFAULT_DATA_FILE_PATH);
			try {
				Path folderPath = Paths.get(propertyPath);
				Files.createDirectories(folderPath);
				filePathForResult = Paths.get(propertyPath, pageName);
			} catch (InvalidPathException | IOException e) {
				throw new RuntimeException(
					"Invalid data file path " + propertyPath, e);
			}
        }
		return filePathForResult;
	}
}
