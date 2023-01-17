package com.automation.core.enums;

public enum SymptomForm {
	TECHNICIAN("Technician"), CUSTOMER("Customer");
	
	private String name;

	public String getSymptomFormName() {
		return this.name;
	}

	private SymptomForm(String name) {
		this.name = name;
	}
}
