package com.photon.phresco.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class OrderedProperties extends Properties {

	private static final long serialVersionUID = 1L;
	public OrderedProperties() {}

	public OrderedProperties(Properties defaults) {
		super(defaults);
	}

	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	public Set<Object> keySet() {
		return keys;
	}

	public Enumeration<Object> keys() {
		return Collections.<Object> enumeration(keys);
	}

	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}

}