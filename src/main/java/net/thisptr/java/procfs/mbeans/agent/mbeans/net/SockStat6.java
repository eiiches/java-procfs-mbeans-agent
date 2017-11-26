package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import java.time.Duration;
import java.util.Map;

import net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc.SockStatStyleReader;
import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

public class SockStat6 implements SockStat6MXBean {
	private static SingleCache<Map<String, LongCompositeData>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> SockStatStyleReader.readFile("/proc/net/sockstat6"));

	@Override
	public LongCompositeData getUDP6() {
		return CACHE.get().get("UDP6");
	}

	@Override
	public LongCompositeData getUDPLITE6() {
		return CACHE.get().get("UDPLITE6");
	}

	@Override
	public LongCompositeData getRAW6() {
		return CACHE.get().get("RAW6");
	}

	@Override
	public LongCompositeData getFRAG6() {
		return CACHE.get().get("FRAG6");
	}

	@Override
	public LongCompositeData getTCP6() {
		return CACHE.get().get("TCP6");
	}
}
