package com.automation.core.utils.ui;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.automation.core.WebElement.ExtWebElement;
import com.automation.core.WebElement.ExtWebElementImpl;
import com.automation.core.utils.StringMatcher;
import com.automation.core.utils.StringUtil;

public class WebDriverExpectedConditions {

	private WebDriverExpectedConditions() {

	}
	
	public static ExpectedCondition<WebDriver, Boolean> elementPresent(final WebElement element) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				
				return	element.isDisplayed() && element.isEnabled();
					
			}
		};
	}
	
	

	public static ExpectedCondition<WebDriver, Boolean> elementPresent(final By locator) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					driver.findElement(locator);
					return true;
				} catch (RuntimeException e) {
					return false;
				}
			}
		};
	}

	public static ExpectedCondition<WebDriver, Boolean> elementNotPresent(final By locator) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					driver.findElement(locator);

				} catch (RuntimeException e) {
					return true;
				}
				return false;
			}
		};
	}

	public static ExpectedCondition<WebDriver, Alert> alertPresent() {
		return new ExpectedCondition<WebDriver, Alert>() {
			@Override
			public Alert apply(WebDriver driver) {
				try {
					Alert alert = driver.switchTo().alert();
					alert.getText();
					return alert;
				} catch (NullPointerException e) {
				}
				return null;
			}
		};
	}

	public static ExpectedCondition<WebDriver, Boolean> noOfwindowsPresent(final int count) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return driver.getWindowHandles().size() >= count;
				} catch (Exception e) {
				}
				return false;
			}
		};
	}

	public static ExpectedCondition<WebDriver, Boolean> windowTitle(final StringMatcher title) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return title.match(driver.getTitle());
				} catch (Exception e) {

				}
				return false;
			}
		};
	}

	public static ExpectedCondition<WebDriver, Boolean> currentURL(final StringMatcher url) {
		return new ExpectedCondition<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return url.match(driver.getCurrentUrl());
			}
		};
	}
	
	 public static ExpectedCondition<WebDriver, Boolean> frameToBeAvailableAndSwitchToIt(
			 final ExtWebElement frameLocator) {
			    return new ExpectedCondition<WebDriver, Boolean>() {
			      @Override
			      public Boolean apply(WebDriver driver) {
			        try {
			          driver.switchTo().frame(frameLocator.getWrappedElement());
			          return true;
			        } catch (NoSuchFrameException e) {
			          throw new NoSuchFrameException(e.getLocalizedMessage());
			        	//return false;
			        }
			      }

			      @Override
			      public String toString() {
			        return "frame to be available: " + frameLocator;
			      }
			    };
			  }
	 
	 private static WebElement findElement(By by, WebDriver driver) {
		    try {
		      return driver.findElements(by).stream().findFirst().orElseThrow(
		          () -> new NoSuchElementException("Cannot locate an element using " + by));
		    } catch (NoSuchElementException e) {
		      throw e;
		    } catch (WebDriverException e) {
		    	e.printStackTrace();
		      throw e;
		    }
		  }
	 
	 public static ExpectedCondition<WebDriver, Boolean> elementTextEq(final Object val, By by) {
			return new ExpectedCondition<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					if (val instanceof StringMatcher) {
						return ((StringMatcher) val).match(driver.findElement(by).getText());
					}
					return StringUtil.seleniumEquals(driver.findElement(by).getText(), String.valueOf(val));
				}
			};
		}

		  /**
		   * @param driver WebDriver
		   * @param by     locator
		   * @return List of WebElements found
		   * @see #findElement(By, WebDriver)
		   */
		  private static List<WebElement> findElements(By by, WebDriver driver) {
		    try {
		      return driver.findElements(by);
		    } catch (WebDriverException e) {
		     e.printStackTrace();
		      throw e;
		    }
		  }
}
