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
 * Test Reference No	: 	AD05001 Return to Store
 * Purpose              :   Return stock to store using the Fetch facility 
 * ACCESS               :   HCK,HBA,EDA
 */

public class AD05001_Return_To_StoreTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");	
		List<String> stockBalance = dataRow.findNamesReturnValues("stockBalance");		
		List<String> materialIssue = dataRow.findNamesReturnValues("materialIssue");
		List<String> stockBalanceMI = dataRow.findNamesReturnValues("stockBalanceMI");
		List<String> currentStockBalance = dataRow.findNamesReturnValues("currentStockBalance");
		List<String> currencyStockMI = dataRow.findNamesReturnValues("currencyStockMI");
		List<String> stockBalanceRS = dataRow.findNamesReturnValues("stockBalanceRS");
		List<String> currentStockBalanceRS = dataRow.findNamesReturnValues("currentStockBalanceRS");	
		List<String> returnStoreStock = dataRow.findNamesReturnValues("returnStoreStock");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		verifyStockBalance(currencyPage,dataRow,stockBalance,currentStockBalance,1);
		
		createMaterialIssue(currencyPage,dataRow,materialIssue,0);			
				
		verifyStockBalance(currencyPage,dataRow,stockBalanceMI,currencyStockMI,1);
		
		reviewBatches(currencyPage, dataRow,1,"IMMI");currencyPage.clickOnCancel();
		
		createMaterialIssue(currencyPage,dataRow,returnStoreStock,1);			
		
		verifyStockBalance(currencyPage,dataRow,stockBalanceRS,currentStockBalanceRS,1);
		
		reviewBatches(currencyPage, dataRow,2,"IMRS");			
		
		currencyPage.logOut(2);
	}
	
	private void createMaterialIssue(CurrencyPageNew currencyPage,DataRow dataRow,List<String> elements,int i) throws InterruptedException{
		String code = "EDTHMVMT ACT=INSERT,CMPY="+companyId+",STORE=EAST,MVMT-IND=M";
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");	
		
		String message = "Movement Reference";
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Material Issue Line Details Edit","Currency search page","displayed");
		
		if(i==1){
			List<String> storeDetails = dataRow.findNamesReturnValues("storeDetails");
			
			currencyPage.addStoreDetails(storeDetails);
		}
		
		currencyPage.addLineDetails(elements,"M");
		
		currencyPage.clickOnUpdate();
		
		String referenceMessage = currencyPage.getToolContentText();
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Movement reference for material issue created "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Movement reference for material issue not created ");
		}
		
		referenceMessage = referenceMessage.substring(8).replaceAll("[^0-9]", "");
		
		testcases.add(getCurreentDate()+" | Pass : "+referenceMessage+"Movement reference for material issue not created ");

		currencyPage.isCommandDisplayed();
	}
	
	
	private void verifyStockBalance(CurrencyPageNew currencyPage,DataRow dataRow,List<String> elements,List<String> stockBalance,int i) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
			
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Item Store Stock Value","Currency search page","displayed");
		
		currencyPage.searchItemStore(companyId,elements);
		
		boolean totalStockBalance = currencyPage.verifyTotalStockBalance(elements);
		
		if(totalStockBalance== true){
			testcases.add(getCurreentDate()+" | Pass : Total stock balance is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Total stock balance is not as expected ");
		}

		
		boolean storeItemValues = currencyPage.verifyStoreItemValues(elements,0);
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}
		
		if(i==1){
			currencyPage.clickOnCurrentStock();
			
			boolean currentStock = currencyPage.verifyCurrenctStock(stockBalance,0);
			if(currentStock== true){
				testcases.add(getCurreentDate()+" | Pass : Currenct stock is as expected ");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Currenct stock is not as expected ");
			}
					
			currencyPage.clickOnCancel();
		}
		
		currencyPage.clickOnCancel();
		
	}
	

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow,int i,String batch) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> issuBatchDetails = dataRow.findNamesReturnValues("issuBatchDetails"+i);
		List<String> stkeBatchDetails = dataRow.findNamesReturnValues("stkeBatchDetails"+i);		
		
							
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search(batch, 16, 5);
		
		currencyPage.sortValues();
		
		currencyPage.clickOnView();
		
		Assert.assertTrue(testcases,currencyPage.getTableHeader().contains("Journal Header"),"Journal header page","displayed");

		currencyPage.clickOnLines();
		
		boolean verifyStkeBatch = currencyPage.verifyJournalDetails(1, stkeBatchDetails);
		Assert.assertTrue(testcases,verifyStkeBatch,"STKE batch values are "," correct");
		
		boolean verifyIssuBatch = currencyPage.verifyJournalDetails(2, issuBatchDetails);
		Assert.assertTrue(testcases,verifyIssuBatch,"ISSU batch values are "," correct");		
	
		if(i==2){
			List<String> rstrBatchDetails = dataRow.findNamesReturnValues("rstrBatchDetails"+i);
			currencyPage.verifyJournalDetails(3, rstrBatchDetails);
			
			boolean verifyRstrBatch = currencyPage.verifyJournalDetails(2, rstrBatchDetails);
			Assert.assertTrue(testcases,verifyRstrBatch,"RSTR batch values are "," correct");
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
		String[] nodeID = { "AD05001" };
		String [] selectedNames = {"userName","passWord","currencyCode","stockBalance","currentStockBalance","materialIssue","stockBalanceMI","currencyStockMI","stkeBatchDetails","issuBatchDetails"
				,"storeDetails","returnStoreStock","stockBalanceRS","currentStockBalanceRS","stkeBatchDetails1","rstrBatchDetails2","issuBatchDetails1","stkeBatchDetails2","issuBatchDetails2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
