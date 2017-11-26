package net.thisptr.java.procfs.mbeans.agent.mbeans;

import javax.management.MXBean;

@MXBean
public interface DiskStatsMXBean {
	ReadWrite getios();

	ReadWrite getmerges();

	ReadWrite getsectors();

	ReadWrite getticks();

	long getinflight();

	long getio_ticks();

	long gettime_in_queue();

	int getMAJOR();

	int getMINOR();

	public static class ReadWrite {
		private long READ;
		private long WRITE;

		public ReadWrite(final long READ, final long WRITE) {
			this.READ = READ;
			this.WRITE = WRITE;
		}

		public long getREAD() {
			return READ;
		}

		public long getWRITE() {
			return WRITE;
		}
	}
}
