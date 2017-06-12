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
 * Test Reference No	: 	A083 Structure Authorisers (AP & PM)
 * Purpose              :   Create Structure Authorisers for Accounts Payable
 * Date					:   24-06-2014
 * ACCESS               :   GOS,DBS
 */

public class A083_Structure_AuthorisersTest extends BaseTest{
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
		List<String> values = dataRow.findNamesReturnValues("values");
		List<String> apStructureForQuery = dataRow.findNamesReturnValues("apStructureForQuery");
		List<String> apStructureForManager = dataRow.findNamesReturnValues("apStructureForManager");
		List<String> apStructureForAdmin = dataRow.findNamesReturnValues("apStructureForAdmin");
		List<String> apStructureForEmployee = dataRow.findNamesReturnValues("apStructureForEmployee");
		List<String> apStructureForUser = dataRow.findNamesReturnValues("apStructureForUser");	
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Structure Authorisor List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,values, 4, 1);
		
		currencyPage.clickOnInsert();
		
		createStructureAuthorisers(currencyPage,apStructureForQuery);
		createStructureAuthorisers(currencyPage,apStructureForManager);
		createStructureAuthorisers(currencyPage,apStructureForAdmin);
		createStructureAuthorisers(currencyPage,apStructureForEmployee);
		createStructureAuthorisers(currencyPage,apStructureForUser);

		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,apStructureForQuery);
		verifyValues(currencyPage,apStructureForManager);
		verifyValues(currencyPage,apStructureForAdmin);
		verifyValues(currencyPage,apStructureForEmployee);
		verifyValues(currencyPage,apStructureForUser);
		
		currencyPage.logOut(2);
	}

	
	private void createStructureAuthorisers(CurrencyPage currencyPage,List<String> structureAuthorisers) throws InterruptedException{
		currencyPage.enterStructureAuthorisorForAP(structureAuthorisers);

		currencyPage.clickOnUpdate();
	}

	private void verifyValues(CurrencyPage currencyPage,List<String> structureAuthorisers){
		/*Verify new structure authorizers displayed in the list*/
		if(currencyPage.verifyValues(structureAuthorisers.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New structure authorizers "+structureAuthorisers.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New structure authorizers "+structureAuthorisers.get(1)+ " not displayed in the list");
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
		String xmlFilePath = folder  + "A083.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
