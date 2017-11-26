package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import java.time.Duration;
import java.util.Map;

import net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc.SnmpStyleReader;
import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L490">https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L490</a>
 */
public class NetStat implements NetStatMXBean {
	private static SingleCache<Map<String, LongCompositeData>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> SnmpStyleReader.readFile("/proc/net/netstat"));

	@Override
	public LongCompositeData getIpExt() {
		return CACHE.get().get("IpExt");
	}

	@Override
	public LongCompositeData getTcpExt() {
		return CACHE.get().get("TcpExt");
	}
}
