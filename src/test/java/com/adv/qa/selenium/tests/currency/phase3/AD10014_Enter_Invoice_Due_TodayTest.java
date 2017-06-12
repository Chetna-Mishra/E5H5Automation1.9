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
 * Test Reference No	: 	AD10014 Enter Invoice Due Today
 * Purpose              :   Enter invoice with settlement date ‘Today’ 
 * ACCESS               :   GBB
 */

public class AD10014_Enter_Invoice_Due_TodayTest extends BaseTest{
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

		verifyTransactionMenu(currencyPage,dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		verifyUnauthorisedTransaction(currencyPage,dataRow);
		
		currencyPage.logOut(2);
	}

	
	
	private void createInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "EDTGBTCH ACT=INSERT,CMPY="+companyId+",TRAN=1";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> invoiceDetails = dataRow.findNamesReturnValues("invoiceDetails");
		List<String> transactionDetails = dataRow.findNamesReturnValues("transactionDetails");
		List<String> lineDetails = dataRow.findNamesReturnValues("lineDetails");
		List<String> verifyLineDetails = dataRow.findNamesReturnValues("verifyLineDetails");

		String message = "created with sysref";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Enter Transaction Batch Header","Currency search page","displayed");
		
		currencyPage.enterInvoice(invoiceDetails,"Transaction");
		
		currencyPage.clickOnNewTransaction();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Transaction Header"),"Transaction page","displayed");
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());
			
		currencyPage.enterTransactionDetails(transactionDetails,currDate,currDate);
		
		currencyPage.clickOnAcceptWarn();
		
		currencyPage.clickOnLines();
		
		currencyPage.enterTaxableDetails(lineDetails, 1);
		
		currencyPage.clickOnAcceptWarn();
		
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
		boolean storeItemValues = currencyPage.verifyStoreItem(verifyLineDetails, 0);
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}
		
		currencyPage.clickOnReturnButton();
		
		currencyPage.isCommandDisplayed();

	}
	
	private void verifyTransactionMenu(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> supplierTransaction = dataRow.findNamesReturnValues("supplierTransaction");

		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Supplier Selection","Currency search page","displayed");
		
		currencyPage.searchElement(companyId,supplierTransaction, 10);

		boolean verifySupplier = currencyPage.verifySupplierTransaction(supplierTransaction);
		if(verifySupplier== true){
			testcases.add(getCurreentDate()+" | Pass : Supplier transaction is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Supplier transaction is not as expected ");
		}
		
		currencyPage.clickOnCancel();
	}

	private void verifyUnauthorisedTransaction(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> unauthorisedTransaction = dataRow.findNamesReturnValues("unauthorisedTransaction");
		String message = "The previously-requested action will be performed in background";

		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Unauthorised Transactions List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,unauthorisedTransaction, 11,10);

		boolean verifyTransaction = currencyPage.verifyUnauthorisedTransaction(unauthorisedTransaction);
		if(verifyTransaction== true){
			testcases.add(getCurreentDate()+" | Pass : Supplier Unauthorised Transaction is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Supplier Unauthorised Transaction are is not as expected ");
		}
		
		currencyPage.authoriseTransaction();
		
		if(currencyPage.verifyValues(message)){
			testcases.add(getCurreentDate()+" | Pass : Invoice  "+unauthorisedTransaction.get(1) +" Authorised successfully");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Invoice  "+unauthorisedTransaction.get(1) +" not Authorised");
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
		String[] nodeID = { "AD10014" };
		String [] selectedNames = {"userName","passWord","currencyCode","invoiceDetails","transactionDetails","lineDetails","supplierTransaction","unauthorisedTransaction","verifyLineDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
