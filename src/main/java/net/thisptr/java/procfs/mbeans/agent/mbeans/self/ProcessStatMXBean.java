package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import javax.management.MXBean;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/array.c#L580">https://github.com/torvalds/linux/blob/v4.13/fs/proc/array.c#L580</a>
 */
@MXBean
public interface ProcessStatMXBean {
	/**
	 * @return the number of threads in the process
	 */
	int getnum_threads();

	/**
	 * @return the number of bytes used by the process (= PAGE_SIZE * mm-&gt;total_vm).
	 */
	long getvsize();

	/**
	 * @return the number of pages the process has in real memory (= MM_FILEPAGES + MM_ANONPAGES + MM_SHMEMPAGES).
	 */
	long getrss();

	/**
	 * @return the number of minor faults caused by the process
	 */
	long getmin_flt();

	/**
	 * @return the number of major faults caused by the process
	 */
	long getmaj_flt();

	/**
	 * @return CPU time spent in user code, measured in clock ticks
	 */
	long getutime();

	/**
	 * @return CPU time spent in kernel code, measured in clock ticks
	 */
	long getstime();

	/**
	 * @return CPU time spent in a guest operating system, measured in clock ticks
	 */
	long getgtime();
}
