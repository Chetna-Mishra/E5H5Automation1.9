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
 * Test Reference No	: 	A015 Balance Sheet Controls
 * Purpose              :   Set Up Normal Balance Sheet Controls
 * Date					:   21-04-2014
 * ACCESS               :   API
 */

public class A013_Fiscal_CalendarTest extends BaseTest{

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
		List<String> calendar = dataRow.findNamesReturnValues("calendar");

		List<String> year96 = dataRow.findNamesReturnValues("year96");
		List<String> year97 = dataRow.findNamesReturnValues("year97");
		List<String> year98 = dataRow.findNamesReturnValues("year98");
		List<String> year99 = dataRow.findNamesReturnValues("year99");
		List<String> year2000 = dataRow.findNamesReturnValues("year2000");
		
		List<String> year2001 = dataRow.findNamesReturnValues("year2001");
		List<String> year2002 = dataRow.findNamesReturnValues("year2002");
		List<String> year2003 = dataRow.findNamesReturnValues("year2003");
		List<String> year2004 = dataRow.findNamesReturnValues("year2004");
		List<String> year2005 = dataRow.findNamesReturnValues("year2005");
		
		List<String> year2006 = dataRow.findNamesReturnValues("year2006");
		List<String> year2007 = dataRow.findNamesReturnValues("year2007");
		List<String> year2008 = dataRow.findNamesReturnValues("year2008");
		List<String> year2009 = dataRow.findNamesReturnValues("year2009");
		List<String> year2010 = dataRow.findNamesReturnValues("year2010");
		
		List<String> year2011 = dataRow.findNamesReturnValues("year2011");
		List<String> year2012 = dataRow.findNamesReturnValues("year2012");
		List<String> year2013 = dataRow.findNamesReturnValues("year2013");
		List<String> year2014 = dataRow.findNamesReturnValues("year2014");
		List<String> year2015 = dataRow.findNamesReturnValues("year2015");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
			
		/*Create new balance sheet control*/
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Fiscal Calendar List","Currency search page","displayed");
		
		currencyPage.searchValue(calendar,1,0);
		
		if(!currencyPage.verifyValues(calendar.get(0))){
			/*Insert new balance sheet details*/
			currencyPage.clickOnInsert();
			
			currencyPage.enterFiscalCalendarDetails(calendar,year96,year97,year98,year99,year2000,year2001,year2002,year2003,year2004,year2005,year2006,
					year2007,year2008,year2009,year2010,year2011,year2012,year2013,year2014,year2015);
			
			if(currencyPage.verifyValues("E")){
				testcases.add(getCurreentDate()+" | Pass : Level 0 line does contain events E");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Level 0 line does contain events E");
			}
			
			currencyPage.clickOnYears();
			
			currencyPage.lockCalendar();
			
			currencyPage.clickOnEventsH();
			
			if(currencyPage.verifyValues("L")){
				testcases.add(getCurreentDate()+" | Pass : Level 0 line does contain events L");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Level 0 line does not contain events L");
			}
						
			currencyPage.clickOnUpdate();
			
			/*Exit from the Balance sheet page*/
			currencyPage.clickOnCancel();
			
			/*Verify new Balance control in the balance sheet list*/
			Assert.assertTrue(testcases,currencyPage.verifyValues(calendar.get(0)), "New calendar activity "+calendar.get(0),"displayed in the list");
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New fiscal calendar displayed in the list");
		}
		/*Clear test data*/
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
		String[] nodeID = { "A013" };
		String [] selectedNames = {"userName","passWord","code","calendar","year96","year97","year98","year99","year2000","year2001","year2002","year2003",
				"year2004","year2005","year2006","year2007","year2008","year2009","year2010","year2011","year2012","year2013","year2014","year2015"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
