package com.github.tadukoo.aome.construct;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

/**
 * Construct is used for a base class of several different things that have a name and description
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @version 2.0
 * @since 1.0 or earlier
 */
public abstract class Construct extends AbstractDatabasePojo{
	/** The column name of the ID column */
	public static final String ID_COLUMN_NAME = "id";
	/** The column name of the name column */
	public static final String NAME_COLUMN_NAME = "name";
	/** The column name of the short description column */
	public static final String SHORT_DESCRIPTION_COLUMN_NAME = "short_description";
	/** The column name of the long description column */
	public static final String LONG_DESCRIPTION_COLUMN_NAME = "long_description";
	
	/**
	 * Creates a {@link Construct} with no parameters
	 */
	protected Construct(){
		super();
	}
	
	/**
	 * Creates a {@link Construct} with the given parameters
	 *
	 * @param id The ID of the {@link Construct}
	 * @param name The name of the {@link Construct}
	 * @param shortDescription The short description of the {@link Construct}
	 * @param longDescription The long description of the {@link Construct}
	 */
	protected Construct(Integer id, String name, String shortDescription, String longDescription){
		super();
		setID(id);
		setName(name);
		setShortDescription(shortDescription);
		setLongDescription(longDescription);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getIDColumnName(){
		return ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		// ID Primary Key Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build());
		
		// Name Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(NAME_COLUMN_NAME)
				.varchar()
				.length(40)
				.build());
		
		// Short Description Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(SHORT_DESCRIPTION_COLUMN_NAME)
				.varchar()
				.length(100)
				.build());
		
		// Long Description Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(LONG_DESCRIPTION_COLUMN_NAME)
				.varchar()
				.length(200)
				.build());
	}
	
	/**
	 * @return The ID of this {@link Construct}
	 */
	public int getID(){
		return (Integer) getItem(ID_COLUMN_NAME);
	}
	
	/**
	 * @param id The ID to be set on the {@link Construct}
	 */
	public void setID(Integer id){
		setItem(ID_COLUMN_NAME, id);
	}
	
	/**
	 * @return The name of this {@link Construct}
	 */
	public String getName(){
		return (String) getItem(NAME_COLUMN_NAME);
	}
	
	/**
	 * @param name The name to be set on the {@link Construct}
	 */
	public void setName(String name){
		setItem(NAME_COLUMN_NAME, name);
	}
	
	/**
	 * @return The short description of this {@link Construct}
	 */
	public String getShortDescription(){
		return (String) getItem(SHORT_DESCRIPTION_COLUMN_NAME);
	}
	
	/**
	 * @param shortDescription The short description to be set on the {@link Construct}
	 */
	public void setShortDescription(String shortDescription){
		setItem(SHORT_DESCRIPTION_COLUMN_NAME, shortDescription);
	}
	
	/**
	 * @return The long description of this {@link Construct}
	 */
	public String getLongDescription(){
		return (String) getItem(LONG_DESCRIPTION_COLUMN_NAME);
	}
	
	/**
	 * @param longDescription The long description to be set on the {@link Construct}
	 */
	public void setLongDescription(String longDescription){
		setItem(LONG_DESCRIPTION_COLUMN_NAME, longDescription);
	}
}
