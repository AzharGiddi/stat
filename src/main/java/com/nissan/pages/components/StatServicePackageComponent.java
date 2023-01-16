package com.nissan.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.BasePage;

/***
 * 
 * @author AB00789853
 *
 */
public class StatServicePackageComponent extends BasePage {

	@FindBy(xpath = "//td/span[contains(text(),'STATService')]")
	public ExtWebElement tabStatServicePackage;

	@FindBy(xpath = "//iframe[@title='STATServicePackage Services CreateUpdateRepairOrder']")
	public ExtWebElement iframe;

	@FindBy(xpath = "//button[contains(@data-click,'className\":\"Rule-Service-MQ') and text()='Actions']")
	public ExtWebElement buttonActions;

	@FindBy(xpath = "//ul[contains(@style,'display: block')]//span[text()='Run']")
	public ExtWebElement linkRun;

	public void waitForComponentToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			tabStatServicePackage.waitForPresent();
			;
			DriverManager.getDriver().switchTo().frame(iframe);
			logger.info(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			throw new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ tabStatServicePackage.toString(), e);

		}

	}

	public void openRuleSet(String ruleSetVersion) {

		String xpath = "//div[text()='CreateUpdateRepairOrder ']/parent::td/following-sibling::td/child::div[contains(text(),'"
				+ ruleSetVersion + "')]";
		DriverManager.getDriver().findElement(By.xpath(xpath)).click();
	}

}
