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
 * Test Reference No	: 	A085 Authorisation by Value Level (AP & PM)
 * Purpose              :   Authorisation by Value Level for Accounts Payable
 * Date					:   24-06-2014
 * ACCESS               :   GOG,DBG
 */

public class A085_Authorisation_By_Value_LevelTest extends BaseTest{
	/*Launch the browser*/
	@BeforeClass
	public void beforeClass() throws Exception {
		super.setUp();
	}
	
	@Test( dataProvider ="dp")
	public void verify(DataRow dataRow) throws InterruptedException{
		String userName = dataRow.get("userName");
		String passWord = dataRow.get("passWord");
		List<String> currencyCode = dataRow.findNamesReturnValues("currencyCode");		
		List<String> apLev1 = dataRow.findNamesReturnValues("apLev1");
		List<String> apLev2 = dataRow.findNamesReturnValues("apLev2");
		List<String> apLev3 = dataRow.findNamesReturnValues("apLev3");
		List<String> apLev4 = dataRow.findNamesReturnValues("apLev4");	
		
		List<String> pmLev1 = dataRow.findNamesReturnValues("pmLev1");
		List<String> pmLev2 = dataRow.findNamesReturnValues("pmLev2");
		List<String> pmLev3 = dataRow.findNamesReturnValues("pmLev3");
		List<String> pmLev4 = dataRow.findNamesReturnValues("pmLev4");
		

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPage currencyPage = new CurrencyPage(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(0));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Value Level Auth Defn List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 3, 0);//GOG search
		
		currencyPage.clickOnInsert();
		
		createStructureAuthorisers(currencyPage,apLev1,1);
		createStructureAuthorisers(currencyPage,apLev2,1);
		createStructureAuthorisers(currencyPage,apLev3,1);
		createStructureAuthorisers(currencyPage,apLev4,1);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,apLev1,"AP");
		verifyValues(currencyPage,apLev2,"AP");
		verifyValues(currencyPage,apLev3,"AP");
		verifyValues(currencyPage,apLev4,"AP");
		
		currencyPage.clickOnCancel();
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Auth Definition Value Level -","Currency search page","displayed");
		
		currencyPage.searchValue(companyId, 2, 0);//GOG search
		
		currencyPage.clickOnInsert();
		
		createStructureAuthorisers(currencyPage,pmLev1,2);
		createStructureAuthorisers(currencyPage,pmLev2,2);
		createStructureAuthorisers(currencyPage,pmLev3,2);
		createStructureAuthorisers(currencyPage,pmLev4,2);
		
		currencyPage.clickOnCancel();
		
		verifyValues(currencyPage,pmLev1,"PM");
		verifyValues(currencyPage,pmLev2,"PM");
		verifyValues(currencyPage,pmLev3,"PM");
		verifyValues(currencyPage,pmLev4,"PM");

		currencyPage.logOut(2);
	}

	
	private void createStructureAuthorisers(CurrencyPage currencyPage,List<String> authoriser,int i) throws InterruptedException{
		boolean update = false;
		if(i == 1){		
			update = currencyPage.enterValueLevelAuthForAP(authoriser);
		}
		else{
			update = currencyPage.enterValueLevelAuthForPM(authoriser);	
		}
		
		if(update == true){
			currencyPage.clickOnUpdate();
		}
		
		else{
			testcases.add(getCurreentDate()+" | Pass : New authorisers "+authoriser.get(1)+ " displayed in the list");
		}
	}
	
	private void verifyValues(CurrencyPage currencyPage,List<String> authoriser,String level){		
		/*Verify New authorizers displayed in the list*/
		if(currencyPage.verifyValues(authoriser.get(1))){
			testcases.add(getCurreentDate()+" | Pass : New "+level+" authorisers "+authoriser.get(1)+ " displayed in the list");
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : New "+level+" authorisers "+authoriser.get(1)+ " not displayed in the list");
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
		String[] nodeID = { "A085" };
		String [] selectedNames = {"userName","passWord","currencyCode","apLev1","apLev2","apLev3","apLev4","pmLev1","pmLev2","pmLev3","pmLev4"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
