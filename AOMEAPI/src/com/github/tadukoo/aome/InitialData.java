package com.github.tadukoo.aome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.ItemToPlayerMap;
import com.github.tadukoo.aome.character.NameAndGenderPair;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.aome.construct.map.MapTileConnections;
import com.github.tadukoo.aome.construct.map.MapTileToMapMap;
import com.github.tadukoo.aome.construct.map.ObjectToMapTileMap;
import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.util.StringUtil;

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
	 * Used to parse int values from text in the CSV's where the value could be null
	 *
	 * @param text The text to be parsed
	 * @return The integer parsed from the text, or null
	 */
	private static Integer parsePotentiallyNullInt(String text){
		if(StringUtil.equalsIgnoreCase(text, "null")){
			return null;
		}
		return Integer.parseInt(text);
	}
	
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
				items.add(new Item(null, name, shortDescription, longDescription,
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
			List<String> tuple = readObjects.next();
			while(tuple != null){
				// Create the object and add it to the list
				GameObject object = new GameObject();
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
			List<String> tuple = readMapTiles.next();
			while(tuple != null){
				// Create the Map Tile and add it to the list
				MapTile mapTile = new MapTile();
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
	
	/**
	 * @return A List of {@link MapTileConnections} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<MapTileConnections> getMapTileConnections() throws IOException{
		List<MapTileConnections> mapTileConnections = new ArrayList<>();
		// Read Map Tile Connections
		try(ReadCSV readMapTileConnections = new ReadCSV("maptileconnections.csv")){
			List<String> tuple = readMapTileConnections.next();
			while(tuple != null){
				// Create the Map Tile Connections and add it to the list
				int mapTileID = Integer.parseInt(tuple.get(0));
				Integer northID = parsePotentiallyNullInt(tuple.get(1));
				Integer northeastID = parsePotentiallyNullInt(tuple.get(2));
				Integer eastID = parsePotentiallyNullInt(tuple.get(3));
				Integer southeastID = parsePotentiallyNullInt(tuple.get(4));
				Integer southID = parsePotentiallyNullInt(tuple.get(5));
				Integer southwestID = parsePotentiallyNullInt(tuple.get(6));
				Integer westID = parsePotentiallyNullInt(tuple.get(7));
				Integer northwestID = parsePotentiallyNullInt(tuple.get(8));
				mapTileConnections.add(new MapTileConnections(mapTileID,
						northID, northeastID,
						eastID, southeastID, southID,
						southwestID, westID, northwestID));
				
				// Grab next tuple
				tuple = readMapTileConnections.next();
			}
			return mapTileConnections;
		}
	}
	
	/**
	 * @return A List of {@link GameMap Maps} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<GameMap> getMaps() throws IOException{
		List<GameMap> maps = new ArrayList<>();
		// Read Maps
		try(ReadCSV readMaps = new ReadCSV("map.csv")){
			List<String> tuple = readMaps.next();
			while(tuple != null){
				// Create the Map and add it to the list
				String name = tuple.get(0);
				String shortDescription = tuple.get(1);
				String longDescription = tuple.get(2);
				maps.add(new GameMap(null, name, shortDescription, longDescription));
				
				// Grab next tuple
				tuple = readMaps.next();
			}
			return maps;
		}
	}
	
	/**
	 * @return A List of {@link MapTileToMapMap Map Tile to Map mappings} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<MapTileToMapMap> getMapTilesToMaps() throws IOException {
		List<MapTileToMapMap> mapTilesToMaps = new ArrayList<>();
		// Read Map Tiles to Maps
		try(ReadCSV readMapTilesToMaps = new ReadCSV("maptilestomaps.csv")){
			List<String> tuple = readMapTilesToMaps.next();
			while(tuple != null){
				// Create the Map Tile to Map mapping and add it to the list
				int mapTileID = Integer.parseInt(tuple.get(0));
				int mapID = Integer.parseInt(tuple.get(1));
				mapTilesToMaps.add(new MapTileToMapMap(mapTileID, mapID));
				
				// Grab next tuple
				tuple = readMapTilesToMaps.next();
			}
			return mapTilesToMaps;
		}
	}
	
	/**
	 * @return A List of {@link Player players} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<Player> getPlayers() throws IOException{
		List<Player> players = new ArrayList<>();
		// Read Players
		try(ReadCSV readPlayers = new ReadCSV("players.csv")){
			List<String> tuple = readPlayers.next();
			while(tuple != null){
				// Create the Player and add it to the list
				Player player = new Player();
				player.setName(tuple.get(0));
				player.setRace(tuple.get(1));
				player.setGender(tuple.get(2));
				
				// Stats
				player.setLevel(Integer.parseInt(tuple.get(3)));
				player.setHP(Integer.parseInt(tuple.get(4)));
				player.setMP(Integer.parseInt(tuple.get(5)));
				player.setAttack(Integer.parseInt(tuple.get(6)));
				player.setDefense(Integer.parseInt(tuple.get(7)));
				player.setSpecialAttack(Integer.parseInt(tuple.get(8)));
				player.setSpecialDefense(Integer.parseInt(tuple.get(9)));
				
				// Location
				player.setLocationID(Integer.parseInt(tuple.get(10)));
				
				// Items (Armor/Weapons)
				player.setHelmID(parsePotentiallyNullInt(tuple.get(11)));
				player.setBracesID(parsePotentiallyNullInt(tuple.get(12)));
				player.setChestID(parsePotentiallyNullInt(tuple.get(13)));
				player.setLegsID(parsePotentiallyNullInt(tuple.get(14)));
				player.setBootsID(parsePotentiallyNullInt(tuple.get(15)));
				player.setLeftHandID(parsePotentiallyNullInt(tuple.get(16)));
				player.setRightHandID(parsePotentiallyNullInt(tuple.get(17)));
				
				// Other Settings
				player.setCoins(Integer.parseInt(tuple.get(18)));
				player.setExperience(Integer.parseInt(tuple.get(19)));
				player.setCarryWeight(Integer.parseInt(tuple.get(20)));
				players.add(player);
				
				// Grab next tuple
				tuple = readPlayers.next();
			}
			return players;
		}
	}
	
	/**
	 * @return A List of {@link ItemToPlayerMap Item to Player mappings} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<ItemToPlayerMap> getItemsToPlayers() throws IOException{
		List<ItemToPlayerMap> itemsToPlayers = new ArrayList<>();
		// Read Items to Players
		try(ReadCSV readItemsToPlayers = new ReadCSV("itemstoplayers.csv")){
			List<String> tuple = readItemsToPlayers.next();
			while(tuple != null){
				// Create the Item to Player mapping and add it to the list
				int itemID = Integer.parseInt(tuple.get(0));
				int playerID = Integer.parseInt(tuple.get(1));
				itemsToPlayers.add(new ItemToPlayerMap(itemID, playerID));
				
				// Grab next tuple
				tuple = readItemsToPlayers.next();
			}
			return itemsToPlayers;
		}
	}
	
	/**
	 * @return A List of {@link Enemy enemies} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<Enemy> getEnemies() throws IOException{
		List<Enemy> enemies = new ArrayList<>();
		// Read Enemies
		try(ReadCSV readEnemies = new ReadCSV("enemies.csv")){
			List<String> tuple = readEnemies.next();
			while(tuple != null){
				// Create the Enemy and add it to the list
				Enemy enemy = new Enemy();
				enemy.setRace(tuple.get(0));
				enemy.setAttack(Integer.parseInt(tuple.get(1)));
				enemy.setDefense(Integer.parseInt(tuple.get(2)));
				enemy.setHP(Integer.parseInt(tuple.get(3)));
				enemies.add(enemy);
				
				// Grab next tuple
				tuple = readEnemies.next();
			}
			return enemies;
		}
	}
	
	/**
	 * @return A List of {@link NameAndGenderPair name-gender pairs} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<NameAndGenderPair> getNameGenderPairs() throws IOException{
		List<NameAndGenderPair> nameGenderPairs = new ArrayList<>();
		// Read name and gender pairs
		try(ReadCSV readNameGenderPairs = new ReadCSV("names.csv")){
			List<String> tuple = readNameGenderPairs.next();
			while(tuple != null){
				// Create the Name-Gender Pair and add it to the list
				String name = tuple.get(0);
				String gender = tuple.get(1);
				nameGenderPairs.add(new NameAndGenderPair(name, gender));
				
				// Grab next tuple
				tuple = readNameGenderPairs.next();
			}
			return nameGenderPairs;
		}
	}
	
	/**
	 * @return A List of {@link Game games} based on the CSV to use for initial data
	 * @throws IOException If anything goes wrong
	 */
	public static List<Game> getGames() throws IOException{
		List<Game> games = new ArrayList<>();
		// Read games
		try(ReadCSV readGames = new ReadCSV("games.csv")){
			List<String> tuple = readGames.next();
			while(tuple != null){
				// Create the Game and add it to the list
				Game game = new Game();
				game.setName(tuple.get(0));
				games.add(game);
				
				// Grab next tuple
				tuple = readGames.next();
			}
			return games;
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
