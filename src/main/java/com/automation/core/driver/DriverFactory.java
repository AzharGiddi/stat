package com.automation.core.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.automation.core.configuration.ConfigurationManager;
import com.automation.core.reports.ExtentReport;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public static String parentWindow;

	public static void initDriver(String browser, boolean headless) {
		ExtentReport.initReports();
		
		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.setHeadless(headless);
			//WebDriverManager.chromedriver().setup();
			
			//DriverManager.setDriver(setDriverWithConfigs(new ChromeDriver(option)));
			try {
				DriverManager.setDriver(setDriverWithConfigs(new RemoteWebDriver(new URL("http://192.168.0.183:4444/"),option)));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (browser.equalsIgnoreCase("safari")) {

			// add code for safari
		}else if(browser.equalsIgnoreCase("edge")) {
			EdgeOptions option = new EdgeOptions();
			option.setCapability("headless", headless);
			WebDriverManager.edgedriver().setup();
			DriverManager.setDriver(setDriverWithConfigs(new EdgeDriver(option)));
			
		}
		parentWindow = DriverManager.getDriver().getWindowHandle();
	}
	
	public static void initDriver(String browser, boolean headless, boolean isGridExecution) {
		
		ExtentReport.initReports();

		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.setHeadless(headless);
			if (isGridExecution) {
				try {
					String hubUrl = ConfigurationManager.getBundle().getString("selenium.grid.hub");
					DriverManager.setDriver(
							setDriverWithConfigs(new RemoteWebDriver(new URL(hubUrl), option)));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
//				WebDriverManager.chromedriver().setup();
				DriverManager.setDriver(setDriverWithConfigs(WebDriverManager.chromedriver().create()));
			}

		} else if (browser.equalsIgnoreCase("safari")) {

			// add code for safari
		} else if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions option = new EdgeOptions();
			option.setCapability("headless", headless);
			WebDriverManager.edgedriver().setup();
			DriverManager.setDriver(setDriverWithConfigs(new EdgeDriver(option)));

		}
		parentWindow = DriverManager.getDriver().getWindowHandle();
	}
	
	private static WebDriver setDriverWithConfigs(WebDriver driver) {
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigurationManager.getBundle().getLong("selenium.wait.timeout")));
		//driver.manage().timeouts().implicitlyWait(ConfigurationManager.getBundle().getLong("selenium.wait.timeout"),TimeUnit.MILLISECONDS);
		//driver.manage().timeouts().setScriptTimeout(ConfigurationManager.getBundle().getLong("script.wait.timeout"),TimeUnit.MILLISECONDS);
		//driver.manage().timeouts().pageLoadTimeout(ConfigurationManager.getBundle().getLong("pageLoad.wait.timeout"),TimeUnit.MILLISECONDS);
		//driver.get(ConfigurationManager.getBundle().getString("app.url"));
		
		return driver;

	}

	public static WebDriver getNewDriver() {
		WebDriver driver = new ChromeDriver();
		setDriverWithConfigs(driver);
		return driver;
	}

	public static void quitDriver() {
		if (Objects.nonNull(DriverManager.getDriver()))
			DriverManager.getDriver().quit();
		DriverManager.unload();
	}

	public static void quitDriver(WebDriver driver) {
		driver.quit();
	}
	
	/*	public static void initDriver(String browser) {
	browserName = browser;
	ExtentReport.initReports();
	if (browserName.equalsIgnoreCase("chrome")) {

		WebDriverManager.chromedriver().setup();
		DriverManager.setDriver(new ChromeDriver());
		setDriverWithConfigs(DriverManager.getDriver());
		if (Objects.isNull(originalDriver)) {
			setOriginalDriver(DriverManager.getDriver());
		}
		

	} else if (browserName.equalsIgnoreCase("safari")) {

		// add code for safari
	}else if(browserName.equalsIgnoreCase("edge")) {
		WebDriverManager.edgedriver().setup();
		DriverManager.setDriver(new EdgeDriver());
		setDriverWithConfigs(DriverManager.getDriver());
		if (Objects.isNull(originalDriver)) {
			setOriginalDriver(DriverManager.getDriver());
		}
		
	}
	parentWindow = DriverManager.getDriver().getWindowHandle();
}*/
	
}
