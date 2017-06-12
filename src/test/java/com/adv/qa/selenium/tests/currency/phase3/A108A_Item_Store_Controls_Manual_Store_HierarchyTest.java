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
 * Test Reference No	: 	A108 Item Store Controls/Manual Store Hierarchy
 * Purpose              :   Item Store Controls 
 * ACCESS               :   HAE
 */

public class A108A_Item_Store_Controls_Manual_Store_HierarchyTest extends BaseTest{
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
		List<String> storeControl8 = dataRow.findNamesReturnValues("storeControl8");
		List<String> storeControl9 = dataRow.findNamesReturnValues("storeControl9");
		List<String> storeControl10 = dataRow.findNamesReturnValues("storeControl10");
		List<String> storeControl11 = dataRow.findNamesReturnValues("storeControl11");
		List<String> storeControl12 = dataRow.findNamesReturnValues("storeControl12");
		List<String> storeControl13 = dataRow.findNamesReturnValues("storeControl13");
		List<String> storeControl14 = dataRow.findNamesReturnValues("storeControl14");
		List<String> storeControl15 = dataRow.findNamesReturnValues("storeControl15");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - IM Item / Store Controls List","Currency search page","displayed");

		currencyPage.searchValue(companyId,storeControl8, 3, 1);
		
		currencyPage.clickOnInsert();
		
		createInventoryStoreControl(currencyPage,storeControl8);
		createInventoryStoreControl(currencyPage,storeControl9);
		createInventoryStoreControl(currencyPage,storeControl10);
		createInventoryStoreControl(currencyPage,storeControl11);
		createInventoryStoreControl(currencyPage,storeControl12);
		createInventoryStoreControl(currencyPage,storeControl13);
		createInventoryStoreControl(currencyPage,storeControl14);
		createInventoryStoreControl(currencyPage,storeControl15);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
				
		verifyValues(currencyPage,storeControl8);
		verifyValues(currencyPage,storeControl9);
		verifyValues(currencyPage,storeControl10);
		verifyValues(currencyPage,storeControl11);
		verifyValues(currencyPage,storeControl12);
		verifyValues(currencyPage,storeControl13);
		verifyValues(currencyPage,storeControl14);
		verifyValues(currencyPage,storeControl15);

		currencyPage.logOut(2);
	}
	
	
	private void createInventoryStoreControl(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{
		boolean update = currencyPage.insertItemStoreControl(elements);

		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New item story control "+elements.get(0)+ "  displayed in the list");
		}

	}
	
	private void verifyValues(CurrencyPageNew currencyPage,List<String> elements){
		
		if(!currencyPage.verifyValues(elements.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New item story control "+elements.get(0)+ "  displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New item story control "+elements.get(0)+ "  displayed in the list");
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
		String[] nodeID = { "A108A" };
		String [] selectedNames = {"userName","passWord","currencyCode","storeControl8","storeControl9","storeControl10",
				"storeControl11","storeControl12","storeControl13","storeControl14","storeControl15"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
