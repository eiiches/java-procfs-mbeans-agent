package net.thisptr.java.procfs.mbeans.agent.misc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MoreFiles {
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
