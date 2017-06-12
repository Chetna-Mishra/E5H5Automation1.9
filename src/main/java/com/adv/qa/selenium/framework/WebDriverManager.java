package com.adv.qa.selenium.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class is used to store/retrieve Basetest instances to achieve screen shot for failed test scripts.
 *
 */
public class WebDriverManager {

	private static final Logger log = LoggerFactory.getLogger(WebDriverManager.class);
	private static Map<String, BaseTest>  map = new HashMap<String, BaseTest>();
	private static List<String>  classeList= new ArrayList<String>();
	
/**
 * Method is used to store BaseTest instance in hashmap, and key as Test script class name.
 * @param classNm, key as Test script class name.
 * @param baseTest,  BaseTest instance.
 */
	public static void storeInstance(String classNm, BaseTest baseTest ) {
		log.info("########## Adding BaseTest instance in WebDriverManager  "+classNm+" ##########");
		map.put(classNm, baseTest);
		if(classeList.contains(classNm)){
			log.info("##########  "+classNm+" already available ##########");
		}else{
		classeList.add(classNm);
		}
	}
	
/**
 * Method is used to retrieve  BaseTest instance.
 * @param classNm, key as Test script class name.
 * @return baseTest,  BaseTest instance.
 */
	public static BaseTest retrieveInstance(String classNm)
	{
		log.info("######### Retrieving BaseTest instance from WebDriverManager  "+classNm+" ##########");
		return (BaseTest)map.get(classNm);
	}

	/**
	 * Method is used to remove  BaseTest instance.
	 * @param classNm, key as Test script class name.
	 */
	public static void removeInstance(String classNm)
	{
		log.info("######### Removing BaseTest instance from WebDriverManager  "+classNm+" ##########");
		map.remove(classNm);
	}

}
