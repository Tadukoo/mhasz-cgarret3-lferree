package com.github.tadukoo.aome.construct;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an Object in the Game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class GameObject extends Construct{
	public static final String DESCRIPTION_UPDATE_COLUMN_NAME = "description_update";
	
	private List<Item> items;
	private Map<String, String> commandResponses;
	
	// TODO: Figure out how to put location in here???
	public GameObject(){
		items = new ArrayList<>();
		commandResponses = new HashMap<>();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "Objects";
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		super.setDefaultColumnDefs();
		
		// Description Update
		addColumnDef(ColumnDefinition.builder()
				.columnName(DESCRIPTION_UPDATE_COLUMN_NAME)
				.varchar()
				.length(100)
				.build());
	}
	
	public String getDescriptionUpdate(){
		return (String) getItem(DESCRIPTION_UPDATE_COLUMN_NAME);
	}
	
	public void setDescriptionUpdate(String descriptionUpdate){
		setItem(DESCRIPTION_UPDATE_COLUMN_NAME, descriptionUpdate);
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public void setItems(List<Item> items){
		this.items = items;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void removeItem(Item item){
		Item remove = new Item();
		for(Item check: items){
			if(check == item){
				remove = item;
				break;
			}
		}
		items.remove(remove);
	}
	
	public Map<String, String> getCommandResponses(){
		return commandResponses;
	}
	
	public void setCommandResponses(Map<String, String> commandResponses){
		this.commandResponses = commandResponses;
	}
	
	public void addCommandResponse(String command, String response){
		commandResponses.put(command, response);
	}
}
