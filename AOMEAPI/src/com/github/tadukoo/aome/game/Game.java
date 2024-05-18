package com.github.tadukoo.aome.game;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

/**
 * Represents a Game stored in the database
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class Game extends AbstractDatabasePojo{
	/** The table name */
	public static final String TABLE_NAME = "Games";
	/** The ID column name */
	public static final String ID_COLUMN_NAME = "id";
	/** The name column name */
	public static final String NAME_COLUMN_NAME = "name";
	
	/**
	 * Creates a new Game with no parameters
	 */
	public Game(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return TABLE_NAME;
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
	}
	
	/**
	 * @return The ID of this Game
	 */
	public Integer getID(){
		return (Integer) getItem(ID_COLUMN_NAME);
	}
	
	/**
	 * @param id The ID to be set on this Game
	 */
	public void setID(Integer id){
		setItem(ID_COLUMN_NAME, id);
	}
	
	/**
	 * @return The name of this Game
	 */
	public String getName(){
		return (String) getItem(NAME_COLUMN_NAME);
	}
	
	/**
	 * @param name The name to be set on this Game
	 */
	public void setName(String name){
		setItem(NAME_COLUMN_NAME, name);
	}
}
