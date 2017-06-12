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
 * Test Reference No	: 	AD10015 Create Manual Payment Schedule
 * Purpose              :   Create Manual Payment Schedule 
 * ACCESS               :   GGA
 */

public class AD10015_Create_Manual_Payment_ScheduleTest extends BaseTest{
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
		List<String> scheduleDetails = dataRow.findNamesReturnValues("scheduleDetails");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Payment Schedule List","Currency search page","displayed");
		
		currencyPage.search(companyId,6,0);
		
		currencyPage.clickOnInsert();
		
		currencyPage.scheduleMaintenance(scheduleDetails);
		
		currencyPage.clickOnUpdate();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.clickOnButton(9);//Suppler button
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Schedule Suppliers Maintenance"),"Schedule suppliers maintenance page ","displayed");
		
		currencyPage.clickOnInsert();
		
		currencyPage.enterSchedueSupplierTranDetails(scheduleDetails);
		
		currencyPage.clickOnButton(18);//Due button
		
		currencyPage.clickOnButton(20);//Pay button
		
		currencyPage.clickOnUpdate();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.clickOnCancel();
				
		//Verify status
		Assert.assertTrue(testcases,currencyPage.verifyValues(scheduleDetails.get(6)),"Schedule status is " ," "+scheduleDetails.get(6));
		
		currencyPage.clickOnButton(10);//Request button
		
		currencyPage.enterScheduleRequestRun(scheduleDetails.get(7));
		
		currencyPage.clickOnUpdate();
		
		//Verify status
		Assert.assertTrue(testcases,currencyPage.verifyValues(scheduleDetails.get(8)),"Schedule status is " ," "+scheduleDetails.get(8));

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
		String[] nodeID = { "AD10015" };
		String [] selectedNames = {"userName","passWord","currencyCode","scheduleDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
