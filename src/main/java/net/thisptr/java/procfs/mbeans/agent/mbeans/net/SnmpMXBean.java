package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface SnmpMXBean {
	LongCompositeData getIp();

	LongCompositeData getIcmp();

	LongCompositeData getIcmpMsg();

	LongCompositeData getTcp();

	LongCompositeData getUdp();

	LongCompositeData getUdpLite();
}
