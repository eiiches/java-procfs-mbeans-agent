package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import java.time.Duration;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

public class ProcessStat implements ProcessStatMXBean {
	private static SingleCache<Data> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final String line = MoreFiles.readLinesAndExpectRows("/proc/self/stat", 1).get(0);
		final String[] tokens = line.split(" ");
		final Data data = new Data();
		data.min_flt = Long.parseLong(tokens[9]);
		data.maj_flt = Long.parseLong(tokens[11]);
		data.utime = Long.parseLong(tokens[13]);
		data.stime = Long.parseLong(tokens[14]);
		data.num_threads = Integer.parseInt(tokens[19]);
		data.vsize = Long.parseLong(tokens[22]);
		data.rss = Long.parseLong(tokens[23]);
		data.gtime = Long.parseLong(tokens[42]);
		return data;
	});

	private static class Data {
		public int num_threads;
		public long vsize;
		public long rss;
		public long min_flt;
		public long maj_flt;
		public long utime;
		public long stime;
		public long gtime;
	}

	@Override
	public int getnum_threads() {
		return CACHE.get().num_threads;
	}

	@Override
	public long getvsize() {
		return CACHE.get().vsize;
	}

	@Override
	public long getrss() {
		return CACHE.get().rss;
	}

	@Override
	public long getmin_flt() {
		return CACHE.get().min_flt;
	}

	@Override
	public long getmaj_flt() {
		return CACHE.get().maj_flt;
	}

	@Override
	public long getutime() {
		return CACHE.get().utime;
	}

	@Override
	public long getstime() {
		return CACHE.get().stime;
	}

	@Override
	public long getgtime() {
		return CACHE.get().gtime;
	}
}
