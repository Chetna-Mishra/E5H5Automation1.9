package com.adv.qa.selenium.framework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.adv.qa.selenium.helpers.WaitHelper;

public class LoginPage extends Page{

	public LoginPage(EventFiringWebDriver driver) {
		super(driver);
		log.info("In Login Page");
	}

	private PageObjects pObject = new PageObjects();
	
	private WebElement getUserName(){
		return driver.findElement(By.id(pObject.ZERO_+pObject.ZERO));
	}
	
	private WebElement getPassword(){
		return driver.findElement(By.id(pObject.ZERO_+pObject.ONE));
	}
	
	private WebElement getRetriveAndClear(){
		return driver.findElement(By.id(pObject.ZERO_+pObject.SIX));//EVEN+pObject.LABEL));
	}
	
	public void logIn(String userName,String passWord){
		getUserName().sendKeys(userName);
		getPassword().sendKeys(passWord);
		getRetriveAndClear().click();
		
//		driver.findElement(By.id("0_1")).sendKeys("BM");
//		 driver.findElement(By.id("0_2")).sendKeys("BM");
//		 driver.findElement(By.id("0_4_label")).click();
//		 WaitHelper.waitAdditional(4);
//		 cpage.clickOnCancel();
		WaitHelper.waitAdditional(4);
		log.info("Successfully logged in");
	}
	
	public boolean isElementPresent(){
		return getUserName().isDisplayed();
	}
	
	public boolean isLoginPageDisplayed(){
		boolean loginPage = false; 
		if(isElementPresent()){
			loginPage = true;
		}
		return loginPage;
	}
}
