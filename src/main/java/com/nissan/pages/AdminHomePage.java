package com.nissan.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.automation.core.utils.StringUtil;
import com.nissan.driver.DriverFactory;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.DVehicleReferenceComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.DVehicleReferenceDetailsUtil;
import com.nissan.utils.ROUtil;

/***
 * 
 * @author AB00789853
 *
 */

public class AdminHomePage extends BasePage {

	private WebDriver driver;

	@FindBy(xpath = "//a[@title='Switch Studio']")
	public ExtWebElement linkDevStudio;

	@FindBy(xpath = "//a[@title='Create menu']")
	public ExtWebElement linkCreateMenu;

	@FindBy(xpath = "//span[text()='Integration-Services']")
	public ExtWebElement linkIntegrationServices;

	@FindBy(xpath = "//span[@class='menu-item-title' and text()='Service MQ']")
	public ExtWebElement linkServiceMQ_HeaderMenu;

	@FindBy(xpath = "//li[@role='tab' and @aria-label='Service MQ']")
	public ExtWebElement tabServiceMQ;

	@FindBy(xpath = "//*[@aria-label='Records']")
	public ExtWebElement iconRecords;

	@FindBy(xpath = "//span[@class='explorer_primary' and text()='Integration-Services']")
	public ExtWebElement linkExplorerMenuIntegrationServices;

	@FindBy(xpath = "//span[@class='explorer_primary' and text()='Integration-Services']/ancestor::ul[@class='rowContent gridCellSelected']/child::li[@class=' cellCont  rowHandle  gridColumn ']//a")
	public ExtWebElement collapseIntegrationServices;

	@FindBy(xpath = "//span[@class='explorer_primary' and text()='Integration-Services']/ancestor::ul[starts-with(@class,'rowContent')]/child::li[@class=' cellCont  rowHandle  gridColumn ']//a")
	public ExtWebElement expandIntegrationServices;

	@FindBy(xpath = "//a[text()='Service MQ']")
	public ExtWebElement linkServiceMQ_SideMenu;

	@FindBy(xpath = "//span[@class='primary_search']/input[contains(@name,'SearchText')]")
	public ExtWebElement textboxSearch;

	@FindBy(xpath = "//tr[contains(@id,'SearchResults')]//a[text()='CaseListReport']")
	public ExtWebElement linkCaseListReport;

	@FindBy(xpath = "//tr[contains(@id,'SearchResults')]//a[text()='D_VehicleReference']")
	public ExtWebElement linkDVehicleRef;

	@FindBy(xpath = "//a[@title='Configure Dev Studio']")
	public ExtWebElement linkConfigureDevStudio;
	
	@FindBy(xpath = "//a[@title='Launch portal']")
	public ExtWebElement linkLaunchPortal;
	
	@FindBy(xpath = "//span[text()='STAT Manager Portal']")
	public ExtWebElement linkStatManagerPortal;

	@FindBy(xpath = "//span[@class='menu-item-title' and text()='System']")
	public ExtWebElement linkSystem;

	// span[@class='explorer_primary' and
	// text()='Integration-Services']/ancestor::ul[starts-with(@class,'rowContent')]/child::li[@class='
	// cellCont rowHandle gridColumn ']//

