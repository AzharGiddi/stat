package com.utils;

import io.restassured.response.Response;

public abstract class BaseAPIClass {

	
	public abstract Response create();
	
	public abstract Response getAll();
	
	public abstract Response getById(String id);
		
	public abstract Response update(String id, String reqBody);
	
	public abstract Response delete(String id);
	
	public abstract Response filter(String reqBody);
	
	
	
}
