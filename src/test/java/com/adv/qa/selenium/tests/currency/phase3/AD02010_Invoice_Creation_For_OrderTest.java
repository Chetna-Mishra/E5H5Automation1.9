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
 * Test Reference No	: 	AD02010 Invoice Creation for Order
 * Purpose              :   Create Stock Adjustment In 
 * ACCESS               :   GBB
 */

public class AD02010_Invoice_Creation_For_OrderTest extends BaseTest{
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
		String code = "EDTGBTCH ACT=INSERT,CMPY="+companyId+",TRAN=1";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> transactionDetails = dataRow.findNamesReturnValues("transactionDetails");
		List<String> invoiceDetails = dataRow.findNamesReturnValues("invoiceDetails");
		List<String> invoiceDetailLines = dataRow.findNamesReturnValues("invoiceDetailLines");
		
		String message = "Batch Number has been created with sysref";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Enter Transaction Batch Header","Currency search page","displayed");
		
		currencyPage.enterInvoice(transactionDetails,"Transaction");
		
		currencyPage.clickOnNewTransaction();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Transaction Header"),"Transaction page","displayed");
		
	    Calendar currentMonth = Calendar.getInstance();
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
	    String currDate = dateFormat1.format(currentMonth.getTime());
		
		currencyPage.enterTransactionDetails(invoiceDetails,currDate,"null");
		
		currencyPage.clickOnLines();//currencyPage.clickOnButton(98);
		
		currencyPage.enterInvoiceDetails(invoiceDetailLines);
		
		currencyPage.clickOnAcceptWarn();
		
		currencyPage.clickOnUpdate();
				
		String referenceMessage = currencyPage.getToolContentText();
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Invoice created successfully "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice not created");
		}		
		
		currencyPage.clickOnReturnButton();
		
		currencyPage.isCommandDisplayed();
	}

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> searchValue = dataRow.findNamesReturnValues("searchValue");
		List<String> pdeBatchDetails1 = dataRow.findNamesReturnValues("pdeBatchDetails1");
		List<String> pdeBatchDetails2 = dataRow.findNamesReturnValues("pdeBatchDetails2");
		List<String> accBatchDetails1 = dataRow.findNamesReturnValues("accBatchDetails1");
		List<String> accBatchDetails2 = dataRow.findNamesReturnValues("accBatchDetails2");
					
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search(searchValue.get(0), 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Journal Header"),"Journal header page","displayed");

		currencyPage.clickOnLines();
		
		boolean batchDetailsPde1 = currencyPage.verifyJournalDetails(1, pdeBatchDetails1);
		Assert.assertTrue(testcases,batchDetailsPde1,"PDE Batch values are "," as expected");
		
		boolean batchDetailsPde2 = currencyPage.verifyJournalDetails(2, pdeBatchDetails2);
		Assert.assertTrue(testcases,batchDetailsPde2,"PDE Batch values are "," as expected");
		
		currencyPage.clickOnButton(16);
		
		currencyPage.search(searchValue.get(1), 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();

		currencyPage.clickOnLines();
		
		boolean batchDetails1 = currencyPage.verifyJournalDetails(1, accBatchDetails1);
		Assert.assertTrue(testcases,batchDetails1,"ACC Batch values are "," as expected");
		
		boolean batchDetails2 = currencyPage.verifyJournalDetails(2, accBatchDetails2);				
		Assert.assertTrue(testcases,batchDetails2,"ACC Batch values are "," as expected");

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
		String[] nodeID = { "AD02010" };
		String [] selectedNames = {"userName","passWord","currencyCode","searchValue","transactionDetails","invoiceDetails","invoiceDetailLines","pdeBatchDetails1","pdeBatchDetails2",
				"accBatchDetails1","accBatchDetails2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
