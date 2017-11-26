package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import javax.management.MXBean;

@MXBean
public interface ProcessIoMXBean {
	/**
	 * @return bytes read
	 */
	long getrchar();

	/**
	 * @return bytes written
	 */
	long getwchar();

	/**
	 * @return # of read syscalls
	 */
	long getsyscr();

	/**
	 * @return # of write syscalls
	 */
	long getsyscw();

	/**
	 * @return the number of bytes which this task has caused to be read from storage
	 */
	long getread_bytes();

	/**
	 * @return the number of bytes which this task has caused, or shall cause to be written to disk
	 */
	long getwrite_bytes();

	/**
	 * @return the number of bytes which this task has cancelled by truncating some dirty page caches
	 */
	long getcancelled_write_bytes();
}
