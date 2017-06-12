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
 * Test Reference No	: 	AD03001 Store Transfer
 * Purpose              :   Check IM Stock Totals before Transfer 
 * ACCESS               :   HBA
 */

public class AD03001_Store_TransferTest extends BaseTest{
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
		List<String> storeElements = dataRow.findNamesReturnValues("storeElements");
		List<String> westStoreElements = dataRow.findNamesReturnValues("westStoreElements");
		List<String> eastStoreElements = dataRow.findNamesReturnValues("eastStoreElements");
		List<String> westStoreDetails = dataRow.findNamesReturnValues("westStoreDetails");
		List<String> eastStoreDetails = dataRow.findNamesReturnValues("eastStoreDetails");
		List<String> storeDetails = dataRow.findNamesReturnValues("storeDetails");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Item Store Stock Value","Currency search page","displayed");
		
		verifyStockBalance(currencyPage,dataRow,westStoreElements,0);
		verifyStockBalance(currencyPage,dataRow,eastStoreElements,0);
		
		currencyPage.clickOnCancel();
		
		createStoreTransfer(currencyPage,currencyCode.get(1),storeElements,storeDetails);
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Item Store Stock Value","Currency search page","displayed");

		verifyStockBalance(currencyPage,dataRow,westStoreDetails,1);
		verifyStockBalance(currencyPage,dataRow,eastStoreDetails,0);
		
		currencyPage.clickOnCancel();
		
		reviewBatches(currencyPage, dataRow);
		
		currencyPage.clickOnCancel();
		
		verifyRHEDReport(currencyPage,dataRow);
		
		currencyPage.logOut(2);
	}
	
	private void createStoreTransfer(CurrencyPageNew currencyPage,String currencyCode,List<String> storeElements,List<String> elements) throws InterruptedException{
		String code = "EDTHMVMT ACT=INSERT,CMPY="+companyId+",STORE=EAST,MVMT-IND=T";
		String message = "Movement Reference ";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Store Transfer Line Details Edit","Currency search page","displayed");
		
		currencyPage.addStoreTransferDetails(storeElements);
		
		currencyPage.addLineDetails(elements,"T");
		
		currencyPage.clickOnUpdate();
			
		String referenceMessage = currencyPage.getToolContentText();
		
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
			testcases.add(getCurreentDate()+" | Pass : Store transfer created");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Store transfer not created");
		}

		currencyPage.isCommandDisplayed();
	}
	
	private void verifyStockBalance(CurrencyPageNew currencyPage,DataRow dataRow,List<String> elements,int i) throws InterruptedException{
		
		currencyPage.searchItemStore(companyId,elements);
		
		boolean storeItemValues = currencyPage.verifyStoreItemValues(elements,i);
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}		
		if(i==1){
			List<String> westStoreCurrentStock = dataRow.findNamesReturnValues("westStoreCurrentStock");
			
			currencyPage.clickOnCurrentStock();
			
			boolean currentStock = currencyPage.verifyCurrenctStock(westStoreCurrentStock,0);
			if(currentStock== true){
				testcases.add(getCurreentDate()+" | Pass : Currenct stock is as expected ");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Currenct stock is not as expected ");
			}					
			currencyPage.clickOnCancel();
		}
	
	}
	

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> stkwBatchDetails = dataRow.findNamesReturnValues("stkwBatchDetails");
		List<String> stkeBatchDetails = dataRow.findNamesReturnValues("stkeBatchDetails");
		List<String> imdfBatchDetails = dataRow.findNamesReturnValues("imdfBatchDetails");
		List<String> imdfEBatchDetails = dataRow.findNamesReturnValues("imdfEBatchDetails");		
					
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search("IMTR", 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Journal Header"),"Journal header page","displayed");

		currencyPage.clickOnLines();
		
		boolean stkwDetails = currencyPage.verifyJournalDetails(1, stkwBatchDetails);
		boolean stkeDetails = currencyPage.verifyJournalDetails(2, stkeBatchDetails);
		boolean imdfDetails = currencyPage.verifyJournalDetails(3, imdfBatchDetails);
		boolean imdfEDetails = currencyPage.verifyJournalDetails(4, imdfEBatchDetails);
		
		Assert.assertTrue(testcases,stkwDetails,"IMTR batch details for STKW report is "," correct");
		Assert.assertTrue(testcases,stkeDetails,"IMTR batch details for STKW report is "," correct");
		Assert.assertTrue(testcases,imdfDetails,"IMTR batch details for STKW report is "," correct");
		Assert.assertTrue(testcases,imdfEDetails,"IMTR batch details for STKW report is "," correct");
		
		currencyPage.clickOnCancel();				
	}
	
	private void verifyRHEDReport(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> reportSearchValue = dataRow.findNamesReturnValues("reportSearchValue");
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(3));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(3)+" - Spool List","Currency search page","displayed");
		
		currencyPage.searchValue("17",reportSearchValue,10,1);
		
		currencyPage.clickOnViewDocument();		
		
		boolean verifyReport = currencyPage.verifyRHEDReport(reportSearchValue);
		Assert.assertTrue(testcases,verifyReport,"RHED05 report is "," correct");
		
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
		String[] nodeID = { "AD03001" };
		String [] selectedNames = {"userName","passWord","currencyCode","storeElements","westStoreElements","eastStoreElements",
		"westStoreCurrentStock","westStoreDetails","eastStoreDetails","storeDetails","stkwBatchDetails","stkeBatchDetails","imdfBatchDetails"
		,"imdfEBatchDetails","reportSearchValue"};;
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
