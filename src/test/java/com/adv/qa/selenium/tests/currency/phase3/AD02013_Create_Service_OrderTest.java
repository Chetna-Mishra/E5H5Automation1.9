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
 * Test Reference No	: 	AD02013 Create Service Order 
 * Purpose              :   Create Service Order 
 * ACCESS               :   DOC
 */

public class AD02013_Create_Service_OrderTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> authorisor1 = dataRow.findNamesReturnValues("authorisor1");
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
				
		String orderNumber = createOrder(currencyPage,dataRow);
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Unauthorised Documents List","Currency search page","displayed");
		
		authoriseOrder(currencyPage,authorisor1,orderNumber);
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
				
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Order - List","Currency search page","displayed");

		currencyPage.searchOrder(companyId, orderNumber, 19);
		
		Assert.assertEquals(testcases,currencyPage.getStatus(6), "Outstanding","Serviice order status is"," Outstanding");
		
		currencyPage.logOut(2);
	}
	
	private String createOrder(CurrencyPageNew currencyPage,DataRow dataRow) throws InterruptedException{		
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");
		List<String> orderCode = dataRow.findNamesReturnValues("orderCode");
		String message = "Order Reference";
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
				
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Order - List","Currency search page","displayed");

		currencyPage.searchValue(companyId, orderCode, 19, 8);
		
		currencyPage.clickOnInsert();
		
		currencyPage.createServiceOrderCode(orderCode);
		
		currencyPage.clickOnOrderExplode();
		
		currencyPage.clickOnAcceptWarnings();
		
		currencyPage.clickOnUpdate();
			
		String referenceMessage = currencyPage.getToolContentText();
		
		/*Verify new batch type in the list*/
		if(referenceMessage.contains(message)){
			testcases.add(getCurreentDate()+" | Pass :  "+referenceMessage);
			testcases.add(getCurreentDate()+" | Pass : Service order "+orderCode.get(0)+ " created");
		}
		else{			
			testcases.add(getCurreentDate()+" | Fail : Service order "+orderCode.get(0)+ " not created");
		}

		referenceMessage = referenceMessage.substring(8).replaceAll("[^0-9]", "");
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();
		
		currencyPage.clickOnCancel();
		
		return referenceMessage;
		
	}
	
	private void authoriseOrder(CurrencyPageNew currencyPage,List<String> authorisorList,String orderNumber) throws InterruptedException{
		
		currencyPage.searchAuthorisor(companyId, authorisorList,orderNumber, 2);
		
		currencyPage.selectOrder();
		
		currencyPage.clickOnCancel();

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
		String[] nodeID = { "AD02013" };
		String [] selectedNames = {"userName","passWord","currencyCode","orderCode","authorisor1"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
