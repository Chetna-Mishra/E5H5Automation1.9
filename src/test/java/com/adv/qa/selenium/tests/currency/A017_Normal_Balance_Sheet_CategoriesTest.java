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
import com.adv.qa.selenium.helpers.WaitHelper;


/**
 * @author              :   Draxayani
 * Test Reference No	:   A017 Balance Sheet Categories
 * Purpose              :   Set Up Normal Balance Sheet Categories
 * Date					:   21-04-2014
 * ACCESS               :   EAK
 */

public class A017_Normal_Balance_Sheet_CategoriesTest extends BaseTest{

	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{	
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String balanceCategoryCode = dataRow.get("balanceCategoryCode");
		List<String> values = dataRow.findNamesReturnValues("values");
		List<String> a02Category = dataRow.findNamesReturnValues("a02Category");
		List<String> b08Category = dataRow.findNamesReturnValues("b08Category");
		List<String> b01Category = dataRow.findNamesReturnValues("b01Category");
		List<String> b02Category = dataRow.findNamesReturnValues("b02Category");
		List<String> b03Category = dataRow.findNamesReturnValues("b03Category");
		List<String> b04Category = dataRow.findNamesReturnValues("b04Category");
		List<String> c01Category = dataRow.findNamesReturnValues("c01Category");
		List<String> c02Category = dataRow.findNamesReturnValues("c02Category");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Create new balance category*/
		currencyPage.fillCurrenceyCode(balanceCategoryCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+balanceCategoryCode+" - Category List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,values,4,2);
		
		/*Insert new balance sheet details*/
		currencyPage.clickOnInsert();
		
		createBalanceCategory(currencyPage,a02Category);
		createBalanceCategory(currencyPage,b08Category);
		createBalanceCategory(currencyPage,b01Category);
		createBalanceCategory(currencyPage,b02Category);
		createBalanceCategory(currencyPage,b03Category);
		createBalanceCategory(currencyPage,b04Category);
		createBalanceCategory(currencyPage,c01Category);
		createBalanceCategory(currencyPage,c02Category);

		/*Exit from the Balance sheet page*/
		currencyPage.clickOnCancel();
		
		/*Verify new balance sheet category*/
		verifyValues(currencyPage,a02Category);
		verifyValues(currencyPage,b08Category);
		verifyValues(currencyPage,b01Category);
		verifyValues(currencyPage,b02Category);
		verifyValues(currencyPage,b03Category);
		verifyValues(currencyPage,b04Category);
		verifyValues(currencyPage,c01Category);
		verifyValues(currencyPage,c02Category);
		
		currencyPage.logOut(2);
	}

	
	private void createBalanceCategory(CurrencyPage currencyPage,List<String> balanceSheetCategory) throws InterruptedException{
		/*Create mew balance sheet category*/
		boolean update = currencyPage.enterBalanceSheetCategory(balanceSheetCategory);
		
		if(update == true){
			currencyPage.clickOnUpdate();			
		}
		
		else {
			testcases.add(getCurreentDate()+" | Pass : New balance sheet category  "+balanceSheetCategory.get(2)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> balanceSheetCategory){
		
		currencyPage.searchElement(balanceSheetCategory,4);
		
		if(currencyPage.verifyValues(balanceSheetCategory.get(2))){
			testcases.add(getCurreentDate()+" | Pass : New balance sheet category "+balanceSheetCategory.get(2)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New balance sheet category "+balanceSheetCategory.get(2)+ " not displayed in the list");
		}
		WaitHelper.waitAdditional(3);
	}

	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A017.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
