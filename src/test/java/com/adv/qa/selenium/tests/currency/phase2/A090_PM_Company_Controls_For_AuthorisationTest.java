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
 * Test Reference No	: 	A090 PM Company Controls for Authorisation
 * Purpose              :   Amend Purchasing Management Company Controls for Authorisation and Update Doc Codes.
 * Date					:   24-06-2014
 * ACCESS               :   DAG,DAI
 */

public class A090_PM_Company_Controls_For_AuthorisationTest extends BaseTest{
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
		List<String> pmCompanyControl = dataRow.findNamesReturnValues("pmCompanyControl");
		List<String> document03 = dataRow.findNamesReturnValues("document03");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - PM Company Controls - List","Currency search page","displayed");

		currencyPage.search(companyId,3,2);
		
		currencyPage.clickOnAmend();
		
		/*Create batch type code*/
		currencyPage.editPMCompanyControl(pmCompanyControl);	
		
		 if(currencyPage.isToolTipDisplayed()){
			   currencyPage.clickOnUpdtWarnings();
		 }
		 else{
		   currencyPage.clickOnUpdate();
		 }
	
		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(companyId), "Purchasing company  "+companyId," updated");
		
		currencyPage.clickOnCancel();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Documents Codes List","Currency search page","displayed");

		amendDocumentCode(currencyPage,document03);

		currencyPage.logOut(2);

	}
	
	private void amendDocumentCode(CurrencyPage currencyPage,List<String> document){
		currencyPage.searchValue(companyId,document, 2, 1);
		
		/*Verify new batch type in the list*/
		Assert.assertTrue(testcases,currencyPage.verifyValues(document.get(0)), "Document code  "+document.get(0)," displayed");
		
		currencyPage.clickOnAmend();

		currencyPage.editDocumentCodes(document.get(2));
		
		currencyPage.clickOnUpdate();
		
		/*Verify new batch type in the list*/
		if(currencyPage.verifyValues(document.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Document code "+document.get(0)+ " updated successfully");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Document code "+document.get(0)+ " not updated");
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
		String[] nodeID = { "A090" };
		String [] selectedNames = {"userName","passWord","currencyCode","pmCompanyControl","document03"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
