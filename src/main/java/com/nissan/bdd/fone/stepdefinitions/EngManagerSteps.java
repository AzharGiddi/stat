package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.enums.UserData;
import com.nissan.enums.Roles;
import com.nissan.pages.EngManagerPage;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.TSBAPIUtil;

import io.cucumber.java.en.Given;

public class EngManagerSteps {

	private EngManagerPage engManager;

	public EngManagerSteps() {

		engManager = new EngManagerPage();
	}

	@Given("Login to application as Engineering Manager")
	public void login_to_application_as_engineering_manager() {
		engManager.login(Roles.ENGINEERINGMANAGER);
	}

	@Given("Engineering Manager evaluates TSBs")
	public void engineering_manager_evaluates_ts_bs(io.cucumber.datatable.DataTable dataTable) {
		
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
		String component = map.get(UserData.COMPONENT);
		String checkBoxString = map.get(UserData.CUSTOMERSYMPTOMCHECKBOXES);
		String dropDownString = map.get(UserData.CUSTOMERSYMPTOMDROPDOWNS);
		String comments = map.get(UserData.CUSTOMEROTHERSYMPTOMS);
		String paymentAssumption = map.get(UserData.PAYMENTASSUMPTION);
		String techCheckBoxString = map.get(UserData.CUSTOMERSYMPTOMCHECKBOXES);
		String techDropDownString = map.get(UserData.CUSTOMERSYMPTOMDROPDOWNS);
		String techComments = map.get(UserData.CUSTOMEROTHERSYMPTOMS);
		getTestData().setPaymentAssumption(paymentAssumption);
		getTestData().setCustomerSymptom(checkBoxString, dropDownString, comments, paymentAssumption);
		getTestData().settechnicianSymptom(techCheckBoxString, techDropDownString, techComments, "");
		List<String> custSymptomData = getTestData().getCustomerSymptom().getSymptomSet();
		List<String> techSymptomData = getTestData().getTechnicianSymptom().getSymptomSet();
		Map<String,String> vehref = getTestData().getVehicleRefDetails();
		//TSBAPIUtil.evaluateTSB(getTestData());
		getTestData().setTsbDataBean(custSymptomData,
				techSymptomData, vehref,component, false, false);
		if (!getTestData().getTsbDataBean().getListTSB().isEmpty()) {
			int size = getTestData().getTsbDataBean().getListTSB().size();
			Assert.assertTrue(size == 1,String.format("Expected 1 tsb, actual ; %d tsb(s) evaluated", size));
		}
		

	}
	
	

	@Given("Engineering Manager fetches NAA rules")
	public void engineering_manager_fetches_naa_rules() {
		getTestData().setnAARulesDataBean(getTestData().getUserData().get(UserData.DEALERCODE),
				getTestData().getUserData().get(UserData.COMPONENT), getTestData().getPaymentAssumption(),
				getTestData().getVehicleRefDetails(), false,
				false, false,
				false);
		
	}

	@Given("Engineering Manager logs out")
	public void engineering_manager_logs_out() {
		engManager.logOut();
	}

}
