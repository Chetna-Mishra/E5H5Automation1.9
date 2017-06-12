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
 * Test Reference No	: 	A063 Standard Text	
 * Purpose              :   Insert Standard Text
 * Date					:   19-06-2014
 * ACCESS               :   PXU
 */

public class A063_Standard_TextTest extends BaseTest{
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

		List<String> standardAccept = dataRow.findNamesReturnValues("standardAccept");
		List<String> standardReject = dataRow.findNamesReturnValues("standardReject");
		List<String> standardRequest = dataRow.findNamesReturnValues("standardRequest");
		List<String> acceptLines = dataRow.findNamesReturnValues("acceptLines");
		List<String> rejectLines = dataRow.findNamesReturnValues("rejectLines");
		List<String> requestLines = dataRow.findNamesReturnValues("requestLines");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Standard Text Reference List","Currency search page","displayed");
			
		currencyPage.searchValue(companyId,2,0);
	
		currencyPage.clickOnInsert();
		
		createPurchasingManagement(currencyPage,standardAccept,acceptLines);
		createPurchasingManagement(currencyPage,standardReject,rejectLines);
		createPurchasingManagement(currencyPage,standardRequest,requestLines);
		
		/*Exit from the batch details page*/
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,standardAccept,acceptLines);
		verifyValues(currencyPage,standardReject,rejectLines);
		verifyValues(currencyPage,standardRequest,requestLines);
			
		currencyPage.logOut(2);

	}
	
	private void createPurchasingManagement(CurrencyPage currencyPage,List<String> standard,List<String> lines) throws InterruptedException{
		/*Create standard text*/
		boolean update = currencyPage.enterStandardTextReferenceDetails(standard,lines);	
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New Standard text "+standard.get(0)+ " created");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> standard,List<String> lines){
		/*Verify new standard text in the list*/
		if(currencyPage.verifyValues(standard.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New Standard text "+standard.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New Standard text "+standard.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A063" };
		String [] selectedNames = {"userName","passWord","currencyCode","standardAccept","standardReject","standardRequest","acceptLines","rejectLines","requestLines"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
