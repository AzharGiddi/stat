package com.api.response.pojoclasses;


import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JobTagGetAll {

	
	@SerializedName("data")
	public List<Data> data; 
	
	@SerializedName("error")
	public boolean error;
	
	public class Data {
		@SerializedName("_id")
		public String id;
		
		@SerializedName("formtagname")
		public String jobTagName;
		
		

	}
	
	
	
	
	
}
