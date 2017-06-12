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
 * Test Reference No	: 	A022 Balance Classes
 * Purpose              :   Set Up Initial Balance Class
 * Date					:   13-05-2014
 * ACCESS               :   AKS
 */

public class A022_Balance_ClassesTest extends BaseTest{
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

		List<String> firstBalanceClass = dataRow.findNamesReturnValues("firstBalanceClass");
		List<String> secondBalanceClass = dataRow.findNamesReturnValues("secondBalanceClass");
		List<String> thirdBalanceClass = dataRow.findNamesReturnValues("thirdBalanceClass");
		List<String> fourthBalanceClass = dataRow.findNamesReturnValues("fourthBalanceClass");
		List<String> BalanceClassForRS = dataRow.findNamesReturnValues("BalanceClassForRS");
		List<String> BalanceClassForBR = dataRow.findNamesReturnValues("BalanceClassForBR");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Balance Class List","Currency search page","displayed");
		
		/*Verify balance class present in the list*/
		currencyPage.searchEement(companyId, 2);
		
		currencyPage.clickOnInsert();
		
		/*Create users*/
		createBalanceClass(currencyPage,firstBalanceClass);
		createBalanceClass(currencyPage,secondBalanceClass);
		createBalanceClass(currencyPage,thirdBalanceClass);
		createBalanceClass(currencyPage,fourthBalanceClass);
		createBalanceClass(currencyPage,BalanceClassForRS);
		createBalanceClass(currencyPage,BalanceClassForBR);
		
		/*Exit from the company details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		verifyValues(currencyPage,firstBalanceClass);
		verifyValues(currencyPage,secondBalanceClass);
		verifyValues(currencyPage,thirdBalanceClass);
		verifyValues(currencyPage,fourthBalanceClass);
		verifyValues(currencyPage,BalanceClassForRS);
		verifyValues(currencyPage,BalanceClassForBR);
		
		/*Exit from the application*/
		currencyPage.logOut(2);
	}
	
	private void createBalanceClass(CurrencyPage currencyPage,List<String> balanceClass) throws InterruptedException{
		/*Create balance class*/
		boolean update = currencyPage.enterBalanceClass(balanceClass);
			
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New balance class  "+balanceClass.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> balanceClass){		
		/*Verify new balance class in the list*/
		if(currencyPage.verifyValues(balanceClass.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New balance class "+balanceClass.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New balance class "+balanceClass.get(0)+ " not displayed in the list");
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
		String xmlFilePath = folder  + "A022.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
