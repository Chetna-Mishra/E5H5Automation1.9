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
 * Test Reference No	: 	A075 Circulation Code
 * Purpose              :   Set Up Circulation Code.
 * Date					:   24-06-2014
 * ACCESS               :   PX8
 */

public class A075_Circulation_CodeTest extends BaseTest{
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
		List<String> companyControl = dataRow.findNamesReturnValues("companyControl");
		List<String> mappingsOrder = dataRow.findNamesReturnValues("mappingsOrder");
		List<String> mappingsReq = dataRow.findNamesReturnValues("mappingsReq");
		List<String> mappingAdj = dataRow.findNamesReturnValues("mappingAdj");
		List<String> mappingTrans = dataRow.findNamesReturnValues("mappingTrans");
		List<String> mappingDisp = dataRow.findNamesReturnValues("mappingDisp");
		List<String> mappingStock = dataRow.findNamesReturnValues("mappingStock");
		List<String> mappingMtIssue = dataRow.findNamesReturnValues("mappingMtIssue");
		List<String> mappingPickList = dataRow.findNamesReturnValues("mappingPickList");
		List<String> mappingStoreTran = dataRow.findNamesReturnValues("mappingStoreTran");		
		List<String> mappingRet = dataRow.findNamesReturnValues("mappingRet");		
					
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Company Controls List","Currency search page","displayed");
		
		createCompanyControl(currencyPage,companyControl);
		
		currencyPage.clickOnCancel();		
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Mapping List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 10,2);
		
		createMappings(currencyPage,mappingsOrder);
		createMappings(currencyPage,mappingsReq);
		createMappings(currencyPage,mappingAdj);
		createMappings(currencyPage,mappingTrans);
		createMappings(currencyPage,mappingDisp);
		createMappings(currencyPage,mappingStock);
		createMappings(currencyPage,mappingMtIssue);
		createMappings(currencyPage,mappingPickList);
		createMappings(currencyPage,mappingStoreTran);
		createMappings(currencyPage,mappingRet);
		
		currencyPage.logOut(2);
	}

	private void createCompanyControl(CurrencyPage currencyPage,List<String> companyControl) throws InterruptedException{
		currencyPage.searchValue(companyId, 1, 0);
		
		currencyPage.clickOnInsert();
		
		currencyPage.createCompanyControl(companyId,companyControl);

		currencyPage.clickOnUpdate();
		
		/*Verify new companyCode*/
		if(currencyPage.verifyValues(companyId)){
			testcases.add(getCurreentDate()+" | Pass : New company "+companyId+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New company "+companyId+ " not displayed in the list");
		}

	}
	
	private void createMappings(CurrencyPage currencyPage,List<String> mappings) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		
		currencyPage.clickOnInsert();
			
		/*Create batch type code*/
		currencyPage.createMappings(mappings);	
			
		currencyPage.clickOnUpdate();
		
		/*Verify new circulation code type in the list*/
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : New mappings "+mappings.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New mappings "+mappings.get(0)+ " not displayed in the list");
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
		String xmlFilePath = folder  + "A075.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
