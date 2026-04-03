package com.qa.chroma.util;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.chroma.base.TestBase;


public class ElementUtil extends TestBase {
	
	private JavaScriptUtil jsUtil;
	
	public ElementUtil() {
		PageFactory.initElements(driver, this);
		jsUtil = new JavaScriptUtil();
	}
	
	private void nullBlankCheck(String value) {
		if (value == null || value.length() == 0) {
			System.out.println("Element Value is  :" + value);
		}
	}

	public By getBy(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toLowerCase().trim()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "css":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;

		default:
			break;
		}

		return locator;

	}

	private void checkHighlight(WebElement element) {
		if (Boolean.parseBoolean(prop.getProperty("highlight"))) {
			jsUtil.flash(element);
		}
	}

	public WebElement getElement(WebElement btnloginLink) {
		WebElement element = null;

		try {
			element = btnloginLink;
			checkHighlight(element);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page");
			e.printStackTrace();
		}

		return element;
	}

	public WebElement waitForElementVisible(WebElement locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		WebElement element = wait.until(ExpectedConditions.visibilityOf(locator));
		// WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) locator));
		checkHighlight(element);
		return element;
	}

	public void doClick(WebElement btnloginLink) {
		getElement(btnloginLink).click();
	}

	public void doSendKeys(WebElement locator, String value) {
		nullBlankCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (Exception e) {
			System.out.println("title is not found within : " + timeOut);
		}
		return driver.getTitle();
	}
	
	public boolean isElementDisplayed(WebElement locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not present on the page using : " + locator);
			return false;
		}
	}
	
	public boolean isElementEnable(WebElement locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("element is not Enable on the page using : " + locator);
			return false;
		}
	}
	
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		checkHighlight(element);
		return element;
	}
	
	public void clickElementWhenReady(WebElement locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public String doGetElementText(WebElement loginHeader) {
		return getElement(loginHeader).getText();
	}
	
	public String doElementGetAttribute(WebElement locator, String attrName) {
		return getElement(locator).getDomAttribute(attrName);
	}
	
	

}
