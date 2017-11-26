package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/softirqs.c#L9">https://github.com/torvalds/linux/blob/v4.13/fs/proc/softirqs.c#L9</a>
 */
public class SoftIrqs implements SoftIrqsMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(SoftIrqs.class);

	private static final SingleCache<Data> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final List<String> lines = MoreFiles.readLines("/proc/softirqs");

		final Data data = new Data();

		final String[] cpus = lines.get(0).trim().split(" +");
		for (int index = 0; index < cpus.length; ++index)
			data.cpus.put(cpus[index].toLowerCase(), index); // convert to lower case because /proc/stat uses lower case CPU names

		for (int nr = 1; nr < lines.size(); ++nr) {
			final String[] tokens = lines.get(nr).trim().split(":", 2);
			if (tokens.length != 2) {
				LOG.warn("/proc/softirqs: invalid line: {}", lines.get(nr));
				continue;
			}
			final String name = tokens[0];
			final List<Long> values = Arrays.stream(tokens[1].trim().split(" +")).map(value -> Long.parseLong(value)).collect(Collectors.toList());
			if (values.size() != data.cpus.size()) {
				LOG.warn("/proc/softirqs: unexpected number of columns in line: {}", lines.get(nr));
				continue;
			}
			data.softirqs.put(name, values);
		}

		return data;
	});

	private static class Data {
		public Map<String, Integer> cpus = new HashMap<>();
		public Map<String, List<Long>> softirqs = new HashMap<>();
	}

	private final String cpu;
	private final String softirq;

	public SoftIrqs(final String softirq, final String cpu) {
		this.softirq = softirq;
		this.cpu = cpu;
	}

	@Override
	public long getsoftirqs() {
		final Data data = CACHE.get();
		final Integer index = data.cpus.get(cpu);
		if (index == null)
			return -1;
		final List<Long> values = data.softirqs.get(softirq);
		if (values == null)
			return -1;
		return values.get(index);
	}

	public static Collection<String> listCpus() {
		return CACHE.get().cpus.keySet();
	}

	public static Collection<String> listSoftIrqs() {
		return CACHE.get().softirqs.keySet();
	}
}
