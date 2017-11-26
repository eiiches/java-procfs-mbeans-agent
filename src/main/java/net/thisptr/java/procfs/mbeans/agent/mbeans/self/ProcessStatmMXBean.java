package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import javax.management.MXBean;

@MXBean
public interface ProcessStatmMXBean {
	/**
	 * @return the number of total pages mapped (= mm-&gt;total_vm).
	 */
	long getsize();

	/**
	 * @return the number of resident pages (= shared + mm-&gt;rss_stat.count[MM_ANONPAGES]).
	 */
	long getresident();

	/**
	 * @return the number of shared pages (= mm-&gt;rss_stat.count[MM_FILEPAGES] + mm-&gt;rss_stat.count[MM_SHMEMPAGES]).
	 */
	long getshared();

	long gettext();

	/**
	 * @return mm-&gt;data_vm + mm-&gt;stack_vm
	 */
	long getdata();
}
