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
 * Test Reference No	: 	AD01008 IM Audit Reports 
 * Purpose              :   IM Audit Reports 
 * ACCESS               :   ED3,ED4,ED8,GM4,DX1,HX1,PQ1
 */

public class AD01008_IM_Audit_ReportsTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> processED3 = dataRow.findNamesReturnValues("processED3");
		List<String> processED4 = dataRow.findNamesReturnValues("processED4");
		List<String> processED8 = dataRow.findNamesReturnValues("processED8");
		List<String> processGM4 = dataRow.findNamesReturnValues("processGM4");
		List<String> processDX1 = dataRow.findNamesReturnValues("processDX1");
		List<String> processHX1 = dataRow.findNamesReturnValues("processHX1");
		List<String> processPQ1 = dataRow.findNamesReturnValues("processPQ1");

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		submitProcess(currencyPage,processED3,0);
		submitProcess(currencyPage,processED4,0);
		submitProcess(currencyPage,processED8,0);
		submitProcess(currencyPage,processGM4,1);
		submitProcess(currencyPage,processDX1,1);
		submitProcess(currencyPage,processHX1,0);
		submitProcess(currencyPage,processPQ1,1);
	
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
		Assert.assertTrue(testcases,value,"Precess "+processList.get(2)," performed on request"+processList.get(1));
		
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
		String [] selectedNames = {"userName","passWord","currencyCode","processED3","processED4","processED8","processGM4",
				"processDX1","processHX1","processPQ1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
