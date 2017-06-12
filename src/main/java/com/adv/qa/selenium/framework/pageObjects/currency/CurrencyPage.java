package com.adv.qa.selenium.framework.pageObjects.currency;

import java.sql.SQLException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.adv.qa.selenium.framework.pageObjects.Page;
import com.adv.qa.selenium.framework.pageObjects.PageObjects;
import com.adv.qa.selenium.helpers.DatabaseQuery;
import com.adv.qa.selenium.helpers.SeleniumDaoException;
import com.adv.qa.selenium.helpers.WaitHelper;


public class CurrencyPage extends Page{
	
	private PageObjects pObject = new PageObjects();
	private DatabaseQuery dbQuery = new DatabaseQuery();
	private String message = "The specified key already exists";
	
	public CurrencyPage(EventFiringWebDriver driver) {
		super(driver);
		log.info("In currency page");
	}
	
	private WebElement msgNode(){
		return getDriver().findElement(By.id(pObject.MESSAGE_NODE_LABEL));
	}
	/**
	 * Get tool content message
	 * @return tool content
	 */
	public String getToolContentText(){
		log.info("Inside getToolContext method");
		WaitHelper.waitAdditional(2);
		String toolTipValue = "";
		try{
			if(!getDriver().findElement(By.id(pObject.TOOL_TIP_CONTENT)).isDisplayed()){			
					msgNode().click();
					WaitHelper.waitAdditional(2);
			}
			toolTipValue = getDriver().findElement(By.id(pObject.TOOL_TIP_CONTENT)).getText();
		}
		catch(NoSuchElementException e){
			toolTipValue = "";
		}
		WaitHelper.waitAdditional(2);
		return toolTipValue;
	}
	
	public boolean isToolTipDisplayed(){
		boolean toolTip = false;
		try{
		if(getDriver().findElement(By.id(pObject.MESSAGE_NODE_LABEL)).isDisplayed()){

			toolTip = true;
		}

		}
		catch(NoSuchElementException e){
			toolTip = false;
		}
		return toolTip;
	}
	/**
	 * Verify command box
	 * @return true/false
	 */
	public boolean isCommandDisplayed(){
		log.info("Inside isCommandDisplayed method");
		boolean command = false;
		try{
			if(getDriver().findElement(By.name("COMMAND_field_1")).isDisplayed()){
				command = true;
			}
		}
		catch (NoSuchElementException e){
			clickOnCancel();
			if(getDriver().findElement(By.name("COMMAND_field_1")).isDisplayed()){
				command = true;
			}
			WaitHelper.waitAdditional(2);
		}
		return command;
	}
	
