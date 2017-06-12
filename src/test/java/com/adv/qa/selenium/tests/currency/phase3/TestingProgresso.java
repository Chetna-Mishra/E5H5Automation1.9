package com.adv.qa.selenium.tests.currency.phase3;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.PageObjects;
import com.adv.qa.selenium.framework.pageObjects.currency.ProgressFunctions;

import com.thoughtworks.selenium.*;

public class TestingProgresso extends SeleneseTestNgHelper{
		
//	ScriptEngineManager manager = new ScriptEngineManager();
//    ScriptEngine engine = manager.getEngineByName("JavaScript");
    
	private PageObjects pObject = new PageObjects();
	public WebDriver driver;	
		@Test
		public void runPgm() throws Exception{
			driver = new FirefoxDriver();
	        driver.get("http://oasaasweb.azurewebsites.net/#/login");		
	        Thread.sleep(1000);

	        driver.findElement(By.id(pObject.pUserName)).sendKeys("mk");
	        driver.findElement(By.id(pObject.pPassword)).sendKeys("root");
	        
	        Thread.sleep(1000);
	        driver.findElement(By.className("btn")).click();
	        Thread.sleep(1000);

	        selenium.type("document.costCodeForm.elements[2]", "oaqa");
//	        driver.findElement(By.xpath("//body/nav/div[2]/div/ul/li[3]/span")).click();
//	        Thread.sleep(300);
//	        driver.findElement(By.xpath("//body/nav/div[2]/div/ul/li[3]/div/ul/li[1]/span")).click();
//	        Thread.sleep(300);
//	        driver.findElement(By.xpath("//body/nav/div[2]/div/ul/li[3]/div/ul/li[1]/div/ul/li[1]/span")).click();
//	        Thread.sleep(300);
//	        driver.findElement(By.xpath("//body/nav/div[2]/div/ul/li[3]/div/ul/li[1]/div/ul/li[1]/div/ul/li[1]/a")).click();
//	        Thread.sleep(300);
//	        
////	        List<WebElement> wbs = driver.findElements(By.className("bg-primary"));
////	        System.out.println(wbs.size());
//	        
//	        driver.close();
//	        driver.findElement(By.xpath("//div/ul/li/a/span")).click();
//	        driver.findElement(By.xpath("//div[2]/div/ul/li[3]/span/span")).click();
//	        driver.findElement(By.xpath("//li[3]/div/ul/li/span")).click();
//	        driver.findElement(By.xpath("//li[3]/div/ul/li/div/ul/li/span")).click();
//	        driver.findElement(By.linkText("Transaction Maintenance")).click();
	        
//	        for(WebElement wb : wbs){
////	        	if(wb.getText().equals("Accounts")){
//	        		wb.getText();
////	        	}
//	        }
//	        
//	        List<WebElement> wbs1 = driver.findElements(By.className("k-link"));
//	        System.out.println(wbs1.size());
//	        for(WebElement wb1 : wbs1){
//	        	if(wb1.getText().equals("GL")){
//	        		wb1.click();
//	        	}
//	        }
	        
//	        takeSnapShot(driver, "c://"+this.getClass().getSimpleName()+".png");
		}
	
		public void enterTextBox(String idName , String value){
			System.out.println(pObject.pUserName);
			Object s = eval("pUserName");
			WebElement wb  = driver.findElement(By.id(pObject.pUserName));
			if(wb.isDisplayed() == true){
				wb.sendKeys(value);					
			}				
		}
//		public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{			 
//	        //Convert web driver object to TakeScreenshot	 
//	        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
//	 
//	        //Call getScreenshotAs method to create image file
//	        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//	 
//	        //Move image file to new destination
//	        File DestFile=new File(fileWithPath);
//	 
//	        //Copy file at destination	 
//	        FileUtils.copyFile(SrcFile, DestFile);
//	    }

		private Object eval(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}
