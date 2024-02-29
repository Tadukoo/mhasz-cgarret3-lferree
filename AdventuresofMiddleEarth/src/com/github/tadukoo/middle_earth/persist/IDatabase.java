package com.github.tadukoo.middle_earth.persist;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.Map;
import com.github.tadukoo.aome.construct.MapTile;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;

public interface IDatabase{
	/*
	 * Account related Queries
	 */
	
	DatabaseResult<String> getUserPasswordByUsername(String username);
	DatabaseResult<Boolean> doesUsernameExist(String username);
	DatabaseResult<Boolean> isEmailInUse(String email);
	DatabaseResult<Boolean> createNewUser(String username, String password, String email);
	
	Map getMap();
	Player getPlayer();
	
	List<Item> getAllItems();
	List<GameObject> getAllObjects();
	List<MapTile> getAllMapTiles();
	List<Character> getAllCharacters();
	List<Quest> getAllQuests();
	
	Item getItemByID(int itemID);
	GameObject getObjectByID(int objectID);
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
	
	Item getLegendaryItem();
	Item getLegendaryItem(String itemType);
	Item getHandHeldItem();
	Item getHandHeldItem(String whichHand);
	Item getArmorItem();
	Item getArmorItem(String armorType);
}
