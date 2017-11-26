package net.thisptr.java.procfs.mbeans.agent;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import net.thisptr.java.procfs.mbeans.agent.mbeans.BuddyInfo;
import net.thisptr.java.procfs.mbeans.agent.mbeans.BuddyInfo.ZoneAndOrder;
import net.thisptr.java.procfs.mbeans.agent.mbeans.DiskStats;
import net.thisptr.java.procfs.mbeans.agent.mbeans.LoadAvg;
import net.thisptr.java.procfs.mbeans.agent.mbeans.MemInfo;
import net.thisptr.java.procfs.mbeans.agent.mbeans.SoftIrqs;
import net.thisptr.java.procfs.mbeans.agent.mbeans.Stat;
import net.thisptr.java.procfs.mbeans.agent.mbeans.Stat_Cpu;
import net.thisptr.java.procfs.mbeans.agent.mbeans.VmStat;
import net.thisptr.java.procfs.mbeans.agent.mbeans.net.NetStat;
import net.thisptr.java.procfs.mbeans.agent.mbeans.net.Snmp;
import net.thisptr.java.procfs.mbeans.agent.mbeans.net.Snmp6;
import net.thisptr.java.procfs.mbeans.agent.mbeans.net.SockStat;
import net.thisptr.java.procfs.mbeans.agent.mbeans.net.SockStat6;
import net.thisptr.java.procfs.mbeans.agent.mbeans.self.ProcessIo;
import net.thisptr.java.procfs.mbeans.agent.mbeans.self.ProcessStat;
import net.thisptr.java.procfs.mbeans.agent.mbeans.self.ProcessStatm;

public class AgentMain {
	private static final Logger LOG = LoggerFactory.getLogger(AgentMain.class);
	static {
		LoggerFactory.configure(Level.INFO, null); // default
	}

	public static void premain(final String args, final Instrumentation inst) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, IOException {
		final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		server.registerMBean(new NetStat(), new ObjectName("procfs", "path", "/proc/net/netstat"));
		server.registerMBean(new Snmp(), new ObjectName("procfs", "path", "/proc/net/snmp"));
		server.registerMBean(new Snmp6(), new ObjectName("procfs", "path", "/proc/net/snmp6"));
		server.registerMBean(new SockStat(), new ObjectName("procfs", "path", "/proc/net/sockstat"));
		server.registerMBean(new SockStat6(), new ObjectName("procfs", "path", "/proc/net/sockstat6"));
		server.registerMBean(new MemInfo(), new ObjectName("procfs", "path", "/proc/meminfo"));
		server.registerMBean(new VmStat(), new ObjectName("procfs", "path", "/proc/vmstat"));
		server.registerMBean(new LoadAvg(), new ObjectName("procfs", "path", "/proc/loadavg"));
		server.registerMBean(new Stat(), new ObjectName("procfs", "path", "/proc/stat"));
		server.registerMBean(new ProcessStatm(), new ObjectName("procfs", "path", "/proc/self/statm"));
		server.registerMBean(new ProcessStat(), new ObjectName("procfs", "path", "/proc/self/stat"));

		discoverOnce(server);
		discoverPeriodically(server);
		final Thread th = new Thread(AgentMain::discoveryLoop);
		th.setDaemon(true);
		th.setName("Procfs Discovery");
		th.start();

		LOG.info("Registered /procs MXBeans.");
	}

	private static void discoverDiskStats(final MBeanServer server) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, IOException {
		for (final String partitionName : DiskStats.listPartitionNames()) {
			final Hashtable<String, String> props = new Hashtable<>();
			props.put("path", "/proc/diskstats");
			props.put("name", partitionName);

			final ObjectName name = new ObjectName("procfs", props);
			if (!server.isRegistered(name))
				server.registerMBean(new DiskStats(partitionName), name);
		}
	}

	private static void discoverBuddyInfo(final MBeanServer server) throws IOException, MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		for (final ZoneAndOrder zoneAndOrder : BuddyInfo.listZoneAndOrders()) {
			final Hashtable<String, String> props = new Hashtable<>();
			props.put("node", String.valueOf(zoneAndOrder.node));
			props.put("zone", zoneAndOrder.zone);
			props.put("order", String.valueOf(zoneAndOrder.order));
			props.put("path", "/proc/buddyinfo");

			final ObjectName name = new ObjectName("procfs", props);
			if (!server.isRegistered(name))
				server.registerMBean(new BuddyInfo(zoneAndOrder), name);
		}
	}

	private static void discoverSelfIo(final MBeanServer server) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		if (!new File("/proc/self/io").exists())
			return;
		final ObjectName name = new ObjectName("procfs", "path", "/proc/self/io");
		if (!server.isRegistered(name))
			server.registerMBean(new ProcessIo(), name);
	}

	private static void discoverStat_Cpu(final MBeanServer server) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		for (final String cpu : Stat_Cpu.listCpus()) {
			final Hashtable<String, String> props = new Hashtable<>();
			props.put("cpu", cpu);
			props.put("path", "/proc/stat");
			final ObjectName name = new ObjectName("procfs", props);
			if (!server.isRegistered(name))
				server.registerMBean(new Stat_Cpu(cpu), name);
		}
	}

	private static void discoverSoftIrqs(final MBeanServer server) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
		for (final String cpu : SoftIrqs.listCpus()) {
			for (final String softirq : SoftIrqs.listSoftIrqs()) {
				final Hashtable<String, String> props = new Hashtable<>();
				props.put("cpu", cpu);
				props.put("softirq", softirq);
				props.put("path", "/proc/softirqs");
				final ObjectName name = new ObjectName("procfs", props);
				if (!server.isRegistered(name))
					server.registerMBean(new SoftIrqs(softirq, cpu), name);
			}
		}
	}

	private static void discoverOnce(final MBeanServer server) {
		try {
			discoverSelfIo(server);
		} catch (Throwable th) {
			th.printStackTrace();
		}

		try {
			discoverBuddyInfo(server);
		} catch (Throwable th) {
			th.printStackTrace();
		}

		try {
			discoverSoftIrqs(server);
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	private static void discoverPeriodically(final MBeanServer server) {
		try {
			discoverDiskStats(server);
			discoverStat_Cpu(server);
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	private static void discoveryLoop() {
		final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		while (true) {
			try {
				discoverPeriodically(server);
				Thread.sleep(300000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}