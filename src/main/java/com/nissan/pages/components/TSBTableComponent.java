package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class TSBTableComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[@node_name='pyGridPaginator']//a[contains(@title,'Next Page')]")
	public ExtWebElement linkPagination;

	@FindBy(xpath = "//h2[text()='TSB & Tribal Knowledge']")
	public ExtWebElement headerTSB;

	@FindBy(xpath = "//div/span/a[contains(@name,'ServiceBulletinWB')] | //div[text()='Work queue is empty']")
	public List<ExtWebElement> listTSBNumbers;

	public void clickNextLink() {
		WebElement ext = DriverManager.getDriver()
				.findElement(By.xpath("//div[@node_name='pyGridPaginator']//a[contains(@title,'Next Page')]"));

		DriverManager.getJavaScriptExecutor().executeScript("arguments[0].click();", ext);
	}

	public List<ExtWebElement> getActiveTSBsList() {

		List<ExtWebElement> extWebElementList = getExtWebElements(
				"//div/span/a[contains(@name,'ServiceBulletinWB')] | //div[text()='Work queue is empty']");

		return extWebElementList;
	}

	@Override
	public void waitForComponentToLoad() {
		try {
			// DriverManager.getDriver().switchTo().defaultContent();
			// DriverManager.getDriver().switchTo().frame(getIFrame("TSB & Tribal"));
			//waitForFrameToLoad(getIFrame("TSB & Tribal"));
			waitForFrameToLoad();
			// headerTSB.waitForPresent();
			// headerTSB.waitForVisible();
			// ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public int getPaginationSize() {
		List<ExtWebElement> extWebElementList = ExtWebComponent.getExtWebElements(
				"//div[@node_name='pyGridPaginator']//a[@href='#'] | //i[@name='ServiceBulletinWB_pyDisplayHarness_20']");
		return extWebElementList.size() == 1 ? 1 : extWebElementList.size() - 1;
	}

	public TSBTableComponent() {
	//	waitForComponentToLoad();
	}

}
