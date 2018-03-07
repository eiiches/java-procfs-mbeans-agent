package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

public class MemInfo implements MemInfoMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(MemInfo.class);

	public Map<String, Long> parseMemInfo() throws IOException {
		final Map<String, Long> result = new HashMap<>();

		for (final String rawLine : MoreFiles.readLines("/proc/meminfo")) {
			final String[] keyAndVal = rawLine.split(":", 2);
			if (keyAndVal.length != 2) {
				LOG.warn("/proc/meminfo: failed to parse line: {}", rawLine);
				continue;
			}

			final String key = keyAndVal[0];
			final String[] val = keyAndVal[1].trim().split(" ", 2);

			final Long value;
			if (val.length == 2) {
				if (!val[1].trim().equals("kB")) {
					LOG.warn("/proc/meminfo: unsupported unit \"{}\"", val[1]);
					continue;
				}
				value = Long.valueOf(val[0]) * 1024;
			} else if (val.length == 1) {
				value = Long.valueOf(val[0]);
			} else {
				LOG.warn("/proc/meminfo: invalid value: {}", keyAndVal[1]);
				continue;
			}

			result.put(key, value);
		}

		return result;
	}

	@Override
	public LongCompositeData get$() throws IOException {
		return new LongCompositeData(parseMemInfo(), "/proc/meminfo");
	}
}
