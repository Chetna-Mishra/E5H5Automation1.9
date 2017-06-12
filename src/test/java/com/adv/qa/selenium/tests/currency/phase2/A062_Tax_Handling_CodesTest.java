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
 * Test Reference No	: 	A062 Tax Handling Codes
 * Purpose              :   Set Up Taxation Handling Codes
 * Date					:   19-06-2014
 * ACCESS               :   RAF
 */

public class A062_Tax_Handling_CodesTest extends BaseTest{
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
		List<String> taxR1 = dataRow.findNamesReturnValues("taxR1");
		List<String> tax02 = dataRow.findNamesReturnValues("tax02");
		List<String> tax03 = dataRow.findNamesReturnValues("tax03");
		List<String> taxH1 = dataRow.findNamesReturnValues("taxH1");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Tax Handling List","Currency search page","displayed");
			
		currencyPage.clickOnInsert();
		
		createPurchasingManagement(currencyPage,taxR1);
		createPurchasingManagement(currencyPage,tax02);
		createPurchasingManagement(currencyPage,tax03);
		createPurchasingManagement(currencyPage,taxH1);

		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,taxR1);
		verifyValues(currencyPage,tax02);
		verifyValues(currencyPage,tax03);
		verifyValues(currencyPage,taxH1);
		
		currencyPage.logOut(2);

	}
	
	private void createPurchasingManagement(CurrencyPage currencyPage,List<String> location) throws InterruptedException{
		
		/*Create batch type code*/
		boolean update = currencyPage.enterTaxHandlingDetails(location);	
	
		if(update == true){
		
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : Tax handling "+location.get(0)+" present in the list");
		}

	}
		
	private void verifyValues(CurrencyPage currencyPage,List<String> location){
		currencyPage.searchValue(location.get(0),1,0);
		
		if(currencyPage.verifyValues(location.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Tax handling "+location.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Tax handling "+location.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A062" };
		String [] selectedNames = {"userName","passWord","currencyCode","taxR1","tax02","tax03","taxH1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
