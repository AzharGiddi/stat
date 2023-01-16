package com.nissan.WebElement;

import java.util.Objects;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nissan.reports.ExtentLogger;

public final class Validator {

	private Validator() {

	}

	private static void verifyTextMatches(String expctedText, String actualText) {

		assertTrue(expctedText.equals(actualText));

	}

	private static void verifyConditionTrue(boolean condition) {

		assertTrue(condition);

	}
	
	public static boolean verifyTrue(boolean condition, String passMsg, String failMessage) {
		
		boolean outcome = false;
		try {
			assertTrue(condition,passMsg, failMessage);
			outcome = true;
			
		} catch (AssertionError e) {
			outcome = false;
			ExtentLogger.fail(failMessage);
		}
		return outcome;
	}

	public static boolean verifyTrue(boolean condition, String... label) {
		boolean outcome = false;
		String labelFor = Objects.isNull(label) || label.length == 0 ? "condition" : label[0];
		String msg = "Expected %s: %b Actual %s: %b";
		/*try {
			verifyTrue(condition,String.format(msg, labelFor, true, labelFor, true),String.format(msg, labelFor, true, labelFor, false));
			outcome = true;
			//msg = String.format(msg, labelFor, true, labelFor, outcome);
			//ExtentLogger.pass(msg);
		} catch (AssertionError e) {
			outcome = false;
			msg = String.format(msg, labelFor, true, labelFor, outcome);
			ExtentLogger.fail(msg);
		}
		return outcome;*/
		
		return verifyTrue(condition,String.format(msg, labelFor, true, labelFor, true),String.format(msg, labelFor, true, labelFor, false));
	}

	public static boolean verifyTrue(int exp, int act, String... label) {
		boolean outcome = false;
		String labelFor = Objects.isNull(label) || label.length == 0 ? "condition" : label[0];
		String msgFormat = "Expected %s: %d Actual %s: %d";
		String msg = String.format(msgFormat, labelFor, exp, labelFor, act);
		
		return verifyTrue(exp == act,msg,msg);
		/*try {
			msg = String.format(msg, labelFor, exp, labelFor, act);
			assertTrue(exp == act,msg,msg);
			outcome = true;
			//ExtentLogger.pass(msg);
		} catch (AssertionError e) {
			outcome = false;
			msg = String.format(msg, labelFor, exp, labelFor, act);
			ExtentLogger.fail(msg);
		}
		return outcome;*/
	}
	
	public static boolean verifyTrue(double exp, double act, String... label) {
		boolean outcome = false;
		String labelFor = Objects.isNull(label) || label.length == 0 ? "condition" : label[0];
		String msgFormat = "Expected %s: %,.2f Actual %s: %,.2f";
		String msg = String.format(msgFormat, labelFor, exp, labelFor, act);
		return verifyTrue(exp == act,msg,msg);
		/*try {
			verifyConditionTrue(exp == act);
			outcome = true;
			msg = String.format(msg, labelFor, exp, labelFor, act);
			ExtentLogger.pass(msg);
		} catch (AssertionError e) {
			outcome = false;
			msg = String.format(msg, labelFor, exp, labelFor, act);
			ExtentLogger.fail(msg);
		}
		return outcome;*/
	}

	public static boolean verifyText(String expectedText, String actualText, String... label) {
		boolean outcome = false;
		String labelFor = Objects.isNull(label) || label.length == 0 ? "text" : label[0];
		String msgFormat = "Expected %s: %s %s Actual %s: %s";
		String passMsg = String.format(msgFormat, labelFor, expectedText, "matches", labelFor, actualText);
		String failMsg = String.format(msgFormat, labelFor, expectedText, "matches", labelFor, actualText);
		return verifyTrue(expectedText.equals(actualText),passMsg, failMsg);
		/*try {
			verifyTextMatches(expectedText, actualText);
			outcome = true;
			msg = String.format(msg, labelFor, expectedText, "matches", labelFor, actualText);
			ExtentLogger.pass(msg);
		} catch (AssertionError e) {
			outcome = false;
			msg = String.format(msg, labelFor, expectedText, "does not matches", labelFor, actualText);
			ExtentLogger.fail(msg);
		}
		return outcome;*/
	}

	public static boolean verifyTextIgnoringCase(String expectedText, String actualText, String... label) {

		return verifyText(expectedText.toUpperCase(), actualText.toUpperCase(), label);

	}
	
	/*public static void assertText(String expectedText, String actualText, String... label) {
		
		try{
			Assert.assertTrue(expectedText.equals(actualText), label[0]);
		}catch(AssertionError e) {
			ExtentLogger.fail(msg);
		}
		
	}*/
	
	public static void assertText(String expectedText, String actualText, String... label) {
		boolean outcome = false;
		String labelFor = Objects.isNull(label) || label.length == 0 ? "text" : label[0];
		String msg = "Expected %s: %s %s Actual %s: %s";
		try {
			Assert.assertTrue(expectedText.equals(actualText), labelFor);
			outcome = true;
			msg = String.format(msg, labelFor, expectedText, "matches", labelFor, actualText);
			ExtentLogger.passWithScreenShot(msg);
		} catch (AssertionError e) {
			outcome = false;
			msg = String.format(msg, labelFor, expectedText, "does not matches", labelFor, actualText);
			ExtentLogger.failWithScreenShot(msg);
			throw new AssertionError(e.getLocalizedMessage());
		}
		
	}
	
	
	public static void assertTrue(boolean condition, String... msg) {
		
		String passLabel = Objects.isNull(msg) || msg.length == 0 ? "text" : msg[0];
		
		String failLabel = Objects.isNull(msg) || msg.length == 0 ? "text" : msg[1];
		
		Assert.assertTrue(condition, failLabel);

		ExtentLogger.passWithScreenShot(passLabel);
		
	}
	
public static void assertTrue(boolean condition, String msg) {
		
		Assert.assertTrue(condition, msg);

		ExtentLogger.pass(msg);
		
	}
	
	
	
		
}
