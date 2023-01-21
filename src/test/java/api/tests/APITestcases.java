package api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.automation.core.utils.APIUtil;
import com.google.gson.Gson;
import com.utils.LoginAPI;

import io.restassured.response.Response;

public class APITestcases {
	
	@Test
	public void test() {
		
		String uri= "https://o2power.api.kitchen.sgrids.io/auth/login";
		Map<String,String> params = new HashMap<>();
		String filePath = System.getProperty("user.dir")+"\\data\\Loginrequestbody.json";
		File reqBody = new File(filePath);
		//String reqBody = "{\"email\":\"azhar.b@sgrids.io\",\"password\":\"ArmaX1234\"}";
		Response response = APIUtil.post(uri, params, reqBody,400);
		System.out.println(response.asString());
		
		//Gson gson = new Gson();
		//LoginAPI loginApi = gson.fromJson(response.asString(), LoginAPI.class);
	
		//System.out.println(loginApi.data.accessToken);
		
	}

	public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
	
}
