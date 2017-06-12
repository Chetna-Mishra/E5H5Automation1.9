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
 * Test Reference No	: 	A091A Suppliers
 * Purpose              :   Set Up Suppliers 
 * ACCESS               :   PBA
 */

public class A091A_SupplierAuditTest extends BaseTest{
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
		List<String> sonySupplier = dataRow.findNamesReturnValues("sonySupplier");
		List<String> auditDetails = dataRow.findNamesReturnValues("auditDetails");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Supplier List","Currency search page","displayed");

		currencyPage.searchValue(companyId,sonySupplier,8,1);
		
		currencyPage.clickOnAmend();
		
		/*Create batch type code*/
		currencyPage.amendSupplierAddress(sonySupplier);	
		
		currencyPage.clickOnUpdate();
		
		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(sonySupplier.get(0)), "New supplier  "+sonySupplier.get(0)," created");
		
		currencyPage.clickOnCancel();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Audit List","Currency search page","displayed");

		currencyPage.searchValue(companyId,auditDetails,21,7);
		
		currencyPage.expandAction();
		
		/*Verify new circulation code type in the list*/
		if(currencyPage.verifyValues(sonySupplier.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Supplier changes for "+companyId+ " reflected");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Supplier changes for  "+companyId+ " not reflected");
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
		String[] nodeID = { "A091A" };
		String [] selectedNames = {"userName","passWord","currencyCode","sonySupplier","auditDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
