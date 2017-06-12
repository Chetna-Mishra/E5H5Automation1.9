package com.adv.qa.selenium.framework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.helpers.WaitHelper;

public class HomePage extends Page{

	private BaseTest baseTest = new BaseTest();
	private PageObjects pObject = new PageObjects();
	
	public HomePage(EventFiringWebDriver driver) {
		super(driver);
	}
	
	/**
	 * Get OK text 
	 * @return
	 */
	public String getOKText(){
		return getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.LABEL)).getText();
	}
	
	/**
	 * Verify presence of command line  
	 * @return true\false
	 */
	public boolean isCommandDisplayed(){
		boolean coomand = false;
		try{
			return getDriver().findElement(By.name("COMMAND_field_1")).isDisplayed();
		}
		catch (NoSuchElementException e){
			baseTest.clickOnCancel();
		}
		return coomand;
	}
	
	/**
	 * Enter currency code
	 * @param code
	 * @throws InterruptedException
	 */
	public void fillCurrenceyCode(String code) throws InterruptedException{
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(code);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on OK button
	 * @throws InterruptedException
	 */
	public void clickOnOk() throws InterruptedException{
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.LABEL)).click();
	}

}
