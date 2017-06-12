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
 * Test Reference No	: 	A032 Management codes
 * Purpose              :   Set Up Management Codes
 * Date					:   14-05-2014
 * ACCESS               :   ECG
 */

public class A032A_Management_CodesTest extends BaseTest{
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
		List<String> pathKeyNorth = dataRow.findNamesReturnValues("pathKeyNorth");
		List<String> pathKeyEast =	dataRow.findNamesReturnValues("pathKeyEast");			
		List<String> pathKeyWest = dataRow.findNamesReturnValues("pathKeyWest");
		List<String> pathKeySouth = dataRow.findNamesReturnValues("pathKeySouth");
					
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Path Key List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,pathKeyNorth,6,4);
		
		/*Edit management code*/
		editManagementCode(currencyPage,pathKeyNorth);
		editManagementCode(currencyPage,pathKeyEast);
		editManagementCode(currencyPage,pathKeyWest);
		editManagementCode(currencyPage,pathKeySouth);
		
		currencyPage.clickOnCancel();
		
		currencyPage.logOut(1);

	}
	
	private void editManagementCode(CurrencyPage currencyPage,List<String> managemenCode){
		currencyPage.searchManagementCode(managemenCode.get(2),6);
		
		if(currencyPage.verifyValues(managemenCode.get(2))){			
			
			currencyPage.clickOnAmend();
			
			currencyPage.createManagementLink(managemenCode.get(3));
			
			currencyPage.clickOnUpdate();	
			
			testcases.add(getCurreentDate()+" | Pass : Management code "+managemenCode.get(3)+ " updated successfully");
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
		String xmlFilePath = folder  + "A032A.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
