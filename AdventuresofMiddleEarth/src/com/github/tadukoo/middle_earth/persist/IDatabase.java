package com.github.tadukoo.middle_earth.persist;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.GameMap;
import com.github.tadukoo.aome.construct.MapTile;
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
	
	GameMap getMap();
	Player getPlayer();
	
	List<MapTile> getAllMapTiles();
	List<Character> getAllCharacters();
	List<Quest> getAllQuests();
	
	MapTile getMapTileByID(int mapTileID);
	Inventory getInventoryByID(int inventoryID);
	
	Character getCharacterByName(String characterName);
	
	Item removeItemFromInventory(Item item, Inventory inventory);
	Item removeItemFromObject(Item item, GameObject object);
	
	void addItemToInventory(Item item, Inventory inventory);
	void addItemToObject(Item item, GameObject object);
	
	Game loadGame(int gameID);
	void saveGame(Game game);
	
	Integer createNewGame(String username);
	ArrayList<Integer> getGameIDs(String username);
	
	Enemy getEnemyByRace(String race);
	ArrayList<Enemy> getAllEnemies();
	ArrayList<String> getAllEnemyRaces();
}
