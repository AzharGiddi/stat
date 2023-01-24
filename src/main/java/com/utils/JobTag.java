package com.utils;

import java.util.Random;

import org.json.JSONObject;

import com.api.response.pojoclasses.JobTagGetAll;
import com.api.response.pojoclasses.JobTagGetAll.Data;
import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.utils.APIUtil;
import com.automation.core.utils.RandomUtil;
import com.fields.strategies.NameStrategy;
import com.google.gson.Gson;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamStrategyValue;


@Builder
@Setter
@Getter
public class JobTag extends BaseAPIClass {

	@PodamStrategyValue(value = NameStrategy.class)
	private static String jobTagName;

	public String getRequestBody() {

		JSONObject json = new JSONObject();
		json.put("jobtagname", jobTagName);

		return json.toString();
	}

	public String getRequestBody(String regEx) {

		JSONObject json = new JSONObject();
		json.put("jobtagname", RandomUtil.getRandomStringWithRegExp(regEx));

		return json.toString();
	}

	public String getRequestBodyWithName(String name) {

		JSONObject json = new JSONObject();
		json.put("jobtagname", name);

		return json.toString();
	}
	
	
	
	@Override
	public Response create() {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag/create";

		return APIUtil.post(url, null, "jobtag_"+getRequestBody("[a-zA-Z0-9-_]{12}"));
	}
	
	public Response create(String name) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag/create";

		return APIUtil.post(url, null, getRequestBodyWithName(name));
	}

	@Override
	public Response getAll() {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag";
		return APIUtil.get(url, null);
	}
	
    public String getId() {
		
	JobTagGetAll jobtagAll = new Gson().fromJson(getAll().asString(), JobTagGetAll.class);
	int size = jobtagAll.data.size();
	return jobtagAll.data.get(new Random().nextInt(size)).id;
			  
	}
    
    public String getjobtagNameForId(String id) {
		
    	JobTagGetAll jobtagAll = new Gson().fromJson(getAll().asString(), JobTagGetAll.class);
    		String jobtagName="";
    	for(Data data:jobtagAll.data) {
    		if(data.jobTagName.equals(jobTagName)) {
    			jobtagName=data.jobTagName;
    		}
    		return jobTagName;
    	}
    	return jobTagName;
    			  
    	}

	@Override
	public Response getById(String id) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag/"+id;
		return APIUtil.get(url, null);
	}

	@Override
	public Response update(String id, String reqBody) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag/"+id;
		return APIUtil.put(url, null,reqBody);
	}

	@Override
	public Response delete(String id) {
		String url = ConfigurationManager.getBundle().getString("base.uri") + "setting/cmms/jobtag/"+id;
		return APIUtil.delete(url, null);
	
	}
	
	
	@Override
	public Response filter(String reqBody) {
		// TODO Auto-generated method stub
		return null;
	}

}
