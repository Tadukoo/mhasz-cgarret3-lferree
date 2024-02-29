package com.github.tadukoo.middle_earth.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.github.tadukoo.middle_earth.model.Constructs.GameObject;
import com.github.tadukoo.middle_earth.persist.pojo.User;
import persist.dbmod.IntPair;
import persist.dbmod.ObjectIDCommandResponse;
import persist.dbmod.StringPair;
import com.github.tadukoo.middle_earth.model.Constructs.Item;
import com.github.tadukoo.middle_earth.model.Constructs.Map;
import com.github.tadukoo.middle_earth.model.Constructs.MapTile;
import com.github.tadukoo.middle_earth.model.Constructs.ItemType;
import com.github.tadukoo.middle_earth.model.Quest;
import com.github.tadukoo.middle_earth.model.Characters.Enemy;
import com.github.tadukoo.middle_earth.model.Characters.Player;

public class InitialData {
/*
	public static ArrayList<Integer> getInventoriesToPlayers() throws IOException {
		ArrayList<Integer> inventoriesToPlayersList = new ArrayList<Integer>();
		ReadCSV readInventoriesToPlayers = new ReadCSV("inventoriestoplayers.csv");
		try {
			while (true) {
				List<String> tuple = readInventoriesToPlayers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				while (i.hasNext()) {
					inventoriesToPlayersList.add(Integer.parseInt(i.next()));
				}
			}
			return inventoriesToPlayersList;
			
		} finally {
			readInventoriesToPlayers.close();
		}
	}
	*/
	
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
	
	public static ArrayList<Item> getItems() throws IOException {
		ArrayList<Item> itemList = new ArrayList<>();
		try(ReadCSV readItems = new ReadCSV("items.csv")){
			while(true){
				List<String> tuple = readItems.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				
				item.setName(i.next());
				item.setLongDescription(i.next());
				item.setShortDescription(i.next());
				item.setDescriptionUpdate(i.next());
				item.setAttackBonus(Integer.parseInt(i.next()));
				item.setDefenseBonus(Integer.parseInt(i.next()));
				item.setHPBonus(Integer.parseInt(i.next()));
				item.setWeight(Integer.parseInt(i.next()));
				item.setType(ItemType.valueOf(i.next()));
				item.setLevelRequirement(Integer.parseInt(i.next()));
				
				itemList.add(item);
			}
			return itemList;
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
	
	public static ArrayList<IntPair> getItemsToObjects() throws IOException {
		ArrayList<IntPair> itemsToObjectsList = new ArrayList<>();
		
		try(ReadCSV readItemsToObjects = new ReadCSV("itemstoobjects.csv")){
			while(true){
				List<String> tuple = readItemsToObjects.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				IntPair intPair = new IntPair();
				intPair.setInt1(Integer.parseInt(i.next()));
				intPair.setInt2(Integer.parseInt(i.next()));
				
				itemsToObjectsList.add(intPair);
			}
			return itemsToObjectsList;
			
		}
	}
	
	public static ArrayList<Map> getMaps() throws IOException {
		ArrayList<Map> mapList = new ArrayList<>();
		try(ReadCSV readMaps = new ReadCSV("map.csv")){
			while(true){
				List<String> tuple = readMaps.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				Map map = new Map();
				
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
	
	public static ArrayList<MapTile> getMapTiles() throws IOException {
		ArrayList<MapTile> mapTileList = new ArrayList<>();
		
		try(ReadCSV readMapTiles = new ReadCSV("maptiles.csv")){
			int mapTileID = 1;
			while(true){
				List<String> tuple = readMapTiles.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				MapTile mapTile = new MapTile();
				
				mapTile.setID(mapTileID++);
				mapTile.setName(i.next());
				mapTile.setLongDescription(i.next());
				mapTile.setShortDescription(i.next());
				mapTile.setAreaDifficulty(Integer.parseInt(i.next()));
				
				mapTileList.add(mapTile);
			}
			
			return mapTileList;
		}
	}
	
	public static ArrayList<ObjectIDCommandResponse> getObjectCommandResponses() throws IOException {
		ArrayList<ObjectIDCommandResponse> objectCommandResponseList = new ArrayList<>();
		
		try(ReadCSV readObjectCommandResponses = new ReadCSV("objectcommandresponses.csv")){
			while(true){
				List<String> tuple = readObjectCommandResponses.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				ObjectIDCommandResponse objectCommandResponse = new ObjectIDCommandResponse();
				
				while(i.hasNext()){
					objectCommandResponse.setObjectID(Integer.parseInt(i.next()));
					objectCommandResponse.setCommand(i.next());
					objectCommandResponse.setResponse(i.next());
					objectCommandResponseList.add(objectCommandResponse);
				}
			}
			return objectCommandResponseList;
		}
	}
	
	public static ArrayList<GameObject> getObjects() throws IOException {
		ArrayList<GameObject> objectList = new ArrayList<>();
		
		try(ReadCSV readObjects = new ReadCSV("objects.csv")){
			while(true){
				List<String> tuple = readObjects.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				GameObject object = new GameObject();
				
				object.setName(i.next());
				object.setLongDescription(i.next());
				object.setShortDescription(i.next());
				
				objectList.add(object);
			}
			return objectList;
		}
	} 
	
	public static ArrayList<IntPair> getObjectsToMapTiles() throws IOException {
		ArrayList<IntPair> objectsToMapTilesList = new ArrayList<>();
		try(ReadCSV readObjectsToMapTiles = new ReadCSV("objectstomaptiles.csv")){
			while(true){
				List<String> tuple = readObjectsToMapTiles.next();
				if(tuple == null){
					break;
				}
				Iterator<String> i = tuple.iterator();
				IntPair intPair = new IntPair();
				intPair.setInt1(Integer.parseInt(i.next()));
				intPair.setInt2(Integer.parseInt(i.next()));
				
				objectsToMapTilesList.add(intPair);
			}
			return objectsToMapTilesList;
			
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
}
