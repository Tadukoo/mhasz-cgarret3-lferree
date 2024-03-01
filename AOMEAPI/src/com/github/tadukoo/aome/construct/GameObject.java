package com.github.tadukoo.aome.construct;

import com.github.tadukoo.database.mysql.pojo.SubPojoDefinition;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<String, String> commandResponses;
	private ArrayList<Item> items;
	private String description_update;
	
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
	
	public void setCommandResponses(HashMap<String, String> commandResponses) {
		this.commandResponses = commandResponses;
	}
	
	public HashMap<String, String> getCommandResponses() {
		return this.commandResponses;
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void removeItem(Item item) {
		Item remove = new Item();
		for (Item check: items) {
			if (check == item) {
				remove = item;
			}
		}
		items.remove(remove);
	}
	
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public ArrayList<Item> getItems() {
		return this.items;
	}
	
	public void setdescription_update(String string){
		this.description_update = string;
	}
	
	public String getdescription_update(){
		return description_update;
	}
}
