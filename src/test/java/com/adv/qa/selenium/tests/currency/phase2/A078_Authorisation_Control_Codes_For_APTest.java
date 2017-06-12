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
 * Test Reference No	: 	A078  Authorisation Control Codes (AP)
 * Purpose              :   Create Authorisation Control Codes for Accounts Payable
 * Date					:   24-06-2014
 * ACCESS               :   GOA
 */

public class A078_Authorisation_Control_Codes_For_APTest extends BaseTest{
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
		List<String> authorisationCodeL0 = dataRow.findNamesReturnValues("authorisationCodeL0");
		List<String> authorisationCodeL1 = dataRow.findNamesReturnValues("authorisationCodeL1");
		List<String> authorisationCodeL2 = dataRow.findNamesReturnValues("authorisationCodeL2");
					
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Authorisation Controls List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();
		
		createAuthorisationCode(currencyPage,authorisationCodeL0);
		createAuthorisationCode(currencyPage,authorisationCodeL1);
		createAuthorisationCode(currencyPage,authorisationCodeL2);

		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,authorisationCodeL0);
		verifyValues(currencyPage,authorisationCodeL1);
		verifyValues(currencyPage,authorisationCodeL2);
		
		currencyPage.logOut(2);
	}

	
	private void createAuthorisationCode(CurrencyPage currencyPage,List<String> authorisationControlCode) throws InterruptedException{
		/*Create authorization control code*/
		boolean update = currencyPage.enterAuthorisationControlDetails(authorisationControlCode);			
		
		if(update == true){
			
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New authorisation controls code "+authorisationControlCode.get(0)+ " displayed in the list");
		}
	}

	
	private void verifyValues(CurrencyPage currencyPage,List<String> authorisationControlCode){
		
		/*Verify new authorisation code type in the list*/
		if(currencyPage.verifyValues(authorisationControlCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New authorisation controls code "+authorisationControlCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New authorisation controls code "+authorisationControlCode.get(0)+ " not displayed in the list");
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
		String xmlFilePath = folder  + "A078.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
