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
 * Test Reference No	: 	AD12004 Create Order with Authorisation Switched On
 * Purpose              :   Create Order with Authorisation Switched on for a Supplier with Financial Limits 
 * ACCESS               :   DOC,DB3,DOA
 */

public class AD12004_Create_Order_With_Authorisation_Switched_OnTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> orderCode1 = dataRow.findNamesReturnValues("orderCode1");
		List<String> orderCode2 = dataRow.findNamesReturnValues("orderCode2");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
				
		createOrder(currencyPage,dataRow,orderCode1);
		createOrder(currencyPage,dataRow,orderCode2);
					
		currencyPage.logOut(1);
	}
	
	private String createOrder(CurrencyPageNew currencyPage,DataRow dataRow,List<String> orderCode) throws InterruptedException{
		String code  = "ORDER ACT=INSERT,COMPANY="+companyId;
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		
		String message = "Order Reference will be created.";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(code);
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Order - Edit","Currency search page","displayed");
		
		currencyPage.createOrderCode(orderCode);
		
		currencyPage.clickOnAcceptWarnings();
		
		currencyPage.clickOnUpdate();
			
		String referenceMessage = currencyPage.getToolContentText();
		
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
			testcases.add(getCurreentDate()+" | Pass : Material issue "+orderCode.get(0)+ " added");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Material issue "+orderCode.get(0)+ " added");
		}

		referenceMessage = referenceMessage.substring(8).replaceAll("[^0-9]", "");
		
		System.out.println("----referenceMessage-----------"+ referenceMessage);
		return referenceMessage;
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
		String[] nodeID = { "AD12004" };
		String [] selectedNames = {"userName","passWord","currencyCode","orderCode1","orderCode2","authorisor1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
