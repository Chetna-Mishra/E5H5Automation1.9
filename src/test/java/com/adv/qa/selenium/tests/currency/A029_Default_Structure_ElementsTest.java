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
 * Test Reference No	:   A029 Default Structure Elements
 * Purpose              :   Set Up Default Structure Elements and add DUM to West Element
 * Date					:   05-05-2014
 * ACCESS               :   ECE
 */


public class A029_Default_Structure_ElementsTest extends BaseTest{

	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{	
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		String code = dataRow.get("code");
		List<String> values = dataRow.findNamesReturnValues("values");
		List<String> structureForHQ = dataRow.findNamesReturnValues("structureForHQ");
		List<String> structureForNorth = dataRow.findNamesReturnValues("structureForNorth");
		List<String> structureForSouth = dataRow.findNamesReturnValues("structureForSouth");
		List<String> structureForEast = dataRow.findNamesReturnValues("structureForEast");
		List<String> structureForWest = dataRow.findNamesReturnValues("structureForWest");
		List<String> structureForSusp = dataRow.findNamesReturnValues("structureForSusp");
		List<String> pathKeyList = dataRow.findNamesReturnValues("pathKeyList");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		/*Search currency code*/
		currencyPage.fillCurrenceyCode(code);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+code+" - Elements List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,values, 3, 1);
		
		/*Insert new structure element*/
		currencyPage.clickOnInsert();
		
		createDefautStructure(currencyPage,structureForHQ);
		createDefautStructure(currencyPage,structureForNorth);
		createDefautStructure(currencyPage,structureForSouth);
		createDefautStructure(currencyPage,structureForEast);
		createDefautStructure(currencyPage,structureForWest);
		
		/*Exit from the structure element details page*/
		currencyPage.clickOnCancel();

		verifyValues(currencyPage,structureForHQ);
		verifyValues(currencyPage,structureForNorth);
		verifyValues(currencyPage,structureForSouth);
		verifyValues(currencyPage,structureForEast);
		verifyValues(currencyPage,structureForWest);
		
		currencyPage.searchValue(companyId,structureForSusp, 3, 2);
		
		currencyPage.clickOnAmend();
		
		createDefautStructure(currencyPage,structureForSusp);
		
		createPathKey(currencyPage,pathKeyList,structureForWest);
		
		verifyValues(currencyPage,structureForSusp);

		currencyPage.logOut(2);
	}

	
	private void createDefautStructure(CurrencyPage currencyPage,List<String> structureElement) throws InterruptedException{		
		/*Create new structure element*/
		boolean update = currencyPage.enterElementDetails(structureElement);

		if(update == true){
			currencyPage.clickOnUpdate();	
		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New structure element  "+structureElement.get(1)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> structureElement){
		/*Verify new structure element in the list*/
		if(currencyPage.verifyValues(structureElement.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New structure element "+structureElement.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New structure element "+structureElement.get(1)+ " not displayed in the list");
		}
	}

	public void createPathKey(CurrencyPage currencyPage,List<String> pathKeyList,List<String> structureElement) throws InterruptedException{
			currencyPage.clickOnPathKey();
			
			currencyPage.searchValue(companyId,pathKeyList,6,2);
			
			currencyPage.searchManagementCode(pathKeyList.get(2),6);

			currencyPage.clickOnInsert();
			
			currencyPage.createPathKey(pathKeyList);
			
			currencyPage.clickOnUpdate();
			
			/*Exit from the batch type details page*/
			currencyPage.clickOnCancel();
			
			/*Verify new batch type in the Batch list*/
			Assert.assertTrue(testcases,currencyPage.verifyValues(pathKeyList.get(2)), "New path key "+pathKeyList.get(2),"displayed in the list");		
			
			currencyPage.clickOnReturnButton(9);
	}

	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A029.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
