package com.adv.qa.selenium.tests.currency;

import javax.xml.parsers.ParserConfigurationException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPage;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	A001 Switch On online auditing
 * Purpose              :   Switch on online auditing
 * Date					:   15-04-2014
 * ACCESS               :   XDJ
 */

public class A001_Switch_Intra_Company_Accounting_OnTest extends BaseTest{	
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException, ParserConfigurationException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String commandParameter = dataRow.get("commandParameter");
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(commandParameter);
		
		/*Verify financial module*/
		Assert.assertTrue(testcases,currencyPage.verifyFinancialModules(),"Financial module fields","checked by default");
		
		/*Verify Purchasing module*/
		Assert.assertTrue(testcases,currencyPage.verifyPurchasingModules(),"Purchasing module fields","checked by default");
		
		/*Logout from the application*/
		currencyPage.logOut(2); 
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{		
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A001" };
		String [] selectedNames = {"userName","passWord","code","commandParameter"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}

}
