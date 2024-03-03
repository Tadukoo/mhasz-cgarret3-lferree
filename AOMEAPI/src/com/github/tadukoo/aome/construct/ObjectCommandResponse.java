package com.github.tadukoo.aome.construct;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;

/**
 * Represents a Command Response for an Object from the database
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class ObjectCommandResponse extends AbstractDatabasePojo{
	public static final String OBJECT_ID_COLUMN_NAME = "object_id";
	public static final String COMMAND_COLUMN_NAME = "command";
	public static final String RESPONSE_COLUMN_NAME = "response";
	
	@Override
	public String getTableName(){
		return "ObjectCommandResponses";
	}
	
	@Override
	public String getIDColumnName(){
		return null;
	}
	
	@Override
	public void setDefaultColumnDefs(){
		// Object ID
		ColumnDefinition objectIDCol = ColumnDefinition.builder()
				.columnName(OBJECT_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(objectIDCol);
		
		// Object ID foreign constraint
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(objectIDCol)
				.references(new GameObject().getTableName())
				.referenceColumnNames(GameObject.ID_COLUMN_NAME)
				.build());
		
		// Command
		addColumnDef(ColumnDefinition.builder()
				.columnName(COMMAND_COLUMN_NAME)
				.varchar()
				.length(10)
				.build());
		
		// Response
		addColumnDef(ColumnDefinition.builder()
				.columnName(RESPONSE_COLUMN_NAME)
				.varchar()
				.length(100)
				.build());
	}
	
	public int getObjectID(){
		return (int) getItem(OBJECT_ID_COLUMN_NAME);
	}
	
	public void setObjectID(int objectID){
		setItem(OBJECT_ID_COLUMN_NAME, objectID);
	}
	
	public String getCommand(){
		return (String) getItem(COMMAND_COLUMN_NAME);
	}
	
	public void setCommand(String command){
		setItem(COMMAND_COLUMN_NAME, command);
	}
	
	public String getResponse(){
		return (String) getItem(RESPONSE_COLUMN_NAME);
	}
	
	public void setResponse(String response){
		setItem(RESPONSE_COLUMN_NAME, response);
	}
}
