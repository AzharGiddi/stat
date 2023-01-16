package com.nissan.pages;

import java.util.Objects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.pages.components.GoodWillRequestComponent;
import com.nissan.pages.components.SMGoodWillWorkListComponent;
import com.nissan.pages.components.SelectRepairOrderComponent;
import com.nissan.pages.components.SelectedRepairOrderComponent;
import com.nissan.reports.ExtentLogger;

public class ServiceManagerPage extends BasePage {

	private SelectedRepairOrderComponent selectedRepairOrderComponent;
	
	private GoodWillRequestComponent goodWillRequestComponent;
	
	private SelectRepairOrderComponent selectROTableComponent;
	
	private SMGoodWillWorkListComponent goodWillWL;
	
	@FindBy(xpath = "//img[@name='PortalHeaderGW_pyDisplayHarness_1']")
	public ExtWebElement logoNissan;
	
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

	

	public SelectRepairOrderComponent getSelectROTableComponent() {
		if (Objects.isNull(selectROTableComponent)) {
			setSelectROTableComponent(new SelectRepairOrderComponent());
		}

		return selectROTableComponent;
	}

	public void setSelectROTableComponent(SelectRepairOrderComponent selectROTableComponent) {
		this.selectROTableComponent = selectROTableComponent;
	}
	
	public SelectedRepairOrderComponent getSelectedRepairOrderComponent() {
		if (Objects.isNull(selectedRepairOrderComponent)) {
			setSelectedRepairOrderComponent(new SelectedRepairOrderComponent());
		}

		return selectedRepairOrderComponent;
	}

	public void setSelectedRepairOrderComponent(SelectedRepairOrderComponent selectedRepairOrderComponent) {
		this.selectedRepairOrderComponent = selectedRepairOrderComponent;
	}

	public void waitForPageToLoad() {

		try {
			DriverManager.getDriver().switchTo().defaultContent();
			closeErrorMessageAndOpenTabs();
			logoNissan.waitForVisible();

			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()+ " did not load, Waited 20 seconds for the visibility of the element located by xpath: "
					+ logoNissan.toString()));

		}

	}

	public ServiceManagerPage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}

	public ServiceManagerPage(Roles role) {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		login(role);
	}

	public ServiceManagerPage(String userName, String password) {
		ElementFactory.initElements(DriverManager.getDriver(), this);
		login(userName, password);
	}

	public SMGoodWillWorkListComponent getGoodWillWL() {
		if(Objects.isNull(goodWillWL))
			setGoodWillWL(new SMGoodWillWorkListComponent());
		
		return goodWillWL;
	}

	public void setGoodWillWL(SMGoodWillWorkListComponent goodWillWL) {
		this.goodWillWL = goodWillWL;
	}

}
