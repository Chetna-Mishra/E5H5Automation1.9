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
 * Test Reference No	: 	A014 Account Code Definition
 * Purpose              :   Set Up Account Code Definition
 * Date					:   27-05-2014
 * ACCESS               :   AKA
 */

public class A014_Account_Code_DefinitionTest extends BaseTest{

	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp",groups="A014")
	public void verify(DataRow dataRow) throws InterruptedException{	
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("code");
		String accountDefinition = dataRow.get("accountDefinition");
		List<String> accountCodeList = dataRow.findNamesReturnValues("accountCodeList");
		List<String> costList = dataRow.findNamesReturnValues("costList");
		List<String> location = dataRow.findNamesReturnValues("location");
		List<String> product = dataRow.findNamesReturnValues("product");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Create new account code*/
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Account Definition List","Currency search page","displayed");
		
		/*Search account code*/
		currencyPage.searchValue(companyId,1,0);
		
		/*Insert new account code definition*/
		currencyPage.clickOnInsert();
		
		/*Create account code definition */
		currencyPage.enterAccountDefinitionDetails(companyId,accountDefinition,accountCodeList,costList,location,product);
		
		currencyPage.clickOnUpdate();
		
		/*Verify new account code definition in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(accountDefinition), "New account "+accountDefinition,"displayed in the list");
		
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
		String[] nodeID = { "A014" };
		String [] selectedNames = {"userName","passWord","code","accountDefinition","accountCodeList","costList","location","product"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
