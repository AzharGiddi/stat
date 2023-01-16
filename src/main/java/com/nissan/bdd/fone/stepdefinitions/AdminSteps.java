package com.nissan.bdd.fone.stepdefinitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.enums.UserData;
import com.nissan.enums.Roles;
import com.nissan.pages.AdminHomePage;
import com.nissan.utils.DApplicableWarrantyUtil;
import com.nissan.utils.DDcalUtil;
import com.nissan.utils.DVehicleReferenceDetailsUtil;
import com.nissan.utils.DVehicleWarrantyUtil;
import com.nissan.utils.ROAPIUtil;
import com.nissan.utils.ROUtil;

import static com.nissan.databeans.TestDataBeanManager.getTestData;


import io.cucumber.java.en.Given;

public class AdminSteps {

	private AdminHomePage adminHomePage;
	private AdminHomePage adminHomePage2;
	
	
	//DEMO
	
	public AdminSteps() {
		
		adminHomePage = new AdminHomePage();
		
	}
	
	@Given("Log into application as Admin user")
	public void log_into_application_as_admin_user() {
		adminHomePage.login(Roles.ADMIN);
		adminHomePage.waitForPageToLoad();
	}
	
	@Given("Admin creates RO with below set of details")
	public void admin_creates_ro_with_below_set_of_details(io.cucumber.datatable.DataTable dataTable) {
		getTestData().setUserData(BDDDataTableUtil.getAsMap(dataTable));
		//creating RO and adding it to test data bean
		getTestData().setRoNumber(ROUtil.createRO(getTestData().getUserData(), false, false));
	}

	
	@Given("Admin fetches vehicle reference details")
	public void admin_fetches_vehicle_reference_details() {
		//getTestData().setVehicleRefDetails(DVehicleReferenceDetailsUtil.getVehicleReferenceMap(getTestData().getUserData().get(InputFileField.VIN), false, false));
		getTestData().setVehicleRefDetails(DVehicleReferenceDetailsUtil.getVehicleReferenceMap(getTestData().getUserData().get(UserData.VIN)));
	}
	
	@Given("Admin fetches vehicle reference details for below vin")
	public void admin_fetches_vehicle_reference_details_for_below_vin(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		String vin = getTestData().getUserData().get(UserData.VIN);
		getTestData().setVehicleRefDetails(DVehicleReferenceDetailsUtil.getVehicleReferenceMap(vin));  
	}
	@Given("Admin logs out")
	public void admin_logs_out() {
		adminHomePage.logOut();
	}

	@Given("Admin fetches applicable warranty details")
	public void admin_fetches_applicable_warranty_details(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		String vIN = getTestData().getUserData().get(UserData.VIN);
		String component = getTestData().getUserData().get(UserData.COMPONENTDESCRIPTION);
		getTestData().setApplicableWarranty(DApplicableWarrantyUtil.getApplicableWarrantyMap(vIN, component));
		
	}
	
	@Given("Admin creates RO for good will request with below set of details")
	public void admin_creates_ro_for_good_will_request_with_below_set_of_details(io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(dataTable);
		//creating RO and adding it to test data bean
				getTestData().setRoNumber(ROAPIUtil.createGWRO());
		//getTestData().setdCal(DDcalUtil.getGWDcalAmount(getTestData().getUserData().get(UserData.DEALERCODE),getTestData().getrOOpenDate().replace("-", "")));
	}
	
	@Given("Admin fetches vehiclewarranty details")
	public void admin_fetches_vehiclewarranty_details() {
		
		
		String pfp = getTestData().getUserData().get(UserData.PFP);
		String opCode = getTestData().getUserData().get(UserData.OPCODE);
		String make = getTestData().getVehicleRefDetails().get("Make");
		//TODO need to update below line to get mileage from testdata object
		String mileage = getTestData().getMileage();
		String modelYear = getTestData().getVehicleRefDetails().get("ModelYearVersionNumber");
		String vIN = getTestData().getUserData().get(UserData.VIN);
		String rOOpenDate = getTestData().getrOOpenDate().replace("-", "");
		Map<String, String> params = new HashMap<>();
		params.put("Make", make);
		params.put("ModelYear", modelYear);
		params.put("Mileage", mileage);
		params.put("OPCODE", opCode);
		params.put("PFP", pfp);
		params.put("VIN", vIN);
		params.put("ROOpenDate", rOOpenDate);
		getTestData().setVehicleWarranty(DVehicleWarrantyUtil.getVehicleWarrantyMap(params));
		
	}
	
	
	
}
