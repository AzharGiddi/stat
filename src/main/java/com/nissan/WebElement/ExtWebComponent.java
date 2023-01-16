package com.nissan.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.driver.DriverManager;
import com.nissan.pages.BasePage;

public class ExtWebComponent {

	@FindBy(xpath = "//label[text()='Search Text']/following-sibling::div/span/input")
	protected ExtWebElement txtboxSearchText;

	@FindBy(xpath = "//button[text()='Apply']")
	protected ExtWebElement btnApply;

	@FindBy(xpath = "//button[@class = 'pzhc pzbutton' and text()='Cancel']")
	protected ExtWebElement btnCancel;

	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	private ExtWebElement iframe;
	
	@FindBy(xpath = "//span[@id='pega_ui_load' and @class='pega_ui_busyIndicator' and @aria-valuetext='Loading Content' and @style='display: block;']")
	public ExtWebElement loadingAnimation;

	/*
	 * protected static WebDriver DriverManager.getDriver(); public static void
	 * setDriver(WebDriver DriverManager.getDriver()) {
	 * ExtWebComponent.DriverManager.getDriver() = DriverManager.getDriver(); }
	 */

	public static ExtWebElement getIFrame(String name) {

		DriverManager.getDriver().switchTo().defaultContent();

		String xPath = "//iframe[contains(@title,'%s')]";

		return getExtWebElement(String.format(xPath, name));
	}
	
	public static ExtWebElement getExtWebElement(By by) {
		return BasePage.getExtWebElement(by);
	} 
	
	public static ExtWebElement getExtWebElement(By by, String fieldName) {
		return BasePage.getExtWebElement(by,fieldName);
	} 


	public static ExtWebElement getExtWebElement(String xPath) {
		return BasePage.getExtWebElement(xPath);
	}

	public static ExtWebElement getExtWebElement(String xPath, String fieldName) {
		return BasePage.getExtWebElement(xPath, fieldName);
	}

	public static List<ExtWebElement> getExtWebElements(String xPath) {
		return BasePage.getExtWebElements(xPath);
	}

	public static List<ExtWebElement> getExtWebElements(String xPath, String fieldName) {
		return BasePage.getExtWebElements(xPath);
	}

