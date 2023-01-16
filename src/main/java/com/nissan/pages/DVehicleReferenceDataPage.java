package com.nissan.pages;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.utils.DVehicleReferenceDetailsUtil;

public class DVehicleReferenceDataPage extends BasePage {

	@FindBy(xpath = "//span[text()='Vehicle Reference']")
	public ExtWebElement textHeader;

	@FindBy(xpath = "//input[@id='pyValue']")
	public ExtWebElement textboxVIN;
	
	@FindBy(xpath = "//select[@class='standard']")
	public ExtWebElement dropdownThread;
	
	@FindBy(xpath = "//div[text()='Run']")
	public ExtWebElement btnRun;
	
	@FindBy(xpath = "//h3[text()='Results']")
	public ExtWebElement headerResults;
	
	@FindBy(xpath = "//tr[@pl_index='18']//span[starts-with(@class,'smartInfoNew')]")
	public ExtWebElement txtModelCode;
	
	@FindBy(xpath = "//tr[@pl_index='6']//span[starts-with(@class,'smartInfoNew')]")
	public ExtWebElement txtEngineCode;
	
	//tr[@pl_index='18']//span[@data-back-nav-id]
	@FindBy(xpath = "//span[text()='Unable to load data page D_VehicleReference. Required parameters : VIN. cannot be blank']")
	public ExtWebElement txtUnableToLoad;
	
	@FindBy(xpath = "//li[@class='gridRow']//span/a")
	public List<ExtWebElement> listVehicleRefProperties;
	
	@FindBy(xpath = "//tr[@pl_index]//span[starts-with(@class,'smartInfoNew')]")
	public List<ExtWebElement> listVehicleRefValues;
	
	public void waitForPageToLoad() {

		try {
			getWindow(DriverFactory.parentWindow, textHeader);
			//textHeader.waitForPresent();

		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ textHeader.toString(), e);

		}

	}
	
	public static Map<String, String> vehicleReferenceMap;
	
	
	
	
	public static Map<String, String> getVehicleReferenceMap() {
		
		return vehicleReferenceMap;
	}
	


	public static void setVehicleReferenceMap(Map<String, String> vehicleReferenceMap) {
		DVehicleReferenceDataPage.vehicleReferenceMap = vehicleReferenceMap;
	}

	public static String get(String property) {
		String value ="";
		
		
		
		return value;
		
		
	}
	
	public void getWindow(String window) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			if(wh.equals(window))
				continue;
			DriverManager.getDriver().switchTo().window(wh);
			try {
			if (textHeader.isDisplayed()) {
					return;

				}
			}catch(NoSuchElementException e) {
				
				continue;
				
			}
			

		}
	}
}
