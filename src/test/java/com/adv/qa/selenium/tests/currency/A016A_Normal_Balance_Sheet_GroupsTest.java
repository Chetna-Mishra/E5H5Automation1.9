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
 * Test Reference No 	:   A016 Balance Sheet Groups
 * Purpose              :   Set Up Normal Balance Sheet Groups
 * Date					:   21-04-2014
 * ACCESS               :   EAI
 */


public class A016A_Normal_Balance_Sheet_GroupsTest extends BaseTest{

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
		List<String> value = dataRow.findNamesReturnValues("value");
		List<String> gGroup = dataRow.findNamesReturnValues("gGroup");
		List<String> pGroup = dataRow.findNamesReturnValues("pGroup");
		List<String> iGroup = dataRow.findNamesReturnValues("iGroup");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Create new balance group*/		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Group List","Currency search page","displayed");

		currencyPage.searchValue(companyId,value,3,1);
		
		/*Insert new balance sheet details*/
		currencyPage.clickOnInsert();
		
		createNormalBalance(currencyPage,gGroup);
		createNormalBalance(currencyPage,pGroup);
		createNormalBalance(currencyPage,iGroup);
		
		/*Exit from the Balance sheet page*/
		currencyPage.clickOnCancel();

		/*Verify presence balance sheet*/
		verifyValues(currencyPage,gGroup);
		verifyValues(currencyPage,pGroup);
		verifyValues(currencyPage,iGroup);	

		/*Exit from the application*/
		currencyPage.logOut(2);

	}

	private void createNormalBalance(CurrencyPage currencyPage,List<String> balanceSheetGroup){
		/*Create new balance sheet group*/
		boolean update = currencyPage.enterBalanceSheetGroup(balanceSheetGroup);
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New balance group  "+balanceSheetGroup.get(0)+ " displayed in the list");
		}	
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> balanceSheetGroup){		
		/*Verify balance group*/
		if(currencyPage.verifyValues(balanceSheetGroup.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New balance group "+balanceSheetGroup.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New balance group "+balanceSheetGroup.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A016A" };
		String [] selectedNames = {"userName","passWord","code","value","gGroup","pGroup","iGroup"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}

}
