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
 * Test Reference No	: 	A098 Item Creation
 * Purpose              :   Set Up Items
 * ACCESS               :   PIB
 */

public class A101_Item_BuyersTest extends BaseTest{
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
		List<String> item0 = dataRow.findNamesReturnValues("item0");	
		List<String> item1 = dataRow.findNamesReturnValues("item1");	
		List<String> item2 = dataRow.findNamesReturnValues("item2");	
		List<String> item3 = dataRow.findNamesReturnValues("item3");	
		List<String> item4 = dataRow.findNamesReturnValues("item4");	
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		createItem(currencyPage,currencyCode,item0);
		createItem(currencyPage,currencyCode,item1);
		createItem(currencyPage,currencyCode,item2);
		createItem(currencyPage,currencyCode,item3);
		createItem(currencyPage,currencyCode,item4);
		
		currencyPage.logOut(1);
	}
	
	public void createItem(CurrencyPageNew currencyPage,String currencyCode,List<String> items) throws InterruptedException{
		String command = "EDTPIBYR ACT=INSERT,COMPANY="+companyId;
		String message = "The previously-requested action has been performed";
			
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(command);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Item Buyer Edit","Currency search page","displayed");

		currencyPage.insertItemBuyer(items);
		
		currencyPage.clickOnUpdate();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : New Item buyer "+items.get(0)+ " displayed in the list");
		}
		else{
			currencyPage.clickOnCancel();
			
			testcases.add(getCurreentDate()+" | Fail : New Item buyer "+items.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A101" };
		String [] selectedNames = {"userName","passWord","currencyCode","item0","item1","item2","item3","item4"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
