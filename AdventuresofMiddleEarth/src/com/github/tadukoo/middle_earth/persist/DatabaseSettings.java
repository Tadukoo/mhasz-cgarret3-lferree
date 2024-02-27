package com.github.tadukoo.middle_earth.persist;

import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Settings used for initializing a MySQL Database
 *
 * @param host The host for the database
 * @param port The port used for the database
 * @param databaseName The database name to be used
 * @param username The name of the user for the database
 * @param password The password of the user for the database
 */
public record DatabaseSettings(String host, int port, String databaseName, String username, String password){
	
	/**
	 * Creates a {@link DatabaseSettings} object using the config file found from the
	 * {@link ServletContext}
	 *
	 * @param servletContext The {@link ServletContext} to use to find the config file
	 * @return The {@link DatabaseSettings} from the config file
	 * @throws IOException If anything goes wrong
	 */
	public static DatabaseSettings loadFromConfig(ServletContext servletContext) throws IOException{
		Properties prop = new Properties();
		InputStream is = servletContext.getResourceAsStream("WEB-INF/config/database.properties");
		prop.load(is);
		return new DatabaseSettings(prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")),
				prop.getProperty("database-name"), prop.getProperty("username"), prop.getProperty("password"));
	}
}
