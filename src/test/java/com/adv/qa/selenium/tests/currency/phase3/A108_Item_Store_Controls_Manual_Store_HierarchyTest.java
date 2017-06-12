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

public class A108_Item_Store_Controls_Manual_Store_HierarchyTest extends BaseTest{
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
		List<String> storeControl1 = dataRow.findNamesReturnValues("storeControl1");
		List<String> storeControl2 = dataRow.findNamesReturnValues("storeControl2");
		List<String> storeControl3 = dataRow.findNamesReturnValues("storeControl3");
		List<String> storeControl4 = dataRow.findNamesReturnValues("storeControl4");
		List<String> storeControl5 = dataRow.findNamesReturnValues("storeControl5");
		List<String> storeControl6 = dataRow.findNamesReturnValues("storeControl6");
		List<String> storeControl7 = dataRow.findNamesReturnValues("storeControl7");
		
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

		currencyPage.searchValue(companyId,storeControl1, 3, 1);
		
		currencyPage.clickOnInsert();
		
		createInventoryStoreControl(currencyPage,storeControl1);
		createInventoryStoreControl(currencyPage,storeControl2);
		createInventoryStoreControl(currencyPage,storeControl3);
		createInventoryStoreControl(currencyPage,storeControl4);
		createInventoryStoreControl(currencyPage,storeControl5);
		createInventoryStoreControl(currencyPage,storeControl6);
		createInventoryStoreControl(currencyPage,storeControl7);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		verifyValues(currencyPage,storeControl1);
		verifyValues(currencyPage,storeControl2);
		verifyValues(currencyPage,storeControl3);
		verifyValues(currencyPage,storeControl4);
		verifyValues(currencyPage,storeControl5);
		verifyValues(currencyPage,storeControl6);
		verifyValues(currencyPage,storeControl7);

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
		String[] nodeID = { "A108" };
		String [] selectedNames = {"userName","passWord","currencyCode","storeControl1","storeControl2","storeControl3","storeControl4",
				"storeControl5","storeControl6","storeControl7"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
