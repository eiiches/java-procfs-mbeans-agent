package net.thisptr.java.procfs.mbeans.agent.misc;

import java.time.Duration;
import java.util.function.Supplier;

public class SingleCache<T> implements Supplier<T> {
	private final Supplier<T> supplier;
	private final Duration timeToLive;

	private Data<T> data;

	private static class Data<T> {
		public final long expireAt;
		public final T data;

		public Data(final T data, final long expireAt) {
			this.data = data;
			this.expireAt = expireAt;
		}
	}

	public SingleCache(final Duration timeToLive, final Supplier<T> supplier) {
		this.timeToLive = timeToLive;
		this.supplier = supplier;
	}

	@Override
	public T get() {
		final long now = System.currentTimeMillis();
		synchronized (this) {
			if (data != null && now < data.expireAt)
				return data.data;
			data = new Data<>(supplier.get(), now + timeToLive.toMillis());
			return data.data;
		}
	}

	public void clear() {
		synchronized (this) {
			data = null;
		}
	}
}