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
 * Test Reference No	: 	AD10005 Enter FOC Invoice
 * Purpose              :   Enter FOC Invoice 
 * ACCESS               :   GBB
 */

public class AD10005_Enter_FOC_InvoiceTest extends BaseTest{
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
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - AP Company Controls List","Currency search page","displayed");

		currencyPage.search(companyId, 1, 0);
		
		currencyPage.clickOnAmend();
		
		currencyPage.amendAPCompanyControl(3,"Allowed");
		
		currencyPage.clickOnUpdate();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
				
		createInvoice(currencyPage,dataRow);
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.logOut(1);
	}

	
	
	private void createInvoice(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "EDTGBTCH ACT=INSERT,CMPY="+companyId+",TRAN=1";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> invoiceDetails = dataRow.findNamesReturnValues("invoiceDetails");
		List<String> transactionDetails = dataRow.findNamesReturnValues("transactionDetails");
		List<String> lineDetails = dataRow.findNamesReturnValues("lineDetails");
		List<String> splitLineDetails = dataRow.findNamesReturnValues("splitLineDetails");		
		String message = "Batch Number has been created with sysref";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Enter Transaction Batch Header","Currency search page","displayed");
		
		currencyPage.enterInvoice(invoiceDetails,"Transaction");
		
		currencyPage.clickOnNewTransaction();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Transaction Header"),"Transaction page","displayed");
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());
			
		currencyPage.enterTransactionDetails(transactionDetails,currDate,"null");
		
		currencyPage.clickOnAcceptWarn();
		
		currencyPage.clickOnLines();
		
		currencyPage.enterTaxableDetails(lineDetails, 2);
		
		currencyPage.clickOnGoSplitAnalysis();
		
		currencyPage.enterTranSplitAnalysis(splitLineDetails);
		
		currencyPage.clickOnAcceptWarnings();
						
		currencyPage.clickOnReturnButton();
		
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
		String[] nodeID = { "AD10005" };
		String [] selectedNames = {"userName","passWord","currencyCode","invoiceDetails","transactionDetails","lineDetails","splitLineDetails"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
