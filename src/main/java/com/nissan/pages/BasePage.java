package com.nissan.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebComponent;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.ExtWebElementImpl;
import com.nissan.WebElement.Validator;
import com.nissan.automation.core.utils.EncodingDecodingUtils;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.WaitUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.driver.DriverManager;
import com.nissan.enums.Roles;
import com.nissan.exceptions.NoSuchOpenTabException;
import com.nissan.exceptions.UserNotFoundException;
import com.nissan.reports.ExtentLogger;

public class BasePage{

	
	
	public static Logger logger = Logger.getLogger(BasePage.class);

	public static String baseUrl = ConfigurationManager.getBundle().getString("app.url");

	@FindBy(xpath = "//button[normalize-space(@class)='icons avatar name-k'] | //a[@name='PortalHeaderGW_pyDisplayHarness_5']")
	private ExtWebElement btnUserProfile;

	@FindBy(xpath = "//div[@id='title'] | //span[text()='Log off']")
	private ExtWebElement txtLogOut;

	@FindBy(xpath = "//div[text()='Close']")
	public ExtWebElement btnCloseSystemMessage;

	/*
	 * @FindBy(xpath =
	 * "//span[@title='Close this tab'] | //li[@class='rightborder disabled' and contains(@style,'hidden')] | //span[text()='DASHBOARD']"
	 * ) public List<ExtWebElement> listCloseBtn;
	 */

	@FindBy(xpath = "//div[@id='modalWrapper']//button[text()='OK']")
	public ExtWebElement btnOk;

	@FindBy(xpath = "//ol[@style]//a[@aria-label='Currently open']")
	public ExtWebElement btnCloseAllDownArrow;

	@FindBy(xpath = "//td[@title='Close All']")
	public ExtWebElement linkCloseAll;

	@FindBy(xpath = "//div[contains(text(),'Close')] | //ol[@style]//a[@aria-label='Currently open']")
	public List<ExtWebElement> listCloseBtn;
	
	@FindBy(xpath = "//td/span[@id='close']")
	public List<ExtWebElement> listOpenTabs;
	
	@FindBy(xpath = "//div[@id='modalOverlay' and @style='display: block;']//button[contains(@name,'pyDirtyCheckConfirm') and (text()='Discard' or text()='OK')]")
	public ExtWebElement btnModalDiscard;
	
	@FindBy(xpath = "//div[@aria-hidden='false']//iframe[@title]")
	private ExtWebElement iframe;
	
	@FindBy(xpath = "//span[@class='pega_ui_busyIndicator' and @style='display: block;']")
	public ExtWebElement iconLoading;

	

	public void closeErrorMessageAndOpenTabs() {
		
		DriverManager.getDriver().switchTo().defaultContent();
		for (ExtWebElement ele : listCloseBtn) {
			if (ele.getText().trim().equals("Close")) {
				ele.click();
				continue;
			}

			ele.click();
			linkCloseAll.click();
		}
		
		
			
		}
		
		
		
		

	

	public static ExtWebElement getExtWebElement(String xPath) {
		
		By by = By.xpath(xPath);
		//ExtWebElement extWebElement = new ExtWebElementImpl(DriverManager.getDriver().findElement(By.xpath(xPath)));
		/*ExtWebElement extWebElement = new ExtWebElementImpl(by);
		extWebElement.waitForPresent();
		//extWebElement.scrollIntoView();
		return extWebElement;*/
		return getExtWebElement(by);
	}
	
	public static ExtWebElement getExtWebElement(By by, String fieldName) {
		ExtWebElement extWebElement = new ExtWebElementImpl(by,fieldName);
		extWebElement.waitForPresent(2000);
		return extWebElement;
	}
	
	public static ExtWebElement getExtWebElement(By by) {
		ExtWebElement extWebElement = new ExtWebElementImpl(by);
		extWebElement.waitForPresent();
		return extWebElement;
	}
	
	public static ExtWebElement getExtWebElement(String xPath, String fieldName) {
		
		By by = By.xpath(xPath);
		ExtWebElement extWebElement = new ExtWebElementImpl(by,fieldName);
		extWebElement.waitForPresent(2000);
		return extWebElement;
	}

	public static List<ExtWebElement> getExtWebElements(String xPath) {
		List<ExtWebElement> listOfEle = new ArrayList<>();

		for (WebElement ele : DriverManager.getDriver().findElements(By.xpath(xPath))) {
			listOfEle.add(new ExtWebElementImpl(ele));
		}

		return listOfEle;
	}
	
