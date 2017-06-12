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
 * Test Reference No	: 	A115 User Roles Set-up   
 * Purpose              :   To Set up and Use User Roles 
 * ACCESS               :   ADC
 */

public class A115_User_Roles_Set_upTest extends BaseTest{
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
		List<String> securityGroup = dataRow.findNamesReturnValues("securityGroup");
		List<String> rangeListPM = dataRow.findNamesReturnValues("rangeListPM");
		List<String> rangeListP = dataRow.findNamesReturnValues("rangeListP");
		List<String> rangeListIM = dataRow.findNamesReturnValues("rangeListIM");
		List<String> accessCodeP = dataRow.findNamesReturnValues("accessCodeP");
		List<String> accessCodePM = dataRow.findNamesReturnValues("accessCodePM");
		List<String> accessCodeIM = dataRow.findNamesReturnValues("accessCodeIM");
		List<String> securityGroupP = dataRow.findNamesReturnValues("securityGroupP");
		List<String> securityGroupPM = dataRow.findNamesReturnValues("securityGroupPM");
		List<String> securityGroupIM = dataRow.findNamesReturnValues("securityGroupIM");
		List<String> role1 = dataRow.findNamesReturnValues("role1");
		List<String> role2 = dataRow.findNamesReturnValues("role2");
		List<String> userRole1 = dataRow.findNamesReturnValues("userRole1");
		List<String> userRole2 = dataRow.findNamesReturnValues("userRole2");		

		/*Log in to application*/
		LoginPage loginPage = new LoginPage(driver);
		
		Assert.assertTrue(testcases, loginPage.isLoginPageDisplayed(), "Login page", "displayed");
		loginPage.logIn(userName, passWord);
		
		/*Navigate to currency page Home page e5 application*/
		CurrencyPageNew currencyPage = new CurrencyPageNew(driver);
		
		/*Verify command line*/
		Assert.assertTrue(testcases,currencyPage.isCommandDisplayed(),"Command line","displayed");
		
		String code = "EDTSCTYGRP ACT=INSERT"; 
		
