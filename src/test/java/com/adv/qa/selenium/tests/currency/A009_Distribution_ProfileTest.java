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
 * Test Reference No	: 	A009 Distribution Profile
 * Purpose              :   Set Up Distribution Profile
 * Date					:   15-04-2014
 * ACCESS               :   AOJ
 */

public class A009_Distribution_ProfileTest extends BaseTest{
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
		String value = dataRow.get("value");
		List<String> profileDetails = dataRow.findNamesReturnValues("profileDetails");
		List<String> profileDetails1 = dataRow.findNamesReturnValues("profileDetails1");
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Distribution Profile List","Search page","displayed");
		
		currencyPage.searchValue(value, 1, 0);
		
		/*Insert new profile details*/
		currencyPage.clickOnInsert();

		createDistributionProfile(currencyPage,profileDetails);
		createDistributionProfile(currencyPage,profileDetails1);	
		
		/*Exit from the profile page*/
		currencyPage.clickOnCancel();

		verifyValues(currencyPage,profileDetails);
		verifyValues(currencyPage,profileDetails1);
				
		/*Logout from the application*/
		currencyPage.logOut(2);
	}
	
	private void createDistributionProfile(CurrencyPage currencyPage,List<String> profileDetails){
		/*Enter profile details*/
		boolean update = currencyPage.enterDistributionProfileDetails(profileDetails);
	
		if(update == true){
			currencyPage.clickOnUpdate();				
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : Distribution profile "+profileDetails.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> profileDetails){
		currencyPage.searchValue(profileDetails, 1, 0);
		
		/*Verify new profile in the profile list*/
		if(currencyPage.verifyValues(profileDetails.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Distribution profile "+profileDetails.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Distribution profile "+profileDetails.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A009" };
		String [] selectedNames = {"userName","passWord","code","value","profileDetails","profileDetails1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