	public static List<ExtWebElement> getExtWebElements(String xPath, String fieldName) {
		List<ExtWebElement> listOfEle = new ArrayList<>();

		for (WebElement ele : DriverManager.getDriver().findElements(By.xpath(xPath))) {
			listOfEle.add(new ExtWebElementImpl(ele,fieldName));
		}

		return listOfEle;
	}
	
	
	public String getPassword() {

		return EncodingDecodingUtils.decode(ConfigurationManager.getBundle().getString("common.pwd"));

	}

	public String getUserName(Roles role) {

		String userName = "";

		switch (role) {
		case ADMIN:
			userName = ConfigurationManager.getBundle().getString("admin.user");
			break;

		case F1:
			userName = ConfigurationManager.getBundle().getString("f1.user");
			break;

		case ENGINEERINGMANAGER:
			userName = ConfigurationManager.getBundle().getString("engmanager.user");
			break;
		
		case SERVICEADVISOR:
			userName = ConfigurationManager.getBundle().getString("serviceadvisor.user");
			break;
		
		case TECHNICIAN:
			userName = ConfigurationManager.getBundle().getString("technician.user");
			break;
		
		case VCATSUPPORT:
			userName = ConfigurationManager.getBundle().getString("vcat.user");
			break;
		case SERVICEMANAGER:
			userName = ConfigurationManager.getBundle().getString("servicemanager.user");
			break;
			
		case GOODWILLMANAGER:
			userName = ConfigurationManager.getBundle().getString("gwmanager.user");
			break;
		case FOM:
			userName = ConfigurationManager.getBundle().getString("fom.user");
			break;
		case HRKMANAGER:
			userName = ConfigurationManager.getBundle().getString("hrkmanager.user");
			break;	
			
			

		default:
			throw new UserNotFoundException("Username for " + role + " not found.");
		}

		return userName;
	}
	
	public String getUserName(Roles role, String dealerCode) {

		String userName = "";

		switch (role) {
		case ADMIN:
			userName = ConfigurationManager.getBundle().getString("admin.user");
			break;

		case F1:
			userName = ConfigurationManager.getBundle().getString("f1.user");
			break;

		case ENGINEERINGMANAGER:
			userName = ConfigurationManager.getBundle().getString("engmanager.user");
			break;
		
		case SERVICEADVISOR:
			userName = ConfigurationManager.getBundle().getString("serviceadvisor.user."+dealerCode);
			break;
		
		case TECHNICIAN:
			userName = ConfigurationManager.getBundle().getString("technician.user."+dealerCode);
			break;
		
		case VCATSUPPORT:
			userName = ConfigurationManager.getBundle().getString("vcat.user");
			break;
		case SERVICEMANAGER:
			userName = ConfigurationManager.getBundle().getString("servicemanager.user."+dealerCode);
			break;
			
		case GOODWILLMANAGER:
			userName = ConfigurationManager.getBundle().getString("gwmanager.user");
			break;
		case FOM:
			userName = ConfigurationManager.getBundle().getString("fom.user."+dealerCode);
			break;	
		case RAM:
			userName = ConfigurationManager.getBundle().getString("ram.user");
			break;

		default:
			throw new UserNotFoundException("Username for " + role + " not found.");
		}

		return userName;
	}

