package net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreMaps;

/**
 * <p>
 * Parses sockstat-style text, which appears in /proc/net/sockstat and /proc/net/sockstat6.
 * </p>
 * 
 * e.g.)
 * 
 * <pre>
 * sockets: used 1534
 * TCP: inuse 34 orphan 0 tw 34 alloc 54 mem 8
 * UDP: inuse 11 mem 11
 * UDPLITE: inuse 0
 * RAW: inuse 0
 * FRAG: inuse 0 memory 0
 * </pre>
 */
public class SockStatStyleReader {
	private static final Logger LOG = LoggerFactory.getLogger(SockStatStyleReader.class);

	public static Map<String, LongCompositeData> readFile(final String path) {
		final Map<String, Map<String, Long>> values = new HashMap<>();

		for (final String line : MoreFiles.readLines(path)) {
			final String[] parts = line.split(": ", 2);
			final String category = parts[0];

			final String[] members = parts[1].split(" ");
			if (members.length % 2 != 0) {
				LOG.warn("{}: failed to parse line: {}", path, line);
				continue;
			}

			for (int i = 0; i < members.length; i += 2) {
				final String name = members[i];
				final Long value = Long.valueOf(members[i + 1]);
				values.computeIfAbsent(category, __ -> new HashMap<>()).put(name, value);
			}
		}

		return MoreMaps.mapValues(values, subvalues-> new LongCompositeData(subvalues, path));
	}
}
