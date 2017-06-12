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
 * Test Reference No	: 	A035 Structure Rebuild (EP3)
 * Purpose              :   Run Structure Rebuild
 * Date					:   02-05-2014
 * ACCESS               :   Submit EP3
 */

public class A035_Structure_RebuildTest extends BaseTest{
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
		List<String> structureList = dataRow.findNamesReturnValues("structureList");
		String process = dataRow.get("process");
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+structureList.get(0)+" - Structure Rebuild Parameters","Structure Rebuild page","displayed");
		
		/*Create structure build details*/
		currencyPage.enterStructureReBuildDetails(structureList,companyId);	
		
		currencyPage.enterAboutsubmitDetails();
		
		String statBefore = currencyPage.getProcessDetails(process,structureList.get(1));
		
		Assert.assertEquals(testcases,statBefore, "2","Precess has","entered task list");
		if(statBefore.equals("2")){;
			currencyPage.updateProcess(process,structureList.get(1));
		}
		
		String statAfter = currencyPage.getProcessDetails(process,structureList.get(1));
		
		if(statAfter == null || statAfter.equals("3"))
		{
			value = true;			
		}
		Assert.assertTrue(testcases,value,"Precess" +process,"performed on "+structureList.get(1));
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
		String[] nodeID = { "A035" };
		String [] selectedNames = {"userName","passWord","code","structureList","process"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
