package com.utils;

import com.google.gson.annotations.SerializedName;

public class LoginAPI {
	
	@SerializedName("data")
	public Data data; 
	
	@SerializedName("error")
	public boolean error;
	
	public class Data {
		@SerializedName("accessToken")
		public String accessToken;
	}
	

}
