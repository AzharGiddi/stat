package com.nissan.pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.driver.DriverManager;
import com.nissan.exceptions.PageNotFoundException;
import com.nissan.reports.ExtentLogger;

public class LoginPage extends BasePage {

	@FindBy(id = "txtUserID")
	public ExtWebElement txtBoxUsername;

	@FindBy(id = "txtPassword")
	public ExtWebElement txtBoxpassword;

	@FindBy(xpath = "//button[@id='sub' and @type='submit']")
	public ExtWebElement btnLogin;

	public String getTitle() {

		return DriverManager.getDriver().getTitle();
	}

	public LoginPage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);

	}

	public void invoke() {
		DriverManager.getDriver().get(ConfigurationManager.getBundle().getString("app.url"));
	}

	public void loginWith(String userName, String password) {
		ExtentLogger.info("Logging in with "+userName);
		txtBoxUsername.clearAndSendKeys(userName);
		txtBoxpassword.clearAndSendKeys(password);
		btnLogin.waitForElementToBeClickable();
		btnLogin.click();
	}

	@Override
	public void waitForPageToLoad() {

		try {
			txtBoxUsername.waitForPresent();
//			ExtentLogger.pass(this.getClass().getSimpleName() + " loaded Successfully");
			}catch (Exception e) {

			ExtentLogger.fail(new PageNotFoundException(this.getClass().getSimpleName()
							+ " did not load, Waited 20 seconds for the visibility of the element located by : " + txtBoxUsername.getBy()));
			}
			

		}

	

}
