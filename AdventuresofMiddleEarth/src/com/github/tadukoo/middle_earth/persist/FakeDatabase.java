package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.aome.InitialData;
import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.character.ItemToPlayerMap;
import com.github.tadukoo.aome.character.NameAndGenderPair;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.GameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.aome.construct.map.MapTileConnections;
import com.github.tadukoo.aome.construct.map.MapTileToMapMap;
import com.github.tadukoo.aome.construct.map.ObjectToMapTileMap;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.util.StringUtil;

/**
 * A Fake Database implementation of {@link IDatabase}, mostly to use for testing purposes
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class FakeDatabase implements IDatabase{
	
	private final Random random;
	private final List<User> users;
	private final List<Item> items;
	private final Map<Integer, Item> itemsByID;
	private final List<GameObject> objects;
	private final Map<Integer, GameObject> objectsByID;
	private final List<MapTile> mapTiles;
	private final Map<Integer, MapTile> mapTilesByID;
	private final Map<Integer, GameMap> mapsByID;
	private final Map<Integer, Player> playersByID;
	private final List<Enemy> enemies;
	private final List<NameAndGenderPair> nameGenderPairs;
	private final List<Quest> quests;
	
	public FakeDatabase(){
		try{
			random = new Random(System.currentTimeMillis());
			
			// Users
			users = InitialData.getUsers();
			int id = 1;
			for(User user: users){
				user.setItem(User.USER_ID_COLUMN_NAME, id++);
			}
			
			// Items
			items = InitialData.getItems();
			id = 1;
			for(Item item: items){
				item.setID(id++);
			}
			
			// Objects
			objects = InitialData.getObjects();
			id = 1;
			for(GameObject object: objects){
				object.setID(id++);
			}
			
			// Items to Objects
			List<ItemToObjectMap> itemsToObjects = InitialData.getItemsToObjects();
			itemsByID = items.stream()
					.collect(Collectors.toMap(Item::getID, item -> item));
			objectsByID = objects.stream()
					.collect(Collectors.toMap(GameObject::getID, object -> object));
			itemsToObjects.forEach(itemToObject -> {
				Item item = itemsByID.get(itemToObject.getItemID());
				GameObject object = objectsByID.get(itemToObject.getObjectID());
				object.addItem(item);
			});
			
			// Object Command Responses
			List<ObjectCommandResponse> objectCommandResponses = InitialData.getObjectCommandResponses();
			for(ObjectCommandResponse objectCommandResponse: objectCommandResponses){
				GameObject object = objectsByID.get(objectCommandResponse.getObjectID());
				object.addCommandResponse(objectCommandResponse.getCommand(), objectCommandResponse.getResponse());
			}
			
			// Map Tiles
			mapTiles = InitialData.getMapTiles();
			id = 1;
			for(MapTile mapTile: mapTiles){
				mapTile.setID(id++);
			}
			
			// Objects to Map Tiles
			List<ObjectToMapTileMap> objectsToMapTiles = InitialData.getObjectsToMapTiles();
			mapTilesByID = mapTiles.stream()
					.collect(Collectors.toMap(MapTile::getID, mapTile -> mapTile));
			objectsToMapTiles.forEach(objectToMapTile -> {
				GameObject object = objectsByID.get(objectToMapTile.getObjectID());
				MapTile mapTile = mapTilesByID.get(objectToMapTile.getMapTileID());
				mapTile.addObject(object);
			});
			
			// Map Tile Connections
			List<MapTileConnections> mapTileConnections = InitialData.getMapTileConnections();
			for(MapTileConnections mapTileConnection: mapTileConnections){
				int mapTileID = mapTileConnection.getMapTileID();
				mapTilesByID.get(mapTileID).setConnections(mapTileConnection);
			}
			
			// Maps
			List<GameMap> maps = InitialData.getMaps();
			id = 1;
			for(GameMap map: maps){
				map.setID(id++);
			}
			mapsByID = maps.stream()
					.collect(Collectors.toMap(GameMap::getID, map -> map));
			
			// Map Tiles to Maps
			List<MapTileToMapMap> mapTilesToMaps = InitialData.getMapTilesToMaps();
			for(MapTileToMapMap mapTileToMap: mapTilesToMaps){
				MapTile mapTile = mapTilesByID.get(mapTileToMap.getMapTileID());
				GameMap map = mapsByID.get(mapTileToMap.getMapID());
				map.addMapTile(mapTile);
			}
			
			// Players
			List<Player> players = InitialData.getPlayers();
			id = 1;
			for(Player player: players){
				player.setID(id++);
			}
			playersByID = players.stream()
					.collect(Collectors.toMap(Player::getID, player -> player));
			
			// Items for players
			for(Player player: players){
				// Helm
				if(player.getHelmID() != null){
					player.setHelm(itemsByID.get(player.getHelmID()));
				}
				
				// Braces
				if(player.getBracesID() != null){
					player.setBraces(itemsByID.get(player.getBracesID()));
				}
				
				// Chest
				if(player.getChestID() != null){
					player.setChest(itemsByID.get(player.getChestID()));
				}
				
				// Legs
				if(player.getLegsID() != null){
					player.setLegs(itemsByID.get(player.getLegsID()));
				}
				
				// Boots
				if(player.getBootsID() != null){
					player.setBoots(itemsByID.get(player.getBootsID()));
				}
				
				// Left Hand
				if(player.getLeftHandID() != null){
					player.setLeftHand(itemsByID.get(player.getLeftHandID()));
				}
				
				// Right Hand
				if(player.getRightHandID() != null){
					player.setRightHand(itemsByID.get(player.getRightHandID()));
				}
			}
			
			// Items to Players
			List<ItemToPlayerMap> itemsToPlayers = InitialData.getItemsToPlayers();
			for(ItemToPlayerMap itemToPlayer: itemsToPlayers){
				Item item = itemsByID.get(itemToPlayer.getItemID());
				Player player = playersByID.get(itemToPlayer.getPlayerID());
				player.getInventory().add(item);
			}
			
			// Enemies
			enemies = InitialData.getEnemies();
			id = 1;
			for(Enemy enemy: enemies){
				enemy.setID(id++);
			}
			
			// Name-Gender Pairs
			nameGenderPairs = InitialData.getNameGenderPairs();
			
			quests = InitialData.getQuests();
		}catch(IOException e){
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	/*
	 * Account Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<String> getUserPasswordByUsername(String username){
		String password = users.stream()
				.filter(user -> user.getUsername().equals(username))
				.map(User::getPassword)
				.findFirst().orElse(null);
		String error = StringUtil.isBlank(password)?"Failed to find user":null;
		return new DatabaseResult<>(password, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> doesUsernameExist(String username){
		Optional<User> foundUser = users.stream()
				.filter(user -> user.getUsername().equals(username))
				.findFirst();
		return new DatabaseResult<>(foundUser.isPresent(), null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> isEmailInUse(String email){
		Optional<User> foundUser = users.stream()
				.filter(user -> user.getEmail().equals(email))
				.findFirst();
		return new DatabaseResult<>(foundUser.isPresent(), null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> createNewUser(String username, String password, String email){
		users.add(new User(username, password, email));
		return new DatabaseResult<>(true, null);
	}
	
	/*
	 * Item Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<List<Item>> getAllItems(){
		return new DatabaseResult<>(items, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getItemByID(int id){
		return itemsByID.get(id) != null?new DatabaseResult<>(itemsByID.get(id), null)
				:new DatabaseResult<>(null, "No items match that ID number");
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getLegendaryItem(ItemType type){
		List<Item> legendaryItems = items.stream()
				.filter(item -> StringUtil.equals(item.getShortDescription(), "LEGENDARY") &&
						item.getType() == type)
				.toList();
		if(legendaryItems.size() == 1){
			return new DatabaseResult<>(legendaryItems.get(0), null);
		}else if(legendaryItems.isEmpty()){
			return new DatabaseResult<>(null, "No legendary items of that type");
		}else{
			return new DatabaseResult<>(legendaryItems.get(random.nextInt(legendaryItems.size())), null);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getHandheldItem(){
		List<Item> handheldItems = items.stream()
				.filter(item -> {
					ItemType type = item.getType();
					return (type == ItemType.L_HAND || type == ItemType.R_HAND) &&
							StringUtil.notEquals(item.getShortDescription(), "LEGENDARY");
				})
				.toList();
		if(handheldItems.size() == 1){
			return new DatabaseResult<>(handheldItems.get(0), null);
		}else if(handheldItems.isEmpty()){
			return new DatabaseResult<>(null, "Found no handheld items");
		}else{
			return new DatabaseResult<>(handheldItems.get(random.nextInt(handheldItems.size())), null);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getArmorItem(){
		List<Item> armorItems = items.stream()
				.filter(item -> {
					ItemType type = item.getType();
					return (type == ItemType.CHEST || type == ItemType.BRACES ||
							type == ItemType.LEGS || type == ItemType.BOOTS) &&
							StringUtil.notEquals(item.getShortDescription(), "LEGENDARY");
				})
				.toList();
		if(armorItems.size() == 1){
			return new DatabaseResult<>(armorItems.get(0), null);
		}else if(armorItems.isEmpty()){
			return new DatabaseResult<>(null, "Found no armor items");
		}else{
			return new DatabaseResult<>(armorItems.get(random.nextInt(armorItems.size())), null);
		}
	}
	
	/*
	 * Object Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<List<GameObject>> getAllObjects(){
		return new DatabaseResult<>(objects, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<GameObject> getObjectByID(int id){
		return objectsByID.get(id) != null?new DatabaseResult<>(objectsByID.get(id), null)
				:new DatabaseResult<>(null, "No objects match that ID number");
	}
	
	/*
	 * Map Tile and Map Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<List<MapTile>> getAllMapTiles(){
		return new DatabaseResult<>(mapTiles, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<MapTile> getMapTileByID(int id){
		return new DatabaseResult<>(mapTilesByID.get(id), null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<GameMap> getMapByID(int id){
		return new DatabaseResult<>(mapsByID.get(id), null);
	}
	
	/*
	 * Player Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Player> getPlayerByID(int id){
		return new DatabaseResult<>(playersByID.get(id), null);
	}
	
	/*
	 * Enemy Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<List<String>> getAllEnemyRaces(){
		return new DatabaseResult<>(enemies.stream()
				.map(Enemy::getRace)
				.distinct()
				.collect(Collectors.toList()), null);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Enemy> getEnemyByRace(String race){
		List<Enemy> foundEnemies = enemies.stream()
				.filter(enemy -> enemy.getRace().equals(race))
				.toList();
		if(foundEnemies.isEmpty()){
			return new DatabaseResult<>(null, "No enemies found");
		}
		Enemy enemy = foundEnemies.get(random.nextInt(foundEnemies.size()));
		NameAndGenderPair nameGenderPair = nameGenderPairs.get(random.nextInt(nameGenderPairs.size()));
		enemy.setName(nameGenderPair.getName());
		enemy.setGender(nameGenderPair.getGender());
		return new DatabaseResult<>(enemy, null);
	}
	
	@Override
	public List<Quest> getAllQuests() {
		for(Quest quest : quests) {
			if(quest.getRewardItems() != null) {
				for(Item item : quest.getRewardItems()) {
					for(Item listedItem : items) {
						if(item.getID() == listedItem.getID())
						{
							item.setName(listedItem.getName());
							item.setLongDescription(listedItem.getLongDescription());
							item.setShortDescription(listedItem.getShortDescription());
							item.setWeight(listedItem.getWeight());
							item.setQuestItem(listedItem.isQuestItem());
							break;
						}
					}
				}
			}
		}
		return quests;
	}

	@Override
	public Item removeItemFromObject(Item item, GameObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItemToObject(Item item, GameObject object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveGame(Game game) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Integer createNewGame(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game loadGame(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getGameIDs(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
