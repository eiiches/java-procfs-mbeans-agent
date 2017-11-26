package net.thisptr.java.procfs.mbeans.agent.mbeans;

import javax.management.MXBean;

/**
 * We omit per-IRQ interrupts here. There's /proc/softirq and /proc/interrupts for that.
 */
@MXBean
public interface StatMXBean {
	/**
	 * @return the number of total interrupts
	 */
	long getintr();

	/**
	 * @return the number of total soft IRQs
	 */
	long getsoftirq();

	long getprocs_blocked();

	long getprocs_running();

	/**
	 * @return the number of total forks
	 */
	long getprocesses();

	/**
	 * @return the number of context switches
	 */
	long getctxt();
}
