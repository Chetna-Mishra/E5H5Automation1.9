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
 * Test Reference No	: 	AD01004 Stock Take on Using Positive Adjustment
 * Purpose              :   Enter stock item quantities 
 * ACCESS               :   HBA
 */

public class AD01004_Stock_Take_On_Using_Positive_AdjustmentTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> eastStoreForItem1 = dataRow.findNamesReturnValues("eastStoreForItem1");
//		List<String> westStoreForItem1 = dataRow.findNamesReturnValues("westStoreForItem1");
		List<String> westStoreForItem2 = dataRow.findNamesReturnValues("westStoreForItem2");
		List<String> westStoreForItem3 = dataRow.findNamesReturnValues("westStoreForItem3");
		List<String> westStoreForItem4 = dataRow.findNamesReturnValues("westStoreForItem4");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");

		insertStockAdjustment(currencyPage,dataRow,eastStoreForItem1);
//		insertStockAdjustment(currencyPage,dataRow,westStoreForItem1);
		insertStockAdjustment(currencyPage,dataRow,westStoreForItem2);
		insertStockAdjustment(currencyPage,dataRow,westStoreForItem3);
		insertStockAdjustment(currencyPage,dataRow,westStoreForItem4);
		
		
		currencyPage.logOut(2);
	}
	
	private void insertStockAdjustment(CurrencyPageNew currencyPage,DataRow dataRow,List<String> elements) throws InterruptedException{		
			String code = "EDTHMVMT ACT=INSERT,CMPY="+companyId+",STORE="+elements.get(0)+",MVMT-IND=A";
			String currencyCode = dataRow.get("currencyCode");
			String message = "Movement Reference";
			
			currencyPage.isCommandDisplayed();
			
			currencyPage.fillCurrenceyCode(code);
			
			Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Positive Adjustment Line Details Edit","Currency search page","displayed");
			
			currencyPage.addLineDetails(elements,"A");
			
			currencyPage.clickOnAcceptWarnings();
			
			currencyPage.clickOnUpdate();
				
			String referenceMessage = currencyPage.getToolContentText();
			
			/*Verify material issue*/
			if(referenceMessage.contains(message)){
				testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
				testcases.add(getCurreentDate()+" | Pass : Material issue created");
			}
			else{			
				testcases.add(getCurreentDate()+" | Fail : Material issue not created");
			}

			currencyPage.isCommandDisplayed();		
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
		String[] nodeID = { "AD01004" };
		String [] selectedNames = {"userName","passWord","currencyCode","eastStoreForItem1","westStoreForItem1","westStoreForItem2",
				"westStoreForItem3","westStoreForItem4"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
