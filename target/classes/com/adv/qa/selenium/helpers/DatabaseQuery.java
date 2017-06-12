package com.adv.qa.selenium.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adv.qa.selenium.framework.JDBCDriverInformation;

public class DatabaseQuery {
	public static final Logger log = LoggerFactory.getLogger(DatabaseQuery.class);
	static ResultSet rs = null;
	PreparedStatement stmt = null;
	static Connection connection = null;


	public int getProcessCount() throws SQLException, SeleniumDaoException{
		int processCount = 0;		
		connection = JDBCDriverInformation.getSqlJDBCConnection();
		try {					
			connection = JDBCDriverInformation.getSqlJDBCConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("Select count(*) AS TOTALCOUNT from  TZXQBTAR");			
			while (rs.next()) {
				processCount = rs.getInt("TOTALCOUNT");			  
			}
		}
		  catch (SQLException e) {
				throw new SeleniumDaoException("Unable to execute query to fetch details"); 
			} finally {
				JDBCDriverInformation.releaseResources(connection,stmt);
			}
		return processCount;
	}
	
	public String getStatProcess(String prcss,String req) throws SQLException, SeleniumDaoException{
		String name = null;		
		connection = JDBCDriverInformation.getSqlJDBCConnection();
		try {
			connection = JDBCDriverInformation.getSqlJDBCConnection();
			Statement st = connection.createStatement();
			  ResultSet rs = st.executeQuery("SELECT STAT FROM TZXQTASK WHERE PRCSS = '"+prcss+"' AND REQ = '"+req+"'");			
			while (rs.next()) {
			  name = rs.getString("STAT");			  
			}
		}
		  catch (SQLException e) {
				throw new SeleniumDaoException("Unable to execute query to fetch details"); 
			} finally {
				JDBCDriverInformation.releaseResources(connection,stmt);
			}
		return name;
	}
	
	public void updateProcess(String prcss,String req) throws SQLException, SeleniumDaoException{
			
		connection = JDBCDriverInformation.getSqlJDBCConnection();
		try {
			connection = JDBCDriverInformation.getSqlJDBCConnection();
			Statement st = connection.createStatement();
			String sql = "UPDATE TZXQTASK SET HOLD_FLAG = ' ' WHERE PRCSS = '"+prcss+"' AND REQ = '"+req+"'";
			st.executeUpdate(sql);	

		}
		  catch (SQLException e) {
				throw new SeleniumDaoException("Unable to execute query to fetch details"); 
			} finally {
				JDBCDriverInformation.releaseResources(connection,stmt);
			}
	}
	
	public void checkProcess() throws SQLException, SeleniumDaoException{
		
		connection = JDBCDriverInformation.getSqlJDBCConnection();
		try {
			connection = JDBCDriverInformation.getSqlJDBCConnection();
			Statement st = connection.createStatement();
			String sql = "select * from eventdatemappings";
			st.executeUpdate(sql);	

		}
		  catch (SQLException e) {
				throw new SeleniumDaoException("Unable to execute query to fetch details"); 
			} finally {
				JDBCDriverInformation.releaseResources(connection,stmt);
			}
	}
	
	
	//Update TZXQBTAR set HOLD-FLAG to 'Y'
	public void updateHoldFlag() throws SQLException, SeleniumDaoException{
		
		connection = JDBCDriverInformation.getSqlJDBCConnection();
		try {
			connection = JDBCDriverInformation.getSqlJDBCConnection();
			Statement st = connection.createStatement();
			String sql = "UPDATE TZXQBTAR set HOLD-FLAG to 'Y'";
			st.executeUpdate(sql);	

		}
		  catch (SQLException e) {
				throw new SeleniumDaoException("Unable to execute query to fetch details"); 
			} finally {
				JDBCDriverInformation.releaseResources(connection,stmt);
			}
	}

}
