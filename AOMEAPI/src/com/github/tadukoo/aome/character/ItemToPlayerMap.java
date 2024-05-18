package com.github.tadukoo.aome.character;

import com.github.tadukoo.aome.ManyToManyDatabasePojo;
import com.github.tadukoo.aome.construct.Item;

/**
 * A Many-to-Many mapping of {@link Item} to {@link Player}
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class ItemToPlayerMap extends ManyToManyDatabasePojo<Item, Player>{
	/** The Column Name for the {@link Item} ID Column */
	public static final String ITEM_ID_COLUMN_NAME = "item_id";
	/** The Column Name for the {@link Player} ID Column */
	public static final String PLAYER_ID_COLUMN_NAME = "player_id";
	
	/**
	 * Creates a new empty Item to Player mapping pojo
	 */
	public ItemToPlayerMap(){
	
	}
	
	/**
	 * Creates a new Item to Player mapping for the given item and Player IDs
	 *
	 * @param itemID The ID of the Item for this mapping
	 * @param playerID The ID of the Player for this mapping
	 */
	public ItemToPlayerMap(int itemID, int playerID){
		setItemID(itemID);
		setPlayerID(playerID);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "ItemsToPlayers";
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<Item> getType1Class(){
		return Item.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignTableName(){
		return Item.TABLE_NAME;
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
	public Class<Player> getType2Class(){
		return Player.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignTableName(){
		return Player.TABLE_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2IDColumnName(){
		return PLAYER_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignIDColumnName(){
		return Player.ID_COLUMN_NAME;
	}
	
	/**
	 * @return The {@link Item} ID for this mapping
	 */
	public int getItemID(){
		return (int) getItem(ITEM_ID_COLUMN_NAME);
	}
	
	/**
	 * @param itemID The {@link Item} ID to be set for this mapping
	 */
	public void setItemID(int itemID){
		setItem(ITEM_ID_COLUMN_NAME, itemID);
	}
	
	/**
	 * @return The {@link Player} ID for this mapping
	 */
	public int getPlayerID(){
		return (int) getItem(PLAYER_ID_COLUMN_NAME);
	}
	
	/**
	 * @param playerID The {@link Player} ID to be set for this mapping
	 */
	public void setPlayerID(int playerID){
		setItem(PLAYER_ID_COLUMN_NAME, playerID);
	}
}
