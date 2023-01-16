package com.nissan.bdd.fone.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.Objects;

import com.nissan.WebElement.Validator;
import com.nissan.enums.UserData;
import com.nissan.utils.NAARulesAPIUtil;

public class NAARulesEvaluationSteps {

	
	@Given("Evaluate NAA Rules")
	public void evaluate_naa_rules() {
	    
		getTestData().setnAARulesDataBean(NAARulesAPIUtil.evaluateNAARules(getTestData()), false, false);

		
	}
	
	@Then("Evaluate NAA Rules and verify routeToVcat is {string}")
	public void evaluate_naa_rules_and_verify_route_to_vcat_is(String string) {
		boolean tecDisregardRepair = Objects.isNull(getTestData().getUserData().get(UserData.CCCTECHNICIANDISAGREEDREPAIR))?Boolean.parseBoolean("false"):Boolean.parseBoolean(getTestData().getUserData().get(UserData.CCCTECHNICIANDISAGREEDREPAIR));
		getTestData().setnAARulesDataBean(NAARulesAPIUtil.evaluateNAARules(getTestData()), false,tecDisregardRepair );
		boolean expectedRouteToVcat = Boolean.parseBoolean(string);
		boolean actualRouteToVcat = getTestData().getnAARulesDataBean().isRouteToVCAT() 
				|| (getTestData().getPaymentAssumption().equals("Factory Warranty") && !getTestData().getUserData().get(UserData.CCCWHYPARTFAILED).startsWith("Defect in factory")) 
				|| getTestData().getPaymentAssumption().equals("Goodwill") 
				|| getTestData().isQAReferToPCC() 
				|| tecDisregardRepair;
		Validator.assertTrue(actualRouteToVcat==expectedRouteToVcat ,"Expected routeToVCAT="+expectedRouteToVcat+", Actual routeToVCAT="+actualRouteToVcat);
		
	}
	
}
