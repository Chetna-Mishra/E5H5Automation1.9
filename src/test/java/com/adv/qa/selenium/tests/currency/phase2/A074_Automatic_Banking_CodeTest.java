package com.adv.qa.selenium.tests.currency.phase2;

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
 * Test Reference No	: 	A074 Automatic Banking Code 
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   19-06-2014
 * ACCESS               :   GAM
 */

public class A074_Automatic_Banking_CodeTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("currencyCode");
		List<String> bankingCode = dataRow.findNamesReturnValues("bankingCode");
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Automatic Bank Code List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,bankingCode,2,1);
		
		currencyPage.clickOnInsert();
		
		/*Create batch type code*/
		currencyPage.enterAutomaticBankCode(bankingCode,companyId);	
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();

		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(bankingCode.get(0)), "New automatic bank code  "+bankingCode.get(0),"displayed in the list");
			
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
		String[] nodeID = { "A074" };
		String [] selectedNames = {"userName","passWord","currencyCode","bankingCode"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
