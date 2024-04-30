package org.springframework.samples.petclinic.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.deque.html.axecore.results.AxeResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService implements DataService {

	private static final Logger LOGGER = LoggerFactory
		.getLogger(FileService.class);

	/**
	 * The default path of the file to store kit's data in the project root.
	 */
	static final String DEFAULT_DATA_FILE_PATH = "";

	private final FileConfiguration configuration = new FileConfiguration();

	static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void saveA11yResults(String pageName, AxeResults results) {
		Path filePath = configuration.getFilePath(pageName + ".json");
		File dataFile = filePath.toFile();
		try {
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
			MAPPER.writeValue(dataFile, results);
			LOGGER.debug("A11y data file updated "
				+ dataFile.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(
				"Cannot write the A11y data file: "
					+ filePath.toString(),
				e);
		}
	}

}
