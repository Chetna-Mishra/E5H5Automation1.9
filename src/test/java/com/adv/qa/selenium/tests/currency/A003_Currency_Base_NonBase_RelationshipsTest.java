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
 * Test Reference No	: 	A003 Currency Base/Non Base Relationships
 * Purpose              :   Set Up Currency Relationships
 * Date					:   24-04-2014
 * ACCESS               :   AGC
 */

public class A003_Currency_Base_NonBase_RelationshipsTest extends BaseTest{	
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
		List<String> ukpeurCurrency = dataRow.findNamesReturnValues("ukpeurCurrency");
		List<String> eurukpCurrency = dataRow.findNamesReturnValues("eurukpCurrency");
		List<String> usdukpCurrency = dataRow.findNamesReturnValues("usdukpCurrency");
		List<String> frfukpCuurency = dataRow.findNamesReturnValues("frfukpCuurency");
		List<String> demukpCurrency = dataRow.findNamesReturnValues("demukpCurrency");
		List<String> ukpfrfCurrency = dataRow.findNamesReturnValues("ukpfrfCurrency");
		List<String> ukpdemCurrency = dataRow.findNamesReturnValues("ukpdemCurrency");

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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Currency Relationship List","Currency search page", "displayed");
		
		currencyPage.clickOnInsert();
				
		/*Create Base and Non Base currency*/
		createBaseNonBaseRelation(currencyPage,eurukpCurrency);
		createBaseNonBaseRelation(currencyPage,ukpeurCurrency);
		createBaseNonBaseRelation(currencyPage,usdukpCurrency);
		createBaseNonBaseRelation(currencyPage,frfukpCuurency);
		createBaseNonBaseRelation(currencyPage,demukpCurrency);
		createBaseNonBaseRelation(currencyPage,ukpfrfCurrency);
		createBaseNonBaseRelation(currencyPage,ukpdemCurrency);
		
		/*Exit from the currency edit page*/
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,eurukpCurrency);
		verifyValues(currencyPage,ukpeurCurrency);
		verifyValues(currencyPage,usdukpCurrency);
		verifyValues(currencyPage,frfukpCuurency);
		verifyValues(currencyPage,demukpCurrency);
		verifyValues(currencyPage,ukpfrfCurrency);
		verifyValues(currencyPage,ukpdemCurrency);
		
		/*Logout from the application*/
		currencyPage.logOut(2);

	}
	
	private void createBaseNonBaseRelation(CurrencyPage currencyPage,List<String> currencyList) throws InterruptedException{
		/*Create new currency code*/
		boolean update = currencyPage.enterCurrencyRelationshipDetails(currencyList);	
		
		if(update == true){
			currencyPage.clickOnUpdate();		
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : Currency "+currencyList.get(1)+ " displayed in the list");
		}
	}

	private void verifyValues(CurrencyPage currencyPage,List<String> currencyList){
		/*Search currency already present in the list*/
		currencyPage.searchValue(currencyList,2,1);

		/*Verify new currency in the list*/
		if(currencyPage.verifyValues(currencyList.get(1))){
			testcases.add(getCurreentDate()+" | Pass : Currency "+currencyList.get(1) +" "+currencyList.get(2)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Currency "+currencyList.get(1) +" "+currencyList.get(2)+  " not displayed in the list");
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
		String[] nodeID = { "A003" };
		String [] selectedNames = {"userName","passWord","code","ukpeurCurrency","eurukpCurrency","usdukpCurrency","frfukpCuurency",
				"demukpCurrency","ukpfrfCurrency","ukpdemCurrency"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
