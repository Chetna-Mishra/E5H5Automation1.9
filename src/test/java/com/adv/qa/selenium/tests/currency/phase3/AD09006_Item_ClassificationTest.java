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
 * Test Reference No	: 	AD09006 Item Classification 
 * Purpose              :   Create Item Classification 
 * ACCESS               :   PIA
 */

public class AD09006_Item_ClassificationTest extends BaseTest{
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
		List<String> itemDv525 = dataRow.findNamesReturnValues("itemDv525");	
		List<String> itemDv626d = dataRow.findNamesReturnValues("itemDv626d");
		List<String> itemDvpS325 = dataRow.findNamesReturnValues("itemDvpS325");
		List<String> itemDvpS335 = dataRow.findNamesReturnValues("itemDvpS335");
		List<String> itemA160 = dataRow.findNamesReturnValues("itemA160");
		List<String> itemDvd909 = dataRow.findNamesReturnValues("itemDvd909");
		
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Item List","Currency search page","displayed");
		
		addClassificationToStores(currencyPage,itemDv525);
		addClassificationToStores(currencyPage,itemDv626d);
		addClassificationToStores(currencyPage,itemDvpS325);
		addClassificationToStores(currencyPage,itemDvpS335);
		addClassificationToStores(currencyPage,itemA160);
		addClassificationToStores(currencyPage,itemDvd909);
		
		currencyPage.logOut(2);
	}
	
	
	private void addClassificationToStores(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		currencyPage.searchValue(companyId,elements,2,1);
		
		currencyPage.clickOnAmendClassification();

		currencyPage.createItemClassification(elements);
	
		currencyPage.clickOnUpdate();
		
		if(currencyPage.getToolContentText().contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  Classification amended successfully");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail :  Classification not amended ");
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
		String[] nodeID = { "AD09006" };
		String [] selectedNames = {"userName","passWord","currencyCode","itemDv525","itemDv626d","itemDv525","itemDvpS325","itemDvpS335",
				"itemA160","itemDvd909"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
