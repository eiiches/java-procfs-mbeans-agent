package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

public class VmStat implements VmStatMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(VmStat.class);

	@Override
	public LongCompositeData get$() throws IOException {
		return new LongCompositeData(parse(), "/proc/vmstat");
	}

	private static Map<String, Long> parse() throws IOException {
		final Map<String, Long> out = new HashMap<>();

		for (final String rawLine : MoreFiles.readLines("/proc/vmstat")) {
			final String[] kv = rawLine.split(" +");
			if (kv.length != 2) {
				LOG.warn("/proc/vmstat: failed to parse line: {}", rawLine);
				continue;
			}
			out.put(kv[0], Long.parseLong(kv[1]));
		}

		return out;
	}
}
