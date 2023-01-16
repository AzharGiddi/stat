package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.ConnectorLookupComponent;
import com.nissan.pages.components.HRKConnectorDetailsComponent;
import com.nissan.reports.ExtentLogger;

public class DealerUserPage extends BasePage {

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;
	
	private ConnectorLookupComponent connectorLookupComponent;
	
	public ConnectorLookupComponent getConnectorLookupComponent() {
		if (Objects.isNull(connectorLookupComponent))
			setConnectorLookupComponent(new ConnectorLookupComponent());
		return connectorLookupComponent;
	}

	public void setConnectorLookupComponent(ConnectorLookupComponent connectorLookupComponent) {
		this.connectorLookupComponent = connectorLookupComponent;
	}

	public void waitForPageToLoad() {

		try {

			logoNissan.waitForPresent();
			closeErrorMessageAndOpenTabs();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}

	}

	public DealerUserPage() {

	}

	public DealerUserPage(String userName, String password) {
		login(userName, password);
	}

	
}
