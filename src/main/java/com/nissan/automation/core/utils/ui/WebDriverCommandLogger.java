package com.nissan.automation.core.utils.ui;

import java.text.MessageFormat;

import com.nissan.configuration.ConfigurationManager;

public class WebDriverCommandLogger {

	
	private static String notOpPassFormat = "Expected {0} not {op} : Actual {0} not {op}";
	private static String notOpFailFormat = "Expected {0} not {op} : Actual {0} {op}";
	private static String opPassFormat = "Expected {0} {op} : Actual {0} {op}";
	private static String opFailFormat = "Expected {0} {op} : Actual {0} not {op}";

	private static String notOpValFormat = "Expected {0} {op} should not be {1} : Actual {0} {op} is {2}";
	private static String opValFormat = "Expected {0} {op} should be {1} : Actual {0} {op} is {2}";
	
	private static String notOpPassFormatWindow = "Expected window not {op} : Actual window not {op}";
	private static String notOpFailFormatWindow = "Expected window not {op} : Actual window {op}";
	private static String opPassFormatWindow = "Expected window {op} : Actual window {op}";
	private static String opFailFormatWindow = "Expected window {op} : Actual window not {op}";

	private static String notOpValFormatWindow = "Expected window {op} should not be {0} : Actual window {op} is {1}";
	private static String opValFormatWindow = "Expected window {op} should be {0} : Actual window {op} is {1}";
	
	/**
	 * @param operation
	 * @param success
	 * @param args
	 *            to provide in message. Expect arg 1 : as label/message for,
	 *            arg 2 as expected val, arg 3 as actual value
	 * @return
	 */
	public static String getMsgForElementOp(String operation, boolean success, Object... args) {
		String key = "element." + operation + "." + (success ? "pass" : "fail");
		String format=null;
		try {
			format = ConfigurationManager.getBundle().getString(key);
		}catch(Exception e) {
			//TODO check why InvocationTargetException is thrown at this point.
			e.printStackTrace();
		}
		
		if (format == null) {
			format = (operation.startsWith("not")
					? ((args != null) && (args.length > 2) ? notOpValFormat
							: (success ? notOpPassFormat : notOpFailFormat))
					: ((args != null) && (args.length > 2) ? opValFormat : (success ? opPassFormat : opFailFormat)))
							.replace("{op}", operation.replace("not", ""));
			// store for future reference
			try {
				ConfigurationManager.getBundle().setProperty(key, format);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			}

		return MessageFormat.format(format, args);
	}
	
	public static String getMsgForDriverOp(String operation, boolean success, Object... args) {
		String key = "window." + operation + "." + (success ? "pass" : "fail");

		String format = ConfigurationManager.getBundle().getString(key);
		if (format == null) {
			format = (operation.startsWith("not")
					? ((args != null) && (args.length > 2) ? notOpValFormatWindow
							: (success ? notOpPassFormatWindow : notOpFailFormatWindow))
					: ((args != null) && (args.length > 2) ? opValFormatWindow : (success ? opPassFormatWindow : opFailFormatWindow)))
							.replace("{op}", operation.replace("not", ""));
			// store for future reference

			ConfigurationManager.getBundle().setProperty(key, format);

		}

		return MessageFormat.format(format, args);
	}
}
