package com.adv.qa.selenium.tests.currency;

import java.util.List;
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
 * Test Reference No	: 	A015 Balance Sheet Controls
 * Purpose              :   Set Up Normal Balance Sheet Controls
 * Date					:   21-04-2014
 * ACCESS               :   EAT
 */


public class A015_BalanceSheetTest extends BaseTest{

	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{	
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("code");
		List<String> balanceSheetControl = dataRow.findNamesReturnValues("balanceSheetControl");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Create new balance sheet control*/
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Balance Sheet Controls List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,balanceSheetControl,2,1);
		
		/*Insert new balance sheet details*/
		currencyPage.clickOnInsert();
		
		/*Create new balance sheet control*/
		currencyPage.enterBalanceSheetControls(balanceSheetControl);
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the Balance sheet page*/
		currencyPage.clickOnCancel();
		
		/*Verify new Balance control in the balance sheet list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(balanceSheetControl.get(0)), "New balance activity "+balanceSheetControl.get(0),"displayed in the list");

		/*Clear test data*/
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
		String[] nodeID = { "A015" };
		String [] selectedNames = {"userName","passWord","code","balanceSheetControl"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}}
