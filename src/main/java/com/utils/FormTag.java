package com.utils;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.utils.APIUtil;
import com.automation.core.utils.RandomUtil;
import com.fields.strategies.NameStrategy;
import com.github.javafaker.Faker;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Builder
@Setter
@Getter
public class FormTag extends BaseAPIClass {

	@PodamStrategyValue(value = NameStrategy.class)
	private static String formTagName;

	public String getRequestBody() {

		JSONObject json = new JSONObject();
		json.put("formtagname", formTagName);

		return json.toString();
	}

	public String getRequestBody(String regEx) {

		JSONObject json = new JSONObject();
		json.put("formtagname", RandomUtil.getRandomStringWithRegExp(regEx));

		return json.toString();
	}

	public String getRequestBodyWithName(String name) {

		JSONObject json = new JSONObject();
		json.put("formtagname", name);

		return json.toString();
	}
	
	@Override
	public Response create() {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag";

		return APIUtil.post(url, null, getRequestBody("[a-zA-Z]{1}[a-zA-Z0-9-_]{19}"), 200);
	}
	
	public Response create(String name, int statusCode) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag";

		return APIUtil.post(url, null, getRequestBodyWithName(name), statusCode);
	}

	@Override
	public Response getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response update(String id, String reqBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response filter(String reqBody) {
		// TODO Auto-generated method stub
		return null;
	}

}
