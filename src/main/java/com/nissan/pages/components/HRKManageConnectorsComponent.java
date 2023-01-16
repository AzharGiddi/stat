package com.nissan.pages.components;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class HRKManageConnectorsComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[text()='Manage Connectors']")
	public ExtWebElement header;
	
	@FindBy(xpath = "//tr[contains(@id,'$PD_GetConnectorList') and contains(@id,'ppxResults')]")
	public List<ExtWebElement> listConnectorData;
	
	
	
	public ExtWebElement getTableColumnHeaders(String eleName) {

		String xPath = String.format("//table[@pl_prop='D_GetConnectorList.pxResults']//th//div[text()='%s']",eleName);

		return getExtWebElement(xPath);

	}
	
	public void openConnectorDataAtIndex(int index) {

		String xPath = String.format("//a[contains(@name,'ManageConnectors_D_GetConnectorList') and contains(@name,'pxResults(%d)')]",index);

		getExtWebElement(xPath).scrollAndClick();

	}
	
	

	@Override
	public void waitForComponentToLoad() {

		try {
			
			waitForFrameToLoad();
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}

	}

	public HRKManageConnectorsComponent() {
		waitForComponentToLoad();
	}



	

}
