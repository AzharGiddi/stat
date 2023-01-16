package com.nissan.pages.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.support.FindBy;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.databeans.SymptomsDataBean;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class AutomaticTransmissionCVTSymptomFormComponent extends ExtWebComponent {

	@FindBy(xpath = "//div[@class='layout-body clearfix  ']/div[@class=' flex content layout-content-stacked  content-stacked ']")
	public ExtWebElement componentNewCVT;

	@FindBy(xpath = "//div[text()='Automatic Transmission / CVT - Symptom Form']")
	public ExtWebElement txtHeader;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	public ExtWebElement iframe;

	@FindBy(xpath = "//div[text()='Poor Shift Quality']")
	public ExtWebElement headerPoorShiftQuality;

	@FindBy(xpath = "//div[text()='Noise']")
	public ExtWebElement headerNoise;
	
	@FindBy(xpath = "//h6[text()='Concern']//parent::div/preceding-sibling::div")
	public ExtWebElement headerConcern;
	
	@FindBy(xpath = "//h6[text()='Cause']//parent::div/preceding-sibling::div")
	public ExtWebElement headerCause;

	@FindBy(xpath = "//h6[text()='Occurs when']")
	public ExtWebElement headerOccursWhen;

	@FindBy(xpath = "//textarea[contains(@name,'OtherSymptoms')]")
	public ExtWebElement txtboxOtherSymptomsRecentRepairs;

	@FindBy(xpath = "//button[text()='Save for Later']")
	public ExtWebElement btnSaveForLater;

	@FindBy(xpath = "//button[text()='Submit']")
	public ExtWebElement btnSubmit;

	@FindBy(xpath = "//div[@string_type='label' and contains(text(),'Thank you!')]")
	public ExtWebElement txtThankYou;

	@FindBy(xpath = "//input[@type='checkbox' and contains(@name,'PoorShiftQualityNA')]")
	public ExtWebElement checkBoxPSQNotApplicable;

	@FindBy(xpath = "//input[@type='checkbox' and contains(@name,'NoiseNA')]")
	public ExtWebElement checkBoxNoiseNotApplicable;

	// @FindBy(xpath =
	// "//span[text()]//ancestor::div/preceding-sibling::div/child::input[2][@checked]")
	@FindBy(xpath = "//input[2][@checked]/ancestor::div[@node_name='MultiSelectOptions']/descendant::div[@style='']/span[text()]")
	public List<ExtWebElement> selectedSymptoms;

	public void selectSymptoms(SymptomsDataBean symptoms) {
		
		selectCheckBoxes(symptoms.getCheckboxList());
		Map<String, String> dMap = symptoms.getDropDownMap();
		dMap.putAll(getDropDownMap("Payment_Assumption:"+symptoms.getPaymentAssumption()));
		selectDropDownValue(dMap);
		txtboxOtherSymptomsRecentRepairs.clearAndSendKeys(symptoms.getComments());
		
	}
	
	
	
	
	/**
	 * Use this method to fetch header elements.
	 * 
	 * Please use only to fetch following elements: <b>Case ID,Pending
	 * With,Payment Assumption,Dealer,RO # /WO #,Customer,VIN,Model,Year,Mileage</b>
	 * 
	 * @param eleName
	 *            Name of the header element
	 * @return ExtWebElement
	 */
	public ExtWebElement getHeaderWebElement(String eleName) {

		String xPath = "//span[text()='%s']/following-sibling::div/child::*";

		return getExtWebElement(String.format(xPath, eleName));
	}

	/**
	 * Use this method to fetch drop down elements on Automatic Transmission CVT
	 * Symptom Form.
	 * 
	 * Please use only for to fetch following dropdown elements: <b>Vehicle does not
	 * move,Vibration, Engine stalls when</b>
	 * 
	 * @param eleName
	 *            Name of the dropdown element
	 * @return ExtWebElement
	 */
	public ExtWebElement getDropDownWebElement(DropDownName eleName) {

		String xPath = "//div/descendant::Select[contains(@name,'%s')]";
		ExtWebElement extWebElement = ExtWebComponent.getExtWebElement(String.format(xPath, eleName.getDropDownName()));
		extWebElement.moveToElement();
		return extWebElement;
	}

	public ExtWebElement getDropDownWebElement(String eleName) {

		String xPath = "//div/descendant::Select[contains(@name,'%s')]";
		ExtWebElement extWebElement = ExtWebComponent.getExtWebElement(String.format(xPath, eleName));
		extWebElement.moveToElement();
		return extWebElement;
	}

	public List<String> getStringList(List<ExtWebElement> listExtWebElement) {

		return listExtWebElement.stream().map(ele -> {
			ele.moveToElement();
			return ele.getText();
		}).collect(Collectors.toList());

	}

	public Set<String> getSymptomsSet() {

		Set<String> symptomSet = new LinkedHashSet();
		// convertToStringList(selectedSymptoms);
		symptomSet.addAll(getStringList(selectedSymptoms).stream().collect(Collectors.toSet()));

		symptomSet.add(getDropDownWebElement(DropDownName.VEHICLEDOESNOTMOVE).getFirstSelectedOptionText());
		symptomSet.add(getDropDownWebElement(DropDownName.VIBRATION).getFirstSelectedOptionText());
		symptomSet.add(getDropDownWebElement(DropDownName.ENGINESTALLWHEN).getFirstSelectedOptionText());

		return symptomSet;

	}

	public List<String> getSymptomsList() {

		List<String> symptomSet = new ArrayList<>();
		symptomSet.addAll(getStringList(selectedSymptoms));
		symptomSet.add(getDropDownWebElement(DropDownName.VEHICLEDOESNOTMOVE).getFirstSelectedOptionText());
		symptomSet.add(getDropDownWebElement(DropDownName.VIBRATION).getFirstSelectedOptionText());
		symptomSet.add(getDropDownWebElement(DropDownName.ENGINESTALLWHEN).getFirstSelectedOptionText());
		return symptomSet;

	}

	public ExtWebElement getCheckBoxWebElement(CheckboxName eleName) {

		String xPath = "//span[text()='%s']//ancestor::div/preceding-sibling::div/child::input[2]";
		ExtWebElement extWebElement = getExtWebElement(String.format(xPath, eleName.getCheckboxName()));
		extWebElement.moveToElement();
		return extWebElement;

	}

	private ExtWebElement getCheckBoxWebElement(String eleName) {

		String xPath = "//span[text()='%s']//ancestor::div/preceding-sibling::div/child::input[2]";
		return ExtWebComponent.getExtWebElement(String.format(xPath, eleName));

	}

	public List<String> selectCheckBoxes(String checkboxesString) {

		List<String> checkboxList = Arrays.asList(checkboxesString.split(","));

		List<String> selectedSymptoms = new ArrayList<>();

		for (String checkbox : checkboxList) {
			if(checkbox.equals("")) {
				continue;
			}
			String cb = "";
			try {
				cb = getCheckBox(checkbox).getCheckboxName();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			if (cb.equals(""))
				continue;
			getCheckBoxWebElement(cb).scrollAndClick();
			selectedSymptoms.add(cb);

		}

		return selectedSymptoms;

	}
	
	public void selectCheckBoxes(List<String> listCheckboxes) {

		for (String checkbox : listCheckboxes) {
			
			getCheckBoxWebElement(checkbox).scrollAndClick();

		}

	}
	
	public List<String> getCheckBoxesList(String checkboxesString) {

		List<String> checkboxList = Arrays.asList(checkboxesString.split(";"));

		List<String> selectedSymptoms = new ArrayList<>();

		for (String checkbox : checkboxList) {
			System.out.println("selecting cb="+checkbox);
			if(checkbox.equals("")) {
				continue;
			}
			String cb = "";
			try {
				cb = getCheckBox(checkbox).getCheckboxName();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			if (cb.equals(""))
				continue;
			selectedSymptoms.add(cb);

		}

		return selectedSymptoms;

	}

	private CheckboxName getCheckBox(String eleName) {

		eleName = eleName.toUpperCase().replaceAll("[^a-zA-Z0-9]", "").trim();

		CheckboxName returnCB = null;

		for (CheckboxName cb : CheckboxName.values()) {
			String cbName = cb.name().trim();
			System.out.println("Comparing "+eleName+" with "+cbName);
			if (cbName.equalsIgnoreCase(eleName)) {
				returnCB = cb;
				break;
			}

		}

		return returnCB;
	}

	public enum CheckboxName {

		FLARE("Flare"), HARSHSHIFT("Harsh Shift"), HESITATION("Hesitation"), LACKOFPOWER("Lack of Power"), NODOWNSHIFT(
				"No Downshift"), NOUPSHIFT("No Upshift"), SHIFTPOINTTOOHIGH("Shift Point Too High"), SHIFTPOINTTOOLOW(
						"Shift Point Too Low"), SLIPS("Slips"), EXCESSIVENOISE("Excessive Noise"), TICKCLICKRATTLE(
								"Tick / Click / Rattle"), BOOMDRONERUMBLEROARTHROB(
										"Boom / Drone / Rumble / Roar / Throb"), HUMWHINESTATICMELODIOUSSOUND(
												"Hum / Whine / Static (Melodious Sound)"), HISSWHISTLEBUZZAIRMOVEMENT(
														"Hiss / Whistle / Buzz (Air Movement)"), CHIRPCREAKSQUEAKSQUEAL(
																"Chirp / Creak / Squeak / Squeal"), GRINDGROWLGROAN(
																		"Grind / Growl / Groan"), CLUNKKNOCKPOPTHUMP(
																				"Clunk / Knock / Pop / Thump"), GURGLESLOSHLIQUIDMOVEMENT(
																						"Gurgle / Slosh (Liquid Movement)"), ATABOUTMPH(
																								"At about MPH"), ENGINECOLD(
																										"Engine Cold"), ENGINEHOT(
																												"Engine Hot"), GOINGDOWNHILL(
																														"Going downhill"), GOINGUPHILLHEAVYTHROTTLE(
																																"Going uphill, heavy throttle"), GOINGUPHILLLIGHTTHROTTLE(
																																		"Going uphill, light throttle"), IDLE(
																																				"Idle"), OTHERS(
																																						"Others"), SLOWINGDOWN(
																																								"Slowing Down"), SPEEDINGUPHEAVYTHROTTLE(
																																										"Speeding up, heavy throttle"), SPEEDINGUPLIGHTTHROTTLE(
																																												"Speeding up, light throttle"), STARTINGUPENGINE(
																																														"Starting up engine"),NOMATCHFOUND("No Match Found");

		private String name;

		public String getCheckboxName() {
			return name;
		}

		private CheckboxName(String checkboxName) {
			name = checkboxName;
		}

	}

	public enum DropDownName {

		VEHICLEDOESNOTMOVE("Vehicle"), VIBRATION("Vibration"), ENGINESTALLWHEN(
				"EngineStallsWhen"), WHENDIDTHECONCERNBEGIN(
						"CustomerConcern"), FREQUENCY("Frequency"), PAYMENTASSUMPTION("PaymentType"), EVENT("Event");

		private String name;

		public String getDropDownName() {
			return this.name;
		}

		private DropDownName(String name) {
			this.name = name;
		}

	}

	public List<String> selectDropDownValue(String dropDownsString) {

		List<String> dropDownList = Arrays.asList(dropDownsString.split(","));

		List<String> selectedSymptoms = new ArrayList<>();

		for (String dropDown : dropDownList) {
			if(dropDown.equals("")) {
				continue;
			}
			String dropDownName = StringUtils.substringBefore(dropDown, ":");
			String dd = "";
			String value = StringUtils.substringAfter(dropDown, ":").replace("_", " ");
			try {
				dd = getDropDown(dropDownName).getDropDownName();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			if (dd.equals(""))
				continue;
			getDropDownWebElement(dd).select(value);
			selectedSymptoms.add(value);

		}

		return selectedSymptoms;

	}
	
	public void selectDropDownValue(Map<String, String> MapDropDown) {

		for (Map.Entry<String, String> dropDown : MapDropDown.entrySet()) {
			if(dropDown.getValue().equals(""))
				continue;
			getDropDownWebElement(dropDown.getKey()).select(dropDown.getValue());

		}

	}
	
	public Map<String,String> getDropDownMap(String dropDownsString) {

		List<String> dropDownList = Arrays.asList(dropDownsString.split(";"));

		Map<String, String> selectedSymptoms = new LinkedHashMap<>();

		for (String dropDown : dropDownList) {
			if(dropDown.equals("")) {
				continue;
			}
			String dropDownName = StringUtils.substringBefore(dropDown, ":");
			String dd = "";
			String value = StringUtils.substringAfter(dropDown, ":").replace("_", " ");
			try {
				dd = getDropDown(dropDownName).getDropDownName();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			if (dd.equals(""))
				continue;
			selectedSymptoms.put(dd,value);

		}

		return selectedSymptoms;

	}
	
	public List<String> getDropDownValues(Map<String, String> map){
		
		return map.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
		
	}

	private DropDownName getDropDown(String eleName) {

		eleName = eleName.toUpperCase().replaceAll("[^a-zA-Z0-9]", "");

		DropDownName returndd = null;

		for (DropDownName dd : DropDownName.values()) {

			if (dd.name().contains(eleName)) {
				returndd = dd;
				break;
			}

		}

		return returndd;
	}

	public void waitForComponentToLoad() {

		try {
			/*
			 * driver.switchTo().defaultContent(); driver.switchTo().frame(iframe);
			 * txtHeader.waitForPresent(); // txtHeader.waitForVisible();
			 */
			waitForFrameToLoad();
			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
		} catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName() + " did not load."));

		}
	}

	public AutomaticTransmissionCVTSymptomFormComponent() {
		waitForComponentToLoad();
	}
	
	public AutomaticTransmissionCVTSymptomFormComponent(boolean waitForComponent) {
		
		if(waitForComponent)
			waitForComponentToLoad();
		
		
	}
}
