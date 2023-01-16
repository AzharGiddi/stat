package com.nissan.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nissan.WebElement.ElementFactory;
import com.nissan.WebElement.ExtWebElement;
import com.nissan.driver.DriverManager;

public class testPage extends BasePage{

	@FindBy(id="txtUserID")
	public ExtWebElement txtBoxUserId;
	
	@FindBy(id="txtPassword")
	public ExtWebElement gtxtBoxPwd;
	
	@FindBy(name="q")
	public ExtWebElement gtxtSearch;
	
	@FindBy(id="txtPassword")
	public ExtWebElement txtBoxPwd;
	
	
	
	public testPage() {
		ElementFactory.initElements(DriverManager.getDriver(), this);
	}
	
}
