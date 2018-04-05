package net.thisptr.java.procfs.mbeans.agent.mbeans.net.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import net.thisptr.java.procfs.mbeans.agent.LoggerFactory;

import net.thisptr.java.procfs.mbeans.agent.misc.LongCompositeData;
import net.thisptr.java.procfs.mbeans.agent.misc.MoreFiles;

/**
 * <p>
 * Parses SNMP-style text, which appears in /proc/net/snmp, /proc/net/netstat, etc.
 * </p>
 *
 * e.g.)
 * 
 * <pre>
 * Ip: Forwarding DefaultTTL InReceives InHdrErrors InAddrErrors ForwDatagrams InUnknownProtos InDiscards InDelivers OutRequests OutDiscards OutNoRoutes ReasmTimeout ReasmReqds ReasmOKs ReasmFails FragOKs FragFails FragCreates
 * Ip: 1 64 6513119 0 0 117 0 0 6512562 6033818 1537291 6297 0 0 0 0 0 4 0
 * Icmp: InMsgs InErrors InCsumErrors InDestUnreachs InTimeExcds InParmProbs InSrcQuenchs InRedirects InEchos InEchoReps InTimestamps InTimestampReps InAddrMasks InAddrMaskReps OutMsgs OutErrors OutDestUnreachs OutTimeExcds OutParmProbs OutSrcQuenchs OutRedirects OutEchos OutEchoReps OutTimestamps OutTimestampReps OutAddrMasks OutAddrMaskReps
 * Icmp: 26100 12318 1 25552 0 0 0 0 0 547 0 0 0 0 27828 0 27258 0 0 0 0 570 0 0 0 0 0
 * </pre>
 *
 */
public class SnmpStyleReader {
	private static final Logger LOG = LoggerFactory.getLogger(SnmpStyleReader.class);

	public static Map<String, LongCompositeData> readFile(final String path) {
		final Map<String, List<String>> categories = new HashMap<>();
		for (final String line : MoreFiles.readLines(path)) {
			final String[] parts = line.split(": ", 2);
			if (parts.length != 2)
				throw new IllegalStateException("Unparseable line in " + path);
			final String category = parts[0];
			categories.computeIfAbsent(category, __ -> new ArrayList<>()).add(parts[1]);
		}

		final Map<String, LongCompositeData> results = new HashMap<>();
		categories.forEach((category, lines) -> {
			if (lines.size() != 2) {
				LOG.warn("{}: failed to parse line", path);
				return;
			}

			final String[] columns = lines.get(0).split(" ");
			final String[] values = lines.get(1).split(" ");

			if (columns.length != values.length) {
				LOG.warn("{}: failed to parse line", path);
				return;
			}

			final Map<String, Long> fields = new HashMap<>();
			for (int i = 0; i < columns.length; ++i)
				fields.put(columns[i], Long.valueOf(values[i]));
			results.put(category, new LongCompositeData(fields, path));
		});
		return results;
	}
}
