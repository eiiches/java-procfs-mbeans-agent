package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface NetStatMXBean {
	LongCompositeData getIpExt();

	LongCompositeData getTcpExt();
}
