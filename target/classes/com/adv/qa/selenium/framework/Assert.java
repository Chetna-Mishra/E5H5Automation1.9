package com.adv.qa.selenium.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Assert class used as wrapper for TestNG Assert class method for assertion.
 */

public class Assert {

	/**
	 * Protect constructor since it is a static only class
	 */
	protected Assert() {
		// hide constructorhna
	}
	
	public static String getCurreentDate(){
		return new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(new Date());
	}
	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	static public void assertTrue(boolean condition, String message){
		org.testng.Assert.assertTrue(condition, message);
	}
	
	static public void assertTrue(List<String> testcases,boolean condition, String message,String message1){
		if(condition == true){
			testcases.add(getCurreentDate()+" | Pass : "+message+" "+message1);
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : "+message+" not "+message1);
		}
		org.testng.Assert.assertTrue(condition, message+" not "+message1);
	}
	
	/**
	 * Asserts that a condition is true. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 */
	static public void assertTrue(boolean condition){
		org.testng.Assert.assertTrue(condition);
	}
	
	/**
	 * Asserts that a condition is false. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 * @param message
	 *            the assertion error message
	 */
	static public void assertFalse(boolean condition, String message) {
		org.testng.Assert.assertFalse(condition, message);
	}
	
	static public void assertFalse(List<String> testcases,boolean condition, String message,String message1) {
		if(condition == false){
			testcases.add(getCurreentDate()+" | Pass : "+message+"  not "+message1);
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : "+message+" "+message1);
		}
		org.testng.Assert.assertFalse(condition, message);
	}
	/**
	 * Asserts that a condition is false. If it isn't, an AssertionError, with
	 * the given message, is thrown.
	 * 
	 * @param condition
	 *            the condition to evaluate
	 */
	static public void assertFalse(boolean condition) {
		org.testng.Assert.assertFalse(condition);
	}
	
	/**
	 * Fails a test with the given message and wrapping the original exception.
	 * 
	 * @param message
	 *            the assertion error message
	 * @param realCause
	 *            the original exception
	 */
	static public void fail(String message, Throwable realCause) {
		org.testng.Assert.fail(message, realCause);
	}

	/**
	 * Fails a test with the given message.
	 * 
	 * @param message
	 *            the assertion error message
	 */
	static public void fail(String message) {
		org.testng.Assert.fail(message);
	}
	
	/**
	 * Asserts that two objects are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(Object actual, Object expected,String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}
	
	static public void assertEquals(List<String> testcases,Object actual, Object expected,String message,String message1) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two objects are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(Object actual, Object expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(List<String> testcases,String actual, String expected,String message,String message1) {
		if(actual.equals(expected)){
			testcases.add(getCurreentDate()+" | Pass : "+message+" "+message1);
		}
		else{
			testcases.add(getCurreentDate()+" | Fail : "+message+" not "+message1);
		}
		
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two Strings are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(String actual, String expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not,
	 * an AssertionError, with the given message, is thrown. If the expected
	 * value is infinity then the delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerate value value between the actual and
	 *            expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(double actual, double expected,
			double delta, String message) {
		org.testng.Assert.assertEquals(actual, expected, delta, message);
	}

	/**
	 * Asserts that two doubles are equal concerning a delta. If they are not,
	 * an AssertionError is thrown. If the expected value is infinity then the
	 * delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerate value value between the actual and
	 *            expected value
	 */
	static public void assertEquals(double actual, double expected,
			double delta) {
		org.testng.Assert.assertEquals(actual, expected, delta);
	}

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not, an
	 * AssertionError, with the given message, is thrown. If the expected value
	 * is infinity then the delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerate value value between the actual and
	 *            expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(float actual, float expected, float delta,
			String message) {
		org.testng.Assert.assertEquals(actual, expected, delta, message);
	}

	/**
	 * Asserts that two floats are equal concerning a delta. If they are not, an
	 * AssertionError is thrown. If the expected value is infinity then the
	 * delta value is ignored.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param delta
	 *            the absolute tolerate value value between the actual and
	 *            expected value
	 */
	static public void assertEquals(float actual, float expected, float delta) {
		org.testng.Assert.assertEquals(actual, expected, delta);
	}

	/**
	 * Asserts that two longs are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(long actual, long expected, String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two longs are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(long actual, long expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two booleans are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(boolean actual, boolean expected,
			String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two booleans are equal. If they are not, an AssertionError
	 * is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(boolean actual, boolean expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two bytes are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(byte actual, byte expected, String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two bytes are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(byte actual, byte expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two chars are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(char actual, char expected, String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two chars are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(char actual, char expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two shorts are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(short actual, short expected,String message) {	
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two shorts are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(short actual, short expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}

	/**
	 * Asserts that two ints are equal. If they are not, an
	 * AssertionFailedError, with the given message, is thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 * @param message
	 *            the assertion error message
	 */
	static public void assertEquals(int actual, int expected, String message) {
		org.testng.Assert.assertEquals(actual, expected, message);
	}

	/**
	 * Asserts that two ints are equal. If they are not, an AssertionError is
	 * thrown.
	 * 
	 * @param actual
	 *            the actual value
	 * @param expected
	 *            the expected value
	 */
	static public void assertEquals(int actual, int expected) {
		org.testng.Assert.assertEquals(actual, expected);
	}
	
}
