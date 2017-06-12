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
 * Test Reference No	: 	AD02009 Goods Receiving
 * Purpose              :   Create Stock Adjustment In 
 * ACCESS               :   DOA,PLE,EDA,HCJ
 */

public class AD02009_Goods_ReceivingTest extends BaseTest{
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
		List<String> authorisor1 = dataRow.findNamesReturnValues("authorisor1");
		List<String> authorisor2 = dataRow.findNamesReturnValues("authorisor2");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
				
		String orderNumber = createOrder(currencyPage,dataRow);
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(5));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(5)+" - Unauthorised Documents List","Currency search page","displayed");
		
		authoriseOrder(currencyPage,authorisor1,orderNumber);
		authoriseOrder(currencyPage,authorisor2,orderNumber);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		createReceiveGoods(currencyPage,dataRow,orderNumber);
		
		currencyPage.isCommandDisplayed();
		
		reviewBatches(currencyPage, dataRow);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		verifyTotalStockBalance(currencyPage,dataRow);
				
		currencyPage.logOut(2);
	}
	
	private String createOrder(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> orderCode = dataRow.findNamesReturnValues("orderCode");
		String message = "Order Reference";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
				
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Order - List","Currency search page","displayed");

		currencyPage.searchValue(companyId, orderCode, 19, 8);
		
		currencyPage.clickOnInsert();
		
		currencyPage.createOrderCode(orderCode);
		
		currencyPage.clickOnUpdate();
			
		String referenceMessage = currencyPage.getToolContentText();
		
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
			testcases.add(getCurreentDate()+" | Pass : Order  "+orderCode.get(0)+ " created");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Order "+orderCode.get(0)+ " not created");
		}

		referenceMessage = referenceMessage.substring(8).replaceAll("[^0-9]", "");
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.clickOnCancel();
		
		return referenceMessage;
	}
	
	private void authoriseOrder(CurrencyPageNew currencyPage,List<String> authorisorList,String orderNumber) throws InterruptedException{
		
		currencyPage.searchAuthorisor(companyId, authorisorList,orderNumber, 2);
		
		currencyPage.selectOrderAuthorisor();

	}
	
	
	private void createReceiveGoods(CurrencyPageNew currencyPage,DataRow dataRow,String orderNumber) throws InterruptedException{
		String code = "EDTPGRNN ACT=INSERT,CMPY="+companyId+",ORDER="+orderNumber;
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> receiveGoods = dataRow.findNamesReturnValues("receiveGoods");
		String message = "GRNs will be created in Background";
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Goods Receive/Return Edit","Currency search page","displayed");
		
		currencyPage.addGoodsReceive(receiveGoods);
		
		currencyPage.clickOnUpdate();
		
		String referenceMessage = currencyPage.getToolContentText();
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Receive goods created "+referenceMessage);
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Receive goods created ");
		}
		
		currencyPage.enterGoodsDetailsInPopUp(orderNumber);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		currencyPage.isCommandDisplayed();
	}

	private void reviewBatches(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> batchDetails1 = dataRow.findNamesReturnValues("batchDetails1");
		List<String> batchDetails2 = dataRow.findNamesReturnValues("batchDetails2");
					
		currencyPage.fillCurrenceyCode(currencyCode.get(3));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(3)+" - Journal List","Journey search page","displayed");
		
		currencyPage.search(companyId, 4, 0);
		
		currencyPage.clickOnSections(1);
		
		currencyPage.search("ACC", 16, 5);
		
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
		List<String> stockBalance = dataRow.findNamesReturnValues("stockBalance");
		List<String> stockBalanceValuation = dataRow.findNamesReturnValues("stockBalanceValuation");
		List<String> stockBalanceCurrencyStock = dataRow.findNamesReturnValues("stockBalanceCurrencyStock");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(4));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(4)+" - Item Store Stock Value","Currency search page","displayed");
		
		currencyPage.searchItemStore(companyId,stockBalance);
		
		
		boolean totalStockBalance = currencyPage.verifyTotalStockBalance(stockBalance);
		if(totalStockBalance== true){
			testcases.add(getCurreentDate()+" | Pass : Total stock balance is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Total stock balance is not as expected ");
		}
		
		boolean storeItemValues = currencyPage.verifyStoreItemValues(stockBalance,1);				
		if(storeItemValues== true){
			testcases.add(getCurreentDate()+" | Pass : Store item values is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item values is not as expected ");
		}		
		currencyPage.clickOnValuation();
		
		boolean valuation = currencyPage.verifyStoreItemValuation(stockBalanceValuation,0);
		if(valuation== true){
			testcases.add(getCurreentDate()+" | Pass : Store item valuation is as expected ");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Store item valuation is not as expected ");
		}		
		currencyPage.clickOnReturnButton(5);
		
		currencyPage.clickOnCurrentStock();
		
		boolean currentStock = currencyPage.verifyCurrenctStock(stockBalanceCurrencyStock,0);
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
		String[] nodeID = { "AD02009" };
		String [] selectedNames = {"userName","passWord","currencyCode","orderCode","receiveGoods","batchDetails1","batchDetails2","stockBalance",
				"stockBalanceValuation","stockBalanceCurrencyStock"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
