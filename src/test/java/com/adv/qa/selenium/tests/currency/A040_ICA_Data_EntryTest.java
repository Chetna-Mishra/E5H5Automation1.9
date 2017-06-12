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
 * Test Reference No	: 	A040 ICA Data Entry
 * Purpose              :   General Ledger ICA Postings
 * Date					:   26-05-2014
 * ACCESS               :   EJI
 */

public class A040_ICA_Data_EntryTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> firstICAData = dataRow.findNamesReturnValues("firstICAData");
		List<String> secondICAData = dataRow.findNamesReturnValues("secondICAData");		
		List<String> fAccount1500 = dataRow.findNamesReturnValues("fAccount1500");
		List<String> sAccount1500 = dataRow.findNamesReturnValues("sAccount1500");
		List<String> account6300 = dataRow.findNamesReturnValues("account6300");
		List<String> account6400 = dataRow.findNamesReturnValues("account6400");
		List<String> aGroup = dataRow.findNamesReturnValues("aGroup");
		List<String> structureEnqForA02 = dataRow.findNamesReturnValues("structureEnqForA02");
		List<String> structureEnqForN1 = dataRow.findNamesReturnValues("structureEnqForN1");
		List<String> structureEnqForN2 = dataRow.findNamesReturnValues("structureEnqForN2");
		List<String> structureEnq2ForN1 = dataRow.findNamesReturnValues("structureEnq2ForN1");
		List<String> structureEnqForS1 = dataRow.findNamesReturnValues("structureEnqForS1");
		


		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		enterJournalDetails(currencyPage,dataRow,firstICAData);
		
		accountDetailEnquiry(currencyPage,dataRow,aGroup,fAccount1500,account6300);

		currencyPage.clickOnCancel();
				
		verifyBalanceEnquiry(currencyPage,dataRow,structureEnqForA02,structureEnqForN1,structureEnqForN2);
		
		currencyPage.clickOnCancel();
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		enterJournalDetails(currencyPage,dataRow,secondICAData);
		
		accountDetailEnquiry(currencyPage,dataRow,aGroup,sAccount1500,account6400);

		currencyPage.clickOnCancel();
				
		verifyBalanceEnquiry(currencyPage,dataRow,structureEnqForA02,structureEnq2ForN1,structureEnqForS1);

		currencyPage.logOut(2);

	}
	
	private void enterJournalDetails(CurrencyPage currencyPage,DataRow dataRow,List<String> icaData) throws InterruptedException{
		String code = "EDTEBTCH ACT=INSERT,CMPY="+companyId;		

		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		
		currencyPage.fillCurrenceyCode(code);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Journal Header","Search page","displayed");
		
		/*Enter Journal details*/
		currencyPage.enterJournalDetails(icaData);
		
		currencyPage.clickOnUpdtWarnings();

	}

	private void accountDetailEnquiry(CurrencyPage currencyPage,DataRow dataRow,List<String> group,List<String> account1,List<String> account2) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");


		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.clickOnChangeCompany(companyId);
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		currencyPage.verifyBalanceSheetDetail(group);
		
		currencyPage.navigateToAccountDetailPage();
		
		currencyPage.getAccountDetailValues(account1.get(0));
		
		verifyGroup(currencyPage,account1);
		
		currencyPage.getAccountDetailValues(account2.get(0));
		
		verifyGroup(currencyPage,account2);
		
		currencyPage.clickOnCancel();

	}
	

	
	private void verifyBalanceEnquiry(CurrencyPage currencyPage,DataRow dataRow,List<String> structureEnqForCategory,List<String> structureEnqForCost1,List<String> structureEnqForCost2) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.clickOnChangeCompany(companyId);
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		currencyPage.structureEnquiry(structureEnqForCategory);
		
		currencyPage.navigateToAccountDetailPage();
		
		currencyPage.getCostDetailValues(structureEnqForCost1.get(0));
		
		verifyGroup(currencyPage,structureEnqForCost1);
		
		currencyPage.getCostDetailValues(structureEnqForCost2.get(0));
		
		verifyGroup(currencyPage,structureEnqForCost2);
		
		currencyPage.clickOnCancel();	
	}
	
	private void verifyGroup(CurrencyPage currencyPage,List<String> values){
		boolean isValuePresent = false;
		
		for(String value : values){
			if(currencyPage.verifyValues(value)){
				isValuePresent = true;
			}		
		}
		
		/*Verify new ICA data displayed in the list*/
		Assert.assertTrue(testcases,isValuePresent, "New group displayed","displayed in the grid");
	}

	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A040.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
