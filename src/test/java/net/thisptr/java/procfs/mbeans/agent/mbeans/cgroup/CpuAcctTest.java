package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

public class CpuAcctTest {

	@Test
	public void test() {
		final CpuAcct cpuacct = new CpuAcct();
		assertThatCode(() -> cpuacct.getStat()).doesNotThrowAnyException();
		assertThatCode(() -> cpuacct.getUsage()).doesNotThrowAnyException();
		assertThatCode(() -> cpuacct.getUsageSys()).doesNotThrowAnyException();
		assertThatCode(() -> cpuacct.getUsageUser()).doesNotThrowAnyException();
	}
}
