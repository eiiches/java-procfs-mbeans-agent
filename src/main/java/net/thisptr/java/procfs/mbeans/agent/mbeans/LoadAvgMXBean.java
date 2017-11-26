package net.thisptr.java.procfs.mbeans.agent.mbeans;

public interface LoadAvgMXBean {
	double getload1m();

	double getload5m();

	double getload15m();

	long getnr_running();

	long getnr_threads();
}
