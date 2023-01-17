package com.automation.core.WebElement;

import static com.automation.core.WebElement.ImplemenetedByProcessor.getWrapperClass;

import java.lang.reflect.*;
import java.util.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class ElementListHandler implements InvocationHandler {

	private final ElementLocator locator;
	private final Class<?> wrappingType;

	/*
	 * Generates a handler to retrieve the WebElement from a locator for a given
	 * WebElement interface descendant.
	 */
	public <T> ElementListHandler(Class<T> interfaceType, ElementLocator locator) {
		this.locator = locator;
		if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
			throw new RuntimeException("interface not assignable to Element.");
		}

		this.wrappingType = getWrapperClass(interfaceType);
	}

	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
		try {
			List<Object> wrappedList = new ArrayList<Object>();
			Constructor cons = wrappingType.getConstructor(WebElement.class);
			for (WebElement element : locator.findElements()) {
				Object thing = cons.newInstance(element);
				wrappedList.add(wrappingType.cast(thing));
			}

			return method.invoke(wrappedList, objects);
		} catch (InvocationTargetException e) {
			// Unwrap the underlying exception
			throw e.getCause();
		}
	}
}
