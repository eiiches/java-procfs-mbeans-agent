package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import java.io.File;
import java.io.IOException;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

public class Memory implements MemoryMXBean {
	private final File cgroupfs = new File("/sys/fs/cgroup");

	private long readAsLong(final String path) {
		try {
			return MoreFiles.readAsLong(new File(cgroupfs, path).toPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getFailcnt() {
		return readAsLong("memory/memory.failcnt");
	}

	@Override
	public long getLimitInBytes() {
		return readAsLong("memory/memory.limit_in_bytes");
	}

	@Override
	public long getMaxUsageInBytes() {
		return readAsLong("memory/memory.max_usage_in_bytes");
	}

	@Override
	public long getSoftLimitInBytes() {
		return readAsLong("memory/memory.soft_limit_in_bytes");
	}

	@Override
	public long getUsageInBytes() {
		return readAsLong("memory/memory.usage_in_bytes");
	}

	@Override
	public long getKmemFailcnt() {
		return readAsLong("memory/memory.kmem.failcnt");
	}

	@Override
	public long getKmemLimitInBytes() {
		return readAsLong("memory/memory.kmem.limit_in_bytes");
	}

	@Override
	public long getKmemMaxUsageInBytes() {
		return readAsLong("memory/memory.kmem.max_usage_in_bytes");
	}

	@Override
	public long getKmemUsageInBytes() {
		return readAsLong("memory/memory.kmem.usage_in_bytes");
	}

	@Override
	public long getKmemTcpFailcnt() {
		return readAsLong("memory/memory.kmem.tcp.failcnt");
	}

	@Override
	public long getKmemTcpLimitInBytes() {
		return readAsLong("memory/memory.kmem.tcp.limit_in_bytes");
	}

	@Override
	public long getKmemTcpMaxUsageInBytes() {
		return readAsLong("memory/memory.kmem.tcp.max_usage_in_bytes");
	}

	@Override
	public long getKmemTcpUsageInBytes() {
		return readAsLong("memory/memory.kmem.tcp.usage_in_bytes");
	}

	@Override
	public long getMemswFailcnt() {
		return readAsLong("memory/memory.memsw.failcnt");
	}

	@Override
	public long getMemswLimitInBytes() {
		return readAsLong("memory/memory.memsw.limit_in_bytes");
	}

	@Override
	public long getMemswMaxUsageInBytes() {
		return readAsLong("memory/memory.memsw.max_usage_in_bytes");
	}

	@Override
	public long getMemswUsageInBytes() {
		return readAsLong("memory/memory.memsw.usage_in_bytes");
	}

	@Override
	public LongCompositeData getStat() {
		try {
			return MoreFiles.readAsLongMap(new File(cgroupfs, "memory/memory.stat").toPath(), " ");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
