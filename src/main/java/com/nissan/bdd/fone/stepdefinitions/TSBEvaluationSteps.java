package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;
import java.util.Map;
import org.testng.Assert;

import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.enums.UserData;
import com.nissan.reports.ExtentLogger;
import com.nissan.utils.ESMAPIUtil;
import com.nissan.utils.TSBAPIUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TSBEvaluationSteps {

	
	
	@Given("Evaluates TSBs")
	public void evaluates_ts_bs(io.cucumber.datatable.DataTable dataTable) {

		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
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
		getTestData().setTsbDataBean(TSBAPIUtil.evaluateLegacyTSBs(getTestData()));
		if (!getTestData().getTsbDataBean().getListTSB().isEmpty()) {
			int size = getTestData().getTsbDataBean().getListTSB().size();
			ExtentLogger.fail(String.format("Expected No tsb, actual ; %d tsb(s) evaluated", size));
			Assert.assertTrue(size == 0);
		}

	}
	
	@Given("Evaluates TSBs for {string}")
	public void evaluates_ts_bs_for(String string, io.cucumber.datatable.DataTable dataTable) {
		getTestData().addUserData(BDDDataTableUtil.getAsMap(dataTable));
		Map<UserData, String> map = getTestData().getUserData();
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
		getTestData().setTsbDataBean(TSBAPIUtil.evaluateLegacyTSBs(getTestData()));
		int size = getTestData().getTsbDataBean().getListTSB().size();
			if(size==1) {
				Assert.assertTrue(string.equalsIgnoreCase("1 TSB"));
			}else if(size>1) {
				Assert.assertTrue(string.equalsIgnoreCase("Multiple TSB"));
			}else {
				Assert.assertTrue(string.equalsIgnoreCase("No TSB"));
			}
			ExtentLogger.info(String.format("Expected %s, actual ; %d tsb(s) evaluated", string,size));
			
	}
	
	@Then("Verify {string} evaluated")
	public void verify_evaluated(String string) {
		getTestData().setTsbDataBean(TSBAPIUtil.evaluateLegacyTSBs(getTestData()));
		int size = getTestData().getTsbDataBean().getListTSB().size();
		if(size==1) {
			Validator.assertTrue(string.equalsIgnoreCase("1 TSB"),String.format("Expected %s, actual ; %d tsb(s) evaluated", string,size));
		}else if(size>1) {
			Validator.assertTrue(string.equalsIgnoreCase("Multiple TSB"),String.format("Expected %s, actual ; %d tsb(s) evaluated", string,size));
		}else {
			Validator.assertTrue(string.equalsIgnoreCase("No TSB"),String.format("Expected %s, actual ; %d tsb(s) evaluated", string,size));
		}
		ExtentLogger.info(String.format("Expected %s, actual ; %d tsb(s) evaluated", string,size));
	}
	
	@Then("Verify ESM is evaluated")
	public void verify_esm_is_evaluated() {
	   
		getTestData().setEsmDataBean(ESMAPIUtil.evaluateESM(getTestData()));
		int size = getTestData().getEsmDataBean().getListESM().size();
		Validator.assertTrue(size==0, "");
		System.out.println("test");
	}
	


}
