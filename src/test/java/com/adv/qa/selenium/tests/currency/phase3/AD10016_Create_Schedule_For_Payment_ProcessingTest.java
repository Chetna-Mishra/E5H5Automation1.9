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

public class AD10016_Create_Schedule_For_Payment_ProcessingTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> scheduleList = dataRow.findNamesReturnValues("scheduleList");
		
		List<String> scheduleGHO = dataRow.findNamesReturnValues("scheduleGHO");
		List<String> scheduleGH4 = dataRow.findNamesReturnValues("scheduleGH4");
		List<String> scheduleGH6 = dataRow.findNamesReturnValues("scheduleGH6");
		List<String> scheduleGHB = dataRow.findNamesReturnValues("scheduleGHB");
		List<String> scheduleGHC = dataRow.findNamesReturnValues("scheduleGHC");
		List<String> scheduleGH0 = dataRow.findNamesReturnValues("scheduleGH0");
		List<String> scheduleGIA = dataRow.findNamesReturnValues("scheduleGIA");
		
		List<String> scheduleRequestGHO = dataRow.findNamesReturnValues("scheduleRequestGHO");
		List<String> scheduleRequestGH4 = dataRow.findNamesReturnValues("scheduleRequestGH4");
		List<String> scheduleRequestGH6 = dataRow.findNamesReturnValues("scheduleRequestGH6");
		List<String> scheduleRequestGHB = dataRow.findNamesReturnValues("scheduleRequestGHB");
		List<String> scheduleRequestGHC = dataRow.findNamesReturnValues("scheduleRequestGHC");
		List<String> scheduleRequestGH0 = dataRow.findNamesReturnValues("scheduleRequestGH0");
		List<String> scheduleRequestGIA = dataRow.findNamesReturnValues("scheduleRequestGIA");
		
		List<String> scheduleReportGHO = dataRow.findNamesReturnValues("scheduleReportGHO");
		List<String> scheduleReportGH6 = dataRow.findNamesReturnValues("scheduleReportGH6");
		List<String> scheduleReportGHC = dataRow.findNamesReturnValues("scheduleReportGHC");
		List<String> scheduleReportGIA = dataRow.findNamesReturnValues("scheduleReportGIA");

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
//		currencyPage.fillCurrenceyCode(currencyCode.get(0));
//
//		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Schedule List","Currency search page","displayed");
//		
//		currencyPage.search(scheduleList.get(0), 2, 0);//Search Schedule
//		
//		currencyPage.clickOnInsert();
//		
//		currencyPage.createSchedules(scheduleList);
//		
//		currencyPage.clickOnUpdate();
//		
//		currencyPage.clickOnCancel();
//		
//		currencyPage.search(scheduleList.get(0), 2, 0);//Search Schedule
//		
//		if(currencyPage.verifyValues(scheduleList.get(0))){
//			testcases.add(getCurreentDate()+" | Pass : Schedule "+scheduleList.get(0)+ "  created");
//		}
//		else{
//			testcases.add(getCurreentDate()+" | Fail : Schedule "+scheduleList.get(0)+ "  not created");
//		}
//		
//		currencyPage.clickOnCancel();
		
//		currencyPage.isCommandDisplayed();
		
//		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Request List","Currency search page","displayed");

		currencyPage.search(scheduleList.get(0), 6, 3);//Search Schedule
		
		currencyPage.clickOnInsert();
		
		createRequests(currencyPage,scheduleGHO,currencyCode.get(1));
		createRequests(currencyPage,scheduleGH4,currencyCode.get(1));
		createRequests(currencyPage,scheduleGH6,currencyCode.get(1));
		createRequests(currencyPage,scheduleGHB,currencyCode.get(1));
		createRequests(currencyPage,scheduleGHC,currencyCode.get(1));
		createRequests(currencyPage,scheduleGH0,currencyCode.get(1));
		createRequests(currencyPage,scheduleGIA,currencyCode.get(1));
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		addReports(currencyPage, scheduleReportGHO);
		addReports(currencyPage, scheduleReportGH6);
		addReports(currencyPage, scheduleReportGHC);
		addReports(currencyPage, scheduleReportGIA);
		
		addRequest(currencyPage,scheduleRequestGHO);
		addRequest(currencyPage,scheduleRequestGH4);
		addRequest(currencyPage,scheduleRequestGH6);
		addRequest(currencyPage,scheduleRequestGHB);
		addRequest(currencyPage,scheduleRequestGHC);
		addRequest(currencyPage,scheduleRequestGH0);
		addRequest(currencyPage,scheduleRequestGIA);

		currencyPage.logOut(2);
	}

	public void createRequests(CurrencyPageNew currencyPage,List<String> elements,String currencyCode) throws InterruptedException{
		String message = "The previously-requested action has been performed";
				
		boolean update = currencyPage.enterRequestDetails(elements);
		
		if(update == true){
			currencyPage.clickOnUpdate();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : Request is "+elements.get(0)+ "  created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Request is "+elements.get(0)+ "  not created");
			}
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : Request is "+elements.get(0)+ "  present");
		}
		
	}
	
	public void addReports(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		currencyPage.searchRequest(elements, 6);
		
		currencyPage.clickOnMaintainReports();
		
		currencyPage.enterReportDetails(elements);
		
		currencyPage.clickOnUpdate();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Report "+elements.get(1)+ "  updated");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Report "+elements.get(1)+ "  not created");
		}
	}
	
	public void addRequest(CurrencyPageNew currencyPage,List<String> elements){
		String message = "The previously-requested action has been performed";
		currencyPage.searchRequest(elements, 6);
		
		currencyPage.clickOnMaintainParameters();
		
		currencyPage.updateParameters(companyId,elements.get(2));
		
		currencyPage.clickOnUpdateCompany();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Request  "+elements.get(1)+ "  submitted");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Request  "+elements.get(1)+ "  not submitted");
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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "AD10016" };
		String [] selectedNames = {"userName","passWord","currencyCode","scheduleList","scheduleGHO","scheduleGH4","scheduleGH6","scheduleGHB"
				,"scheduleGHC","scheduleGH0","scheduleGIA","scheduleRequestGHO","scheduleReportGHO","scheduleRequestGH4","scheduleRequestGH6"
				,"scheduleReportGH6","scheduleRequestGHB","scheduleRequestGHC","scheduleReportGHC","scheduleRequestGH0","scheduleRequestGIA",
				"scheduleReportGIA"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
