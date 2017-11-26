package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import java.time.Duration;
import java.util.List;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/array.c#L586">https://github.com/torvalds/linux/blob/v4.13/fs/proc/array.c#L586</a>
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/task_mmu.c#L93">https://github.com/torvalds/linux/blob/v4.13/fs/proc/task_mmu.c#L93</a>
 */
public class ProcessStatm implements ProcessStatmMXBean {
	private static SingleCache<Data> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final List<String> lines = MoreFiles.readLinesAndExpectRows("/proc/self/statm", 1);
		final String[] values = lines.get(0).split(" ");
		if (values.length < 7)
			throw new RuntimeException("/proc/self/statm: unable to parse line: " + lines.get(0));
		final Data data = new Data();
		data.size = Long.parseLong(values[0]);
		data.resident = Long.parseLong(values[1]);
		data.shared = Long.parseLong(values[2]);
		data.text = Long.parseLong(values[3]);
		// values[4] was library pages, which has been unused since Linux 2.6
		data.data = Long.parseLong(values[5]);
		// values[6] was dirty pages, which has been unused since Linux 2.6
		return data;
	});

	private static class Data {
		public long size;
		public long resident;
		public long shared;
		public long text;
		public long data;
	}

	@Override
	public long getsize() {
		return CACHE.get().size;
	}

	@Override
	public long getresident() {
		return CACHE.get().resident;
	}

	@Override
	public long getshared() {
		return CACHE.get().shared;
	}

	@Override
	public long gettext() {
		return CACHE.get().text;
	}

	@Override
	public long getdata() {
		return CACHE.get().data;
	}
}
