package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.GoodWillRequestComponent;
import com.nissan.pages.components.VCATAutomaticTransmissionCVTSymtomFormComponent;
import com.nissan.reports.ExtentLogger;

public class VCATPage extends BasePage {

	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;

	private GoodWillRequestComponent goodWillRequestComponent;

	public GoodWillRequestComponent getGoodWillRequestComponent() {
		if (Objects.isNull(goodWillRequestComponent)) {
			setGoodWillRequestComponent(new GoodWillRequestComponent());
		}

		return goodWillRequestComponent;
	}
	
	public GoodWillRequestComponent getGoodWillRequestComponent(boolean newObject) {
		if (newObject) {
			setGoodWillRequestComponent(null);
		}

		return getGoodWillRequestComponent();
	}

	public void setGoodWillRequestComponent(GoodWillRequestComponent goodWillRequestComponent) {
		this.goodWillRequestComponent = goodWillRequestComponent;
	}

	private VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent;

	public VCATAutomaticTransmissionCVTSymtomFormComponent getvCATAutomaticTransmissionCVTSymtomFormComponent() {
		if (Objects.isNull(vCATAutomaticTransmissionCVTSymtomFormComponent)) {
			setvCATAutomaticTransmissionCVTSymtomFormComponent(new VCATAutomaticTransmissionCVTSymtomFormComponent());
		}
		return vCATAutomaticTransmissionCVTSymtomFormComponent;
	}

	public void setvCATAutomaticTransmissionCVTSymtomFormComponent(
			VCATAutomaticTransmissionCVTSymtomFormComponent vCATAutomaticTransmissionCVTSymtomFormComponent) {
		this.vCATAutomaticTransmissionCVTSymtomFormComponent = vCATAutomaticTransmissionCVTSymtomFormComponent;
	}

	public void waitForPageToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			closeErrorMessageAndOpenTabs();
			logoNissan.waitForVisible();

			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
					+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}

	}

	public VCATPage(String userName, String password) {
		login(userName, password);
	}

	public VCATPage() {

	}

}
