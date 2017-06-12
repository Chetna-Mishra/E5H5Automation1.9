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
 * Test Reference No	: 	A004 Currency Exchange Rate Types
 * Purpose              :   Set Up Currency Exchange Rate Types
 * Date					:   24-04-2014
 * ACCESS               :   AGG
 */

public class A004_Currency_Exchange_Rate_TypesTest extends BaseTest{
		
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
		List<String> ukpusdCurrency = dataRow.findNamesReturnValues("ukpusdCurrency");
		List<String> ukpfrfCurrency = dataRow.findNamesReturnValues("ukpfrfCurrency");
		List<String> ukpdemCurrency = dataRow.findNamesReturnValues("ukpdemCurrency");
		List<String> ukpeurCurrency = dataRow.findNamesReturnValues("ukpeurCurrency");
		List<String> eurukpCurrency = dataRow.findNamesReturnValues("eurukpCurrency");
		List<String> demukpCurrency = dataRow.findNamesReturnValues("demukpCurrency");
		List<String> frfukpCurrency = dataRow.findNamesReturnValues("frfukpCurrency");
				
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Exchange Rate Type List","Currency search page","displayed");
		
		currencyPage.clickOnInsert();
		
		/*Create currency exchange rate*/
		createCurrencyExchangeRate(currencyPage,ukpusdCurrency);
		createCurrencyExchangeRate(currencyPage,ukpfrfCurrency);
		createCurrencyExchangeRate(currencyPage,ukpdemCurrency);
		createCurrencyExchangeRate(currencyPage,ukpeurCurrency);
		createCurrencyExchangeRate(currencyPage,eurukpCurrency);
		createCurrencyExchangeRate(currencyPage,demukpCurrency);
		createCurrencyExchangeRate(currencyPage,frfukpCurrency);
				
		/*Exit from the currency exchange details page*/
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,ukpusdCurrency);
		verifyValues(currencyPage,ukpfrfCurrency);
		verifyValues(currencyPage,ukpdemCurrency);
		verifyValues(currencyPage,ukpeurCurrency);
		verifyValues(currencyPage,eurukpCurrency);
		verifyValues(currencyPage,demukpCurrency);
		verifyValues(currencyPage,frfukpCurrency);

		/*Logout from the application*/
		currencyPage.logOut(2);
	
	}
	
	private void createCurrencyExchangeRate(CurrencyPage currencyPage,List<String> currencyList) throws InterruptedException{		
		/*Create currency exchange code*/
		boolean update = currencyPage.enterCurrencyExchangeDetails(currencyList);	

		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : Currency "+currencyList.get(1)+ " displayed in the list");
		}
	}

	private void verifyValues(CurrencyPage currencyPage,List<String> currencyList){
		currencyPage.searchValue(currencyList,2,1);
		
		/*Verify new currency record in the list*/
		if(currencyPage.verifyValues(currencyList.get(1))){
			testcases.add(getCurreentDate()+" | Pass : Currency "+currencyList.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Currency "+currencyList.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A004" };
		String [] selectedNames = {"userName","passWord","code","ukpusdCurrency","ukpfrfCurrency","ukpdemCurrency","ukpeurCurrency",
				"eurukpCurrency","demukpCurrency","frfukpCurrency"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
