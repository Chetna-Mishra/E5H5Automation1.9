package com.adv.qa.selenium.tests.currency;

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
 * Test Reference No	: 	A001A Switch On online auditing
 * Purpose              :   Switch on online auditing
 * Date					:   15-04-2014
 * ACCESS               :   XDJ
 */

public class A001A_On_line_AuditingTest extends BaseTest{
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
		String tableValue = dataRow.get("tableValue");
		String entityValue = dataRow.get("entityValue");
		String url = dataRow.get("url");		
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Table List","Search page","displayed");
		
		/*Search for Table value and verify presence of value in Table*/
		currencyPage.searchCurrency(tableValue);
		Assert.assertTrue(testcases,currencyPage.verifyValues(entityValue),"Entity "+entityValue,"found");
		
		/*Select entity value*/
		currencyPage.selectEntity(entityValue);
		
		/*Edit entity by clicking on Amend*/
		currencyPage.clickOnAmend();
		
		/*Verify Column definition tab displayed by default*/
		if(!currencyPage.isColumnDefinitionDispayed()){
			currencyPage.clickOnColumnDefinition();
		}
		
		Assert.assertTrue(testcases,currencyPage.isColumnDefinitionDispayed(), "Column definition","displayed");
		
		/*Navigate to Definition Defn tab*/
		currencyPage.clickOnDefinitionDef();
		
		/*Verift Definition Def page/section displayed*/
		Assert.assertTrue(testcases,currencyPage.isDefinitionDefnDispayed(), "Column definition def","displayed");
		
		/*Navigate back to search page*/
		currencyPage.clickOnCancel();
		
		/*Logout from the application*/
		currencyPage.logOut(2); 
		
		/*Launch application for second test data*/
		driver.get(url);	
	}
	

	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A001A.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
