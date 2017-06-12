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
 * Test Reference No	: 	A037 ICA Default Trading Relationships 
 * Purpose              :   Create Default Trading Relationships between BTZ Elements for ICA
 * Date					:   02-05-2014
 * ACCESS               :   AKI
 */

public class A037_ICA_Default_Trading_RelationshipsTest extends BaseTest{
	
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
		List<String> elements = dataRow.findNamesReturnValues("elements");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - ICA Relationship List","Search page","displayed");
		
		currencyPage.searchValue(companyId,elements, 3, 2);
		
		currencyPage.clickOnInsert();
		
		/*Create new ICA trading relationship*/
		currencyPage.enterICARelationShip(elements);
		
		currencyPage.clickOnAcceptWarnings();	
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the ICA details page*/
		currencyPage.clickOnCancel();
		
		/*Verify new ICA in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(elements.get(0)), "New relationship between BTZ element for ICA structure "+elements.get(0),"created");
		
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
		String[] nodeID = { "A037" };
		String [] selectedNames = {"userName","passWord","code","elements"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
