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
 * Test Reference No	: 	A006 Company Definition
 * Purpose              :   Set Up Company Definition
 * Date					:   15-04-2014
 * ACCESS               :   AAE
 * 
 */

public class A006_Company_DefinitionTest extends BaseTest{
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
		List<String> companyDetails = dataRow.findNamesReturnValues("companyDetails");
		
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Company List","Search page","displayed");
		
		currencyPage.searchValue(companyId,2,1);
		
		/*Insert new company details*/
		currencyPage.clickOnInsert();
				
		if(!currencyPage.isPrimaryDetailsTabDisplayed()){
			currencyPage.clickOnPrimaryDetailsTab();
		}
		
		Assert.assertTrue(testcases,currencyPage.isPrimaryDetailsTabDisplayed(), "Primary Details Tab","displayed");
		/*Enter company primary details*/
		currencyPage.enterPrimaryDetails(companyId,companyDetails);
		
		/*Navigate to currency intrastat*/
		currencyPage.clickOnSecondTab();
		
		Assert.assertTrue(testcases,currencyPage.isSecondTabDisplayed(), "Currency Intrastat Tab","displayed");
		
		/*Enter company currency intrastat details*/
		currencyPage.enterCurrencyIntrastatDetails(companyDetails);
		
		currencyPage.clickOnUpdate();
				
		/*Exit from the company details page*/
		currencyPage.clickOnCancel();
				
		/*Verify new company in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(companyId), "New company id "+companyId,"displayed in the list");
			
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
		String[] nodeID = { "A006" };
		String [] selectedNames = {"userName","passWord","code","companyDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
