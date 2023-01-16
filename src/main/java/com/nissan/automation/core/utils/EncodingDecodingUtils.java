package com.nissan.automation.core.utils;

import java.util.Arrays;
import java.util.Base64;

public final class EncodingDecodingUtils {

	private EncodingDecodingUtils() {

	}

	public static String encode(String stringToEncode) {

		return Base64.getEncoder().encodeToString(stringToEncode.getBytes());

	}
	
	public static String decode(String stringToDecode) {

		return new String(Base64.getDecoder().decode(stringToDecode));

	}

}
