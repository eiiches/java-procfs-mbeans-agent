package net.thisptr.java.procfs.mbeans.agent.mbeans;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/mm/vmstat.c#L1168">https://github.com/torvalds/linux/blob/v4.13/mm/vmstat.c#L1168</a>
 */
public class BuddyInfo implements BuddyInfoMXBean {
	private static final Logger LOG = LoggerFactory.getLogger(BuddyInfo.class);

	private static final SingleCache<Map<ZoneAndOrder, Long>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> {
		final Map<ZoneAndOrder, Long> pages = new HashMap<>();

		for (final String rawLine : MoreFiles.readLines("/proc/buddyinfo")) {
			final String[] nodeAndRest = rawLine.split(",", 2);

			final String[] nodeName = nodeAndRest[0].split(" ", 2);
			if (nodeName.length != 2) {
				LOG.warn("/proc/buddyinfo: failed to parse node name");
				continue;
			}

			final int node = Integer.parseInt(nodeName[1]);

			final String[] zoneAndFreePages = nodeAndRest[1].trim().split(" +");
			if (!zoneAndFreePages[0].equals("zone")) {
				LOG.warn("/proc/buddyinfo: failed to parse zone name");
				continue;
			}

			final String zone = zoneAndFreePages[1];

			for (int i = 2; i < zoneAndFreePages.length; ++i) {
				final int order = i - 2;
				pages.put(new ZoneAndOrder(node, zone, order), Long.valueOf(zoneAndFreePages[i]));
			}
		}

		return pages;
	});

	private final ZoneAndOrder zoneAndOrder;

	public BuddyInfo(final ZoneAndOrder zoneAndOrder) {
		this.zoneAndOrder = zoneAndOrder;
	}

	@Override
	public long getnr_free() throws IOException {
		final Long pages = CACHE.get().get(zoneAndOrder);
		if (pages == null)
			return -1;
		return pages;
	}

	public static Collection<ZoneAndOrder> listZoneAndOrders() throws IOException {
		return CACHE.get().keySet();
	}

	public static class ZoneAndOrder {
		public final int node;
		public final String zone;
		public final int order;

		public ZoneAndOrder(final int node, final String zone, final int order) {
			this.node = node;
			this.zone = zone;
			this.order = order;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + node;
			result = prime * result + order;
			result = prime * result + ((zone == null) ? 0 : zone.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ZoneAndOrder other = (ZoneAndOrder) obj;
			if (node != other.node)
				return false;
			if (order != other.order)
				return false;
			if (zone == null) {
				if (other.zone != null)
					return false;
			} else if (!zone.equals(other.zone))
				return false;
			return true;
		}
	}
}
