package com.nissan.automation.core;

import static com.nissan.configuration.ConfigurationManager.getBundle;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.jexl3.JexlContext;

import com.nissan.automation.core.utils.JSONUtil;

public class NAFJexlContext implements JexlContext {

	private Map<String, Object> _ctx;

	public NAFJexlContext() {
		_ctx = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
		set("_ctx", this);
	}

	/**
	 * Constructs a <code>QAFJexlContext</code> that wraps the existing
	 * <code>Map</code> of bean names to bean values.
	 * 
	 * @param beans A <code>Map</code> of bean names to bean values.
	 */
	public NAFJexlContext(Map<? extends String, ? extends Object> beans) {
		this();
		if (null != beans) {
			_ctx.putAll(beans);
		}
	}

	@Override
	public boolean has(String name) {
		return _ctx.containsKey(name) || getClass(name) != null || getBundle().containsKey(name);
	}

	@Override
	public Object get(String name) {
		Object value = _ctx.get(name);
		// Check for a legitimate null value for a variable name before
		// attempting to resolve a class name.
		if (value == null && !_ctx.containsKey(name) && (value = getObject(name)) == null) {
			value = getClass(name);
		}
		return value;
	}

	@Override
	public void set(String name, Object value) {
		_ctx.put(name, value);
	}

	/**
	 * Clears all variables.
	 */
	public void clear() {
		_ctx.clear();
	}

	private Class<?> getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	private Object getObject(String name) {
		Object value = getBundle().getProperty(name);
		if (value instanceof String) {
			String res = getBundle().getSubstitutor().replace((String) value);
			value = JSONUtil.toObject(res);
		}
		return value;
	}
}
