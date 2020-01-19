java-procfs-mbeans-agent
========================

Java agent to make /proc metrics available as MXBeans, which is mainly intended for use with [java-prometheus-metrics-agent](https://github.com/eiiches/java-prometheus-metrics-agent) to send /proc metrics to Prometheus.

**WARNING** (2017-11-27) I just started this project and should not be considered production-ready yet. Also, I'm developing this on linux 4.13 so might not work well on older kernels for now.

Project status
--------------

As of now (2020-01-12), I consider this project a failure due to the following reasons:

* Extra layers in observability (/proc -> MBeans -> Prometheus format) makes it harder to reach and dig into kernel code that produces the metric. Moreover, this wastes CPU and other resources.
* JVM has GC. If JVM stops due to long GC or something, metrics become unavailable, which is not good. We then have to investigate why they are missing.
* Kubernetes now has sharedProcessNamespaces. When enabled, process-specific (/proc/self/io, etc.) and namespaced metrics are accessible from other containers in a Pod. It's better to add an exporter sidecar that scrapes /proc/PID/io, etc.
* This agent cannot be used for non-JVM apps. I'm sure using a separate tool for non-JVM apps will be a headache because of different metric names, etc.

Once I find a better alternative for my use (or build one myself), this repository will be archived.

Installation
------------

#### Building from source

```sh
git clone https://github.com/eiiches/java-procfs-mbeans-agent.git
cd java-procfs-mbeans-agent
mvn clean package
```

Then, copy `target/java-procfs-mbeans-agent-{version}.jar` to your desired location.

#### Downloading from Maven Central

```sh
curl -O 'https://repo1.maven.org/maven2/net/thisptr/java-procfs-mbeans-agent/0.0.4/java-procfs-mbeans-agent-0.0.4.jar'
```

Usage
-----

```sh
java -javaagent:/path/to/java-procfs-mbeans-agent-$VERSION.jar ...
```

![visualvm](docs/visualvm.png)

Current Status
--------------

| path                         | status | requirements       |
|------------------------------|--------|--------------------|
| /proc/buddyinfo              | DONE   |                    |
| /proc/cpuinfo                |        |                    |
| /proc/diskstats              | DONE   |                    |
| /proc/interrupts             |        |                    |
| /proc/loadavg                | DONE   |                    |
| /proc/meminfo                | DONE   |                    |
| /proc/net/dev                |        |                    |
| /proc/net/netstat            | DONE   |                    |
| /proc/net/snmp6              | DONE   |                    |
| /proc/net/snmp               | DONE   |                    |
| /proc/net/sockstat6          | DONE   |                    |
| /proc/net/sockstat           | DONE   |                    |
| /proc/net/stat/arp_cache     |        |                    |
| /proc/net/stat/ndisc_cache   |        |                    |
| /proc/net/stat/nf_conntrack  |        |                    |
| /proc/net/stat/rt_cache      |        |                    |
| /proc/pagetypeinfo           |        |                    |
| /proc/schedstat              |        |                    |
| /proc/self/io                | DONE   |                    |
| /proc/self/stat              | DONE   |                    |
| /proc/self/statm             | DONE   |                    |
| /proc/self/sched             |        | CONFIG_SCHED_DEBUG |
| /proc/slabinfo               |        |                    |
| /proc/softirqs               | DONE   |                    |
| /proc/stat                   | DONE   |                    |
| /proc/swaps                  |        |                    |
| /proc/sys/fs/aio-nr          |        |                    |
| /proc/sys/fs/dentry-state    |        |                    |
| /proc/sys/fs/file-nr         |        |                    |
| /proc/sys/fs/inode-nr        |        |                    |
| /proc/sys/fs/inode-state     |        |                    |
| /proc/sys/kernel/pty/nr      |        |                    |
| /proc/sys/kernel/random/entropy_avail |        |           |
| /proc/uptime                 |        |                    |
| /proc/vmstat                 | DONE   |                    |
| /proc/zoneinfo               |        |                    |
| /sys/fs/cgroup/cpu           | DONE   |                    |
| /sys/fs/cgroup/cpuacct       | DONE   |                    |
| /sys/fs/cgroup/memory        | DONE   |                    |
| /sys/fs/cgroup/blkio         |        |                    |

License
-------

[The Apache License, Version 2.0](LICENSE)
