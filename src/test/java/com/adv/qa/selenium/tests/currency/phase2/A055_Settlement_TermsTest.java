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
 * Test Reference No	: 	A055 Settlement Terms
 * Purpose              :   Insert Settlement Terms.
 * Date					:   19-06-2014
 * ACCESS               :   PAH,PAG
 */

public class A055_Settlement_TermsTest extends BaseTest{
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
		String code = "EDTPSETTL ACT=INSERT,COMPANY="+companyId;
		List<String> settlementS1 = dataRow.findNamesReturnValues("settlementS1");
		List<String> settlementS2 = dataRow.findNamesReturnValues("settlementS2");
				
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
				
		createBankCode(currencyPage,code,currencyCode,settlementS1);
		createBankCode(currencyPage,code,currencyCode,settlementS2);
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Settlement Terms List","Currency search page","displayed");

		currencyPage.searchValue(companyId,2,0);
		
		verifyBankCode(currencyPage,settlementS1);
		verifyBankCode(currencyPage,settlementS2);
	
		currencyPage.logOut(2);

	}
	
	private void createBankCode(CurrencyPage currencyPage,String code,List<String> currencyCode,List<String> settlement) throws InterruptedException{
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");

		currencyPage.fillCurrenceyCode(code);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Settlement Terms Edit","Currency search page","displayed");
		
		/*Create batch type code*/
		boolean update = currencyPage.enterSettlementTerms(settlement);	
		
		if(update == true){
		
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New settlement term "+settlement.get(0)+ " present in the list");
			currencyPage.clickOnCancel();
		}
	}
	
	private void verifyBankCode(CurrencyPage currencyPage,List<String> settlement){		
		if(currencyPage.verifyValues(settlement.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New settlement term "+settlement.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New settlement term "+settlement.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A055" };
		String [] selectedNames = {"userName","passWord","code","currencyCode","company","settlementS1","settlementS2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
