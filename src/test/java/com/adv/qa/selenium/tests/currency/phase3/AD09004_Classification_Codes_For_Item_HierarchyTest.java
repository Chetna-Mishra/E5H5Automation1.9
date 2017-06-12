package com.adv.qa.selenium.tests.currency.phase3;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.adv.qa.selenium.framework.Assert;
import com.adv.qa.selenium.framework.BaseTest;
import com.adv.qa.selenium.framework.pageObjects.LoginPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPage;
import com.adv.qa.selenium.framework.pageObjects.currency.CurrencyPageNew;
import com.adv.qa.selenium.helpers.DataResource;
import com.adv.qa.selenium.helpers.DataRow;

/**
 * @author              :   Draxayani
 * Test Reference No	: 	AD09004 Classification Codes for Item Hierarchy 
 * Purpose              :   Create Classification Codes for Item Hierarchy 
 * ACCESS               :   AT2
 */

public class AD09004_Classification_Codes_For_Item_HierarchyTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String currencyCode = dataRow.get("currencyCode");
		List<String> classificationCodeBud = dataRow.findNamesReturnValues("classificationCodeBud");
		List<String> classificationCodeHigh = dataRow.findNamesReturnValues("classificationCodeHigh");
		List<String> classificationCodeMid = dataRow.findNamesReturnValues("classificationCodeMid");

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Classification Codes - List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,2,0);
		
		currencyPage.clickOnInsert();
		
		createSecurityGroups(currencyPage,classificationCodeBud);
		createSecurityGroups(currencyPage,classificationCodeHigh);
		createSecurityGroups(currencyPage,classificationCodeMid);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,classificationCodeBud);
		verifyValues(currencyPage,classificationCodeHigh);
		verifyValues(currencyPage,classificationCodeMid);
	
		currencyPage.logOut(2);
	}
	
	
	private void createSecurityGroups(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{		
		
		boolean update = currencyPage.createClassificationCode(elements);
		
		if(update == true){
			currencyPage.clickOnUpdate();		
		}	
		else{
			testcases.add(getCurreentDate()+" | Pass : New classification code "+elements.get(0)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> classification){
		
		if(currencyPage.verifyValues(classification.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New classification code "+classification.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New classification code "+classification.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "AD09004" };
		String [] selectedNames = {"userName","passWord","currencyCode","classificationCodeBud","classificationCodeHigh","classificationCodeMid"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
