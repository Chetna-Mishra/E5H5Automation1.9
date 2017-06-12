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
 * Test Reference No	: 	A046  Bank Account Names for Purchasing
 * Purpose              :   Insert Bank Account Names for Purchasing
 * Date					:   12-06-2014
 * ACCESS               :   AKY
 */

public class A046_Bank_Account_Names_for_PurchasingTest extends BaseTest{
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
		String bankName = dataRow.get("bankName");
		List<String> bankName1 = dataRow.findNamesReturnValues("bankName1");
		List<String> bankName2 = dataRow.findNamesReturnValues("bankName2");
		List<String> bankName3 = dataRow.findNamesReturnValues("bankName3");
			
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

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Bank Sort Codes List","Currency search page","displayed");

		currencyPage.searchValue(bankName, 2, 0);
		
		currencyPage.clickOnInsert();
		
		/*Create bank account name*/
		createBankAccount(currencyPage,bankName1);
		createBankAccount(currencyPage,bankName2);
		createBankAccount(currencyPage,bankName3);
		
		currencyPage.clickOnCancel();
	
		/*Verify bank account name*/
		verifyBankAccount(currencyPage,bankName1);
		verifyBankAccount(currencyPage,bankName2);
		verifyBankAccount(currencyPage,bankName3);
		
		currencyPage.logOut(2);
	}

	/*Create bank account name*/
	private void createBankAccount(CurrencyPage currencyPage,List<String> bankName) throws InterruptedException{
		/*Create bank account name*/
		boolean update = currencyPage.bankSortCode(bankName); 
		
		if(update){
			currencyPage.clickOnUpdate();
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New bank code  "+bankName.get(1)+ " displayed in the list");
		}
	}
	
	/*Verify bank account name*/
	private void verifyBankAccount(CurrencyPage currencyPage,List<String> bankName) throws InterruptedException{
			
		if(currencyPage.verifyValues(bankName.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New bank code "+bankName.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New bank code "+bankName.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A046" };
		String [] selectedNames = {"userName","passWord","currencyCode","bankName","bankName1","bankName2","bankName3"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
