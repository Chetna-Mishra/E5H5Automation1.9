package com.adv.qa.selenium.tests.currency.phase3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * Test Reference No	: 	AD12005 Supplier Financial Commitment
 * Purpose              :   Supplier Financial Commitment 
 * ACCESS               :   PBA
 */

public class AD12005_Supplier_Financial_CommitmentTest extends BaseTest{
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
		List<String> supplierFinancialValue = dataRow.findNamesReturnValues("supplierFinancialValue");
		String message = "The previously-requested action has been performed";
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");

		currencyPage.fillCurrenceyCode(currencyCode);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Supplier List","Currency search page","displayed");

		currencyPage.searchValue(companyId,supplierFinancialValue, 8, 1);
		
		currencyPage.clickOnGoLimits();
		
        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        currentMonth.add(Calendar.DATE, 6);
        String reviewDate = dateFormat1.format(currentMonth.getTime());
		
		currencyPage.logSupplierFinanacialLimit(supplierFinancialValue,reviewDate);
		
		currencyPage.clickOnUpdate();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Supplier financial commitment value updated");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Supplier financial commitment value not updated");
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
		String[] nodeID = { "AD12005" };
		String [] selectedNames = {"userName","passWord","currencyCode","supplierFinancialValue"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
