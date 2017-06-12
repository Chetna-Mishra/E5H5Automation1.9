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
 * Test Reference No	: 	A053 Category Code
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   19-05-2014
 * ACCESS               :   PAD
 */

public class A052_Tax_AccountsTest extends BaseTest{
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
		String system = dataRow.get("system");
		String code = "EDTTACCT ACT=AMEND,CMPY="+companyId+",SYS="+system;
		
		List<String> codeUKE = dataRow.findNamesReturnValues("codeUKE");
		List<String> codeUKS = dataRow.findNamesReturnValues("codeUKS");
		List<String> codeUKV = dataRow.findNamesReturnValues("codeUKV");
		List<String> codeUKZ = dataRow.findNamesReturnValues("codeUKZ");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(code);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Tax Accounts By Tax Code","Currency search page","displayed");
		
		/*Create batch type code*/
		currencyPage.enterTaxAccountDetails(codeUKE,codeUKS,codeUKV,codeUKZ);	
		
		currencyPage.clickOnUpdateWarnings();
		
		currencyPage.clickOnUpdate();

		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(), "New  Tax AccoUnt "+system,"displayed in the list");
		
		currencyPage.logOut(1);

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
		String[] nodeID = { "A052" };
		String [] selectedNames = {"userName","passWord","code","currencyCode","system","codeUKE","codeUKS","codeUKV","codeUKZ"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	 
	}
}
