package com.nissan.pages.components;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.Validator;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class ConcernsCheckBoxComponent extends ExtWebComponent {

	@FindBy(xpath = "//span[@id='modaldialog_hd_title']")
	public ExtWebElement checkBoxComponentHeader;
	
	@FindBy(xpath = "//label[normalize-space(@class)='cb_standard']")
	public List<ExtWebElement> listCheckBoxesLabel;

	public String getCheckBoxComponentName(String concernType) {

		switch (concernType.toUpperCase()) {

		case "I SEE":
			return "What Do You See?";

		case "I HEAR":
			return "Please Select Sounds";

		case "I SMELL":
			return "What Do You Smell?";

		case "I FEEL":
			return "What Do You Feel?";

		case "I TASTE":
			return "Yuck!";

		case "MAINTENANCE":
			return "Maintenance";

		case "FUNCTIONALITY":
			return "Please Select Operating Conditions";

		case "SERVICE":
			return "Service";

		case "TIME":
			return "Since When Does This Occur?";

		case "DRIVING CONDITIONS":
			return "Please Select Driving Conditions";

		case "ENVIRONMENTAL CONDITIONS":
			return "Please Select Environmental Conditions";

		case "VEHICLE OPERATION":
			return "When During Vehicle Operation Does It Occur?";

		case "ROAD CONDITIONS":
			return "Please Select Where";

		default:
			throw new CustomRuntimeException(concernType + " is an invalid concern type");
		}

	}

	@FindBy(id = "ModalButtonSubmit")
	public ExtWebElement btnOk;

	public void selectCheckBox(String concern) {

		String concernChkBoxXpath = String.format("//label[text()='%s']/preceding-sibling::input[@type='checkbox']",
				concern);
		getExtWebElement(concernChkBoxXpath).click();
	}

	public List<ExtWebElement> getCheckBoxes() {

		String concernChkBoxXpath = "//label[@class=\" cb_standard\"]";
		return getExtWebElements(concernChkBoxXpath);
	}

	public ExtWebElement getCheckBox(String concern) {

		String concernChkBoxXpath = String.format("//label[text()='%s']/preceding-sibling::input[@type='checkbox']",concern);
		return getExtWebElement(concernChkBoxXpath);
	}
	
	

	public void waitForComponentToLoad(String concernType) {
		checkBoxComponentHeader.waitForPresent();
		checkBoxComponentHeader.waitForVisible();
		try {
			Validator.assertText(checkBoxComponentHeader.getText().trim(), getCheckBoxComponentName(concernType),
					"checkBoxHeader");
		} catch (Exception e) {
			ExtentLogger.fail(new PageNotFoundException(this.getClass().getName() + " did not load"));
		}
	}

	public ConcernsCheckBoxComponent(String concernType) {
		waitForComponentToLoad(concernType);
	}

}
