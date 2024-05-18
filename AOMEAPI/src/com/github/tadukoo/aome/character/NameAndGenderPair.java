package com.github.tadukoo.aome.character;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

/**
 * Represents a potential name-gender pair to be used for generated names
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class NameAndGenderPair extends AbstractDatabasePojo{
	/** The column name for the name column */
	public static final String NAME_COLUMN_NAME = "name";
	/** The column name for the gender column */
	public static final String GENDER_COLUMN_NAME = "gender";
	
	/**
	 * Creates a new Name and Gender Pair with no values
	 */
	public NameAndGenderPair(){
	
	}
	
	/**
	 * Creates a new Name and Gender Pair with the given name and gender
	 *
	 * @param name The name for this name-gender pair
	 * @param gender The gender for this name-gender pair
	 */
	public NameAndGenderPair(String name, String gender){
		setName(name);
		setGender(gender);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "NamesAndGenders";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getIDColumnName(){
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		// Name Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(NAME_COLUMN_NAME)
				.varchar()
				.length(40)
				.build());
		
		// Gender Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(GENDER_COLUMN_NAME)
				.varchar()
				.length(10)
				.build());
	}
	
	/**
	 * @return The name from this name-gender pair
	 */
	public String getName(){
		return (String) getItem(NAME_COLUMN_NAME);
	}
	
	/**
	 * @param name The name to be set on this name-gender pair
	 */
	public void setName(String name){
		setItem(NAME_COLUMN_NAME, name);
	}
	
	/**
	 * @return The gender from this name-gender pair
	 */
	public String getGender(){
		return (String) getItem(GENDER_COLUMN_NAME);
	}
	
	/**
	 * @param gender The gender to be set on this name-gender pair
	 */
	public void setGender(String gender){
		setItem(GENDER_COLUMN_NAME, gender);
	}
}
