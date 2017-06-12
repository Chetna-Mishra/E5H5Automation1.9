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

public class A098_Item_CreationTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> item525 = dataRow.findNamesReturnValues("item525");	
		List<String> item626 = dataRow.findNamesReturnValues("item626");		
		List<String> item325 = dataRow.findNamesReturnValues("item325");		
		List<String> item335 = dataRow.findNamesReturnValues("item335");		
		List<String> item160 = dataRow.findNamesReturnValues("item160");
		List<String> itemDvdSrvc = dataRow.findNamesReturnValues("itemDvdSrvc");	
		List<String> item909 = dataRow.findNamesReturnValues("item909");	
		List<String> itemGen1 = dataRow.findNamesReturnValues("itemGen1");
		List<String> itemITEM1 = dataRow.findNamesReturnValues("itemITEM1");		
		List<String> itemITEM2 = dataRow.findNamesReturnValues("itemITEM2");		
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		createItem(currencyPage,item525,0);
		createItem(currencyPage,item626,0);
		createItem(currencyPage,item325,0);
		createItem(currencyPage,item335,0);
		createItem(currencyPage,item160,1);
		createItem(currencyPage,itemDvdSrvc,1);
		createItem(currencyPage,item909,1);
		createItem(currencyPage,itemGen1,0);
		createItem(currencyPage,itemITEM1,0);
		createItem(currencyPage,itemITEM2,0);
		
		currencyPage.logOut(1);
	}
	
	public void createItem(CurrencyPageNew currencyPage,List<String> items,int i) throws InterruptedException{
		String command = "EDTPITEM ACT=INSERT,COMPANY="+companyId;
		String message = "The previously-requested action has been performed";
			
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(command);
		
		currencyPage.createItem(items,i);
		
		currencyPage.clickOnUpdate();
						
		currencyPage.clickOnUpdate();		
		
		currencyPage.isCommandDisplayed();
		
		/*Verify new item code in the list*/
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : New item code "+items.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New item code "+items.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A098" };
		String [] selectedNames = {"userName","passWord","currencyCode","item525","item626","item325","item335","item160","itemDvdSrvc","item909","itemGen1","itemITEM1","itemITEM2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
