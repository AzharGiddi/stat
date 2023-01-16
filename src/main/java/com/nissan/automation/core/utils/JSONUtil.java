package com.nissan.automation.core.utils;

import static com.nissan.automation.core.utils.ClassUtil.isAssignableFrom;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.nissan.automation.core.utils.ClassUtil;



public class JSONUtil {
		private static final Log logger = LogFactory.getLog(JSONUtil.class);

		
		/**
		 * 
		 * @param str
		 * @return
		 */
		public static boolean isValidJsonString(String str) {
			try {
				new JSONObject(str);
				return true;
			} catch (JSONException e) {
				return false;
			}

		}
		
		/**
		 * 
		 * @param str
		 * @return
		 */
		public static JSONArray getJsonArrayOrNull(String str) {
			try {
				return new JSONArray(str);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

		}

		/**
		 * 
		 * @param csv
		 * @return
		 */
		public static JSONArray getJsonArrayFromCsvOrNull(String csv){
			try {
				return CDL.rowToJSONArray(new JSONTokener(csv));
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * This is specifically to work with GSON. for example even "some string" is
		 * not valid json but gson can considers as valid json
		 * 
		 * @param str
		 * @return
		 */
		public static boolean isValidGsonString(String str) {
			try {
				new JsonParser().parse(str);
				return true;
			} catch (JsonParseException e) {
				return false;
			}

		}

		/**
		 * @param obj
		 * @return
		 */
		static Map<String, Object> toMap(JSONObject obj) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (obj != null) {
				Iterator<?> iter = obj.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					try {
						map.put(key, obj.get(key));
					} catch (JSONException e) {

					}
				}
			}
			return map;
		}
		
		/**
		 * 
		 * @param json
		 * @return
		 * @throws JSONException
		 */
		public static Map<String, Object> toMap(String json) throws JSONException {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) toObject(json, Map.class);
			return map;
		}

		/**
		 * @param str
		 * @return JsonElement or null if not a valid json
		 */
		public static JsonElement getGsonElement(String str) {
			try {
				return new JsonParser().parse(str);
			} catch (JsonParseException e) {
				return null;
			}
		}

		/**
		 * 
		 * @param file
		 * @param obj
		 */
		public static <T> void writeJsonObjectToFile(final String file, final T obj) {

			File f = new File(file);
			try {
				Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();
				String jsonStr = gson.toJson(obj, obj.getClass());

				FileUtil.writeStringToFile(f, jsonStr, "UTF-8");
			} catch (Throwable e) {
				System.err.println("Unable to write : " + obj.getClass().getCanonicalName() + " in file: " + file + " :"
						+ e.getMessage());
				logger.error("Unable to write : " + obj.getClass().getCanonicalName() + " in file: " + file + " :"
						+ e.getMessage());
			}
		}

		
		 
			
		public static String toString(Object o) {
			if (String.class.isAssignableFrom(o.getClass()) || o.getClass().isPrimitive()) {
				return String.valueOf(o);
			}
			GsonBuilder builder = new GsonBuilder().setLenient().serializeNulls();
			return builder.create().toJson(o);
		}
		
		public static <T> T toObject(String s, Class<T> t) {
			GsonBuilder builder = new GsonBuilder().setLenient().serializeNulls();
			return t.cast(toObject(s, t, builder));
		}
		
		public static Object toObject(String s, Type t, GsonBuilder builder) {
			Gson gson = builder.registerTypeAdapter(ObjectWrapper.class, new GsonDeserializerObjectWrapper(t)).create();
			try {
				ObjectWrapper val = gson.fromJson(s, ObjectWrapper.class);
				return val.getObject();// gson.fromJson(s,t);
			} catch (JsonSyntaxException e) {
				if (e.getCause() instanceof MalformedJsonException) {
					ObjectWrapper val = gson.fromJson("\"" + StringEscapeUtils.escapeJava(s) + "\"", ObjectWrapper.class);
					return val.getObject();
				}
				throw e;
			}catch (JsonIOException e) {
				return s;
			}
			catch (NullPointerException e) {
				return s;
			}
		}
		
		public static Object toObject(String s) {
			return toObject(s, Object.class);
		}
		
		

		
		
	}
	


