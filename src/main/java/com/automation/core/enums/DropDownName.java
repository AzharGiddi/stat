package com.automation.core.enums;

public enum DropDownName {

	VEHICLEDOESNOTMOVE("Vehicle"), VIBRATION("Vibration"), ENGINESTALLSWHEN(
			"EngineStallsWhen"), WHENDIDTHECONCERNBEGIN(
					"CustomerConcern"), FREQUENCY("Frequency"), PAYMENTASSUMPTION("PaymentType"), EVENT("Event"),NOMATCHFOUND("No Match Found");

	private String name;

	public String getDropDownName() {
		return this.name;
	}

	private DropDownName(String name) {
		this.name = name;
	}

}
