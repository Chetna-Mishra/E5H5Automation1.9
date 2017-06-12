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
 * Test Reference No	: 	A059 Bank Payment Method
 * Purpose              :   Insert Bank Payment Methods.
 * Date					:   19-06-2014
 * ACCESS               :   GAO
 */

public class A059_Bank_Payment_MethodTest extends BaseTest{
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Bank/Payment Method List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createBankPaymentMethod(currencyPage,payment01);
		createBankPaymentMethod(currencyPage,payment02);
			
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,payment01);
		verifyValues(currencyPage,payment02);
		
		currencyPage.logOut(2);

	}
	
	private void createBankPaymentMethod(CurrencyPage currencyPage,List<String> paymentCode) throws InterruptedException{

		/*Create batch type code*/
		boolean update = currencyPage.enterBankPayMethods(paymentCode);
	
		if(update == true){
			
			currencyPage.clickOnUpdate();		
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New bank payment method "+paymentCode.get(1)+ " displayed in the list");
		}

	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> paymentCode){
		
		if(currencyPage.verifyValues(paymentCode.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New bank payment method "+paymentCode.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New bank payment method "+paymentCode.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A059" };
		String [] selectedNames = {"userName","passWord","currencyCode","company","payment01","payment02"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
