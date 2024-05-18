package com.github.tadukoo.middle_earth.persist;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;

/**
 * An interface for database operations
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public interface IDatabase{
	/*
	 * Account related Queries
	 */
	
	DatabaseResult<String> getUserPasswordByUsername(String username);
	DatabaseResult<Boolean> doesUsernameExist(String username);
	DatabaseResult<Boolean> isEmailInUse(String email);
	DatabaseResult<Boolean> createNewUser(String username, String password, String email);
	
	/*
	 * Item Related Queries
	 */
	
	DatabaseResult<List<Item>> getAllItems();
	DatabaseResult<Item> getItemByID(int id);
	DatabaseResult<Item> getLegendaryItem(ItemType type);
	DatabaseResult<Item> getHandheldItem();
	DatabaseResult<Item> getArmorItem();
	
	/*
	 * Object Related Queries
	 */
	
	DatabaseResult<List<GameObject>> getAllObjects();
	DatabaseResult<GameObject> getObjectByID(int id);
	// TODO: Below 2 methods?
	void addItemToObject(Item item, GameObject object);
	Item removeItemFromObject(Item item, GameObject object);
	
	/*
	 * Map Tile and Map Related Queries
	 */
	
	DatabaseResult<List<MapTile>> getAllMapTiles();
	DatabaseResult<MapTile> getMapTileByID(int id);
	DatabaseResult<GameMap> getMapByID(int id);
	
	/*
	 * Character Related Queries
	 */
	
	DatabaseResult<Player> getPlayerByID(int id);
	
	List<Quest> getAllQuests();
	
	Game loadGame(int gameID);
	void saveGame(Game game);
	
	Integer createNewGame(String username);
	ArrayList<Integer> getGameIDs(String username);
	
	Enemy getEnemyByRace(String race);
	ArrayList<Enemy> getAllEnemies();
	ArrayList<String> getAllEnemyRaces();
}
