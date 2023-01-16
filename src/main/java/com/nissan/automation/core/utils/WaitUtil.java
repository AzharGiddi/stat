package com.nissan.automation.core.utils;

public class WaitUtil {

	public static void sleep(long sleep) {

		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
			//Thread.currentThread().interrupt();
		}

	}

}
