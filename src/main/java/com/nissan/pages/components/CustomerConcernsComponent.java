package com.nissan.pages.components;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class CustomerConcernsComponent extends ExtWebComponent {
	
		@FindBy(xpath = "//div[text()='Customer Concerns']")
		public ExtWebElement headerCustomerConcerns;
		
		@FindBy(xpath = "//input[@name='$PpyWorkPage$pVIN']")
		public ExtWebElement txtBoxVIN;
		
		@FindBy(xpath = "//button[text()='Go']")
		public ExtWebElement btnGo;
		
		@FindBy(xpath = "//button[text()='Add a Concern']")
		public ExtWebElement btnAddAConcern;
		
		@FindBy(xpath = "//a[text()='[ I See ]']")
		public ExtWebElement linkISee;
		
		@FindBy(xpath = "//label[text()='Poor Appearance']/preceding-sibling::input[@type='checkbox']")
		public ExtWebElement chkboxPoorAppearance;
		
		@FindBy(xpath = "//button[normalize-space(text())='OK' and @class='buttonTdButton']")
		public ExtWebElement btnOk;
		
		@FindBy(xpath = "//button[text()='Add to Summary']")
		public ExtWebElement btnAddToSUmaryScreen;
		
		@FindBy(xpath = "//button[normalize-space(text())='Next']")
		public ExtWebElement btnNext;
		
		//@FindBy(xpath = "//select[contains(@name='$PpyWorkPage$pUSSCustomerVehicleConcern']")
		@FindBy(xpath = "//select[contains(@name,'$PpyWorkPage$pUSSCustomerVehicleConcern')]")
		public ExtWebElement dropDownPaytype;
		
		@FindBy(xpath = "//button[text()='DONE']")
		public ExtWebElement btnDone;
		
		@FindBy(xpath = "//div[text()='Congratulations!']")
		public ExtWebElement txtCongratulations;
		
		
		
		
		
		
	public void selectConcerns(String concernType, List<String> concerns) {

		ExtWebElement concernTypeLink = getExtWebElement(
				String.format("//div[text()='%s']/preceding-sibling::div//i", concernType), concernType + " link");
		concernTypeLink.click();
		
		ConcernsCheckBoxComponent concernsCheckBoxComponent = new ConcernsCheckBoxComponent(concernType);
		for (String concern : concerns) {

			concernsCheckBoxComponent.selectCheckBox(concern);

		}

		concernsCheckBoxComponent.btnOk.click();
		WaitUtil.sleep(2000);
		
	}
	
	public void verifyAllConcernsPresent(String concernType, List<String> concerns) {

		ExtWebElement concernTypeLink = getExtWebElement(
				String.format("//div[text()='%s']/preceding-sibling::div//i", concernType), concernType + " link");
		concernTypeLink.click();
		
		ConcernsCheckBoxComponent concernsCheckBoxComponent = new ConcernsCheckBoxComponent(concernType);
		/*for (String concern : concerns) {

			concernsCheckBoxComponent.getCheckBox(concern).assertVisible(concern+ " checkbox");

		}*/
		List<String> expectedLabel = concerns.stream().map(e->e).collect(Collectors.toList());
		List<String> actualLabel = concernsCheckBoxComponent.listCheckBoxesLabel.stream().map(e->e.getText()).collect(Collectors.toList());
		Validator.assertTrue(actualLabel.equals(expectedLabel),"All check box labels are present","Following labels are missing : "+expectedLabel.removeAll(actualLabel));
			
	}
	
	public List<ExtWebElement> getAllOptions(String concernType) {
		ExtWebElement concernTypeLink = getExtWebElement(
				String.format("//div[text()='%s']/preceding-sibling::div//i", concernType), concernType + " link");
		concernTypeLink.click();
		ConcernsCheckBoxComponent concernsCheckBoxComponent = new ConcernsCheckBoxComponent(concernType);
		return concernsCheckBoxComponent.getCheckBoxes();
	}
	
	
	
	public void selectHotSpot(String hotspot, List<String> concerns) {

		/*ExtWebElement hotspotElement = getExtWebElement(
				String.format("//div[text()='%s']/preceding-sibling::div//i", hotspot), hotspot + " link");
		hotspotElement.click();*/
		HotspotComponent hotspotComponent = new HotspotComponent(); 
		hotspotComponent.touchScreenArea.click();
		//DriverManager.getJavaScriptExecutor().executeScript("arguments[0].click();", hotspotComponent.touchScreenArea.getWrappedElement());
		
		ConcernsCheckBoxComponent concernsCheckBoxComponent = new ConcernsCheckBoxComponent(hotspot);
		for (String concern : concerns) {

			concernsCheckBoxComponent.selectCheckBox(concern);

		}

		concernsCheckBoxComponent.btnOk.click();

	}
		
		
		

		
		@Override
		public void waitForComponentToLoad() {
			try {
				waitForFrameToLoad();
				headerCustomerConcerns.waitForVisible();
				
			} catch (Exception e) {
				ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));
			}
		}
		
		
		public CustomerConcernsComponent() {
			ElementFactory.initElements(DriverManager.getDriver(), this);
			waitForComponentToLoad();
		}
	}


