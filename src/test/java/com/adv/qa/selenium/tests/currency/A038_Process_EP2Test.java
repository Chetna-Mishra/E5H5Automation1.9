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
 * Test Reference No	: 	A038 Process EP2
 * Purpose              :   Run Structure Rebuild
 * Date					:   26-05-2014
 * ACCESS               :   Submit EP2
 */

public class A038_Process_EP2Test extends BaseTest{
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
		String process = dataRow.get("process");	
		List<String> processList = dataRow.findNamesReturnValues("processList");
		boolean value = false;
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+processList.get(0)+" - Postings Rebuild Parameters","Structure Rebuild page","displayed");
		
		/*Create new process*/
		currencyPage.enterEP2ProcessDetails(processList,companyId);	
		
		currencyPage.enterAboutsubmitDetails();
		
		String statBefore = currencyPage.getProcessDetails(process,processList.get(1));
		Assert.assertEquals(testcases,statBefore, "2","Precess has","entered task list");
		if(statBefore.equals("2")){
			currencyPage.updateProcess(process,processList.get(1));
		}
		
		String statAfter = currencyPage.getProcessDetails(process,processList.get(1));
		
		if(statAfter == null || statAfter.equals("3"))
		{
			value = true;			
		}
		Assert.assertTrue(testcases,value,"Precess "+process,"performed on "+processList.get(1));
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.logOut(1);
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
		String[] nodeID = { "A038" };
		String [] selectedNames = {"userName","passWord","code","process","processList"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
