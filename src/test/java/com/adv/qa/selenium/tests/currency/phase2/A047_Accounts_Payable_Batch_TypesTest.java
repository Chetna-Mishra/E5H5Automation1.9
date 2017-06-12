package com.adv.qa.selenium.tests.currency.phase2;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPage;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	A047 Accounts Payable Batch Types
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   16-05-2014
 * ACCESS               :   AKL
 */

public class A047_Accounts_Payable_Batch_TypesTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");

		List<String> batchTypePDE1 = dataRow.findNamesReturnValues("batchTypePDE1");
		List<String> batchTypePDE2 = dataRow.findNamesReturnValues("batchTypePDE2");
		List<String> batchTypePDE3 = dataRow.findNamesReturnValues("batchTypePDE3");
		List<String> batchTypePDE4 = dataRow.findNamesReturnValues("batchTypePDE4");
		List<String> batchTypePDE5 = dataRow.findNamesReturnValues("batchTypePDE5");
		List<String> batchTypePDE6 = dataRow.findNamesReturnValues("batchTypePDE6");
		List<String> batchTypePDE7 = dataRow.findNamesReturnValues("batchTypePDE7");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
	
		createBatchType(currencyPage,dataRow,batchTypePDE1);
		createBatchType(currencyPage,dataRow,batchTypePDE2);
		createBatchType(currencyPage,dataRow,batchTypePDE3);
		createBatchType(currencyPage,dataRow,batchTypePDE4);
		createBatchType(currencyPage,dataRow,batchTypePDE5);
		createBatchType(currencyPage,dataRow,batchTypePDE6);
		createBatchType(currencyPage,dataRow,batchTypePDE7);
					
		currencyPage.logOut(1);
	}
	
	private void createBatchType(CurrencyPage currencyPage, DataRow dataRow,List<String> batchList) throws InterruptedException{
		String code = "EDTBTCH ACT=INSERT,COMPANY="+companyId;
		List<String> currencyCodeForBatchType = dataRow.findNamesReturnValues("currencyCode");
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(code);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCodeForBatchType.get(0)+" - Batch Type Edit","Currency search page","displayed");
		
		/*Create batch type code*/
		boolean update = currencyPage.enterLedgerBatchTypeDetails(batchList);	
		
		if(update == true){
		
			currencyPage.clickOnUpdate();
			
			String message = "The previously-requested action has been performed";
			
			/*Verify new batch type in the list*/
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : New Batch type of AP "+batchList.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New Batch type of AP "+batchList.get(0)+ " not displayed in the list");
			}
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New Batch type of AP "+batchList.get(0)+ " displayed in the list");
			
			currencyPage.clickOnCancel();
			
			currencyPage.isConfirmPopUpDisplayed();
		}

		currencyPage.isCommandDisplayed();
	}
	
//	private void verifyAPBatchType(CurrencyPage currencyPage,List<String> batchType){
//				
//		/*Verify new batch type in the list*/
//		if(currencyPage.verifyValues(batchType.get(0))){
//			testcases.add(getCurreentDate()+" | Pass : New Batch type of AP "+batchType.get(0)+ " displayed in the list");
//		}
//		else{
//			testcases.add(getCurreentDate()+" | Fail : New Batch type of AP "+batchType.get(0)+ " not displayed in the list");
//		}
//	}

	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{		
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A047" };
		String [] selectedNames = {"userName","passWord","code","currencyCode","company","batchTypePDE1","batchTypePDE2","batchTypePDE3",
				"batchTypePDE4","batchTypePDE5","batchTypePDE6","batchTypePDE7"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
