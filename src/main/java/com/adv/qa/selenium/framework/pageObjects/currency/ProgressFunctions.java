package com.adv.qa.selenium.framework.pageObjects.currency;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.adv.qa.selenium.framework.pageObjects.Page;
import com.adv.qa.selenium.framework.pageObjects.PageObjects;

public class ProgressFunctions extends Page{

	private PageObjects pObject = new PageObjects();
	
	public ProgressFunctions(WebDriver driver1) {
		super(driver1);
		log.info("In Progresso page");
	}
	
	public void enterTextBox(WebDriver driver2, String idName , String value){
		log.info("In enter text box function");
		WebElement wb  = driver2.findElement(By.id(pObject+idName));
		if(wb.isDisplayed() == true){
			wb.sendKeys(value);					
		}				
	}
	
	public void clickButton(){
		log.info("In click button function");
		

	}
	
}
