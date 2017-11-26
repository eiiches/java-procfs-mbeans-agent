package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/block/genhd.c#L1201">https://github.com/torvalds/linux/blob/v4.13/block/genhd.c#L1201</a>
 */
public class DiskStats implements DiskStatsMXBean {
	private static int NAME_COLUMN_INDEX = 2;

	private static SingleCache<Map<String, Data>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final Map<String, Data> disks = new HashMap<>();

		for (final String line : MoreFiles.readLines("/proc/diskstats")) {
			final String[] tokens = line.trim().split(" +");
			final String diskName = tokens[NAME_COLUMN_INDEX];

			final long[] values = new long[tokens.length];
			for (int i = 0; i < tokens.length; ++i) {
				if (i == NAME_COLUMN_INDEX)
					continue;
				values[i] = Long.parseLong(tokens[i]);
			}

			disks.put(diskName, new Data(values));
		}

		return disks;
	});

	private String diskName;

	public DiskStats(final String name) {
		this.diskName = name;
	}

	private static class Data {
		private long[] values;

		public Data(final long[] values) {
			this.values = values;
		}

		public ReadWrite getios() {
			return new ReadWrite(values[3], values[7]);
		}

		public ReadWrite getmerges() {
			return new ReadWrite(values[4], values[8]);
		}

		public ReadWrite getsectors() {
			return new ReadWrite(values[5], values[9]);
		}

		public ReadWrite getticks() {
			return new ReadWrite(values[6], values[10]);
		}

		public long getinflight() {
			return values[11];
		}

		public long getio_ticks() {
			return values[12];
		}

		public long gettime_in_queue() {
			return values[13];
		}

		public int getMAJOR() {
			return (int) values[0];
		}

		public int getMINOR() {
			return (int) values[1];
		}
	}

	public static Collection<String> listPartitionNames() {
		return CACHE.get().keySet();
	}

	@Override
	public ReadWrite getios() {
		return CACHE.get().get(diskName).getios();
	}

	@Override
	public ReadWrite getmerges() {
		return CACHE.get().get(diskName).getmerges();
	}

	@Override
	public ReadWrite getsectors() {
		return CACHE.get().get(diskName).getsectors();
	}

	@Override
	public ReadWrite getticks() {
		return CACHE.get().get(diskName).getticks();
	}

	@Override
	public long getinflight() {
		return CACHE.get().get(diskName).getinflight();
	}

	@Override
	public long getio_ticks() {
		return CACHE.get().get(diskName).getio_ticks();
	}

	@Override
	public long gettime_in_queue() {
		return CACHE.get().get(diskName).gettime_in_queue();
	}

	@Override
	public int getMAJOR() {
		return CACHE.get().get(diskName).getMAJOR();
	}

	@Override
	public int getMINOR() {
		return CACHE.get().get(diskName).getMINOR();
	}
}
