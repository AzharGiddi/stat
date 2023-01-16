package com.nissan.WebElement;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import com.nissan.automation.core.utils.JSONUtil;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.automation.core.utils.ui.WebDriverCommandLogger;
import com.nissan.automation.core.utils.ui.WebDriverExpectedConditions;
import com.nissan.automation.core.utils.ui.WebElementExpectedConditions;
import com.nissan.automation.core.utils.ui.WebElementWait;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.exceptions.UnableToScrollToElementException;
import com.nissan.exceptions.webelementexception.WebElementException;
import com.nissan.reports.ExtentLogger;
import com.nissan.reports.ExtentLogger.MessageType;

public class ExtWebElementImpl implements ExtWebElement {

	private final WebElement element;
	private final By by;
	protected final Log logger = LogFactory.getLog(getClass());

	public ExtWebElementImpl(final WebElement element, String fieldName) {
		this(element);
		setDescription(fieldName);
	}

	public ExtWebElementImpl(final WebElement webElement) {
		this.element = webElement;
		this.by = (getBy(webElement));

	}

	public ExtWebElementImpl(final ElementLocator locator) {
		this(locator.findElement());
	}

	public ExtWebElementImpl(final By by) {
		this.by = by;
		this.element = findElement(this.by, DriverManager.getDriver());
	}

	public ExtWebElementImpl(By by, String fieldName) {

		this(by);
		setDescription(fieldName);
	}

	private By getBy(WebElement ele) {

		By byObj = null;
		String fieldVal = "";
		Field field = null;
		try {
			field = ele.getClass().getDeclaredField("foundBy");
			field.setAccessible(true);
			Object value = field.get(ele);
			fieldVal = (String) value;
			String xpath = StringUtils.substringAfterLast(fieldVal, "xpath: ");
			/*
			 * if(xpath.startsWith(".")) xpath=xpath.replace(".", "");
			 */
			byObj = By.xpath(xpath);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		if (Objects.isNull(byObj)) {
			throw new NullPointerException("By is null");
		}

		return byObj;
	}

	public String getBy() {
		return this.by.toString();
	}
	
private WebElement getElement() {
		
		return this.element;
		/*try{
			if(Objects.isNull(this.element))
				throw new NoSuchElementException("Unable to find element located by: "+this.by.toString());
			return this.element;
		}catch (Exception e) {
			throw new WebElementException("WebElement exception occurred due to: "+e.getLocalizedMessage(), e);
		}*/
			
	}

	@Override
	public void click() {

		logger.info("Performing click operation on " + getDescription());

		getElement().click();
	}

	@Override
	public void submit() {

	}

	@Override
	public void clearAndSendKeys(CharSequence... keysToSend) {
		clear();
		logger.info(
				"Performing sendKeys operation on " + getDescription() + ", sending " + Arrays.toString(keysToSend));
		getElement().sendKeys(keysToSend);

	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		logger.info(
				"Performing sendKeys operation on " + getDescription() + ", sending " + Arrays.toString(keysToSend));
		getElement().sendKeys(keysToSend);

	}

	@Override
	public void sendKeysJS(CharSequence... keysToSend) {
		clear();
		logger.info(
				"Performing sendKeys operation on " + getDescription() + ", sending " + Arrays.toString(keysToSend));
		DriverManager.getJavaScriptExecutor().executeScript("arguments[0].value='" + keysToSend[0] + "';", getElement());

	}
	
	
	
	
	@Override
	public void clear() {
		
		getElement().clear();
	}
		

	@Override
	public String getTagName() {

		return getElement().getTagName();
	}

	@Override
	public String getAttribute(String name) {

		return getElement().getAttribute(name);
	}

	@Override
	public boolean isSelected() {

		return getElement().isSelected();
	}

	@Override
	public boolean isEnabled() {

		return getElement().isEnabled();
	}

	@Override
	public String getText() {

		return getElement().getText();
	}

	@Override
	public List<WebElement> findElements(By by) {

		return getElement().findElements(by);
	}

	@Override
	public WebElement findElement(By by) {

		return getElement().findElement(by);
	}

	@Override
	public boolean isDisplayed() {

		return getElement().isDisplayed();
	}

	@Override
	public Point getLocation() {

		return getElement().getLocation();
	}

	@Override
	public Dimension getSize() {

		return getElement().getSize();
	}

	@Override
	public Rectangle getRect() {

		return getElement().getRect();
	}

	@Override
	public String getCssValue(String propertyName) {

		return getElement().getCssValue(propertyName);
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {

		return getElement().getScreenshotAs(target);
	}

	/*@Override
	public Coordinates getCoordinates() {

		return ((Locatable) getElement()).getCoordinates();
	}*/
	
	@Override
	public org.openqa.selenium.interactions.Coordinates getCoordinates() {

		return ((Locatable) getElement()).getCoordinates();
	}

	@Override
	public boolean elementWired() {

		return (getElement() != null);
	}

	@Override
	public WebElement getWrappedElement() {

		try{
			return getElement();
		}catch (Exception e) {
			throw new WebElementException("WebElement exception occurred due to: "+e.getLocalizedMessage(), e);
		}
	}

	public void waitForVisible(long... timeoutInMillis) {
		this.scrollIntoView();
		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class)
				.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitForNotVisible(long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(RuntimeException.class)
				.until(WebElementExpectedConditions.elementNotVisible());

	}

	public void waitForDisabled(long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(RuntimeException.class, NoSuchElementException.class)
				.withMessage("Wait time out for " + getDescription() + " to be disabled")
				.until(WebElementExpectedConditions.elementDisabled());
	}

	public void waitForEnabled(long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " to be enabled")
				.until(WebElementExpectedConditions.elementEnabled());
	}

	public void waitForPresent(long... timeoutInMilliSeconds) {

		long tOut = Objects.isNull(timeoutInMilliSeconds) || timeoutInMilliSeconds.length == 0 ? 20000l : timeoutInMilliSeconds[0];
		/*WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofMillis(tOut));
		wait.until(ExpectedConditions.presenceOfElementLocated(this.by));*/
		
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
		.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class, WebElementException.class)
		.until(ExpectedConditions.presenceOfElementLocated(this.by));
	}

