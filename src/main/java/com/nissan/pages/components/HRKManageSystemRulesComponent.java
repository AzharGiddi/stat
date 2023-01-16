package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKManageSystemRulesComponent extends ExtWebComponent {

	private WhatsNewMessagingComponent whatsNewMessagingComponent;
	
	
	
	public ExtWebElement getMenuElement(String eleName) {

		String xPath = "//a[contains(text(),'" + eleName + "')]";

		return getExtWebElement(xPath);

	}
	
	

	public void waitForComponentToLoad() {

		try {
			
			waitForFrameToLoad();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}

	}

	public HRKManageSystemRulesComponent() {
		waitForComponentToLoad();
	}



	public WhatsNewMessagingComponent getWhatsNewMessagingComponent() {
		if(Objects.isNull(whatsNewMessagingComponent))
			setWhatsNewMessagingComponent(new WhatsNewMessagingComponent());
		return whatsNewMessagingComponent;
	}



	public void setWhatsNewMessagingComponent(WhatsNewMessagingComponent whatsNewMessagingComponent) {
		whatsNewMessagingComponent = whatsNewMessagingComponent;
	}

}
