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
 * Test Reference No	: 	A018 Ledger Control Nominals
 * Purpose              :   Set Up Ledger Control Nominals
 * Date					:   22-04-2014
 * ACCESS               :   EBA
 */

public class A018A_Ledger_Control_Nominals extends BaseTest{
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

		List<String> nominal6500 = dataRow.findNamesReturnValues("nominal6500");		
		List<String> nominalBANKR = dataRow.findNamesReturnValues("nominalBANKR");
		List<String> nominalBANKR1 = dataRow.findNamesReturnValues("nominalBANKR1");
		List<String> nominalBANKR2 = dataRow.findNamesReturnValues("nominalBANKR2");
		List<String> nominal2000 = dataRow.findNamesReturnValues("nominal2000");
		List<String> nominal5000 = dataRow.findNamesReturnValues("nominal5000");	
		List<String> nominal5050 = dataRow.findNamesReturnValues("nominal5050");
		List<String> nominal2150 = dataRow.findNamesReturnValues("nominal2150");
		List<String> nominalSTKR = dataRow.findNamesReturnValues("nominalSTKR");

		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Nominal List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();

		createNominal(nominal2000,dataRow,currencyPage);
		createNominal(nominal5000,dataRow,currencyPage);
		createNominal(nominal5050,dataRow,currencyPage);
		createNominal(nominal2150,dataRow,currencyPage);		
		createNominal(nominal6500,dataRow,currencyPage);
		createNominal(nominalBANKR,dataRow,currencyPage);
		createNominal(nominalBANKR1,dataRow,currencyPage);
		createNominal(nominalBANKR2,dataRow,currencyPage);
		createNominal(nominalSTKR,dataRow,currencyPage);
		
		/*Exit from the company details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.logOut(2);

	}
	
	private void createNominal(List<String> nominal,DataRow dataRow,CurrencyPage currencyPage) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		
		/*Create new nominal control*/
		boolean update = currencyPage.enterNominalControl(nominal);
		if(update == true){
			currencyPage.clickOnUpdate();	
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : New ledger control "+nominal.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New ledger control "+nominal.get(0)+ " not displayed in the list");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New ledger control "+nominal.get(0)+ " displayed in the list");
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
		String xmlFilePath = folder  + "A018A.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
