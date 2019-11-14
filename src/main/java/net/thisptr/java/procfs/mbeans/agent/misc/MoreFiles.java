package net.thisptr.java.procfs.mbeans.agent.misc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

public class MoreFiles {
	private static final Logger LOG = LoggerFactory.getLogger(MoreFiles.class);

	public static String readAsString(final Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	public static long readAsLong(final Path path) throws NumberFormatException, IOException {
		return Long.parseLong(readAsString(path).trim());
	}

	/**
	 * <pre>
	 * nr_periods 0
	 * nr_throttled 0
	 * throttled_time 0
	 * </pre>
	 * 
	 * @param path    path
	 * @param sepText separator
	 * @return LongCompositeData
	 * @throws IOException on I/O error
	 */
	public static LongCompositeData readAsLongMap(final Path path, final String sepText) throws IOException {
		final Pattern sep = Pattern.compile(Pattern.quote(sepText));

		final Map<String, Long> data = new HashMap<>();
		for (final String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
			final String[] tokens = sep.split(line, 2);
			if (tokens.length != 2) {
				LOG.warn("{}: failed to parse line: {}", path.toString(), line.trim());
				continue;
			}
			try {
				final String key = tokens[0].trim();
				final long value = Long.parseLong(tokens[1]);
				data.put(key, value);
			} catch (NumberFormatException e) {
				LOG.warn("{}: failed to parse line: {}", path.toString(), line.trim());
				continue;
			}
		}

		return new LongCompositeData(data, path.toString());
	}

	public static List<String> readLines(final String path) {
		try {
			final List<String> lines = new ArrayList<>();
			try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
				String line;
				while ((line = reader.readLine()) != null)
					lines.add(line);
			}
			return lines;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String> readLinesAndExpectRows(final String path, final int rows) {
		final List<String> lines = readLines(path);
		if (lines.size() != rows)
			throw new IllegalStateException(path + ": expected " + rows + " lines");
		return lines;
	}
}
