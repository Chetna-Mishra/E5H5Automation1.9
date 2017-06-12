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
 * Test Reference No	: 	A087 Authorisation by Level/GL Responsibility (AP)
 * Purpose              :   Authorisation by Level/GL Responsibility for Accounts Payable
 * Date					:   25-06-2014
 * ACCESS               :   GOK
 */

public class A087_Authorisation_By_Level_Or_GL_ResponsibilityTest extends BaseTest{
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
		List<String> authorisationCode = dataRow.findNamesReturnValues("authorisationCode");
		List<String> lev1User = dataRow.findNamesReturnValues("lev1User");
		List<String> lev2and3User = dataRow.findNamesReturnValues("lev2and3User");
		List<String> lev4User = dataRow.findNamesReturnValues("lev4User");
		String url = dataRow.get("url");		
					
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Val Lvl/GL Resp Auth Defn list","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,authorisationCode, 6, 6);
		
		currencyPage.clickOnInsert();
				
		/*Create batch type code*/
		currencyPage.enterAuthorisationByLevelOrGLResponsibilityForAP(authorisationCode,lev1User,lev2and3User,lev2and3User,lev4User);	
				
		currencyPage.clickOnCancel();
		
		/*Verify New authorization by level or GL responsibility code displayed in the list*/
		if(currencyPage.verifyValues(authorisationCode.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New authorisation by level or GL responsibility code "+authorisationCode.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New authorisation by level or GL responsibility code "+authorisationCode.get(1)+ " not displayed in the list");
		}
	
		currencyPage.logOut(2);
		
		/*Launch application for second test data*/
		driver.get(url);	

	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown(){
		super.tearDown();
	}
	
	@DataProvider
	public Object[][] dp() 
	{
		String folder = "src/test/resources/";
		String xmlFilePath = folder  + "A087.xml";
		DataResource dataResource = new DataResource(xmlFilePath);
		DataRow [] [] rows = dataResource.getDataRows4DataProvider();
		return rows;
	}
}
