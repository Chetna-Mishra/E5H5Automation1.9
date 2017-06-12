package com.adv.qa.selenium.tests.currency.phase2;

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
 * Test Reference No	: 	A057 Legend Controls
 * Purpose              :   Insert Payment Legend.
 * Date					:   19-06-2014
 * ACCESS               :   GAE
 */

public class A057_Legend_ControlsTest extends BaseTest{
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
		List<String> transactionControl0 = dataRow.findNamesReturnValues("transaction0");
		List<String> transactionControl1 = dataRow.findNamesReturnValues("transaction1");
		
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Transaction Legend List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();
		
		createTranLegend(currencyPage,transactionControl0);
		createTranLegend(currencyPage,transactionControl1);
		
		currencyPage.clickOnCancel();
				
		verifyValues(currencyPage,transactionControl0);
		verifyValues(currencyPage,transactionControl1);
		
		currencyPage.logOut(2);
	}
	
	private void createTranLegend(CurrencyPage currencyPage,List<String> transactionControl){
		
		boolean update = currencyPage.enterTransactionLegendDetails(transactionControl);
		
		if(update == true){					
			currencyPage.clickOnUpdate();		
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New legend control  "+transactionControl.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> transactionControl){
		
		if(currencyPage.verifyValues(transactionControl.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New legend control "+transactionControl.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New legend control "+transactionControl.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A057" };
		String [] selectedNames = {"userName","passWord","currencyCode","transaction0","transaction1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
