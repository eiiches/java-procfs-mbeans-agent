package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

/**
 * TODO: numa_stat
 */
@MXBean
public interface MemoryMXBean {
	/**
	 * @return memory.failcnt
	 */
	long getFailcnt();

	/**
	 * @return memory.limit_in_bytes
	 */
	long getLimitInBytes();

	/**
	 * @return memory.max_usage_in_bytes
	 */
	long getMaxUsageInBytes();

	/**
	 * @return memory.soft_limit_in_bytes
	 */
	long getSoftLimitInBytes();

	/**
	 * @return memory.usage_in_bytes
	 */
	long getUsageInBytes();

	// ----- kmem -----

	/**
	 * @return memory.kmem.failcnt
	 */
	long getKmemFailcnt();

	/**
	 * @return memory.kmem.limit_in_bytes
	 */
	long getKmemLimitInBytes();

	/**
	 * @return memory.kmem.max_usage_in_bytes
	 */
	long getKmemMaxUsageInBytes();

	/**
	 * @return memory.kmem.usage_in_bytes
	 */
	long getKmemUsageInBytes();

	// ----- kmem.tcp -----

	/**
	 * @return memory.kmem.tcp.failcnt
	 */
	long getKmemTcpFailcnt();

	/**
	 * @return memory.kmem.tcp.limit_in_bytes
	 */
	long getKmemTcpLimitInBytes();

	/**
	 * @return memory.kmem.tcp.max_usage_in_bytes
	 */
	long getKmemTcpMaxUsageInBytes();

	/**
	 * @return memory.kmem.tcp.usage_in_bytes
	 */
	long getKmemTcpUsageInBytes();

	// ----- memsw -----

	/**
	 * @return memory.memsw.failcnt
	 */
	long getMemswFailcnt();

	/**
	 * @return memory.memsw.limit_in_bytes
	 */
	long getMemswLimitInBytes();

	/**
	 * @return memory.memsw.max_usage_in_bytes
	 */
	long getMemswMaxUsageInBytes();

	/**
	 * @return memory.memsw.usage_in_bytes
	 */
	long getMemswUsageInBytes();

	// ----- stat -----

	/**
	 * @return memory.stat
	 */
	LongCompositeData getStat();
}
