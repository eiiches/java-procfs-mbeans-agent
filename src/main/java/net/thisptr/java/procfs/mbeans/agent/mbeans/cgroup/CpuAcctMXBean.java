package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface CpuAcctMXBean {

	long getUsage();

	long getUsageUser();

	long getUsageSys();

	LongCompositeData getStat();
}
