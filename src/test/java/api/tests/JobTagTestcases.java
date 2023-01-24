package api.tests;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.response.pojoclasses.FormTagGetAll;
import com.api.response.pojoclasses.JobTagGetAll;
import com.automation.core.utils.PoiExcelUtil;
import com.automation.core.utils.RandomUtil;
import com.google.gson.Gson;
import com.utils.FormTag;
import com.utils.JobTag;

import io.restassured.response.Response;

public class JobTagTestcases {

	public String string;

	@DataProvider(name = "FormTagData")
	public Object[][] getData() {

		String file = "/data/excelfiles/FormTagCreate.xlsx";
		String sheetName = "";

		return PoiExcelUtil.getExcelDataAsMap(file, sheetName);
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
	/*
	 * @Test(dataProvider = "FormTagData") public void create(Map<String, Object>
	 * data) {
	 * 
	 * FormTag formTag = new FormTag(); String inputData =
	 * String.valueOf(data.get("FormTagName")); String formTagName =
	 * Objects.isNull(inputData) ? "" : inputData; String errorMessage =
	 * String.valueOf(data.get("ErrorMessage")); // int status = ((Double) //
	 * Double.parseDouble(String.valueOf(data.get("Status")))).intValue(); int
	 * status = ((Double) data.get("Status")).intValue(); Response response =
	 * formTag.create(formTagName); if (status == 200) {
	 * response.then().statusCode(status).body("error",
	 * Matchers.equalTo(false)).body("data.formtagname",
	 * Matchers.equalTo(formTagName.toLowerCase())); } else if (status == 400) { //
	 * String errormessage = String.format("tag with this formtagname /%s/ already
	 * // exists.", formTagName); response.then().statusCode(status).body("error",
	 * Matchers.equalTo(true)) .body("errormessage",
	 * Matchers.equalTo(errorMessage)).body("data", Matchers.equalTo(false)); }
	 * System.out.println(response.asPrettyString());
	 * 
	 * }
	 */

	
	  @Test(invocationCount = 10) 
	  public void create() {
	  
	  JobTag jobTag = new JobTag(); 
	  String jobTagName = "JobTag_"+RandomUtil.getRandomStringWithRegExp("[a-zA-Z0-9-_]{12}"); //
	  int status = 201; 
	  Response response = jobTag.create(jobTagName);
	  System.out.println(response.asPrettyString());
	  response.then().statusCode(status).body("error", Matchers.equalTo(false))
	  .body("data.formtagname", Matchers.equalTo(jobTagName.toLowerCase()));
	  System.out.println(response.asPrettyString());
	  
	  }
	 
	// create existing, should throw error
	@Test
	public void createExisting() {

		JobTag jobTag = new JobTag(); 
		String jobTagName = "Testing_Auto_1";
		String errormessage = String.format("tag with this formtagname /%s/ already exists.", jobTagName);
		Response response = jobTag.create(jobTagName);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));

		// System.out.println(response.asPrettyString());

	}

	@Test
	public void createEmpty() {

		JobTag jobTag = new JobTag();
		String jobTagName = "";
		String errormessage = "\"formtagname\" is not allowed to be empty";
		Response response = jobTag.create(jobTagName);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));

	}

	// delete formTag
	@Test
	public void deleteFomtag() {

		JobTag jobTag = new JobTag();
		String id = "63bb9b5a438bfcc97238ae82";
		Response response = jobTag.delete(id);
		response.then().body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}

	// delete same formTag 2 times
	@Test
	public void deleteFomtagError() {

		JobTag jobTag = new JobTag();
		Response getAll = jobTag.getAll();
		JobTagGetAll jobTagAll = new Gson().fromJson(getAll.asString(), JobTagGetAll.class);
		int size = jobTagAll.data.size();
		System.out.println("size is " + size);
		String id = jobTagAll.data.get(0).id;
		System.out.println("deleteing:" + id);
		Response response = jobTag.delete(id);
		String errormessage = String.format("formtag with this id /%s/ does not exists.", id);
		response.then().body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));
		System.out.println(response.asPrettyString());
		response = jobTag.delete(id);
		response.then().body("error", Matchers.equalTo(true)).body("errormessage", Matchers.equalTo(errormessage))
				.body("data", Matchers.equalTo(false));
		System.out.println(response.asPrettyString());

	}

	@Test
	public void getAll() {

		JobTag jobTag = new JobTag();
		Response response = jobTag.getAll();
		response.then().body("error", Matchers.equalTo(false));
		System.out.println(response.asPrettyString());
		JobTagGetAll jobTagAll = new Gson().fromJson(response.asString(), JobTagGetAll.class);
		int size = jobTagAll.data.size();
		String id = jobTagAll.data.get(new Random().nextInt(size)).id;
		System.out.println(id);

	}

	@Test
	public void getById() {

		JobTag jobTag = new JobTag();
		Response getAll = jobTag.getAll();
		JobTagGetAll jobTagAll = new Gson().fromJson(getAll.asString(), JobTagGetAll.class);
		String id = jobTagAll.data.get(0).id;
		Response response = jobTag.getById(id);
		System.out.println(response.asPrettyString());

	}

	// update with different name 
	@Test
	public void update() {

		JobTag jobTag = new JobTag();
		String id = jobTag.getId();
		String jobTagName = "Formtag_124";
		Response response = jobTag.update(id, jobTag.getRequestBodyWithName(jobTagName));
		System.out.println(response.asPrettyString());
		response.then().statusCode(201).body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}
	
	//update with same name
	@Test
	public void updateSameName() {

		JobTag jobTag = new JobTag();
		String id = jobTag.getId();
		String jobTagName = "Formtag_124";
		Response response = jobTag.update(id, jobTag.getRequestBodyWithName(jobTagName));
		System.out.println(response.asPrettyString());
		response.then().statusCode(201).body("error", Matchers.equalTo(false)).body("data", Matchers.equalTo(true));

	}
	
	//update with existing name
		@Test
		public void updateExistingName() {

			JobTag jobTag = new JobTag();
			String id = jobTag.getId();
			String jobTagName = "TestNew114_-";
			String errormessage = String.format("Tag with this Form Tag Name /%s/ already exists.",jobTagName);
			Response response = jobTag.update(id, jobTag.getRequestBodyWithName(jobTagName));
			//System.out.println(response.asPrettyString());
			response.then().statusCode(400).body("error", Matchers.equalTo(true))
			.body("errormessage", Matchers.equalTo(errormessage))
			.body("data", Matchers.equalTo(false));

		}
	
	

}
