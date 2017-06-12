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
 * Test Reference No	: 	A020 Ledger Control Accounts
 * Purpose              :   Set Up Ledger Control Accounts
 * Date					:   29-04-2014
 * ACCESS               :   EAC
 */

public class A020_Ledger_Control_AccountsTest extends BaseTest{
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
		List<String> ledgerControlList = dataRow.findNamesReturnValues("ledgerControlList");
	
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Ledger Control Account List","Currency search page","displayed");
		
		currencyPage.searchElement(companyId,ledgerControlList,3);
		
		currencyPage.clickOnInsert();
		
		/*Create ledger control account*/
		currencyPage.enterLedgerControlDetails(ledgerControlList);	
		
		currencyPage.clickOnUpdtWarnings();
		
		/*Exit from the ledger account*/
		currencyPage.clickOnCancel();

		currencyPage.isConfirmPopUpDisplayed();
		
		/*Verify new ledger control account displayed in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(ledgerControlList.get(0)), "Ledger control account "+ledgerControlList.get(0),"displayed in the list");
		
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
		String[] nodeID = { "A020" };
		String [] selectedNames = {"userName","passWord","code","ledgerControlList"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
