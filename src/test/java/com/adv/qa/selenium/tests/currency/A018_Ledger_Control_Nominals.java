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

public class A018_Ledger_Control_Nominals extends BaseTest{
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
		
		List<String> nominal2500 = dataRow.findNamesReturnValues("nominal2500");
		List<String> nominal2600 = dataRow.findNamesReturnValues("nominal2600");
		List<String> nominal3000 = dataRow.findNamesReturnValues("nominal3000");
		List<String> nominal1500 = dataRow.findNamesReturnValues("nominal1500");
		
		List<String> nominalGLICA = dataRow.findNamesReturnValues("nominalGLICA");
		List<String> nominal1550 = dataRow.findNamesReturnValues("nominal1550");
		List<String> nominal6300 = dataRow.findNamesReturnValues("nominal6300");
		List<String> nominal6400 = dataRow.findNamesReturnValues("nominal6400");
		List<String> nominalAKAI = dataRow.findNamesReturnValues("nominalAKAI");
		
		
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
		
		createNominal(nominal2500,currencyPage);
		createNominal(nominal2600,currencyPage);
		createNominal(nominal3000,currencyPage);
		createNominal(nominal1500,currencyPage);
		createNominal(nominalGLICA,currencyPage);
		createNominal(nominal1550,currencyPage);
		createNominal(nominal6300,currencyPage);
		createNominal(nominal6400,currencyPage);
		createNominal(nominalAKAI,currencyPage);
	
		/*Exit from the company details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.logOut(2);
	}
	
	private void createNominal(List<String> nominal,CurrencyPage currencyPage) throws InterruptedException{
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
		String xmlFilePath = folder  + "A018.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
