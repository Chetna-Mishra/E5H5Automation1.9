package com.adv.qa.selenium.framework;

import org.slf4j.MDC;

public class TestLogHelper {
	public static final String TEST_NAME = "testname";
	
	  /**
	   * @param name name of the new log file
	   * @throws Exception
	   */
	  public static void startTestLogging(String name) throws Exception {
	    MDC.put(TEST_NAME, name);
	  }
	 
	  /**
	   * @return name of the log file, if one existed in MDC
	   */
	  public static String stopTestLogging() {
	    String name = MDC.get(TEST_NAME);
	    MDC.remove(TEST_NAME);
	    return name;
	  }
}
