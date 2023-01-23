package com.fields.strategies;

import java.lang.annotation.Annotation;
import java.util.List;

import com.automation.core.utils.RandomUtil;

import uk.co.jemos.podam.common.AttributeStrategy;

public class NameStrategy implements AttributeStrategy<String>{

	@Override
	public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		// TODO Auto-generated method stub
		return RandomUtil.getRandomStringWithRegExp("[a-zA-Z]{1}[a-zA-Z0-9-_]{19}");
	}

	
	
}
