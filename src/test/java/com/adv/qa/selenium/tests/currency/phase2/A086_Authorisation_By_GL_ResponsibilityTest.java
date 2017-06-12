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
 * Test Reference No	: 	A078  Authorisation Control Codes (AP)
 * Purpose              :   Create Authorisation Control Codes for Accounts Payable
 * Date					:   24-06-2014
 * ACCESS               :   GOI
 */

public class A086_Authorisation_By_GL_ResponsibilityTest extends BaseTest{
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
		List<String> authorisationHQ = dataRow.findNamesReturnValues("authorisationHQ");
		List<String> authorisationWest = dataRow.findNamesReturnValues("authorisationWest");
		List<String> authorisationSouth = dataRow.findNamesReturnValues("authorisationSouth");
		List<String> authorisationNorth = dataRow.findNamesReturnValues("authorisationNorth");
		List<String> authorisationEast = dataRow.findNamesReturnValues("authorisationEast");
					
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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - GL Responsibility Auth Defn Li","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 6, 0);
		
		currencyPage.clickOnInsert();
		
		createAuthorisationByGLResponsibility(currencyPage,authorisationHQ);
		createAuthorisationByGLResponsibility(currencyPage,authorisationWest);
		createAuthorisationByGLResponsibility(currencyPage,authorisationSouth);
		createAuthorisationByGLResponsibility(currencyPage,authorisationNorth);
		createAuthorisationByGLResponsibility(currencyPage,authorisationEast);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,authorisationHQ);
		verifyValues(currencyPage,authorisationWest);
		verifyValues(currencyPage,authorisationSouth);
		verifyValues(currencyPage,authorisationNorth);
		verifyValues(currencyPage,authorisationEast);
		
		currencyPage.logOut(2);
	}

	
	private void createAuthorisationByGLResponsibility(CurrencyPage currencyPage,List<String> authorisationCode) throws InterruptedException{
		/*Create batch type code*/
		boolean update = currencyPage.enterGLResponsibilityAuthForAP(authorisationCode);	
		
		if(update == true){
		
			currencyPage.clickOnUpdate();
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New authorisation GL responsibility code "+authorisationCode.get(1)+ " displayed in the list");	
		}
		
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> authorisationCode){		
		/*Verify New authorization GL responsibility code displayed in the list*/
		if(currencyPage.verifyValues(authorisationCode.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New authorisation GL responsibility code "+authorisationCode.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New authorisation GL responsibility code "+authorisationCode.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A086" };
		String [] selectedNames = {"userName","passWord","currencyCode","authorisationHQ","authorisationWest","authorisationSouth","authorisationNorth",
		"authorisationEast"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
