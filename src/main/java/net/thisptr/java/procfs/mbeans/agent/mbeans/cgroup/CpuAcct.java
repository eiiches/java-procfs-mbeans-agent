package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

/**
 * TODO: usage_percpu_user, usage_percpu_sys, usage_percpu
 * TODO: usage_all
 */
public class CpuAcct implements CpuAcctMXBean {
	private final File cgroupfs = new File("/sys/fs/cgroup");

	private long readAsLong(final String path) {
		try {
			return MoreFiles.readAsLong(new File(cgroupfs, path).toPath());
		} catch (NoSuchFileException e) {
			throw new UnsupportedOperationException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getUsage() {
		return readAsLong("cpu/cpuacct.usage");
	}

	@Override
	public long getUsageSys() {
		return readAsLong("cpu/cpuacct.usage_sys");
	}

	@Override
	public long getUsageUser() {
		return readAsLong("cpu/cpuacct.usage_user");
	}

	@Override
	public LongCompositeData getStat() {
		try {
			return MoreFiles.readAsLongMap(new File(cgroupfs, "cpu/cpuacct.stat").toPath(), " ");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
