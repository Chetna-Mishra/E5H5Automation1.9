package com.adv.qa.selenium.tests.currency;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPage;

public class Login extends BaseTest{

	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}

	
	@Test
	public void testing() throws InterruptedException{
		String userName = "DaRedma2";
		String passWord = " 299@RaD01009";
		
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		currencyPage.createLearner(userName,passWord);

		currencyPage.createLearner12();
	}
	
	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}

}
