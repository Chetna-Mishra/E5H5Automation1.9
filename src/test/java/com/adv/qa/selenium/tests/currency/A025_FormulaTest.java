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
 * Test Reference No	: 	A025 Formula
 * Purpose              :   Set Up Formulae
 * Date					:   23-04-2014
 * ACCESS               :   EAO
 */

public class A025_FormulaTest extends BaseTest{
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

		List<String> formulaForCal1 = dataRow.findNamesReturnValues("formulaForCal1");
		List<String> formulaForCal2 = dataRow.findNamesReturnValues("formulaForCal2");
		List<String> formulaForBs1 = dataRow.findNamesReturnValues("formulaForBs1");
		List<String> formulaForMs1 = dataRow.findNamesReturnValues("formulaForMs1");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Formula List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);

		currencyPage.clickOnInsert();
		
		/*Create Formula*/
		createFormula(currencyPage,formulaForCal1);
		createFormula(currencyPage,formulaForCal2);
		createFormula(currencyPage,formulaForBs1);
		createFormula(currencyPage,formulaForMs1);
		
		/*Exit from the formula details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		/*Verify presence of formula*/
		verifyValues(currencyPage,formulaForCal1);
		verifyValues(currencyPage,formulaForCal2);
		verifyValues(currencyPage,formulaForBs1);
		verifyValues(currencyPage,formulaForMs1);
		
		currencyPage.logOut(2);
	}

	private void createFormula(CurrencyPage currencyPage,List<String> formulaList) throws InterruptedException{
		/*Create new formula*/
		boolean update = currencyPage.enterFormulaDetails(formulaList);	

		if(update == true){
			currencyPage.clickOnUpdate();			
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New formula "+formulaList.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> formulaList){
		/*Verify new formula added in the list*/
		if(currencyPage.verifyValues(formulaList.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New formula "+formulaList.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New formula "+formulaList.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A025" };
		String [] selectedNames = {"userName","passWord","code","company","formulaForCal1","formulaForCal2","formulaForBs1","formulaForMs1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
