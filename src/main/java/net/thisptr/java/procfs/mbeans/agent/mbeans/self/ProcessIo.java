package net.thisptr.java.procfs.mbeans.agent.mbeans.self;

import java.time.Duration;

import org.slf4j.Logger;
import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * Available when the kernel has CONFIG_TASK_IO_ACCOUNTING.
 * 
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/base.c#L2727">https://github.com/torvalds/linux/blob/v4.13/fs/proc/base.c#L2727</a>
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/fs/proc/base.c#L2675">https://github.com/torvalds/linux/blob/v4.13/fs/proc/base.c#L2675</a>
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/include/linux/task_io_accounting.h">https://github.com/torvalds/linux/blob/v4.13/include/linux/task_io_accounting.h</a>
 */
public class ProcessIo implements ProcessIoMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessIo.class);

	private static final SingleCache<TaskIoAccounting> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final TaskIoAccounting acct = new TaskIoAccounting();
		for (final String line : MoreFiles.readLines("/proc/self/io")) {
			final String[] kv = line.split(": +");
			if (kv.length != 2) {
				LOG.warn("/proc/self/io: failed to parse line: {}", line);
				continue;
			}
			final long value = Long.parseLong(kv[1]);
			switch (kv[0]) {
				case "rchar":
					acct.rchar = value;
					break;
				case "wchar":
					acct.wchar = value;
					break;
				case "syscr":
					acct.syscr = value;
					break;
				case "syscw":
					acct.syscw = value;
					break;
				case "read_bytes":
					acct.read_bytes = value;
					break;
				case "write_bytes":
					acct.write_bytes = value;
					break;
				case "cancelled_write_bytes":
					acct.cancelled_write_bytes = value;
					break;
				default:
					LOG.warn("/proc/self/io: unsupported key: {}", kv[0]);
			}
		}
		return acct;
	});

	private static class TaskIoAccounting {
		// #ifdef CONFIG_TASK_XACCT
		public long rchar = -1;
		public long wchar = -1;
		public long syscr = -1;
		public long syscw = -1;
		// #endif /* CONFIG_TASK_XACCT */

		// #ifdef CONFIG_TASK_IO_ACCOUNTING
		public long read_bytes = -1;
		public long write_bytes = -1;
		public long cancelled_write_bytes = -1;
		// #endif /* CONFIG_TASK_IO_ACCOUNTING */
	}

	@Override
	public long getrchar() {
		return CACHE.get().rchar;
	}

	@Override
	public long getwchar() {
		return CACHE.get().wchar;
	}

	@Override
	public long getsyscr() {
		return CACHE.get().syscr;
	}

	@Override
	public long getsyscw() {
		return CACHE.get().syscw;
	}

	@Override
	public long getread_bytes() {
		return CACHE.get().read_bytes;
	}

	@Override
	public long getwrite_bytes() {
		return CACHE.get().write_bytes;
	}

	@Override
	public long getcancelled_write_bytes() {
		return CACHE.get().cancelled_write_bytes;
	}
}
