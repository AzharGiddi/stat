package com.nissan.pages.components;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class PCCQuestionsComponent  extends ExtWebComponent{

	@FindBy(xpath = "//h2[normalize-space(text())='Question# 1']")
	public ExtWebElement headerQuestions;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	public ExtWebElement iframe;
	
	
	@Override
	public void waitForComponentToLoad() {

		try {
			/*driver.switchTo().defaultContent();
			driver.switchTo().frame(iframe);
			headerQuestions.waitForPresent();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");*/
			waitForFrameToLoad();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "));

		}
	}

	public PCCQuestionsComponent() {
		waitForComponentToLoad();
	}
	
}
