package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.middle_earth.model.Constructs.ItemType;
import com.github.tadukoo.middle_earth.model.Constructs.Map;
import com.github.tadukoo.middle_earth.model.Constructs.MapTile;
import com.github.tadukoo.middle_earth.model.Constructs.Item;
import com.github.tadukoo.middle_earth.model.Constructs.GameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.middle_earth.model.Quest;
import com.github.tadukoo.middle_earth.model.Characters.Character;
import com.github.tadukoo.middle_earth.model.Characters.Enemy;
import com.github.tadukoo.middle_earth.model.Characters.Inventory;
import com.github.tadukoo.middle_earth.model.Characters.Player;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.middle_earth.persist.pojo.User;
import com.github.tadukoo.util.StringUtil;
import persist.dbmod.StringPair;

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
	private final Map map;
	private final List<MapTile> mapTileList;
	private final List<GameObject> objectList;
	private final List<Item> itemList;
	private final List<Quest> questList;
	private final List<Character> characterList;
	private final List<Inventory> inventoryList;
	private final List<Player> playerList;
	private final List<User> users;
	private final List<Enemy> enemies;
	private final List<StringPair> nameGenders;
	
	public FakeDatabase(){
		try{
			random = new Random(System.currentTimeMillis());
			//map = InitialData.getMap(); TODO: Fix?
			map = new Map();
			mapTileList = InitialData.getMapTiles();
			objectList = InitialData.getObjects();
			itemList = InitialData.getItems();
			questList = InitialData.getQuests();
			characterList = new ArrayList<>(); // TODO: Load in InitialData?
			//inventoryList.addAll(InitialData.getItemsToInventories()); TODO: Fix?
			inventoryList = new ArrayList<>();
			playerList = InitialData.getPlayers();
			users = InitialData.getUsers();
			enemies = InitialData.getEnemies();
			nameGenders = InitialData.getNameGenderList();
		}catch(IOException e){
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
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
	
	@Override
	public List<Item> getAllItems() {
		return itemList;
	}
	
	@Override
	public List<GameObject> getAllObjects() {
		
		for(GameObject object : objectList) {
			if(object.getItems() != null) {
				for(Item item : object.getItems()) {
					for(Item listedItem : itemList) {
						if(item.getID() == listedItem.getID()) {
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
			
		return objectList;
	}
	
	@Override
	public List<MapTile> getAllMapTiles() {
		
		for (MapTile mapTile :mapTileList) {
			if (mapTile.getObjects() != null) {
				for (GameObject object : mapTile.getObjects()) {
					for (GameObject listedObject : getAllObjects()) {
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
		return mapTileList;
	}
	
	@Override
	public Map getMap() {
		getAllItems();
		getAllObjects();
		getAllMapTiles();
		for(MapTile mapTile : mapTileList) {
			map.addMapTile(mapTile);
		}
		return map;
	}
	
	@Override
	public List<Character> getAllCharacters() {
		return characterList;
	}
	
	@Override
	public Player getPlayer() {
		
		for(Inventory inventory : inventoryList) {
			if(inventory.getinventory_id() == playerList.get(0).getinventory_id()) {
				playerList.get(0).setinventory(inventory);
			}
		}
		return playerList.get(0);
	}
	
/*	public ArrayList<Inventory> getAllInventories() {
		for(Inventory inventory : inventoryList) {
			for(Item item : inventory.getitems()) {
				for(Item listedItem : itemList) {
					if(item.getID() == listedItem.getID())
					{
						item.setName(listedItem.getName());
						item.setLongDescription(listedItem.getLongDescription());
						item.setShortDescription(listedItem.getShortDescription());
						item.setItemWeight(listedItem.getItemWeight());
						item.setIsQuestItem(listedItem.getIsQuestItem());
						break;
					}
				}
			}
		}
		return inventoryList;
	}
*/	
	@Override
	public List<Quest> getAllQuests() {
		for(Quest quest : questList) {
			if(quest.getRewardItems() != null) {
				for(Item item : quest.getRewardItems()) {
					for(Item listedItem : itemList) {
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
		return questList;
	}
	
	
	
	@Override
	public Item getItemByID(int itemID) {
		for(Item item : itemList) {
			if(item.getID() == itemID) {
				return item;
			}
		}
		
		System.out.println("No items match that ID number");
		return null;
	}
	
	@Override
	public GameObject getObjectByID(int objectID) {
		for(GameObject object : objectList) {
			if(object.getID() == objectID) {
				return object;
			}
		}
		
		System.out.println("no objects match that ID number");
		return null;
	}
	
	@Override
	public MapTile getMapTileByID(int mapTileID) {
		for(MapTile mapTile : mapTileList) {
			if(mapTile.getID() == mapTileID) {
				return mapTile;
			}
		}
		
		System.out.println("No mapTiles match that ID number");
		return null;
	}
	
	@Override
	public Character getCharacterByName(String characterName) {
		for(Character character : characterList) {
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
	public Item getLegendaryItem(String itemType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getHandHeldItem(){
		List<Item> handHeldItems = itemList.stream()
				.filter(item -> {
					ItemType type = item.getType();
					return (type == ItemType.L_HAND || type == ItemType.R_HAND) &&
							!item.getLongDescription().startsWith("LEGENDARY");
				})
				.toList();
		return handHeldItems.get(random.nextInt(handHeldItems.size()));
	}

	@Override
	public Item getHandHeldItem(String whichHand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getArmorItem(){
		List<Item> armorItems = itemList.stream()
				.filter(item -> {
					ItemType type = item.getType();
					return (type == ItemType.CHEST || type == ItemType.BRACES ||
							type == ItemType.LEGS || type == ItemType.BOOTS) &&
							!item.getLongDescription().startsWith("LEGENDARY");
				})
				.toList();
		return armorItems.get(random.nextInt(armorItems.size()));
	}

	@Override
	public Item getArmorItem(String armorType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getLegendaryItem() {
		// TODO Auto-generated method stub
		return null;
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
