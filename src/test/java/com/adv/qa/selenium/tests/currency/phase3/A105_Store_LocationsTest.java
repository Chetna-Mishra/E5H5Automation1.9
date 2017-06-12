package com.adv.qa.selenium.tests.currency.phase3;

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
 * Test Reference No	: 	A105 Store Locations
 * Purpose              :   Insert PM Locations
 * ACCESS               :   AK2
 */

public class A105_Store_LocationsTest extends BaseTest{
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

		List<String> locationWarehse = dataRow.findNamesReturnValues("locationWarehse");
		List<String> locationNorthITS = dataRow.findNamesReturnValues("locationNorthITS");
		List<String> locationSouthITS = dataRow.findNamesReturnValues("locationSouthITS");
		List<String> locationWestITS = dataRow.findNamesReturnValues("locationWestITS");
		List<String> locationEastITS = dataRow.findNamesReturnValues("locationEastITS");
		List<String> locationNorth = dataRow.findNamesReturnValues("locationNorth");
		List<String> locationSouth = dataRow.findNamesReturnValues("locationSouth");
		List<String> locationWest = dataRow.findNamesReturnValues("locationWest");
		List<String> locationEast = dataRow.findNamesReturnValues("locationEast");

		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Location Codes List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createPurchasingManagement(currencyPage,locationWarehse);
		createPurchasingManagement(currencyPage,locationNorthITS);
		createPurchasingManagement(currencyPage,locationSouthITS);
		createPurchasingManagement(currencyPage,locationWestITS);
		createPurchasingManagement(currencyPage,locationEastITS);
		createPurchasingManagement(currencyPage,locationNorth);
		createPurchasingManagement(currencyPage,locationSouth);
		createPurchasingManagement(currencyPage,locationEast);
		createPurchasingManagement(currencyPage,locationWest);

			
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.logOut(2);

	}
	
	private void createPurchasingManagement(CurrencyPage currencyPage,List<String> location) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		
		/*Create purchasing management location*/
		boolean update  = currencyPage.enterLocationCodeDetails(location);	
		
		if(update == true){
			
			currencyPage.clickOnUpdate();
			
			/*Verify new purchasing management location in the list*/
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : New purchasing management location "+location.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New purchasing management location "+location.get(0)+ " not displayed in the list");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New purchasing management location "+location.get(0)+ " displayed in the list");
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
		String[] nodeID = { "A061" };
		String [] selectedNames = {"userName","passWord","currencyCode",
				"locationWarehse","locationNorthITS","locationSouthITS","locationWestITS","locationEastITS","locationEastITS","locationNorth","locationSouth","locationEast","locationWest"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
