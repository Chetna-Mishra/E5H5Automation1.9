package com.adv.qa.selenium.framework.pageObjects.currency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.adv.qa.selenium.framework.pageObjects.PageObjects;
import com.adv.qa.selenium.helpers.WaitHelper;

public class CurrencyPageNew extends CurrencyPage{

	private PageObjects pObject = new PageObjects();
	private String message = "The specified key already exists";
	
	public CurrencyPageNew(EventFiringWebDriver driver) {
		super(driver);
	}
	
	/**
	 * Description : Click on expand action
	 */
	public void expandAction(){
		getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[1]")).click();
		WaitHelper.waitAdditional(2);
		clickOnExpandAndCollapse();
	}
	
	/**
	 * Description : Click on expand and close
	 */	
	public void clickOnExpandAndCollapse(){
		log.info("Click on expand collapse button");
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Amend supplier address
	 * @param elements
	 */
	public void amendSupplierAddress(List<String> elements){
		log.info("Enter suppler address details");
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject.LABEL)).click();//Address
		WaitHelper.waitAdditional(5);
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
		sendKeys(elements.get(1)).build().perform();//Address line 1
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
		sendKeys(elements.get(2)).build().perform();//Address line 2 
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().
		sendKeys(elements.get(3)).build().perform();//Address line 3
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[7]"))).click().
		sendKeys(elements.get(4)).build().perform();//Address line 4
		WaitHelper.waitAdditional(3);
		WaitHelper.waitAdditional(6);
	}
	
	/**
	 * Enter supplier elements A092
	 * @param elements 
	 */
	public void enterSupplierElements(List<String> elements){
		log.info("Create supplier elements");
		WaitHelper.waitAdditional(1);
		enterSupplierElementDetails(elements.get(0),1);
		enterSupplierElementDetails(elements.get(1),2);
		enterSupplierElementDetails(elements.get(2),3);
		enterSupplierElementDetails(elements.get(3),4);
		WaitHelper.waitAdditional(1);
	}

	/*Enter supplier elements*/
	private void enterSupplierElementDetails(String elements,int i){
		log.info("Enter supplier elements");
		WaitHelper.waitAdditional(5);
		Actions builder = new Actions(driver);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click().
		sendKeys(elements).build().perform();//Element
		WaitHelper.waitAdditional(3);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click().
		sendKeys("Y").build().perform();//Balance
		WaitHelper.waitAdditional(3);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).click().
		sendKeys("Y").build().perform();//Turnover
		WaitHelper.waitAdditional(3);
		builder.moveToElement(getDriver().findElement(By.xpath("//div[3]//div/div/div/div/div["+i+"]/table/tbody/tr/td[5]"))).click().
		sendKeys(Keys.ENTER).build().perform();//Turnover
		WaitHelper.waitAdditional(5);
	}

	/**
	 * Amend Transactional Legend details - A93
	 * @param elements
	 */
	public boolean amendTransactionLegendDetails(List<String> elements){
		log.info("Amend transaction legend details");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		String Authorisation_Code = getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).getText();
		String Authorisation_Group = getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).getText();
		
		if(!(Authorisation_Code.equals(elements.get(2)) && Authorisation_Group.equals(elements.get(3)))){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));
			WaitHelper.waitAdditional(1);
			
			if(!(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected())){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();
			}
			update = true;
		}
		return update;
	}

	/**
	 * Click on Events : A094
	 */
	public void clickOnEvents(){
		log.info("Click on events");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.LABEL)).click();
		WaitHelper.waitAdditional(3);
	}
	
	public boolean selectNotification(int i){
		log.info("Select notificatio value");
		boolean update = false;
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+i)).isSelected()){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+i)).click();
			update = true;
		}
		WaitHelper.waitAdditional(1);
		return update;
	}

	
	/**
	 * Create program Event : A095
	 * @param elements
	 */
	public void createProgramEvent(List<String> elements){
		log.info("Create program event");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Program
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Event handler
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Program
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._+pObject.ONE)).click();//pgm event = 1.Handler
		WaitHelper.waitAdditional(2);
			
	}
	
	/**
	 * Business Event Manager Action List : A096
	 */
	public boolean verifyManagerEvent(List<String> elements){
		log.info("Verify business event manager action");
		WaitHelper.waitAdditional(2);
		boolean action = false;
		
		//Multiple tab
		if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected()){
			action = true;
		}
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Email/Diary text tab
		
		String substitution = getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).getText();//Substitution Character
		String messageLine1 = getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.EIGHT)).getText();//Message line 1
		String messageLine2 = getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).getText();//Message line 2
		
		if(substitution.equals(elements.get(1)) && messageLine1.equals(elements.get(2)) && messageLine2.equals(elements.get(3))){
			action = true;
		}
		
		return action;
	}
	
	/**
	 * Create Stock code : A097
	 */
	public void createStockType(List<String> elements){
		log.info("In create stock type");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Stock type
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/**
	 * Item creations - A098
	 */	
	public void createItem(List<String> elements,int i){
		log.info("In create item");
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		
		if(elements.get(2).equals("Stock")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Stock Item
		}
		else if(elements.get(2).equals("Generic")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();//Generic Item
		}
		else if(elements.get(2).equals("Service")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE)).click();//Service Item
		}
		
		if(!elements.get(3).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Stock type
		}
		if(!elements.get(4).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Unit
		}
		WaitHelper.waitAdditional(1);
		if(!elements.get(5).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).sendKeys(elements.get(5));//Issue Uom
			WaitHelper.waitAdditional(1);
		}
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();
		WaitHelper.waitAdditional(2);

		if(!elements.get(6).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.EIGHT)).sendKeys(elements.get(6));//Receipt Control
		}

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();
		WaitHelper.waitAdditional(2);

		
		if(elements.get(7).equals("1")){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.EIGHT)).click();//purchase price
		}
		if(!elements.get(8).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).sendKeys(elements.get(8));//PPV account code
		}
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//GL replacement
		WaitHelper.waitAdditional(2);
		
		if(!elements.get(9).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.SEVEN+pObject._ZERO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.SEVEN+pObject._ZERO)).sendKeys(elements.get(9));//Account
		}
		if(!elements.get(10).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.SEVEN+pObject._FIRST)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.SEVEN+pObject._FIRST)).sendKeys(elements.get(10));//Cost
		}
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Sales invoicing 
		WaitHelper.waitAdditional(2);
			
		if(!elements.get(11).equals("null")){
			//Purchasing Only
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.FOUR)).sendKeys(elements.get(11));//Usage
		}
			
		if(!elements.get(12).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE+pObject._ZERO)).clear();
		
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(12));//Account
	
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE+pObject._FIRST)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE+pObject._FIRST)).sendKeys(elements.get(13));//Cost
		}
		
		if(i==1){
			clickOnSTDCost();
			WaitHelper.waitAdditional(2);
			
			Actions builder = new Actions(driver);
			
			WaitHelper.waitAdditional(3);
			if(!elements.get(14).equals("null")){
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(14)).build().perform();//Effective date			
				WaitHelper.waitAdditional(3);
			}
			if(!elements.get(15).equals("null")){
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
				sendKeys(elements.get(15)).build().perform();//Standard cost
				WaitHelper.waitAdditional(3);
			}
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Item
		WaitHelper.waitAdditional(2);

		clickOnAcceptWarnings();

	}
	
	
	public void clickOnSTDCost(){
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.TWO+pObject.LABEL)).click();
	}
	
	
	/**
	 * Create Manufacturer : A098
	 */
	public  boolean createManufacturer(List<String> elements){
		log.info("In create Manufacturer");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Manufacturer code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Manufacturer Name
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);
		if(!getToolContentText().contains(message)){
			update = true;
		}
		return update;
	}
	
	/**
	 * Create manufacturer item : A098A
	 * @param elements
	 */
	public void createManufacturerItem(List<String> elements){
		log.info("In create Manufacturer");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Manufacturer
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Part number
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//Effective from
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Dafault
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Click on supplier : A099
	 */
	public void clickOnSupplier(){
		log.info("Clicking on supplier button");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.SEVEN+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Create Item supplier : A099
	 * @param elements
	 */
	public void createItemSupplier(List<String> elements){
		log.info("In create Item supplier");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Supplier
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(2));//Catalogue Description
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(3));//Max quantity limit			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Average lead time
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Insert price type :A100
	 * @param priceType
	 * @param elements
	 * @return
	 */
	public boolean insertPriceType(String priceType,String elements){
		log.info("In price type method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(priceType);//Price type
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Price type
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements);//Description
		}
		return update;
	}
	
	/**
	 * Insert Item price
	 * @param elements
	 * @param priceType
	 * @return
	 */
	
	public boolean insertItemPrice(List<String> elements,String priceType){
		log.info("In item price method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Supplier
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Supplier
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Price
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(priceType);//Price type
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(3));//Quantity
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Currency
			
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//UOM/Tax details tab
		
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(5));//Price UOM
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(6));//Quantity UOM
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(elements.get(7));//Code
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(elements.get(8));//Type
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(9));//Location
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.SIX)).sendKeys(elements.get(10));//Handling code

			update = true;			
		}
		return update;
	}
	
	/**
	 * Insert Item buyer : A101
	 */
	public void insertItemBuyer(List<String> elements){
		log.info("In Item buyer method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Buyer
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Default check box
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Amend ICA trading relationship : A102
	 * @param elements
	 */
	public void amendICATradingRelationship(List<String> elements){
		log.info("In ICA trading relationship method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).sendKeys(elements.get(1));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+pObject.ONE)).sendKeys(elements.get(2));//Accounts payable

		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).sendKeys(elements.get(3));//Common purchasing	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._+pObject.ONE)).sendKeys(elements.get(4));//Common purchasing

		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).sendKeys(elements.get(5));//Purchasing mgmt	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._+pObject.ONE)).sendKeys(elements.get(6));//Purchasing mgmt

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._ZERO)).sendKeys(elements.get(7));//Fixed assets	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._+pObject.ONE)).sendKeys(elements.get(8));//Fixed assets
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._ZERO)).sendKeys(elements.get(9));//Inventory mgmt	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._+pObject.ONE)).sendKeys(elements.get(10));//Inventory mgmt
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._+pObject.ONE)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
	}
	
	
	/**
	 * Insert IM Control accounts : A103
	 */
	public void insertImControl(List<String> elements){
		log.info("Inside IM control accounts method");
		
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Account code
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(2));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject._+pObject.ONE)).sendKeys(elements.get(3));//Accounts payable

		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._ZERO)).sendKeys(elements.get(4));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject._+pObject.ONE)).sendKeys(elements.get(5));//Accounts payable

		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._ZERO)).sendKeys(elements.get(6));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject._+pObject.ONE)).sendKeys(elements.get(7));//Accounts payable
	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._ZERO)).sendKeys(elements.get(8));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject._+pObject.ONE)).sendKeys(elements.get(9));//Accounts payable
		
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._ZERO)).sendKeys(elements.get(10));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject._+pObject.ONE)).sendKeys(elements.get(11));//Accounts payable
	
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._ZERO)).sendKeys(elements.get(12));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT+pObject._+pObject.ONE)).sendKeys(elements.get(13));//Accounts payable
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Default movement accounts
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._ZERO)).sendKeys(elements.get(14));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO+pObject._+pObject.ONE)).sendKeys(elements.get(15));//Accounts payable

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._ZERO)).sendKeys(elements.get(16));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject._+pObject.ONE)).sendKeys(elements.get(17));//Accounts payable

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._ZERO)).sendKeys(elements.get(18));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject._+pObject.ONE)).sendKeys(elements.get(19));//Accounts payable

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._ZERO)).sendKeys(elements.get(20));//Accounts payable		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._+pObject.ONE)).sendKeys(elements.get(21));//Accounts payable
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._+pObject.ONE)).sendKeys(Keys.ENTER);//Accounts payable

		WaitHelper.waitAdditional(2);
	
	}
	
	/**
	 * Insert IM company control : A104
	 */
	public void insertIMCompanyControl(String companyName,List<String> elements){
		log.info("In IM company control method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyName);//Company name
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(0));//Month

		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//YEAR
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//Account code

		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(3));//Default batch type

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(4));//Retain trans history

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(5));//UOM

		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(6));//Retain doc history

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(7));//UOM

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Stock valuation tab
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(8));//% of issue

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).sendKeys(elements.get(9));//Fixed amount

		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SIX)).click();//Allow issue price override
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN+pObject._+pObject.ONE)).click();//FIFO
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT+pObject._ZERO)).click();//Last order price
		
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Numbering tab
				
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.NINE)).click();//Use requisition
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.ZERO)).click();//Use picking lists
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.ONE)).click();//Company numbering
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.TWO)).sendKeys(elements.get(10));//Number length

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(elements.get(11));//Next number

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Defaults tab
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.NINE)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.FOUR)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.NINE)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.FOUR)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.NINE)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.FOUR)).sendKeys(elements.get(12));//Next number		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.NINE)).sendKeys(elements.get(12));//Next number
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.TWO)).sendKeys(elements.get(12));//Next number
		
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Batch types tab
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).sendKeys(elements.get(13));//Material issue
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SEVEN)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SEVEN)).sendKeys(elements.get(14));//Adjustment in
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT)).sendKeys(elements.get(15));//Adjustment out

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.NINE)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.NINE)).sendKeys(elements.get(16));//Stock disposal

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ZERO)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ZERO)).sendKeys(elements.get(17));//Return to store

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ONE)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ONE)).sendKeys(elements.get(18));//Store transfer

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.TWO)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.TWO)).sendKeys(elements.get(19));//Return to supplier

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).sendKeys(elements.get(20));//Revised cost variance

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.FOUR)).clear();		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.FOUR)).sendKeys(elements.get(21));//Standard cost reevaluation
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.FOUR)).sendKeys(Keys.ENTER);//Standard cost reevaluation
		
	}
	
	/************************************************
	 * Insert new Inventory store controls : A106 :  HAD
	 * **********************************************/

	
	public void insertInventoryStore(List<String> elements){
		log.info("In Inventory store control method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Store
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._ZERO)).click();//Standard store
		
		if(elements.get(18).equals(1)){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();//Use requisitions
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Use picking lists
		}
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//Account code

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(2));//BTZ element

		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(3));//Retain trans period

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TEN)).sendKeys(elements.get(4));//UOM

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(5));//Retain document history

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(6));//UOM
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Stock valuation tab
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE+pObject._+pObject.ONE)).click();//FIFO
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject._ZERO)).click();//Last order price
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._ZERO)).click();//Supply from purchasing
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(7));//% of issue

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(elements.get(8));//Fixed amount

		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.EIGHT)).click();//Issue price override

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Defaults tab
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(9));//Material issue

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(elements.get(10));//Adjustment in
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.TWO)).sendKeys(elements.get(11));//Adjustment out

		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.NINE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE+pObject.NINE)).sendKeys(elements.get(12));//Stock disposal

		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.SIX)).sendKeys(elements.get(13));//Return to store

		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.THREE)).sendKeys(elements.get(14));//Store transfer
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.NINE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.NINE)).sendKeys(elements.get(15));//Bin transfer
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.FOUR)).sendKeys(elements.get(16));//Picking list

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.SIX)).sendKeys(elements.get(17));//Stock take

		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.ZERO)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO+pObject.SIX)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE+pObject.THREE)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.ZERO)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR+pObject.SEVEN)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE+pObject.FOUR)).click();//Imm print
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX+pObject.ZERO)).click();//Imm print
		
		WaitHelper.waitAdditional(2);
		
	}
	
	/* Create transit store : A106*/
	public boolean createTransitStore(List<String> elements){
		log.info("In transit store method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Store
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Store
		WaitHelper.waitAdditional(1);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject._+pObject.ONE)).click();//In transit store
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//Account code
			update = true;
		}
		return update;
	}
	
	/**
	 * Insert stores hierarchy : A107
	 */
	public boolean createStoresHierarchy(List<String> elements){
		log.info("In stores hierarchy method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));
	
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(3));
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}
	
	/**
	 * Insert IM Item/Store controls : A108
	 * 
	 */
	public boolean insertItemStoreControl(List<String> elements){
		log.info("In IM item store controld method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Item
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Store
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Store
		WaitHelper.waitAdditional(2);
		if(!isPrimaryDetailsTabDisplayed()){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//General/On costs tab	
		}
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Default bin
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//% of issue
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();//Store
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(4));//Fixed costs
			
			if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).isSelected()){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SEVEN)).click();//Issue price override
			}

			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Controls/Valuation tab	
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject._ZERO)).click();//Supply from purc
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(6));//Lead time
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(7));//Lead time UOM
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(8));//Re-order level

			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(9));//Re-order quantity
			
			if(!getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._+elements.get(10))).isSelected()){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject._+elements.get(10))).click();//FIFO
			}
			if(!getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX+pObject._+elements.get(11))).isSelected()){
				getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX+pObject._+elements.get(11))).click();//LOP
			}
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}
	
	/**
	 * Amend control accounts : A109
	 */
	public void amendControlAccounts(List<String> elements){
		log.info("In Amend control accounts method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(1));//Accounts payable
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(2));//Common purchasing

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(3));//Purchasing management

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(4));//Inventory management

		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Insert security range : A110
	 */
	public void insertSecurityRange(List<String> elements){
		log.info("In Insert security range method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Range list code

		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description

		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//System
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Code Id

		WaitHelper.waitAdditional(2);
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
		sendKeys(elements.get(4)).build().perform();//From value
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
		sendKeys(elements.get(5)).build().perform();//To value
		WaitHelper.waitAdditional(3);	
	}
	
	/**
	 * Create security groups : A111
	 */
	public boolean insertSecurityGroup(List<String> elements){
		log.info("In security group method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}
	
	/**
	 * Click on Denial button
	 */
	public void clickOnDenials(){
		log.info("In click on denials method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.LABEL)).click();
		WaitHelper.waitAdditional(1);
	}
	
	/**
	 * Select deny all check box
	 */
	public void selectDenyAll(){
		log.info("Click on Deny all");
		WaitHelper.waitAdditional(1);
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();
		}
		WaitHelper.waitAdditional(1);
	}
	
	/**
	 * Verify selection of Deny all
	 * @return
	 */
	public boolean verifyDenyAll(){		
		WaitHelper.waitAdditional(1);
		return driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().contains("Y");
	}
	
	/**
	 * Create users for security - A112
	 * @param elements
	 * @param companyId
	 * @return
	 */
	public boolean insertUserCompany(List<String> elements,String companyId){
		log.info("In insert company method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//User
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(companyId);//Company
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Company
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//Security group
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(1));//Report security
			WaitHelper.waitAdditional(1);
			update = true;
		}
		return update;
	}
	
	/**
	 * Insert Access code : A113
	 * @param elements
	 */
	public void insertAccessCode(List<String> elements){
		log.info("In access code method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Access code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Access code  expression
		WaitHelper.waitAdditional(2);		
	}
	
	/**
	 * Insert security group access code : A114
	 * @param elements
	 */
	public void insertSecurityGroupAccessCode(List<String> elements){
		log.info("In Security group access code");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//System
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Security group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Update access code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//Enquire access code
		WaitHelper.waitAdditional(1);
	}
	
	
	/** 
	 * A115 : ADB
	 */
	
	public boolean createSecurityGroup(List<String> elements){
		log.info("In security group method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Group
		
		if(!getToolContentText().contains(message)){	
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1); 
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
			WaitHelper.waitAdditional(2);
			update = false;
		}
		
		return update;
	}
	
	/**
	 * Click on Deny all
	 * @param i
	 * @return
	 */
	public boolean clickOnDenyAll()	
	{
		boolean update = false;
		log.info("In click on deny all method");
		if(!getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.SIX)).click();
			update = true;
		}
		return update;
	}
	
	/**
	 * Insert range code : A115 : ADH
	 * @param elements
	 * @return
	 */
	public boolean createRangeListCode(List<String> elements){
		log.info("In create range list code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(1));//Range list code		
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(2));//Description
	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//System
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//System
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Code id
		if(!getToolContentText().contains(message)){
				
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Code id
	
			WaitHelper.waitAdditional(2);
			Actions builder = new Actions(driver);
			
			/*Balance class field*/
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(elements.get(4)).build().perform();//From value
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(5)).build().perform();//To value
			WaitHelper.waitAdditional(2);
			update = true;
		}
		return update;
	}
	
	/**
	 * Create access code : A115 :ADJ
	 * @param elements
	 * @return
	 */
	public boolean createAccessCode(List<String> elements){
		log.info("In create access code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//System
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//System
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Access code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Access code
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(2));//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(3));//Access code
			WaitHelper.waitAdditional(1);
			update = true;
		}
		
		return update;
	}
	
	/**
	 * Create Security group access Code : A115 :ADL
	 * @param elements
	 * @return
	 */
	public boolean createSecurityGroupAccessCode(List<String> elements){
		log.info("In create access code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//System
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//System
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Security group
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Security group
		WaitHelper.waitAdditional(2);
		
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Update access code
	
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(3));//Enquiry access code
			update = true;
		}
		
		return update;
	}
		
	/**
	 * Create role : A115 :AF2
	 * @param elements
	 * @return
	 */
	public boolean createRole(List<String> elements){
		log.info("In create access code method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Role
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Role
		WaitHelper.waitAdditional(2);
				
		if(!getToolContentText().contains(message)){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description			

			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(2));//Functional Security group
	
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(3));//Data security group
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(3));//Menu
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(Keys.ENTER);//Menu
			update = true;
		}
		
		return update;
	}
	
	/*A115 : Create user roles AF4*/
	public boolean createUserRole(String company,List<String> elements){
		log.info("In user role methods");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(company);//Company
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//User
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(1));//Role
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(Keys.ENTER);//Role
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			if(elements.get(2).equals("1")){
				getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.ONE+pObject.SIX)).click();
			}
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}
	
	/**
	 * Verify security element list : A116
	 * @return
	 */
	public boolean isSecurityElementListDisplayed(){
		log.info("In security element list");
		boolean securityData = false;
		WaitHelper.waitAdditional(2);
		String tableHeader = getDriver().findElement(By.xpath("//div[2]/div/div/div[2]/div/div/span[3]")).getText();//Table header
		
		String securityElement = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[2]/div/div")).getText();//Security element
		String Description = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText();//Description
		String parentElement = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText();//Parent Element
		
		int securityCount = getDriver().findElements(By.xpath("(//div[2]/div/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2])")).size();//Security element list
		System.out.println("------"+securityCount);
		
		if(tableHeader.equals("Security Element List") && securityElement.equals("Security Element") && Description.equals("Description")
				&&parentElement.equals("Parent Element") && securityCount>0){
			securityData = true;
		}
		
		return securityData;
	}
	
	/**
	 * Create Stock Take on Using Positive Adjustment : AD01004
	 * @param elements
	 */
	public void enterPositiveAdjLineDetails(List<String> elements){
		log.info("In positive adjustment line method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Store
		
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")))
		.click().sendKeys(elements.get(1)).build().perform();//Item
		WaitHelper.waitAdditional(2);

		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")))
		.click().sendKeys(elements.get(2)).build().perform();//Quantity
		WaitHelper.waitAdditional(2);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")))
		.click().sendKeys(elements.get(2)).build().perform();//UOM
		WaitHelper.waitAdditional(2);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")))
		.click().sendKeys(elements.get(2)).build().perform();//Price
		WaitHelper.waitAdditional(2);

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);//Store
		
	}
	
	/**
	 * Generate IM Audit Reports : AD01008
	 * @param companyName
	 * @param elements
	 */
	public void generateAuditReports(String companyName,List<String> elements){
		log.info("In Audit reports method");

		if(elements.get(2).equals("ED4")){
			log.info("Enter ED4 process details");
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(1));//Request
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(companyName);//Company
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(Keys.ENTER);//Company
			
			clickOnSubmit();

		}
		if(elements.get(2).equals("PQ1")){
			log.info("Enter process details");
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(1));//Request
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(companyName);//Company
			WaitHelper.waitAdditional(1);
			
		    Calendar currentMonth = Calendar.getInstance();
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		    String currDate = dateFormat1.format(currentMonth.getTime());
			
		    getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).sendKeys(currDate);//From date
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(currDate);//To date
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(Keys.ENTER);
			WaitHelper.waitAdditional(1);
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(3));//Report ind
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(3));//Report ind
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();//Calendar
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(4));//Calendar
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(Keys.ENTER);//Calendar
			WaitHelper.waitAdditional(2);
			clickOnSubmit();
			WaitHelper.waitAdditional(2);
		}		
		if(!(elements.get(2).equals("PQ1")) && (!elements.get(2).equals("ED4"))) {
			
			log.info("Enter process details");
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(1));//Request
			WaitHelper.waitAdditional(5);
			Actions builder = new Actions(driver);
			
			/*Balance class field*/
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(companyName).build().perform();
			WaitHelper.waitAdditional(3);//Company
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(Keys.ENTER).build().perform();
			WaitHelper.waitAdditional(5);//submit enter

			clickOnSubmit();

		}
	}
	
	
	/************************************************
	 * Stock Balance : HBA (AD02001)
	 * **********************************************/
	/**
	 * Search Item store
	 * @param company
	 * @param element
	 */
	public void searchItemStore(String company,List<String> element){
		log.info("Search item store");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(5)){
			clickOnSections(0);
		}
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(company);//Company
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(element.get(1));//Store
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(element.get(0));//Item
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * Amend store control
	 */
	public void amendStoreControl(){
		log.info("Amend store control");
		WaitHelper.waitAdditional(2);
		if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.THREE)).click();// Use Requisitions
		}
		if(getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).isSelected()){
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Use Picking Lists
		}
	}

	/*Current Period : AD03016*/
	public List<String> getPeriodAndYear(){
		log.info("In get period and year");
		WaitHelper.waitAdditional(2);
		List<String> yearDetails = new ArrayList<String>();
		yearDetails.add(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText());//Period
		yearDetails.add(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText());//Year
		return yearDetails;
	}
	
	/**
	 * Get company control
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	public List<String> getCompanyControl(int i,int j,int k){
		log.info("In get period and year");
		WaitHelper.waitAdditional(2);
		List<String> yearDetails = new ArrayList<String>();

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+k)).click();//Numbering/GL/Supplier
		
		yearDetails.add(getDriver().findElement(By.id(pObject.ZERO_+i)).getAttribute("value"));
		yearDetails.add(getDriver().findElement(By.id(pObject.ZERO_+j)).getAttribute("value"));
		
		return yearDetails;
	}
	
	
	/**
	 * Create Stock Adjustment : AD02001
	 */
	public void addLineDetails(List<String> elements,String reference){
		Actions builder = new Actions(driver);
				
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().
				sendKeys(elements.get(1)).build().perform();
		WaitHelper.waitAdditional(5);//Item
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
				sendKeys(elements.get(2)).build().perform();
		WaitHelper.waitAdditional(5);//Quantity
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
				sendKeys(elements.get(3)).build().perform();
		WaitHelper.waitAdditional(5);//UOM
		if(reference.equals("A")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
				sendKeys(elements.get(4)).build().perform();
			WaitHelper.waitAdditional(5);//Price
		}
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
				sendKeys(Keys.ENTER).build().perform();
		WaitHelper.waitAdditional(5);
	}
		
	/**
	 * Click on Currenct stock   
	 * @param elements
	 */
	public void clickOnCurrentStock(){
		log.info("Click on current stock");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.LABEL)).click();//Current stock
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify current stock value
	 * @param elements
	 */
	public boolean verifyCurrenctStock(List<String> elements,int i){
		log.info("Verify currenct stock details");
		WaitHelper.waitAdditional(2);
		boolean output = false;
		output = getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).getAttribute("value").equals(elements.get(2));//Physical quantity
		output = getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).getText().equals(elements.get(3));//Available quantity
		output = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).getText().equals(elements.get(4));//Notional quantity
		WaitHelper.waitAdditional(2);
		if(i==1){			
			clickOnReOrderLevel();
			WaitHelper.waitAdditional(2);
			output = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).getText().equals(elements.get(5));//Re-order value
			output = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).getText().equals(elements.get(6));//Re-order quantity
		}
		return output;
	}

	/**
	 * Click on re-order level
	 */
	public void clickOnReOrderLevel(){
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Reorder levels/Average cost tab	
	}
	
	
	/**
	 * Click on Item totals : 
	 * @param elements
	 */
	public void clickOnTotalItems(){
		log.info("Click on item totals");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO+pObject.LABEL)).click();//Item totals
		WaitHelper.waitAdditional(2);
	}

	/**
	 * Verify total items
	 * @param elements
	 */
	public boolean verifyTotalItems(List<String> elements){
		log.info("Verify total items");
		boolean verify = false;
		WaitHelper.waitAdditional(1);
		verify =getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).getText().equals(elements.get(7));//Get total value physical quantity
		verify = getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).getText().equals(elements.get(8));//Get total value available quantity
		verify = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).getText().equals(elements.get(9));//Get total value national quantity
		WaitHelper.waitAdditional(1);
		return verify;
	}	

	/**
	 * Insert material issue 
	 * @param elements
	 */
	public void insertMaterialIssue(List<String> elements,String reference){
		log.info("Inserting material issue");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(1));//Circulation
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(0));//Element : EAST
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//GL details tab	
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div[1]/table/tbody/tr[1]/td[1]"))).click().sendKeys(elements.get(5)).build().perform();
		WaitHelper.waitAdditional(5);//Account
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div[1]/table/tbody/tr[1]/td[2]"))).click().sendKeys(elements.get(6)).build().perform();
		WaitHelper.waitAdditional(5);//Cost
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//GL details tab
		addLineDetails(elements,reference);
		WaitHelper.waitAdditional(5);
	}
	
	public void addStoreDetails(List<String> elements){
		log.info("Add store details");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(0));//Circulation
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(Keys.ENTER);//Circulation
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//Element : EAST
		WaitHelper.waitAdditional(1);
	}
	
	public void sortValues(){
		log.info("Sort Journal values");
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[2]/div/div"))).click().build().perform();
		WaitHelper.waitAdditional(3);//Number

	}
	
	/*Verify Journal details*/
	public boolean verifyJournalDetails(int i,List<String> elements){
		log.info("Verify journal details");
		WaitHelper.waitAdditional(2);
		boolean verify = false;
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[1]"))).click().sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).
			build().perform();
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().contains("Account");//account header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[4]")).getText().equals(elements.get(0));//account value
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().equals("Cost");//cost header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[5]")).getText().equals(elements.get(1));//cost value

		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[9]/div/div")).getText().equals("Description");//description header
		int description = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[9]")).getText().length();//description value
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[10]"))).click().sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).
			build().perform();
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[10]/div/div")).getText().equals("Base Value");//base header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[10]")).getText().equals(elements.get(2));//base value

		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[11]/div/div")).getText().equals("Quantity");//quantity header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[11]")).getText().equals(elements.get(3));//quantity value

		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[18]"))).click().sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).
		build().perform();
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[19]/div/div")).getText().equals("Reference");//reference header
		int referenceHeader = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[19]")).getText().length();//reference value
		if(i==1 || i==2){
			if(referenceHeader>0 && description>0){			
				verify = true;
			}
		}
		return verify;
	}
	
	/*Verify total Stock Balance and valuation*/
	public boolean verifyTotalStockBalance(List<String> elements){
		log.info("Verify total stock balance ");
		WaitHelper.waitAdditional(2);
		return getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).getText().equals(elements.get(2));//Total currenct stock value
		 
	}

	/**
	 * Verify store Item : Verify column header and column value
	 * @param elements
	 */
	public boolean verifyStoreItemValues(List<String> elements,int i){
		log.info("Verify store item header and value");
		boolean verify = false ;
		WaitHelper.waitAdditional(2);
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[1]/div/div")).getText().contains("Item");//Item header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[1]")).getText().equals(elements.get(0));//Item value
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[2]/div/div")).getText().equals("Physical");//Physical header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")).getText().equals(elements.get(3));//Physical value

		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText().equals("Allocated");//Allocated header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText().equals(elements.get(4));//Allocated value

		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().equals("Stock Value");//Stock header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().equals(elements.get(5));//Stock value

		if(i==1){
			verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().equals("Physical Avail");//Physical header
			verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText().equals(elements.get(6));//Physical value
	
			verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[6]/div/div")).getText().equals("Reqs & Orders");//Reqs & orders header
			verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]")).getText().equals(elements.get(7));//Reqs & orders value
		}
		return verify;
	}
	
	/**
	 * Verify store Item : Verify column header and column value
	 * @param elements
	 */
	public boolean verifyStoreItemValuation(List<String> elements,int i){
		log.info("Verify store item header and value");
		WaitHelper.waitAdditional(2);
		boolean verify = false;
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[1]/div/div")).getText().contains("GRN");//GRN
		String GRN = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[1]")).getText();//GRN value
		if(!GRN.equals(null)){
			verify = true;
		}
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[2]/div/div")).getText().equals("Receipt Date");//Receipt Date
		String date = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")).getText();//Receipt Date value
		if(!date.equals(null)){
			verify = true;
		}
		
		
		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText().equals("Remained Quantity");//Receipt Date header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText().equals(elements.get(0));//Receipt Date value

		verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().equals("Receipt Price");//Receipt Price header
		verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().equals(elements.get(1));//Receipt Price value

		if(i==1){
			verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().equals("Original Value");//Original Value header
			verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText().equals(elements.get(2));//Original Value value
	
			verify = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[6]/div/div")).getText().equals("Source");//Source header
			verify = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]")).getText().equals(elements.get(3));//Source value
		}
		return verify;
	}

	
	/**
	 * Verify store Item : Verify column header and column value
	 * @param elements
	 */
	public boolean verifyStoreItem(List<String> elements,int i){
		log.info("Verify store item header and value");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[1]/div/div")).getText().contains(elements.get(0));//System
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[2]/div/div")).getText().equals(elements.get(1));//Supplier
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")).getText().equals(elements.get(2));

		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText().equals(elements.get(3));//Reference
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText().equals(elements.get(4));

        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        String currDate = dateFormat1.format(currentMonth.getTime());

		
        update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().equals(elements.get(5));//Date
        update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().equals(currDate);

        update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().equals(elements.get(6));//Gross
        update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText().equals(elements.get(7));

		if(i==1){
			update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[6]/div/div")).getText().equals(elements.get(8));//Tax
			update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]")).getText().equals(elements.get(9));
		}
		
		return update;
	}
	
	/**
	 * Click on valuation
	 */
	public void clickOnValuation(){
		log.info("Click on Valuation button");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();
		WaitHelper.waitAdditional(2);
	}

	/************************************************
	 * Create classification code : AT2 AD09004
	 * **********************************************/
	/**
	 * Create classification code
	 * @param elements
	 * @return
	 */
	public boolean createClassificationCode(List<String> elements){
		log.info("Create classification code");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();//Classification
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		if(!getToolContentText().contains(message)){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();//Description
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));
			update = true;
		}
		WaitHelper.waitAdditional(2);
		return update;
	}
	
	/************************************************
	 * Classification Parentage : PYB AD09005
	 * **********************************************/
	
	public void createClassificationStructure(List<String> elements){
		log.info("In classification structure method");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();//Classification structure
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();//Description
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(2));	

	}
	
	public void amendPurchasingCompanyControl(List<String> elements){
		log.info("Amend purchasing company control");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Good receipting tab
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.EIGHT)).sendKeys(elements.get(0));//Returning order code
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Miscellaneous tab
		WaitHelper.waitAdditional(2);	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.ONE)).sendKeys(elements.get(1));//Classification structure

	}
	
	/**
	 * Create classification parentage
	 * @param elements
	 */

	public void classificationParentage(List<String> elements,int i){
		log.info("Inside classification parentage");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();//Structure
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));		
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();//Classification
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));		
		WaitHelper.waitAdditional(1);
		
		WaitHelper.waitAdditional(1);
		if(i==1){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();//Parent Classification
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(Keys.ENTER);
		}
	}
	
	
	/************************************************
	 * Create Item Classification : PYB AD09006
	 * **********************************************/
	/**
	 * Click on Classification button
	 */
	public void clickOnClassification(){
		
		log.info("Clicking on Classification button");
		WaitHelper.waitAdditional(3);
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Classification")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * AD09006 - PIA
	 * @param elements
	 */
	public void createItemClassification(List<String> elements){
		log.info("Create Item Classification");
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(elements.get(1)).build().perform();
		WaitHelper.waitAdditional(5);//Classification
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys("Y").build().perform();
		WaitHelper.waitAdditional(5);//Default
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys(Keys.ENTER).build().perform();
		WaitHelper.waitAdditional(5);//Default		
	}
	
	
	/**
	 * Get document list
	 * @return
	 */
	public int getDocumentList(){
		log.info("Get document list");
		WaitHelper.waitAdditional(2);
		int count = 0;
		List<WebElement> documentList = getDriver().findElements(By.xpath("//div[2]/div[2]/div/div/div/div/table/tbody/tr/td[5]"));//Security element list
		
		for(WebElement wb : documentList){
			if(wb.getText().length() > 0)
			{
				count++;
			}
		}
		return count;
	} 
	
	
	/************************************************
	 * Create Goods Receiving : PYB AD02009
	 * **********************************************/

	/**
	 * Create order
	 * @param elements
	 */
	public void createOrderCode(List<String> elements){
		log.info("In create order code method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Order code
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Supplier tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Supplier

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//Address number

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Order currency tab	
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(3));//Element

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(4));//Location

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).sendKeys(elements.get(5));//Buyer
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(6));//Currency code

		if(!elements.get(7).equals("NULL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(7));//Invoice location
		}
		if(!elements.get(8).equals("NULL")){
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(8));//Circulation

		}
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Settlement/Authorisation tab	
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(elements.get(9));//Terms

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(10));//Discount

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Primary detail lines tab	
		WaitHelper.waitAdditional(4);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(11)).build().perform();
		WaitHelper.waitAdditional(5);//Item
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().
			sendKeys(elements.get(12)).build().perform();
		WaitHelper.waitAdditional(5);//Description
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().
			sendKeys(elements.get(13)).build().perform();
		WaitHelper.waitAdditional(5);//Quantity
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().
			sendKeys(elements.get(14)).build().perform();
		WaitHelper.waitAdditional(5);//Price

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Delivery tab
		WaitHelper.waitAdditional(4);		
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().
			sendKeys(elements.get(15)).build().perform();
		WaitHelper.waitAdditional(5);//Location		
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(16)).build().perform();
		WaitHelper.waitAdditional(5);//QUOM
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(17)).build().perform();
		WaitHelper.waitAdditional(5);//PUOM
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());
		
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().
			sendKeys(currDate).build().perform();
		WaitHelper.waitAdditional(5);//Due date

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SIXTH_TAB)).click();//GL details tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[6]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(18)).build().perform();
		WaitHelper.waitAdditional(5);//Account
		builder.moveToElement(driver.findElement(By.xpath("//div[6]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(19)).build().perform();
		WaitHelper.waitAdditional(5);//Cost
		
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+7)).click();//Status tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[7]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[8]"))).click().
			sendKeys(elements.get(20)).build().perform();
		WaitHelper.waitAdditional(5);//Store
		builder.moveToElement(driver.findElement(By.xpath("//div[7]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[9]"))).click().
		sendKeys(elements.get(21)).build().perform();
		WaitHelper.waitAdditional(5);//location
	
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+8)).click();//Tax/Discounts tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[8]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().
			sendKeys(elements.get(22)).build().perform();
		WaitHelper.waitAdditional(5);//Code
		builder.moveToElement(driver.findElement(By.xpath("//div[8]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(23)).build().perform();
		WaitHelper.waitAdditional(5);//Type
		builder.moveToElement(driver.findElement(By.xpath("//div[8]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(24)).build().perform();
		WaitHelper.waitAdditional(5);//Location
		builder.moveToElement(driver.findElement(By.xpath("//div[8]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[5]"))).click().
			sendKeys(elements.get(25)).build().perform();
		WaitHelper.waitAdditional(5);//Handling code

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Receive Goods :2009
	 */
	public void addGoodsReceive(List<String> elements){
		log.info("Adding goods receive");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(0));//Advice note
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(1));//Delivery
		clickOnPrimaryDetailsTab();
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		if(!elements.get(2).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").build().perform();//Remove value 
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(2)).build().perform();//Add received qty
			WaitHelper.waitAdditional(3);
		}
		if(!elements.get(3).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(elements.get(3)).build().perform();//UOM
			WaitHelper.waitAdditional(3);
		}
		if(!elements.get(4).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").build().perform();//Remove value 
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]"))).click().sendKeys(elements.get(4)).build().perform();//Location

		}
		WaitHelper.waitAdditional(3);

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Advised tab
		if(!elements.get(5).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[5]/div[3]/div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").build().perform();//Remove value 
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[5]/div[3]/div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().sendKeys(elements.get(5)).build().perform();//Advised qty
			WaitHelper.waitAdditional(3);
		}
		if(!elements.get(6).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[5]/div[3]/div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().sendKeys(elements.get(6)).build().perform();//UOM
			WaitHelper.waitAdditional(3);
		}
		if(!elements.get(7).equals("NULL")){
			builder.moveToElement(driver.findElement(By.xpath("//div[5]/div[3]/div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(7)).build().perform();//Receipt
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[5]/div[3]/div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(Keys.ENTER).build().perform();//Receipt				
		}
	}
	
	/************************************************
	 * Create Service Order : PYB AD02013
	 * **********************************************/

	/**
	 * Create service order
	 * @param elements
	 */
	public void createServiceOrderCode(List<String> elements){
		log.info("In create order code method");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Order code
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Supplier tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Supplier

		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(2));//Address number

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Order currency tab	
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(3));//Element

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(4));//Location
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(5));//Invoice location


		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE)).sendKeys(elements.get(6));//Buyer
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.NINE)).sendKeys(elements.get(7));//Currency code
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Settlement/Authorisation tab	
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.THREE)).sendKeys(elements.get(8));//Terms

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(9));//Discount

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Primary detail lines tab	
		WaitHelper.waitAdditional(4);
		Actions builder = new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(10)).build().perform();
		WaitHelper.waitAdditional(5);//Item

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Delivery tab
		WaitHelper.waitAdditional(4);		
		
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().
		sendKeys(elements.get(11)).build().perform();
		WaitHelper.waitAdditional(5);//Location
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());
		
		builder.moveToElement(driver.findElement(By.xpath("//div[5]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().
			sendKeys(currDate).build().perform();
		WaitHelper.waitAdditional(5);//Due date

		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SIXTH_TAB)).click();//GL details tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[6]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().
			sendKeys(elements.get(12)).build().perform();
		WaitHelper.waitAdditional(5);//Account
		builder.moveToElement(driver.findElement(By.xpath("//div[6]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(13)).build().perform();
		WaitHelper.waitAdditional(5);//Cost
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SEVENTH_TAB)).click();//Status tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[7]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[5]"))).click().
			sendKeys(elements.get(14)).build().perform();
		WaitHelper.waitAdditional(5);//Service value
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.TAB+9)).click();//Recurring details tab
		WaitHelper.waitAdditional(4);
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().
			sendKeys(elements.get(15)).build().perform();
		WaitHelper.waitAdditional(5);//Interval
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().
			sendKeys(elements.get(16)).build().perform();
		WaitHelper.waitAdditional(5);//Frequency
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().
			sendKeys(elements.get(17)).build().perform();
		WaitHelper.waitAdditional(5);//Occurrences
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(5);//Occurrences
	}
	
	/**
	 * click On Order explode
	 */
	public void clickOnOrderExplode(){
		log.info("Clicking on Order explode button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Order")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Explode")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * click On maintain parameters
	 */
	public void clickOnAuthorisationLineView(){
		log.info("Clicking on authorisation button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Authorisation")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Line View")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Authorise order
	 */
	public void selectOrderAuthorisor(){
		log.info("In select Order Authorisor mrthod");
		WaitHelper.waitAdditional(2);
			
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.LABEL)).click();//Authorie button
		WaitHelper.waitAdditional(4);	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.LABEL)).click();//Select button
		WaitHelper.waitAdditional(4);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//Authorize button
		WaitHelper.waitAdditional(5);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.LABEL)).click();//Confirm button				
		WaitHelper.waitAdditional(4);
		clickOnReturnButton(8);
		WaitHelper.waitAdditional(4);
	}
	
	public void selectOrder(){
		Actions builder = new Actions(driver);

		for(int i=2;i<=5;i++){
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+(i)+"]/table/tbody/tr/td[1]"))).click().build().perform();//Select first row
			WaitHelper.waitAdditional(4);
			
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE+pObject.LABEL)).click();//Select button
			WaitHelper.waitAdditional(5);						
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//Authorize button
			WaitHelper.waitAdditional(5);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO+pObject.LABEL)).click();//Confirm button
			WaitHelper.waitAdditional(5);
			
		}
		
		clickOnReturnButton(8);
		WaitHelper.waitAdditional(5);
	}
	/**
	 * Enter goods details on pop up
	 * @param elements
	 */
	public void enterGoodsDetailsInPopUp(String elements){
		log.info("Enter goods details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.FOUR+pObject._+pObject.THREE)).sendKeys(elements);//Order
		getDriver().findElement(By.id(pObject.FOUR+pObject._ZERO+pObject.LABEL)).click();//Fetch
		WaitHelper.waitAdditional(2);
	}
	
	/************************************************
	 * Enter Invoices : GBB AD02010
	 * **********************************************/
	public void enterInvoice(List<String> elements,String year){
		log.info("Enter invoice details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(0));//Description
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//No.of transaction
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Total gross amount
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(3));//Total tax amount
		
		if(year.equals("Invoice")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Period
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(5));//Year
			getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(Keys.ENTER);//Year
		}
		if(year.equals("Transaction")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(elements.get(4));//Mode
			getDriver().findElement(By.id(pObject.ZERO_+pObject.EIGHT)).sendKeys(Keys.ENTER);//Mode
		}
		WaitHelper.waitAdditional(2);		
	}
	
	/**
	 * Get invoice details and amend invoice
	 * @param elements
	 * @param order
	 * @return
	 */
	public boolean getInvoice(List<String> elements,String order){
		log.info("In get invoice details method");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(order);//Order
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(0));//Order
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(1));//Gross amount
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).sendKeys(elements.get(2));//Tax amount
		
        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        String currDate = dateFormat1.format(currentMonth.getTime());
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(currDate);//Date

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(currDate);//Due Date
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(Keys.ENTER);//Order
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(currDate);//Date
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Tax/Discount tab
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ONE)).sendKeys(Keys.ENTER);//Tax type
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Main tab
		
		int postCode = getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).getAttribute("value").length();
		int element = getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).getAttribute("value").length();
		int period = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).getAttribute("value").length();
		int year = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).getAttribute("value").length();
		int subType = getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).getAttribute("value").length();
		
		if(postCode>0 && element>0 && period>0&& year>0 && subType>0){
			update = true;
		}
		return update;
	}
	
	/*Click on new transaction*/
	public void clickOnNewTransaction(){
		log.info("Click on transaction");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//New Transaction
		WaitHelper.waitAdditional(2);
	}
	
	/*Amend reference : A11008*/
	public void amendTransactionDetails(String element){
		log.info("Amend transaction details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(element);//Reference
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		
	    Calendar currentMonth = Calendar.getInstance();
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
	    String currDate = dateFormat1.format(currentMonth.getTime());

	    getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(currDate);//Due date
	    WaitHelper.waitAdditional(1);
	    getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(Keys.ENTER);//Due date
	    WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Verify transaction details
	 * @param elements
	 * @return
	 */
	public boolean verifyTransactionDetails(String elements){
		log.info("Verify transaction details");
		boolean amendInvoice = false;
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText().contains("Transaction Ref");//Transaction Ref
		String tranRef = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText();// value
		if(tranRef.contains(elements)){
			amendInvoice = true;
			log.info("Transaction reference is : " + tranRef);
		}
		
		getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().contains("Transaction date");//Transaction date
		String date = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText();// value
		
	    Calendar currentMonth = Calendar.getInstance();
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
	    String currDate = dateFormat1.format(currentMonth.getTime());

	    if(date.equals(currDate)){
	    	amendInvoice = true;
	    }
		
		return amendInvoice;
	}
		
	/*Amend AP company control*/
	public void amendAPCompanyControl(int i,String value){
		log.info("Amend company control");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Tax discount tab
		WaitHelper.waitAdditional(1);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+i)).sendKeys(value);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+i)).sendKeys(value);
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Enter transaction details
	 * 
	 */
	public void enterTransactionDetails(List<String> elements,String currDate,String dueDate){
		log.info("Enter transaction details");
		WaitHelper.waitAdditional(2);
		if(!elements.get(0).equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Order
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Supplier
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(2));//Post code
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.NINE)).sendKeys(elements.get(3));//Element
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(elements.get(4));//Period
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.TWO)).sendKeys(elements.get(5));//Year
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.THREE)).sendKeys(elements.get(6));//Reference
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR)).sendKeys(elements.get(8));//Sub type
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(currDate);//Current Date
		WaitHelper.waitAdditional(2);
		if(!dueDate.equals("null")){
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).clear();//Due Date
			WaitHelper.waitAdditional(1);
			getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SEVEN)).sendKeys(dueDate);//Due Date
			WaitHelper.waitAdditional(1);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.EIGHT)).sendKeys(elements.get(7));//Gross amount

		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.ZERO)).sendKeys(elements.get(9));//Tax amount

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Currency tab
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(elements.get(10));//Currency
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FIVE)).sendKeys(Keys.ENTER);//Currency
		WaitHelper.waitAdditional(2);
		if(elements.get(11).equals(1)){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Tax discount tab
			WaitHelper.waitAdditional(2);
			getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FIVE+pObject.SIX)).click();//Tax only
			WaitHelper.waitAdditional(1);
		}
		if(!elements.get(12).equals("null") && !elements.get(12).equals("Week")){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.THIRD_TAB)).click();//Tax discount tab
			WaitHelper.waitAdditional(2);

			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ZERO)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ZERO)).sendKeys(elements.get(12));//Tax code

			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ONE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR+pObject.ONE)).sendKeys(elements.get(13));//Tax type

		}
		if(elements.get(12).equals("Week")){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Miscellaneous tab
			WaitHelper.waitAdditional(2);

			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.TWO)).sendKeys(elements.get(12));//Recurring frequency
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.TWO)).sendKeys(elements.get(12));

			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).clear();
			getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN+pObject.THREE)).sendKeys(elements.get(13));//Occurrences

		}
	}

	/**
	 * Enter invoice details
	 * @param elements
	 */
	public void enterInvoiceDetails(List<String> elements){
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		if(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]")).getText().length()>0){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").
			sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").build().perform();//Remove value 
			WaitHelper.waitAdditional(3);
		}
		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(0)).build().perform();//Description
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(1)).build().perform();//Invoiced price
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(elements.get(2)).build().perform();//Invoiced qty
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(Keys.ENTER).build().perform();//Invoiced qty
		WaitHelper.waitAdditional(3);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//GL details tab
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(3)).build().perform();//Account		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(4)).build().perform();//Cost		
	
	}
	
	
	/************************************************
	 * Enter Invoices : HBA AD03001
	 * **********************************************/

	public void addStoreTransferDetails(List<String> elements){
		log.info("Add store transfer details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Store
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Receipt store
	}
	
	/**
	 * Click on Forward button
	 */
	public void clickOnFwdButton(){
		log.info("Clicking on Forward button");
		WaitHelper.waitAdditional(3);
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Fwd")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/**
	 * Click on Forward button
	 */
	public void clickOnDetailButton(){
		log.info("Clicking on detail button");
		WaitHelper.waitAdditional(3);
		List<WebElement> wbs = getAlllButton();
		for(WebElement wb : wbs){
			if(wb.getText().equals("Detail")){
				wb.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	private WebElement getReportValue(int i){
		return getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td"));
	}
	
	/**
	 * Verify RHED report
	 * @param elements
	 * @return
	 */
	public boolean verifyRHEDReport(List<String> elements){
		log.info("Verify RHED report template");
		boolean verifyReport = false;
		WaitHelper.waitAdditional(2);

		verifyReport =  getReportValue(3).getText().trim().contains(elements.get(1));//From store
		verifyReport =  getReportValue(4).getText().trim().contains(elements.get(2));//Road and detail
		verifyReport =  getReportValue(5).getText().trim().contains(elements.get(3));//Tel
		verifyReport =  getReportValue(6).getText().trim().contains(elements.get(4));//Fax
		verifyReport =  getReportValue(7).getText().trim().contains(elements.get(5));//Contact
		verifyReport =  getReportValue(11).getText().trim().contains(elements.get(6));//To store
		verifyReport =  getReportValue(12).getText().trim().contains(elements.get(7));//To Road
		
		verifyReport =  getReportValue(19).getText().trim().contains(elements.get(8));//Document reference
		WaitHelper.waitAdditional(1);
		clickOnFwdButton();
		
		verifyReport =  getReportValue(1).getText().trim().contains(elements.get(9));//Line item
		verifyReport =  getReportValue(2).getText().trim().contains(elements.get(10));//Description
		verifyReport =  getReportValue(4).getText().trim().contains(elements.get(11));//Item details
		verifyReport =  getReportValue(5).getText().trim().contains(elements.get(12));//Item
		
		verifyReport =  getReportValue(8).getText().trim().contains(elements.get(13));//Document value
		
		WaitHelper.waitAdditional(2);
		return verifyReport;
	}
	
	/**
	 * Click on view document
	 */
	public void clickOnViewDocument(){
		WaitHelper.waitAdditional(2);
		List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb2 : wbs1){
			if(wb2.getText().equals("View Document")){
				wb2.click();
				break;
			}
		}
		WaitHelper.waitAdditional(5);
	}
	
	/************************************************
	 * Enter Invoices : GCA AD10001
	 * **********************************************/
	public void logTransactionDetails(List<String> elements){
		log.info("Log transaction details");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Main tab
		WaitHelper.waitAdditional(1);
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(0)).build().perform();////Supplier		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(elements.get(1)).build().perform();//Supplier post code		
		WaitHelper.waitAdditional(3);		
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[9]"))).click().sendKeys(elements.get(5)).build().perform();//Order reference		
		WaitHelper.waitAdditional(3);
		
        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        String currDate = dateFormat1.format(currentMonth.getTime());

        builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[10]"))).click().sendKeys(currDate).build().perform();//Date		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[12]"))).click().sendKeys(elements.get(2)).build().perform();//Sub type		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[14]"))).click().sendKeys(elements.get(3)).build().perform();//Gross amount		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[15]"))).click().sendKeys(elements.get(4)).build().perform();//Tax amount		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[17]"))).click().sendKeys(elements.get(6)).build().perform();//Element		
		WaitHelper.waitAdditional(3);


		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//Currency tab
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().sendKeys(elements.get(7)).build().perform();//Currency		

		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.NINTH_TAB)).click();//GL account tab
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[2]"))).click().sendKeys(elements.get(8)).build().perform();//Account		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(9)).build().perform();//Cost		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[9]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(Keys.ENTER).build().perform();//Cost		
		WaitHelper.waitAdditional(3);
	}
	
	
	/************************************************
	 * Enter Invoices : GCA AD10002
	 * **********************************************/
	
	public String enterTaxableDetails(List<String> elements,int glValue){
		log.info("Enter taxable details");
		String price="";
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Main tab
		WaitHelper.waitAdditional(1);
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		if(!elements.get(0).equals("null")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(0)).build().perform();////Description		
			WaitHelper.waitAdditional(5);
		}
		if(!elements.get(1).equals("null")){
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(1)).build().perform();////Price	
			WaitHelper.waitAdditional(5);
		}
		else{
			price = driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText();
			
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().build().perform();////Description
		}
		if(!elements.get(2).equals("null")){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").sendKeys("\u0008").build().perform();
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(elements.get(2)).build().perform();//Quantity		
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(Keys.ENTER).build().perform();////Quantity		
			WaitHelper.waitAdditional(5);
		}		

		if(glValue==1){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//GL account tab
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[3]"))).click().sendKeys(elements.get(3)).build().perform();////Account		
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(4)).build().perform();////cost		
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[4]"))).click().sendKeys(Keys.ENTER).build().perform();////cost		
			WaitHelper.waitAdditional(5);
		}
		
		if(glValue==2){
			getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SECOND_TAB)).click();//S/L analysis tab
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().sendKeys("1").build().perform();//S/A type		
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div/div[2]/div/div/div[2]/div[2]/div/div/div/div/table/tbody/tr/td[1]"))).click().sendKeys(Keys.ENTER).build().perform();//S/A type		
			WaitHelper.waitAdditional(5);

		}
		return price;
	}
	
	/**
	 * Enter transaction split 
	 * @param elements
	 */
	public void enterTranSplitAnalysis(List<String> elements){
		log.info("Enter taxable details");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIRST_TAB)).click();//Details tab
		WaitHelper.waitAdditional(1);
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(elements.get(0)).build().perform();//Percent		
		WaitHelper.waitAdditional(5);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click().sendKeys(elements.get(1)).build().perform();//Account		
		WaitHelper.waitAdditional(5);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click().sendKeys(elements.get(2)).build().perform();//Cost		
		WaitHelper.waitAdditional(5);

	}
	
	/************************************************
	 * Enter Invoice Due Today : GZA AD10014
	 * **********************************************/
	
	public boolean verifySupplierTransaction(List<String> elements){
		log.info("Verify supplier transaction");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[1]/div/div")).getText().contains("Transaction Ref");//Transaction Ref
		String tranRef = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]")).getText();// value
		if(tranRef.length()>0){
			log.info("Transaction reference is : " + tranRef);
		}
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().contains("Legend");//Legend
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().equals(elements.get(1));// value

		update= getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().contains("Trans Status Desc");//Trans Status Desc
		update= getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText().equals(elements.get(2));// value

		update= getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[6]/div/div")).getText().contains("Auth Status Desc");//Auth Status Desc
		update= getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]")).getText().equals(elements.get(3));// value
	
		update= getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[7]/div/div")).getText().contains("Outstanding Amount");//Outstanding Amount
		update= getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[7]")).getText().equals(elements.get(4));// value	
		
		return update;
	}	
	
	/**add authorisors
	 * 
	 * @param companyId
	 * @param elements
	 */
	public void addAuthorisors(String companyId,List<String> elements){
		log.info("Add authorisors to transaction");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(companyId);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(0));//Authorizer
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Supplier

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FIVE+pObject.LABEL)).click();//Select button
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.FOUR+pObject.LABEL)).click();//Authorize button
		WaitHelper.waitAdditional(3);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE+pObject.LABEL)).click();//Confirm button				
	}
	
	
	/************************************************
	 * Create Manual Payment Schedule : AD10015 
	 * **********************************************/
	public void scheduleMaintenance(List<String> elements){
		log.info("Payment Schedule");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(0));//Payment method
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(1));//Payment code
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.SIX)).sendKeys(elements.get(2));//Currency
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(elements.get(3));//Print distribution
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO+pObject.FOUR)).sendKeys(Keys.ENTER);//Print distribution
		WaitHelper.waitAdditional(2);
		
	}
	
	/**
	 * Enter schduler details for supplier
	 * @param elements
	 */
	public void enterSchedueSupplierTranDetails(List<String> elements){
		log.info("In Schedue Supplier Tran Details");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(4));//Supplier
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(5));//Payment address
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(Keys.ENTER);//Payment address
		WaitHelper.waitAdditional(2);
		
	}
	
	/**
	 * Enter schedule request
	 * @param bankCode
	 */
	public void enterScheduleRequestRun(String bankCode){
		log.info("Enter schedule request");
		WaitHelper.waitAdditional(2);
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(bankCode).build().perform();//Bank code		
		WaitHelper.waitAdditional(5);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click().sendKeys(Keys.ENTER).build().perform();		
		WaitHelper.waitAdditional(4);				
	}
	
	/**
	 * Verify status
	 * @return
	 */
	public String getStatus(int i){
		String statusMessage = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td["+i+"]")).getText();// Status description
		WaitHelper.waitAdditional(2);
		return statusMessage;
	}
	
	/************************************************
	 * Create Schedules : AD10016
	 * **********************************************/
	
	public void searchRequest(List<String> elements,int i){
		log.info("Search request");
		WaitHelper.waitAdditional(2);
		if(!isOkButtonDisplayed(i)){
			clickOnSections(0);
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(elements.get(0));//Schedule

		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(1));//Process
		
		getDriver().findElement(By.id(pObject.ZERO_+i+pObject.LABEL)).click();//Ok button
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Create schedules
	 * @param elements
	 */
	public void createSchedules(List<String> elements){
		log.info("Create schedules");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Schedule
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.FOUR)).click();//Dependent
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE)).sendKeys(elements.get(2));//Dependency Level

		
		Actions builder = new Actions(driver);
		int j = 3;
		for(int i=1;i<=9;i++){
			WaitHelper.waitAdditional(5);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click()
				.sendKeys(elements.get(j)).sendKeys(Keys.ENTER).build().perform();//Process		
			
			WaitHelper.waitAdditional(5);
			if(i>=2){
				builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[4]"))).click()
				.sendKeys(elements.get(j-1)).sendKeys(Keys.ENTER).build().perform();//Parent		
				WaitHelper.waitAdditional(5);
			}
			j++;
		}
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(Keys.ENTER);//Description
		WaitHelper.waitAdditional(3);
		
	}
	
	/**
	 * Enter request details
	 * @param elements
	 * @return
	 */
	public boolean enterRequestDetails(List<String> elements){
		log.info("Enter request details");
		WaitHelper.waitAdditional(2);
		boolean update = false;
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//User
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.TWO)).sendKeys(elements.get(1));//Schedule
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.FOUR)).sendKeys(elements.get(2));//Process
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(elements.get(3));//Request
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX)).sendKeys(Keys.ENTER);//Description
		WaitHelper.waitAdditional(1);
		if(!getToolContentText().contains(message)){
			update = true;
		}
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.SEVEN)).sendKeys(elements.get(4));//Description
		
		
	
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.NINE)).click();//Default reports
		return update;
	}
	
	/**
	 * click On maintain reports
	 */
	public void clickOnMaintainReports(){
		log.info("Clicking on Maintain reports");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Maintain")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Reports")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Enter report details
	 * @param elements
	 */
	public void enterReportDetails(List<String> elements){
		log.info("Enter report details");
		WaitHelper.waitAdditional(2);
		Actions builder = new Actions(driver);
		
		int report = Integer.parseInt(elements.get(2));
		int j=3;
		for(int i=1;i<=report;i++){
			
			WaitHelper.waitAdditional(3);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[2]"))).click()
										.sendKeys(elements.get(j)).build().perform();//Report		
			WaitHelper.waitAdditional(3);	
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click()
										.sendKeys(elements.get(j+1)).build().perform();//Retain days		
			WaitHelper.waitAdditional(3);	
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div["+i+"]/table/tbody/tr/td[3]"))).click()
										.sendKeys(Keys.ENTER).build().perform();//Retain days
			WaitHelper.waitAdditional(3);
			j=j+2;
		}
		
			
	}
	
	/**
	 * click On maintain parameters
	 */
	public void clickOnMaintainParameters(){
		log.info("Clicking on Maintain parameters");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Maintain")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Parameters")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * click On maintain parameters
	 */
	public void clickOnAmendClassification(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Amend")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Classifications")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * Update company parameters
	 * @param companyId
	 * @param element
	 */
	public void updateParameters(String companyId,String element){
		log.info("Update parameters");
		WaitHelper.waitAdditional(2);
		
		Actions builder = new Actions(driver);

		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click()
				.sendKeys(companyId).build().perform();//Company
		WaitHelper.waitAdditional(3);
			
		if(element.equals(1)){
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click()
						.sendKeys(element).build().perform();//Cheque sequence
			WaitHelper.waitAdditional(2);
			builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click()
					.sendKeys(Keys.ENTER).build().perform();//Cheque sequence
		}
		else{
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click()
					.sendKeys(Keys.ENTER).build().perform();//Company
		}
		WaitHelper.waitAdditional(5);
	}
	
	/************************************************
	 * Submit Payment Processing : AD10017 
	 * **********************************************/

	/*Schedules ANM*/
	public void submitSchedules(List<String> elements){
		log.info("In submit schedules");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ZERO)).sendKeys(elements.get(0));//Schedule
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).clear();
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE)).sendKeys(elements.get(1));//Description
	}
	
	/************************************************
	 * Cheque Number Allocation : AD10018
	 * **********************************************/

	
	/**
	 * click On Cheques 
	 */
	public void clickOnChequesAmend(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Cheques")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Amend")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
		
	/**
	 * Log supplier financial limit
	 * @param elements
	 * @param reviewDate
	 */
	public void logSupplierFinanacialLimit(List<String> elements,String reviewDate){
		log.info("Log supplier finanacial limit");
		WaitHelper.waitAdditional(2);
		
		getDriver().findElement(By.id(pObject.CHECK+pObject.ZERO_+pObject.TWO)).click();//Financial limit check box
		
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(reviewDate);	
		getDriver().findElement(By.id(pObject.ZERO_+pObject.THREE)).sendKeys(reviewDate);	
		
		Actions builder = new Actions(driver);
		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[2]"))).click()
				.sendKeys(elements.get(1)).build().perform();//Start date		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[3]"))).click()
				.sendKeys(elements.get(2)).build().perform();//Financial limit		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]"))).click()
				.sendKeys(elements.get(3)).build().perform();//Tolrn value		
		WaitHelper.waitAdditional(3);
		builder.moveToElement(driver.findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]"))).click()
				.sendKeys(elements.get(4)).build().perform();//Tolrn %		
		WaitHelper.waitAdditional(3);
	}
	
	/**
	 * click On Go-->Limits 
	 */
	public void clickOnGoLimits(){
		log.info("Click on Go limits");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Go")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Limits")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
	}
	
	/**
	 * click On Go-->Limits 
	 */
	public void clickOnGoSplitAnalysis(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Go")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Split Anal")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Unauthorised Transaction : Verify column header and column value
	 * @param elements
	 */
	public boolean verifyUnauthorisedTransaction(List<String> elements){
		log.info("Verify store item header and value");
		boolean update = false;
		WaitHelper.waitAdditional(2);
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[3]/div/div")).getText().contains("System Ref");//System Ref
		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[4]/div/div")).getText().equals("Transaction Ref");//Tran Ref
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[4]")).getText().length()>0;
		
        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        String currDate = dateFormat1.format(currentMonth.getTime());

		
		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[5]/div/div")).getText().equals("Entry date");//Entry date
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[5]")).getText().equals(currDate);

		update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[6]/div/div")).getText().equals("Gross Value");//Gross value
		update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[6]")).getText().equals(elements.get(2));
		
        update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[7]/div/div")).getText().equals("Line");//Line
        update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[7]")).getText().equals(elements.get(3));

        update = getDriver().findElement(By.xpath("//div[2]/div/div/table/tbody/tr/th[9]/div/div")).getText().equals("Supplier");//Suplier
        update = getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div[1]/table/tbody/tr/td[9]")).getText().equals(elements.get(1));
		
		return update;
	}

	/**
	 * click On Transaction-->Select 
	 */
	public void clickOnTransactionSelect(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Transaction")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Select")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * click On Transaction-->Authorise 
	 */
	public void clickOnTransactionAuthorise(){
		log.info("Clicking on Purge button");
		List<WebElement> wbs = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
		for(WebElement wb : wbs){
			if(wb.getText().equals("Transaction")){
				wb.click();
				List<WebElement> wbs1 = getDriver().findElements(By.className(pObject.TOP_HEADER_TAB_BTN));
				for(WebElement wb2 : wbs1){
					if(wb2.getText().equals("Authorise")){
						wb2.click();
						break;
					}
				}
				break;
			}
		}
		WaitHelper.waitAdditional(2);
	}
	
	/**
	 * Authorise transaction
	 * @throws InterruptedException
	 */
	public void authoriseTransaction() throws InterruptedException{
		log.info("Authorise invoice");
			
//		clickOnTransactionSelect();
		clickOnButton(15);
		
//		clickOnTransactionAuthorise();
		clickOnButton(14);
			
		clickOnButton(11);
	}
	
	/**
	 * Click on suspend 11010
	 */
	public void enterTransactionSuspendAndRelease(List<String> elements){
		log.info("In transaction suspend release");
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ZERO)).sendKeys(elements.get(2));
		WaitHelper.waitAdditional(1);
		
		Calendar currentMonth = Calendar.getInstance();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
		String currDate = dateFormat1.format(currentMonth.getTime());

		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).clear();
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(currDate);
		WaitHelper.waitAdditional(1);
		getDriver().findElement(By.id(pObject.ZERO_+pObject.ONE+pObject.ONE)).sendKeys(Keys.ENTER);
		WaitHelper.waitAdditional(2);
		
	}
	
	/**
	 * Verify suspend index contains 'Y'
	 * @return boolean - True/False
	 */
	public boolean verifySuspendIndex(){
		log.info("Verify suspend index column");
		WaitHelper.waitAdditional(1);
		return getDriver().findElement(By.xpath("//div[2]/div[2]/div/div/div/div/table/tbody/tr")).getText().contains("Y");//Suspend index

	}
	
	/**
	 * 
	 * @return
	 */
	public boolean verifyAuthrisationStatus(){
		log.info("Verify authorisation status");
		boolean status = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FIFTH_TAB)).click();//Authorisation/Withholding tab
		WaitHelper.waitAdditional(1);
		
		String authStatus = getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.EIGHT)).getText();
		
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.SIXTH_TAB)).click();//Authorisation/Withholding tab
		WaitHelper.waitAdditional(1);
		String suspendIndicatior = getDriver().findElement(By.id(pObject.ZERO_+pObject.SIX+pObject.EIGHT)).getText();//Suspend indicator
		
		if(authStatus.equals("Retain") && suspendIndicatior.equals("Y")){
			status = true;
		}
		return status;
	}
	
	public boolean verifyRecurringStatus(){
		log.info("Verify recurring interval and occur");
		boolean status = false;
		WaitHelper.waitAdditional(2);
		getDriver().findElement(By.id(pObject.TAB_STRIP+pObject.FOURTH_TAB)).click();//Payment tab
		WaitHelper.waitAdditional(1);
		
		String recInterval = getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.ZERO)).getText();//Recurring interval
		
		String recOccur= getDriver().findElement(By.id(pObject.ZERO_+pObject.FIVE+pObject.ONE)).getText();//Recurring occurs
		
		if(recInterval.equals("1") && recOccur.equals("10")){
			status = true;
		}
		return status;
	}
	
} 
