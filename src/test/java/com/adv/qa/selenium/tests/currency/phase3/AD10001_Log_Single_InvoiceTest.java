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
 * Test Reference No	: 	AD10001 Log Single Invoice
 * Purpose              :   Create Stock Adjustment In 
 * ACCESS               :   GCA
 */

public class AD10001_Log_Single_InvoiceTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
				
		createInvoice(currencyPage,dataRow);
		
		currencyPage.isCommandDisplayed();
		
		reviewBatches(currencyPage, dataRow);
								
		currencyPage.logOut(2);
	}
	
	
	private void createInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "LOGGBTCH ACT=INSERT,CMPY="+companyId+",TRAN-TYPE=1";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> invoiceDetails = dataRow.findNamesReturnValues("invoiceDetails");
		List<String> transactionDetails = dataRow.findNamesReturnValues("transactionDetails");
		
		List<String> header = dataRow.findNamesReturnValues("header");
		String message = "Batch Number";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Log Batch Header","Currency search page","displayed");
		
		currencyPage.enterInvoice(invoiceDetails,"Invoice");
		
		currencyPage.clickOnButton(13);
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Log Transaction Header"),"Transaction page","displayed");
		
		currencyPage.logTransactionDetails(transactionDetails);
		
		currencyPage.clickOnAcceptWarnings();
						
		currencyPage.clickOnUpdate();
						
		String referenceMessage = currencyPage.getToolContentText();
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Invoice created successfully "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice not created");
		}		
		
		boolean storeItemValues = currencyPage.verifyStoreItem(header, 1);
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}
		
		currencyPage.clickOnReturnButton();
		
		currencyPage.isCommandDisplayed();
	}

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> batchDetails1 = dataRow.findNamesReturnValues("batchDetails1");
		List<String> batchDetails2 = dataRow.findNamesReturnValues("batchDetails2");
					
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search("PDE2", 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Journal Header"),"Journal header page","displayed");

		currencyPage.clickOnLines();
		
		boolean batchDetailsValues1 = currencyPage.verifyJournalDetails(1, batchDetails1);
		Assert.assertTrue(testcases,batchDetailsValues1,"2150 batch values are "," correct");
		
		boolean batchDetailsValues2 = currencyPage.verifyJournalDetails(2, batchDetails2);
		Assert.assertTrue(testcases,batchDetailsValues2,"VATA batch values are "," correct");
		
		currencyPage.clickOnCancel();				
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
		String[] nodeID = { "AD10001" };
		String [] selectedNames = {"userName","passWord","currencyCode","invoiceDetails","transactionDetails","header","batchDetails1","batchDetails2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
