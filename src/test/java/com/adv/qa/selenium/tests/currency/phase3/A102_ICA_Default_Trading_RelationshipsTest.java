package com.adv.qa.selenium.tests.currency.phase3;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPageNew;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	A102 ICA Default Trading Relationships
 * Purpose              :   Add Control ICA Control Accounts for POP and AP 
 * ACCESS               :   AKI
 */

public class A102_ICA_Default_Trading_RelationshipsTest extends BaseTest{
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
		List<String> controlAccounts = dataRow.findNamesReturnValues("controlAccounts");		
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - ICA Relationship List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,controlAccounts, 3, 1);
		
		currencyPage.clickOnAmend();
		
		currencyPage.amendICATradingRelationship(controlAccounts);
		
		 if(currencyPage.isToolTipDisplayed()){
			   currencyPage.clickOnUpdateWarnings();
		 }
		 else{
		   currencyPage.clickOnUpdate();
		 }
		
		if(currencyPage.verifyValues(controlAccounts.get(0))){
			testcases.add(getCurreentDate()+" | Pass : ICA default trading relatioship "+controlAccounts.get(0)+ "  updated");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : ICA default trading relatioship "+controlAccounts.get(0)+ "not updated");
		}

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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "A102" };
		String [] selectedNames = {"userName","passWord","currencyCode","controlAccounts"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
