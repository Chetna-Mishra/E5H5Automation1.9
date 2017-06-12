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
 * Test Reference No	: 	AD10017 Submit Payment Processing
 * Purpose              :   Submit Payment Processing 
 * ACCESS               :   ANM,GZA,GGA
 */

public class AD10017_Submit_Payment_ProcessingTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> transactionList = dataRow.findNamesReturnValues("transactionList");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Schedule List","Currency search page","displayed");
		
		currencyPage.search(transactionList.get(1),2,0);
		
		currencyPage.clickOnButton(5);
		
		currencyPage.submitDetails(1);
		
		String message = "Schedule successfully submitted";
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Schedule "+transactionList.get(0)+ "  submitted");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Schedule "+transactionList.get(0)+ "  not submitted");
		}
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Supplier Selection","Currency search page","displayed");

		currencyPage.searchElement(companyId,transactionList, 10);
		
		currencyPage.verifySupplierTransaction(transactionList);
		
		currencyPage.clickOnCancel();

		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Payment Schedule List","Currency search page","displayed");

		currencyPage.search(companyId, 6, 0);
	
		Assert.assertTrue(testcases,currencyPage.getStatus(3).equals("Payment Production Complete"),"Schedule status is","correcy");
		
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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "AD10017" };
		String [] selectedNames = {"userName","passWord","currencyCode","transactionList"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
