package com.github.tadukoo.middle_earth.persist;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.middle_earth.model.Characters.Inventory;
import com.github.tadukoo.middle_earth.model.Characters.Player;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.middle_earth.model.Quest;
import com.github.tadukoo.middle_earth.model.Characters.Character;
import com.github.tadukoo.middle_earth.model.Characters.Enemy;
import com.github.tadukoo.middle_earth.model.Constructs.Item;
import com.github.tadukoo.middle_earth.model.Constructs.Object;
import com.github.tadukoo.middle_earth.model.Constructs.Map;
import com.github.tadukoo.middle_earth.model.Constructs.MapTile;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;

public interface IDatabase{
	/*
	 * Account related Queries
	 */
	
	DatabaseResult<String> getUserPasswordByUsername(String username);
	List<String> getAllUsernames();
	Boolean doesUsernameExist(String username);
	Boolean isEmailInUse(String email);
	Boolean createNewUser(String username, String password, String email);
	
	Map getMap();
	Player getPlayer();
	
	List<Item> getAllItems();
	List<Object> getAllObjects();
	List<MapTile> getAllMapTiles();
	List<Character> getAllCharacters();
	List<Quest> getAllQuests();
	
	Item getItemByID(int itemID);
	Object getObjectByID(int objectID);
	MapTile getMapTileByID(int mapTileID);
	Inventory getInventoryByID(int inventoryID);
	
	Character getCharacterByName(String characterName);
	
	Item removeItemFromInventory(Item item, Inventory inventory);
	Item removeItemFromObject(Item item, Object object);
	
	void addItemToInventory(Item item, Inventory inventory);
	void addItemToObject(Item item, Object object);
	
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
