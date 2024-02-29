package com.github.tadukoo.middle_earth.persist.pojo;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

/**
 * Represents a User in the database
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class User extends AbstractDatabasePojo{
	/** The column name for the user ID column */
	public static final String USER_ID_COLUMN_NAME = "user_id";
	/** The column name for the username column */
	public static final String USERNAME_COLUMN_NAME = "username";
	/** The column name for the password column */
	public static final String PASSWORD_COLUMN_NAME = "password";
	/** The column name for the email address column */
	public static final String EMAIL_COLUMN_NAME = "email";
	
	/**
	 * Creates a new {@link User} with no parameters set
	 */
	public User(){
		super();
	}
	
	/**
	 * Creates a new {@link User} with the given parameters
	 *
	 * @param username The username for this {@link User}
	 * @param password The password for this {@link User}
	 * @param email The email address for this {@link User}
	 */
	public User(String username, String password, String email){
		super();
		setUsername(username);
		setItem(PASSWORD_COLUMN_NAME, password);
		setEmail(email);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "Users";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getIDColumnName(){
		return USER_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
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
	
	/**
	 * @return The username of this {@link User}
	 */
	public String getUsername(){
		return (String) getItem(USERNAME_COLUMN_NAME);
	}
	
	/**
	 * @param username The username to be set on this {@link User}
	 */
	public void setUsername(String username){
		setItem(USERNAME_COLUMN_NAME, username);
	}
	
	/**
	 * @return The password of this {@link User}
	 */
	public String getPassword(){
		return (String) getItem(PASSWORD_COLUMN_NAME);
	}
	
	/**
	 * @return The email address of this {@link User}
	 */
	public String getEmail(){
		return (String) getItem(EMAIL_COLUMN_NAME);
	}
	
	/**
	 * @param email The email address to be set on this {@link User}
	 */
	public void setEmail(String email){
		setItem(EMAIL_COLUMN_NAME, email);
	}
}
