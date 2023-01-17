package com.automation.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.cucumber.datatable.DataTable;

public class BDDDataTableUtil {

	private BDDDataTableUtil() {

	}

/*	private static Map<String, String> dataMap;

	public static Map<String, String> getDataMap(DataTable table) {
		if (Objects.isNull(dataMap))
			setDataMap(table);
		return dataMap;
	}

	public static void setDataMap(DataTable table) {

		BDDDataTableUtil.dataMap = getStringMap(BDDDataTableUtil.getMap(table));

	}

	public static Map<Object, Object> getMap(DataTable table) {

		return table.asMaps(String.class, String.class).get(0);

	}*/
	
	public static Map<String, String> getAsMap(DataTable table) {
		
			
		return getStringMap(table.asMaps(String.class, String.class).get(0));
	}
	

	public static Map<String, String> getStringMap(Map<Object, Object> map) {

		Map<String, String> stringMap = new HashMap<>();
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			String key = (String) entry.getKey();
			String value = Objects.isNull(map.get(key)) ?null:String.valueOf(map.get(key)).trim();
			stringMap.put(key, value);
		}
		return stringMap;
	}
	
	public static List<Map<String,String>> getAsListOfMap(DataTable table){
		
		List<Map<String,String>> returnList = new ArrayList<>();
		
		for(Map<Object, Object> map: table.asMaps(String.class, String.class)) {
			
			returnList.add(getStringMap(map));
			
		}
		
		return returnList;
		
		
	}
	
	public static List<String> getAsList(DataTable table){
		return table.asList();
	}

}
