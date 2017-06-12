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
 * Test Reference No	: 	A103 IM Accounts
 * Purpose              :   IM Accounts 
  * ACCESS               :   HAG
 */

public class A103_IM_AccountsTest extends BaseTest{
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
		List<String> accountIE = dataRow.findNamesReturnValues("accountIE");
		List<String> accountIW = dataRow.findNamesReturnValues("accountIW");
		List<String> accountIN = dataRow.findNamesReturnValues("accountIN");
		List<String> accountIS = dataRow.findNamesReturnValues("accountIS");
		List<String> accountTE = dataRow.findNamesReturnValues("accountTE");
		List<String> accountTW = dataRow.findNamesReturnValues("accountTW");
		List<String> accountTN = dataRow.findNamesReturnValues("accountTN");
		List<String> accountTS = dataRow.findNamesReturnValues("accountTS");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - IM Control Code List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,accountIE, 2, 1);
		
		currencyPage.clickOnInsert();
		
		createIMAccounts(currencyPage,accountIE);
		createIMAccounts(currencyPage,accountIW);
		createIMAccounts(currencyPage,accountIN);
		createIMAccounts(currencyPage,accountIS);
		createIMAccounts(currencyPage,accountTE);
		createIMAccounts(currencyPage,accountTW);
		createIMAccounts(currencyPage,accountTN);
		createIMAccounts(currencyPage,accountTS);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,accountIE);
		verifyValues(currencyPage,accountIW);
		verifyValues(currencyPage,accountIN);
		verifyValues(currencyPage,accountIS);
		verifyValues(currencyPage,accountTE);
		verifyValues(currencyPage,accountTW);
		verifyValues(currencyPage,accountTN);
		verifyValues(currencyPage,accountTS);
		
		currencyPage.logOut(2);
	}
	
	
	private void createIMAccounts(CurrencyPageNew currencyPage,List<String> elements){
		currencyPage.insertImControl(elements);
		
		 if(currencyPage.isToolTipDisplayed()){
			   currencyPage.clickOnUpdateWarnings();
		 }
		 else{
		   currencyPage.clickOnUpdate();
		 }
	}
	
	private void verifyValues(CurrencyPageNew currencyPage,List<String> elements){
		
		if(!currencyPage.verifyValues(elements.get(0))){
			testcases.add(getCurreentDate()+" | Pass : ICA default trading relatioship "+elements.get(0)+ "  updated");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : ICA default trading relatioship "+elements.get(0)+ "not updated");
		}
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
		String[] nodeID = { "A103" };
		String [] selectedNames = {"userName","passWord","currencyCode","accountIE","accountIW","accountIN","accountIS",
				"accountTE","accountTW","accountTN","accountTS"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
