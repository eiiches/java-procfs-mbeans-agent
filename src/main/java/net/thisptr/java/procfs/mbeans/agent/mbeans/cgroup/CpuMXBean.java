package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface CpuMXBean {

	long getCfsQuotaUs();

	long getCfsPeriodUs();

	long getShares();

	LongCompositeData getStat();
}
