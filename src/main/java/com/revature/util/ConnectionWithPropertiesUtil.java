package com.revature.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionWithPropertiesUtil {

	
	private static Connection connection;
	
	private ConnectionWithPropertiesUtil() {
	}
	
	public static Connection getConnection() {
		// Need a reference to a Properties file in order to insert into code
		Properties props = new Properties();
		
		// FileInputStream is necessary to read the db.properties file
		FileInputStream in = null;
		
		// Not necessary to instantiate a String for the properties file path, helpful for debugging
		final String propsFile = "src/main/resources/db.properties";
		
		// Singleton Design Pattern
		if (connection == null) {
			try {
				// Read the db.properties file
				in = new FileInputStream(propsFile);
				
				// Load the db.properties file content into the Properties instance 
				props.load(in);
				
				// Load the JDBC Driver
				Class.forName(props.getProperty("jdbc.driver"));
				
				// Establish a Connection with URL, Username, and Password
				connection = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
			} catch (FileNotFoundException fnfe) {
				// Thrown by FileInputStream if the file could not be found
				System.err.println("Could not find file: " + propsFile);
			} catch (IOException ioe) {
				// Thrown by the Properties.load() method
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				// Thrown by Class.forName() when an invalid class is supplied
				System.err.println("Could not load driver: " + props.getProperty("jdbc.driver"));
			} catch (SQLException sqle) {
				// Human Readable Message as to what went wrong
				sqle.getMessage();
				
				// SQL Code as to what went wrong... Google this as well as the name of your Database
				System.err.println("SQL State: " + sqle.getSQLState());
				
				// Database Specific code 
				sqle.getErrorCode();
			} finally {
				try {
					in.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return connection;
	}
	
}
