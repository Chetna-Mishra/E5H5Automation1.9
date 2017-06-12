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
 * Test Reference No	: 	A044 TAX Rates by Tax Codes
 * Purpose              :   Insert Tax Rates by Tax Codes
 * Date					:   12-06-2014
 * ACCESS               :   RAN,RAM
 */

public class A044_TAX_Rates_by_Tax_CodesTest extends BaseTest{
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
		
			
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Create tax rate*/
		insertTaxCode(currencyPage,eType,currencyCode.get(0));
		insertTaxCode(currencyPage,sType,currencyCode.get(0));
		insertTaxCode(currencyPage,vType,currencyCode.get(0));
		insertTaxCode(currencyPage,zType,currencyCode.get(0));
				
		currencyPage.logOut(1);
	}

	/*Create tax rate*/
	private void insertTaxCode(CurrencyPage currencyPage,List<String> taxList,String currencyCode) throws InterruptedException{
		String command = "EDTRRATE ACT=INSERT,CMPY="+companyId;
		String message = "The previously-requested action has been performed";
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(command);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Tax Rates Edit","Currency search page","displayed");

		/*Create Tax rate*/
		boolean update = currencyPage.createTaxRate(taxList);	
		
		if(update == false){
			currencyPage.clickOnCancel();
			currencyPage.isConfirmPopUpDisplayed();
		}

		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass : Tax rate "+taxList.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Tax rate "+taxList.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A044" };
		String [] selectedNames = {"userName","passWord","currencyCode","company","sType","zType","eType","vType"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
