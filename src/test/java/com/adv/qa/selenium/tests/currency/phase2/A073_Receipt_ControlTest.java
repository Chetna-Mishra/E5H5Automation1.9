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
 * Test Reference No	: 	A073 Receipt Control 
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   19-06-2014
 * ACCESS               :   PX3
 */

public class A073_Receipt_ControlTest extends BaseTest{
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
		List<String> receiptControl = dataRow.findNamesReturnValues("receiptControl");
			
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Receipt Control - List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,receiptControl,2,1);
		
		currencyPage.clickOnInsert();
		
		/*Create batch type code*/
		currencyPage.enterReceiptControlsDetails(receiptControl);	
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();

		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(receiptControl.get(0)), "New receipt control  "+receiptControl.get(0),"displayed in the list");
			
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
		String[] nodeID = { "A073" };
		String [] selectedNames = {"userName","passWord","currencyCode","receiptControl"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