	@Override
	public void waitForPageToLoad() {

		try {
			//driver.switchTo().defaultContent();
			linkDevStudio.waitForVisible();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

		throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ linkDevStudio.toString());

		}

	}

	public void hoverOnElement(ExtWebElement ele) {

		try {
			DriverManager.getActionDriver().moveToElement(ele).build().perform();
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Unable to hover on element due to: " + e.getLocalizedMessage());

		}

	}

	public void jsClick(WebElement ele) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", ele);

	}

	/*
	 * public void expandDropDown(String ele) {
	 * 
	 * String xpath = "//span[@class='explorer_primary' and text()='" + ele +
	 * "']/ancestor::ul[starts-with(@class,'rowContent')]/child::li[@class=' cellCont  rowHandle  gridColumn ']//a"
	 * ;
	 * 
	 * driver.findElement(By.xpath(xpath)).click();
	 * 
	 * }
	 */

	public void expandDropDown(String ele) {

		String xPath = String.format(
				"//span[@class='explorer_primary' and text()='%s']/ancestor::ul[starts-with(@class,'rowContent')]/child::li[@class=' cellCont  rowHandle  gridColumn ']//a",
				ele);

		ExtWebElement extWebElemet = getExtWebElement(xPath);

		extWebElemet.click();

	}

	private void createRO(String rOXmlPath, String ruleSetVersion) {

		ROUtil.createRO(rOXmlPath, ruleSetVersion, this);
	}

	private void createRO(String rOXmlPath, String ruleSetVersion, String date) {

		ROUtil.createRO(rOXmlPath, ruleSetVersion, this, date);
	}

	public String getRONumber(String rOXmlPath, String ruleSetVersion) {

		createRO(rOXmlPath, ruleSetVersion);
		return ROUtil.getrONumber();
	}

	public String getRONumber(String rOXmlPath, String ruleSetVersion, String... date) {
		if (date == null || date.length == 0 || date[0].equals("")) {
			createRO(rOXmlPath, ruleSetVersion);
			return ROUtil.getrONumber();
		} else {
			createRO(rOXmlPath, ruleSetVersion, date[0]);
			return ROUtil.getrONumber(date[0]);
		}
	}

	// TODO Refactor below code to remove hardcoded values
	public String[] getVehicleModel(String vin) {

		String[] vehicleDetails = new String[2];

		textboxSearch.clearAndSendKeys("d_vehiclereference" + Keys.ENTER);
		linkDVehicleRef.click();
		getdVehicleReferenceComponent().buttonActions.click();
		getdVehicleReferenceComponent().linkRun.click();
		// closeTab("D_VehicleRef");
		DVehicleReferenceDataPage dVehicleReferenceDataPage = new DVehicleReferenceDataPage();
		dVehicleReferenceDataPage.waitForPageToLoad();
		// dVehicleReferenceDataPage.dropdownThread.select("D_VehicleReference");
		dVehicleReferenceDataPage.textboxVIN.clearAndSendKeys(vin + Keys.TAB);
		dVehicleReferenceDataPage.btnRun.click();
		// WaitUtil.sleep(2000);
		dVehicleReferenceDataPage.headerResults.waitForPresent();

		String modelNumber = dVehicleReferenceDataPage.txtModelCode.getText().trim();
		String engCode = dVehicleReferenceDataPage.txtModelCode.getText().trim();
		vehicleDetails[0] = modelNumber;
		vehicleDetails[1] = engCode;

		System.out.println(modelNumber);
		dVehicleReferenceDataPage.close();
		driver.switchTo().window(DriverFactory.parentWindow);
		return vehicleDetails;

	}

	// TODO Refactor below code to remove hardcoded values
	public Map<String, String> getVehicleReferenceMap(String vin) {

		/*Map<String, String> vehicleDetails = new HashMap<>();
		textboxSearch.sendKeys("d_vehiclereference" + Keys.ENTER);
		linkDVehicleRef.click();
		getdVehicleReferenceComponent().buttonActions.click();
		getdVehicleReferenceComponent().linkRun.click();

		DVehicleReferenceDataPage dVehicleReferenceDataPage = new DVehicleReferenceDataPage();
		dVehicleReferenceDataPage.waitForPageToLoad();
		dVehicleReferenceDataPage.textboxVIN.sendKeys(vin + Keys.TAB);
		dVehicleReferenceDataPage.btnRun.click();
		dVehicleReferenceDataPage.headerResults.waitForPresent();

		Iterator<String> propertiesIterator = StringUtil
				.getStringList(dVehicleReferenceDataPage.listVehicleRefProperties).iterator();
		Iterator<String> valuesIterator = StringUtil.getStringList(dVehicleReferenceDataPage.listVehicleRefValues)
				.iterator();

		while (propertiesIterator.hasNext() && valuesIterator.hasNext()) {

			vehicleDetails.put(propertiesIterator.next(), valuesIterator.next());

		}
		dVehicleReferenceDataPage.close();
		driver.switchTo().window(DriverFactory.parentWindow);
		return vehicleDetails;*/
		
		return DVehicleReferenceDetailsUtil.getVehicleReferenceMap(vin);

	}

	private DVehicleReferenceComponent dVehicleReferenceComponent;

	public DVehicleReferenceComponent getdVehicleReferenceComponent() {
		if (Objects.isNull(dVehicleReferenceComponent))
			setdVehicleReferenceComponent(new DVehicleReferenceComponent());

		return dVehicleReferenceComponent;
	}
	
public void getQueryRunner() {
		
		
		waitForPageToLoad();
		linkConfigureDevStudio.click();
		hoverOnDDElement("System");
		hoverOnDDElement("Database");
		clickOnDDElement("Query runner");
		
	}

	private void setdVehicleReferenceComponent(DVehicleReferenceComponent dVehicleReferenceComponent) {
		this.dVehicleReferenceComponent = dVehicleReferenceComponent;
	}

	public AdminHomePage() {

	}

	public AdminHomePage(Roles role) {
		login(role);
	}

	public AdminHomePage(String userName, String password) {
		login(userName, password);
	}
}
