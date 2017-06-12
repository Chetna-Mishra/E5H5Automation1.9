package com.adv.qa.selenium.framework.pageObjects;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adv.qa.selenium.framework.BaseTest;

public class Page {
public  EventFiringWebDriver driver;
public  WebDriver driver1;
	public static final Logger log = LoggerFactory.getLogger(BaseTest.class);
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(EventFiringWebDriver driver) {
		this.driver = driver;
	}

	public Page(EventFiringWebDriver driver) {
		this.driver = driver;
		if(BaseTest.browser.contains("internetexplorer"))
        { 
           try {
               driver.switchTo().alert().accept();}
                 catch(NoAlertPresentException e)
                   {
                       return ;
                    }
                }
	}
	
	public Page(WebDriver driver12) {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return driver.getTitle();
	}
	
	public String getPageSource() {
		return driver.getPageSource();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public void refreshThePage() {
		driver.navigate().refresh();
	}

}
