package com.nissan.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class UtilitiesDriverclass {

	public static void main(String[] args) {
		
		
		TestNG testng = new TestNG();
		String userDir = System.getProperty("user.dir");
		List<String> suites = new ArrayList<String>();
		//String xmlName = args[0];
		//String xmlPath = String.format("C:\\Users\\ab00789853\\eclipse-workspace\\stat\\%s.xml", xmlName);
		String xmlPath = userDir+"\\resources\\testngXMLs\\createRO.xml";
		suites.add(xmlPath);
		testng.setTestSuites(suites);
		testng.run();

	}

}
