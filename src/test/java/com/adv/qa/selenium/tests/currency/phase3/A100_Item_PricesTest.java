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
 * Test Reference No	: 	A100 Item Prices
 * Purpose              :   Set Up Items Prices
 * ACCESS               :   PXO,PIM
 */

public class A100_Item_PricesTest extends BaseTest{
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
		String priceType = dataRow.get("priceType");
		List<String> item525 = dataRow.findNamesReturnValues("item525");
		List<String> item626 = dataRow.findNamesReturnValues("item626");
		List<String> item325 = dataRow.findNamesReturnValues("item325");
		List<String> item335 = dataRow.findNamesReturnValues("item335");
		List<String> item160 = dataRow.findNamesReturnValues("item160");
				
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Price Types - List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();
		
		boolean update = currencyPage.insertPriceType(companyId, priceType);
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New Item price "+companyId+ " displayed in the list");
		}
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		
		/*Verify new Items in the list*/
		if(currencyPage.verifyValues(companyId)){
			testcases.add(getCurreentDate()+" | Pass : New Item price "+companyId+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New Item price "+companyId+ " not displayed in the list");
		}
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Item Prices List","Currency search page","displayed");

		currencyPage.searchValue(companyId,item525, 11, 8);

		createSupplierForItem(currencyPage,item525);
		createSupplierForItem(currencyPage,item626);
		createSupplierForItem(currencyPage,item325);
		createSupplierForItem(currencyPage,item335);
		createSupplierForItem(currencyPage,item160);
	
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
				
		verifySupplierItem(currencyPage,item525);
		verifySupplierItem(currencyPage,item626);
		verifySupplierItem(currencyPage,item325);
		verifySupplierItem(currencyPage,item335);
		verifySupplierItem(currencyPage,item160);
			
		currencyPage.logOut(2);
	}
	
	private void createSupplierForItem(CurrencyPageNew currencyPage,List<String> item){
		currencyPage.clickOnInsert();
		
		boolean update = currencyPage.insertItemPrice(item,companyId);
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New Item price "+item.get(1)+ " displayed in the list");
		}
	}
	
	private void verifySupplierItem(CurrencyPageNew currencyPage,List<String> item){
		/*Verify new item supplier in the list*/
		if(currencyPage.verifyValues(item.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New item price "+item.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New item price "+item.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A100" };
		String [] selectedNames = {"userName","passWord","currencyCode","priceType","item525","item626","item325","item335","item160"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
