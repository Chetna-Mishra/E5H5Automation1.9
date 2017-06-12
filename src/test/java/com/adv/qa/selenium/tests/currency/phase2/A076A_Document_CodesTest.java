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
 * Test Reference No	: 	A076  Document Codes
 * Purpose              :   Setup Document Code.
 * Date					:   16-05-2014
 * ACCESS               :   DAJ and DAI
 */

public class A076A_Document_CodesTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> documentCodeG1 = dataRow.findNamesReturnValues("documentCodeG1");
		List<String> documentCodePM = dataRow.findNamesReturnValues("documentCodePM");
		List<String> documentCodePR = dataRow.findNamesReturnValues("documentCodePR");
		List<String> documentCodeRE = dataRow.findNamesReturnValues("documentCodeRE");
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		createBatchType(currencyPage,dataRow,documentCodeG1);
		createBatchType(currencyPage,dataRow,documentCodePM);
		createBatchType(currencyPage,dataRow,documentCodePR);
		createBatchType(currencyPage,dataRow,documentCodeRE);
			
		currencyPage.logOut(1);

	}
	
	private void createBatchType(CurrencyPage currencyPage, DataRow dataRow,List<String> documentList) throws InterruptedException{
		String code = "EDTDADOC ACT=INSERT,CMPY="+companyId;
		List<String> currencyCodeForBatchType = dataRow.findNamesReturnValues("currencyCode");
		String message = "The previously-requested action has been performed";
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(code);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCodeForBatchType.get(0)+" - Document Codes Edit","Currency search page","displayed");
		
		/*Create batch type code*/
		currencyPage.enterDocumentCodeDetails(documentList);	
		
		currencyPage.clickOnUpdate();
		
		/*Verify new batch type in the list*/
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : New document code "+documentList.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New document code "+documentList.get(0)+ " not displayed in the list");
		}
		
		currencyPage.isCommandDisplayed();
	}
	
	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A076A.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
