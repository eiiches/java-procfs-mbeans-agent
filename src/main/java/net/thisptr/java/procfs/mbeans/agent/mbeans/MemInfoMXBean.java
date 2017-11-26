package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.io.IOException;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;

/**
 *
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/meminfo.c#L46">https://github.com/torvalds/linux/blob/v4.13/fs/proc/meminfo.c#L46</a>
 */
public interface MemInfoMXBean {
	public LongCompositeData get$() throws IOException;
}
