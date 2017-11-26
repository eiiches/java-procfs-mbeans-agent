package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import java.time.Duration;
import java.util.Map;

import net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc.SockStatStyleReader;
import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L54">https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L54</a>
 */
public class SockStat implements SockStatMXBean {
	private static SingleCache<Map<String, LongCompositeData>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> SockStatStyleReader.readFile("/proc/net/sockstat"));

	@Override
	public LongCompositeData getUDP() {
		return CACHE.get().get("UDP");
	}

	@Override
	public LongCompositeData getUDPLITE() {
		return CACHE.get().get("UDPLITE");
	}

	@Override
	public LongCompositeData getRAW() {
		return CACHE.get().get("RAW");
	}

	@Override
	public LongCompositeData getFRAG() {
		return CACHE.get().get("FRAG");
	}

	@Override
	public LongCompositeData getTCP() {
		return CACHE.get().get("TCP");
	}

	@Override
	public LongCompositeData getsockets() {
		return CACHE.get().get("sockets");
	}
}