	/**
	 * Change company from default company
	 * @param company
	 */
	public void clickOnChangeCompany(String company){
		log.info("Inside chamgeCompany method");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.LABEL)).click();
		getDriver().findElement(By.id(pObject.ONE+pObject._+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ONE+pObject._+pObject.FOUR)).sendKeys(company);//Company
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ONE+pObject._FIRST+pObject.LABEL)).click();
		WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Enter currency code in the command line
	 * @param code
	 * @throws InterruptedException
	 */
	public void fillCurrenceyCode(String code) throws InterruptedException{
		WaitHelper.waitAdditional(3);
		log.info("Fill currency code");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(code);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Click on OK button
	 * @throws InterruptedException
	 */
	public void clickOnButton(int i) throws InterruptedException{
		log.info("Clicing button");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(4);
	}
	
	private WebElement getSecondHeader(){
		return getDriver().findElement(By.xpath("//html//body//div[2]//div[2]/div/div[2]/div/div[2]"));
	}
	
	/**
	 * Verify title of table displayed with currency code
	 * @return page header
	 */
	public String getTableHeader(){
		log.info("Inside getTab header method");
		String header = "";
		if(getSecondHeader() != null){
			header = getSecondHeader().getText();
		}
		WaitHelper.waitAdditional(1);
		return header;
	}
	
	/**
	 * Verify Currency list
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isCurrencyListDisplayed() throws InterruptedException{
		log.info("Inside isCurrencyDisplayed method");
		WaitHelper.waitAdditional(2);
		return getDriver().findElement(By.className(pObject.CURRENCY_PANE)).isDisplayed();
	}
	
	/**
	 * Click on Insert button
	 */
	public void clickOnInsert(){
		WaitHelper.waitAdditional(2);
		log.info("Clicking on Insert button");
		int count = 0;
		do{
			count=count+1;
			List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
			for(WebElement wb : wbs){
				if(wb.getText().equals("Insert")){
					wb.click();
					break;
				}
			}
		}while(!(getTableHeader().contains("Edit")) && count !=3);
		WaitHelper.waitAdditional(2);
	}

	 /**
     *Add new currency
	 * @throws InterruptedException 
     */
	public boolean addNewCurrency(List<String> currencyList) throws InterruptedException{
		log.info("Adding new currency");
		boolean update = false;
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(currencyList.get(0)); //Currency Test
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER); //Currency Test
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(currencyList.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(currencyList.get(2));//Decimal places
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(currencyList.get(3));//Unit
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(currencyList.get(4));//Units
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(currencyList.get(5));//Sub-Unit
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(currencyList.get(6));//Sub-Units
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(currencyList.get(7));//Emu Indicator dropdown
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(currencyList.get(8));//Fixed rate
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(currencyList.get(9));//Date
			WaitHelper.waitAdditional(3);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(currencyList.get(10));//Rounding Ind
			update = true;
		}
		return update;
	}
	
	/**
     *Click on update button
	 * @throws InterruptedException 
     */
	public void clickOnUpdateCurrency() throws InterruptedException{
		log.info("Clicking on Update btton");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();	
		try{
			isRefreshDisplayed();
		}
		catch(NoSuchElementException e){
			clickOnCancel();
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify Entity present in Currency table by iterating in to each column
	 * @param value
	 * @return true
	 */
	public boolean verifyValues(String value){
		WaitHelper.waitAdditional(2);
		log.info("Verify presence of value in the list");
		boolean currencyValue = false;
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.CURRENCY_TABLE_CELL));
		for(WebElement wb : wbs){
			if(wb.getText().contains(value)){
				currencyValue = true;
				break;
			}
		}
		return currencyValue;
	}
	
	/**
	 * Get cancel button
	 */
	
	public List<WebElement> getAlllButton(){
		log.info("Verify presence of Cancel button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		return wbs;
	}
	
	/**
	 * Click on Cancel button
	 */
	public void clickOnCancel(){
		log.info("Clicking on cancel button");
		WaitHelper.waitAdditional(3);
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Cancel")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}

	/**
	 * Verify presence of confirmation window
	 * @return true/false
	 */
	public void isConfirmPopUpDisplayed(){
		log.info("Verify confirmation pop up");
		WaitHelper.waitAdditional(3);
		try{
			if(getDriver().findElement(By.id(pObject.ONE+pObject._ZERO)).isDisplayed()){
				getDriver().findElement(By.id(pObject.ONE+pObject._ZERO)).click();
			}
		}
		catch (NoSuchElementException e){
			
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Verify presence of Cancel button
	 * @return
	 */
	public boolean isCancelDisplayed(){
		log.info("Verify cancel button");
		WaitHelper.waitAdditional(2);
		boolean cancel = false;
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Cancel")){
				cancel = true;
				break;
			}
		}
		return cancel;
	}

	/*Get id of YES button*/
	private WebElement getYesButton(){
		return getDriver().findElement(By.id(pObject.TWO+pObject._ZERO));
	}
	
	/**
	 * Click on Yes button
	 */
	public void clickOnYesButton(){
		log.info("Verify Yes button");
		WaitHelper.waitAdditional(2);
		getYesButton().click();
	}
	
	/**
	 * Logout from the application
	 * @throws InterruptedException
	 */
	public void logOut(int i) throws InterruptedException{
		log.info("Logging out from the application");
		if(i==2){
			clickOnCancel();
		}
		clickOnCancel();	
		getYesButton().click();
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Exit form top header
	 */
	public void clickOnExit(){
		log.info("Clicking on Exit button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Exit")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Exit")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Click on Exit form top header
	 */
	public void exitFromSupplierElement(){
		log.info("Clicking on Exit button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Exit")){
				wb.click();
				break;
			}
		}
	}
	
	/**
	 * Delete currency from the currency ist
	 */
	public void clickOnPurge(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Edit")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Purge")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Click on view button
	 */
	public void clickOnView(){
		log.info("Clicking on View button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("View")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Refresh after deleting currency
	 * @throws InterruptedException
	 */
	public void clickOnRefresh(){
		log.info("Clicking on Refresh button");
		WaitHelper.waitAdditional(5);
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Refresh")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Verify Refresh button
	 * @throws InterruptedException
	 */
	public boolean isRefreshDisplayed() throws InterruptedException{
		log.info("Verify refresh button");
		boolean refreshButton = false;
		WaitHelper.waitAdditional(5);
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Refresh")){
				refreshButton = true;
				break;
			}
		}
		WaitHelper.waitAdditional(2);
		return refreshButton;
	}
	/**
	 * Get sections
	 * @return
	 */
	private List<WebElement> getSections(){
		 return getDriver().findElements(By.className(pObject.SECTION_LAYOUT));		 
	}
	
	/**
	 * Click on sections of Search page
	 * @param i
	 */
	public void clickOnSections(int i){
		log.info("Clicking on sections");
		getSections().get(i).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on the entity
	 * @param entity
	 * @return
	 */
	public void selectEntity(String entity){
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.CURRENCY_TABLE_CELL));
		for(WebElement wb : wbs){
			if(wb.getText().contains(entity)){
				wb.click();
				break;
			}
		}
	}
	
	/**
	 * Edit currency from the currency list
	 */
	public boolean isEditDisplayed(){
		log.info("Verify edit button");
		boolean edit = false;
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Edit")){
				wb.click();
				edit = true;
				break;
			}
		}
		return edit;
	}
	
	/**
	 * Click on Amend button
	 */
	public void clickOnAmend(){
		log.info("Click on Amend button");
		int count = 0;
		do{
			count=count+1;
			List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
			for(WebElement wb2 : wbs1){
				if(wb2.getText().equals("Amend")){
					wb2.click();
					break;
				}
			}
			WaitHelper.waitAdditional(2);
		}while(!(getTableHeader().contains("Edit")) && count !=3);
		
		WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Click on prompt button
	 */
	public void clickOnPrompt(){
		log.info("Click on prompt button");
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Prompt")){
						wb2.click();
						break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	/**
	 * Click on Update currency
	 */
	public void clickOnUpdate(){
		log.info("Clicking on update button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Update")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Update currency
	 */
	public void clickOnUpdateCompany(){
		log.info("Clicking on update button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		int i = 0;
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Update")){				
				if(i==1){
					wb2.click();
					break;
				}
				i = i+1;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Update warnings
	 */
	public void clickOnUpdateWarnings(){
		log.info("Clickin on update warnings");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Update Warnings")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on UpdatWarnings
	 */
	public void clickOnUpdatWarnings(){
		log.info("Clicking on updat warnings");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Updat Warnings")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on UpdatWarnings
	 */
	public void clickOnUpdtWarn(){
		log.info("Clicking on updt warn");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Updt Warn")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Updt warnings
	 */
	public void clickOnUpdtWarnings(){
		log.info("Clicking on Updt warnings method");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Updt Warnings")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on accept warnings
	 */
	public void clickOnAcceptWarnings(){
		log.info("Clicking on Accept warnings");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Accept Warnings")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	 /* Click on accept warnings
	 */
	public void clickOnAcceptWarn(){
		log.info("Clicking on Accept warn");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Accept Warn")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify Column Definition tab displayed
	 * @return true
	 */
	public boolean isColumnDefinitionDispayed(){
		WebElement columnDefinition = getDriver().findElement(By.id(pObject.XDJ_COLUMN_DEF));
		WaitHelper.waitUntilWebElementDisplayed(getDriver(), columnDefinition);
		return columnDefinition.isDisplayed();
	}
	
	/**
	 * Verify Definition defn tab displayed
	 * @return true
	 */
	public boolean isDefinitionDefnDispayed(){
		WebElement columnDefinition = getDriver().findElement(By.id(pObject.XDJ_DEFINITION_DEF));
		WaitHelper.waitUntilWebElementDisplayed(getDriver(), columnDefinition);
		return columnDefinition.isDisplayed();
	}
	
	/**
	 * Click on Column definition tab
	 */
	public void clickOnColumnDefinition(){
		getDriver().findElement(By.id(pObject.COLUMN_DEFINITION_LABEL)).click();
	}
	
	/**
	 * Click on Definition defn tab
	 */
	public void clickOnDefinitionDef(){
		getDriver().findElement(By.id(pObject.DEFINITION_DEF_LABEL)).click();
	}
	
	/**
	 * Verify Primary Details Tab displayed
	 * @return true
	 */
	public boolean isPrimaryDetailsTabDisplayed(){
		return getPrimaryDetailsTab().isDisplayed();
	}
	
	/**
	 * Click on Primary Details Tab
	 */
	public void clickOnPrimaryDetailsTab(){
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();
	}
	/**
	 * Click on Currency Intrastat Tab
	 */
	public void clickOnSecondTab(){
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
	}
	/**
	 * Verify Currency Intrastat Tab displayed
	 * @return true
	 */
	public boolean isSecondTabDisplayed(){
		return getSecondTab().isDisplayed();
	}
	
	/**
	 *Get primary details tab id 
	 */
	private WebElement getPrimaryDetailsTab(){
		return getDriver().findElement(By.id(pObject.FIRST_TAB));
	}
	
	/**
	 * Get second tab
	 * @return
	 */
	private WebElement getSecondTab(){
		return getDriver().findElement(By.id(pObject.SECOND_TAB));
	}
	
	/**
	 * Enter primary details of company
	 * @param companydetails
	 */
	public void enterPrimaryDetails(String companyName,List<String> companydetails){
		log.info("Enter preimary details");
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(companydetails.get(0));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(companydetails.get(1));//Name
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(companydetails.get(2));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(companydetails.get(3));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(companydetails.get(4));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(companydetails.get(5));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(companydetails.get(6));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(companydetails.get(7));//Address line
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(companydetails.get(8));//Post code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(companydetails.get(9));//Telephone
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(companydetails.get(10));//Fax
	}
	
	/**
	 * Enter Currency/EC Intrastat details of company
	 * @param companydetails
	 */
	public void enterCurrencyIntrastatDetails(List<String> companydetails){
		log.info("Enter currency intrastat details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(companydetails.get(11));//Country
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(companydetails.get(12));//Currency
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SIX)).click();//Emu flag
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.NINE)).click();//Currency flag
	}

		
	/**
	 * Enter device details
	 * @param deviceDetails
	 */
	public void enterDeviceDetails(List<String> deviceDetails){
		log.info("Enter device details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(deviceDetails.get(0));//Device name
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ZERO)).click();//Upper case check box
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(deviceDetails.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(deviceDetails.get(2));//Definition
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(deviceDetails.get(3));//Definition
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(deviceDetails.get(4));//Definition
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(deviceDetails.get(5));//Definition
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(deviceDetails.get(6));//Definition
	}
	
	/**
	 * Enter Distribution profile details 
	 * @param profileDetails
	 */
	public boolean enterDistributionProfileDetails(List<String> profileDetails){
		log.info("Enter distribution profiles details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(profileDetails.get(0));//D Profile
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//D Profile
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(profileDetails.get(1));//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(profileDetails.get(2));//Destination
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(profileDetails.get(3));//No.of copies
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();
			update = true;
		}
		return update;
	}
	
	/**
	 * Search currency
	 * @param currencyList
	 */
	public void searchCurrency(String currencyValue){
		log.info("search currency method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(currencyValue);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).click();
	}
	
	/**
	 * 
	 * @param user
	 * @param i = OK button
	 * @param j = testbox
	 */
	public void search(String user,int i,int j){
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+j)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+j)).sendKeys(user);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	public void searchOrder(String companyId,String user,int i){
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyId);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(user);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/*Search document */
	public void searchDocument(String companyId,String user,int i){
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyId);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(user);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Enter calender activities
	 * @param calenderActivity
	 * @param day
	 */
	public void createDayCalendar(List<String> calenderActivity,int day){
		log.info("Create day calendar");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(calenderActivity.get(0));//Activity
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(calenderActivity.get(1));//Desc
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(calenderActivity.get(2));//Type
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(calenderActivity.get(3));
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(calenderActivity.get(4));
		WaitHelper.waitAdditional(1);
		if(calenderActivity.get(5).equals("day")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(calenderActivity.get(6));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(calenderActivity.get(7));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(calenderActivity.get(8));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(calenderActivity.get(9));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(calenderActivity.get(10));
			WaitHelper.waitAdditional(1);
		}
	}
	
	public void enterAccountDefinitionDetails(String companyName,String accountDefinition ,List<String> accountCodeList,List<String> costList,List<String> location,List<String> product){
		log.info("Enter account definition details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(accountDefinition);//Nomina code
		WaitHelper.waitAdditional(2);
		enterAccountCodeDefinitionDetails(accountCodeList,1);
		enterAccountCodeDefinitionDetails(costList,2);
		enterAccountCodeDefinitionDetails(location,3);
		enterAccountCodeDefinitionDetails(product,4);
		WaitHelper.waitAdditional(4);
	}
	
	private void enterAccountCodeDefinitionDetails(List<String> elements,int i){
		log.info("Enter account code definition details");
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
		sendKeys(elements.get(0)).build().perform();//id
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click().
		sendKeys(elements.get(1)).build().perform();//Size
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click().
		sendKeys(elements.get(2)).build().perform();//Description
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).click().
		sendKeys(elements.get(3)).build().perform();//Rel
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[6]"))).click().
		sendKeys(elements.get(4)).build().perform();//Ind
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[7]"))).click().
		sendKeys(elements.get(5)).build().perform();//Chk
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[8]"))).click().
		sendKeys(elements.get(6)).build().perform();//Heading
		WaitHelper.waitAdditional(2);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[9]"))).click().
		sendKeys(elements.get(7)).build().perform();//Hd size
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Enter balance sheet control details
	 * @param balanceSheetList
	 */
	public void enterBalanceSheetControls(List<String> balanceSheetList){
		log.info("Enter balance sheet control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(balanceSheetList.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(balanceSheetList.get(1));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();
	}
	
	/**
	 * Enter balance sheet group details
	 * @param balancegroupList
	 */
	public boolean enterBalanceSheetGroup(List<String> balancegroupList){
		log.info("Enter Normal balance sheet group details");
		boolean update = false;
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(balancegroupList.get(0));//Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Group
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(balancegroupList.get(1));//Desc
			WaitHelper.waitAdditional(2);
			if(balancegroupList.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TEN)).click();//Profit and loss chk box
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Search balance value
	 * @param valueList
	 * @param i
	 * @param j
	 */
	public void searchBalanceClass(List<String> valueList,int i,int j){
		log.info("Search balance class");
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.id("pObject.ZERO_+pObject.ZERO"))).click()
		.sendKeys(valueList.get(0)).build().perform();
		
		if(j==1){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(1));
		}
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Search elements
	 * @param valueList
	 * @param i
	 * @param j
	 */
	public void searchValue(String valueList,int i,int j){
		log.info("Search values");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		
		if(j==0){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(valueList);
			WaitHelper.waitAdditional(1);
		}
		if(j==1){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList);
			WaitHelper.waitAdditional(1);
		}
		if(j==2){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList);
			WaitHelper.waitAdditional(1);
		}
		if(j==4){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(valueList);
			WaitHelper.waitAdditional(1);
		}
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Search value
	 * @param valueList
	 * @param i = button
	 * @param j = fields
	 */
	public void searchValue(String companyName,List<String> valueList,int i,int j){
		log.info("Search value");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
				
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);
		if(j==1){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
		}
		
		if(j==2){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
		}
		if(j==3){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
			if(!valueList.get(2).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(2));
			}
			WaitHelper.waitAdditional(1);
		}
		if(j==4){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();		
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(valueList.get(2));
			WaitHelper.waitAdditional(1);
		}
		if(j==5){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(2));
			WaitHelper.waitAdditional(1);
			if(!valueList.get(3).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(valueList.get(3));	
			}
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(valueList.get(4));
			WaitHelper.waitAdditional(1);
		}
		if(j==6){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
		}
		if(j==7){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(0));
		}
		if(j==8){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(0));
		}
		if(j==9){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(0));
		}
		if(j==10){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
		}
		if(j==11){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(0));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(valueList.get(2));
			WaitHelper.waitAdditional(1);
		}
	
			getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
			WaitHelper.waitAdditional(3);
	}
	
	public void searchAuthorisor(String companyName,List<String> elements,String orderNumber,int i){
		log.info("Search value");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//USer
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Structure
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(orderNumber);//Doc ref
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		
	}
	/**
	 * Search value
	 * @param valueList
	 * @param i = button
	 * @param j = fields
	 */
	public void searchValue(List<String> valueList,int i,int j){
		log.info("Search value");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
				
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(valueList.get(0));
		if(j==1){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
		}
		
		if(j==2){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(valueList.get(1));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(valueList.get(2));
			WaitHelper.waitAdditional(1);
		}
			getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
			WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Search structure
	 * @param companyName
	 * @param valueList
	 * @param i
	 */
	public void searchStructure(String companyName,List<String> valueList,int i){
		log.info("Search structure");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(companyName);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(valueList.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Search element
	 * @param companyName
	 * @param ledgerControl
	 * @param i
	 */
	public void searchElement(String companyName,List<String> ledgerControl,int i){
		log.info("Inside search element");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();	
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(companyName);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(ledgerControl.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Search nominal balance
	 * @param ledgerControl
	 * @param i
	 */
	public void searchElement(List<String> ledgerControl,int i){
		log.info("Inside search element");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();	
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(ledgerControl.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(ledgerControl.get(1));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Search calendar
	 * @param element
	 * @param i
	 */
	public void searchCalendar(List<String> element,int i){
		log.info("Inside search calendar");
		WaitHelper.waitAdditional(3);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(element.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject._FIRST)).click();
			
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Is ok button displayed
	 * @param i
	 * @return
	 */
	public boolean isOkButtonDisplayed(int i){
		log.info("Is ok button displayed");
		boolean displayed = false;
		WebElement wb = getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL));
		
		if(wb.isDisplayed()){
			displayed = true;
		}
		WaitHelper.waitAdditional(2);
		return displayed;
		
	}
	/**
	 * Enter balance sheet category details - A017
	 * @param balanceCategoryList
	 */
	public boolean enterBalanceSheetCategory(List<String> balanceCategoryList){
		log.info("Inside balance sheet category method");
		boolean update = false;
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(balanceCategoryList.get(0));//Version
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(balanceCategoryList.get(1));//Group
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(balanceCategoryList.get(2));//Category
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(Keys.ENTER);//Category
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(balanceCategoryList.get(3));//Desc
			WaitHelper.waitAdditional(4);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(balanceCategoryList.get(4));//Category type
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(balanceCategoryList.get(4));//Category type
			WaitHelper.waitAdditional(1);
			update = true;
		}
		return update;
	}
	
	/**
	 * Double click on row
	 * @param i
	 */
	public void doubleClick(int i){
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.id("0_"+i))).doubleClick().build().perform();
	}
	
	/**
	 * Click on delete and refresh
	 * @throws InterruptedException
	 */
	public void deleteAndRefresh() throws InterruptedException{
		clickOnPurge();
		clickOnUpdate();
		clickOnRefresh();
		WaitHelper.waitAdditional(2);
	}
	
	
	/**
	 * Create Nominal controls
	 */
	public boolean enterNominalControl(List<String> nominalList){
		log.info("Inside nominal control method");
		boolean update = false;
		if(!isPrimaryDetailsTabDisplayed()){
			clickOnPrimaryDetailsTab();
		}

		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(nominalList.get(0));//Nominal
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Nominal
		WaitHelper.waitAdditional(1);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(nominalList.get(1));//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(nominalList.get(2));//Group
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(nominalList.get(3));//Category
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(nominalList.get(4));//Nominal type
			WaitHelper.waitAdditional(3);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(nominalList.get(5));//Nominal posting type
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).sendKeys(nominalList.get(6));//Management Code Relationships
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();//Management Code Relationships
			WaitHelper.waitAdditional(1);
			if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.NINE)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.NINE)).click();//Financial Allowed
			}
			if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TEN)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TEN)).click();//Planning Allowed
			}
			if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.ONE)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.ONE)).click();//Cost allocation
			}
			WaitHelper.waitAdditional(2);
	
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Third tab
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.THREE)).sendKeys(nominalList.get(7));//Currency code
			WaitHelper.waitAdditional(3);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.FIVE)).sendKeys(nominalList.get(8));//Currency processing
			update  = true;
		}
		return update;
	}
		
	/**
	 * Create Management/Analysis code
	 */
	public boolean enterManagementDetails(List<String> managementList){
		log.info("Inside management details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(managementList.get(0));//Management code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Management code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(managementList.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(managementList.get(2));//Short Description
			WaitHelper.waitAdditional(1);
			if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();//Financial
			}
			if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Planning
			}
			if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.EIGHT)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.EIGHT)).click();//Cost allocation
			}
			if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.NINE)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.NINE)).click();//Sum
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Batch type
	 */
	public boolean enterBatchTypeDetails(List<String> batachTypeList){
		log.info("Inside batch type details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(batachTypeList.get(0));//Batch type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Batch type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(batachTypeList.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(batachTypeList.get(2));//Ledger Control Code:
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(batachTypeList.get(3));//Update Indicator
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).click();//Base
			if(batachTypeList.get(4).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();//Foreign
			}
			if(batachTypeList.get(5).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Base and Foreign
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Formula
	 */
	public boolean enterFormulaDetails(List<String> fomulaList){
		log.info("Inside Formula details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(fomulaList.get(0));//Formula
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Formula
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(fomulaList.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(fomulaList.get(2));//Heading
			if(fomulaList.get(3).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).click();//Apply currency
			}
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(fomulaList.get(4));//Totalling Control
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(3);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(fomulaList.get(5));//Formula expression
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Layout
	 */
	public void enterLayoutDetails(List<String> layoutList){
		log.info("Enter layout details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(layoutList.get(0));//Layout
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(layoutList.get(1));//Description
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(layoutList.get(2));//Total display
		WaitHelper.waitAdditional(2);
		enterFormulasForLayout(layoutList.get(3),1);
		enterFormulasForLayout(layoutList.get(4),2);
		enterFormulasForLayout(layoutList.get(5),3);
		enterFormulasForLayout(layoutList.get(6),4);
	}
	
	/**
	 * Enter fourmula for layout
	 * @param formula
	 * @param i
	 */
	private void enterFormulasForLayout(String formula,int i){
		log.info("Enter formula layout details");
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().sendKeys(formula).build().perform();
		
		WaitHelper.waitAdditional(8);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click().keyDown(Keys.CONTROL).sendKeys("<").build().perform();		
		WaitHelper.waitAdditional(5);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).doubleClick().build().perform();
		WaitHelper.waitAdditional(5);
		if(!getDriver().findElement(By.className("dijitDialogPaneContent")).isDisplayed()){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).doubleClick().build().perform();
		}
		driver.findElement(By.xpath("//tr[7]/td[3]/span/span/span")).click();
		driver.findElement(By.xpath("//tr[6]/td[3]/span/span/span")).click();

		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id("calcBtnUpdate_label")).click();
		WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Enter Destination details
	 * @param destinatioList
	 * @param i
	 */
	public void enterDestinationDetails(List<String> destinatioList,int i){
		log.info("Enter destination details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(destinatioList.get(0));//Destination
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(destinatioList.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX_+i)).click();//Device type - Spool only
	}
	
	/**
	 * Enter environment details - AFA
	 * @param destinatioList
	 * @param i
	 */
	public boolean enterEnvironmentDetails(List<String> destinatioList){
		log.info("Enter environment details page");
		boolean update = false;
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(destinatioList.get(0));//Environment group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(1);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(destinatioList.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(destinatioList.get(2));//Session time
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(destinatioList.get(3));//Password expiry
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(destinatioList.get(4));//Maximum sessions
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(destinatioList.get(6));//Profile
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO_+destinatioList.get(5))).click();//Indicator - Normal
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Currency Relationship
	 * @param currencyList
	 */
	public boolean enterCurrencyRelationshipDetails(List<String> currencyList){
		log.info("Enter currency relationship details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(currencyList.get(0));//Base currency
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(currencyList.get(1));//Non-base currency
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Non-base currency
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(currencyList.get(2));//Direction
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(currencyList.get(3));//Conversion Units
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(currencyList.get(4));//Tolerance %
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(currencyList.get(5));//Tolerance amount
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter currency exchange details
	 * @param currencyExchangeLis
	 */
	public boolean enterCurrencyExchangeDetails(List<String> currencyExchangeLis){
		log.info("Enter currency exchange details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(currencyExchangeLis.get(0));//Base currency
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(currencyExchangeLis.get(1));//Non base currency
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(currencyExchangeLis.get(2));//Rate type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			update = true;
		}
		return update;
	}

	/**
	 * Enter currency exchange rate details
	 * @param currencyExchangeLis
	 */
	public boolean enterCurrencyExchangeRateDetails(List<String> currencyExchangeLis){
		log.info("Enter currency exchange rate details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(currencyExchangeLis.get(0));//BC
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(currencyExchangeLis.get(1));//NBC
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(currencyExchangeLis.get(2));//Rate type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(Keys.ENTER);//Rate type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(currencyExchangeLis.get(3));//Exchange rate
			WaitHelper.waitAdditional(1);
			if(getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).getText().equals("1.00")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(currencyExchangeLis.get(4));	//Tolerance %
			}
			if(getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).getText().equals("1.00")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(currencyExchangeLis.get(5));	//Tolerance amount
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Structure details
	 * @param structureList
	 */
	public void enterStructureDetails(List<String> structureList){
		log.info("Enter structure details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(structureList.get(0));//structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(structureList.get(1));//Description
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Unique elements
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Balance class update
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		/*Balance class field*/
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[3]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[4]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[5]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[6]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[7]/table/tbody/tr/td[3]"))).click().sendKeys(structureList.get(2)).build().perform();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter default structure control details - A028
	 * @param controlList
	 */
	public void enterControlDetails(List<String> controlList){
		log.info("Enter control details");
		WaitHelper.waitAdditional(2);	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(controlList.get(0));//structure
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(controlList.get(1));//Path code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(controlList.get(2));//Suspense element 
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(controlList.get(3));//Update control	
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(controlList.get(4));//Net balance indicator
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(controlList.get(5));//Net balance layout
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(controlList.get(6));//Nominal balance layout
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Click on balance sheet
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys("\u0008").build().perform();//Remove balance sheet update value 
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(controlList.get(7)).build().perform();//Balance sheet update value
		WaitHelper.waitAdditional(3);
	}

	/**
	 * Enter ledger control list details
	 * @param ledgerControlList
	 */
	public void enterLedgerControlDetails(List<String> ledgerControlList){
		log.info("Enter ledger control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(ledgerControlList.get(0));//Ledger code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(ledgerControlList.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).sendKeys(ledgerControlList.get(2));//Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).sendKeys(ledgerControlList.get(3));//Cost
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(ledgerControlList.get(4));//Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).sendKeys(ledgerControlList.get(5));//Cost
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).sendKeys(ledgerControlList.get(6));//Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).sendKeys(ledgerControlList.get(7));//Cost
	}
	
	/**
	 * 
	 * @param command
	 * @param i = OK button id eg: 0_4_label and here i==4
	 * @param j
	 */
	public void enterCommandParameters(List<String> command,int i,int j){
		log.info("Entering command parameters");
		WaitHelper.waitAdditional(3);
		Actions builder = new Actions(driver);
		if(j==01){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().sendKeys(command.get(0)).build().perform();
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();	
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on financial module
	 */
	private void clickOnFinancialModule(){
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on purchasing module
	 */
	private void clickOnPurchasingModule(){
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify financial module
	 * @return
	 */
	private boolean verifyFinancialModule(){
		log.info("Verifying Financial module");
		WaitHelper.waitAdditional(2);
		boolean value = false;
		boolean generalLedger = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.FIVE)).isSelected();
		boolean projectTracking = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.SIX)).isSelected();
		boolean intraCompanyAccounting = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.SEVEN)).isSelected();
		boolean averageDaysBalance = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.NINE)).isSelected();
		boolean valueDatedAccounting = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.ONE)).isSelected();
		
		if(generalLedger==true && projectTracking==true)
		{
			value = true;
		}
		if(intraCompanyAccounting==true && averageDaysBalance==true && valueDatedAccounting==true){
			value = true;
		}
		
		return value;
	}
	
	/**
	 * Verify purchasing module
	 * @return
	 */
	private boolean verifyPurchasingModule(){
		log.info("Verifying purchasing module");
		WaitHelper.waitAdditional(2);
		boolean value = false;
		boolean accountsPayable = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ZERO)).isSelected();
		boolean cISEDI = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.TWO)).isSelected();
		boolean purchasingManagement = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.FOUR)).isSelected();
		boolean inventoryManagement = getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.FIVE)).isSelected();
		
		if(accountsPayable==true && cISEDI==true)
		{
			value = true;
		}
		if(purchasingManagement==true && inventoryManagement==true)
		{
			value = true;
		}
		return value;
	}
	
	/**
	 * Verify finance module
	 * @return
	 */
	public boolean verifyFinancialModules(){
		log.info("Verify Financial module");
		boolean value = false;
		clickOnFinancialModule();
		if(verifyFinancialModule() == true){
			value = true;
		}
		return value;
	}
	
	/**
	 * Verify purchasing module
	 * @return
	 */
	public boolean verifyPurchasingModules(){
		log.info("Verify purchasing module");
		boolean value = false;
		clickOnPurchasingModule();
		if(verifyPurchasingModule() == true){
			value = true;
		}
		return value;
	}
	
	/**
	 * Enter structure build details
	 * @param structureList
	 */
	public void enterStructureReBuildDetails(List<String> structureList,String companyName){
		log.info("Enter structure rebuild details");
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(structureList.get(1));//Request
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(companyName);//Company
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(Keys.ENTER);//Press Enter to view years
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.FOUR)).click();//Select years
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SIX)).click();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.EIGHT)).click();
		WaitHelper.waitAdditional(1);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")))
		.click().sendKeys(structureList.get(2)).build().perform();//Add structure
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+10+pObject.LABEL)).click();//Submit button
		
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify presence of About to Submit pop up
	 * @return
	 */
	private boolean isAboutSubmitPopUpDisplayed(){
		WaitHelper.waitAdditional(2);
		return getYesButton().isDisplayed();
	}
	
	/**
	 * 
	 * @param Submit details
	 */
	public void enterAboutsubmitDetails(){
		WaitHelper.waitAdditional(4);
		if(!isAboutSubmitPopUpDisplayed()){
			WaitHelper.waitAdditional(3);
		}
		getDriver().findElement(By.id(pObject.CHECK+pObject.TWO+pObject._+pObject.ONE)).click();//Hold
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TWO_+"2"+pObject.LABEL)).click();//Submit
		
		WaitHelper.waitAdditional(5);
	}
	
	public void submitDetails(int i){
		WaitHelper.waitAdditional(4);
		if(!isAboutSubmitPopUpDisplayed()){
			WaitHelper.waitAdditional(3);
		}
		if(i==1){
			getDriver().findElement(By.id(pObject.CHECK+pObject.TWO+pObject._+pObject.THREE)).click();//Hold
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TWO_+"0"+pObject.LABEL)).click();//Submit
		
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Enter security group structure details
	 * @param securityList
	 */
	public void enterSecurityGroupStructureDetails(List<String> securityList){
		log.info("Enter security structure details");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(securityList.get(1));
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(securityList.get(2));
	}
	
	/**
	 * Enter action commands
	 * @param parameters
	 */
	public void enterCommands(String parameters){
		log.info("Enter commands");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.LABEL)).sendKeys(parameters);//Enter currency code
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.LABEL)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Verify security group has access to e5h5 application
	 * @param parameters
	 * @return
	 */
	public void enterUserDetails(List<String> parameters){
		log.info("Enter user details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(parameters.get(3));//security element
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(parameters.get(3));//security element
		WaitHelper.waitAdditional(2);		
	}
	
	public boolean getUserDetails(List<String> parameters){
		log.info("Get user details");
		WaitHelper.waitAdditional(2);
		boolean userDetails = false;
		String securityGroup=" ";
		String reportSecurity=" ";

		securityGroup = getDriver().findElement(By.id("0_4")).getText();
		
		reportSecurity= getDriver().findElement(By.id("0_6")).getText();
		
		if(securityGroup.equals(parameters.get(4)) && reportSecurity.equals(parameters.get(4))){
			userDetails = true;
		}
		WaitHelper.waitAdditional(2);
		return userDetails;
		
	}
	
	/**
	 * Enter ledger batch details
	 * @param batchList
	 * @return
	 */
	public boolean enterLedgerBatchTypeDetails(List<String> batchList){
		log.info("Inside ledger batch type details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(batchList.get(0));//Batch type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Batch type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(batchList.get(1));//Batch description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(batchList.get(2));//Ledger code
			if(!batchList.get(3).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(batchList.get(3));//Update indicator
			}
			if(batchList.get(4).equals("1")){
				if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Secondary index
				}
			}
			if(batchList.get(5).equals("1")){
				if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).click();//Base values
				}
			}
			if(batchList.get(6).equals("1")){
				if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();//Foreign Values
				}
			}
			if(batchList.get(7).equals("1")){
				if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Base and Foreign values
				}
			}
			WaitHelper.waitAdditional(2);
			if(!batchList.get(8).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(batchList.get(8));//Accruals
			}
			WaitHelper.waitAdditional(2);
			if(!batchList.get(9).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(batchList.get(9));//Recurrals
			}
			WaitHelper.waitAdditional(1);
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter default structure element details
	 * @param elementList
	 * @return
	 */
	public boolean enterElementDetails(List<String> elementList){
		log.info("Inside default structure elements method");
		boolean update = false;
		
		if(elementList.get(1).equals("SUSP")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elementList.get(2));//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elementList.get(3));//New Parent
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.ZERO)).click();//Nominal balance
			WaitHelper.waitAdditional(2);
			update = true;
		}
		
		else{
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elementList.get(0));//Structure
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(2);		
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elementList.get(1));//Element
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Element
			WaitHelper.waitAdditional(2);
				
			if(!getToolContentText().contains(message)){
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
				WaitHelper.waitAdditional(2);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elementList.get(2));//Description
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
				WaitHelper.waitAdditional(2);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elementList.get(3));//New Parent
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.ZERO)).click();//Nominal balance
				WaitHelper.waitAdditional(2);
				update = true;
			}
		}
		return update;
	}
	
	/**
	 * Click on path key
	 * @param i
	 */
	public void clickOnPathKey(){
		log.info("Click on path key");
		WaitHelper.waitAdditional(3);
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Path")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Search path key
	 * @param pathList
	 * @param i
	 */
	public void searchPathKey(List<String> pathList,int i){
		log.info("Search path key details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(pathList.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(pathList.get(1));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(pathList.get(2));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(pathList.get(3));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL));
		WaitHelper.waitAdditional(2);		
	}
	
	public void createPathKey(List<String> pathList){
		log.info("Enter path key details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).sendKeys(pathList.get(2));///Path key
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(pathList.get(3));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(pathList.get(4));//New Parent
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on Return button
	 * @param i
	 */
	public void clickOnReturnButton(int i){
		log.info("Click on return button");
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(5);
	}	
	
	/**
	 * Click on Return button
	 */
	public void clickOnReturnButton(){
		WaitHelper.waitAdditional(2);
		log.info("Clicking on Return button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Return")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(4);
	}

	/**
	 * Verify company control check list
	 * @param companyControl
	 * @return
	 */
	public boolean verifyCompanyControl(List<String> companyControl){
		log.info("Verify company control details");
		boolean control = false;
		boolean accLayout = false;
		boolean bSPLLayout = false;
		boolean icaStructure = false;
		boolean transfer = false;
		boolean reversal = false;
		WaitHelper.waitAdditional(3);
		
		if(isPrimaryDetailsTabDisplayed() == false){
			clickOnPrimaryDetailsTab();
		}
		
		String acc = getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).getText();
		String bspl = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).getText();
		String structure = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).getText();
		
		
		if(acc.equals(companyControl.get(6))){
			accLayout = true;
		}
		if(bspl.equals(companyControl.get(7))){
			bSPLLayout = true;
		}
		if(structure.equals(companyControl.get(8))){
			icaStructure = true;
		}
		
		clickOnSecondTab();
		WaitHelper.waitAdditional(3);
		
		String three = getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).getText();
		String four = getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SEVEN)).getText();
				
		if(isSecondTabDisplayed() == false){
			clickOnSecondTab();
		}
				
		if(three.equals(companyControl.get(9))){
			transfer = true;
		}
		if(four.equals(companyControl.get(10))){
			reversal = true;
		}
		
		if(accLayout == true && bSPLLayout == true && icaStructure == true && transfer==true && reversal==true){
			control = true;
		}
		
		WaitHelper.waitAdditional(3);
		return control;	
	}
	
	public void amendUsersDetails(String companyName){
		log.info("Inside user detail page");
		WaitHelper.waitUntilWebElementDisplayed(getDriver(), getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)));
		if(!isPrimaryDetailsTabDisplayed())
		{
			clickOnPrimaryDetailsTab();
		}
		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(companyName);//Default company
		WaitHelper.waitAdditional(1);
	}
	
	
	/*
	 * Enter user details
	 * @param userDetails
	 */
	public void enterUsersDetails(String companyName,List<String> userDetails){
		log.info("Inside user detail page");
		WaitHelper.waitUntilWebElementDisplayed(getDriver(), getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)));
		if(!isPrimaryDetailsTabDisplayed())
		{
			clickOnPrimaryDetailsTab();
		}
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(userDetails.get(0));//User
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//User
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(userDetails.get(1));//Description
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(userDetails.get(2));//Menu
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(companyName);//Default company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(userDetails.get(3));//Password
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(userDetails.get(4));//Non-Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(userDetails.get(5));//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(userDetails.get(6));//Report
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(userDetails.get(7));//Report Company
		
		WaitHelper.waitAdditional(3);
		clickOnSecondTab();
		if(!isSecondTabDisplayed()){
			clickOnSecondTab();
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ONE)).sendKeys(userDetails.get(8));//Environment Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.THREE)).sendKeys(userDetails.get(9));//EWS Partition
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FIVE)).sendKeys(userDetails.get(10));//Language
		WaitHelper.waitAdditional(1);
	}
	
	/**
	 * Enter balance class details - A022
	 * @param balanceList
	 */
	public boolean enterBalanceClass(List<String> balanceList){
		log.info("Inside balance class method");
		boolean update = false;
		WaitHelper.waitAdditional(3);
		if(!isPrimaryDetailsTabDisplayed()){
			clickOnPrimaryDetailsTab();
		}
		
		String[] element  = balanceList.get(0).split(",");
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(element[0]);//Balance class
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(element[1]);//Balance class
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Balance class
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(balanceList.get(1));//Description
			
			if(balanceList.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Statistical
			}
			if(balanceList.get(3).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Transaction
			}
			if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Roll Flag
			}
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(balanceList.get(5));//Calendar Type
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(balanceList.get(6));//Path Code
			
			WaitHelper.waitAdditional(3);
			clickOnSecondTab();
			if(!isSecondTabDisplayed()){
				clickOnSecondTab();
			}
			WaitHelper.waitAdditional(1);
			if(balanceList.get(7).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TEN)).click();// Foreign Currency
			}
			WaitHelper.waitAdditional(1);
			if(balanceList.get(8).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.ONE)).click();//VAT//chk_0_11
			}
			WaitHelper.waitAdditional(1);
			if(balanceList.get(9).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.TWO)).click();//Allow Batches Out Of Balance
			}
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(balanceList.get(10));//Average Daily Balances
			WaitHelper.waitAdditional(2);
			update = true;
		}
		return update;
	}
	
	/**
	 *Enter company controls - A023 
	 * @param companyControl
	 */
	public void enterCompanyControlDetails(String companyName,List<String> companyControl){
		log.info("Enter company control details");
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(companyControl.get(0));//Period
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(companyControl.get(1));//Year
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(companyControl.get(2));//Control account code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(companyControl.get(3));//Calender
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(companyControl.get(4));//Future
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(companyControl.get(5));//History
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Batch Types
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(companyControl.get(6));//Summ
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Account Controls
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FOUR)).sendKeys(companyControl.get(7));	//Tran
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).sendKeys(companyControl.get(8));//Stat balance
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("chk_0_40")).click();//Financial
		
		getDriver().findElement(By.id("chk_0_41")).click();//Summarise
		
		getDriver().findElement(By.id("chk_0_42")).click();//Cost allocation
		
		getDriver().findElement(By.id("chk_0_43")).click();//Planning
		
		getDriver().findElement(By.id("chk_0_44")).click();//Average balances
		
		getDriver().findElement(By.id("chk_0_45")).click();//Alt balances
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Data netry Controls
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("0_54")).sendKeys(companyControl.get(9));//Suspense account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_75")).sendKeys(companyControl.get(10));//Dynamic bal sheet
		
		getDriver().findElement(By.id("chk_0_59")).click();//Accept warnings off line
		
		getDriver().findElement(By.id("chk_0_62")).click();//Totaling on quantity
		
		getDriver().findElement(By.id("chk_0_66")).click();//Generate batch balancing records
		
		getDriver().findElement(By.id("chk_0_67")).click();//Close account for last year
		
		getDriver().findElement(By.id("chk_0_69")).click();//Revolution write off flag
		
		getDriver().findElement(By.id("chk_0_70")).click();//Data entry immidiate update
		
		getDriver().findElement(By.id("chk_0_72")).click();//Reconcilation message
		
		getDriver().findElement(By.id("chk_0_74")).click();//Delete check
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Currency Control
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("0_76")).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_76")).sendKeys(companyControl.get(11));//Currency rate type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_78")).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_78")).sendKeys(companyControl.get(12));//Rounding currency
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_79")).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_79")).sendKeys(companyControl.get(13));//Rounding tolerance %
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("chk_0_87")).click();//Currency revaluation allowed
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_86")).sendKeys(companyControl.get(14));//Currency tolerence processing
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id("0_88")).sendKeys(companyControl.get(15));//Batch type
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SIXTH_TAB)).click();//Reconcillation
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("chk_0_90")).click();
		
		getDriver().findElement(By.id("chk_0_91")).click();//Archive
		
		getDriver().findElement(By.id("chk_0_92")).click();//Archive
		
		getDriver().findElement(By.id("chk_0_93")).click();
		
		getDriver().findElement(By.id("chk_0_95")).click();
		
		getDriver().findElement(By.id("chk_0_96")).click();
		
		getDriver().findElement(By.id("chk_0_97")).click();
		
		getDriver().findElement(By.id("chk_0_98")).click();
		WaitHelper.waitAdditional(1);
		
	}
	
	/**
	 * Update company controls A031
	 * @param companyControl
	 */
	public void updateCompanyControlDetails(List<String> companyControl){
		log.info("Update company controls");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(companyControl.get(0));//Account Layout
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(companyControl.get(1));//BPSL Layout
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(companyControl.get(2));//Structure
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(companyControl.get(3));//Code id
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Batch Types
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(companyControl.get(4));//Transfer
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).sendKeys(companyControl.get(5));//Reversal
		WaitHelper.waitAdditional(2);
		clickOnUpdate();
		WaitHelper.waitAdditional(2);
	}
	/**
	 * Enter BTZ ICA elements details - A036
	 * @param elements
	 */
	public boolean enterICAElements(List<String> elements){
		log.info("Inside ICA elements method");
		boolean update = false;
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Element
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);		
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//ICA management code
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//General ledger
			WaitHelper.waitAdditional(1);
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter ICA relationship details - A037
	 * @param elments
	 */
	public void enterICARelationShip(List<String> elments){
		log.info("Enter ICA relationship");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elments.get(0));//From BTZ entity
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elments.get(1));//To BTZ entity
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).sendKeys(elments.get(2));//General Ledger Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).sendKeys(elments.get(3));//Cost
		WaitHelper.waitAdditional(1);
	}
	
	/**
	 * Enter Process EP2 details A038
	 * @param processDetails
	 */
	public void enterEP2ProcessDetails(List<String> processDetails,String companyName){
		log.info("Enter EP2 process details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(processDetails.get(1));//Request
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company ID
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE)).click();//Update all
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Net balance
		WaitHelper.waitAdditional(5);
		Actions builder = new Actions(driver);
		
		/*Balance class field*/
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(processDetails.get(2)).build().perform();

		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+21+pObject.LABEL)).click();//Submit button
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.ZERO_+21+pObject.LABEL)).click();//Submit button
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter Process EP4 details - A041
	 * @param processDetails
	 */
	public void enterEP4ProcessDetails(String processDetails){
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(processDetails);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(3);
		clickOnSubmit();
	}
	
	/**
	 * Click on submit button
	 */
	public void clickOnSubmit(){
		log.info("Clicking on Years button");
		WaitHelper.waitAdditional(2);		
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Submit")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	/**
	 * Enter Process EP5 details - A041
	 * @param processDetails
	 */
	public void enterEP5ProcessDetails(List<String> processDetails,String companyName){
		log.info("Enter EP5 process details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(processDetails.get(3));//Request
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company ID
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE)).click();//Update all
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(3);
		clickOnSubmit();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter balance sheet details - A039 	
	 * @param group
	 */
	public void balanceSheetDetails(List<String> group){
		log.info("Enter balance sheet details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(group.get(0));//Enter Label
		
		clickOnSections(1);		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).sendKeys(group.get(1));//Enter Group
		WaitHelper.waitAdditional(1);
//		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
//		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(group.get(1));//Enter catg
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.FOUR+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(2);
	}

	/**
	 * Verify balance sheet - A039
	 * @param group
	 */
	public void verifyBalanceSheetDetails(List<String> group){
		log.info("Verify balance sheet details");
		 List<WebElement> wbs = driver.findElements(By.xpath("//div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"));
		 for(WebElement wb : wbs){
			 wb.getText().contains(group.get(1));
		 }
		
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[1]")).getText();//gets Group
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[2]")).getText();//gets Cat
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[3]")).getText();//gets Description
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[4]")).getText();//gets CP actl Balance
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[5]")).getText();//gets YTD actl Balance
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter fiscal calendar details
	 * @param calender
	 * @param year1
	 * @param year2
	 * @param year3
	 * @param year4
	 * @param year5
	 */
	public void enterFiscalCalendarDetails(List<String> calender,List<String> year1,List<String> year2,List<String> year3,List<String> year4,List<String> year5,List<String> year6,List<String> year7,List<String> year8
			,List<String> year9,List<String> year10,List<String> year11,List<String> year12,List<String> year13,List<String> year14,List<String> year15,List<String> year16,List<String> year17,List<String> year18,List<String> year19
			,List<String> year20){
		log.info("Enter fiscal calender details");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(calender.get(0));//Calender
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(calender.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(calender.get(2));//Company
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(calender.get(3));//Week end day
		
		WaitHelper.waitAdditional(5);
		Actions builder = new Actions(driver);
		WaitHelper.waitAdditional(8);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
		sendKeys(calender.get(4)).build().perform();//Level Name
		WaitHelper.waitAdditional(5);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
		sendKeys(calender.get(5)).build().perform();//Level description
		WaitHelper.waitAdditional(5);
		clickOnYears();
		clickOnYears();
		WaitHelper.waitAdditional(5);
		/*Enter fiscal years*/
		enterFiscalYears(year1,1);
		enterFiscalYears(year2,2);
		enterFiscalYears(year3,3);
		enterFiscalYears(year4,4);
		enterFiscalYears(year5,5);

		enterFiscalYears(year6,6);
		enterFiscalYears(year7,7);
		enterFiscalYears(year8,8);
		enterFiscalYears(year9,9);
		enterFiscalYears(year10,10);
		
		enterFiscalYears(year11,11);
		enterFiscalYears(year12,12);
		enterFiscalYears(year13,13);
		enterFiscalYears(year14,14);
		enterFiscalYears(year15,15);
		
		getDriver().findElement(By.id("0_4_gridScrollPane")).click();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("0_4_gridScrollPane")).click();
		WaitHelper.waitAdditional(5);
		
		
		
		enterFiscalYears(year16,2);
		enterFiscalYears(year17,3);
		enterFiscalYears(year18,4);
		enterFiscalYears(year19,5);
		enterFiscalYears(year20,6);
		
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[5]/table/tbody/tr/td[5]"))).click().
		sendKeys(Keys.ENTER).build().perform();//Press Enter to get days
		WaitHelper.waitAdditional(4);
		clickOnEventsH();
		getCalendarEvents("01 Dec 1999",calender.get(6));
		WaitHelper.waitAdditional(5);
	}
	
	
	/**
	 * Click on Years H button
	 */
	public void clickOnYearsH(){
		WaitHelper.waitAdditional(2);
		log.info("Clicking on Years button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Years (H)")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Run activity
	 */
	public void clickOnRunActivity(){
		WaitHelper.waitAdditional(2);
		log.info("Clicking on Run activity button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Run Activity")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on years
	 */
	public void clickOnYears(){
		WaitHelper.waitAdditional(2);
		log.info("Clicking on Years button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Years")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Events H
	 */
	public void clickOnEventsH(){
		WaitHelper.waitAdditional(3);
		log.info("Clicking on Years button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Events (H)")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Activity
	 */
	public void clickOnActivity(){
		WaitHelper.waitAdditional(3);
		log.info("Clicking on Years button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Activity")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	/**
	 * Enter fiscal years in the grid
	 * @param years
	 * @param i
	 */
	private void enterFiscalYears(List<String> years,int i){
		log.info("Enter fiscal years");
		WaitHelper.waitAdditional(4);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
		sendKeys(years.get(0)).build().perform();//1996
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click().
		sendKeys(years.get(1)).build().perform();//Name
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click().
		sendKeys(years.get(2)).build().perform();//Start
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).click().
		sendKeys(years.get(3)).build().perform();//End
		WaitHelper.waitAdditional(5);
		if(i==15){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).click().
			sendKeys(Keys.ENTER).build().perform();//End
			WaitHelper.waitAdditional(5);
		}
	}
	
	/**
	 * Get calendar events
	 * @param date
	 * @param calender
	 */
	public void getCalendarEvents(String date,String calender){
		log.info("Get calendar"); 
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(date);//Enter date
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(5);
		clickOnActivity();
		WaitHelper.waitAdditional(2);

		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id("4_0")).sendKeys(calender);
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("4_0")).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		clickOnActivity();
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on lock calendar
	 */
	public void lockCalendar(){
		log.info("Click on lock Calendar");
		WaitHelper.waitAdditional(3);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[7]"))).click().
		sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[7]"))).click().
		sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[3]/table/tbody/tr/td[7]"))).click().
		sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[4]/table/tbody/tr/td[7]"))).click().
		sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[5]/table/tbody/tr/td[7]"))).click().
		sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(1);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[5]/table/tbody/tr/td[7]"))).click().
		sendKeys(Keys.ENTER).build().perform();
		WaitHelper.waitAdditional(1);
	}
	
	public void enterPeriodAndYearDetails(String element){
		if(!isOkButtonDisplayed(5)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(element);//Company
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on GL period
	 */
	public void clickOnGLPeriod(){
		log.info("Click on GL period");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Close GL Period")){
						wb2.click();
						break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on period of the year
	 * @return
	 */
	public String periodOfTheYear(){
		log.info("Get period of the year");
		String period = null;
		WaitHelper.waitAdditional(2);
		period = driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText();//Period	
		return period;
	}
	
	public int getProcessorCount(){
		log.info("Get process count");
		int dspatcherCnt = 0;
		try {
			dspatcherCnt = dbQuery.getProcessCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SeleniumDaoException e) {
			e.printStackTrace();
		}
		log.info("dispatcherCount : " +dspatcherCnt);
		WaitHelper.waitAdditional(2);
		return dspatcherCnt;
	}
	
	public void updateHoldFlag(){
		log.info("Update Hold flag");
		try {
			dbQuery.updateHoldFlag();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SeleniumDaoException e) {
			e.printStackTrace();
		}
		WaitHelper.waitAdditional(2);
		log.info("Preocess is updated.");
	}
	
	public String getProcessDetails(String process,String request){
		log.info("Get process details");
		WaitHelper.waitAdditional(2);
		String stat = null;
		try {
			stat = dbQuery.getStatProcess(process,request);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SeleniumDaoException e) {
			e.printStackTrace();
		}
		log.info("stat value :" +stat);
		WaitHelper.waitAdditional(2);
		return stat;
	}
	
	public void updateProcess(String process,String request){
		log.info("Update process");
		try {
			dbQuery.updateProcess(process,request);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SeleniumDaoException e) {
			e.printStackTrace();
		}
		WaitHelper.waitAdditional(3);
		log.info("Preocess is updated.");
	}
	
	public void testing(){
		log.info("Testing");
		try {
			dbQuery.checkProcess();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SeleniumDaoException e) {
			e.printStackTrace();
		}
		WaitHelper.waitAdditional(3);
		log.info("Preocess is updated.");
		
	}

	/**
	 * EDB
	 * Enter Journal details 
	 * @param details
	 */
	public void enterJournalDetails(List<String> details){
		log.info("Enter journal details");
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(details.get(0));//J Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(details.get(1));//J Reference
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(details.get(2));//BTZ Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(details.get(3));//Description
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(details.get(4));//No.of transc
		WaitHelper.waitAdditional(2);
		clickOnAcceptWarnings();
		WaitHelper.waitAdditional(2);
		clickOnLines();
		WaitHelper.waitAdditional(3);
		enterJournalLines(details);
	}

	/**
	 * Enter journal lines
	 * @param lines
	 */
	private void enterJournalLines(List<String> lines){
		log.info("Enter journal lines");
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
		sendKeys(lines.get(5)).build().perform();//Account
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
		sendKeys(lines.get(6)).build().perform();//Cost
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[8]"))).click().
		sendKeys(lines.get(7)).build().perform();//Base Value
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[3]"))).click().
		sendKeys(lines.get(8)).build().perform();//Account
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[4]"))).click().
		sendKeys(lines.get(9)).build().perform();//Cost
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[8]"))).click().
		sendKeys(lines.get(10)).build().perform();//Base Value
		WaitHelper.waitAdditional(3);		
	}
	
	/**
	 * Click on Lines button
	 */
	public void clickOnLines(){
		log.info("Click on lines");
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Lines")){
						wb2.click();
						break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**EJJ
	 * Structure Enquiry
	 * @param group
	 */
	public void structureEnquiry(List<String> group){
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(group.get(0));//Layout
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(group.get(1));//Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(group.get(2));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(group.get(3));//Group
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(group.get(4));//Enter Category
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Verify balance sheet details
	 * @param group
	 */
	public void verifyBalanceSheetDetail(List<String> group){
		log.info("Verify balance sheet details");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(group.get(0));//Layout
		
		clickOnSections(1);	//Click on sections
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).sendKeys(group.get(1));//Group
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(group.get(2));//Category
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.FOUR+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(4);	
	}
	
	/**
	 * Navigate to account details page
	 * @param account
	 */
	public void navigateToAccountDetailPage(){
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[2]"))).doubleClick().
		build().perform();//Category
		WaitHelper.waitAdditional(3);
	}
	
	public void getAccountDetailValues(String account){
		log.info("Verify account detail values");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject._ZERO)).sendKeys(account);//Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);		
	}
	
	public void getCostDetailValues(String account){
		log.info("Verify account detail values");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject._ZERO)).sendKeys(account);//Cost
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);		
	}
	
	
	public void searchAccount(String account){
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("0_1_0")).sendKeys(account);
		getDriver().findElement(By.id("0_1_0")).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Search management code
	 * @param element
	 * @param i
	 */
	public void searchManagementCode(String element,int i){
		log.info("Search management code");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(element);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Create management link
	 * @param element
	 */
	public void createManagementLink(String element){
		log.info("Create management link");
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(element);//New Parent
			WaitHelper.waitAdditional(4);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(Keys.ENTER);
			WaitHelper.waitAdditional(4);
	}
	
	/**
	 * Search value
	 * @param ValueList
	 * @param i = button
	 * @param j = fields
	 */
	public void searchEement(String companyName,int i){
		log.info("Search elemnt");
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
				
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName.subSequence(0, 1));
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName.subSequence(1, 2));
		
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
		
	/**
	 * Start and stop dispatcher
	 * @throws InterruptedException
	 */
	public void startAndStopDispatcher(String region) throws InterruptedException{
		search(region, 3, 2);
		
//		clickOnStop();
//		
//		clickOnStopDispatcher();
//		
//		clickOnRefresh();
//		
//		clickOnRefresh();
		
		clickOnForce();
		
		clickOnForceStartDispatcher();
		
		clickOnRefresh();
		
	}
	
	/**
	 * Click on Stop button
	 */
	public void clickOnStop(){
		log.info("Click on Stop dispatcher");
		WaitHelper.waitAdditional(2);

		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Stop")){
						wb2.click();
						break;
			}
		}
		WaitHelper.waitAdditional(3);
	}

	
	public void clickOnStopDispatcher(){
		log.info("Click on Stop dispatcher");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ONE+pObject._+pObject.ZERO+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on Force button
	 */
	
	public void clickOnForce(){
		WaitHelper.waitAdditional(3);

		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Force")){
						wb2.click();
						break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	public void clickOnForceStartDispatcher(){
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.TWO+pObject._+pObject.ZERO+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	/*--------------------------------PHASE II METHODS----------------------------------------------------------------*/
	
	/**
	 * Create tax type - A043
	 * @param taxList
	 */
	public boolean createTaxType(List<String> taxList){
		log.info("In Tax code type method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(taxList.get(0));//Type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(taxList.get(1));//Desc
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(taxList.get(2));//Percentage
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(taxList.get(3));//Amount
			if(taxList.get(4).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Tax rate chk
			}
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}

	/**
	 * Create Locations for Tax code created - A043
	 * @param elments
	 */
	public boolean createTaxCodeLocation(List<String> elements){
		log.info("In Tax code location method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Desc
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Location
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//Type
			WaitHelper.waitAdditional(1);
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Tax Rate - A044
	 * @param elments
	 */
	public boolean createTaxRate(List<String> elements){
		log.info("In Tax rate  method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Date
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Location
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(4));//Tax rate
		clickOnUpdate();
		
		if(!getToolContentText().contains(message)){			
			update = true;
		}
		return update;
	}
	
	/**
	 * BACS Calendar - A045
	 * @param elements
	 */
	public void enterBASCCalendarDetails(List<String> elements){
		log.info("In BACS calendar method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Calendar
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Week end day
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();
		WaitHelper.waitAdditional(3);
		clickOnYearsH();
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(3));//Year
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * BACS Calendar - A045
	 * @param elements
	 */
	public void runActivityForCalendar(List<String> elements){
		log.info("In BACS calendar method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Calendar to populate
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(4));//Activity to use
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(5));//From Year
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(6));//To Year
		WaitHelper.waitAdditional(2);
	}
	
	/**Create bank sort code - A046
	 * 
	 * @param elements
	 */
	public boolean bankSortCode(List<String> elements){
		log.info("In Bank sort code  method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Country
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Sort code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Sort code
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Bank name
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Ad L1
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(4));//Ad L2
			WaitHelper.waitAdditional(1);
			if(!elements.get(5).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(5));//Ad L2
				WaitHelper.waitAdditional(1);
			}
			WaitHelper.waitAdditional(1);
			if(!elements.get(6).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(6));//Postal code
				WaitHelper.waitAdditional(1);
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create UOM - A048
	 * @param elements
	 */
	public boolean enterUomDetails(List<String> elements){
		log.info("In Uom Details  method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//UOM Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//UOM Code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//UOM Description
			WaitHelper.waitAdditional(1);
			if(elements.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Sub-Division
			}
			update = true;
		}
		
		return update;
	}
	
	/**
	 * Create UOM Relationship - A048
	 * @param elements
	 */
	public boolean enterUOMRelationShip(List<String> elements){
		log.info("In Uom Relationship Details  method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Base UOM Code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Non Base UOM Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Non Base UOM Code
		WaitHelper.waitAdditional(1);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();//
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Non-Base to Base Factor
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();//
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//Non-Base to Base Factor
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter Purchasing company control details - A049
	 * @param elements
	 */
	public void enterPurchasingCompanyControlDetails(String companyName,List<String> elements){
		log.info("Enter purchasing company control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();//Company
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		
		//GL - Numbering 
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Foreign exchangerate
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(companyName);//GL Holding company
		WaitHelper.waitAdditional(2);
		if(elements.get(1).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._ZERO)).click();//GL Relationship indicator
		}
		WaitHelper.waitAdditional(1);
		//Click On Miscellaneous/ERS
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).sendKeys(elements.get(2));//Days UOM
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT)).sendKeys(elements.get(3));//Working days calender
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX+pObject.SEVEN)).click();//Keyword in use
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX+pObject.NINE)).click();//NSV in use
		WaitHelper.waitAdditional(1);
		//Click on Supplier tab
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO+pObject._ZERO)).click();//Duplicate Postcode Validation - Nor-req
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN+pObject._FIRST)).click();//Supplier Postcode Enquiry - All supplier types
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT+pObject._ZERO)).click();//PM Multiple Supplier Address notification
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE+pObject._ZERO)).click();//AP Multiple Supplier Address notification
	}
	
	/**
	 * Amend batch details A051A
	 */
	public void editBatchTypes(){
		log.info("Inside batch type edit method");
		WaitHelper.waitAdditional(2);		
		clickOnRefresh();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.TWO)).click();//Reconciliation required
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.THREE)).click();//Bank account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.FOUR)).click();//Reconciliation required
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.FIVE)).click();//Bank account
	}
	
	/**
	 * Enter Account payable control details -AO50
	 * @param elements
	 */
	public void enterAccountPayableControlDetails(List<String> elements){
		log.info("Enter account payable control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Control account Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(2));//Creditors control Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).sendKeys(elements.get(3));//Creditors control Cost
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(elements.get(4));//Discount receivable Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).sendKeys(elements.get(5));//Discount receivable Cost
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).sendKeys(elements.get(6));//Bank charges Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._FIRST)).sendKeys(elements.get(7));//Bank charges Cost
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).sendKeys(elements.get(8));//Tax suspense Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._FIRST)).sendKeys(elements.get(9));//Tax suspense Cost
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).sendKeys(elements.get(10));//Pre-payments Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._FIRST)).sendKeys(elements.get(11));//Pre-payments Cost
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).sendKeys(elements.get(12));//Detail variance Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._FIRST)).sendKeys(elements.get(13));//Detail variance Cost
		
		//Control accounts tab2
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._ZERO)).sendKeys(elements.get(14));//Service ledger accurals Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._FIRST)).sendKeys(elements.get(15));//Service ledger accurals Cost
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._ZERO)).sendKeys(elements.get(16));//Service ledger deferrals Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._FIRST)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._FIRST)).sendKeys(elements.get(17));//Service ledger deferrals Cost
		WaitHelper.waitAdditional(1);
	}
	
	/**
	 * Create BR company - A051A
	 * @param elements
	 */
	public void createBRCompanyControl(String companyName,List<String> elements){
		log.info("Create BR company control");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Bank stmt
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Bank adj
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//Bank stmt
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(3));//Bank adj
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(4));//GL adj
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._FIRST)).click();//Origional date
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(5));//cheques
	}
	
	/**
	 * Create BR Bank code - A051
	 * @param elements
	 */	
	public boolean enterBRBankCodeList(List<String> elements){
		log.info("Enter BR bank code list");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Bank code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(1);
		if(!getToolContentText().contains(message)){
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Closing Balance
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Statement Date
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(4));//Bank sort code
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(5));//Account number
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).sendKeys(elements.get(6));//GL - Account Account
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._FIRST)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._FIRST)).sendKeys(elements.get(7));//GL - Account Cost
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(8));//BTZ Element
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(9));//Currency
			
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Currency tab
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(10));//User
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(Keys.ENTER);//User
			update = true;
		}

		return update;
	}
	
	/**
	 * Enter Tax account details - A052
	 * @param elements
	 * @param elements1
	 * @param elements2
	 * @param elements3
	 */
	public void enterTaxAccountDetails(List<String> elements,List<String> elements1,List<String> elements2,List<String> elements3){
		log.info("Enter tax account details");
		enterTaxAccountByCode(elements,1);
		enterTaxAccountByCode(elements1,2);
		enterTaxAccountByCode(elements2,3);
		enterTaxAccountByCode(elements3,4);
	}
	
	private void enterTaxAccountByCode(List<String> elemnts,int i){
		log.info("Enter tax account details");
		WaitHelper.waitAdditional(3);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
		sendKeys(elemnts.get(0)).build().perform();//Code
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click().
		sendKeys(elemnts.get(1)).build().perform();//Account
		WaitHelper.waitAdditional(2);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click().
		sendKeys(elemnts.get(2)).build().perform();//Cost
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter Category code details - A053
	 * @param elements
	 */
	public void enterCategoryCodeDetails(List<String> elements){
		log.info("Enter category code details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Category
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Hold days
		WaitHelper.waitAdditional(2);
		if(elements.get(3).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Auto supplier Cleardown
		}
	}
	
	/**
	 * Enter Discount terms - A054
	 * @param elements
	 */
	public void enterDiscountTerms(List<String> elements){
		log.info("Enter discount terms");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Discount term
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(2);
		if(elements.get(2).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Period details
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(3));//No.of days
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Rate %
		WaitHelper.waitAdditional(2);
		if(elements.get(5).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).click();//Invoice date
			
		}
	}
	
	/**
	 * Enter Settlement Terms - A055
	 * @param elements
	 */
	public boolean enterSettlementTerms(List<String> elements){
		log.info("Inside settlement terms method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Settlement terms
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Settlement terms
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			WaitHelper.waitAdditional(1);
			if(elements.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Monthly
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(3).equals("1")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._FIRST)).click();//Monthly
			}
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(4));//Daily
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(5));//Monthly
			WaitHelper.waitAdditional(1);
			if(!elements.get(6).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(6));//Month
			}
			if(!elements.get(7).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(7));//Days
			}
			if(!elements.get(8).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(8));//Date
			}
			WaitHelper.waitAdditional(1);
			if(!elements.get(9).equals("1")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).click();//Invoice
			}
			WaitHelper.waitAdditional(1);
			if(!elements.get(10).equals("1")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._FIRST)).click();//Period
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create bank account
	 * @param elements
	 * @return
	 */
	public boolean createBankAccount(List<String> elements){
		log.info("Create bank account");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Bank code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Bank code
		WaitHelper.waitAdditional(1);
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Bank account no
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Bank account desc
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Currency
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(4));//Bank sort code
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._ZERO)).sendKeys(elements.get(5));//Bank sort code
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._FIRST)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._FIRST)).sendKeys(elements.get(6));//Bank sort code
			update = true;
		}
		return update;
	}
	
	/**
	 * AP Ledger Controls - A056
	 * @param elements
	 */
	public void enterAccountPayableCompanyControl(String companyId,List<String> elements){
		log.info("Enter account payable company control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Currency tab
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyId);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(0));//Foreign currency rate type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Tolerance processing
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Currency turnover mentioned
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Turnover to include tax
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.ONE)).click();//Transaction enquiry in reverse date sequence
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject._FIRST)).click();//Auto cancel - Await payment
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Data Entry tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._FIRST)).click();//Transaction Duplicate - Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._TWO)).click();//Log Transactions - Optional
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._FIRST)).click();//Post code entry  - Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT+pObject._FIRST)).click();//Credit due date - Transactio date
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.FOUR)).click();// Transaction Totals Correction
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SIX)).click();//Tax at Detail Level
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SEVEN)).click();// Tax On Expenses at Detail Level
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(1));//Tax Variance Tax Code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Data Entry batch tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.NINE)).click();//Batch Totals Correction
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.ZERO)).click();//Batch Totals on Number of Transactions
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.ONE)).click();//Batch Totals Override
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.TWO)).click();//Batch on Entry
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.THREE)).click();// Batch on Logging
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.FOUR)).click();//Batch on Expense
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.FIVE)).click();//Mandatory Transaction Date
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Withholding Tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.FIVE+pObject._ZERO)).click();//Withholding type - None
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.SEVEN)).sendKeys(elements.get(2));//Tax Rate
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.EIGHT)).sendKeys(elements.get(3));//Non-compliant Tax Rate
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).sendKeys(elements.get(4));//Number of days to Tax prompt
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SEVENTH_TAB)).click();//Payment processing Tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.NINE)).sendKeys(elements.get(5));//Schedule Days Advance Warning:
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.ZERO)).sendKeys(elements.get(6));//Default Bank Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.ONE)).sendKeys(elements.get(7));//Minimum Payment Value
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.TWO)).sendKeys(elements.get(8));//Payment Register Value
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.THREE)).sendKeys(elements.get(9));//Payment Reconciliation
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.EIGHT+pObject._FIRST)).click();//Cheque duplication - Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).sendKeys(elements.get(10));//Number of Days Before Archiving
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.EIGHTH_TAB)).click();//GL Diary	tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT+pObject._ZERO)).click();//Relationship		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).sendKeys(elements.get(11));//Control Accounts Code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN+pObject.FOUR)).click();//Default Period
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.FIVE)).sendKeys(elements.get(12));//Current Period / Ye
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.SIX)).sendKeys(elements.get(13));//Current Period / Ye
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.SEVEN)).sendKeys(elements.get(14));//Days Resident
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.NINTH_TAB)).click();//Batch types tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.ZERO)).sendKeys(elements.get(15));//Default Batch Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.ONE)).sendKeys(elements.get(16));//Logged Transactions
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.TWO)).sendKeys(elements.get(17));//Entered Transactions
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.THREE)).sendKeys(elements.get(18));//Cancelled Transactions
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.FOUR)).sendKeys(elements.get(19));//Transaction Transfer
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.EIGHT)).sendKeys(elements.get(20));//VAT Analysis by Cost Centre
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.NINE)).sendKeys(elements.get(21));//Payments	
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id("0_tabbedPanel_1_tablist_rightBtn")).click();
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+pObject.TEN)).click();//Payment analysis/Netting off Tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.FOUR)).sendKeys(elements.get(22));//Netting off allowed
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+pObject.ONE+pObject.ONE)).click();//Invoce order matching Tab
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.FIVE)).sendKeys(elements.get(23));//Tolerance type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.EIGHT)).sendKeys(elements.get(24));//Tolerance (+)
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.NINE)).sendKeys(elements.get(25));//Tolerance (-)
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.ONE)).sendKeys(elements.get(26));//Tolerance Amount
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.ZERO+pObject._FIRST)).click();//Transaction Held for Price Difference
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TEN+pObject.TWO)).click();//Over Invoicing of Auto GRN Orders - Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.SIX+pObject._FIRST)).click();//Goods Receipt Further Matching Option - Prorata		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+pObject.ONE+pObject.FOUR)).click();//Credit/Debit matching Tab
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.TWO)).sendKeys(elements.get(27));//Tolerance Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.THREE)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.THREE)).sendKeys(elements.get(28));//Tolerance (+) 
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(29));//Tolerance Amount
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.FOUR)).sendKeys(Keys.ENTER);//Tolerance Amount
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Set Up Additional Batch Types - A056A
	 * @param elements
	 */
	public void amendBatchTypes(List<String> elements){
		log.info("Amend batch types");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(elements.get(1));//Last Batch Reference 		
	}
	
	/**
	 * Enter Transactional Legend details - A057
	 * @param elements
	 */
	public boolean enterTransactionLegendDetails(List<String> elements){
		log.info("Inside transaction legend details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Transaction Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Transaction Sub-type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Transaction Sub-type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Legend
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter payment methods - A058
	 * @param elements
	 */
	public boolean enterPaymentMethod(List<String> elements){
		log.info("Inside payment methods");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Payment method
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Payment method
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Payment description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Currency
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Payment Medium
			WaitHelper.waitAdditional(1);
			if(!elements.get(4).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(4));//Payment Rate	
			}
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(5));//Indicator
			WaitHelper.waitAdditional(1);
			if(elements.get(6).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Split print
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter Bank payment methods - A059
	 * @param elements
	 */
	public boolean enterBankPayMethods(List<String> elements){
		log.info("Inside bank payment methods");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Bank code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Payment method
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Payment method
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(2));//Payment Sub-Type
			update = true;
		}
		return update;
	}
	
	/**
	 * Enter payment code - A060
	 * @param elements
	 */
	public boolean enterPaymentCode(List<String> elements){
		log.info("Inside payment code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Payment code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Payment code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Payment description
			if(elements.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Settlement Due Date
			}
			
			WaitHelper.waitAdditional(3);
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath("//div[1]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(3)).build().perform();//Currency
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[1]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(4)).build().perform();//Payment method
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[1]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().
			sendKeys(elements.get(5)).build().perform();//Bank
			WaitHelper.waitAdditional(2);
			if(elements.get(6).equals("Y")){
			builder.moveToElement(driver.findElement(By.xpath("//div[1]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().
			sendKeys(elements.get(6)).build().perform();//Auto-Payment
			}
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().
			sendKeys(elements.get(7)).build().perform();//Schedule Destination
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(8)).build().perform();//Payment Destination
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Location Code - A061
	 * @param elements
	 */
	public boolean enterLocationCodeDetails(List<String> elements){
		log.info("Inside location code details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Location
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Location
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Address line 1
			WaitHelper.waitAdditional(1);
			
			if(!elements.get(3).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(3));//Post code
			}
			
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Details tab
			WaitHelper.waitAdditional(1);
			if(!elements.get(4).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).sendKeys(elements.get(4));//Management code
			}
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(5));//Accept user
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Tax handlers - A062
	 * @param elements
	 */
	public boolean enterTaxHandlingDetails(List<String> elements){
		log.info("Inside tax handling details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Handling
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Handling
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			WaitHelper.waitAdditional(1);
			if(elements.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Inclusive
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(3).equals("1")){
				if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Recoverable
				}
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(3).equals("0")){
				if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).isSelected()){
					getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Recoverable
				}
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(4).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Pro-data
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Create Standard Reference details - A063
	 * @param elements
	 * @param lines
	 */
	public boolean enterStandardTextReferenceDetails(List<String> elements,List<String> lines){
		log.info("Enter standard text reference details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		if(elements.get(1).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ZERO)).click();//Disabled check box
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Text ref
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Text ref
		WaitHelper.waitAdditional(1);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			WaitHelper.waitAdditional(1);
			for(int i=1;i<=lines.size();i++){
				Actions builder = new Actions(driver);
				builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div/div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td"))).click().
				sendKeys(lines.get(i-1)).build().perform();//Line
				WaitHelper.waitAdditional(1);
		}
		update = true;
		}
		
		return update;
	}
	
	/**
	 * Create PPV Control Account - A064
	 * @param elements
	 */
	public void enterPPVControlAccount(List<String> elements){
		log.info("Enter PPV control account details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//PPV Control code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(2));//PPV Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).sendKeys(elements.get(3));//PPV Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(elements.get(4));//PPV Reserve
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._FIRST)).sendKeys(elements.get(5));//PPV Reserve
	}
	
	/**
	 * Edit purchasing company control - A065
	 * @param elements
	 */
	public void editCommonPurchasingComany(List<String> elements){
		log.info("Edit company purchasing company");
		WaitHelper.waitAdditional(2);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Purchasing price
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(0));//PPV account code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Supplier tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(1));//Price expiry
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Goods tab
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ONE)).click();//At point of receipt
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.FIVE)).click();//Print receipt/Return Note
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).sendKeys(elements.get(2));//No.of copies
	}
	
	/**
	 * Create Accrual control account - A066
	 * @param elements
	 */
	public void enterAccrualControlAccount(List<String> elements){
		log.info("Enter accrual control account details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Accrual Control code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(2));//Accrual Account
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).sendKeys(elements.get(3));//Accrual Account
	}
	
	
	/**
	 * Create Inspection code  - A067
	 * @param elements
	 */
	public void enterPOPCompanyControls(String companyName,List<String> elements){
		log.info("Enter POP company controls");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Numbering / GL /Supplier
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(0));//Company no length		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(1));//Next doc no
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(2));//Control Account
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(3));//Batch Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).sendKeys(elements.get(4));//Current GL Period/Year
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(5));//Current GL Period/Year
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE)).click();//Maintain Turnover
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Turnover to Include Tax
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Maintain Currency Turnover
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(6));//Turn-over period definition
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(elements.get(7));//Prompts Active	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(8));//Number of Prices to Return
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Data entry
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR+pObject._ZERO)).click();//Amend or revise
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).sendKeys(elements.get(9));//Supplier address
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SEVEN)).sendKeys(elements.get(10));//Invoice point
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.EIGHT)).sendKeys(elements.get(11));//Delivery point
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ONE+pObject._FIRST)).click();//Print cancelled lines- suppressed
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.SEVEN)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.SEVEN)).click();//tem Short Description Amendable
		}
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.EIGHT)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.EIGHT)).click();//Use EDI Processing
		}
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.NINE)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.NINE)).click();//Location Codes Mandatory
		}
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ZERO)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ZERO)).click();// Order Line Accept User
		}
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ONE)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ONE)).click();//Allow Auto Order creation from Requisitions
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.THREE)).sendKeys(elements.get(12));//Numbering
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.TWO)).sendKeys(elements.get(13));//Quotation Request
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FIVE)).sendKeys(elements.get(14));//Quotation Acceptance
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).sendKeys(elements.get(15));//Quotation Rejection
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(elements.get(16));//Document Retention Period
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ZERO)).sendKeys(elements.get(17));//Foreign Currency Rate Type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ZERO)).sendKeys(Keys.ENTER);//Foreign Currency Rate Type
		WaitHelper.waitAdditional(2);
	}
	
	
	
	/**
	 * Create Inspection code  - A068
	 * @param elements
	 */
	public void enterInspectionCode(List<String> elements){
		log.info("Enter inspection code details");
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Inspection code
		WaitHelper.waitAdditional(1);		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject._+elements.get(1))).click();//Inspection type
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(3));//Description
	}
	
	/**
	 * Create Disposal code - A0069
	 * @param elements
	 */
	public boolean enterDisposalCode(List<String> elements){
		log.info("Enter disposal code details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Disposal code
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Disposal code
		WaitHelper.waitAdditional(1);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			update = true;
		}
		return update ;
	}
	
	/**
	 * Create Disposal code - A0070
	 * @param elements
	 */
	public void enterReasonCode(List<String> elements){
		log.info("Enter reason code details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Reason code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/**
	 * Document prefix code - A071
	 * @param elements
	 */
	public boolean enterDocumentPrefixCode(List<String> elements){
		log.info("Inside document prefix code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Document type
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Prefix
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Prefix
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Suffix type
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(4));//Length
			update = true;
		}
		return update;
	}
	
	/**
	 * Search CP user
	 * @param elements
	 */
	public void searchCPUser(List<String> elements){
		log.info("Search CP User");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(6)){
			clickOnSections(0);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(0));
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.LABEL)).click();
		
	}
	
	/**
	 * Create CP User - A072
	 * @param elements
	 */
	public void enterCPUserDetails(List<String> elements){
		log.info("Inside CP User details page");
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//CP User
		
		WaitHelper.waitAdditional(2);
		
		if(elements.get(1).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Buyer
		}
		WaitHelper.waitAdditional(1);
		if(elements.get(2).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Expediter
		}
		WaitHelper.waitAdditional(1);
		if(elements.get(3).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).click();//Acceptor
		}
		WaitHelper.waitAdditional(1);
		if(elements.get(4).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();//Goods Receiver
		}

		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Default Details
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(elements.get(5));//Buyer
		WaitHelper.waitAdditional(2);

	}
	
	/**
	 * Create Receipt Control details - A073
	 * @param elements
	 */
	public void enterReceiptControlsDetails(List<String> elements){
		log.info("Enter receipt control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Receipt Control
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Tolerance Processing
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._FIRST)).click();//Under Check Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Under Check %
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys("1.00");//Under Check value
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._FIRST)).click();//Over Check Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(4));//Over Check %
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys("1.00");//Under Check value
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject._FIRST)).click();//Early check Warning
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(5));//Early check
	}
	
	/**
	 * CreatebAutomatic bank code - A074
	 * @param elements
	 */
	public void enterAutomaticBankCode(List<String> elements,String protocolId){
		log.info("Enter automatic bank code details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Automatic Banking Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//User Number	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//User Name
		WaitHelper.waitAdditional(1);
		if(!elements.get(3).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Bureau Number
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(protocolId);//Protocol Id
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(4));//Calendar
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(5));//Processing Cycle/Days
	}
	
	/**
	 * Create Circulation code - A075
	 * @param elements
	 */
	public boolean enterCirculationCode(List<String> elements){
		log.info("Inside circulation code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Circulation
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Circulation
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Descrption
			WaitHelper.waitAdditional(3);
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
			sendKeys("01").build().perform();//Company
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(2)).build().perform();//Mapping
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
			sendKeys(elements.get(3)).build().perform();//Profile
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
			sendKeys(elements.get(4)).build().perform();//Description
			WaitHelper.waitAdditional(2);
			update = true;
		}
		return update;
	}
	
	/*Company control QSA*/
	public void createCompanyControl(String companyName,List<String> elements){
		log.info("Create company control");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(companyName);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(0));//User
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(1));//Default Report Prefix
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(2));//Default Report Prefix No
	}
	
	/*Create Mappings QMA*/
	public void createMappings(List<String> elements){
		log.info("Inside create maappings method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Mapping
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	
	/**
	 * Document Codes - A076
	 * @param elements
	 */
	public void enterDocumentCodeDetails(List<String> elements){
		log.info("Enter document code details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Document code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Document type
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Miscellaneous
		
		if(!elements.get(3).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(3));//Header Entry
		}
		if(!elements.get(4).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(4));//Detail Entry
		}
		if(!elements.get(5).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(5));//Fast Entry
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Print options
		WaitHelper.waitAdditional(2);
		if(elements.get(6).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._FIRST)).click();//Amendments - Print All
			WaitHelper.waitAdditional(1);
		}
		if(elements.get(7).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._FIRST)).click();//Revisions - Print All
			WaitHelper.waitAdditional(1);
		}
		if(elements.get(8).equals("1")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX+pObject._FIRST)).click();//Printing of Cancelled Lines -  Suppressed
			WaitHelper.waitAdditional(1);
		}
		if(elements.get(8).equals("2")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX+pObject._TWO)).click();//Printing of Cancelled Lines -  Annotated
			WaitHelper.waitAdditional(1);
		}
		if(elements.get(9).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SEVEN)).click();//Immediate Print Required
			WaitHelper.waitAdditional(1);
		}
		if(!elements.get(10).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(10));//Circulation
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Data entry tab
		WaitHelper.waitAdditional(1);
		if(!elements.get(11).equals("NILL")){
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(elements.get(11));//Price Mandatory
		}
		if(!elements.get(12).equals("NILL")){
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).sendKeys(elements.get(12));//Auto-Numbering Required
		}
		if(!elements.get(13).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.NINE)).sendKeys(elements.get(13));//Value Ceiling For Document
		}
		if(elements.get(14).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.ONE)).click();// Returns
		}
		if(!elements.get(15).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FOUR)).sendKeys(elements.get(15));//Material Request
		}
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Invoice tab
		WaitHelper.waitAdditional(2);
		if(elements.get(16).equals("1")){
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ONE)).click();//Miscellaneous Charges Accepted
		}
		WaitHelper.waitAdditional(1);
		if(elements.get(17).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.TWO)).click();//Invoice Price Tolerance Check
		}
		WaitHelper.waitAdditional(1);
		if(!elements.get(18).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.THREE)).sendKeys(elements.get(18));//Order Line Selection Default
		}
		WaitHelper.waitAdditional(1);
		if(!elements.get(19).equals("NILL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.ZERO)).sendKeys(elements.get(19));//Accruals Batch type
		}	


	}
	
	/**
	 * Edit CP company controls - A077
	 * @param elements
	 */
	public void editCPCompanyControls(List<String> elements){
		log.info("Edit CP company controls");
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Goods receipting/IM tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.NINE)).sendKeys(elements.get(1));//Purchase Requisition Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ZERO)).sendKeys(elements.get(2));//Replenishment Requisition Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.ONE)).sendKeys(elements.get(3));//Buyer Code
	}
	
	/**
	 * Authorisation Controls - A078
	 * @param elements
	 */
	public boolean enterAuthorisationControlDetails(List<String> elements){
		log.info("Inside authorisation control details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Control Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Authorisation Control Code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Method
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Type
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO_+elements.get(4))).click();// Level
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(5));//Minimum Value
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(6));//Query Authoriser
			WaitHelper.waitAdditional(1);
			if(elements.get(7).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.EIGHT)).click();//Auth Order Related Transaction
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Authorisation Group for Purchase management - A079
	 * @param elements
	 */
	public void enterAuthorisationGroupForPM(List<String> elements){
		log.info("Enter authorisation group for PM");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	
	/**
	 * Authorisation Group for Accounts Payable - A079
	 * @param elements
	 */
	public void enterAuthorisationGroupForAP(List<String> elements){
		log.info("Enter authorisation group for AP");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/**
	 * Authorisation Control Details For PM - A080
	 * @param elements
	 */
	public boolean enterAuthorisationControlDetailsForPM(List<String> elements){
		log.info("Inside authorisation control details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Control Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Authorisation Control Code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Method
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Type
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO_+elements.get(4))).click();// Level
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(5));//Minimum Value
			WaitHelper.waitAdditional(1);
			if(!elements.get(6).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
				getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(6));//Query Authoriser
			}
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject._+elements.get(7))).click();//Authorisation on Amendments
			if(elements.get(8).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.ONE)).click();// Site
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(9).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.TWO)).click();//Document Type
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(10).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.THREE)).click();//Document Code
			}
			WaitHelper.waitAdditional(1);
			if(elements.get(11).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.FOUR)).click();// Buyer
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Authorisation Groupings Details FOR PM - A081
	 * @param elements
	 */
	public boolean enterAuthorisationGroupingsDetails(List<String> elements){
		log.info("Inside authorisation grouping details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(5));//Authorisation Control Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Location
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Document Type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//Document Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(Keys.ENTER);//Document Code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			if(!elements.get(3).equals("NILL")){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
				WaitHelper.waitAdditional(1);
				getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(3));//Buyer
			}
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(4));//Authorisation Group
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(6));//Grouping Description
			update = true;
		}
		return update;
	}
	
	/**
	 * Authorisation Structure For AP - A082
	 * @param elements
	 */
	public void enterAuthorisationStructureForAP(List<String> elements){
		log.info("Enter authorisation structure for AP");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/**
	 * Authorisation Structure For PM - A082
	 * @param elements
	 */
	public void enterAuthorisationStructureForPM(List<String> elements){
		log.info("Enter authorisation structure for AP");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/**
	 * Structure Authorisor For AP - A083
	 * @param elements
	 */
	public void enterStructureAuthorisorForAP(List<String> elements){
		log.info("Inside authorisor for AP method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Authorisation Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//User
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//User
		WaitHelper.waitAdditional(2);
		
		if(!elements.get(2).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(2));//Parent User
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(3));//Rating
	}
	
	/**
	 * Structure Authorisor For PM - A083
	 * @param elements
	 */
	public void enterStructureAuthorisorForPM(List<String> elements){
		log.info("Inside authorisation for PM method");

		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//User	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//User
		WaitHelper.waitAdditional(2);
		
		if(!elements.get(2).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Parent User
		}
		if(!elements.get(3).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(3));//Rating
		}
	}
	
	/**
	 * Authorisation Value PM - A084
	 * @param elements
	 */
	public boolean enterAuthorisationValuePM(List<String> elements){
		log.info("Inside Authorisation Value for PM");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Value Level
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Value Level
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Value
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			update = true;
		}
		return update;
	}
	
	/**
	 * Authorisation Value AP - A084
	 * @param elements
	 */
	public boolean enterAuthorisationValueAP(List<String> elements){
		log.info("Inside Authorisation Value for AP method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Value Level
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Value Level
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Value
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			update = true;
		}
		return update;
	}
	
	/**
	 * Value Level Auth For AP - A085
	 * @param elements
	 */
	public boolean enterValueLevelAuthForAP(List<String> elements){
		log.info("Inside authorisation by value method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Value
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Value
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Minimum Authorisers
			Actions builder = new Actions(driver);
			WaitHelper.waitAdditional(2);
			int users = Integer.parseInt(elements.get(2));
			int j = 3;
			for(int i=1;i<=users;i++){
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(j)).build().perform();//User
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(Keys.ENTER).build().perform();//User
				WaitHelper.waitAdditional(6);
				j++;
			}
			update = true;
		}
		return update;
	}
	
	public void searcValueLevel(List<String> elements){
		log.info("Search value level");
		WaitHelper.waitAdditional(3);
		if(!isOkButtonDisplayed(2)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Auth group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Value level
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.LABEL)).click();
	}
	
	/**
	 * Value Level Auth For PM - A085
	 * @param elements
	 */
	public boolean enterValueLevelAuthForPM(List<String> elements){
		log.info("Inside authorisation by value method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Value
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Value
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Minimum Authorisers
			Actions builder = new Actions(driver);	
			WaitHelper.waitAdditional(2);
			
			int users = Integer.parseInt(elements.get(2));
			int j = 3;
			for(int i=1;i<=users;i++){
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(j)).build().perform();//User
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(Keys.ENTER).build().perform();//User
				WaitHelper.waitAdditional(5);
				j++;
			}	
			update = true;
		}
		return update;
	}
	
	/**
	 * GL Responsibility Auth For AP - A086
	 * @param elements
	 */
	public boolean enterGLResponsibilityAuthForAP(List<String> elements){
		log.info("Inside GL responsibility method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Element
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(2));//Minimum Authorisers
	
			int j = 3;
			int users = Integer.parseInt(elements.get(2));
			Actions builder = new Actions(driver);
			WaitHelper.waitAdditional(2);
			for(int i=1;i<=users;i++){
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(j)).build().perform();//User
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(Keys.ENTER).build().perform();//User
				WaitHelper.waitAdditional(5);
				j++;
			}
			update = true;
		}
		return update;
	}
	
	/**
	 * Authorisation by Level/GL Responsibility for Accounts Payable - A087
	 * @param elements
	 * @param l1Users
	 * @param l2Users
	 * @param l3Users
	 * @param l4Users
	 */
	public void enterAuthorisationByLevelOrGLResponsibilityForAP(List<String> elements,List<String> l1Users,List<String> l2Users,List<String> l3Users,List<String> l4Users){
		log.info("In authorisation by Level Or GL responsibility for AP method");
	
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Element
		WaitHelper.waitAdditional(5);
	
			Actions builder = new Actions(driver);
			WaitHelper.waitAdditional(2);	
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(2)).build().perform();//LEVEL1
			WaitHelper.waitAdditional(5);
	
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[2]/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(3)).build().perform();//LEVEL2
			WaitHelper.waitAdditional(5);
	
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[3]/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(4)).build().perform();//LEVEL3
			WaitHelper.waitAdditional(5);
		
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[4]/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(5)).build().perform();//LEVEL4
			WaitHelper.waitAdditional(5);
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//To enable 'Authorisors' fields
			WaitHelper.waitAdditional(3);
			addUsers(l1Users,1);
			addUsers(l2Users,2);
			addUsers(l3Users,3);
			addUsers(l4Users,4);	
	}
	
	private void addUsers(List<String> users,int i){
		log.info("Adding users");
		WaitHelper.waitAdditional(5);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().build().perform();
		WaitHelper.waitAdditional(5);
		clickOnAuthorisors();
		if(isAuthorisorsButtonVisible() == true){
			clickOnAuthorisors();
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(users.get(0));//Min Authorisers
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		int userCount = Integer.parseInt(users.get(0));
		Actions builderNew = new Actions(driver);
		for(int j=1;j<=userCount; j++){
			WaitHelper.waitAdditional(5);
			builderNew.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+j+"]/table/tbody/tr/td[2]"))).click().
			sendKeys(users.get(j)).build().perform();//User
			WaitHelper.waitAdditional(5);
			builderNew.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+j+"]/table/tbody/tr/td[2]"))).click().
			sendKeys(Keys.ENTER).release().perform();//User
			WaitHelper.waitAdditional(7);
		}		
		clickOnUpdate();
		WaitHelper.waitAdditional(5);
	}
	 
	/**
	 * Click on authorisors
	 */
	public void clickOnAuthorisors(){
		log.info("Click on Authorisors button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Authorisors")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify authorisors button
	 * @return
	 */
	public boolean isAuthorisorsButtonVisible(){
		log.info("Verify presence of Authorisors button");
		boolean visible = false;
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Authorisors")){
				visible = true;
				break;
			}
		}
		WaitHelper.waitAdditional(2);
		return visible;
	}
				
	/**
	 * Value/GL Responsibility For AP - A088
	 * @param elements
	 */
	public boolean enterValueORGLResponsibilityForAP(List<String> elements){
		log.info("Inside GL responsibility of AP method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Authorisation Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Element
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(2));//Minimum Authorisers
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(Keys.ENTER);
			WaitHelper.waitAdditional(3);
			int userCount = Integer.parseInt(elements.get(3));			
			Actions builder = new Actions(driver);
			int j = 4;
			for(int i=1;i<=userCount;i++){				
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(j)).build().perform();//User
				WaitHelper.waitAdditional(5);
				j = j+1;
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click().
				sendKeys(elements.get(j)).build().perform();//Value
				WaitHelper.waitAdditional(5);
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click().
				sendKeys(Keys.ENTER).build().perform();//Value
				WaitHelper.waitAdditional(5);
				j++;
				WaitHelper.waitAdditional(4);
			}	
			update = true;
		}
		return update;
	}
	
	/**
	 * AP Company Control - A089
	 * @param elements
	 */
	public void editAPCompanyControl(List<String> elements){
		log.info("Edit AP company control details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Authorisation tab
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SIX)).sendKeys(elements.get(0));//Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).sendKeys(elements.get(1));//Structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.NINE)).sendKeys(elements.get(2));//Company Structure:
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ZERO)).sendKeys(elements.get(3));//Structure Path
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ONE)).click();//Use Analysis
		WaitHelper.waitAdditional(1);		
	
		if(!elements.get(4).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.TWO)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.TWO)).sendKeys(elements.get(4));//Code Identifier
		}
		WaitHelper.waitAdditional(3);
		if(!elements.get(5).equals("NILL")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.FOUR)).sendKeys(elements.get(5));//Posting Type
		}
	}
	
	/**
	 * Amend PM Company Control - A090
	 * @param elements
	 */
	public void editPMCompanyControl(List<String> elements){
		log.info("Edit PM company control detais");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Data entry tab
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(0));//Control Code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).sendKeys(elements.get(1));//Company structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).sendKeys(elements.get(2));//Default structure
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(elements.get(3));//Code id
	}
	
	/**
	 * Edit document code
	 * @param elements
	 */
	public void editDocumentCodes(String elements){
		log.info("Edit document code details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Miscellaneous tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements);//Control Code
	}
			
	/**
	 * Set Up Suppliers - A091
	 * @param elements
	 */
	public void enterSupplierListDetail(List<String> elements){
		log.info("Enter supplier details");
		WaitHelper.waitAdditional(2);		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Supplier
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Name
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Short name
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).click();//Supplier
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Currency
		WaitHelper.waitAdditional(2);
		
		enterSupplierAddress(elements);
		enterSupplierPurchasingControl(elements);
		enterSupplierTaxDetails(elements);
		enterSupplierLedgerControlDetails(elements);
		enterPOPControlDetails();
	}
	
	/**
	 * Enter supplier address
	 * @param elements
	 */
	public void enterSupplierAddress(List<String> elements){
		log.info("Enter suppler address details");
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.LABEL)).click();//Address
		WaitHelper.waitAdditional(5);
		
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
		sendKeys(elements.get(4)).build().perform();//Address number
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
		sendKeys(elements.get(5)).build().perform();//Address line 1
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
		sendKeys(elements.get(6)).build().perform();//Address line 2 
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().
		sendKeys(elements.get(7)).build().perform();//Address line 3
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[7]"))).click().
		sendKeys(elements.get(8)).build().perform();//Address line 4
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[10]"))).click().
		sendKeys(elements.get(9)).build().perform();//Post Code 
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[11]"))).click().
		sendKeys(elements.get(10)).build().perform();//Phone Number
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[12]"))).click().
		sendKeys(elements.get(11)).build().perform();//Telex Number
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[13]"))).click().
		sendKeys(elements.get(12)).build().perform();//Fax Number
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[14]"))).click().
		sendKeys(elements.get(13)).build().perform();//Contact Name
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[14]"))).click().
		sendKeys(Keys.ENTER).build().perform();//Contact Name
		WaitHelper.waitAdditional(6);
	}
	
	/**
	 * Enter supplier purchasing control
	 * @param elements
	 */
	private void enterSupplierPurchasingControl(List<String> elements){

		log.info("Enter PM control details");
		WaitHelper.waitAdditional(8);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.LABEL)).click();//Pur control
		if(getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.LABEL)).getText().equals("Pur Cntrl")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN+pObject.LABEL)).click();//Pur control
		}
		
		WaitHelper.waitAdditional(8);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Processing tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(14));//Category code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Discounts defaults tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(15));//Discount terms
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(16));//Settlement terms
		if(!getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._ZERO)).isSelected()){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._ZERO)).click();//Supplier discount
		}
		if(!getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN+pObject._ZERO)).isSelected()){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN+pObject._ZERO)).click();//Always take
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Enter supplier tax details
	 * @param elements
	 */
	private void enterSupplierTaxDetails(List<String> elements){
		log.info("Enter supplier tax details");
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.ONE+pObject.LABEL)).click();//TAX
		
		WaitHelper.waitAdditional(5);
		
		Actions builder = new Actions(driver);	
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
		sendKeys(elements.get(17)).build().perform();//Location
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
		sendKeys(elements.get(18)).build().perform();//Registration
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
		sendKeys(elements.get(19)).build().perform();//Tax type
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
		sendKeys(elements.get(20)).build().perform();//Tax code
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().
		sendKeys(elements.get(21)).build().perform();//Handling code
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[8]"))).click().
		sendKeys(elements.get(22)).build().perform();//Default
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[8]"))).click().
		sendKeys(Keys.ENTER).build().perform();//Default
		WaitHelper.waitAdditional(6);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.LABEL)).click();//Pur cntrl button
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Enter suppler ledger control details
	 * @param elements
	 */
	private void enterSupplierLedgerControlDetails(List<String> elements){
		log.info("Enter AP control details");
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.TWO+pObject.LABEL)).click();//AP Control
		
		WaitHelper.waitAdditional(5);		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Supplier tab
		WaitHelper.waitAdditional(5);		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(23));//Authorisation Code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(24));//Self Assessed Tax
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(25));//Turnover
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(26));//Invoice Payment
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Payment tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject	.TWO+pObject.ZERO)).sendKeys(elements.get(27));//Method
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).sendKeys(elements.get(28));//Code
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Payment tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FOUR+pObject._FIRST)).click();//Retain net
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Enter POP control details
	 */
	private void enterPOPControlDetails(){
		log.info("Enter POP control details");
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.SEVEN+pObject.LABEL)).click();//PM Control		
		WaitHelper.waitAdditional(5);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Order details tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX+pObject._FIRST)).click();//EDI Orders
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._ZERO)).click();// Not Maintaine
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Tolerance tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ONE)).click();///Tolerance amount
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO+pObject._ZERO)).click();//Miscellaneous Invoice Charges
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on Address
	 */
	public void clickOnAddress(){
		log.info("Click on address button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Address")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on PUR control
	 */
	public void clickOnPurControl(){
		log.info("Click on Pur Cntrl button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Pur Cntrl")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on PM control
	 */
	public void clickOnPMControl(){
		log.info("Click on PM Cntrl button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("PM Cntrl")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on AP control
	 */
	public void clickOnAPControl(){
		log.info("Click on AP Cntrl button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("AP Cntrl")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Click on Tax
	 */
	public void clickOnTax(){
		log.info("Click on Tax button");
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("Tax")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(3);
	}
	
	public void getUserNameTextBox(){
		log.info("Inside get username text box method");
		WaitHelper.waitAdditional(2);
//		WebElement wb = getDriver().findElement(By.cssSelector("input[name='USR_field_0'][type='text']"));
		WebElement wb = getDriver().findElement(By.cssSelector("input[name[contains(text(),'USR_field_0')]][type='text']"));
		
		wb.sendKeys("T66");
		
	}
	
	public void createLearner(String uName,String pwd) throws InterruptedException{
		getDriver().findElement(By.id("UserName")).sendKeys(uName);//"PaChand1");
		getDriver().findElement(By.id("Password")).sendKeys(pwd);//("299@CaP010458");
		getDriver().findElement(By.id("loginbtn")).click();
		Thread.sleep(3000);
	}
	
	public void createLearner12() throws InterruptedException{
	
		getDriver().get("https://perf.progresso.net/LearnerRecord/LearnerList");
		getDriver().findElement(By.linkText("Add")).click();
		Thread.sleep(1000);
		
		for(int i=70;i<=100;i++){
			

//			driver.get("https://perf.progresso.net/LearnerRecord/LearnerList");
//			driver.findElement(By.linkText("Add")).click();
		
			getDriver().findElement(By.id("ETS2001")).sendKeys("abc");
			getDriver().findElement(By.id("ETS2004")).sendKeys("efg");
			getDriver().findElement(By.id("ETS2007")).sendKeys("14512"+i);
			getDriver().findElement(By.id("ETS2003")).sendKeys("13/08/2000");
			
			getDriver().findElement(By.xpath(".//*[@id='dvActionbtnArea']/div[3]/div[2]")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.xpath(".//*[@id='fourthNavItemPersonal']/div[2]/a")).click();
			Thread.sleep(2000);
			
			WebElement gender=getDriver().findElement(By.id("ETS3017"));
			Select selectgender=new Select(gender);
			selectgender.selectByVisibleText("Female");
			
			getDriver().findElement(By.id("SectionHeader3")).click();
			
			getDriver().findElement(By.id("ETS3025")).sendKeys("05/09/2011");
			
			WebElement course=getDriver().findElement(By.id("ETS3028"));
			Select selectcourse=new Select(course);
			selectcourse.selectByVisibleText("Key Stage 4");
			Thread.sleep(2000);
			
			WebElement year=getDriver().findElement(By.id("ETS3030"));
			Select selectyear=new Select(year);
			selectyear.selectByVisibleText("Year 10");
			Thread.sleep(2000);
			
			WebElement ncyear=getDriver().findElement(By.id("ETS3026"));
			Select selectNCyear=new Select(ncyear);
			selectNCyear.selectByVisibleText("Year 10");
			Thread.sleep(2000);
			
			getDriver().findElement(By.xpath(".//*[@id='dvActionbtnArea']/div[3]/div[2]")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//*[@id='modalcontent']/div/div[2]/div/div[1]/div[2]/a")).click();
		    Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			
			Thread.sleep(2000);
			getDriver().findElement(By.linkText("Add")).click();
		}
	}
	
	public void createLearner45() throws InterruptedException{
		
		getDriver().get("https://perf.progresso.net/LearnerRecord/LearnerList");
		getDriver().findElement(By.linkText("Add")).click();
		Thread.sleep(1000);
		
		for(int i=70;i<=100;i++){
			

//			driver.get("https://perf.progresso.net/LearnerRecord/LearnerList");
//			driver.findElement(By.linkText("Add")).click();
		
			getDriver().findElement(By.id("ETS2001")).sendKeys("xcbc");
			getDriver().findElement(By.id("ETS2004")).sendKeys("efg");
			getDriver().findElement(By.id("ETS2007")).sendKeys("14542"+i);
			getDriver().findElement(By.id("ETS2003")).sendKeys("13/08/2000");
			
			getDriver().findElement(By.xpath(".//*[@id='dvActionbtnArea']/div[3]/div[2]")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.xpath(".//*[@id='fourthNavItemPersonal']/div[2]/a")).click();
			Thread.sleep(2000);
									
//			getDriver().findElement(By.id("ETS3001")).sendKeys("xcbcz");
//			Thread.sleep(2000);
			
			WebElement gender=getDriver().findElement(By.id("ETS3017"));
			Select selectgender=new Select(gender);
			selectgender.selectByVisibleText("Female");
			
			Thread.sleep(1000);
			
			getDriver().findElement(By.id("SectionHeader3")).click();
			Thread.sleep(1000);			
			
			getDriver().findElement(By.id("ETS3025")).sendKeys("05/09/2011");
			Thread.sleep(1000);
			
			WebElement course=getDriver().findElement(By.id("ETS3028"));
			Select selectcourse=new Select(course);
			selectcourse.selectByVisibleText("Key Stage 4");
			Thread.sleep(2000);
			
			WebElement year=getDriver().findElement(By.id("ETS3030"));
			Select selectyear=new Select(year);
			selectyear.selectByVisibleText("Year 10");
			Thread.sleep(2000);
			
			WebElement ncyear=getDriver().findElement(By.id("ETS3026"));
			Select selectNCyear=new Select(ncyear);
			selectNCyear.selectByVisibleText("Year 10");
			Thread.sleep(2000);
			
			getDriver().findElement(By.xpath(".//*[@id='dvActionbtnArea']/div[3]/div[2]")).click();
			Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//*[@id='modalcontent']/div/div[2]/div/div[1]/div[2]/a")).click();
		    Thread.sleep(2000);
			
			getDriver().findElement(By.id("okatag")).click();
			
			Thread.sleep(2000);
			getDriver().findElement(By.linkText("Add")).click();
		}
	}
	
} 
