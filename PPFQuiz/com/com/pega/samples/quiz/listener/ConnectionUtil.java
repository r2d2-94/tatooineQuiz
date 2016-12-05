package com.pega.samples.quiz.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * @author bajam 
 * Utility class for getting JDBC connection and closing it
 */
public class ConnectionUtil {
	private final static Logger LOGGER = Logger.getLogger(ConnectionUtil.class.getName());
	private static Connection conn;

	/**
	 * get JDBC Connection
	 */
	/**
	 * @param dbURL URL of the Databse
	 * @param user user name of the database
	 * @param pwd	password
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String dbURL, String user, String pwd) throws SQLException {
		//
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Setting JDBC Conenction");
		// set connection in class to be used for closing later
		conn = DriverManager.getConnection(dbURL, user, pwd);
		if(conn!=null)
		{
		LOGGER.info("Connection done");
		}
		else
		{
			LOGGER.info("Got no connection");
		}
		return conn;

	}

	/**
	 * close JDBC Connection
	 */
	public static void closeConnection() throws SQLException {
		LOGGER.info("Closing JDBC Conenction");
		conn.close(); // close the connection
	}

}
