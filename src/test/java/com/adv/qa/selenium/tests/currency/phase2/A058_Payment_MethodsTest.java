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
 * Test Reference No	: 	A058 Payment Methods
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   19-06-2014
 * ACCESS               :   GAI
 */

public class A058_Payment_MethodsTest extends BaseTest{
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Payment Method List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createPaymentMethods(currencyPage,payment01);
		createPaymentMethods(currencyPage,payment02);
			
		/*Exit from payment method page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();	

		verifyValues(currencyPage,payment01);
		verifyValues(currencyPage,payment02);
		
		currencyPage.logOut(2);
	}
	
	private void createPaymentMethods(CurrencyPage currencyPage,List<String> paymentCode) throws InterruptedException{
		/*Create payment method*/
		boolean update = currencyPage.enterPaymentMethod(paymentCode);	
		
		if(update == true){		
			currencyPage.clickOnUpdate();			
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New payment method  "+paymentCode.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> paymentCode){
		/*Verify payment method in the list*/
		if(currencyPage.verifyValues(paymentCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New payment method "+paymentCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New payment method "+paymentCode.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A058" };
		String [] selectedNames = {"userName","passWord","currencyCode","payment01","payment02"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
