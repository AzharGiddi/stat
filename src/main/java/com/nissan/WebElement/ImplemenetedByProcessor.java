package com.nissan.WebElement;

public final class ImplemenetedByProcessor {

	
	
	private ImplemenetedByProcessor() {
		
	}
	
	public static <T> Class<?> getWrapperClass(Class<T> interfaceAnnotation){
		if(interfaceAnnotation.isAnnotationPresent(ImplementedBy.class)) {
			ImplementedBy annotation = interfaceAnnotation.getAnnotation(ImplementedBy.class);
			Class<?> clazz = annotation.value();
			if(ExtWebElement.class.isAssignableFrom(clazz)) {
				return annotation.value();
			}
			
		}
		
		throw new UnsupportedOperationException("Apply @ImplementedBy to your Interface "+interfaceAnnotation.getCanonicalName()+" if you want to extend ");
		
	}
}
