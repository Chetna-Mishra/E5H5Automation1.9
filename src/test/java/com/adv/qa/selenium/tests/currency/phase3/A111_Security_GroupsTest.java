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
 * Test Reference No	: 	A111 Security Groups 
 * Purpose              :   Security Groups 
 * ACCESS               :   ADA
 */

public class A111_Security_GroupsTest extends BaseTest{
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
		List<String> securityGroupIM1 = dataRow.findNamesReturnValues("securityGroupIM1");
		List<String> securityGroupIM2 = dataRow.findNamesReturnValues("securityGroupIM2");		
		List<String> securityGroupIM4 = dataRow.findNamesReturnValues("securityGroupIM4");		

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
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Security Group List","Currency search page","displayed");
		
		createSecurityGroups(currencyPage,securityGroupIM1);
		currencyPage.clickOnReturnButton(5);
		
		createSecurityGroups(currencyPage,securityGroupIM2);
		currencyPage.clickOnReturnButton(5);
	
		createSecurityGroups(currencyPage,securityGroupIM4);

		currencyPage.logOut(2);
	}
	
	
	private void createSecurityGroups(CurrencyPageNew currencyPage,List<String> elements) throws InterruptedException{
		currencyPage.search(elements.get(0),2,0);
		
		currencyPage.clickOnInsert();
		
		boolean update = currencyPage.insertSecurityGroup(elements);
		
		if(update == true){
			currencyPage.clickOnUpdate();		
		}
		
		currencyPage.clickOnCancel();
		
		if(currencyPage.verifyValues(elements.get(0))){			
			testcases.add(getCurreentDate()+" | Pass : Security group "+elements.get(0)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : Security group "+elements.get(0)+ " displayed in the list");
		}
		
		currencyPage.clickOnDenials();
		
		int securityGroup = Integer.parseInt(elements.get(2));	
		
		for(int i=3;i<=(securityGroup+3);i++){
			amendDenials(currencyPage,elements,elements.get(i));
		}
	}
	
	public void amendDenials(CurrencyPageNew currencyPage,List<String> elements,String securityGroup){
		
		currencyPage.searchValue(securityGroup,2,1);
		
		if(!currencyPage.verifyDenyAll()){			
			currencyPage.clickOnAmend();
			
			currencyPage.selectDenyAll();
			
			currencyPage.clickOnUpdate();			
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
		String[] nodeID = { "A111" };
		String [] selectedNames = {"userName","passWord","currencyCode","securityGroupIM1","securityGroupIM2","securityGroupIM4"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
