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
 * Test Reference No	:   A024 Batch Types
 * Purpose              :   Set Up Additional Batch Types
 * Date					:   05-05-2014
 * ACCESS               :   AKK
 */


public class A024_Batch_TypesTest extends BaseTest{

	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{	
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String batchCode = dataRow.get("batchCode");

		List<String> batchTypeForTran = dataRow.findNamesReturnValues("batchTypeForTran");
		List<String> batchTypeForRev = dataRow.findNamesReturnValues("batchTypeForRev");
		List<String> batchTypeForBase = dataRow.findNamesReturnValues("batchTypeForBase");
		
		List<String> batchTypeForAccr = dataRow.findNamesReturnValues("batchTypeForAccr");
		List<String> batchTypeForForn = dataRow.findNamesReturnValues("batchTypeForForn");
		List<String> batchTypeForFrnb = dataRow.findNamesReturnValues("batchTypeForFrnb");
		
		List<String> batchTypeForPlan = dataRow.findNamesReturnValues("batchTypeForPlan");
		List<String> batchTypeForRecc = dataRow.findNamesReturnValues("batchTypeForRecc");

		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Search currency code*/
		currencyPage.fillCurrenceyCode(batchCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+batchCode+" - Batch Type List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		/*Insert new batch type details*/
		currencyPage.clickOnInsert();
		
		createBatchType(currencyPage,batchTypeForTran);
		createBatchType(currencyPage,batchTypeForRev);
		createBatchType(currencyPage,batchTypeForBase);
		createBatchType(currencyPage,batchTypeForAccr);
		createBatchType(currencyPage,batchTypeForForn);
		createBatchType(currencyPage,batchTypeForFrnb);
		createBatchType(currencyPage,batchTypeForPlan);
		createBatchType(currencyPage,batchTypeForRecc);

		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.logOut(2);
	}

	
	private void createBatchType(CurrencyPage currencyPage,List<String> batchType) throws InterruptedException{
		String message = "The previously-requested action has been performed";

		/*Create new Batch type*/
		boolean update = currencyPage.enterLedgerBatchTypeDetails(batchType);
		
		if(update == true){
			currencyPage.clickOnUpdate();
			
			/*Verify new batch type in the Batch list*/
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : New batch type  "+batchType.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New batch type  "+batchType.get(0)+ " not displayed in the list");
			}	
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New batch type  "+batchType.get(0)+ " displayed in the list");
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
		String xmlFilePath = folder  + "A024.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
