package net.thisptr.java.procfs.mbeans.agent.mbeans;

import javax.management.MXBean;

@MXBean
public interface Stat_CpuMXBean {
	long getcpu_user();

	long getcpu_nice();

	long getcpu_system();

	long getcpu_idle();

	long getcpu_iowait();

	long getcpu_irq();

	long getcpu_softirq();

	long getcpu_steal();

	long getcpu_guest();

	long getcpu_guest_nice();
}
