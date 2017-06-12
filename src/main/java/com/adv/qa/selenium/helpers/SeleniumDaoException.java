/**
 * 
 */
package com.adv.qa.selenium.helpers;

/**
 * <br>Exception Class to handle exceptions thrown during accessing database.
 * <br> The messages for the database exceptions are handled by this class
 */
public class SeleniumDaoException extends Exception {

	private static final long serialVersionUID = -2263698759412616021L;
	
	
	/**
	 * Default Constructor
	 * Constructs a new exception with null as its detail message.
	 */
	public SeleniumDaoException() {
	}

	/**
	 * @param msg
	 * Constructs a new exception related to database with the specified detail message
	 */
	public SeleniumDaoException(String msg) {
		super(msg);
	}

	/**
	 * @param e
	 * Constructs a new exception related to database with the specified cause and a detail message of 
	 * (cause==null ? null : cause.toString()) (which typically contains the class and 
	 * detail message of cause).
	 */
	public SeleniumDaoException(Throwable e) {
		super(e);
	}

	/**
	 * @param msg
	 * @param e
	 * Constructs a new exception related to database with the specified detail message and cause
	 */
	public SeleniumDaoException(String msg, Throwable e) {
		super(msg, e);
	}

}
