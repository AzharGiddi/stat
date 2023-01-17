package com.automation.core.WebElement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;

import com.automation.core.utils.StringMatcher;

@ImplementedBy(ExtWebElementImpl.class)
public interface ExtWebElement extends WebElement, Locatable, WrapsElement {

	boolean elementWired();
	
	void waitForVisible(long... timeoutInMilliSeconds);

	void waitForNotVisible(long... timeoutInMilliSeconds);

	void waitForDisabled(long... timeoutInMilliSeconds);

	void waitForEnabled(long... timeoutInMilliSeconds);

	void waitForPresent(long... timeoutInMilliSeconds);

	void waitForNotPresent(long... timeoutInMilliSeconds);

	void waitForText(String text, long... timeoutInMilliSeconds);

	void waitForText(StringMatcher matcher, long... timeoutInMilliSeconds);

	void waitForNotText(String text, long... timeoutInMilliSeconds);

	void waitForNotText(StringMatcher matcher, long... timeoutInMilliSeconds);

	void waitForValue(Object value, long... timeoutInMilliSeconds);

	void waitForNotValue(Object value, long... timeoutInMilliSeconds);

	void waitForSelected(long... timeoutInMilliSeconds);

	void waitForNotSelected(long... timeoutInMilliSeconds);

	void waitForAttribute(String attr, String value, long... timeoutInMilliSeconds);

	void waitForNotAttribute(String attr, String value, long... timeoutInMilliSeconds);

	void waitForAttribute(String attr, StringMatcher value, long... timeoutInMilliSeconds);

	void waitForNotAttribute(String attr, StringMatcher value, long... timeoutInMilliSeconds);

	void waitForCssClass(String className, long... timeoutInMilliSeconds);

	void waitForNotCssClass(String className, long... timeoutInMilliSeconds);

	void waitForCssStyle(String prop, String value, long... timeoutInMilliSeconds);

	void waitForNotCssStyle(String prop, String value, long... timeoutInMilliSeconds);
	
	void waitForElementToBeClickable(long... timeoutInMilliSeconds);
	
	public void waitForElementLocatedByToBeClickable(long... timeoutInMilliSeconds);
	
	void waitForFrameToLoad(long... timeoutInMilliSeconds);

	/**
	 * Special method to wait for css color property. For other css properties use
	 * {@link #waitForCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param timeoutInMilliSeconds
	 *            optional timeoutInMilliSeconds and interval
	 */
	void waitForCssStyleColor(String prop, String value, long... timeoutInMilliSeconds);

	/**
	 * Special method to wait for css color property. For other css properties use
	 * {@link #waitForNotCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param timeoutInMilliSeconds
	 *            optional timeoutInMilliSeconds and interval
	 */
	void waitForNotCssStyleColor(String prop, String value, long... timeoutInMilliSeconds);

	// verifications
	boolean verifyPresent(String... label);

	boolean verifyNotPresent(String... label);

	boolean verifyVisible(String... label);

	boolean verifyNotVisible(String... label);

	boolean verifyEnabled(String... label);

	boolean verifyDisabled(String... label);

	boolean verifyText(String text, String... label);

	boolean verifyText(StringMatcher matcher, String... label);

	boolean verifyNotText(String text, String... label);

	boolean verifyNotText(StringMatcher matcher, String... label);

	<T> boolean verifyValue(T t, String... label);

	<T> boolean verifyNotValue(T t, String... label);

	boolean verifySelected(String... label);

	boolean verifyNotSelected(String... label);

	boolean verifyAttribute(String attr, String value, String... label);

	boolean verifyAttribute(String attr, StringMatcher matcher, String... label);

	boolean verifyNotAttribute(String attr, String value, String... label);

	boolean verifyNotAttribute(String attr, StringMatcher matcher, String... label);

	boolean verifyCssClass(String className, String... label);

	boolean verifyNotCssClass(String className, String... label);

	boolean verifyCssStyle(String prop, String value, String... label);

	boolean verifyNotCssStyle(String prop, String value, String... label);

	/**
	 * Special method to validate css color property. For other css properties use
	 * {@link #verifyCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param label
	 *            optional label to use in report. If not provided it will use
	 *            description if available
	 */
	boolean verifyCssStyleColor(String prop, String value, String... label);

	/**
	 * Special method to validate css color property. For other css properties use
	 * {@link #verifyNotCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param label
	 *            optional label to use in report. If not provided it will use
	 *            description if available
	 */
	boolean verifyNotCssStyleColor(String prop, String value, String... label);

	// preconditions
	void givenPresent();

	void givenNotPresent(String... label);

	// assertions
	void assertPresent(String... label);

	void assertNotPresent(String... label);

	void assertVisible(String... label);

	void assertNotVisible(String... label);

	void assertEnabled(String... label);

	void assertDisabled(String... label);

	void assertText(String text, String... label);

	void assertNotText(String text, String... label);

	void assertText(StringMatcher matcher, String... label);

	void assetNotText(StringMatcher matcher, String... label);

	<T> void assertValue(T t, String... label);

	<T> void assertNotValue(T t, String... label);

	void assertSelected(String... label);

	void assertNotSelected(String... label);

	void assertAttribute(String attr, String value, String... label);

	void assertAttribute(String attr, StringMatcher matcher, String... label);

	void assertNotAttribute(String attr, String value, String... label);

	void assertNotAttribute(String attr, StringMatcher matcher, String... label);

	void assertCssClass(String className, String... label);

	void assertNotCssClass(String className, String... label);

	void assertCssStyle(String prop, String value, String... label);

	void assertNotCssStyle(String prop, String value, String... label);

	/**
	 * Special method to validate css color property. For other css properties use
	 * {@link #assertCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param label
	 *            optional label to use in report. If not provided it will use
	 *            description if available
	 */
	void assertCssStyleColor(String prop, String value, String... label);

	/**
	 * Special method to validate css color property. For other css properties use
	 * {@link #assertNotCssStyle(String, String, String...)}
	 * 
	 * @param prop
	 *            css style property for color to validate. For example: color,
	 *            background-color
	 * @param value
	 *            expected value - valid color name or rgb or rgba or hax
	 * @param label
	 *            optional label to use in report. If not provided it will use
	 *            description if available
	 */
	void assertNotCssStyleColor(String prop, String value, String... label);

	// other
	void setAttribute(String attr, String value);

	boolean isPresent();

	<T> T executeScript(String sctipt);

	<T> T executeAsyncScript(String sctipt);
	
	ExtWebElement moveToElement();
	
	void select(String value);
	
	void scrollAndClick();

	void selectByValue(String value);
	
	void selectByIndex(int index);

	String getFirstSelectedOptionText();

	ExtWebElement scrollIntoView(long...waitTimeInMillis);

	void sendKeysJS(CharSequence... keysToSend);

	void clearAndSendKeys(CharSequence... keysToSend);

	void doubleClick();
	
	String getDescription(String... label);
	
	String getBy();

	ExtWebElement getChildElement(By by, String elementName);

	List<ExtWebElement> getChildElements(By by, String elementName);

	ExtWebElement getChildElement(String xpath, String elementName);

	List<ExtWebElement> getChildElements(String xpath, String elementName);
}

