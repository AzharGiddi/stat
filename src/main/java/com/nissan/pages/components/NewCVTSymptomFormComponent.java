package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class NewCVTSymptomFormComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[@class='layout-body clearfix  ']/div[@class=' flex content layout-content-stacked  content-stacked ']")
	private ExtWebElement componentNewCVT;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title='New']")
	private ExtWebElement iframeNew;

	@FindBy(xpath = "//*[@id='headerlabel5768']")
	public ExtWebElement txtHeader;

	@FindBy(xpath = "//button[text()='Skip ROL']")
	public ExtWebElement btnSkipROL;
	
	@FindBy(xpath = "//button[text()='Select']")
	public ExtWebElement btnSelect;

	public ExtWebElement selectRO(String rONumber) {
		String xPath = "//span[text()='%s']//ancestor::td[1]//following-sibling::td//child::div/span/button[text()='Select']";
		return getExtWebElement(String.format(xPath, rONumber));
	}

	public void waitForComponentToLoad() {
		try {
			waitForFrameToLoad();
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
		}
	}

	public NewCVTSymptomFormComponent() {
		waitForComponentToLoad();
	}

}
