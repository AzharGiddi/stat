package com.nissan.tests;

import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.nissan.automation.core.utils.PoiExcelUtil;
import com.nissan.automation.core.utils.listeners.ConfigListener;
import com.nissan.utils.ROAPIUtil;

@Listeners(ConfigListener.class)
public class ROCreationUtilTestCase extends BaseTest {

	@Test
	public void createRO() {

		String filePath = "/resources/testdata/ROCreationTestDataMacro.xlsm";
		Map<String, Object> excelData = PoiExcelUtil.getExcelDataAsMapObject(filePath, "Ro_InputSheet");
		String dealerCode = String.valueOf(((Double) excelData.get("Dealer_Code")).intValue());
		String vin = String.valueOf(excelData.get("VIN"));
		String mileage = String.valueOf(excelData.get("Mileage"));
		String inWarranty = String.valueOf(excelData.get("Warranty"));
		ROAPIUtil.createRO(dealerCode, vin, mileage, inWarranty);

	}

}
