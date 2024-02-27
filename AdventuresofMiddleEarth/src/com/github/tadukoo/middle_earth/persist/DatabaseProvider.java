package com.github.tadukoo.middle_earth.persist;

import jakarta.servlet.ServletContext;

import java.io.IOException;

public class DatabaseProvider{
	private static IDatabase theInstance;
	
	public static void setInstance(IDatabase db){
		theInstance = db;
	}
	
	public static void initDefaultDatabase(ServletContext servletContext) throws IOException{
		setInstance(new MySQLDatabase(DatabaseSettings.loadFromConfig(servletContext)));
	}
	
	public static IDatabase getInstance(){
		if(theInstance == null){
			throw new IllegalStateException("Database instance has not been set!");
		}
		return theInstance;
	}
}
