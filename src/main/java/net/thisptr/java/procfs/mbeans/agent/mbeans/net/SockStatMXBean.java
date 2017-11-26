package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import javax.management.MXBean;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

@MXBean
public interface SockStatMXBean {
	LongCompositeData getUDP();

	LongCompositeData getUDPLITE();

	LongCompositeData getRAW();

	LongCompositeData getFRAG();

	LongCompositeData getTCP();

	LongCompositeData getsockets();
}
