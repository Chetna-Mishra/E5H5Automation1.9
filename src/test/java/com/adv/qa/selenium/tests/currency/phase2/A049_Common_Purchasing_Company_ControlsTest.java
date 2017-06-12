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
 * Test Reference No	: 	A049 Common Purchasing Company Controls
 * Purpose              :   Insert Common Purchasing Company Controls.
 * Date					:   23-06-2014
 * ACCESS               :   PAB and PAA
 */

public class A049_Common_Purchasing_Company_ControlsTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String code = "EDTPCNTRL ACT=INSERT,COMPANY="+companyId;
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> purchasingCompany = dataRow.findNamesReturnValues("purchasingCompany");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Common Purchasing Company Controls Edit","Currency search page","displayed");
		
		/*Create batch type code*/
		currencyPage.enterPurchasingCompanyControlDetails(companyId,purchasingCompany);	
		
		currencyPage.clickOnUpdate();

		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Purchasing Company Controls Li","Currency search page","displayed");

		currencyPage.searchValue(companyId,1,0);
	
		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(companyId), "New purchasing company  "+companyId,"displayed in the list");

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
		String[] nodeID = { "A049" };
		String [] selectedNames = {"userName","passWord","code","currencyCode","purchasingCompany"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
