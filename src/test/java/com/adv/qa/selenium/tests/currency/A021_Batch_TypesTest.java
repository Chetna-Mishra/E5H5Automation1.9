package com.adv.qa.selenium.tests.currency;

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
 * Test Reference No	: 	A021 Batch Types
 * Purpose              :   Set Up Initial Batch Types
 * Date					:   22-04-2014
 * ACCESS               :   AKK
 */

public class A021_Batch_TypesTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("code");
		List<String> batchTypeRval = dataRow.findNamesReturnValues("batchTypeRval");
		List<String> batchTypeSumm = dataRow.findNamesReturnValues("batchTypeSumm");
		List<String> batchTypeTran = dataRow.findNamesReturnValues("batchTypeTran");
		
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
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();

		createBatchType(currencyPage,batchTypeRval);
		createBatchType(currencyPage,batchTypeSumm);
		createBatchType(currencyPage,batchTypeTran);
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();

		/*Verify new batch type*/
		verifyValues(currencyPage,batchTypeRval);
		verifyValues(currencyPage,batchTypeSumm);
		verifyValues(currencyPage,batchTypeTran);
		
		currencyPage.logOut(2);
	}
	
	private void createBatchType(CurrencyPage currencyPage,List<String> batchList) throws InterruptedException{

		/*Create batch type code*/
		boolean update = currencyPage.enterBatchTypeDetails(batchList);	

		if(update == true){
			currencyPage.clickOnUpdate();			
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New batch type  "+batchList.get(1)+ " displayed in the list");
		}
	}

	private void verifyValues(CurrencyPage currencyPage,List<String> batchList){
		
		/*Verify new batch type in the list*/		
		if(currencyPage.verifyValues(batchList.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New batch type "+batchList.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New batch type "+batchList.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A021" };
		String [] selectedNames = {"userName","passWord","code","company","batchTypeRval","batchTypeSumm","batchTypeTran"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
