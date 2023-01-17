package com.automation.core.enums;

public enum UserData {

	COMPONENT("Component"), DEALERCODE("Dealer Code"), VIN("VIN"), MILEAGE("Mileage"), ROXMLPATH(
			"ROXml Path"), RULESETVERSION("RuleSet Version"), WARRANTY("Warranty"), MANUFACTUREDATE(
					"Manufacture Date"), CUSTOMERSYMPTOMCHECKBOXES(
							"Customer Symptom Checkboxes"), CUSTOMERSYMPTOMDROPDOWNS(
									"Customer Symptom DropDowns"), CUSTOMEROTHERSYMPTOMS(
											"Customer OtherSymptoms"), PAYMENTASSUMPTION(
													"Payment Assumption"), TECHNICIANSYMPTOMCHECKBOXES(
															"Technician Symptom Checkboxes"), TECHNICIANSYMPTOMDROPDOWNS(
																	"Technician Symptom DropDowns"), TECHNICIANOTHERSYMPTOMS(
																			"Technician OtherSymptoms"), CCCPARTFILEDFIRST(
																					"CCC-Part Failed First"), CCCWHYPARTFAILED(
																							"CCC-Why Part Failed"), CCCTECHNICIANDISAGREEDREPAIR(
																									"CCC-Technician Disagreed Repair"), CCCREPAIRRECOMMENDATION(
																											"CCC-Repair Recommendation"), CCCTECHNICIANREPAIRRECOMMENDATION(
																													"CCC-Technician Repair Recommendation"), CCCREPAIRJUSTIFICATIONCOMMENTS(
																															"CCC-Repair Justification/Comments"), VCATSYSTEMMAKETHERIGHTRECOMMENDATION(
																																	"VCAT-System make the right recommendation"), VCATAGREEWITHTHETECHNICIANRECOMMENDATION(
																																			"VCAT-Agree with the technician recommendation"), VCATWANTTOOVERRIDETHERECOMMENDATION(
																																					"VCAT-Want to override the recommendation"), VCATSUPPORTNOTES(
																																							"VCAT-Support Notes"), PCCANYOILLEAKSPRESENT(
																																									"Any Oil Leaks Present"), PCCHASDYEBEENINSTALLEDTOISOLATELEAK(
																																											"Has dye been installed to isolate leak"), PCCLISTALLIFANYVEHICLEMODIFICATIONS(
																																													"List all if any vehicle modifications"), PCCATTACHMENTPATH(
																																															"Attachment Path"), PCCTECHLINECONTACTED(
																																																	"techline contacted"), PCCWHENWASTECHLINECONTACTED(
																																																			"when was tech line contacted"), SYMPTOMCODE(
																																																					"Symptom_Code"), DIAGNOSISCODE(
																																																							"Diagnosis_Code"), COVERAGECODE(
																																																									"Coverage_Code"), COMPONENTDESCRIPTION(
																																																											"Component_Description"), PFP(
																																																													"PFP"), OPCODE(
																																																															"OPCODE"), RODATEBOUNDARY(
																																																																	"Date"), COVERAGETYPE(
																																																																			"Coverage_Type"), STATUSCODE(
																																																																					"Status_Code"), ROL(
																																																																							"ROL"), EXPENSEAMOUNT(
																																																																									"Expense_Amount"), LABORAMOUNT(
																																																																											"Labor_Amount"), PARTAMOUNT(
																																																																													"Part_Amount"), WLEXPENSEAMOUNT(
																																																																															"WL_Expense_Amount"), WLLABORAMOUNT(
																																																																																	"WL_Labor_Amount"), WLPARTAMOUNT(
																																																																																			"WL_Part_Amount"), NISSANAMOUNTGTDCAL(
																																																																																					"NissanAmountGTDcal"), WLGTDCAL(
																																																																																							"WLGTDcal"), WLGTNISSANAMOUNT(
																																																																																									"WLGTNissanAmount"), ISRESTRICTEDPART(
																																																																																											"IsRestrictedPart"), VEHICLECOMPONENT(
																																																																																													"Vehicle_Component"),NISSANCPNAMTGTNISSANAMT("NissanCpnAmtGTNissanAmt"),GENERALSYMPTOMS("General Symptoms"),SYSTEMSCOMPONENTS("Systems Components"),OCCURSWHEN("Occurs_when"),OCCURSWHERE("Occurs_Where"),OCCURSCONDITION("Occurs_Condition"),WHENDIDTHECONCERNBEGIN("when_did_the_concern_begin?"),FREQUENCY("Frequency"),VEHICLEDOESNOTMOVE("Vehicle_does_not_move"),VIBRATION("Vibration"), ENGINESTALLSWHEN("Engine_stalls_when"),POORSHIFTQUALITY("Poor_Shift_Quality"),NOISE("Noise"),TESTDATA("Test Data");

	private String field;

	public String getFieldName() {
		return this.field;
	}

	private UserData(String field) {
		this.field = field;
	}

}
