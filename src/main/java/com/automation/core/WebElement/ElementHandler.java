package com.automation.core.WebElement;

import static com.automation.core.WebElement.ImplemenetedByProcessor.getWrapperClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import com.automation.core.reports.ExtentLogger;

public class ElementHandler implements InvocationHandler {
	private final ElementLocator locator;
	private final Class<?> wrappingType;
	private final String fieldName;
	private final By by;

	/*
	 * Generates a handler to retrieve the WebElement from a locator for a given
	 * WebElement interface descendant.
	 */
	public <T> ElementHandler(Class<T> interfaceType, ElementLocator locator) {
		this.locator = locator;
		this.fieldName="";
		this.by = getBy(locator);
		if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
			throw new RuntimeException("interface not assignable to Element.");
		}

		this.wrappingType = getWrapperClass(interfaceType);
	}
	
	public <T> ElementHandler(Class<T> interfaceType, ElementLocator locator, String fieldName) {
		this.locator = locator;
		this.fieldName=fieldName;
		this.by = getBy(locator);
		if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
			throw new RuntimeException("interface not assignable to Element.");
		}

		this.wrappingType = getWrapperClass(interfaceType);
	}

	/*@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
		WebElement element=null;
		try {
			
			element = locator.findElement();

			if ("getWrappedElement".equals(method.getName())) {
				return element;
			}
			Constructor<?> cons = wrappingType.getConstructor(WebElement.class);
			Object thing = cons.newInstance(element);

			return method.invoke(wrappingType.cast(thing), objects);
		} catch (NullPointerException | StaleElementReferenceException e) {
			return invoke(object, method, objects);

		}catch(NoSuchElementException e){
			
			Constructor<?> cons = wrappingType.getConstructor(WebElement.class);
			Object thing = cons.newInstance(element);

			return method.invoke(wrappingType.cast(thing), objects);
			
		}catch (Exception e) {
			//return invoke(object, method, objects);
			// Unwrap the underlying exception
			e.printStackTrace();
			ExtentLogger.fail(e);
			throw e.getCause();
			
		}
	}*/
	
	private By getBy(ElementLocator locator) {
		By by=null ;
		Field field=null;
		try {
			field = locator.getClass().getDeclaredField("by");
			field.setAccessible(true);
			Object value=field.get(locator);
			by	= (By) value;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return by;
	}
	
	@Override
	public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
		
		
		try {
			if ("getWrappedElement".equals(method.getName())) {
				return locator.findElement();
			}
			Constructor<?> cons = wrappingType.getConstructor(By.class,String.class);
			Object thing = cons.newInstance(by,fieldName);

			return method.invoke(wrappingType.cast(thing), objects);
		} catch (NullPointerException | StaleElementReferenceException e) {
			return invoke(object, method, objects);

		}catch(Exception e){
			
			Constructor<?> cons = wrappingType.getConstructor(By.class,String.class);
			Object thing = cons.newInstance(null,fieldName);

			return method.invoke(wrappingType.cast(thing), objects);
			
		}/*catch (Exception e) {
			//return invoke(object, method, objects);
			// Unwrap the underlying exception
			e.printStackTrace();
			ExtentLogger.fail(e);
			throw e.getCause();
			
		}*/
	}

	/*
	 * @Override public Object invoke(Object object, Method method, Object[]
	 * objects) throws Throwable {
	 * 
	 * try { //WebElement element = locator.findElement(); WebElement element=null;
	 * try { element = locator.findElement(); }catch(NoSuchElementException e) {
	 * e.printStackTrace(); } if ("getWrappedElement".equals(method.getName())) {
	 * return element; } Constructor<?> cons =
	 * wrappingType.getConstructor(WebElement.class); Object thing =
	 * cons.newInstance(element);
	 * 
	 * return method.invoke(wrappingType.cast(thing), objects); } catch (Exception
	 * e) { // Unwrap the underlying exception e.printStackTrace();
	 * ExtentLogger.fail(e); throw e.getCause(); } }
	 */
}
