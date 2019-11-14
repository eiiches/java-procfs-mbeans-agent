package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

public class MemoryTest {

	@Test
	public void test() {
		final Memory memory = new Memory();
		assertThatCode(() -> memory.getStat()).doesNotThrowAnyException();

		assertThatCode(() -> memory.getFailcnt()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getLimitInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getMaxUsageInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getSoftLimitInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getUsageInBytes()).doesNotThrowAnyException();

		assertThatCode(() -> memory.getKmemFailcnt()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemLimitInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemMaxUsageInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemUsageInBytes()).doesNotThrowAnyException();

		assertThatCode(() -> memory.getKmemTcpFailcnt()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemTcpLimitInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemTcpMaxUsageInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getKmemTcpUsageInBytes()).doesNotThrowAnyException();

		assertThatCode(() -> memory.getMemswFailcnt()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getMemswLimitInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getMemswMaxUsageInBytes()).doesNotThrowAnyException();
		assertThatCode(() -> memory.getMemswUsageInBytes()).doesNotThrowAnyException();
	}
}
