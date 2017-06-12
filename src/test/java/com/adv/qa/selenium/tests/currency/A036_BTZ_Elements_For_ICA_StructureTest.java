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
 * Test Reference No	: 	A036 BTZ Elements for ICA Structure
 * Purpose              :   Create BTZ Elements for ICA Structure
 * Date					:   02-05-2014
 * ACCESS               :   ECI
 */

public class A036_BTZ_Elements_For_ICA_StructureTest extends BaseTest{
	
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

		List<String> icaNorth = dataRow.findNamesReturnValues("icaNorth");
		List<String> icaWest = dataRow.findNamesReturnValues("icaWest");
		List<String> icaEast = dataRow.findNamesReturnValues("icaEast");
		List<String> icaSouth = dataRow.findNamesReturnValues("icaSouth");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - ICA Elements List","Search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();
		
		/*Create new ICA structure*/
		createBTZElement(currencyPage,icaNorth);
		createBTZElement(currencyPage,icaWest);
		createBTZElement(currencyPage,icaEast);
		createBTZElement(currencyPage,icaSouth);
		
		/*Exit from the ICA details page*/
		currencyPage.clickOnCancel();

		/*Verify presence of ICA structure*/
		verifyValues(currencyPage,icaNorth);
		verifyValues(currencyPage,icaWest);
		verifyValues(currencyPage,icaEast);
		verifyValues(currencyPage,icaSouth);
		
		currencyPage.logOut(2);
	}
	
	private void createBTZElement(CurrencyPage currencyPage,List<String> element){
		/*Create new ICA element*/
		boolean update = currencyPage.enterICAElements(element);
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New BTZ element for ICA structure "+element.get(1)+ " present in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> element){
		/*Verify new ICA element in the list*/
		if(currencyPage.verifyValues(element.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New BTZ element for ICA structure "+element.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New BTZ element for ICA structure "+element.get(1)+ " not displayed in the list");
		}
	}
	
	@DataProvider
	public Object[][] dp() 
	{		
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A036" };
		String [] selectedNames = {"userName","passWord","code","company","icaNorth","icaWest","icaEast","icaSouth"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}

}
