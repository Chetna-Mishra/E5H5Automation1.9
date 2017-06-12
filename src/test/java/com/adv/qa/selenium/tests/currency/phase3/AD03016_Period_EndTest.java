package com.adv.qa.selenium.tests.currency.phase3;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPageNew;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	AD03016 Period End 
 * Purpose              :   Close Current Period. 
 * ACCESS               :   EYB
 */

public class AD03016_Period_EndTest extends BaseTest{
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
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
			
		List<String> periodAndYear = getPerodAndYear(currencyPage,currencyCode.get(0));
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		getPMCompanyControls(currencyPage,dataRow,periodAndYear,1);
		getPMCompanyControls(currencyPage,dataRow,periodAndYear,2);
		getPMCompanyControls(currencyPage,dataRow,periodAndYear,3);
						
		currencyPage.logOut(2);
	}
	
	private List<String> getPerodAndYear(CurrencyPageNew currencyPage,String currencyCode) throws InterruptedException{
		currencyPage.fillCurrenceyCode(currencyCode);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Period and Year End List","Currency search page","displayed");
		
		currencyPage.search(companyId,5,3);
		
		return currencyPage.getPeriodAndYear();
				
	}
	
	private void getPMCompanyControls(CurrencyPageNew currencyPage,DataRow dataRow,List<String> periodAndYear,int companyControl) throws InterruptedException{
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		
		switch(companyControl){
		
		case 1:
			/*Verify PM company controls*/
			currencyPage.fillCurrenceyCode("EDTDCCTL ACT=AMEND,CMPY="+companyId);
			
			Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - POP Company Controls Edit","Currency search page","displayed");
				
			List<String> companyControls = currencyPage.getCompanyControl(15, 16,1);
			
			Assert.assertTrue(testcases,companyControls.get(0).equals(periodAndYear.get(0)),"Period and year of PM company control is "," as expected");
			Assert.assertTrue(testcases,companyControls.get(1).equals(periodAndYear.get(1)),"Period and year of PM company control is "," as expected");

			currencyPage.clickOnCancel();

			break;
		 
		case 2:
			/*Verify IM company controls*/
			currencyPage.fillCurrenceyCode("EDTHCCTL ACT=AMEND,CMPY="+companyId);
			
			Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - IM Company Controls Edit","Currency search page","displayed");
		
			List<String> companyIMControls = currencyPage.getCompanyControl(4, 5, 1);
			
			Assert.assertTrue(testcases,companyIMControls.get(0).equals(periodAndYear.get(0)),"Period and year of IM company control is "," as expected");
			Assert.assertTrue(testcases,companyIMControls.get(1).equals(periodAndYear.get(1)),"Period and year of IM company control is "," as expected");

			currencyPage.clickOnCancel();
			
			currencyPage.isCommandDisplayed();

			break;
		 
		case 3:
			/*Verify AP company controls*/
			currencyPage.fillCurrenceyCode(currencyCode.get(3));
			
			Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(3)+" - AP Company Controls List","Currency search page","displayed");
			
			currencyPage.search(companyId,1,0);

			currencyPage.clickOnAmend();
			
			List<String> companyAPControls = currencyPage.getCompanyControl(75, 76,8);
			
			Assert.assertTrue(testcases,companyAPControls.get(0).equals(periodAndYear.get(0)),"Period and year of AP company control is "," as expected");
			Assert.assertTrue(testcases,companyAPControls.get(1).equals(periodAndYear.get(1)),"Period and year of AP company control is "," as expected");

			currencyPage.clickOnCancel();
			
			break;
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
		String xmlFilePath = folder  + "phase3.xml";
		String[] nodeID = { "AD03016" };
		String [] selectedNames = {"userName","passWord","currencyCode"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
