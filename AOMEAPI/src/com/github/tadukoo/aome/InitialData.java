package com.github.tadukoo.aome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.GameMap;
import com.github.tadukoo.aome.construct.MapTile;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.aome.construct.ObjectToMapTileMap;

/**
 * A class used to load Initial Data from CSV files
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class InitialData{
	
	/**
	 * @return A List of {@link User Users} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<User> getUsers() throws IOException{
		List<User> users = new ArrayList<>();
		// Read users from the CSV
		try(ReadCSV readUsers = new ReadCSV("users.csv")){
			List<String> tuple = readUsers.next();
			while(tuple != null){
				// Grab the parts of the user
				String username = tuple.get(0);
				String password = tuple.get(1);
				String email = tuple.get(2);
				
				// Create the user and add it to the List
				users.add(new User(username, password, email));
				
				// Grab next tuple
				tuple = readUsers.next();
			}
			return users;
		}
	}
	
	/**
	 * @return A List of {@link Item items} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<Item> getItems() throws IOException{
		List<Item> items = new ArrayList<>();
		// Read items from the CSV
		try(ReadCSV readItems = new ReadCSV("items.csv")){
			int id = 1;
			List<String> tuple = readItems.next();
			while(tuple != null){
				// Grab the parts of the Item
				String name = tuple.get(0);
				String longDescription = tuple.get(1);
				String shortDescription = tuple.get(2);
				String descriptionUpdate = tuple.get(3);
				int attackBonus = Integer.parseInt(tuple.get(4));
				int defenseBonus = Integer.parseInt(tuple.get(5));
				int hpBonus = Integer.parseInt(tuple.get(6));
				float weight = Float.parseFloat(tuple.get(7));
				ItemType type = ItemType.valueOf(tuple.get(8));
				int levelRequirement = Integer.parseInt(tuple.get(9));
				
				// Create the item and add it to the List
				items.add(new Item(id++, name, shortDescription, longDescription,
						descriptionUpdate, type, levelRequirement,
						attackBonus, defenseBonus, hpBonus, weight));
				
				// Grab next tuple
				tuple = readItems.next();
			}
			return items;
		}
	}
	
	/**
	 * @return A List of {@link GameObject objects} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<GameObject> getObjects() throws IOException{
		List<GameObject> objects = new ArrayList<>();
		// Read Objects from the CSV
		try(ReadCSV readObjects = new ReadCSV("objects.csv")){
			int objectID = 1;
			List<String> tuple = readObjects.next();
			while(tuple != null){
				// Create the object and add it to the list
				GameObject object = new GameObject();
				object.setID(objectID++);
				object.setName(tuple.get(0));
				object.setLongDescription(tuple.get(1));
				object.setShortDescription(tuple.get(2));
				objects.add(object);
				
				// Grab next tuple
				tuple = readObjects.next();
			}
			return objects;
		}
	}
	
	/**
	 * @return A List of {@link ItemToObjectMap Item to Object mappings} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<ItemToObjectMap> getItemsToObjects() throws IOException{
		List<ItemToObjectMap> itemsToObjects = new ArrayList<>();
		// Read Items to Objects from the CSV
		try(ReadCSV readItemsToObjects = new ReadCSV("itemstoobjects.csv")){
			List<String> tuple = readItemsToObjects.next();
			while(tuple != null){
				// Create the Item to Object map and add it to the list
				int itemID = Integer.parseInt(tuple.get(0));
				int objectID = Integer.parseInt(tuple.get(1));
				ItemToObjectMap itemToObject = new ItemToObjectMap(itemID, objectID);
				itemsToObjects.add(itemToObject);
				
				// Grab next tuple
				tuple = readItemsToObjects.next();
			}
			return itemsToObjects;
		}
	}
	
	/**
	 * @return A List of {@link ObjectCommandResponse Object Command Responses} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<ObjectCommandResponse> getObjectCommandResponses() throws IOException{
		List<ObjectCommandResponse> objectCommandResponses = new ArrayList<>();
		// Read Object Command Responses from the CSV
		try(ReadCSV readObjectCommandResponses = new ReadCSV("objectcommandresponses.csv")){
			List<String> tuple = readObjectCommandResponses.next();
			while(tuple != null){
				// Create the Object Command Response and add it to the list
				ObjectCommandResponse objectCommandResponse = new ObjectCommandResponse();
				objectCommandResponse.setObjectID(Integer.parseInt(tuple.get(0)));
				objectCommandResponse.setCommand(tuple.get(1));
				objectCommandResponse.setResponse(tuple.get(2));
				objectCommandResponses.add(objectCommandResponse);
				
				// Grab next tuple
				tuple = readObjectCommandResponses.next();
			}
			return objectCommandResponses;
		}
	}
	
	/**
	 * @return A List of {@link MapTile Map Tiles} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<MapTile> getMapTiles() throws IOException{
		List<MapTile> mapTiles = new ArrayList<>();
		// Read Map Tiles from the CSV
		try(ReadCSV readMapTiles = new ReadCSV("maptiles.csv")){
			int mapTileID = 1;
			List<String> tuple = readMapTiles.next();
			while(tuple != null){
				// Create the Map Tile and add it to the list
				MapTile mapTile = new MapTile();
				mapTile.setID(mapTileID++);
				mapTile.setName(tuple.get(0));
				mapTile.setLongDescription(tuple.get(1));
				mapTile.setShortDescription(tuple.get(2));
				mapTile.setDifficulty(Integer.parseInt(tuple.get(3)));
				mapTiles.add(mapTile);
				
				// Grab next tuple
				tuple = readMapTiles.next();
			}
			return mapTiles;
		}
	}
	
	/**
	 * @return A List of {@link ObjectToMapTileMap Object to Map Tile mappings} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<ObjectToMapTileMap> getObjectsToMapTiles() throws IOException{
		List<ObjectToMapTileMap> objectsToMapTiles = new ArrayList<>();
		// Read Objects to Map Tiles from the CSV
		try(ReadCSV readObjectsToMapTiles = new ReadCSV("objectstomaptiles.csv")){
			List<String> tuple = readObjectsToMapTiles.next();
			while(tuple != null){
				// Create the Object to Map Tile mapping and add it to the list
				int objectID = Integer.parseInt(tuple.get(0));
				int mapTileID = Integer.parseInt(tuple.get(1));
				objectsToMapTiles.add(new ObjectToMapTileMap(objectID, mapTileID));
				
				// Grab next tuple
				tuple = readObjectsToMapTiles.next();
			}
			return objectsToMapTiles;
		}
	}
	
	public static ArrayList<Integer> getInventoriesToCharacters() throws IOException {
		ArrayList<Integer> inventoriesToCharactersList = new ArrayList<>();
		try(ReadCSV readInventoriesToCharacters = new ReadCSV("inventoriestocharacters.csv")){
			while(true){
				List<String> tuple = readInventoriesToCharacters.next();
				if(tuple == null){
					break;
				}
				
				for(String s: tuple){
					inventoriesToCharactersList.add(Integer.parseInt(s));
				}
			}
			return inventoriesToCharactersList;
			
		}
	}
	
	
	public static ArrayList<StringPair> getNameGenderList() throws IOException {
		ArrayList<StringPair> nameGenderList = new ArrayList<>();
		try(ReadCSV readNames = new ReadCSV("names.csv")){
			while(true){
				List<String> tuple = readNames.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				StringPair nameGender = new StringPair();
				nameGender.setString1(i.next());
				nameGender.setString2(i.next());
				nameGenderList.add(nameGender);
			}
			return nameGenderList;
			
		}
	}
	
	public static ArrayList<Enemy> getEnemies() throws IOException {
		ArrayList<Enemy> enemyList = new ArrayList<>();
		try(ReadCSV readEnemies = new ReadCSV("enemies.csv")){
			while(true){
				List<String> tuple = readEnemies.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				Enemy enemy = new Enemy();
				
				enemy.setrace(i.next());
				enemy.setattack(Integer.parseInt(i.next()));
				enemy.setdefense(Integer.parseInt(i.next()));
				enemy.sethit_points(Integer.parseInt(i.next()));
				
				enemyList.add(enemy);
			}
			return enemyList;
		}
	}
		
	public static ArrayList<IntPair> getItemsToInventories() throws IOException {
		ArrayList<IntPair> itemToInventoryList = new ArrayList<>();
		try(ReadCSV readItemsToInventories = new ReadCSV("itemstoinventories.csv")){
			while(true){
				List<String> tuple = readItemsToInventories.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				IntPair intPair = new IntPair();
				intPair.setInt1(Integer.parseInt(i.next()));
				intPair.setInt2(Integer.parseInt(i.next()));
				
				itemToInventoryList.add(intPair);
			}
			return itemToInventoryList;
			
		}
	}
	
	public static ArrayList<GameMap> getMaps() throws IOException {
		ArrayList<GameMap> mapList = new ArrayList<>();
		try(ReadCSV readMaps = new ReadCSV("map.csv")){
			while(true){
				List<String> tuple = readMaps.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				GameMap map = new GameMap();
				
				map.setName(i.next());
				map.setShortDescription(i.next());
				map.setLongDescription(i.next());
				
				mapList.add(map);
			}
			return mapList;
		}
	}
	
	public static ArrayList<IntPair> getMapTilesToMaps() throws IOException {
		ArrayList<IntPair> intPairList = new ArrayList<>();
		try(ReadCSV readMapTilesToMaps = new ReadCSV("maptilestomaps.csv")){
			while(true){
				List<String> tuple = readMapTilesToMaps.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				IntPair intPair = new IntPair();
				
				intPair.setInt1(Integer.parseInt(i.next()));
				intPair.setInt2(Integer.parseInt(i.next()));
				intPairList.add(intPair);
			}
			return intPairList;
		}
	}
	
	public static ArrayList<HashMap<String, Integer>> getMapTileConnections() throws IOException {
		ArrayList<HashMap<String, Integer>> mapTileConnectionsList = new ArrayList<>();
		try(ReadCSV readMapTileConnections = new ReadCSV("maptileconnections.csv")){
			while(true){
				List<String> tuple = readMapTileConnections.next();
				
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				HashMap<String, Integer> mapTileConnections = new HashMap<>();
				
				mapTileConnections.put("north", Integer.parseInt(i.next()));
				mapTileConnections.put("northeast", Integer.parseInt(i.next()));
				mapTileConnections.put("east", Integer.parseInt(i.next()));
				mapTileConnections.put("southeast", Integer.parseInt(i.next()));
				mapTileConnections.put("south", Integer.parseInt(i.next()));
				mapTileConnections.put("southwest", Integer.parseInt(i.next()));
				mapTileConnections.put("west", Integer.parseInt(i.next()));
				mapTileConnections.put("northwest", Integer.parseInt(i.next()));
				
				mapTileConnectionsList.add(mapTileConnections);
			}
			return mapTileConnectionsList;
		}
	}

	public static ArrayList<Player> getPlayers() throws IOException {
		ArrayList<Player> playerList = new ArrayList<>();
		try(ReadCSV readPlayers = new ReadCSV("players.csv")){
			while(true){
				List<String> tuple = readPlayers.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				
				player.setrace(i.next());
				player.setname(i.next());
				player.setgender(i.next());
				player.setlevel(Integer.parseInt(i.next()));
				player.sethit_points(Integer.parseInt(i.next()));
				
				player.setmagic_points(Integer.parseInt(i.next()));
				player.setattack(Integer.parseInt(i.next()));
				player.setdefense(Integer.parseInt(i.next()));
				player.setspecial_attack(Integer.parseInt(i.next()));
				player.setspecial_defense(Integer.parseInt(i.next()));
				
				player.setcoins(Integer.parseInt(i.next()));
				player.setlocation(Integer.parseInt(i.next()));
				player.setinventory_id(Integer.parseInt(i.next()));
				
				/*
				 * The next few lines are equipping armor pieces to pass player
				 * back up to the database.  If there is no item associated with
				 * a slot, it puts an empty item of the correct ItemType in the
				 * slot. There is a check in DerbyDatabase to make sure the any
				 * item in the slot is of the correct type in actuality, but they
				 * must be passed assuming so until that check.  It would mean
				 * that the database is broken.  Unfortunately you cannot check
				 * at this stage, you must check after the item is pulled from
				 * the masteritems table
				 */
				Item item = new Item();
				
				// helm
				item.setType(ItemType.HELM);
				item.setID(Integer.parseInt(i.next()));
				player.sethelm(item);
				
				// braces
				item.setType(ItemType.BRACES);
				item.setID(Integer.parseInt(i.next()));
				player.setbraces(item);
				
				// chest
				item.setType(ItemType.CHEST);
				item.setID(Integer.parseInt(i.next()));
				player.setchest(item);
				
				// legs
				item.setType(ItemType.LEGS);
				item.setID(Integer.parseInt(i.next()));
				player.setlegs(item);
				
				// boots
				item.setType(ItemType.BOOTS);
				item.setID(Integer.parseInt(i.next()));
				player.setboots(item);
				
				// l_hand
				item.setType(ItemType.L_HAND);
				item.setID(Integer.parseInt(i.next()));
				player.setl_hand(item);
				
				// r_hand
				item.setType(ItemType.R_HAND);
				item.setID(Integer.parseInt(i.next()));
				player.setr_hand(item);
				
				player.setexperience(Integer.parseInt(i.next()));
				player.setcarry_weight(Integer.parseInt(i.next()));
				
				playerList.add(player);
			}
			return playerList;
		}
	}
	
	public static ArrayList<Quest> getQuests() throws IOException {
		ArrayList<Quest> questList = new ArrayList<>();
		try(ReadCSV readQuests = new ReadCSV("quests.csv")){
			while(true){
				List<String> tuple = readQuests.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				Quest quest = new Quest();
				if(i.hasNext()){
					String questAtt = i.next();
					if(questAtt.equals("rewardItems")){
						questAtt = i.next();
						ArrayList<Item> itemList = new ArrayList<>();
						
						while(!questAtt.equals("rewardCoins") && i.hasNext()){
							Item item = new Item();
							item.setID(Integer.parseInt(questAtt));
							itemList.add(item);
							questAtt = i.next();
						}
						quest.setRewardItems(itemList);
					}
					
					if(i.hasNext() && questAtt.equals("rewardCoins")){
						quest.setRewardCoins(Integer.parseInt(i.next()));
					}
				}
				questList.add(quest);
			}
			return questList;
		}
	}
}
