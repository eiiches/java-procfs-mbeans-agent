package net.thisptr.java.procfs.mbeans.agent.mbeans.net;

import java.time.Duration;
import java.util.Map;

import net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc.SnmpStyleReader;
import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.SingleCache;

/**
 * @see <a href="https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L460">https://github.com/torvalds/linux/blob/v4.13/net/ipv4/proc.c#L460</a>
 */
public class Snmp implements SnmpMXBean {
	private static SingleCache<Map<String, LongCompositeData>> CACHE = new SingleCache<>(Duration.ofSeconds(1), () -> SnmpStyleReader.readFile("/proc/net/snmp"));

	@Override
	public LongCompositeData getIp() {
		return CACHE.get().get("Ip");
	}

	@Override
	public LongCompositeData getIcmp() {
		return CACHE.get().get("Icmp");
	}

	@Override
	public LongCompositeData getIcmpMsg() {
		return CACHE.get().get("IcmpMsg");
	}

	@Override
	public LongCompositeData getTcp() {
		return CACHE.get().get("Tcp");
	}

	@Override
	public LongCompositeData getUdp() {
		return CACHE.get().get("Udp");
	}

	@Override
	public LongCompositeData getUdpLite() {
		return CACHE.get().get("UdpLite");
	}
}
