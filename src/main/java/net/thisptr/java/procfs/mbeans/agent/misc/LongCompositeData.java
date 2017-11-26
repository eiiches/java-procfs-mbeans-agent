package net.thisptr.java.procfs.mbeans.agent.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class LongCompositeData implements CompositeDataView {
	private final Map<String, Long> values;
	private final long timestamp;

	public LongCompositeData(final Map<String, Long> values) {
		this.values = values;
		this.timestamp = System.currentTimeMillis();
	}

	public long get$timestamp() {
		return timestamp;
	}

	@Override
	public CompositeData toCompositeData(CompositeType ct) {
		final List<String> names = new ArrayList<>();
		final List<String> descs = new ArrayList<>();
		final List<OpenType<?>> types = new ArrayList<>();

		values.forEach((name, value) -> {
			names.add(name);
			descs.add(name);
			types.add(SimpleType.LONG);
		});

		try {
			final CompositeType xct = new CompositeType(this.getClass().getName(), "foo",
					names.toArray(new String[names.size()]),
					descs.toArray(new String[descs.size()]),
					types.toArray(new OpenType[types.size()]));
			return new CompositeDataSupport(xct, values);
		} catch (OpenDataException e) {
			e.printStackTrace();
			return null;
		}
	}
}