	public String getLocatorXpath(Class<?> clazz, String fieldName) {
		String locator = "";
		try {
			locator = clazz.getClass().getField(fieldName).getAnnotation(FindBy.class).xpath();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return locator;
	}

	public void filterAndClick(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format(
				"//span[text()='%s']//ancestor::td[1]//following-sibling::td//child::div/span/*[text()='%s']",
				filterText, eleText);

		getExtWebElement(colHeaderXpath).click();
		// btnApply.scrollIntoView();
		//loadingAnimation.waitForNotPresent(20000);
		waitForLoadingAnimationToComplete();
		txtboxSearchText.waitForPresent(2000);
		txtboxSearchText.clearAndSendKeys(filterText + Keys.TAB);
		WaitUtil.sleep(1000);
		btnApply.scrollAndClick();
		WaitUtil.sleep(1000);
		ExtWebElement eleToClick = getExtWebElement(eleToBeClicked);
		WaitUtil.sleep(2000);
		eleToClick.scrollAndClick();
		waitForLoadingAnimationToComplete();
	}

	public void filterAndClickOnCaseId(String columnName, String eleText) {

		String colHeaderXpath = String.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
		String eleToBeClicked = String.format("//*[text()='%s']", eleText);
		getExtWebElement(colHeaderXpath).click();
		//loadingAnimation.waitForNotPresent(20000);
		waitForLoadingAnimationToComplete();
		txtboxSearchText.clearAndSendKeys(eleText + Keys.TAB);
		btnApply.scrollAndClick();
		WaitUtil.sleep(1000);
		ExtWebElement eleToClick = getExtWebElement(eleToBeClicked);
		WaitUtil.sleep(2000);
		eleToClick.click();
		waitForLoadingAnimationToComplete();
		//loadingAnimation.waitForNotPresent(20000);
	}
	
	public void waitForLoadingAnimationToComplete() {
		
		if(Objects.isNull(loadingAnimation.getWrappedElement())){
			return;
		}
		
		loadingAnimation.waitForNotPresent(20000);
	}

	public void filter(String columnName, String filterText, String eleText) {

		String colHeaderXpath = String
				.format("//*[text()='%s']/parent::div/following-sibling::span/a[@id='pui_filter']", columnName);
				ExtWebElement ele = getExtWebElement(colHeaderXpath);
		ele.click();
		WaitUtil.sleep(2000);
		txtboxSearchText.clearAndSendKeys(filterText);
		btnApply.scrollAndClick();
		WaitUtil.sleep(2000);
		

	}

	public static ExtWebElement getChildElement(ExtWebElement parentElement, String xPath) {

		try {
			List<ExtWebElement> listEle = getChildElements("", parentElement, xPath);
			if (listEle.isEmpty()) {
				WebElement ele = null;
				return new ExtWebElementImpl(ele);
			}
			return listEle.get(0);
		} catch (Exception e) {

			throw new NoSuchElementException(e.getLocalizedMessage());

		}

	}

	public static ExtWebElement getChildElement(String parentElementXpath, String xPath) {

		try {
			return getChildElements(parentElementXpath, xPath).get(0);
		} catch (Exception e) {

			throw new NoSuchElementException(e.getLocalizedMessage());

		}
	}

	public static ExtWebElement getChildElement(String elementName, ExtWebElement parentElement, String xPath) {

		/*
		 * try { List<ExtWebElement> listEle =
		 * getChildElements(elementName,parentElement, xPath); if(listEle.isEmpty()) {
		 * WebElement ele =null; return new ExtWebElementImpl(ele,elementName); } return
		 * listEle.get(0); }catch(Exception e) {
		 * 
		 * throw new NoSuchElementException(e.getLocalizedMessage());
		 * 
		 * }
		 */

		return parentElement.getChildElement(By.xpath("." + xPath), elementName);

	}

	public static List<ExtWebElement> getChildElements(String elementName, ExtWebElement parentElement, String xPath) {

		// List<ExtWebElement> eleList = new ArrayList<>();

		String customXPath = "." + xPath;

		/*
		 * try { for (WebElement ele :
		 * parentElement.findElements(By.xpath(customXPath))) { eleList.add(new
		 * ExtWebElementImpl(parentElement,customXPath,elementName)); } } catch
		 * (StaleElementReferenceException e) {
		 * ElementFactory.initElements(DriverManager.getDriver(), null);
		 * 
		 * }catch(Exception e) {
		 * 
		 * WebElement ele=null; eleList.add(new ExtWebElementImpl(ele,elementName));
		 * 
		 * }
		 */

		return parentElement.getChildElements(By.xpath(customXPath), elementName);

		// return eleList;
	}

	public static List<ExtWebElement> getChildElements(ExtWebElement parentElement, String childXpath) {

		/*
		 * List<ExtWebElement> eleList = new ArrayList<>();
		 * 
		 * String customXPath = "." + xPath;
		 * 
		 * try { for (WebElement ele :
		 * parentElement.findElements(By.xpath(customXPath))) { eleList.add(new
		 * ExtWebElementImpl(By.xpath(customXPath))); } } catch
		 * (StaleElementReferenceException e) {
		 * ElementFactory.initElements(DriverManager.getDriver(), null);
		 * 
		 * }catch(Exception e) {
		 * 
		 * throw new NoSuchElementException(e.getLocalizedMessage());
		 * 
		 * }
		 * 
		 * return eleList;
		 */
		String customXPath = "." + childXpath;

		return getChildElements("", parentElement, customXPath);
	}

	public static List<ExtWebElement> getChildElements(String parentElementXpath, String childXpath) {

		/*
		 * List<ExtWebElement> eleList = new ArrayList<>();
		 * 
		 * String customXPath = "." + childXpath; try { WebElement parentElement =
		 * getExtWebElement(parentElementXpath); for (WebElement ele :
		 * parentElement.findElements(By.xpath(customXPath))) { eleList.add(new
		 * ExtWebElementImpl(ele)); } } catch (StaleElementReferenceException e) {
		 * return getChildElements(parentElementXpath, childXpath); }catch(Exception e)
		 * { throw new
		 * NoSuchElementException("Unable to find child element with locator: "
		 * +childXpath); }
		 * 
		 * return eleList;
		 */

		By by = By.xpath("." + childXpath);

		return getExtWebElement(parentElementXpath).getChildElements(by, "");
	}

	public static List<ExtWebElement> getChildElements(String parentElementXpath, String childXpath,
			int parentElementIndex) {

		/*
		 * List<ExtWebElement> eleList = new ArrayList<>();
		 * 
		 * String customXPath = "." + childXpath; try { WebElement parentElement =
		 * getExtWebElements(parentElementXpath).get(parentElementIndex); for
		 * (WebElement ele : parentElement.findElements(By.xpath(customXPath))) {
		 * eleList.add(new ExtWebElementImpl(ele)); } } catch
		 * (StaleElementReferenceException e) { return
		 * getChildElements(parentElementXpath, childXpath,parentElementIndex); }
		 * 
		 * 
		 * 
		 * return eleList;
		 */
		By by = By.xpath("." + childXpath);
		return getExtWebElements(parentElementXpath).get(parentElementIndex).getChildElements(by, "");
	}

	public static ExtWebElement getChildElement(String parentElementXpath, String childXpath, int parentElementIndex) {

		return getChildElements(parentElementXpath, childXpath, parentElementIndex).get(0);
	}

	public String getValue(ExtWebElement parentElement, String xPath) {

		String customXPath = "." + xPath;

		return new ExtWebElementImpl(parentElement.findElement(By.xpath(customXPath))).getText().trim();
	}

	public ExtWebComponent() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}
	
	

	public void waitForComponentToLoad() {
		loadingAnimation.waitForNotPresent(20000);
		waitForFrameToLoad();
	}

	public void waitForFrameToLoad(ExtWebElement iframe) {

		DriverManager.getDriver().switchTo().defaultContent();
		iframe.waitForFrameToLoad();

	}

	public void waitForFrameToLoad() {

		DriverManager.getDriver().switchTo().defaultContent();
		this.iframe.waitForFrameToLoad();

	}

	public static WebElement getChildElement(ExtWebElement parentElement, String xpath, String fieldName) {
		String customXpath = "." + String.format(xpath, fieldName);
		return getChildElement(parentElement, customXpath);
	}

}
