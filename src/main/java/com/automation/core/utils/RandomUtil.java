package com.automation.core.utils;

import com.github.javafaker.Faker;

public class RandomUtil {
	
	private static Faker faker = new Faker();
	
	/***
	 * 
	 * @param regExp regular expression along with size of the string
	 * @return
	 */
	public static String getRandomStringWithRegExp(String regExp) {
		
		
		return faker.regexify(regExp);
		
	}
	
public static int getRandomNumberBetween(int min,  int max) {
		
		
		return faker.number().numberBetween(min, max);
		
	}

public static int getRandomNumber() {
	
	
	return faker.number().randomDigitNotZero();
	
}

}
