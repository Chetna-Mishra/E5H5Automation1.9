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
 * Test Reference No	: 	A071 Document Prefix Codes
 * Purpose              :   Set Up Document Prefix Codes
 * Date					:   20-06-2014
 * ACCESS               :   PXJ
 */

public class A071_Document_PrefixCodesTest extends BaseTest{
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
		String code = "EDTPDPRFX ACT=INSERT,COMPANY="+companyId;
		
		List<String> documentO = dataRow.findNamesReturnValues("documentO");
		List<String> documentC = dataRow.findNamesReturnValues("documentC");
		List<String> documentR = dataRow.findNamesReturnValues("documentR");
		List<String> documentG = dataRow.findNamesReturnValues("documentG");
				
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
				
		createDocumentPrefix(currencyPage,code,currencyCode,documentO);
		createDocumentPrefix(currencyPage,code,currencyCode,documentC);
		createDocumentPrefix(currencyPage,code,currencyCode,documentR);
		createDocumentPrefix(currencyPage,code,currencyCode,documentG);
		
		if(currencyPage.isCommandDisplayed() == false){
			currencyPage.clickOnCancel();
		}
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Document Prefix List","Currency search page","displayed");

		currencyPage.searchValue(companyId,1,0);
		
		verifyDocumentPrefix(currencyPage,documentO);
		verifyDocumentPrefix(currencyPage,documentC);
		verifyDocumentPrefix(currencyPage,documentR);
		verifyDocumentPrefix(currencyPage,documentG);
	
		currencyPage.logOut(2);

	}

	private void createDocumentPrefix(CurrencyPage currencyPage,String code,List<String> currencyCode,List<String> document) throws InterruptedException{
		
		currencyPage.fillCurrenceyCode(code);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Document Prefix Codes Edit","Currency search page","displayed");
				
		/*Create document prefix code*/
		boolean update = currencyPage.enterDocumentPrefixCode(document);	
		
		if(update == true){
			currencyPage.clickOnUpdate();			
		}
		else{
			currencyPage.clickOnCancel();
			
			currencyPage.isConfirmPopUpDisplayed();
			testcases.add(getCurreentDate()+" | Pass : Document prefix code "+document.get(0)+ " present in the list");
		}
	}
	
	private void verifyDocumentPrefix(CurrencyPage currencyPage,List<String> document){
		
		if(currencyPage.verifyValues(document.get(0))){
			testcases.add(getCurreentDate()+" | Pass : Document prefix code "+document.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Document prefix code "+document.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A071" };
		String [] selectedNames = {"userName","passWord","currencyCode","documentO","documentC","documentR","documentG"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
