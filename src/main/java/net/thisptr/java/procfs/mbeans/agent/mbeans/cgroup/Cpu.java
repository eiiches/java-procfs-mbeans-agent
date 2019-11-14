package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import java.io.File;
import java.io.IOException;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

public class Cpu implements CpuMXBean {
	private final File cgroupfs = new File("/sys/fs/cgroup");

	private long readAsLong(final String path) {
		try {
			return MoreFiles.readAsLong(new File(cgroupfs, path).toPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getCfsQuotaUs() {
		return readAsLong("cpu/cpu.cfs_quota_us");
	}

	@Override
	public long getCfsPeriodUs() {
		return readAsLong("cpu/cpu.cfs_period_us");
	}

	@Override
	public long getShares() {
		return readAsLong("cpu/cpu.shares");
	}

	@Override
	public LongCompositeData getStat() {
		try {
			return MoreFiles.readAsLongMap(new File(cgroupfs, "cpu/cpu.stat").toPath(), " ");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
