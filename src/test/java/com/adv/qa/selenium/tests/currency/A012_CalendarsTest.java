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
 * Test Reference No	: 	A012 Calendars
 * Purpose              :   Set Up Calendar Activities
 * Date					:   16-04-2014
 * ACCESS               :   APC
 */

public class A012_CalendarsTest extends BaseTest{
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
		List<String> calendarActivity = dataRow.findNamesReturnValues("calendarActivity");
		List<String> calendarMonthActivity = dataRow.findNamesReturnValues("calendarMonthActivity");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Common Activities","Search page","displayed");
		
		/*Create calendar activity*/
		createcalendar(currencyPage,dataRow,calendarActivity);
		createcalendar(currencyPage,dataRow,calendarMonthActivity);
			
		currencyPage.logOut(2);
	}
	
	private void createcalendar(CurrencyPage currencyPage,DataRow dataRow,List<String> calendarActivity) throws InterruptedException{
		/*Verify calendar activity present in the list*/
		currencyPage.searchValue(calendarActivity, 2, 0);

		if(!currencyPage.verifyValues(calendarActivity.get(0))){
		
			/*Insert new calendar details*/
			currencyPage.clickOnInsert();
					
			/*Enter calendar activity details*/
			currencyPage.createDayCalendar(calendarActivity,1);
			
			currencyPage.clickOnUpdate();
					
			/*Exit from the calendar page*/
			currencyPage.clickOnCancel();
					
			/*Verify new calendar in the calendar list*/
			if(currencyPage.verifyValues(calendarActivity.get(0))){
				testcases.add(getCurreentDate()+" | Pass : New calendar activity "+calendarActivity.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New calendar activity "+calendarActivity.get(0)+ " not displayed in the list");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New calendar activity "+calendarActivity.get(0)+ " displayed in the list");
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
		String[] nodeID = { "A012" };
		String [] selectedNames = {"userName","passWord","code","calendarActivity","calendarMonthActivity"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
