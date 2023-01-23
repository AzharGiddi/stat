package api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.automation.core.utils.APIUtil;
import com.google.gson.Gson;
import com.utils.FormTag;
import com.utils.LoginAPI;

import io.restassured.response.Response;

public class APITestcases {
	
	
	
	public String string;
	@Test
	public void test() {
		
		String uri= "https://o2power.api.kitchen.sgrids.io/setting/cmms/jobstatus";
		Map<String,String> params = new HashMap<>();
		String filePath = "./data/Login.json";
		File reqBody = new File(filePath);
		//String reqBody = "{\"email\":\"azhar.b@sgrids.io\",\"password\":\"ArmaX1234\"}";
		//JSONObject obj = new JSONObject(reqBody);
		//String email = obj.getString("email");
		Response response = APIUtil.get(uri, params,200);
		System.out.println(response.asString());
		
		/*
		 * Gson gson = new Gson(); LoginAPI loginApi =
		 * gson.fromJson(response.asString(), LoginAPI.class);
		 * 
		 * System.out.println(loginApi.data.accessToken);
		 */
		//setting/cmms/jobstatus
	}
	
	@Test
	public void test2() {
		
		/*
		 * String s = FormTagRequestBody.getRequestBody(); System.out.println(s);
		 */
		
		char[] charr = {'-','_','a','z'};
		String s = RandomStringUtils.random(10, 0, 0, false, false,charr);
		System.out.println(s);
		
		
	}

	
	
}
