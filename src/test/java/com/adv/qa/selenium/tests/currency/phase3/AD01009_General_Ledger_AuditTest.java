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
 * Test Reference No	: 	AD01009 General Ledger Audit 
 * Purpose              :   General Ledger Audit 
 * ACCESS               :   ED4
 */

public class AD01009_General_Ledger_AuditTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> processED4 = dataRow.findNamesReturnValues("processED4");

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		submitProcess(currencyPage,processED4,0);
	
		currencyPage.logOut(1);
	}
	
	public void submitProcess(CurrencyPageNew currencyPage,List<String> processList,int i) throws InterruptedException{
		boolean value = false;
				
		currencyPage.fillCurrenceyCode(processList.get(0));
		
		currencyPage.generateAuditReports(companyId, processList);			
		
		if(i==0){
			currencyPage.enterAboutsubmitDetails();
		}
		else{
			currencyPage.submitDetails(1);
		}
		
		String statBefore = currencyPage.getProcessDetails(processList.get(2),processList.get(1));
		Assert.assertEquals(testcases,statBefore, "2","Precess has","entered task list");
		if(statBefore.equals("2")){
			currencyPage.updateProcess(processList.get(2),processList.get(1));
		}
		
		String statAfter = currencyPage.getProcessDetails(processList.get(2),processList.get(1));
		
		if(statAfter == null || statAfter.equals("3"))
		{
			value = true;			
		}
		Assert.assertTrue(testcases,value,"Precess "+processList.get(2)," performed on "+processList.get(1));
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
	
		
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
		String[] nodeID = { "AD01008" };
		String [] selectedNames = {"userName","passWord","currencyCode","processED4"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
