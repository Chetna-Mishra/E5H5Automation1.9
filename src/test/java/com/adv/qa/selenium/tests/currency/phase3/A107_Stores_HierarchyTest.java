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
 * Test Reference No	: 	A107 Stores Hierarchy
 * Purpose              :   Stores Hierarchy 
 * ACCESS               :   HAI
 */

public class A107_Stores_HierarchyTest extends BaseTest{
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
		List<String> northStore = dataRow.findNamesReturnValues("northStore");
		List<String> southStore = dataRow.findNamesReturnValues("southStore");
		List<String> eastStore = dataRow.findNamesReturnValues("eastStore");
		List<String> westStore = dataRow.findNamesReturnValues("westStore");

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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - IM Stores Hierarchy List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,northStore, 2, 1);
		
		currencyPage.clickOnInsert();
		
		createIMAccounts(currencyPage,northStore);
		createIMAccounts(currencyPage,southStore);
		createIMAccounts(currencyPage,eastStore);
		createIMAccounts(currencyPage,westStore);

		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,northStore);
		verifyValues(currencyPage,southStore);
		verifyValues(currencyPage,eastStore);
		verifyValues(currencyPage,westStore);
		
		currencyPage.logOut(2);
	}
	
	
	private void createIMAccounts(CurrencyPageNew currencyPage,List<String> elements){
		boolean update = currencyPage.createStoresHierarchy(elements);

		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New store hierarchy "+elements.get(0)+ "  displayed in the list");
		}

	}
	
	private void verifyValues(CurrencyPageNew currencyPage,List<String> elements){
		
		if(!currencyPage.verifyValues(elements.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New store hierarchy "+elements.get(0)+ "  displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New store hierarchy "+elements.get(0)+ "  displayed in the list");
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
		String[] nodeID = { "A107" };
		String [] selectedNames = {"userName","passWord","currencyCode","northStore","southStore","eastStore","westStore"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
