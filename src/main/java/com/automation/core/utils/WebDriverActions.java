package com.automation.core.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import com.automation.core.driver.DriverManager;

public final class WebDriverActions implements WrapsDriver{

	

	public static void click(By element) {
		
		DriverManager.getDriver().findElement(element).click();

	}

	public static void sendKeys(By element, String keysToSend) {

		DriverManager.getDriver().findElement(element).sendKeys(keysToSend);

	}

	public static void hover(By element) {
		DriverManager.getActionDriver().moveToElement(getWebELement(element)).build().perform();
	}

	private static WebElement getWebELement(By element) {
		return DriverManager.getDriver().findElement(element);
	}

	private WebDriverActions() {

	}

	@Override
	public WebDriver getWrappedDriver() {
		// TODO Auto-generated method stub
		return null;
	}

}
