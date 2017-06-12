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
 * Test Reference No	: 	A112 User Company Security 
 * Purpose              :   User Company Security 
 * ACCESS               :   AFE
 */

public class A112_User_Company_SecurityTest extends BaseTest{
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
		List<String> userAccept = dataRow.findNamesReturnValues("userAccept");
		List<String> userBuyer = dataRow.findNamesReturnValues("userBuyer");
		List<String> userExped = dataRow.findNamesReturnValues("userExped");
		List<String> userGrec = dataRow.findNamesReturnValues("userGrec");

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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - User Company Security List","Currency search page","displayed");
		
		currencyPage.search(companyId,3,1);
		
		currencyPage.clickOnInsert();
		
		createSecurityGroups(currencyPage,userAccept);
		createSecurityGroups(currencyPage,userBuyer);
		createSecurityGroups(currencyPage,userExped);
		createSecurityGroups(currencyPage,userGrec);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,userAccept);
		verifyValues(currencyPage,userBuyer);
		verifyValues(currencyPage,userExped);
		verifyValues(currencyPage,userGrec);

		currencyPage.logOut(2);
	}
	
	
	private void createSecurityGroups(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{
		
		boolean update = currencyPage.insertUserCompany(elements,companyId);
		
		if(update == true){
			currencyPage.clickOnUpdate();		
		}	
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> securityUser){
		
		if(currencyPage.verifyValues(securityUser.get(0))){
			testcases.add(getCurreentDate()+" | Pass : New user company security "+securityUser.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New user company security "+securityUser.get(0)+ " not displayed in the list");
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
		String[] nodeID = { "A112" };
		String [] selectedNames = {"userName","passWord","currencyCode","userAccept","userBuyer","userExped","userGrec"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
