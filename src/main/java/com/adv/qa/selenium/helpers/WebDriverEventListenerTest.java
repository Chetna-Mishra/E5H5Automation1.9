package com.adv.qa.selenium.helpers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;

/***
 * Implements WebDriverEventListener and overrides onException method
 * 
 */
public class WebDriverEventListenerTest implements WebDriverEventListener{
	String classNameGlobal;
	
	public WebDriverEventListenerTest(String className){
		classNameGlobal = className;
	}
	
	
@Override
 public void onException(Throwable exception, WebDriver driver) {
  File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
  try 
  {
	  String prefix = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
	  FileUtils.copyFile(screenShot, new File(classNameGlobal+"_"+prefix+".png"));
  } catch (IOException e) {
   e.printStackTrace();
  }
  
 }

public void onException1(WebDriver driver, String className) {
	  System.out.println("Second exception");
	  File screenShot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  try {
	   FileUtils.copyFile(screenShot, new File(classNameGlobal+".png"));
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	  
	 }

@Override
public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
	
	
}

@Override
public void afterClickOn(WebElement arg0, WebDriver arg1) {
	
	
}

@Override
public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
	
	
}

@Override
public void afterNavigateBack(WebDriver arg0) {
	
	
}

@Override
public void afterNavigateForward(WebDriver arg0) {
	
	
}

@Override
public void afterNavigateTo(String arg0, WebDriver arg1) {

}

@Override
public void afterScript(String arg0, WebDriver arg1) {
	
}

@Override
public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
	
}

@Override
public void beforeClickOn(WebElement arg0, WebDriver arg1) {	
	
}

@Override
public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {	
	
}

@Override
public void beforeNavigateBack(WebDriver arg0) {
	
}

@Override
public void beforeNavigateForward(WebDriver arg0) {
	
	
}

@Override
public void beforeNavigateTo(String arg0, WebDriver arg1) {

}

@Override
public void beforeScript(String arg0, WebDriver arg1) {
		
}


}