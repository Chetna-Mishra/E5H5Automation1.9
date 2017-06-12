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
 * Test Reference No	: 	A011 Users
 * Purpose              :   Set Up Users
 * Date					:   13-05-2014
 * ACCESS               :   AFC
 */

public class A011_UsersTest extends BaseTest{
	
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
		
		List<String> firstUser = dataRow.findNamesReturnValues("firstUser");
		List<String> secondUser = dataRow.findNamesReturnValues("secondUser");
		List<String> thirdUser = dataRow.findNamesReturnValues("thirdUser");
		List<String> fourthUser = dataRow.findNamesReturnValues("fourthUser");
		List<String> fifthUser = dataRow.findNamesReturnValues("fifthUser");
		List<String> sixthUser = dataRow.findNamesReturnValues("sixthUser");
		List<String> userBuyer = dataRow.findNamesReturnValues("userBuyer");
		List<String> userEXPED = dataRow.findNamesReturnValues("userEXPED");
		List<String> userGREC = dataRow.findNamesReturnValues("userGREC");
	
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - User List","Currency search page","displayed");
				
		/*Create users*/
		createUsers(firstUser,currencyPage);
		createUsers(secondUser,currencyPage);
		createUsers(thirdUser,currencyPage);
		createUsers(fourthUser,currencyPage);
		createUsers(fifthUser,currencyPage);
		createUsers(sixthUser,currencyPage);
		createUsers(userBuyer,currencyPage);
		createUsers(userEXPED,currencyPage);
		createUsers(userGREC,currencyPage);
		
		currencyPage.logOut(2);
	}
	
	private void createUsers(List<String> users,CurrencyPage currencyPage) throws InterruptedException{
		currencyPage.search(users.get(0),4,3);
		
		if(currencyPage.verifyValues(users.get(0))){
			currencyPage.clickOnAmend();
			
			currencyPage.amendUsersDetails(companyId);
			
			currencyPage.clickOnUpdate();		
		}
		else{
			currencyPage.clickOnInsert();
			
			currencyPage.enterUsersDetails(companyId,users);
			
			currencyPage.clickOnUpdate();
			
			/*Exit from the company details page*/
			currencyPage.clickOnCancel();
			
			currencyPage.isConfirmPopUpDisplayed();
		}	

		/*Verify new company in the list*/
		if(currencyPage.verifyValues(users.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New user "+users.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New user "+users.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A011" };
		String [] selectedNames = {"userName","passWord","code","firstUser","secondUser","thirdUser","fourthUser","fifthUser","sixthUser",
				"userBuyer","userEXPED","userGREC"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
