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
 * Test Reference No	: 	A047 Accounts Payable Batch Types
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   16-05-2014
 * ACCESS               :   E02
 */

public class A051_Purchase_Ledger_Bank_CodesTest extends BaseTest{
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

		List<String> bankCodeBP = dataRow.findNamesReturnValues("bankCodeBP");
		List<String> bankCodeB1 = dataRow.findNamesReturnValues("bankCodeB1");
		List<String> bankCodeH1 = dataRow.findNamesReturnValues("bankCodeH1");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Bank Account Code List","Currency search page","displayed");
	
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createBankCode(currencyPage,bankCodeBP);
		createBankCode(currencyPage,bankCodeB1);
		createBankCode(currencyPage,bankCodeH1);
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();		
		
		verifyValues(currencyPage,bankCodeBP);
		verifyValues(currencyPage,bankCodeB1);
		verifyValues(currencyPage,bankCodeH1);

		currencyPage.logOut(2);

	}
	
	private void createBankCode(CurrencyPage currencyPage,List<String> bankCode) throws InterruptedException{
		boolean update = false;
		/*Create batch type code*/
		update = currencyPage.createBankAccount(bankCode);
			
		if(update==true){	
			currencyPage.clickOnUpdtWarnings();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New bank code  "+bankCode.get(0)+ " created");
		}
	}

	private void verifyValues(CurrencyPage currencyPage,List<String> bankCode){
		
		if(currencyPage.verifyValues(bankCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New bank code "+bankCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New bank code "+bankCode.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A051" };
		String [] selectedNames = {"userName","passWord","currencyCode","company","bankCodeBP","bankCodeB1","bankCodeH1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
