package com.adv.qa.selenium.tests.currency.phase3;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestScrnShot {
		
		@Test
		public void runPgm() throws Exception{
			WebDriver driver = new FirefoxDriver();
	        driver.get("http://demo.guru99.com/V4/");		
	        takeSnapShot(driver, "c://"+this.getClass().getSimpleName()+".png");
		}
	
		
		public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{			 
	        //Convert web driver object to TakeScreenshot	 
	        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
	 
	        //Call getScreenshotAs method to create image file
	        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	 
	        //Move image file to new destination
	        File DestFile=new File(fileWithPath);
	 
	        //Copy file at destination	 
	        FileUtils.copyFile(SrcFile, DestFile);
	    }
}
