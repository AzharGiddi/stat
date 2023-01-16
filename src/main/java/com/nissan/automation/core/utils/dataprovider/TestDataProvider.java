package com.nissan.automation.core.utils.dataprovider;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.nissan.automation.core.utils.PoiExcelUtil;

public class TestDataProvider {

	/**
	 * To use this DataProvider, please pass Excel file Path as "file" and sheetname
	 * as "sheetName" in test parameters. Example below: <br>
	 * @Test(parameters={file="test.xlsx",sheetName="InputSheet"}) </br>
	 * 
	 * @param iTest
	 * @return
	 */

	@DataProvider(name = "GetExcelData")
	public static Object[][] getTestData(ITestContext iTest) {
		String file = "/resources/testdata/TestDataFile.xlsx";
		String sheetName = "";

		return PoiExcelUtil.getExcelDataAsMap(file, sheetName);

	}
	
	@DataProvider(name = "GetExcelDataWithTestCaseID")
	public static Object[][] getTestData(Method m) {
		String file = "/resources/testdata/TestDataFile.xlsx";
		String sheetName = "";
		String testCaseID = m.getName();
		return PoiExcelUtil.getExcelDataAsMap(file, sheetName,testCaseID);

	}

}
