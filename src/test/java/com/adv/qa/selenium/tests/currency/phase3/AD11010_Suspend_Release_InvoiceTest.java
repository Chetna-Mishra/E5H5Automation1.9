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
 * Test Reference No	: 	AD11010 Suspend/Release Invoice
 * Purpose              :   Suspend Invoice 
 * ACCESS               :   GBB,GDM,GZA,GO3,GGA
 */

public class AD11010_Suspend_Release_InvoiceTest extends BaseTest{
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
		
		suspendReleaseInvoice(currencyPage,dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		verifySuspendInvoice(currencyPage,dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		verifyUnauthorisedTransaction(currencyPage,dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		scheduleMaintenance(currencyPage,dataRow);
		
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
	
	public void  suspendReleaseInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> suspendDetails = dataRow.findNamesReturnValues("suspendDetails");
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Transaction Suspend/Release Li","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, suspendDetails, 8, 7);
		
		currencyPage.selectEntity(suspendDetails.get(1));
		
		currencyPage.clickOnButton(11);
		
		currencyPage.enterTransactionSuspendAndRelease(suspendDetails);
		
		currencyPage.clickOnUpdate();
		
		boolean status = currencyPage.verifySuspendIndex();
		
		if(status == true){
			testcases.add(getCurreentDate()+" | Pass : Invoice suspended ");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice not suspended");
		}

	}
	
	public void verifySuspendInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> suspendDetails = dataRow.findNamesReturnValues("suspendDetails");
				
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Supplier Selection","Currency search page","displayed");
		
		currencyPage.searchElement(companyId,suspendDetails, 10);
		
		currencyPage.selectEntity(suspendDetails.get(1));
		
		currencyPage.clickOnButton(38);
		
		boolean status = currencyPage.verifyAuthrisationStatus();
		
		if(status == true){
			testcases.add(getCurreentDate()+" | Pass : Invoice authorisation staus and suspend indicatior is as expected ");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Invoice authorisation staus and suspend indicatior is not expected");
		}
		
		currencyPage.clickOnCancel();
		
		currencyPage.clickOnCancel();

	}
	
	private void verifyUnauthorisedTransaction(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> unauthorisedTransaction = dataRow.findNamesReturnValues("unauthorisedTransaction");
		String message = "The previously-requested action will be performed in background";

		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(3));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(3)+" - Unauthorised Transactions List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,unauthorisedTransaction, 11,11);

		boolean verifyTransaction = currencyPage.verifyUnauthorisedTransaction(unauthorisedTransaction);
		if(verifyTransaction== true){
			testcases.add(getCurreentDate()+" | Pass : Supplier Unauthorised Transaction is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Supplier Unauthorised Transaction are is not as expected ");
		}
		
		currencyPage.authoriseTransaction();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Invoice  "+unauthorisedTransaction.get(1) +" Authorised successfully");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Invoice  "+unauthorisedTransaction.get(1) +" not Authorised");
		}
	}
	
	private void scheduleMaintenance(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> scheduleDetails = dataRow.findNamesReturnValues("scheduleDetails");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(4));

		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(4)+" - Payment Schedule List","Currency search page","displayed");
		
		currencyPage.search(companyId,6,0);
		
		currencyPage.clickOnInsert();
		
		currencyPage.scheduleMaintenance(scheduleDetails);
		
		currencyPage.clickOnUpdate();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.sortValues();
		
		currencyPage.clickOnButton(9);//Suppler button
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Schedule Suppliers Maintenance"),"Schedule suppliers maintenance page ","displayed");
		
		currencyPage.clickOnInsert();
		
		currencyPage.enterSchedueSupplierTranDetails(scheduleDetails);
		
		currencyPage.clickOnButton(18);//Due button
		
		if(currencyPage.getToolContentText().contains(" No unallocated transactions were found for this Supplier")){
			testcases.add(getCurreentDate()+" | Pass : Invoice  suspended and schedule not added");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Invoice  suspended and schedule not added");
		}
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
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
		String[] nodeID = { "AD11010" };
		String [] selectedNames = {"userName","passWord","currencyCode","transactionDetails","invoiceDetails","invoiceDetailLines","suspendDetails","unauthorisedTransaction"
				,"scheduleDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
