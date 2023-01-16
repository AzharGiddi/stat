package com.nissan.bdd.fone.stepdefinitions;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import java.util.List;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.utils.ROAPIUtil;

import io.cucumber.java.en.Given;

public class ROCreationSteps {
	
	
	
	@Given("Create RO with below set of details")
	public void create_ro_with_below_set_of_details(io.cucumber.datatable.DataTable dataTable) {
		
		getTestData().addUserData(dataTable);
		getTestData().setRoNumber(ROAPIUtil.createRO(getTestData()));
		
	}
	
	@Given("Create a repair order with {string} and {string} for following VINs")
	public void create_a_repair_order_with_and_for_following_vi_ns(String dealerCode, String roNumber, io.cucumber.datatable.DataTable dataTable) {
		
		List<String> vinList= BDDDataTableUtil.getAsList(dataTable);
		
		for(String vin:vinList) {
			ROAPIUtil.createROForHRK(dealerCode,roNumber,vin);
		}
	}
}
