package com.automation.core.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

public class ElementFactory {

	public static <T> T initElements(WebDriver driver, Class<T> pageClassToProxy) {

		T page = instantiatePage(driver, pageClassToProxy);
		return initElements(driver, page);
	}

	public static <T> T initElements(SearchContext searchContext, T page) {
		initElements(new ElementDecorator(new DefaultElementLocatorFactory(searchContext)), page);
		return page;
	}

	public static void initElements(ElementLocatorFactory factory, Object page) {
		final ElementLocatorFactory factoryRef = factory;
		initElements(new ElementDecorator(factoryRef), page);
	}

	
	
	/**
	   * Similar to the other "initElements" methods, but takes an {@link FieldDecorator} which is used
	   * for decorating each of the fields.
	   *
	   * @param decorator the decorator to use
	   * @param page      The object to decorate the fields of
	   */
	  public static void initElements(FieldDecorator decorator, Object page) {
	    Class<?> proxyIn = page.getClass();
	    while (proxyIn != Object.class) {
	      proxyFields(decorator, page, proxyIn);
	      proxyIn = proxyIn.getSuperclass();
	    }
	  }
	  
	  private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
		    Field[] fields = proxyIn.getDeclaredFields();
		    for (Field field : fields) {
		      Object value = decorator.decorate(page.getClass().getClassLoader(), field);
		      if (value != null) {
		        try {
		          field.setAccessible(true);
		          field.set(page, value);
		        } catch (IllegalAccessException e) {
		          throw new RuntimeException(e);
		        }
		      }
		    }
		  }

	static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
