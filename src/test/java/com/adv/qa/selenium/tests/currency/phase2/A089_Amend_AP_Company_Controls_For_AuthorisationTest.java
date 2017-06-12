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
 * Test Reference No	: 	A089 Amend AP Company Controls for Authorisation
 * Purpose              :   Amend CP Company Controls.
 * Date					:   24-06-2014
 * ACCESS               :   GAA
 */

public class A089_Amend_AP_Company_Controls_For_AuthorisationTest extends BaseTest{
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
		List<String> apCompanyControl = dataRow.findNamesReturnValues("apCompanyControl");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - AP Company Controls List","Currency search page","displayed");

		currencyPage.searchValue(companyId,1,0);
		
		currencyPage.clickOnAmend();
		
		/*Create batch type code*/
		currencyPage.editAPCompanyControl(apCompanyControl);	
		
		 if(currencyPage.isToolTipDisplayed()){
			   currencyPage.clickOnUpdtWarnings();
		 }
		 else{
		   currencyPage.clickOnUpdate();
		 }
	
		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(companyId), "New purchasing company  "+companyId," updated");

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
		String[] nodeID = { "A089" };
		String [] selectedNames = {"userName","passWord","currencyCode","apCompanyControl"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
