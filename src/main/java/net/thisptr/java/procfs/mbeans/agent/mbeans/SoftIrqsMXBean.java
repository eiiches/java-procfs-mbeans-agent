package net.thisptr.java.procfs.mbeans.agent.mbeans;

import javax.management.MXBean;

@MXBean
public interface SoftIrqsMXBean {
	long getsoftirqs();
}
