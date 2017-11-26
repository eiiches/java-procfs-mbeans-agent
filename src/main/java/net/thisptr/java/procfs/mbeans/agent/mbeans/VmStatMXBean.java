package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.io.IOException;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

public interface VmStatMXBean {
	LongCompositeData get$() throws IOException;
}
