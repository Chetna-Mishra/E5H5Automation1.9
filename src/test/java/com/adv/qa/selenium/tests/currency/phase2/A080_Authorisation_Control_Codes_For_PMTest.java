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
 * Test Reference No	: 	A080 Authorisation Control Codes (PM)
 * Purpose              :   Create Authorisation Control Codes for Purchasing Management
 * Date					:   24-06-2014
 * ACCESS               :   DBA
 */

public class A080_Authorisation_Control_Codes_For_PMTest extends BaseTest{
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - PM Authorisation Controls List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();
		
		createCirculationCode(currencyPage,authorisationCodeL1);
		createCirculationCode(currencyPage,authorisationCodeL2);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();

		verifyValues(currencyPage,authorisationCodeL1);
		verifyValues(currencyPage,authorisationCodeL2);

		currencyPage.logOut(2);
	}

	
	private void createCirculationCode(CurrencyPage currencyPage,List<String> authorisationControlCode) throws InterruptedException{
		/*Create authorization control code*/
		boolean update = currencyPage.enterAuthorisationControlDetailsForPM(authorisationControlCode);	
		
		if(update == true){
		currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New authorisation controls code "+authorisationControlCode.get(0)+ " present in the list");
		}
		
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> authorisationControlCode){
		/*Verify new authorization controls code in the list*/
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
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A080" };
		String [] selectedNames = {"userName","passWord","currencyCode","authorisationCodeL1","authorisationCodeL2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
