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
 * Test Reference No	: 	A041 Group Category & Structure Postings
 * Purpose              :   Run Group Category and Structure Postings Update
 * Date					:   26-05-2014
 * ACCESS               :   Submit EP4 and Submit EP5
 */

public class A041_Group_Category_And_Structure_PostingsTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> ep4ProcessList = dataRow.findNamesReturnValues("ep4ProcessList");
		List<String> ep5ProcessList = dataRow.findNamesReturnValues("ep5ProcessList");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(ep4ProcessList.get(0));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+ep4ProcessList.get(1)+" - Company Parameter Edit","Structure Rebuild page","displayed");
		
		/*Create layout code*/	
		currencyPage.enterEP4ProcessDetails(ep4ProcessList.get(4));
		
		currencyPage.enterAboutsubmitDetails();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(ep5ProcessList.get(0));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+ep5ProcessList.get(1)+" - Postings Rebuild Parameters","Structure Rebuild page","displayed");
		
		currencyPage.enterEP5ProcessDetails(ep5ProcessList,companyId);	
		
		currencyPage.enterAboutsubmitDetails();
				
		processVerification(currencyPage,ep5ProcessList.get(2), ep5ProcessList.get(3));
		
		currencyPage.logOut(1);
	}

	public void processVerification(CurrencyPage currencyPage,String process,String Request){
		boolean value = false;
		String statBeforeEp5 = currencyPage.getProcessDetails(process, Request);
		Assert.assertEquals(testcases,statBeforeEp5, "2","Precess has","entered task list");
		if(statBeforeEp5.equals("2")){
			currencyPage.updateProcess(process, Request);
		}
		
		String statAfterEp5 = currencyPage.getProcessDetails(process, Request);
		
		if(statAfterEp5 == null)
		{
			value = true;			
		}
		Assert.assertTrue(testcases,value,"Precess "+process,"performed on "+Request);
		
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
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A041" };
		String [] selectedNames = {"userName","passWord","ep4ProcessList","ep5ProcessList"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
