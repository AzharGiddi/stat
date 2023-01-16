package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class TSBComponent extends ExtWebComponent{

	@FindBy(xpath="//h2[text()='TSB & Tribal Knowledge']")
	public ExtWebElement headerTSB;
	
	//@FindBy(xpath="//div/span/a[contains(@name,'ServiceBulletinWB')]")
	@FindBy(xpath="//div/span/a[contains(@name,'ServiceBulletinWB')] | //div[text()='Work queue is empty']")
	public List<ExtWebElement> listTSBNumbers;
	
	@FindBy(xpath="//select[contains(@name,'ModelSearchTSB')]")
	public ExtWebElement dropdownModel;
	
	@FindBy(xpath="//select[contains(@name,'YearSearchTSB')]")
	public ExtWebElement dropdownYear;
	
	@FindBy(xpath="//div[text()='Work queue is empty']")
	public ExtWebElement txtWQEmpty;
	
	//@FindBy(xpath = "//td/div[@node_name='pyGridPaginator']/descendant::div[@string_type='field']/a")
	@FindBy(xpath = "//div[@node_name='pyGridPaginator']//a[not(@class) and @href='#']")
	public List<ExtWebElement> listPagination;
	
	@FindBy(xpath = "//div[@node_name='pyGridPaginator']//a[contains(@title,'Next Page')]")
	public ExtWebElement linkPagination;	
	
	@FindBy(xpath = "//button[text()='Go']")
	public ExtWebElement buttonGo;	
	
	@FindBy(xpath = "//button[text()='Clear']")
	public ExtWebElement buttonClear;
	
	
	
	
	public List<ExtWebElement> getActiveTSBsList(){
		
		List<ExtWebElement> extWebElementList = getExtWebElements("//div/span/a[contains(@name,'ServiceBulletinWB')] | //div[text()='Work queue is empty']");
		
		return extWebElementList;
	}
	
	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		
		getExtWebElement(colHeaderXpath).click();
		txtboxSearchText.scrollIntoView();
		txtboxSearchText.clearAndSendKeys(filterText);
		btnApply.click();
		WaitUtil.sleep(2000);
		
	}
	
	public void waitForComponentToLoad() {
		try {
			/*driver.switchTo().defaultContent();
			driver.switchTo().frame(getIFrame("TSB & Tribal"));
			headerTSB.waitForPresent();
			headerTSB.waitForVisible();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");*/
		
			waitForFrameToLoad();
			headerTSB.waitForPresent();
		} catch (Exception e) {
			
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
			
		}
	}
	
	public TSBComponent() {
		
		waitForComponentToLoad();
	}
}