	public void waitForNotPresent(long... timeoutInMillis) {
		/*if(Objects.isNull(this.by)){
			return;
		}*/
		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class, WebElementException.class)
				.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(this.by)));
	}

	@Override
	public void waitForText(StringMatcher matcher, long... timeoutInMillis) {

		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class)
				.until(WebDriverExpectedConditions.elementTextEq(matcher, this.by));
	}

	public void waitForText(String text, long... timeoutInMillis) {

		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class)
				.until(ExpectedConditions.textToBe(this.by, text));

	}

	public void waitForNotText(StringMatcher matcher, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " text not: " + matcher.toString())
				.until(WebElementExpectedConditions.elementTextNotEq(matcher));
	}

	public void waitForNotText(String text, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " text not: " + text)
				.until(WebElementExpectedConditions.elementTextNotEq(text));
	}

	public void waitForValue(Object value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " value" + value)
				.until(WebElementExpectedConditions.elementValueEq(value));
	}

	public void waitForNotValue(Object value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " value not " + value)
				.until(WebElementExpectedConditions.elementValueNotEq(value));
	}

	public void waitForSelected(long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " to be selected")
				.until(WebElementExpectedConditions.elementSelected());
	}

	public void waitForNotSelected(long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " to not selected")
				.until(WebElementExpectedConditions.elementNotSelected());
	}

	public void waitForAttribute(String name, String value, long... timeoutInMillis) {
		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class)
				.until(ExpectedConditions.attributeToBe(this.getElement(), name, value));
	}

	@Override
	public void waitForAttribute(String attr, StringMatcher value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " " + attr + " = " + value)
				.until(WebElementExpectedConditions.elementAttributeValueEq(attr, value));
	}

	public void waitForNotAttribute(String name, String value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " " + name + "!=" + value)
				.until(WebElementExpectedConditions.elementAttributeValueNotEq(name, value));
	}

	@Override
	public void waitForNotAttribute(String attr, StringMatcher value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " " + attr + " = " + value)
				.until(WebElementExpectedConditions.elementAttributeValueNotEq(attr, value));
	}

	public void waitForCssClass(String name, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have css class " + name)
				.until(WebElementExpectedConditions.elementHasCssClass(name));
	}

	public void waitForNotCssClass(String name, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have not css class" + name)
				.until(WebElementExpectedConditions.elementHasNotCssClass(name));
	}

	public void waitForCssStyle(String name, String value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have css style " + name + "=" + value)
				.until(WebElementExpectedConditions.elementCssPropertyValueEq(name, value));
	}

	public void waitForNotCssStyle(String name, String value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have css style " + name + "!=" + value)
				.until(WebElementExpectedConditions.elementCssPropertyValueNotEq(name, value));
	}

	public void waitForCssStyleColor(String name, String value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have css style " + name + "=" + value)
				.until(WebElementExpectedConditions.elementCssColorPropertyValueEq(name, value));
	}

	public void waitForNotCssStyleColor(String name, String value, long... timeoutInMillis) {
		new WebElementWait(this, timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " have css style " + name + "!=" + value)
				.until(WebElementExpectedConditions.elementCssColorPropertyValueNotEq(name, value));
	}

	public void waitForElementToBeClickable(long... timeoutInMillis) {
		/*
		 * new WebDriverWaits(timeoutInMillis).ignoring(NoSuchElementException.class,
		 * RuntimeException.class) .withMessage("Wait time out for " + getDescription()
		 * + " to be clicked.") .pollingEvery(1000,
		 * TimeUnit.MILLISECONDS).until(ExpectedConditions.elementToBeClickable(this.
		 * getElement()));
		 */
		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class)
				.until(ExpectedConditions.elementToBeClickable(this.getElement()));
	}

	public void waitForFrameToLoad(long... timeoutInMillis) {
		/*new WebDriverWaits(timeoutInMillis).ignoring(NoSuchElementException.class, RuntimeException.class)
				.withMessage("Wait time out for " + getDescription() + " to be loaded.")
				.until(WebDriverExpectedConditions.frameToBeAvailableAndSwitchToIt(this));
		*/
		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class)
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(this.by));
	}

	/**
	 * will only report if failed
	 * 
	 * @param label
	 * @return
	 */
	private boolean ensurePresent(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForPresent();
		} catch (Exception e) {
			outcome = false;
			report("present", outcome, msgFor);
		}

		return outcome;
	}

	// verifications
	public boolean verifyPresent(String... label) {
		boolean outcome = ensurePresent(label);

		if (outcome) {
			// Success message
			String msgFor = getDescription(label);
			report("present", outcome, msgFor);
		}

		return outcome;
	}

	/**
	 * @param label
	 *            to provide in message
	 * @return outcome of verification
	 */
	public boolean verifyNotPresent(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotPresent();
		} catch (Exception e) {
			outcome = false;

		}
		report("notpresent", outcome, msgFor);
		return outcome;
	}

	public boolean verifyVisible(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForVisible();
		} catch (Exception e) {
			outcome = false;

		}
		report("visible", outcome, msgFor);
		return outcome;
	}

	public boolean verifyNotVisible(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotVisible();
		} catch (Exception e) {
			outcome = false;

		}
		report("notvisible", outcome, msgFor);
		return outcome;
	}

	public boolean verifyEnabled(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForEnabled();
		} catch (Exception e) {
			outcome = false;

		}
		report("enabled", outcome, msgFor);
		return outcome;
	}

	public boolean verifyDisabled(String... label) {

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForDisabled();
		} catch (Exception e) {
			outcome = false;

		}
		report("disabled", outcome, msgFor);
		return outcome;
	}

	public boolean verifyText(String text, String... label) {
		if (!ensurePresent(label))
			return false;
		boolean outcome = true;
		String msgFor = getDescription(label);

		try {
			this.waitForVisible();
			waitForText(text);
		} catch (Exception e) {
			outcome = false;

		}
		report("text", outcome, msgFor, text, getText());
		return outcome;
	}

	public boolean verifyNotText(String text, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotText(text);
		} catch (Exception e) {
			outcome = false;

		}
		report("nottext", outcome, msgFor, text, getText());
		return outcome;
	}

	@Override
	public boolean verifyNotText(StringMatcher matcher, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotText(matcher);
		} catch (Exception e) {
			outcome = false;

		}
		report("nottext", outcome, msgFor, matcher.toString(), getText());
		return outcome;
	}

	@Override
	public boolean verifyText(StringMatcher matcher, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForText(matcher);
		} catch (Exception e) {
			outcome = false;

		}
		report("text", outcome, msgFor, matcher.toString(), getText());
		return outcome;
	}

	public <T> boolean verifyValue(T value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForValue(value);
		} catch (Exception e) {
			outcome = false;

		}
		report("value", outcome, msgFor, value, getAttribute("value"));
		return outcome;
	}

	public <T> boolean verifyNotValue(T value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotValue(value);
		} catch (Exception e) {
			outcome = false;

		}
		report("notvalue", outcome, msgFor, value, getAttribute("value"));
		return outcome;
	}

	public boolean verifySelected(String... label) {
		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForSelected();
		} catch (Exception e) {
			outcome = false;

		}
		report("selected", outcome, msgFor);
		return outcome;
	}

	public boolean verifyNotSelected(String... label) {

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotSelected();
		} catch (Exception e) {
			outcome = false;

		}
		report("notselected", outcome, msgFor);
		return outcome;
	}

	public boolean verifyAttribute(String name, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForAttribute(name, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("attribute", outcome, msgFor, value, getAttribute(name));
		return outcome;
	}

	@Override
	public boolean verifyAttribute(String attr, StringMatcher matcher, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForAttribute(attr, matcher);
		} catch (Exception e) {
			outcome = false;

		}
		report("attribute", outcome, msgFor, matcher, getAttribute(attr));
		return outcome;
	}

	public boolean verifyNotAttribute(String name, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotAttribute(name, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("notattribute", outcome, msgFor, value, getAttribute(name));
		return outcome;
	}

	@Override
	public boolean verifyNotAttribute(String attr, StringMatcher matcher, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotAttribute(attr, matcher);
		} catch (Exception e) {
			outcome = false;

		}
		report("notattribute", outcome, msgFor, matcher, getAttribute(attr));
		return outcome;
	}

	public boolean verifyCssClass(String name, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForCssClass(name);
		} catch (Exception e) {
			outcome = false;

		}
		report("cssclass", outcome, msgFor, name, getAttribute("class"));
		return outcome;
	}

	public boolean verifyNotCssClass(String name, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotCssClass(name);
		} catch (Exception e) {
			outcome = false;

		}
		report("notcssclass", outcome, msgFor, name, getAttribute("class"));
		return outcome;
	}

	public boolean verifyCssStyle(String name, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForCssStyle(name, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("cssstyle", outcome, msgFor, value, getCssValue(name));
		return outcome;
	}

	public boolean verifyNotCssStyle(String name, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotCssStyle(name, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("notcssstyle", outcome, msgFor, value, getCssValue(name));
		return outcome;
	}

	@Override
	public boolean verifyCssStyleColor(String prop, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForCssStyleColor(prop, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("cssstyle", outcome, msgFor, value, getCssValue(prop));
		return outcome;
	}

	@Override
	public boolean verifyNotCssStyleColor(String prop, String value, String... label) {
		if (!ensurePresent(label))
			return false;

		boolean outcome = true;
		String msgFor = getDescription(label);
		try {
			waitForNotCssStyleColor(prop, value);
		} catch (Exception e) {
			outcome = false;

		}
		report("notcssstyle", outcome, msgFor, value, getCssValue(prop));
		return outcome;
	}

	// preconditions
	public void givenPresent() {
		if (!verifyPresent()) {
			throw new SkipException("Precondition failed:" + getDescription() + " should be present");
		}
	}

	public void givenNotPresent(String... label) {
		if (!verifyNotPresent(label)) {
			throw new SkipException("Precondition failed:"
					+ WebDriverCommandLogger.getMsgForElementOp("notpresent", false, getDescription(label)));
		}
	}

	// assertions
	public void assertPresent(String... label) {
		if (!verifyPresent(label)) {
			throw new AssertionError();
		}
	}

	public void assertNotPresent(String... label) {
		if (!verifyNotPresent(label)) {
			throw new AssertionError();
		}
	}

	public void assertVisible(String... label) {
		if (!verifyVisible(label)) {
			throw new AssertionError();
		}
	}

	public void assertNotVisible(String... label) {
		if (!verifyNotVisible(label)) {
			throw new AssertionError();
		}
	}

	public void assertEnabled(String... label) {
		if (!verifyEnabled(label)) {
			throw new AssertionError();
		}
	}

	public void assertDisabled(String... label) {
		if (!verifyDisabled(label)) {
			throw new AssertionError();
		}
	}

	public void assertText(String text, String... label) {
		if (!verifyText(text, label)) {
			throw new AssertionError();
		}
	}

	public void assertNotText(String text, String... label) {
		if (!verifyNotText(text, label)) {
			throw new AssertionError();
		}
	}

	@Override
	public void assertText(StringMatcher matcher, String... label) {
		if (!verifyText(matcher, label)) {
			throw new AssertionError();
		}
	}

	@Override
	public void assetNotText(StringMatcher matcher, String... label) {
		if (!verifyNotText(matcher, label)) {
			throw new AssertionError();
		}
	}

	public <T> void assertValue(T value, String... label) {
		if (!verifyValue(value, label)) {
			throw new AssertionError();
		}
	}

	public <T> void assertNotValue(T value, String... label) {
		if (!verifyNotValue(value, label)) {
			throw new AssertionError();
		}
	}

	public void assertSelected(String... label) {
		if (!verifySelected(label)) {
			throw new AssertionError();
		}
	}

	public void assertNotSelected(String... label) {
		if (!verifyNotSelected(label)) {
			throw new AssertionError();
		}
	}

	public void assertAttribute(String name, String value, String... label) {

		if (!verifyAttribute(name, value, label)) {
			throw new AssertionError();
		}

	}

	@Override
	public void assertAttribute(String attr, StringMatcher matcher, String... label) {
		if (!verifyAttribute(attr, matcher, label)) {
			throw new AssertionError();
		}
	}

	public void assertNotAttribute(String name, String value, String... label) {
		if (!verifyNotAttribute(name, value, label)) {
			throw new AssertionError();
		}
	}

	@Override
	public void assertNotAttribute(String attr, StringMatcher matcher, String... label) {
		if (!verifyNotAttribute(attr, matcher, label)) {
			throw new AssertionError();
		}
	}

	public void assertCssClass(String name, String... label) {
		if (!verifyCssClass(name, label)) {
			throw new AssertionError();
		}
	}

	public void assertNotCssClass(String name, String... label) {
		if (!verifyNotCssClass(name, label)) {
			throw new AssertionError();
		}
	}

	public void assertCssStyle(String name, String value, String... label) {
		if (!verifyCssStyle(name, value, label)) {
			throw new AssertionError();
		}
	}

	public void assertNotCssStyle(String name, String value, String... label) {
		if (!verifyNotCssStyle(name, value, label)) {
			throw new AssertionError();
		}
	}

	@Override
	public void assertCssStyleColor(String prop, String value, String... label) {
		if (!verifyCssStyleColor(prop, value, label)) {
			throw new AssertionError();
		}
	}

	@Override
	public void assertNotCssStyleColor(String prop, String value, String... label) {
		if (!verifyNotCssStyleColor(prop, value, label)) {
			throw new AssertionError();
		}
	}

	public WebDriver getWrappedDriver() {
		return DriverManager.getDriver();
	}

	@SuppressWarnings("unchecked")
	public <T> T executeScript(String js) {
		JavascriptExecutor executor = (JavascriptExecutor) getWrappedDriver();
		return (T) executor.executeScript("arguments[0]." + js, this);
	}

	@SuppressWarnings("unchecked")
	public <T> T executeAsyncScript(String js) {
		JavascriptExecutor executor = (JavascriptExecutor) getWrappedDriver();
		return (T) executor.executeAsyncScript("arguments[0]." + js, this);
	}

	public void setAttribute(String name, String value) {
		executeScript(name + "=" + value);
	}

	protected void report(String op, boolean outcome, Object... args) {
		ExtentLogger.addMessage(WebDriverCommandLogger.getMsgForElementOp(op, outcome, args),
				(outcome ? MessageType.PASS : MessageType.FAIL));
	}

	private String description;

	public String getDescription(String... label) {

		return (label != null) && (label.length > 0) ? label[0] : this.description;

	}

	public void setDescription(String description) {
		if (JSONUtil.isValidJsonString(description)) {
			try {
				Map<String, Object> map = JSONUtil.toMap(description);
				this.description = map.containsKey("desc") ? (String) map.get("desc")
						: map.containsKey("description") ? (String) map.get("description") : "";
			} catch (JSONException e) {
				logger.error(e.getMessage());
			}
		} else {
			this.description = description;
		}
	}

	@Override
	public boolean isPresent() {
		boolean outcome = true;
		try {
			if (Objects.isNull(getElement()))
				return false;
			outcome = this.getElement().isDisplayed();
		} catch (Exception e) {
			outcome = false;
		}

		return outcome;

	}

	public ExtWebElement getChildElement(By by, String fieldName) {
		return new ExtWebElementImpl(findElement(by), fieldName);
	}

	public List<ExtWebElement> getChildElements(By by, String fieldName) {
		List<ExtWebElement> eleList = new ArrayList<>();
		for (WebElement ele : findElements(by)) {
			eleList.add(new ExtWebElementImpl(ele, fieldName));
		}

		return eleList;
	}

	@Override
	public ExtWebElement getChildElement(String xpath, String elementName) {
		return getChildElement(By.xpath("." + xpath), elementName);
	}

	@Override
	public List<ExtWebElement> getChildElements(String xpath, String elementName) {
		return getChildElements(By.xpath("." + xpath), elementName);
	}

	private static WebElement findElement(By by, WebDriver driver) {
		WebElement ele = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000));
			ele = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			/*
			 * ele= driver.findElements(by).stream().findFirst() .orElseThrow(() -> new
			 * NoSuchElementException("Cannot locate an getElement() using " + by));
			 */
			return ele;
		} catch (WebDriverException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	/**
	 * @param driver
	 *            WebDriver
	 * @param by
	 *            locator
	 * @return List of WebElements found
	 * @see #findElement(By, WebDriver)
	 */
	private static List<WebElement> findElements(By by, WebDriver driver) {
		List<WebElement> listELe = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000));
			listELe = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			return listELe;
			// return driver.findElements(by);
		} catch (WebDriverException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ExtWebElement moveToElement() {

		DriverManager.getActionDriver().moveToElement(getElement()).perform();
		return this;

	}

	@Override
	public ExtWebElement scrollIntoView(long... waitTimeInMillis) {

		try {
			long waitTime = Objects.isNull(waitTimeInMillis) || waitTimeInMillis.length == 0 ? 2000l
					: waitTimeInMillis[0];
			WaitUtil.sleep(waitTime);
			ExtWebElement ele = new ExtWebElementImpl(by);
			if(Objects.isNull(ele)) {
				throw new NoSuchElementException("Unable to find Webelement located by "+this.by.toString());
			}
			DriverManager.getJavaScriptExecutor().executeScript("arguments[0].scrollIntoView(true);", ele.getWrappedElement());
		} catch (Exception e) {
			throw new UnableToScrollToElementException("Unable to scroll to getElement() due to " + e.getLocalizedMessage());

		}
		return this;
	}
	
	/**
	   * Select all options that display text matching the argument. That is, when given "Bar" this
	   * would select an option like:
	   *
	   * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	   *
	   * @param text The visible text to match against
	   * @throws NoSuchElementException If no matching option elements are found
	   */
	@Override
	public void select(String text) {

		Select select = new Select(getElement());
		select.selectByVisibleText(text);

	}

	/**
	   * Select all options that have a value matching the argument. That is, when given "foo" this
	   * would select an option like:
	   *
	   * &lt;option value="foo"&gt;Bar&lt;/option&gt;
	   *
	   * @param value The value to match against
	   * @throws NoSuchElementException If no matching option elements are found
	   */
	@Override
	public void selectByValue(String value) {

		Select select = new Select(getElement());
		select.selectByValue(value);

	}

	 /**
	   * Select the option at the given index. This is done by examining the "index" attribute of an
	   * element, and not merely by counting.
	   *
	   * @param index The option at this index will be selected
	   * @throws NoSuchElementException If no matching option elements are found
	   */
	@Override
	public void selectByIndex(int index) {

		Select select = new Select(getElement());
		select.selectByIndex(index);

	}

	/**
	   * @return The first selected option in this select tag (or the currently selected option in a
	   *         normal select)
	   * @throws NoSuchElementException If no option is selected
	   */
	@Override
	public String getFirstSelectedOptionText() {

		Select select = new Select(getElement());
		return select.getFirstSelectedOption().getText();

	}

	@Override
	public void scrollAndClick() {

		scrollIntoView();
		this.click();
	}

	@Override
	public void doubleClick() {

		DriverManager.getActionDriver().doubleClick(this.getElement());
	}

	@Override
	public void waitForElementLocatedByToBeClickable(long... timeoutInMillis) {

		long tOut = Objects.isNull(timeoutInMillis) || timeoutInMillis.length == 0 ? 20000 : timeoutInMillis[0];
		new FluentWait<WebDriver>(DriverManager.getDriver()).withTimeout(Duration.ofMillis(tOut))
				.pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class)
				.until(ExpectedConditions.elementToBeClickable(this.by));
	}

}