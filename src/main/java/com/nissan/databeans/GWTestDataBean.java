package com.nissan.databeans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.nissan.enums.UserData;
import com.nissan.utils.DVehicleReferenceDetailsUtil;
import com.nissan.utils.ROUtil;

public class GWTestDataBean {

	private String roNumber;

	private String dCase;

	private Map<UserData, String> userData;

	private Map<String, String> vehicleRefDetails;

	public String getRoNumber() {
		return roNumber;
	}

	public void setRoNumber(String roNumber) {
		this.roNumber = roNumber;
	}

	public String getDCase() {
		return dCase;
	}

	public void setDCase(String dCase) {
		this.dCase = dCase;
	}

	public Map<UserData, String> getUserData() {
		return userData;
	}

	public void setUserData(Map<String, String> userData) {
		if (userData.isEmpty()) {
			return;
		}
		Map<UserData, String> enumUserDataMap = new LinkedHashMap<>();

		Iterator<UserData> itr = Arrays.asList(UserData.values()).iterator();

		while (itr.hasNext()) {

			UserData field = itr.next();
			enumUserDataMap.put(field, userData.get(field.getFieldName()));

		}

		this.userData = enumUserDataMap;
	}

	public void addUserData(Map<String, String> userData) {
		if (userData.isEmpty()) {
			return;
		}
		Map<UserData, String> enumUserDataMap = new LinkedHashMap<>();

		Iterator<UserData> itr = Arrays.asList(UserData.values()).iterator();

		while (itr.hasNext()) {

			UserData field = itr.next();
			String value = userData.get(field.getFieldName());
			if (Objects.isNull(value)) {
				continue;
			}
			enumUserDataMap.put(field, value);

		}

		if(Objects.isNull(this.userData)){
			this.userData = enumUserDataMap;
		}else {
			this.userData.putAll(enumUserDataMap);
		}

	}

	public Map<String, String> getVehicleRefDetails() {
		return vehicleRefDetails;
	}

	public void setVehicleRefDetails(Map<String, String> vehicleRefDetails) {
		this.vehicleRefDetails = vehicleRefDetails;
	}

	public GWTestDataBean() {

	}

}
