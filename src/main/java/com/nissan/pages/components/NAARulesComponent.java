package com.nissan.pages.components;

import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.ExtWebElementImpl;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class NAARulesComponent extends ExtWebComponent {

	@FindBy(xpath = "//h1[text()='No Automated Approval Rules']")
	public ExtWebElement textHeader;

	@FindBy(xpath = "//tr[contains(@id,'ARCDealerExemptionList')]//input[contains(@data-ctl,'AutoCompleteAG')]")
	public List<ExtWebElement> listARCDealerExemption;
	
	@FindBy(xpath = "//a[contains(@name,'ExemptedARCDealer')]")
	public ExtWebElement linkAddArcDealer;
	
	@FindBy(xpath = "//div[@class='autocomplete-results']//span[@class='match' or @class='no-items']")
	public ExtWebElement txtARCDealerAutocompleteResult;

	@FindBy(xpath = "//tr[contains(@id,'DealerSpecific')]//input[contains(@data-ctl,'AutoCompleteAG')]")
	public List<ExtWebElement> listDealerSpecific;

	@FindBy(xpath = "//tr[contains(@id,'ManufacturingDateList')]")
	public List<ExtWebElement> listManufacturingDates;

	@FindBy(xpath = "//tr[contains(@id,'MonthsInService')]")
	public List<ExtWebElement> listMonthInService;

	@FindBy(xpath = "//tr[contains(@id,'Mileage')]")
	public List<ExtWebElement> listMileage;

	@FindBy(xpath = "//tr[contains(@id,'VIN')]")
	public List<ExtWebElement> listVIN;

	@FindBy(xpath = "//Select[contains(@name,'ManufacturingDateList') and contains(@name,'Model')]")
	public List<ExtWebElement> dropdownModel;

	@FindBy(xpath = "//Select[contains(@name,'ManufacturingDateList') and contains(@name,'Component')]")
	public List<ExtWebElement> dropdownManDateComponent;

	@FindBy(xpath = "//input[contains(@name,'ManufacturingDateList') and contains(@name,'From')]")
	public List<ExtWebElement> textManDateFromDate;

	@FindBy(xpath = "//input[contains(@name,'ManufacturingDateList') and contains(@name,'To')]")
	public List<ExtWebElement> textManDateToDate;
	
	private String arcDealerTextBoxXpath = "//input[@type='text' and contains(@name,'ARCDealerExemptionList$l%d')]"; 
	
	private String arcDealerDeleteRow = "//img[contains(@name,'ARCDealerExemptionList(%d)')]";

	public String getDropDownValue(ExtWebElement rowElement, String tableName, String dropDownName) {

		String xPath = String.format(".//Select[contains(@name,'%s') and contains(@name,'%s')]", tableName, dropDownName);

		WebElement ele = rowElement.findElement(By.xpath(xPath));

		ExtWebElement extWebElement = new ExtWebElementImpl(ele);

		return extWebElement.getFirstSelectedOptionText();
	}

	public String getValue(ExtWebElement rowElement, String tableName, String fieldName) {

		String xPath = String.format(".//input[contains(@name,'%s') and contains(@name,'%s') and @type='text']", tableName, fieldName);

		return new ExtWebElementImpl(rowElement.findElement(By.xpath(xPath))).getAttribute("value").trim();
	}

	public ExtWebElement getHeader(String header) {

		String xPath = String.format("//div[@class='header']/h2[text()='%s']", header);

		ExtWebElement ele = ExtWebComponent.getExtWebElement(xPath);

		return ele;

	}

	public ExtWebElement getCheckBoxValue(String checkBoxName) {

		String xPath = String.format("//label[text()='%s']/preceding-sibling::input[@type='checkbox']", checkBoxName);

		// ExtWebElement ele = ExtWebComponent.getExtWebElement(xPath);

		return ExtWebComponent.getExtWebElement(xPath);

	}
	
	
	public Boolean isARCDealer(String dealer, int rowNum) {
		
		linkAddArcDealer.click();
		ExtWebComponent.getExtWebElement(String.format(arcDealerTextBoxXpath, rowNum)).clearAndSendKeys(dealer);
		WaitUtil.sleep(2000);
		Boolean arcDealer = txtARCDealerAutocompleteResult.getText().equals(dealer) ;
		ExtWebComponent.getExtWebElement(String.format(arcDealerDeleteRow, rowNum)).click();
		return arcDealer;
		
	}

	@Override
	public void waitForComponentToLoad() {

		try {

			waitForFrameToLoad();
			textHeader.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");

		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load, Waited 20 seconds for the visibility of the element located by xpath: "+getLocatorXpath(this.getClass(),"textHeader")));
			

		}
	}
	
	
	public NAARulesComponent() {

		waitForComponentToLoad();

	}
	
	

}
