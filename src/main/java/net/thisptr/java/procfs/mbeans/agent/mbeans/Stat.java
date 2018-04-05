package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.time.Duration;

import org.slf4j.Logger;
import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

public class Stat implements StatMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(Stat.class);

	private static final SingleCache<Data> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final Data data = new Data();
		for (final String line : MoreFiles.readLines("/proc/stat")) {
			final String[] kv = line.split(" +");
			if (kv.length < 2) {
				LOG.warn("/proc/stat: failed to parse line: {}", line);
				continue;
			}
			final String key = kv[0];
			final long value = Long.parseLong(kv[1]);
			switch (key) {
				case "intr":
					data.intr = value;
					break;
				case "ctxt":
					data.ctxt = value;
					break;
				case "processes":
					data.processes = value;
					break;
				case "procs_running":
					data.procs_running = value;
					break;
				case "procs_blocked":
					data.procs_blocked = value;
					break;
				case "softirq":
					data.softirq = value;
					break;
				default:
					// do nothing
			}
		}
		return data;
	});

	private static class Data {
		public long softirq = -1;
		public long intr = -1;
		public long procs_blocked = -1;
		public long procs_running = -1;
		public long processes = -1;
		public long ctxt = -1;
	}

	@Override
	public long getsoftirq() {
		return CACHE.get().softirq;
	}

	@Override
	public long getprocs_blocked() {
		return CACHE.get().procs_blocked;
	}

	@Override
	public long getprocs_running() {
		return CACHE.get().procs_running;
	}

	@Override
	public long getprocesses() {
		return CACHE.get().processes;
	}

	@Override
	public long getctxt() {
		return CACHE.get().ctxt;
	}

	@Override
	public long getintr() {
		return CACHE.get().intr;
	}
}
