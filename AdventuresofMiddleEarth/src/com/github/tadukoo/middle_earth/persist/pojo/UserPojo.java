package com.github.tadukoo.middle_earth.persist.pojo;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

public class UserPojo extends AbstractDatabasePojo{
	public static final String USER_ID_COLUMN_NAME = "user_id";
	public static final String USERNAME_COLUMN_NAME = "username";
	public static final String PASSWORD_COLUMN_NAME = "password";
	public static final String EMAIL_COLUMN_NAME = "email";
	
	
	public UserPojo(){
		super();
	}
	
	public UserPojo(String username, String password, String email){
		super();
		setItem(USERNAME_COLUMN_NAME, username);
		setItem(PASSWORD_COLUMN_NAME, password);
		setItem(EMAIL_COLUMN_NAME, email);
	}
	
	@Override
	public String getTableName(){
		return "Users";
	}
	
	@Override
	public String getIDColumnName(){
		return USER_ID_COLUMN_NAME;
	}
	
	@Override
	public void setDefaultColumnDefs(){
		// User ID
		addColumnDef(ColumnDefinition.builder()
				.columnName(USER_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build());
		
		// Username
		addColumnDef(ColumnDefinition.builder()
				.columnName(USERNAME_COLUMN_NAME)
				.varchar()
				.length(100)
				.unique()
				.build());
		
		// Password
		addColumnDef(ColumnDefinition.builder()
				.columnName(PASSWORD_COLUMN_NAME)
				.varchar()
				.length(100)
				.build());
		
		// Email
		addColumnDef(ColumnDefinition.builder()
				.columnName(EMAIL_COLUMN_NAME)
				.varchar()
				.length(100)
				.unique()
				.build());
	}
	
	public void setUsername(String username){
		setItem(USERNAME_COLUMN_NAME, username);
	}
	
	public String getPassword(){
		return (String) getItem(PASSWORD_COLUMN_NAME);
	}
	
	public void setEmail(String email){
		setItem(EMAIL_COLUMN_NAME, email);
	}
}
