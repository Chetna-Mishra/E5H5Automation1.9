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
 * Test Reference No	: 	A093 Transaction Legend Controls
 * Purpose              :   Set Up Suppliers 
 * ACCESS               :   GAE
 */

public class A093_Transaction_Legend_ControlsTest extends BaseTest{
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
		List<String> transactionControl = dataRow.findNamesReturnValues("transactionControl");		
		String message = "The previously-requested action has been performed";
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Transaction Legend List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,transactionControl, 2, 6);
		
		currencyPage.clickOnAmend();
		
		boolean update = currencyPage.amendTransactionLegendDetails(transactionControl);
		
		if(update == true){					
			currencyPage.clickOnUpdate();		
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New transaction legend control  "+transactionControl.get(0)+ " updated with control");
		}

		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : New transaction legend control "+transactionControl.get(0)+ " updated with control");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New transaction legend control "+transactionControl.get(0)+ " not updated with control");
		}
		
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
		String[] nodeID = { "A093" };
		String [] selectedNames = {"userName","passWord","currencyCode","transactionControl"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
