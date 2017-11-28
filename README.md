java-procfs-mbeans-agent
========================

Java agent to make /proc metrics available as MXBeans, which is mainly intended for use with [java-influxdb-metrics-agent](https://github.com/eiiches/java-influxdb-metrics-agent) to send /proc metrics to InfluxDB.

**WARNING** (2017-11-27) I just started this project and should not be considered production-ready yet. Also, I'm developing this on linux 4.13 so might not work well on older kernels for now.

Usage
-----

```sh
java -javaagent:/path/to/java-procfs-mbeans-agent-$VERSION.jar [-javaagent:/path/to/java-influxdb-metrics-agent-0.0.5.jar=/procfs/{namekeys=path},...] ...
```

![visualvm](docs/visualvm.png)

Current Status
--------------

| path                         | status |
|------------------------------|--------|
| /proc/buddyinfo              | DONE   |
| /proc/cpuinfo                |        |
| /proc/diskstats              | DONE   |
| /proc/interrupts             |        |
| /proc/loadavg                | DONE   |
| /proc/meminfo                | DONE   |
| /proc/net/netstat            | DONE   |
| /proc/net/snmp6              | DONE   |
| /proc/net/snmp               | DONE   |
| /proc/net/sockstat6          | DONE   |
| /proc/net/sockstat           | DONE   |
| /proc/pagetypeinfo           |        |
| /proc/schedstat              |        |
| /proc/self/io                | DONE   |
| /proc/self/stat              | DONE   |
| /proc/self/statm             | DONE   |
| /proc/softirqs               | DONE   |
| /proc/stat                   | DONE   |
| /proc/swaps                  |        |
| /proc/vmstat                 | DONE   |
| /proc/zoneinfo               |        |
| /proc/net/dev                |        |

License
-------

[The Apache License, Version 2.0](LICENSE)
