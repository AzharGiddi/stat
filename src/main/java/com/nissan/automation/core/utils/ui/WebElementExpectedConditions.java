package com.nissan.automation.core.utils.ui;

import java.util.List;

import org.openqa.selenium.support.Color;

import com.nissan.WebElement.ExtWebElement;
import com.nissan.WebElement.ExtWebElementImpl;
import com.nissan.automation.core.utils.StringMatcher;
import com.nissan.automation.core.utils.StringUtil;

public class WebElementExpectedConditions {
			// Restricted to create objects
		private WebElementExpectedConditions() {

		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementVisible() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return element.isPresent() && element.isDisplayed();
				}
			};
		}

		public static ExpectedCondition<List<ExtWebElement>, Boolean> anyElementVisible() {
			return new ExpectedCondition<List<ExtWebElement>, Boolean>() {
				String msg = "any of elements to be visible";

				@Override
				public Boolean apply(List<ExtWebElement> elements) {
					msg = "any of elements to be visible";
					for (ExtWebElement element : elements) {
						if (element.isPresent() && element.isDisplayed()) {
							return true;
						}
						msg = msg+" "+((ExtWebElementImpl)element).getDescription();
					}
					return false;
				}
				@Override
				public String toString() {
					return msg;
				}
			};
		}
		
		public static ExpectedCondition<List<ExtWebElement>, Boolean> allElementVisible() {
			return new ExpectedCondition<List<ExtWebElement>, Boolean>() {
				String msg = "elements to be visible";
				
				@Override
				public Boolean apply(List<ExtWebElement> elements) {
					for (ExtWebElement element : elements) {
						if (!(element.isPresent() && element.isDisplayed())) {
							msg = "elements to be visible "+((ExtWebElementImpl)element).getDescription();
							return false;
						}
					}
					return true;
				}
				
				@Override
				public String toString() {
					return msg;
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementPresent() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return element.isPresent();
				}
			};
		}
		public static ExpectedCondition<List<ExtWebElement>, Boolean> anyElementPresent() {
			return new ExpectedCondition<List<ExtWebElement>, Boolean>() {
				String msg = "any of elements to be present";

				@Override
				public Boolean apply(List<ExtWebElement> elements) {
					msg = "any of elements to be present";

					for (ExtWebElement element : elements) {
						if (element.isPresent()) {
							return true;
						}
						msg = " "+((ExtWebElementImpl)element).getDescription();
					}
					return false;
				}
				
				@Override
				public String toString() {
					return msg;
				}
			};
		}
		
		public static ExpectedCondition<List<ExtWebElement>, Boolean> allElementPresent() {
			return new ExpectedCondition<List<ExtWebElement>, Boolean>() {
				String msg = "all of elements to be present";

				@Override
				public Boolean apply(List<ExtWebElement> elements) {
					for (ExtWebElement element : elements) {
						if (!element.isPresent()) {
							msg = "elements to be present " + ((ExtWebElementImpl)element).getDescription();
							return false;
						}
					}
					return true;
				}
				
				@Override
				public String toString() {
					return msg;
				}
			};
		}
		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementNotPresent() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !element.isPresent();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementNotVisible() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !element.isPresent() || !element.isDisplayed();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementEnabled() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return element.isEnabled();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementDisabled() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !element.isEnabled();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementAttributeValueEq(final String attributeName,
				final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return ((StringMatcher) val).match(element.getAttribute(attributeName));
					}
					return StringUtil.seleniumEquals(element.getAttribute(attributeName), String.valueOf(val));
				}
			};
		}
		
		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementAttributeValueEqual(final String attributeName,
				final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return ((StringMatcher) val).match(element.getAttribute(attributeName));
					}
					return StringUtil.seleniumEquals(element.getAttribute(attributeName), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementAttributeValueNotEq(
				final String attributeName, final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return !((StringMatcher) val).match(element.getAttribute(attributeName));
					}
					return !StringUtil.seleniumEquals(element.getAttribute(attributeName), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementCssPropertyValueEq(final String propertyName,
				final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return StringUtil.seleniumEquals(element.getCssValue(propertyName), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementCssPropertyValueNotEq(
				final String propertyName, final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !StringUtil.seleniumEquals(element.getCssValue(propertyName), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementCssColorPropertyValueEq(final String propertyName,
				final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return Color.fromString(element.getCssValue(propertyName)).asRgba().equals(Color.fromString(String.valueOf(val)).asRgba());
				}
			};
		}
		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementCssColorPropertyValueNotEq(
				final String propertyName, final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !Color.fromString(element.getCssValue(propertyName)).asRgba().equals(Color.fromString(String.valueOf(val)).asRgba());
				}
			};
		}
		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementTextEq(final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return ((StringMatcher) val).match(element.getText());
					}
					return StringUtil.seleniumEquals(element.getText(), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementTextNotEq(final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return !((StringMatcher) val).match(element.getText());
					}
					return !StringUtil.seleniumEquals(element.getText(), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementValueEq(final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return ((StringMatcher) val).match(element.getAttribute("value"));
					}
					return StringUtil.seleniumEquals(element.getAttribute("value"), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementValueNotEq(final Object val) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					if (val instanceof StringMatcher) {
						return !((StringMatcher) val).match(element.getAttribute("value"));
					}
					return !StringUtil.seleniumEquals(element.getAttribute("value"), String.valueOf(val));
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementSelected() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return element.isSelected();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementNotSelected() {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !element.isSelected();
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementHasCssClass(final String className) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return element.getAttribute("class").contains(className);
				}
			};
		}

		public static ExpectedCondition<ExtWebElementImpl, Boolean> elementHasNotCssClass(final String className) {
			return new ExpectedCondition<ExtWebElementImpl, Boolean>() {
				@Override
				public Boolean apply(ExtWebElementImpl element) {
					return !element.getAttribute("class").contains(className);
				}
			};
		}
		
		
	}


