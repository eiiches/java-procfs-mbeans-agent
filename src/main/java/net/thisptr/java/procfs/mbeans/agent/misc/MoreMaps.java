package net.thisptr.java.procfs.mbeans.agent.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MoreMaps {
	public static <K, V, U> Map<K, U> mapValues(final Map<K, V> m, final Function<V, U> fn) {
		final Map<K, U> r = new HashMap<>((int) Math.ceil(m.size() / 0.75f), 0.75f);
		m.forEach((k, v) -> {
			r.put(k, fn.apply(v));
		});
		return r;
	}
}
