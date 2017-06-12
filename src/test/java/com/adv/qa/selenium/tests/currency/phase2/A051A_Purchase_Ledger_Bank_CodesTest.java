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

public class A051A_Purchase_Ledger_Bank_CodesTest extends BaseTest{
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
		List<String> bankControl = dataRow.findNamesReturnValues("bankControl");
		List<String> nominalBANKR = dataRow.findNamesReturnValues("nominalBANKR");
		List<String> nominalBANKR1 = dataRow.findNamesReturnValues("nominalBANKR1");
		List<String> nominalBANKR2 = dataRow.findNamesReturnValues("nominalBANKR2");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - BR Company Controls List","Currency search page","displayed");

		currencyPage.searchValue(companyId,1,0);
		
		if(!currencyPage.verifyValues(companyId)){
		
			currencyPage.clickOnInsert();
			
			currencyPage.createBRCompanyControl(companyId,bankControl);
			
			currencyPage.clickOnUpdate();
			
			currencyPage.clickOnCancel();
			
			if(currencyPage.verifyValues(companyId)){
				testcases.add(getCurreentDate()+" | Pass : New purchase ledger bank code "+companyId+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New purchase ledger bank code "+companyId+ " not displayed in the list");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New purchase ledger bank code "+companyId+ " displayed in the list");
		}
		
		currencyPage.clickOnCancel();
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Nominal List","Currency search page","displayed");

		amendNominal(nominalBANKR,currencyPage);
		amendNominal(nominalBANKR1,currencyPage);
		amendNominal(nominalBANKR2,currencyPage);
		
		currencyPage.logOut(2);

	}
	
	private void amendNominal(List<String> nominal,CurrencyPage currencyPage) throws InterruptedException{
		currencyPage.searchValue(companyId,nominal,2,1);
		
		if(currencyPage.verifyValues(nominal.get(0))){
			currencyPage.clickOnAmend();
			
			currencyPage.editBatchTypes();
			
			currencyPage.clickOnUpdate();
			
			if(currencyPage.verifyValues(nominal.get(0))){
				testcases.add(getCurreentDate()+" | Pass : New ledger control "+nominal.get(0)+ " updated");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New ledger control "+nominal.get(0)+ " not updated");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New ledger control "+nominal.get(0)+ " updated");
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
		String[] nodeID = { "A051A" };
		String [] selectedNames = {"userName","passWord","currencyCode","bankControl","nominalBANKR","nominalBANKR1","nominalBANKR2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
