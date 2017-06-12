package com.adv.qa.selenium.tests.currency.phase3;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPageNew;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	A110 Security Range Lists 
 * Purpose              :   Security range Lists 
 * ACCESS               :   ADI
 */

public class A110_Security_Range_ListsTest extends BaseTest{
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
		List<String> securitRangeForItem = dataRow.findNamesReturnValues("securitRangeForItem");
		List<String> securitRangeForStore = dataRow.findNamesReturnValues("securitRangeForStore");		

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		createIMAccounts(currencyPage,securitRangeForItem,currencyCode);
		createIMAccounts(currencyPage,securitRangeForStore,currencyCode);
	
		currencyPage.logOut(1);
	}
	
	
	private void createIMAccounts(CurrencyPageNew currencyPage,List<String> elements,String currencyCode) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		String code = "EDTADRCDE ACT=INSERT,COMPANY="+companyId; 
		
		currencyPage.isCommandDisplayed();
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(code);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Range List Code Edit","Currency search page","displayed");
				
		/*Create security range*/
		currencyPage.insertSecurityRange(elements);	
		
		currencyPage.clickOnUpdate();	
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Security range "+elements.get(0)+ " created");
		}
		else{
			currencyPage.clickOnCancel();
			
			testcases.add(getCurreentDate()+" | Fail : Security range "+elements.get(0)+ " created");
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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "A110" };
		String [] selectedNames = {"userName","passWord","currencyCode","securitRangeForItem","securitRangeForStore"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
