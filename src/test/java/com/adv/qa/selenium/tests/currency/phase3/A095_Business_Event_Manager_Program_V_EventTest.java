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
 * Test Reference No	: 	A095 Business Event Manager Program V Event
 * Purpose              :   Business Event Manager Program V Events 
 * ACCESS               :   XEE
 */

public class A095_Business_Event_Manager_Program_V_EventTest extends BaseTest{
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
		List<String> program = dataRow.findNamesReturnValues("program");		
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Program v Event List","Currency search page","displayed");
		
		currencyPage.searchValue(program, 2, 1);
		
		/*Verify new circulation code type in the list*/
		if(!currencyPage.verifyValues(program.get(0))){
			currencyPage.clickOnInsert();
			
			currencyPage.createProgramEvent(program);
			
			currencyPage.clickOnUpdate();
			
			currencyPage.clickOnCancel();
						
			if(currencyPage.verifyValues(program.get(0))){
				testcases.add(getCurreentDate()+" | Pass : Program event "+program.get(0)+ " present in grid");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Program event "+program.get(0)+ "not present in grid");
			}
			
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : Program event "+program.get(0)+ " present in grid");
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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "A095" };
		String [] selectedNames = {"userName","passWord","currencyCode","program"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
