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
 * Test Reference No	: 	A092 Supplier Elements
 * Purpose              :   Create Supplier Elements 
 * ACCESS               :   PBH
 */

public class A092_Supplier_ElementsTest extends BaseTest{
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
		List<String> supplierList = dataRow.findNamesReturnValues("supplierList");
		List<String> sonySupplier = dataRow.findNamesReturnValues("sonySupplier");
		List<String> panasonicSupplier = dataRow.findNamesReturnValues("panasonicSupplier");
		List<String> pioneerSupplier = dataRow.findNamesReturnValues("pioneerSupplier");
		List<String> yamahaSupplier = dataRow.findNamesReturnValues("yamahaSupplier");
		List<String> denonSupplier = dataRow.findNamesReturnValues("denonSupplier");
		List<String> akaiSupplier = dataRow.findNamesReturnValues("akaiSupplier");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Supplier Element List","Currency search page","displayed");

		currencyPage.searchValue(companyId,sonySupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.searchValue(companyId,panasonicSupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.searchValue(companyId,pioneerSupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.searchValue(companyId,yamahaSupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.searchValue(companyId,denonSupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.searchValue(companyId,akaiSupplier,2,1);
		addElementsToSupplier(currencyPage,supplierList,currencyCode);
		
		currencyPage.logOut(2);

	}
	
	/*Create elements for Supplier*/
	private void addElementsToSupplier(CurrencyPageNew currencyPage,List<String> elements,String currencyCode) throws InterruptedException{
		currencyPage.clickOnInsert();
		
		/*Create supplier elements*/
		currencyPage.enterSupplierElements(elements);	
		
		currencyPage.clickOnUpdate();
		
		currencyPage.exitFromSupplierElement();
		
		currencyPage.clickOnRefresh();
		
		for(String supplier : elements){
			/*Verify new batch type in the list*/
			Assert.assertTrue(testcases,currencyPage.verifyValues(supplier), "New supplier element "+supplier," created");
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
		String[] nodeID = { "A092" };
		String [] selectedNames = {"userName","passWord","currencyCode","sonySupplier","panasonicSupplier","pioneerSupplier","yamahaSupplier","supplierList"
				,"denonSupplier","akaiSupplier"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
