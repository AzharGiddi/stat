package com.utils;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.automation.core.utils.RandomUtil;
import com.fields.strategies.NameStrategy;
import com.github.javafaker.Faker;

import lombok.Builder;
import lombok.Data;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Builder
public class FormTag {
	
	@PodamStrategyValue(value=NameStrategy.class)
	private static String formTagName;

	
	  public static String getRequestBody() {
	  
	  JSONObject json = new JSONObject(); 
	  json.put("formtagname", formTagName);
	  
	  return json.toString(); }
	  
	  public static String getRequestBody(String regEx) {
	  
	  JSONObject json = new JSONObject(); 
	  json.put("formtagname",
	  RandomUtil.getRandomStringWithRegExp(regEx));
	  
	  return json.toString(); }
	 
	
	
	
	
	
	
	
	
	@Test
	public void test() {
		PodamFactory podam = new PodamFactoryImpl();
		FormTag tag = podam.manufacturePojo(FormTag.class);
		System.out.println(tag.formTagName);
		//Faker faker = new Faker();
		//System.out.println(faker.regexify("[a-zA-Z]{1}[a-zA-Z0-9-_]{19}"));

	}
	
}