	/**
	 * This method switches to the child window handle with target element on it.
	 * 
	 * @param window
	 *            Parent window handle
	 * @param ele
	 *            element on the browser
	 */
	public void getWindow(String window, ExtWebElement ele) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		for (String wh : whs) {
			if (wh.equals(window))
				continue;
			DriverManager.getDriver().switchTo().window(wh);
			try {
				if (ele.isDisplayed()) {
					return;

				}
			} catch (NoSuchElementException e) {

				continue;

			}

		}
	}

	public void getWindow(ExtWebElement ele) {

		Set<String> whs = DriverManager.getDriver().getWindowHandles();
		while (whs.iterator().hasNext()) {

			DriverManager.getDriver().switchTo().window(whs.iterator().next());
			if (ele.isDisplayed()) {
				return;

			}

		}
	}

	public static void closeadditionalWindow(String parentWindow) {

		Set<String> windows = DriverManager.getDriver().getWindowHandles();

		for (String window : windows) {
			if (window.equals(parentWindow))
				continue;
			DriverManager.getDriver().switchTo().window(window);
			DriverManager.getDriver().close();
		}

		DriverManager.getDriver().switchTo().window(parentWindow);
	}

	/**
	 * Hover on target element
	 * 
	 * @param ele
	 */
	public void hoverOnElement(ExtWebElement ele) {

		try {
			DriverManager.getActionDriver().moveToElement(ele).build().perform();
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Unable to hover on element due to: " + e.getLocalizedMessage());

		}

	}

	public void hoverOnDDElement(String dropDownMenu) {

		WebElement ele = getExtWebElement(String.format("//span[text()='%s']", dropDownMenu)).getWrappedElement();
		try {
			WaitUtil.sleep(1000);
			DriverManager.getActionDriver().moveToElement(ele).build().perform();

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Unable to hover on element due to: " + e.getLocalizedMessage());

		}

	}

	public void clickOnDDElement(String dropDownMenu) {
		WebElement ele = getExtWebElement(String.format("//span[text()='%s']", dropDownMenu)).getWrappedElement();
		try {
			DriverManager.getActionDriver().click(ele).perform();
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Unable to hover on element due to: " + e.getLocalizedMessage());

		}

	}
	
	public void invoke(String url) {
		DriverManager.getDriver().get(url);
	}

	public void login(String userName, String password) {

		LoginPage loginPage = new LoginPage();
		loginPage.invoke();
		loginPage.waitForPageToLoad();
		loginPage.loginWith(userName, password);
	}
	
	
	public void login(String userName) {

		LoginPage loginPage = new LoginPage();
		loginPage.invoke();
		loginPage.waitForPageToLoad();
		loginPage.loginWith(userName, getPassword());
	}

	public void login(Roles role) {

		
		login(getUserName(role), getPassword());
	}
	
	public void login(Roles role, String dealerCode) {

		
		login(getUserName(role,dealerCode), getPassword());
		
	}
	
	public void loginWithRole(String role, String dealerCode) {
		
		login(getUserName(getRole(role),dealerCode), getPassword());
		
	}
	
	public Roles getRole(String roleName) {
		
		for(Roles role: Roles.values())
			
			if(role.name().equals(roleName)) {
				return role;
			}
		
		return null;
	}
	
	public String getLocatorXpath(Class<?> clazz, String fieldName) {
		String locator = "";
		try {
			locator= clazz.getClass().getField(fieldName).getAnnotation(FindBy.class).xpath();
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return locator;
	}


	public ExtWebElement getLeftSideMenuElement(String eleName) {
		DriverManager.getDriver().switchTo().defaultContent();
		String xPath = String.format("//span[text()='%s']",eleName);

		return getExtWebElement(xPath,eleName);
	}
	
	/**
	 * Shift Focus on the target tab
	 * 
	 * @param tabName
	 * 
	 *            Note: Only use this when the target tab is open. Else it will
	 *            throw NoSuchOpenTabException.
	 */
	public void getTabInFocus(String tabName) {
		DriverManager.getDriver().switchTo().defaultContent();
		String xPath = String.format("//td/span[contains(text(),'%s')]", tabName);

		try {

			getExtWebElement(xPath).click();
			DriverManager.getDriver().switchTo().frame(ExtWebComponent.getIFrame(tabName));
		} catch (Exception e) {
			ExtentLogger.fail(new NoSuchOpenTabException(
					"Unable to focus on " + tabName + ". Due to: " + e.getLocalizedMessage()));
		}

	}

	public void closeTab(String tabName) {
		DriverManager.getDriver().switchTo().defaultContent();
		getExtWebElement(String
				.format("//span[contains(text(),'%s')]/parent::td/following-sibling::td/span[@id='close']", tabName))
						.click();

	}

	public void logOut() {
		DriverManager.getDriver().switchTo().defaultContent();
		closeErrorMessageAndOpenTabs();
		btnUserProfile.click();
		clickOnDDElement("Log off");
		Validator.assertTrue(DriverManager.getDriver().getTitle().trim().equals("You are logged out."), "Logged Out Successfully","Unable to log out");

	}
	
	
	
	public void logOutOnFailure() {
		DriverManager.getDriver().switchTo().defaultContent();

		String title = DriverManager.getDriver().getTitle().trim();

		if (title.equals("Welcome to PegaRULES") || title.equals("You are logged out.") || title.equals("Pega Platform - Status") || title.equals("")) {
			return;
		}
		try {
			closeErrorMessageAndOpenTabs();
			DriverManager.getDriver().switchTo().alert().accept();
			for (ExtWebElement tab : listOpenTabs) {
				tab.click();
				DriverManager.getDriver().switchTo().frame(iframe);
				btnModalDiscard.click();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		
		// DriverManager.getDriver().switchTo().defaultContent();
		 btnUserProfile.click(); 
		 clickOnDDElement("Log off");
		 
		//logOut();
	}
	
	
	public void waitForPageToLoad() {
		// TODO
	}

	public void close() {

		DriverManager.getDriver().close();

	}

	public BasePage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}

}
