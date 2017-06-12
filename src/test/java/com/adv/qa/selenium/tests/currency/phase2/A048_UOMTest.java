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
 * Test Reference No	: 	A048 UOM
 * Purpose              :   Insert Unit of Measure Descriptions
 * Date					:   16-05-2014
 * ACCESS               :   AKO,AKM
 */

public class A048_UOMTest extends BaseTest{
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
		List<String> uomCodeBox = dataRow.findNamesReturnValues("uomCodeBox");
		List<String> uomCodeEach = dataRow.findNamesReturnValues("uomCodeEach");
		List<String> uomCodeDays = dataRow.findNamesReturnValues("uomCodeDays");
		List<String> uomCodeYear = dataRow.findNamesReturnValues("uomCodeYear");
		List<String> uomBaseForEach = dataRow.findNamesReturnValues("uomBaseForEach");
		List<String> uomBaseForDays = dataRow.findNamesReturnValues("uomBaseForDays");
		
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Unit Of Measure Code List","Currency search page","displayed");
			
		currencyPage.clickOnInsert();
		
		createUOM(currencyPage,uomCodeBox);
		createUOM(currencyPage,uomCodeEach);
		createUOM(currencyPage,uomCodeDays);
		createUOM(currencyPage,uomCodeYear);
		
		/*Exit from the UOM details page*/
		currencyPage.clickOnCancel();
	
		verifyUOMCode(currencyPage,uomCodeBox);
		verifyUOMCode(currencyPage,uomCodeEach);
		verifyUOMCode(currencyPage,uomCodeDays);
		verifyUOMCode(currencyPage,uomCodeYear);
		
		currencyPage.clickOnCancel();
		
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
			
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - UOM Relationship - List","Currency search page","displayed");

		currencyPage.clickOnInsert();
		
		createUOMRelationShip(currencyPage,uomBaseForEach);
		createUOMRelationShip(currencyPage,uomBaseForDays);
			
		/*Exit from the UOM details page*/
		currencyPage.clickOnCancel();
		
		verifyUOMRelation(currencyPage,uomBaseForEach);
		verifyUOMRelation(currencyPage,uomBaseForDays);
		
		currencyPage.logOut(2);

	}
	
	private void createUOM(CurrencyPage currencyPage,List<String> uomCode) throws InterruptedException{
		/*Create UOM code*/
		boolean update = currencyPage.enterUomDetails(uomCode);	
		
		if(update){				
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : UOM code "+uomCode.get(0)+" present in the list");
		}
	}
	
	private void createUOMRelationShip(CurrencyPage currencyPage,List<String> uomCode) throws InterruptedException{
		/*Create UOM relationship*/
		boolean update = currencyPage.enterUOMRelationShip(uomCode);	
		
		if(update == true){		
			currencyPage.clickOnUpdate();
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New UOM Relationship "+uomCode.get(1)+ " displayed in the list");
		}
	}
	
	private void verifyUOMCode(CurrencyPage currencyPage,List<String> uomCode){
		currencyPage.searchValue(uomCode.get(0), 1,0);
		
		/*Verify UOM code and UOM relationship*/
		if(currencyPage.verifyValues(uomCode.get(0))){
			testcases.add(getCurreentDate()+" | Pass : UOM code "+uomCode.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : UOM code "+uomCode.get(0)+ " not displayed in the list");
		}
	}
	
	private void verifyUOMRelation(CurrencyPage currencyPage,List<String> uomCode){
		currencyPage.searchValue(uomCode, 2,1);
		
		/*Verify UOM code and UOM relationship*/
		if(currencyPage.verifyValues(uomCode.get(1))){
			testcases.add(getCurreentDate()+" | Pass : UOM code "+uomCode.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : UOM code "+uomCode.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A048" };
		String [] selectedNames = {"userName","passWord","currencyCode","uomCodeBox","uomCodeEach","uomCodeDays","uomCodeYear","uomBaseForEach","uomBaseForDays"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
