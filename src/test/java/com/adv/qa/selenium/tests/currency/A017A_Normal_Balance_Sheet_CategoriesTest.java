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


public class A017A_Normal_Balance_Sheet_CategoriesTest extends BaseTest{

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
		List<String> a09Category = dataRow.findNamesReturnValues("a09Category");
		List<String> g01Category = dataRow.findNamesReturnValues("g01Category");
		List<String> i01Category = dataRow.findNamesReturnValues("i01Category");
		List<String> i02Category = dataRow.findNamesReturnValues("i02Category");
		List<String> t04Category = dataRow.findNamesReturnValues("t04Category");
		List<String> t05Category = dataRow.findNamesReturnValues("t05Category");
		List<String> p03Category = dataRow.findNamesReturnValues("p03Category");
		List<String> p04Category = dataRow.findNamesReturnValues("p04Category");
				
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
		
		createBalanceCategory(currencyPage,a09Category);
		createBalanceCategory(currencyPage,g01Category);
		createBalanceCategory(currencyPage,i01Category);
		createBalanceCategory(currencyPage,i02Category);
		createBalanceCategory(currencyPage,t04Category);
		createBalanceCategory(currencyPage,t05Category);
		createBalanceCategory(currencyPage,p03Category);
		createBalanceCategory(currencyPage,p04Category);
		
		/*Exit from the Balance sheet page*/
		currencyPage.clickOnCancel();
	
		/*Verify new balance sheet category*/
		verifyValues(currencyPage,a09Category);
		verifyValues(currencyPage,g01Category);
		verifyValues(currencyPage,i01Category);
		verifyValues(currencyPage,i02Category);
		verifyValues(currencyPage,t04Category);
		verifyValues(currencyPage,t05Category);
		verifyValues(currencyPage,p03Category);
		verifyValues(currencyPage,p04Category);

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
		String xmlFilePath = folder  + "A017A.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
