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
 * Test Reference No	: 	A060 Payment Codes
 * Purpose              :   Insert Payment Codes.
 * Date					:   19-06-2014
 * ACCESS               :   GAK
 */

public class A060_Payment_CodesTest extends BaseTest{
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
		List<String> payment01 = dataRow.findNamesReturnValues("payment01");
		List<String> payment02 = dataRow.findNamesReturnValues("payment02");
		List<String> payment03 = dataRow.findNamesReturnValues("payment03");
		List<String> payment04 = dataRow.findNamesReturnValues("payment04");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Payment Code List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createUOM(currencyPage,payment01);
		createUOM(currencyPage,payment02);
		createUOM(currencyPage,payment03);
		createUOM(currencyPage,payment04);
			
		/*Exit from the payment code page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();

		verifyValues(currencyPage,payment01);
		verifyValues(currencyPage,payment02);
		verifyValues(currencyPage,payment03);
		verifyValues(currencyPage,payment04);
		
		currencyPage.logOut(2);

	}
	
	private void createUOM(CurrencyPage currencyPage,List<String> paymentCode) throws InterruptedException{

		/*Create payment code*/
		boolean update  = currencyPage.enterPaymentCode(paymentCode);
		
		if(update == true){
	
		currencyPage.clickOnUpdate();		
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New payment code "+paymentCode.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> paymentCode){
		
		/*Verify new payment code in the list*/
		if(currencyPage.verifyValues(paymentCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New payment code "+paymentCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New payment code "+paymentCode.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A060" };
		String [] selectedNames = {"userName","passWord","currencyCode","company","payment01","payment02","payment03","payment04"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
