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
 * Test Reference No	: 	AD11014 Recurring Payments for Invoices
 * Purpose              :   Create a recurring payment for a invoice 
 * ACCESS               :   GBB,GZA
 */

public class AD11014_Recurring_Payments_For_InvoicesTest extends BaseTest{
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
				
		verifySuspendInvoice(currencyPage,dataRow);
		
		currencyPage.logOut(2);
	}

		
	private void createInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "EDTGBTCH ACT=INSERT,CMPY="+companyId+",TRAN=1";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> transactionDetails = dataRow.findNamesReturnValues("transactionDetails");
		List<String> invoiceDetails = dataRow.findNamesReturnValues("invoiceDetails");
		List<String> invoiceDetailLines = dataRow.findNamesReturnValues("invoiceDetailLines");
		
		String message = "Batch Number";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Enter Transaction Batch Header","Currency search page","displayed");
		
		currencyPage.enterInvoice(transactionDetails,"Transaction");
		
		currencyPage.clickOnNewTransaction();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Transaction Header"),"Transaction page","displayed");
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());
			
		currencyPage.enterTransactionDetails(invoiceDetails,currDate,currDate);
		
		currencyPage.clickOnAcceptWarn();
		
		currencyPage.clickOnLines();
		
		currencyPage.enterTaxableDetails(invoiceDetailLines, 1);
						
		currencyPage.clickOnUpdate();
					
		String referenceMessage = currencyPage.getToolContentText();
		
		if(!currencyPage.getToolContentText().contains(message)){
			currencyPage.clickOnUpdtWarn();
		}
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Invoice created successfully "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice not created");
		}		
		
		currencyPage.clickOnReturnButton();
		
	}
	
	
	public void verifySuspendInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> supplierTransaction = dataRow.findNamesReturnValues("supplierTransaction");
				
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Supplier Selection","Currency search page","displayed");
		
		currencyPage.searchElement(companyId,supplierTransaction, 10);
		
		currencyPage.selectEntity(supplierTransaction.get(1));
		
		currencyPage.clickOnButton(38);
		
		boolean status = currencyPage.verifyRecurringStatus();
		
		if(status == true){
			testcases.add(getCurreentDate()+" | Pass : Invoice recurring interval and occurance is as expected ");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice recurring interval and occurance is not expected");
		}
		
		currencyPage.clickOnCancel();
		
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
		String[] nodeID = { "AD11014" };
		String [] selectedNames = {"userName","passWord","currencyCode","transactionDetails","invoiceDetails","invoiceDetailLines","supplierTransaction"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
