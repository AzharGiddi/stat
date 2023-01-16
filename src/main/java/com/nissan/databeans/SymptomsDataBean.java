package com.nissan.databeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissan.enums.UserData;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.CheckboxName;
import com.nissan.pages.components.AutomaticTransmissionCVTSymptomFormComponent.DropDownName;
import com.nissan.utils.TSBAPIUtil;

public class SymptomsDataBean {

	private static final Log logger = LogFactory.getLog(SymptomsDataBean.class);
	
	private List<String> symptomSet;
	
	private Map<String, String> dropDownMap;
	
	public Map<String, String> getDropDownMap() {
		return dropDownMap;
	}

	private void setDropDownMap(String dropDownString) {
		this.dropDownMap = getDropDownMap(dropDownString);
	}

	public List<String> getSymptomSet() {
		return symptomSet;
	}

	private void setSymptomSet(List<String> checkboxList, List<String> dropDownList) {
		
		symptomSet = new ArrayList<>();
		symptomSet.addAll(checkboxList);
		symptomSet.addAll(dropDownList);
		
	}
	
	public void addToSymptomSet(String symptom) {
		if(Objects.isNull(symptomSet)) {
			symptomSet = new ArrayList<>();
		}
		symptomSet.add(symptom);
	}
	
	public void addToSymptomSet(List<String> symptoms) {
		if(Objects.isNull(symptomSet)) {
			symptomSet = new ArrayList<>();
		}
		symptomSet.addAll(symptoms);
		
	}

	private List<String> checkboxList;

	public List<String> getCheckboxList() {
		return checkboxList;
	}

	private void setCheckboxList(String checkboxesString) {
		
		this.checkboxList = getCheckBoxesList(checkboxesString);
	}

	public List<String> getDropDownList() {
		return dropDownList;
	}

	private void setDropDownList(Map<String, String> dropDownMap) {

		this.dropDownList = getDropDownValues(dropDownMap);
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPaymentAssumption() {
		
		return this.paymentAssumption;
	}

	public void setPaymentAssumption(String paymentAssumption) {
		this.paymentAssumption = paymentAssumption.trim();
	}

	private List<String> dropDownList;

	private String comments;

	private String paymentAssumption;

	private List<String> getCheckBoxesList(String checkboxesString) {

		if(Objects.isNull(checkboxesString)) {
			checkboxesString="";
		}
		List<String> checkboxList = Arrays.asList(checkboxesString.split(";"));

		List<String> selectedSymptoms = new ArrayList<>();

		for (String checkbox : checkboxList) {
			logger.debug("selecting checkboxes" + checkbox);
			if (checkbox.equals("")) {
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

		CheckboxName returnCB = CheckboxName.NOMATCHFOUND;

		for (CheckboxName cb : CheckboxName.values()) {
			String cbName = cb.name().trim();
			// System.out.println("Comparing "+eleName+" with "+cbName);
			if (cbName.equalsIgnoreCase(eleName)) {
				returnCB = cb;
				break;
			}

		}

		return returnCB;
	}

	
	/**
	 * @author AB00789853
	 * @param dropDownsString: Drop down string entered in the excel sheet.
	 * @return Map of DropDown Name, Value to select.
	 */
	
	private Map<String, String> getDropDownMap(String dropDownsString) {

		if(Objects.isNull(dropDownsString)) {
			dropDownsString="";
		}
		List<String> dropDownsList = Arrays.asList(dropDownsString.split(";"));

		Map<String, String> selectedSymptoms = new LinkedHashMap<>();

		for (String dropDown : dropDownsList) {
			if (dropDown.equals("")) {
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
			selectedSymptoms.put(dd, value);

		}

		return selectedSymptoms;

	}
	/**
	 * 
	 * @param eleName
	 * @return
	 */
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

	private List<String> getDropDownValues(Map<String, String> map) {

		return map.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());

	}

	public SymptomsDataBean(String checkboxesString, String dropDownString, String comments, String paymentAssumption) {

		setCheckboxList(checkboxesString);
		setDropDownMap(dropDownString);
		setDropDownList(getDropDownMap());
		setComments(comments);
		setPaymentAssumption(paymentAssumption);
		setSymptomSet(getCheckboxList(), getDropDownList());
	}
	
	public SymptomsDataBean() {

		
	}
	
	

}
