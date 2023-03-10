package api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.response.pojoclasses.FormTagGetAll;
import com.automation.core.utils.APIUtil;
import com.automation.core.utils.PoiExcelUtil;
import com.google.gson.Gson;
import com.utils.FormTag;
import com.utils.LoginAPI;

import io.restassured.response.Response;

public class APITestcases {

	public String string;

	@DataProvider(name = "FormTagData")
	public Object[][] getData() {

		String file = "/data/excelfiles/FormTagCreate.xlsx";
		String sheetName = "";

		return PoiExcelUtil.getExcelDataAsMap(file, sheetName);
	}

	@Test
	public void test(Map<String, String> data) {

		String uri = "https://o2power.api.kitchen.sgrids.io/setting/cmms/jobstatus";
		Map<String, String> params = new HashMap<>();
		String filePath = "./data/Login.json";
		File reqBody = new File(filePath);
		// String reqBody =
		// "{\"email\":\"azhar.b@sgrids.io\",\"password\":\"ArmaX1234\"}";
		// JSONObject obj = new JSONObject(reqBody);
		// String email = obj.getString("email");
		Response response = APIUtil.get(uri, params);
		System.out.println(response.asString());

		/*
		 * Gson gson = new Gson(); LoginAPI loginApi =
		 * gson.fromJson(response.asString(), LoginAPI.class);
		 * 
		 * System.out.println(loginApi.data.accessToken);
		 */
		// setting/cmms/jobstatus
	}

	@Test
	public void test2() {

		/*
		 * String s = FormTagRequestBody.getRequestBody(); System.out.println(s);
		 */

		char[] charr = { '-', '_', 'a', 'z' };
		String s = RandomStringUtils.random(10, 0, 0, false, false, charr);
		System.out.println(s);

	}

	// create new Name
	@Test(dataProvider = "FormTagData")
	public void create(Map<String, Object> data) {

		FormTag formTag = new FormTag();
		String inputData = String.valueOf(data.get("FormTagName"));
		String formTagName = Objects.isNull(inputData) ? "" : inputData;
		String errorMessage = String.valueOf(data.get("ErrorMessage"));
		// int status = ((Double)
		// Double.parseDouble(String.valueOf(data.get("Status")))).intValue();
		int status = ((Double) data.get("Status")).intValue();
		Response response = formTag.create(formTagName);
		if (status == 200) {
			response.then().statusCode(status).body("error", Matchers.equalTo(false)).body("data.formtagname",
					Matchers.equalTo(formTagName.toLowerCase()));
		} else if (status == 400) {
			// String errormessage = String.format("tag with this formtagname /%s/ already
			// exists.", formTagName);
			response.then().statusCode(status).body("error", Matchers.equalTo(true))
					.body("errormessage", Matchers.equalTo(errorMessage)).body("data", Matchers.equalTo(false));
		}
		System.out.println(response.asPrettyString());

	}

	/*
	 * @Test public void create() {
	 * 
	 * FormTag formTag = new FormTag(); String formTagName = "Test 3"; //int status
	 * = ((Double)
	 * Double.parseDouble(String.valueOf(data.get("Status")))).intValue(); int
	 * status = 200; Response response = formTag.create(formTagName);
	 * System.out.println(response.asPrettyString());
	 * response.then().statusCode(status).body("error", Matchers.equalTo(false))
	 * .body("data.formtagname", Matchers.equalTo(formTagName.toLowerCase()))
	 * .body("data._id", Matchers.hasSize(24));
	 * System.out.println(response.asPrettyString());
	 * 
	 * }
	 */
	// create existing, should throw error
	@Test
	public void createExisting() {

		FormTag formTag = new FormTag();
		String formTagName = "Testing_Auto_1";
		String errormessage = String.format("tag with this formtagname /%s/ already exists.", formTagName);
		Response response = formTag.create(formTagName);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));

		// System.out.println(response.asPrettyString());

	}

	@Test
	public void createEmpty() {

		FormTag formTag = new FormTag();
		String formTagName = "";
		String errormessage = "\"formtagname\" is not allowed to be empty";
		Response response = formTag.create(formTagName);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));

	}

	// delete formTag
	@Test
	public void deleteFomtag() {

		FormTag formTag = new FormTag();
		String id = "63bb9b5a438bfcc97238ae82";
		Response response = formTag.delete(id);
		response.then().body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}

	// delete same formTag 2 times
	@Test
	public void deleteFomtagError() {

		FormTag formTag = new FormTag();
		Response getAll = formTag.getAll();
		FormTagGetAll formTagAll = new Gson().fromJson(getAll.asString(), FormTagGetAll.class);
		int size = formTagAll.data.size();
		System.out.println("size is " + size);
		String id = formTagAll.data.get(0).id;
		System.out.println("deleteing:" + id);
		Response response = formTag.delete(id);
		String errormessage = String.format("formtag with this id /%s/ does not exists.", id);
		response.then().body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));
		System.out.println(response.asPrettyString());
		response = formTag.delete(id);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));
		System.out.println(response.asPrettyString());

	}

	@Test
	public void getAll() {

		FormTag formTag = new FormTag();
		Response response = formTag.getAll();
		response.then().body("error", Matchers.equalTo(false));
		System.out.println(response.asPrettyString());
		FormTagGetAll formTagAll = new Gson().fromJson(response.asString(), FormTagGetAll.class);
		int size = formTagAll.data.size();
		String id = formTagAll.data.get(new Random().nextInt(size)).id;
		System.out.println(id);

	}

	@Test
	public void getById() {

		FormTag formTag = new FormTag();
		Response getAll = formTag.getAll();
		FormTagGetAll formTagAll = new Gson().fromJson(getAll.asString(), FormTagGetAll.class);
		String id = formTagAll.data.get(0).id;
		Response response = formTag.getById(id);
		System.out.println(response.asPrettyString());

	}

	// update with different name 
	@Test
	public void update() {

		FormTag formTag = new FormTag();
		String id = formTag.getId();
		String formTagName = "Formtag_124";
		Response response = formTag.update(id, formTag.getRequestBodyWithName(formTagName));
		System.out.println(response.asPrettyString());
		response.then().statusCode(201).body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}
	
	//update with same name
	@Test
	public void updateSameName() {

		FormTag formTag = new FormTag();
		String id = formTag.getId();
		String formTagName = "Formtag_124";
		Response response = formTag.update(id, formTag.getRequestBodyWithName(formTagName));
		System.out.println(response.asPrettyString());
		response.then().statusCode(201).body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}
	
	//update with existing name
		@Test
		public void updateExistingName() {

			FormTag formTag = new FormTag();
			String id = formTag.getId();
			String formTagName = "TestNew114_-";
			String errormessage = String.format("Tag with this Form Tag Name /%s/ already exists.",formTagName);
			Response response = formTag.update(id, formTag.getRequestBodyWithName(formTagName));
			//System.out.println(response.asPrettyString());
			response.then().statusCode(400).body("error", Matchers.equalTo(true))
			.body("errormessage", Matchers.equalTo(errormessage))
			.body("data", Matchers.equalTo(false));

		}
	
	

}
