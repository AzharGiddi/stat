package com.nissan.bdd.fone.stepdefinitions;

import com.nissan.pages.LoginPage;

import io.cucumber.java.en.Given;

public class LoginPageSteps {

	LoginPage loginPage;

	public LoginPageSteps() {

		loginPage = new LoginPage();

	}

	@Given("Login page is displayed")
	public void login_page_is_displayed() {
		loginPage.invoke();
		loginPage.waitForPageToLoad();
	}

}
