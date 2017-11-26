package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface SockStat6MXBean {
	LongCompositeData getUDP6();

	LongCompositeData getUDPLITE6();

	LongCompositeData getRAW6();

	LongCompositeData getFRAG6();

	LongCompositeData getTCP6();
}
