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
 * Test Reference No	: 	A039  Enquiry
 * Purpose              :   Run Structure Rebuild
 * Date					:   26-05-2014
 * ACCESS               :   EJI
 */

public class A039_EnquiryTest extends BaseTest{
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
		List<String> aGroup = dataRow.findNamesReturnValues("aGroup");
		List<String> bGroup = dataRow.findNamesReturnValues("bGroup");
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.clickOnChangeCompany(companyId);
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Balance Sheet/Profit and Loss","Structure Rebuild page","displayed");
		
		verifyGroup(currencyPage,aGroup);
		verifyGroup(currencyPage,bGroup);
		
		currencyPage.logOut(2);

	}

	private void verifyGroup(CurrencyPage currencyPage,List<String> values){
		boolean isValuePresent = false;
		currencyPage.balanceSheetDetails(values);	
		
		for(String value : values){
			if(currencyPage.verifyValues(value)){
				isValuePresent = true;
			}		
		}
		
		/*Verify new group details displayed in the list*/
		Assert.assertTrue(testcases,isValuePresent, "New group displayed" +values.get(1)," in the grid");
	}

	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A039.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
