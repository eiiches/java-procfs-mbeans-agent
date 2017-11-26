package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

public class Stat_Cpu implements Stat_CpuMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(Stat_Cpu.class);

	private static final SingleCache<Map<String, CpuUsage>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final Map<String, CpuUsage> usages = new HashMap<>();

		for (final String line : MoreFiles.readLines("/proc/stat")) {
			final String[] kv = line.split(" +");
			if (!kv[0].startsWith("cpu"))
				continue;
			final String cpu = kv[0];
			if (kv.length != 11) {
				LOG.warn("/proc/stat: unexpected number of columns in line: {}", line);
				continue;
			}

			final CpuUsage usage = new CpuUsage();
			usage.user = Long.parseLong(kv[1]);
			usage.nice = Long.parseLong(kv[2]);
			usage.system = Long.parseLong(kv[3]);
			usage.idle = Long.parseLong(kv[4]);
			usage.iowait = Long.parseLong(kv[5]);
			usage.irq = Long.parseLong(kv[6]);
			usage.softirq = Long.parseLong(kv[7]);
			usage.steal = Long.parseLong(kv[8]);
			usage.guest = Long.parseLong(kv[9]);
			usage.guest_nice = Long.parseLong(kv[10]);
			usages.put(cpu, usage);
		}

		return usages;
	});

	private static class CpuUsage {
		public long user;
		public long nice;
		public long system;
		public long idle;
		public long iowait;
		public long irq;
		public long softirq;
		public long steal;
		public long guest;
		public long guest_nice;
	}

	private final String cpu;

	public Stat_Cpu(final String cpu) {
		this.cpu = cpu;
	}

	@Override
	public long getcpu_user() {
		return CACHE.get().get(cpu).user;
	}

	@Override
	public long getcpu_nice() {
		return CACHE.get().get(cpu).nice;
	}

	@Override
	public long getcpu_system() {
		return CACHE.get().get(cpu).system;
	}

	@Override
	public long getcpu_idle() {
		return CACHE.get().get(cpu).idle;
	}

	@Override
	public long getcpu_iowait() {
		return CACHE.get().get(cpu).iowait;
	}

	@Override
	public long getcpu_irq() {
		return CACHE.get().get(cpu).irq;
	}

	@Override
	public long getcpu_softirq() {
		return CACHE.get().get(cpu).softirq;
	}

	@Override
	public long getcpu_steal() {
		return CACHE.get().get(cpu).steal;
	}

	@Override
	public long getcpu_guest() {
		return CACHE.get().get(cpu).guest;
	}

	@Override
	public long getcpu_guest_nice() {
		return CACHE.get().get(cpu).guest_nice;
	}

	public static Set<String> listCpus() {
		return CACHE.get().keySet();
	}
}
