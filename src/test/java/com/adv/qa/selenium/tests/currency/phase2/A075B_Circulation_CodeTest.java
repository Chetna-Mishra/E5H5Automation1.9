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
 * Test Reference No	: 	A075 Circulation Code
 * Purpose              :   Set Up Circulation Code.
 * Date					:   24-06-2014
 * ACCESS               :   PX8
 */

public class A075B_Circulation_CodeTest extends BaseTest{
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
		List<String> circulationK1 = dataRow.findNamesReturnValues("circulationK1");
		List<String> circulationM1 = dataRow.findNamesReturnValues("circulationM1");
		List<String> circulationP1 = dataRow.findNamesReturnValues("circulationP1");
		List<String> circulationT1 = dataRow.findNamesReturnValues("circulationT1");
		List<String> circulationRS = dataRow.findNamesReturnValues("circulationRS");
					
		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode);
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Circulation Code List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);
		
		currencyPage.clickOnInsert();

		createCirculationCode(currencyPage,circulationK1);
		createCirculationCode(currencyPage,circulationM1);
		createCirculationCode(currencyPage,circulationP1);
		createCirculationCode(currencyPage,circulationT1);
		createCirculationCode(currencyPage,circulationRS);
		
		currencyPage.clickOnCancel();
		
		currencyPage.logOut(1);
	}

	
	private void createCirculationCode(CurrencyPage currencyPage,List<String> circulationCode) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		/*Create batch type code*/
		boolean update = currencyPage.enterCirculationCode(circulationCode);	
		
		if(update == true){
			
			currencyPage.clickOnUpdate();
			
			/*Verify new circulation code type in the list*/	
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : New circulation code "+circulationCode.get(0)+ " displayed in the list");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : New circulation code "+circulationCode.get(0)+ " not displayed in the list");
			}

		}
		else{
			testcases.add(getCurreentDate()+" | Pass : New circulation code "+circulationCode.get(0)+ " displayed in the list");
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
		String xmlFilePath = folder  + "A075B.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
