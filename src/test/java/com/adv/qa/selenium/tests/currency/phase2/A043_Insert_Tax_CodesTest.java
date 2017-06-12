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
 * Test Reference No	: 	A043 Insert Tax Codes
 * Purpose              :   Insert Tax Types
 * Date					:   12-06-2014
 * ACCESS               :   RAI,RAJ,EDTRTYPE ACT=INSERT,TAX-TYPE=PE,PARAM-CMPY=04
 */

public class A043_Insert_Tax_CodesTest extends BaseTest{
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
		List<String> eType = dataRow.findNamesReturnValues("eType");
		List<String> sType = dataRow.findNamesReturnValues("sType");
		List<String> vType = dataRow.findNamesReturnValues("vType");
		List<String> zType = dataRow.findNamesReturnValues("zType");	
		List<String> uksTaxCode = dataRow.findNamesReturnValues("uksTaxCode");
		List<String> ukeTaxCode = dataRow.findNamesReturnValues("ukeTaxCode");
		List<String> ukzTaxCode = dataRow.findNamesReturnValues("ukzTaxCode");
		List<String> ukvTaxCode = dataRow.findNamesReturnValues("ukvTaxCode");
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		insertTaxCode(currencyPage,eType,currencyCode.get(0));
		insertTaxCode(currencyPage,sType,currencyCode.get(0));
		insertTaxCode(currencyPage,vType,currencyCode.get(0));
		insertTaxCode(currencyPage,zType,currencyCode.get(0));
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Tax Type List","Currency search page","displayed");

		currencyPage.searchValue(companyId,3,1);
		
		verifyValues(currencyPage,eType);
		verifyValues(currencyPage,sType);
		verifyValues(currencyPage,vType);
		verifyValues(currencyPage,zType);
		
		currencyPage.clickOnCancel();
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Tax Code List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 4, 0);
		
		currencyPage.clickOnInsert();
		
		/*Create tax locations*/
		createLocations(currencyPage,uksTaxCode,currencyCode.get(2));
		createLocations(currencyPage,ukeTaxCode,currencyCode.get(2));
		createLocations(currencyPage,ukvTaxCode,currencyCode.get(2));
		createLocations(currencyPage,ukzTaxCode,currencyCode.get(2));
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,uksTaxCode);
		verifyValues(currencyPage,ukeTaxCode);
		verifyValues(currencyPage,ukvTaxCode);
		verifyValues(currencyPage,ukzTaxCode);

		currencyPage.logOut(2);

	}

	/*Create tax code*/
	private void insertTaxCode(CurrencyPage currencyPage,List<String> taxList,String currencyCode) throws InterruptedException{
		String command = "EDTRTYPE ACT=INSERT,PARAM-CMPY="+companyId;
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(command);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Tax Type Edit","Currency search page","displayed");
			
		/*Create Tax type*/
		boolean update = currencyPage.createTaxType(taxList);	
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New tax code "+taxList.get(0) +"present in the list");
			currencyPage.clickOnCancel();
		}
	}
	
	/*Create tax locations*/
	private void createLocations(CurrencyPage currencyPage,List<String> taxList,String currencyCode) throws InterruptedException{
		
		/*Create Tax type*/
		boolean update = currencyPage.createTaxCodeLocation(taxList);	
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New tax code "+taxList.get(0)+ " displayed in the list");
		}	
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> taxCode){
		
		if(currencyPage.verifyValues(taxCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New tax code "+taxCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New tax code "+taxCode.get(0)+ " not displayed in the list");
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
		String xmlFilePath = folder  + "A043.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