		currencyPage.fillCurrenceyCode(code);
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(0)+" - Security Group Edit","Currency search page","displayed");
		
		boolean update = currencyPage.createSecurityGroup(securityGroup);
		
		if(update==true){
			currencyPage.clickOnUpdate();
		}
		else{
			currencyPage.clickOnCancel();
			testcases.add(getCurreentDate()+" | Pass : New user role "+securityGroup.get(0)+ "  displayed in the list");
		}
		
		currencyPage.fillCurrenceyCode(currencyCode.get(1));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(1)+" - Range List Code List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,securityGroup, 3, 9);
		
		currencyPage.clickOnInsert();
		
		createRangeList(currencyPage,rangeListP);
		createRangeList(currencyPage,rangeListPM);
		createRangeList(currencyPage,rangeListIM);
		
		currencyPage.clickOnCancel();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(2));
		
		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(2)+" - Access Code List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,securityGroup, 3, 9);
		
		currencyPage.clickOnInsert();
		
		createAccessCode(currencyPage,accessCodeP);
		createAccessCode(currencyPage,accessCodePM);
		createAccessCode(currencyPage,accessCodeIM);
		
		currencyPage.clickOnCancel();
		
		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(3));

		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(3)+" - Security Group Access Code Lis","Currency search page","displayed");

		currencyPage.searchValue(companyId,securityGroup, 3, 9);
		
		currencyPage.clickOnInsert();
		createSecurityGroup(currencyPage,securityGroupP);
		createSecurityGroup(currencyPage,securityGroupPM);
		createSecurityGroup(currencyPage,securityGroupIM);
		
		currencyPage.clickOnCancel();
		
		currencyPage.isConfirmPopUpDisplayed();

		currencyPage.clickOnCancel();
		
		currencyPage.isCommandDisplayed();
		
		String codeForRole1 = "EDTROLE ACT=INSERT,ROLE=BM";
		String codeForRole2 = "EDTROLE ACT=INSERT,ROLE=CMS190";

		createRole(currencyPage,role1,codeForRole1,currencyCode.get(4));
		createRole(currencyPage,role2,codeForRole2,currencyCode.get(4));
		
		currencyPage.isCommandDisplayed();
		
		currencyPage.fillCurrenceyCode(currencyCode.get(5));

		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode.get(5)+" - User Role List","Currency search page","displayed");
		
		currencyPage.searchValue(companyId,userRole1, 3, 2);
		
		currencyPage.clickOnInsert();
		
		createUserRole(currencyPage,userRole1);
		createUserRole(currencyPage,userRole2);
		
		currencyPage.clickOnCancel();
		
		currencyPage.logOut(2);
		
	}
	
	public void createRangeList(CurrencyPageNew currencyPage,List<String> elements){
		String message = "The previously-requested action has been performed";
		
		boolean update = currencyPage.createRangeListCode(elements);
			
		if(update){
			currencyPage.clickOnUpdate();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : Range code "+elements.get(1)+" created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Range code "+elements.get(1)+" not created");
			}
		}		
	}
	
	public void createAccessCode(CurrencyPageNew currencyPage,List<String> elements){
		String message = "The previously-requested action has been performed";
		boolean update = currencyPage.createAccessCode(elements);
			
		if(update){
			currencyPage.clickOnUpdate();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass :Access code "+elements.get(1)+" created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Access code "+elements.get(1)+" not created");
			}
		}		
	}

	public void createSecurityGroup(CurrencyPageNew currencyPage,List<String> elements){
		String message = "The previously-requested action has been performed";
		boolean update = currencyPage.createSecurityGroupAccessCode(elements);
			
		if(update){
			currencyPage.clickOnUpdate();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : Security group "+elements.get(1)+" created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Security group "+elements.get(1)+" not created");
			}
		}		
	}

	public void createRole(CurrencyPageNew currencyPage,List<String> elements,String code,String currencyCode) throws InterruptedException{
		String message = "The previously-requested action has been performed";
		currencyPage.fillCurrenceyCode(code);

		/*Verify currency search page displayed*/
		Assert.assertEquals(testcases,currencyPage.getTableHeader(), "M"+currencyCode+" - Role Edit","Currency search page","displayed");

		boolean update = currencyPage.createRole(elements);
		
		if(update){
			
			currencyPage.clickOnUpdtWarnings();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : Role "+elements.get(0)+" created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : Role "+elements.get(0)+" not created");
			}
			
		}
		else{
			currencyPage.clickOnCancel();
			}
	}
	
	public void createUserRole(CurrencyPageNew currencyPage,List<String> elements){
		String message = "The previously-requested action has been performed";
		boolean update = currencyPage.createUserRole(companyId,elements);
			
		if(update){
			currencyPage.clickOnUpdate();
			
			if(currencyPage.getToolContentText().contains(message)){
				testcases.add(getCurreentDate()+" | Pass : User Role "+elements.get(0)+" created");
			}
			else{
				testcases.add(getCurreentDate()+" | Fail : User Role "+elements.get(0)+" not created");
			}
		}
		else{
			currencyPage.clickOnCancel();
			
			currencyPage.isCommandDisplayed();
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
		String[] nodeID = { "A115" };
		String [] selectedNames = {"userName","passWord","currencyCode","securityGroup","rangeListP","rangeListPM","rangeListIM","accessCodeP","accessCodePM","accessCodeIM"
				,"securityGroupP","securityGroupPM","securityGroupIM","role1","role2","userRole1","userRole2"};
		DataResource dataResourceSelected = new DataResource (xmlFilePath, selectedNames, true,nodeID);
		DataRow [] [] rows = dataResourceSelected.getDataRows4DataProvider();
		return rows;	
	}
}
