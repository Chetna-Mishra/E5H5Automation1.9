package com.adv.qa.selenium.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.adv.qa.selenium.framework.BaseTest;


public class ScreenshotListener  implements IInvokedMethodListener {

	private static final Logger log = LoggerFactory.getLogger(ScreenshotListener.class);

	@Override
	public void afterInvocation(IInvokedMethod m, ITestResult result) {
		System.out.println("Inside screenshot listner");
		if (m.isTestMethod()) {
			onFailure(result);
			ITestNGMethod method = m.getTestMethod();
			String fullClassName = method.getTestClass().getName();
			String className = fullClassName.substring(fullClassName.lastIndexOf('.')+1);
			String methodName = method.getMethodName();
			String prefix = className + "." + methodName + "_" ;
			if (! result.isSuccess()) {
				log.info(" Test method failed: " + className + "." + methodName);
				try {
					BaseTest baseTest = new BaseTest();
					baseTest.takeScreenshot(prefix, fullClassName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				log.info(" Test method completed successfully: "  + className + "." + methodName);
			}
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod m, ITestResult resultsss) {
		if (m.isTestMethod()) {
			log.info(" Test method started: " + m.getTestMethod().getMethodName());
		}
	}
	
	/**
	* Override this method 
	* @param result
	*/
	public void onFailure(ITestResult result) {
		 onFailureForSelenium2(result);
	}
	
	public void onFailureForSelenium2(ITestResult result) {
		Throwable throwable = result.getThrowable();
		String stacktrace = null;
		if(throwable != null){
			stacktrace = ExceptionUtils.getStackTrace(throwable);
			 log.error(stacktrace);
		}
	}
}
