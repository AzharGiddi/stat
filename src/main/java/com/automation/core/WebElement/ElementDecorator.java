package com.automation.core.WebElement;


import java.lang.reflect.*;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/* WrappedElementDecorator recognizes a few things that DefaultFieldDecorator does not. */
public class ElementDecorator implements FieldDecorator {
    /* factory to use when generating ElementLocator. */
    private ElementLocatorFactory factory;
    private static String fieldName;

    public static String getFieldName() {
		return fieldName;
	}

	public static void setFieldName(String fieldName) {
		ElementDecorator.fieldName = fieldName;
	}

	/* Constructor for an ElementLocatorFactory. */
    public ElementDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
    	setFieldName(field.getName());
    	
    	if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
            return null;
        }
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        Class<?> fieldType = field.getType();
        if (WebElement.class.equals(fieldType)) {
            fieldType = ExtWebElement.class;
        }
        
      //  fieldType = ExtWebElement.class;

        if (WebElement.class.isAssignableFrom(fieldType)) {
            return proxyForLocator(loader, fieldType, locator,field.getName());
        } else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field);
            return proxyForListLocator(loader, erasureClass, locator);
        } else {
            return null;
        }
    }

    private Class getErasureClass(Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }
        Class erasureClass = getErasureClass(field);
        if (erasureClass == null || !WebElement.class.isAssignableFrom(erasureClass)) {
            return false;
        }
        if (field.getAnnotation(FindBy.class) == null && field.getAnnotation(FindBys.class) == null) {
            return false;
        }
        return true;
    }

    /* Generate a type-parameterized locator proxy for the element in question. */
    protected <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        InvocationHandler handler = new ElementHandler(interfaceType, locator);

        T proxy;
        proxy = interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
        return proxy;
    }
    
    /* Generate a type-parameterized locator proxy for the element in question. */
    protected <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator, String fieldName) {
        InvocationHandler handler = new ElementHandler(interfaceType, locator,fieldName);

        T proxy;
        proxy = interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
        return proxy;
    }

    /* generates a proxy for a list of elements to be wrapped. */
    @SuppressWarnings("unchecked")
    protected <T> List<T> proxyForListLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        InvocationHandler handler = new ElementListHandler(interfaceType, locator);

        List<T> proxy;
        proxy = (List<T>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }
    
   
	
}
