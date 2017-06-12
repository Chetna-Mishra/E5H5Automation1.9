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
 * Test Reference No	: 	A056A  System Assigned Batch Numbers in AP
 * Purpose              :   Set Up Additional Batch Types
 * Date					:   26-06-2014
 * ACCESS               :   AKK
 */

public class A056A_System_Assigned_Batch_Numbers_In_AP extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("currencyCode");
		List<String> apBatchTypePDE1 = dataRow.findNamesReturnValues("apBatchTypePDE1");
		List<String> apBatchTypePDE2 = dataRow.findNamesReturnValues("apBatchTypePDE2");
		List<String> apBatchTypePDE3 = dataRow.findNamesReturnValues("apBatchTypePDE3");
		List<String> apBatchTypePDE4 = dataRow.findNamesReturnValues("apBatchTypePDE4");
		List<String> apBatchTypePDE5 = dataRow.findNamesReturnValues("apBatchTypePDE5");
		List<String> apBatchTypePDE6 = dataRow.findNamesReturnValues("apBatchTypePDE6");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Batch Type List","Currency search page","displayed");

		amendAPBatchTypes(currencyPage,apBatchTypePDE1);
		amendAPBatchTypes(currencyPage,apBatchTypePDE2);
		amendAPBatchTypes(currencyPage,apBatchTypePDE3);
		amendAPBatchTypes(currencyPage,apBatchTypePDE4);
		amendAPBatchTypes(currencyPage,apBatchTypePDE5);
		amendAPBatchTypes(currencyPage,apBatchTypePDE6);

		currencyPage.logOut(2);

	}
	
	private void amendAPBatchTypes(CurrencyPage currencyPage,List<String> batchType){
		
		currencyPage.searchValue(companyId,batchType,2,1);
		
		currencyPage.clickOnAmend();
		
		/*Create batch type code*/
		currencyPage.amendBatchTypes(batchType);	
		
		currencyPage.clickOnUpdate();
	
		/*Verify new batch type in the list*/
		if(currencyPage.verifyValues(batchType.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Batch number in AP are "+batchType.get(0)+ " updated");
		}
		else{
			currencyPage.clickOnCancel();
			
			testcases.add(getCurreentDate()+" | Fail : Batch number in AP are "+batchType.get(0)+ " not updated");
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
		String xmlFilePath = folder  + "E5H5.xml";
		String[] nodeID = { "A056A" };
		String [] selectedNames = {"userName","passWord","currencyCode","apBatchTypePDE1","apBatchTypePDE2","apBatchTypePDE3","apBatchTypePDE4",
				"apBatchTypePDE5","apBatchTypePDE6"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
