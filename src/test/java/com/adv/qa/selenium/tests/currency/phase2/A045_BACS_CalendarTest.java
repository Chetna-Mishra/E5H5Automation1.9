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
 * Test Reference No	: 	A045 BACS Calendar
 * Purpose              :   Insert A BACS Calendar.
 * Date					:   27-06-2014
 * ACCESS               :   APA
 */

public class A045_BACS_CalendarTest extends BaseTest{
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
		List<String> calendar = dataRow.findNamesReturnValues("calendar");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Calendar List","Currency search page","displayed");
		
		currencyPage.searchCalendar(calendar, 2);
		
		if(!currencyPage.verifyValues(calendar.get(0))){
			
			currencyPage.clickOnInsert();	
				
			/*Create batch type code*/
			currencyPage.enterBASCCalendarDetails(calendar);	
			
			currencyPage.clickOnUpdate();
	
			currencyPage.clickOnCancel();
			
			currencyPage.isConfirmPopUpDisplayed();
			
			currencyPage.searchCalendar(calendar,2);
			
			currencyPage.clickOnRunActivity();
			
			currencyPage.runActivityForCalendar(calendar);	
			
			currencyPage.clickOnUpdateWarnings();
	
			/*Verify new batch type in the list*/
			Assert.assertTrue(testcases,currencyPage.verifyValues(calendar.get(0)), "New calendar  "+calendar.get(0),"displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : calendar "+calendar.get(0) +"prensent in the list");
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
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A045" };
		String [] selectedNames = {"userName","passWord","code","currencyCode","calendar"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
