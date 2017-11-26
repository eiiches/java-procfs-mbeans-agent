package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.time.Duration;
import java.util.List;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/loadavg.c#L16">https://github.com/torvalds/linux/blob/v4.13/fs/proc/loadavg.c#L16</a>
 */
public class LoadAvg implements LoadAvgMXBean {
	private static final SingleCache<Data> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final Data info = new Data();

		final List<String> lines = MoreFiles.readLinesAndExpectRows("/proc/loadavg", 1);
		final String[] stats = lines.get(0).split(" ");
		info.load1m = Double.parseDouble(stats[0]);
		info.load5m = Double.parseDouble(stats[1]);
		info.load15m = Double.parseDouble(stats[2]);
		final String[] threads = stats[3].split("/");
		info.nr_running = Integer.parseInt(threads[0]);
		info.nr_threads = Integer.parseInt(threads[1]);

		return info;
	});

	private static class Data {
		public double load1m;
		public double load5m;
		public double load15m;
		public int nr_running;
		public int nr_threads;
	}

	@Override
	public double getload1m() {
		return CACHE.get().load1m;
	}

	@Override
	public double getload5m() {
		return CACHE.get().load5m;
	}

	@Override
	public double getload15m() {
		return CACHE.get().load15m;
	}

	@Override
	public long getnr_running() {
		return CACHE.get().nr_running;
	}

	@Override
	public long getnr_threads() {
		return CACHE.get().nr_threads;
	}
}
