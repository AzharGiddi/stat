package com.nissan.pages.components;

import java.util.Objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class DashboardComponent extends ExtWebComponent {

	@FindBy(xpath = "//*[@id='INNERDIV-SubSectionTSBAdminPortalContentB']")
	public ExtWebElement componentDashBoard;

	public PleaseSelectDealershipComponent pleaseSelectDealershipComponent;

	public PleaseSelectDealershipComponent getPleaseSelectDealershipComponent() {

		if (Objects.isNull(pleaseSelectDealershipComponent)) {
			setPleaseSelectDealershipComponent(new PleaseSelectDealershipComponent());
		}

		return pleaseSelectDealershipComponent;
	}

	public void setPleaseSelectDealershipComponent(PleaseSelectDealershipComponent pleaseSelectDealershipComponent) {
		this.pleaseSelectDealershipComponent = pleaseSelectDealershipComponent;
	}

	public WhatsNewComponent whatsNewComponent;

	public WhatsNewComponent getWhatsNewComponent() {
		if (Objects.isNull(whatsNewComponent)) {
			setWhatsNewComponent(new WhatsNewComponent());
		}

		return whatsNewComponent;
	}

	public void setWhatsNewComponent(WhatsNewComponent whatsNewComponent) {
		this.whatsNewComponent = whatsNewComponent;
	}

	public void waitForComponentToLoad() {

		try {
			/*driver.switchTo().defaultContent();*/
			componentDashBoard.waitForPresent();
			//waitForFrameToLoad();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ componentDashBoard.toString()));

		}
	}

	public DashboardComponent() {
		waitForComponentToLoad();
	}
}
