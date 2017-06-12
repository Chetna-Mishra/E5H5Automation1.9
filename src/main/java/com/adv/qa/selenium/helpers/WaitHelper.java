package com.adv.qa.selenium.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.adv.qa.selenium.framework.BaseTest;


public class WaitHelper {

	public static final long defaultWaitMilliseconds = 200; // Used in methods which do not throw exceptions

	private WaitHelper() {

	}
	
	public static WaitHelper getInstance() throws InstantiationException,IllegalAccessException {
			return WaitHelper.class.newInstance();
	}
	
	/**
	 * Sleep default time before next operation
	 */
	public static void defaultWait () {
		try {
			Thread.sleep(defaultWaitMilliseconds);
		} catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new WebDriverException(e);
		}
	}
	
	/**
	 * Wait until current url contains  searchString
	 * 
	 * @param driver
	 * @param searchString
	 * @param timeout
	 * @throws TimeoutException if url does not contain searchString during given timeout
	 */
	public static void waitUntilCurrentUrlContains(WebDriver driver, final String searchString, long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.getCurrentUrl().contains(searchString);
			}} 
		);
	}
	
	/**
	 * Wait until the given WebElement is displayed not longer than given timeOutInSeconds
	 * @param driver
	 * @param element
	 * @param timeOutInSeconds
	 * @throws TimeoutException if element does not become visible during given timeout
	 */
	public static void waitUntilWebElementDisplayed(WebDriver driver,final WebElement element,long timeOutInSeconds){
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return element.isDisplayed();
			}
		});
	}
	
	/**
	 * Wait until the given WebElement is displayed; timeout is taken from BaseTest
	 * @param driver
	 * @param element
	 * @throws TimeoutException if element does not become visible during given timeout
	 */
	public static void waitUntilWebElementDisplayed(WebDriver driver, final WebElement element) {
		long timeOut = 120;
	}
	
	/**
	 * Wait until the given WebElement is not displayed not longer than given timeOutInSeconds; WebElement should exist on the page
	 * @param driver
	 * @param element
	 * @param timeOutInSeconds
	 */
	public static void waitUntilWebElementIsNotDisplayed(WebDriver driver, final WebElement element, long timeOutInSeconds) {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
			return element.isDisplayed();
			}
		});
	}
	
	/**
	 * Wait until the given WebElement is not displayed; timeout is taken from BaseTest; WebElement should exist on the page
	 * @param driver
	 * @param element
	 */
	public static void waitUntilWebElementIsNotDisplayed(WebDriver driver, final WebElement element) {
		long timeOut = BaseTest.implicitlyWaitTimeout;
		waitUntilWebElementIsNotDisplayed(driver, element, timeOut);
	}
	
	/**
	 * Wait  'timeoutInSeconds' or until the page Url contains 'searchString'
	 * @param driver
	 * @param searchString
	 * @param timeoutInSeconds
	 * @return true if the page Url contains 'searchString', false if not after timeoutInSeconds
	 */
	public static boolean waitUrlContains(WebDriver driver, String searchString, long timeoutInSeconds) {
		boolean contains = false;
		long timeOutMilliSeconds = timeoutInSeconds * 1000;
		long t0 = System.currentTimeMillis();
		long t1 = 0;
		
		
		while (true) {
			String url = driver.getCurrentUrl();
			url = url.replace("%20", " ");
			contains = url.contains(searchString);
			if (contains) {
				return true;
			}
			t1 = System.currentTimeMillis();
			if (( t1 - t0) > timeOutMilliSeconds){
				return false;
			}
			defaultWait ();
		}
	}
	
	/**
	 * Wait  timeoutInSeconds or until  element is visible
	 * @param element
	 * @param timeoutInSeconds
	 * @return true if the element is visible, false if it is not displayed after timeoutInSeconds
	 */
	public static boolean waitWebElementIsDisplayed (WebElement element, long timeoutInSeconds) {
		return waitWebElementIsDisplayed(timeoutInSeconds, element);
	}
	
	/**
	 * Wait timeoutInSeconds or until at least one element is visible
	 * @param timeoutInSeconds
	 * @param element var args of WebElements; the method checks if at least one of them is displayed.
	 * @return true if at least one element is visible, false if it they are not displayed after timeoutInSeconds
	 */
	public static boolean waitWebElementIsDisplayed (long timeoutInSeconds, WebElement... element ) {
		boolean isVisible = false;
		long timeOutMilliSeconds = timeoutInSeconds * 1000;
		long t0 = System.currentTimeMillis();
		long t1 = 0;
		while (true) {
			for (int i=0; i < element.length; i++) {
				isVisible = element[i].isDisplayed();
				if (isVisible) {
					return true;
				}
			};
			t1 = System.currentTimeMillis();
			if (( t1 - t0) > timeOutMilliSeconds){
				return false;
			}
			defaultWait ();
		}
	}
	
	public static boolean waitUntilTextDisplayed(WebElement element,String value,long timeoutInSeconds){
		
		long t0 = System.currentTimeMillis();
		long t1 = 0;
		long timeOutMilliSeconds = timeoutInSeconds * 1000;
		while(true){
			if(element.getText().contains(value)){
				return true;
			}
			
			t1 = System.currentTimeMillis();
			if (( t1 - t0) > timeOutMilliSeconds){
				return false;
			}
			defaultWait ();
		}
	}
	/**
	 * Wait  'timeoutInSeconds' or until the page Url changed
	 * @param driver
	 * @param oldUrl
	 * @param timeoutInSeconds
	 * @return true if url was changed, false if url was not changed during timeoutInSeconds
	 */
	public static boolean waitUrlChanged (WebDriver driver, String oldUrl, long timeoutInSeconds)  {
		boolean urlChanged = false;
		String newUrl = null;
		long timeOutMilliSeconds = timeoutInSeconds * 1000;
		long t0 = System.currentTimeMillis();
		long t1 = 0;
		while (true) {
			newUrl = driver.getCurrentUrl();
			urlChanged = !newUrl.equals(oldUrl);
			if (urlChanged) {
				return true;
			}
			t1 = System.currentTimeMillis();
			if (( t1 - t0) > timeOutMilliSeconds){
				return false;
			}
			defaultWait ();
		}		
	}
	
	/**
	 * Wait (sleep) required number of seconds, but handle exceptions
	 * @param seconds
	 */
	public static void waitAdditional(double seconds) {
		if (seconds <= 0) {
			return;
		}
		long milliseconds = (long) (seconds * 1000);
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new WebDriverException(e);
		}	
		
	}
	
	/**
	 * Wait until the given text is displayed not longer than given timeOutInSeconds
	 * @param driver
	 * @param element
	 * @param timeOutInSeconds
	 * @throws TimeoutException if element does not become visible during given timeout
	 */
	
	public static void waitUntilWebElementTextDisplayed(WebDriver driver, final WebElement element, long timeOutInSeconds,final String value) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				 return element.getText() != value;
			}
		}
		);
	}

	
	/**
	 * Wait until the given text is displayed; timeout is taken from BaseTest
	 * @param driver
	 * @param element
	 * @throws TimeoutException if element does not become visible during given timeout
	 */
	public static void waitUntilWebElementTextDisplayed(WebDriver driver, final WebElement element) {
		long timeOut = BaseTest.implicitlyWaitTimeout;
		waitUntilWebElementDisplayed(driver, element, timeOut);
	}
	
	
	
}
