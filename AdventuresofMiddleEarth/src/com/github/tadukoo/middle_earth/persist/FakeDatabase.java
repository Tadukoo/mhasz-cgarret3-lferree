package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.aome.InitialData;
import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.GameMap;
import com.github.tadukoo.aome.construct.MapTile;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.GameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.aome.StringPair;

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
	private final GameMap map;
	private final List<MapTile> mapTiles;
	private final List<Quest> quests;
	private final List<Character> characters;
	private final List<Inventory> inventories;
	private final List<Player> players;
	private final List<Enemy> enemies;
	private final List<StringPair> nameGenders;
	
	public FakeDatabase(){
		try{
			random = new Random(System.currentTimeMillis());
			
			// Users
			users = InitialData.getUsers();
			int id = 1;
			for(User user: users){
				user.setItem(User.USER_ID_COLUMN_NAME, id);
				id++;
			}
			
			// Items
			items = InitialData.getItems();
			id = 1;
			for(Item item: items){
				item.setID(id);
				id++;
			}
			
			// Objects
			objects = InitialData.getObjects();
			id = 1;
			for(GameObject object: objects){
				object.setID(id);
				id++;
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
			
			//map = InitialData.getMap(); TODO: Fix?
			map = new GameMap();
			mapTiles = InitialData.getMapTiles();
			quests = InitialData.getQuests();
			characters = new ArrayList<>(); // TODO: Load in InitialData?
			//inventoryList.addAll(InitialData.getItemsToInventories()); TODO: Fix?
			inventories = new ArrayList<>();
			players = InitialData.getPlayers();
			enemies = InitialData.getEnemies();
			nameGenders = InitialData.getNameGenderList();
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
	
	@Override
	public List<MapTile> getAllMapTiles() {
		
		for (MapTile mapTile : mapTiles) {
			if (mapTile.getObjects() != null) {
				for (GameObject object : mapTile.getObjects()) {
					for (GameObject listedObject : getAllObjects().result()) {
						if (object.getID() == listedObject.getID()) {
							object.setCommandResponses(listedObject.getCommandResponses());
							object.setItems(listedObject.getItems());
							object.setLongDescription(listedObject.getLongDescription());
							object.setName(listedObject.getName());
							object.setShortDescription(listedObject.getShortDescription());
							break;
						}
					}
				}
			}
		}
		return mapTiles;
	}
	
	@Override
	public GameMap getMap() {
		getAllItems();
		getAllObjects();
		getAllMapTiles();
		for(MapTile mapTile : mapTiles) {
			map.addMapTile(mapTile);
		}
		return map;
	}
	
	@Override
	public List<Character> getAllCharacters() {
		return characters;
	}
	
	@Override
	public Player getPlayer() {
		
		for(Inventory inventory : inventories) {
			if(inventory.getinventory_id() == players.get(0).getinventory_id()) {
				players.get(0).setinventory(inventory);
			}
		}
		return players.get(0);
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
	public MapTile getMapTileByID(int mapTileID) {
		for(MapTile mapTile : mapTiles) {
			if(mapTile.getID() == mapTileID) {
				return mapTile;
			}
		}
		
		System.out.println("No mapTiles match that ID number");
		return null;
	}
	
	@Override
	public Character getCharacterByName(String characterName) {
		for(Character character : characters) {
			if(character.getname() == characterName) {
				return character;
			}
		}
		System.out.println("No character exists by that name");
		return null;
	}

	@Override
	public Inventory getInventoryByID(int inventoryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item removeItemFromInventory(Item item, Inventory inventory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item removeItemFromObject(Item item, GameObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItemToInventory(Item item, Inventory inventory) {
		// TODO Auto-generated method stub
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
	public Enemy getEnemyByRace(String race){
		Enemy foundEnemy = enemies.stream()
				.filter(enemy -> enemy.getrace().equals(race))
				.findFirst().orElse(null);
		if(foundEnemy == null){
			return null;
		}
		foundEnemy.setname(nameGenders.get(random.nextInt(nameGenders.size())).getString1());
		return foundEnemy;
	}

	@Override
	public ArrayList<Enemy> getAllEnemies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllEnemyRaces(){
		return new ArrayList<>(enemies.stream()
				.map(Enemy::getrace)
				.distinct()
				.toList());
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
