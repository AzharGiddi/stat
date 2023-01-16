package com.nissan.pages;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.ActiveTSBComponent;
import com.nissan.pages.components.ManageSystemRulesComponent;
import com.nissan.pages.components.TSBComponent;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.NAARulesUtil;

public class EngManagerPage extends BasePage {
	
	public static Log logger = LogFactory.getLog(EngManagerPage.class);

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;

	@FindBy(xpath = "//span[contains(text(),'Manage System')]")
	public ExtWebElement tabManageSystemRules;

	@FindBy(xpath = "//div[text()='Close']")
	public ExtWebElement btnCloseSystemMessage;

	@FindBy(xpath = "//div[contains(text(),'Close')] | //ol[@style]//a[@aria-label='Currently open']")
	public List<ExtWebElement> listCloseBtn;

	public TSBComponent tSBComponent;

	public TSBComponent gettSBComponent() {
		if (Objects.isNull(tSBComponent))
			settSBComponent(new TSBComponent());
		return tSBComponent;
	}

	public void settSBComponent(TSBComponent tSBComponent) {
		this.tSBComponent = tSBComponent;
	}

	public ActiveTSBComponent activeTSBComponent;

	public ActiveTSBComponent getActiveTSBComponent(String iframe) {
		if (Objects.isNull(activeTSBComponent))
			setActiveTSBComponent(new ActiveTSBComponent(iframe));
		return activeTSBComponent;
	}

	public void setActiveTSBComponent(ActiveTSBComponent activeTSBComponent) {
		this.activeTSBComponent = activeTSBComponent;
	}

	public ManageSystemRulesComponent manageSystemRulesComponent;

	public ManageSystemRulesComponent getManageSystemRulesComponent() {
		if (Objects.isNull(manageSystemRulesComponent)) {
			setManageSystemRulesComponent(new ManageSystemRulesComponent());
		}
		return manageSystemRulesComponent;
	}

	public void setManageSystemRulesComponent(ManageSystemRulesComponent manageSystemRulesComponent) {
		this.manageSystemRulesComponent = manageSystemRulesComponent;
	}

	

	@Override
	public void waitForPageToLoad() {

		try {
			
			closeErrorMessageAndOpenTabs();
			//logoNissan.waitForVisible();
			logoNissan.isDisplayed();

			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully",logger);
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}

	}

	public Map<VCATCheckpoint, Boolean> getNAARules(Map<String, String> data) {

		Map<VCATCheckpoint, Boolean> returnMap = NAARulesUtil.evaluateNAARules(data);
		returnMap.put(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW, false);
		returnMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, false);

		return returnMap;

	}

	public boolean isAlertPresent() {
		try {
			DriverManager.getDriver().switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	@Override
	public void closeErrorMessageAndOpenTabs() {
		/*
		 * if(Objects.nonNull(btnCloseSystemMessage)) { btnCloseSystemMessage.click(); }
		 */
		DriverManager.getDriver().switchTo().defaultContent();
		for (ExtWebElement ele : listCloseBtn) {
			if (ele.getText().trim().equals("Close")) {
				ele.click();
				if (isAlertPresent()) {

					DriverManager.getDriver().switchTo().alert().accept();
				}
				continue;
			}

			ele.click();
			linkCloseAll.click();
		}

	}

	public void invoke() {

	}

	public EngManagerPage(String userName, String password) {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		login(userName, password);
	}

	public EngManagerPage(Roles role) {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		login(role);
	}

	
	public EngManagerPage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}

}
