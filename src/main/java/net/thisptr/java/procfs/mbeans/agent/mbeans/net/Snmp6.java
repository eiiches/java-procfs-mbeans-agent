package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

public class Snmp6 implements Snmp6MXBean {
	private static final Logger LOG = LoggerFactory.getLogger(Snmp6.class);

	@Override
	public LongCompositeData get$() {
		final Map<String, Long> values = new HashMap<>();
		for (final String line : MoreFiles.readLines("/proc/net/snmp6")) {
			final String[] kv = line.split("\t", 2);
			if (kv.length != 2) {
				LOG.warn("/proc/net/snmp6: failed to parse line: {}", line);
				continue;
			}
			final String key = kv[0].trim();
			final long value = Long.parseLong(kv[1].trim());
			values.put(key, value);
		}
		return new LongCompositeData(values);
	}
}
