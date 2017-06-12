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
 * Test Reference No	: 	AD02001 Normal Material Issue
 * Purpose              :   Create Stock Adjustment In 
 * ACCESS               :   HBA
 */

public class AD02001_Normal_Material_IssueTest extends BaseTest{
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
				
		createStockAdjustment(currencyPage,dataRow);
		
		verifyStockBalance(currencyPage,dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		createMaterialIssue(currencyPage,dataRow);
		
		reviewBatches(currencyPage, dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		verifyTotalStockBalance(currencyPage,dataRow);
				
		currencyPage.logOut(2);
	}
	
	private void createStockAdjustment(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "EDTHMVMT ACT=INSERT,CMPY="+companyId+",STORE=EAST,MVMT-IND=A";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> easrStoreElements = dataRow.findNamesReturnValues("easrStoreElements");
		String message = "Movement Reference";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Positive Adjustment Line Details Edit","Currency search page","displayed");
		
		currencyPage.addLineDetails(easrStoreElements,"A");
		
		currencyPage.clickOnAcceptWarnings();
		
		currencyPage.clickOnUpdate();
			
		String referenceMessage = currencyPage.getToolContentText();
		
		/*Verify new material issue*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
			testcases.add(getCurreentDate()+" | Pass : Material issue created");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Material issue not created");
		}

		currencyPage.isCommandDisplayed();
	}
	
	private void verifyStockBalance(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> eastStoreDetails = dataRow.findNamesReturnValues("eastStoreDetails");
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Item Store Stock Value","Currency search page","displayed");
		
		currencyPage.searchItemStore(companyId,eastStoreDetails);
		
		currencyPage.clickOnCurrentStock();
		
		boolean verify = currencyPage.verifyCurrenctStock(eastStoreDetails,1);
		
		Assert.assertTrue(testcases,verify,"Current stock values are as "," expected");
		
		currencyPage.clickOnTotalItems();
		
		boolean verifyTotalItem = currencyPage.verifyTotalItems(eastStoreDetails);
		
		Assert.assertTrue(testcases,verifyTotalItem,"Total items are "," as expected");
		
		currencyPage.clickOnCancel();
		
		currencyPage.clickOnCancel();
		
	}
	
	private void createMaterialIssue(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		String code = "EDTHMVMT ACT=INSERT,CMPY="+companyId+",STORE=EAST,MVMT-IND=M";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> eastMaterialIssue = dataRow.findNamesReturnValues("eastMaterialIssue");
		String message = "Movement Reference will be created";
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Material Issue Line Details Edit","Currency search page","displayed");
		
		currencyPage.insertMaterialIssue(eastMaterialIssue,"M");
		
		currencyPage.clickOnAcceptWarnings();
		
		currencyPage.clickOnUpdate();
		
		String referenceMessage = currencyPage.getToolContentText();
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Movement reference for material issue created "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Movement reference for material issue not created ");
		}
		
		currencyPage.isCommandDisplayed();
	}

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> batchDetails1 = dataRow.findNamesReturnValues("batchDetails1");
		List<String> batchDetails2 = dataRow.findNamesReturnValues("batchDetails2");
					
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search("IMMI", 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Journal Header"),"Journal header page","displayed");

		currencyPage.clickOnLines();
		
		boolean firstJournalDetails = currencyPage.verifyJournalDetails(1, batchDetails1);
		boolean secondournalDetails = currencyPage.verifyJournalDetails(2, batchDetails2);
		
		Assert.assertTrue(testcases,firstJournalDetails,"Batch values are "," as expected");
		Assert.assertTrue(testcases,secondournalDetails,"Batch values are "," as expected");
		
		currencyPage.clickOnCancel();				
	}
	
	private void verifyTotalStockBalance(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> eastStoreBalance = dataRow.findNamesReturnValues("eastStoreBalance");
		List<String> eastStoreValuation = dataRow.findNamesReturnValues("eastStoreValuation");
		List<String> eastStoreCurrentStock = dataRow.findNamesReturnValues("eastStoreCurrentStock");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Item Store Stock Value","Currency search page","displayed");
		
		currencyPage.searchItemStore(companyId,eastStoreBalance);
		
		boolean totalBalance = currencyPage.verifyTotalStockBalance(eastStoreBalance);

		if(totalBalance== true){
			testcases.add(getCurreentDate()+" | Pass : Total stock balance is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Total stock balance is not as expected ");
		}

		
		boolean storeItemValues = currencyPage.verifyStoreItemValues(eastStoreBalance,1);
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}
				
		currencyPage.clickOnValuation();
		
		currencyPage.sortValues();
		
		boolean verify = currencyPage.verifyStoreItemValuation(eastStoreValuation,1);
		
		Assert.assertTrue(testcases,verify,"Store item valuation is "," correct");

		currencyPage.clickOnReturnButton(5);
		
		currencyPage.clickOnCurrentStock();
		
		boolean currentStock = currencyPage.verifyCurrenctStock(eastStoreCurrentStock,0);
		if(currentStock== true){
			testcases.add(getCurreentDate()+" | Pass : Currenct stock is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Currenct stock is not as expected ");
		}		
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
		String[] nodeID = { "AD02001" };
		String [] selectedNames = {"userName","passWord","currencyCode","easrStoreElements","eastStoreDetails","eastMaterialIssue","batchDetails1","batchDetails2",
				"eastStoreBalance","eastStoreValuation","eastStoreCurrentStock"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
