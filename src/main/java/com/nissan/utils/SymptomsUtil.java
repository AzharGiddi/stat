package com.nissan.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.CheckboxName;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.DropDownName;

public class SymptomsUtil {

	
	private SymptomsUtil() {
		
	}
	
	/***
	 * Use this method to select the check box symptoms
	 * @param symptomString - Name of the checkbpxes to be selected separated by SemiColon
	 * @return This method return the list of check boxes selected.
	 */
	
	public static List<String> selectCheckBox(String symptomString) {

		List<String> checkboxList = Arrays.asList(symptomString.split(";"));

		List<String> selectedSymptoms = new ArrayList<>();

		for (String checkbox : checkboxList) {
			/*CheckboxName checkBoxName = getCheckBox(checkbox);
			if (checkBoxName.equals(CheckboxName.NOMATCHFOUND))
				continue;*/
			getCheckBoxWebElement(checkbox).click();
			selectedSymptoms.add(checkbox);
		}

		return selectedSymptoms;

	}
	
	public static String selectDropDownValue(String dropDownName, String valueToSelect) {

		getDropDownWebElement(dropDownName).select(valueToSelect);

		return valueToSelect;

	}
	
		
	private static ExtWebElement getDropDownWebElement(String eleName) {

		String xPath = "//div/descendant::Select[contains(@name,'%s')]";
		return ExtWebComponent.getExtWebElement(String.format(xPath, eleName), eleName);
		
	}

	private static ExtWebElement getCheckBoxWebElement(String eleName) {

		String xPath = "//*[normalize-space(text())='%s']//ancestor::div/preceding-sibling::div/child::input[2]";
		return ExtWebComponent.getExtWebElement(String.format(xPath, eleName), eleName);

	}
}
