package com.github.tadukoo.aome.construct;

import com.github.tadukoo.aome.ManyToManyDatabasePojo;

/**
 * A ManyToMany Mapping of Items to Objects
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class ItemToObjectMap extends ManyToManyDatabasePojo<Item, GameObject>{
	
	/** The Column Name for the Item ID Column */
	public static final String ITEM_ID_COLUMN_NAME = "item_id";
	/** The Column Name for the Object ID Column */
	public static final String OBJECT_ID_COLUMN_NAME = "object_id";
	
	/**
	 * Creates a new empty Item to Object mapping pojo
	 */
	public ItemToObjectMap(){
	
	}
	
	/**
	 * Creates a new Item to Object mapping for the given item and object IDs
	 *
	 * @param itemID The ID of the Item for this mapping
	 * @param objectID The ID of the Object for this mapping
	 */
	public ItemToObjectMap(int itemID, int objectID){
		setItemID(itemID);
		setObjectID(objectID);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "ItemsToObjects";
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<Item> getType1Class(){
		return Item.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignTableName(){
		return new Item().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1IDColumnName(){
		return ITEM_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignIDColumnName(){
		return Item.ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<GameObject> getType2Class(){
		return GameObject.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignTableName(){
		return new GameObject().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2IDColumnName(){
		return OBJECT_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignIDColumnName(){
		return GameObject.ID_COLUMN_NAME;
	}
	
	public int getItemID(){
		return (int) getItem(ITEM_ID_COLUMN_NAME);
	}
	
	public void setItemID(int itemID){
		setItem(ITEM_ID_COLUMN_NAME, itemID);
	}
	
	public int getObjectID(){
		return (int) getItem(OBJECT_ID_COLUMN_NAME);
	}
	
	public void setObjectID(int objectID){
		setItem(OBJECT_ID_COLUMN_NAME, objectID);
	}
}
