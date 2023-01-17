package com.automation.core.enums;

public enum VCATCheckbox {
	
		PAYMNTASSMPTNCHNGDNWTOW("Payment assumption"), TECHDISAGRDSYSRECMNDREPAIR("Technician Disagreed"),ARCDEALER("ARC Dealer"),ENGINEERINGREVIEW("Engineering Review"),
		MANUFACTURINGDATE("Manufacturing Date"), MILEAGE("Mileage"), MONTHSINSERVICE("Months in Service"),VIN("VIN"),DEALERSPECIFIC("Dealer Specific"),ADDITIONALRULES("Additional Rules"),
		INSERVICEDATEUNAVAILABLE("In-service date unavailable"),SERVICECONTRACTCOVERAGE("Service Contract Coverage"),PARTSWARRANTYCOVERAGE("Parts Warranty Coverage");

		
		private String name;

		public String getchkboxName() {
			return this.name;
		}

		private VCATCheckbox(String name) {
			this.name = name;
		}
		
	}

