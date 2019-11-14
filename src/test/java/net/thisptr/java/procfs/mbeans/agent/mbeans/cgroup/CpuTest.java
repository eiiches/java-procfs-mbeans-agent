package net.thisptr.java.procfs.mbeans.agent.mbeans.cgroup;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

public class CpuTest {

	@Test
	public void test() {
		final Cpu cpu = new Cpu();
		assertThatCode(() -> cpu.getStat()).doesNotThrowAnyException();
		assertThatCode(() -> cpu.getCfsPeriodUs()).doesNotThrowAnyException();
		assertThatCode(() -> cpu.getCfsQuotaUs()).doesNotThrowAnyException();
		assertThatCode(() -> cpu.getShares()).doesNotThrowAnyException();
	}
}
