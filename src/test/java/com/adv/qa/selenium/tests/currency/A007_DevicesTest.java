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
 * Test Reference No	: 	A007 Devices
 * Purpose              :   Set Up Devices
 * Date					:   15-04-2014
 * ACCESS               :   ANS
 */

public class A007_DevicesTest extends BaseTest{
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
		List<String> deviceDetails = dataRow.findNamesReturnValues("deviceDetails");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Device List","Search page","displayed");
		
		currencyPage.searchValue(deviceDetails,2,0);
		
		if(!currencyPage.verifyValues(deviceDetails.get(0))){

			/*Insert new device details*/
			currencyPage.clickOnInsert();
					
			/*Enter device details*/
			currencyPage.enterDeviceDetails(deviceDetails);
			
			currencyPage.clickOnUpdate();
					
			/*Exit from the device page*/
			currencyPage.clickOnCancel();
					
			/*Verify new device in the device list*/
			if(currencyPage.verifyValues(deviceDetails.get(0))){
				testcases.add(getCurreentDate()+" | Pass : New device "+deviceDetails.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New device "+deviceDetails.get(0)+ " not displayed in the list");
			}
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New device "+deviceDetails.get(0)+ " displayed in the list");
		}
		
		
		/*Logout from the application*/
		currencyPage.logOut(2);
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
		String[] nodeID = { "A007" };
		String [] selectedNames = {"userName","passWord","code","deviceDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
