package com.automation.core.configuration;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.automation.core.utils.PropertyUtil;
/**
 * This class provides Threadsafe PropertyUtil Instance
 */
public final class PropertyManager {

	private static ThreadLocal<PropertyUtil> dr = new ThreadLocal<>();
	
	public static PropertyUtil getInstance() {
		return dr.get();
	}

	public static void setInstance(PropertyUtil propUtil) {
		dr.set(propUtil);
	}

	public static void unload() {
		dr.remove();
	}
	
	private PropertyManager() {
		
	}

}
