package com.utils;

import java.util.Random;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.response.pojoclasses.FormTagGetAll;
import com.api.response.pojoclasses.FormTagGetAll.Data;
import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.utils.APIUtil;
import com.automation.core.utils.RandomUtil;
import com.fields.strategies.NameStrategy;
import com.github.javafaker.Faker;
import com.google.gson.Gson;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.common.PodamStrategyValue;


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
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag/create";

		return APIUtil.post(url, null, "FormTag_"+getRequestBody("[a-zA-Z0-9-_]{12}"));
	}
	
	public Response create(String name) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag/create";

		return APIUtil.post(url, null, getRequestBodyWithName(name));
	}

	@Override
	public Response getAll() {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag";
		return APIUtil.get(url, null);
	}
	
    public String getId() {
		
	FormTagGetAll formTagAll = new Gson().fromJson(getAll().asString(), FormTagGetAll.class);
	int size = formTagAll.data.size();
	return formTagAll.data.get(new Random().nextInt(size)).id;
			  
	}
    
    public String getFormTagNameForId(String id) {
		
    	FormTagGetAll formTagAll = new Gson().fromJson(getAll().asString(), FormTagGetAll.class);
    		String formtagName="";
    	for(Data data:formTagAll.data) {
    		if(data.formTagName.equals(formTagName)) {
    			formtagName=data.formTagName;
    		}
    		return formTagName;
    	}
    	return formTagName;
    			  
    	}

	@Override
	public Response getById(String id) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag/"+id;
		return APIUtil.get(url, null);
	}

	@Override
	public Response update(String id, String reqBody) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag/"+id;
		return APIUtil.put(url, null,reqBody);
	}

	@Override
	public Response delete(String id) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/formtag/"+id;
		return APIUtil.delete(url, null);
	
	}
	
	
	@Override
	public Response filter(String reqBody) {
		// TODO Auto-generated method stub
		return null;
	}

}
