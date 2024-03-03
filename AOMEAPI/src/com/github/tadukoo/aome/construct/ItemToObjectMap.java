package com.github.tadukoo.aome.construct;

import com.github.tadukoo.aome.ManyToManyDatabasePojo;

public class ItemToObjectMap extends ManyToManyDatabasePojo<Item, GameObject>{
	
	public static final String ITEM_ID_COLUMN_NAME = "item_id";
	public static final String OBJECT_ID_COLUMN_NAME = "object_id";
	
	public ItemToObjectMap(){
	
	}
	
	public ItemToObjectMap(int itemID, int objectID){
		setItemID(itemID);
		setObjectID(objectID);
	}
	
	@Override
	public String getTableName(){
		return "ItemsToObjects";
	}
	
	@Override
	public Class<Item> getType1Class(){
		return Item.class;
	}
	
	@Override
	public String getType1ForeignTableName(){
		return new Item().getTableName();
	}
	
	@Override
	public String getType1IDColumnName(){
		return ITEM_ID_COLUMN_NAME;
	}
	
	@Override
	public String getType1ForeignIDColumnName(){
		return Item.ID_COLUMN_NAME;
	}
	
	@Override
	public Class<GameObject> getType2Class(){
		return GameObject.class;
	}
	
	@Override
	public String getType2ForeignTableName(){
		return new GameObject().getTableName();
	}
	
	@Override
	public String getType2IDColumnName(){
		return OBJECT_ID_COLUMN_NAME;
	}
	
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
