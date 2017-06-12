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
 * Test Reference No	: 	A079 Authorisation Groups
 * Purpose              :   Create Authorisation Groups for Purchasing Management
 * Date					:   19-06-2014
 * ACCESS               :   GOP,DBP
 */

public class A082_Authorisation_StructuresTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> authorisationGroupForAP = dataRow.findNamesReturnValues("authorisationGroupForAP");
		List<String> authorisationGroupForPM = dataRow.findNamesReturnValues("authorisationGroupForPM");
		
				
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Authorisation Structure List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,authorisationGroupForAP,2,1);
		
		currencyPage.clickOnInsert();
		
		/*Create batch type code*/
		currencyPage.enterAuthorisationStructureForAP(authorisationGroupForAP);	
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();

		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(authorisationGroupForAP.get(0)), "Authorisation Group  "+authorisationGroupForPM.get(0),"displayed in the list");
			
		currencyPage.clickOnCancel();
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Authorisation Structure List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,authorisationGroupForPM,2,1);
		
		currencyPage.clickOnInsert();
		
		/*Create batch type code*/
		currencyPage.enterAuthorisationStructureForPM(authorisationGroupForPM);	
		
		currencyPage.clickOnUpdate();
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();

		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(authorisationGroupForPM.get(0)), "Authorisation Group  "+authorisationGroupForPM.get(0),"displayed in the list");
	
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
		String[] nodeID = { "A082" };
		String [] selectedNames = {"userName","passWord","currencyCode","authorisationGroupForAP","authorisationGroupForPM"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
