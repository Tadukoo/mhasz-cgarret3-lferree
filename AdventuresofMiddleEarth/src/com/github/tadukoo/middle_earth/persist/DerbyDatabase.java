package com.github.tadukoo.middle_earth.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.aome.StringPair;
import com.github.tadukoo.aome.construct.GameMap;
import com.github.tadukoo.aome.construct.MapTile;

@Deprecated
public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	private <ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	private <ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	/************************************************************************************************
	 * 										Loading Methods
	 * **********************************************************************************************/
	
	private void loadItem(Item item, ResultSet resultSet) throws SQLException{
		int index = 1;
		item.setID(resultSet.getInt(index++));
		
		item.setName(resultSet.getString(index++));
		item.setLongDescription(resultSet.getString(index++));
		item.setShortDescription(resultSet.getString(index++));
		
		item.setDescriptionUpdate(resultSet.getString(index++));
		item.setAttackBonus(resultSet.getInt(index++));
  		item.setDefenseBonus(resultSet.getInt(index++));
  		item.setHPBonus(resultSet.getInt(index++));

		item.setWeight(resultSet.getFloat(index++));
		item.setType(ItemType.valueOf(resultSet.getString(index++)));
		item.setLevelRequirement(resultSet.getInt(index));
	}
	
	private void loadObject(GameObject object, ResultSet resultSet) throws SQLException{
		int index = 1;
		object.setID(resultSet.getInt(index++));
		object.setName(resultSet.getString(index++));
		object.setLongDescription(resultSet.getString(index++));
		object.setShortDescription(resultSet.getString(index));
	}
	
	private void loadObjectCommandResponse(HashMap<String, String> objectCommandResponse, ResultSet resultSet) {
		try {
			objectCommandResponse.put(resultSet.getString(1), resultSet.getString(2));
		} catch (SQLException e) {
			throw new UnsupportedOperationException("Derby Database no longer supported!");
		}
	}
	
	private void loadMapTileConnections(HashMap<String, Integer> mapTileConnections, ResultSet resultSet, int index) throws SQLException {
		mapTileConnections.put("north", resultSet.getInt(index++));
		mapTileConnections.put("northeast", resultSet.getInt(index++));
		mapTileConnections.put("east", resultSet.getInt(index++));
		mapTileConnections.put("southeast", resultSet.getInt(index++));
		mapTileConnections.put("south", resultSet.getInt(index++));
		mapTileConnections.put("southwest", resultSet.getInt(index++));
		mapTileConnections.put("west", resultSet.getInt(index++));
		mapTileConnections.put("northwest", resultSet.getInt(index));
	}
	
	private void loadMapTile(MapTile mapTile, ResultSet resultSet) throws SQLException{
		int index = 1;
		mapTile.setID(resultSet.getInt(index++));
		mapTile.setName(resultSet.getString(index++));
		mapTile.setLongDescription(resultSet.getString(index++));
		mapTile.setShortDescription(resultSet.getString(index++));		
		mapTile.setAreaDifficulty(resultSet.getInt(index));
	}
	 
	private void loadMap(GameMap map, ResultSet resultSet) throws SQLException{
		int index = 1;
		map.setID(resultSet.getInt(index++));
		map.setName(resultSet.getString(index++));
		map.setLongDescription(resultSet.getString(index++));
		map.setShortDescription(resultSet.getString(index));
	}
	
	private void loadEnemy(Enemy enemy, ResultSet resultSet) throws SQLException {
		int index = 1;
		enemy.setrace(resultSet.getString(index++));
		enemy.sethit_points(resultSet.getInt(index++));
		enemy.setmagic_points(resultSet.getInt(index++));
		
		StringPair randNameGender = getRandomName();
		
		enemy.setname(randNameGender.getString1());
		enemy.setgender(randNameGender.getString2());
		enemy.setattack(resultSet.getInt(index++));
		enemy.setdefense(resultSet.getInt(index++));
		enemy.setspecial_attack(resultSet.getInt(index++));
		enemy.setspecial_defense(resultSet.getInt(index));
	}
	
	private void loadNameGender(StringPair nameGender, ResultSet resultSet) throws SQLException{
		int index = 1;
		nameGender.setString1(resultSet.getString(index++));
		nameGender.setString2(resultSet.getString(index));
	}
	
	private void loadPlayer(Player player, ResultSet resultSet){
		int index = 1;
		try {
		player.setrace(resultSet.getString(index++));
		player.setname(resultSet.getString(index++));
		player.setgender(resultSet.getString(index++));
		player.setlevel(resultSet.getInt(index++));
		player.sethit_points(resultSet.getInt(index++));
		
		player.setmagic_points(resultSet.getInt(index++));
		player.setattack(resultSet.getInt(index++));
		player.setdefense(resultSet.getInt(index++));
		player.setspecial_attack(resultSet.getInt(index++));
		player.setspecial_defense(resultSet.getInt(index++));
		
		player.setcoins(resultSet.getInt(index++));
		player.setlocation(resultSet.getInt(index++));
		player.setinventory_id(resultSet.getInt(index++));

		float carryWeight = 0;
		player.setinventory(getInventoryByID(player.getinventory_id()));
		
		// Sum up inventory weight
		for(Item item : player.getinventory().getitems()){
			carryWeight += item.getWeight();
		}

		Item emptyItemSlot = new Item();
		emptyItemSlot.setAttackBonus(0);
		emptyItemSlot.setDefenseBonus(0);
		emptyItemSlot.setDescriptionUpdate("You haven't equipped one");
		emptyItemSlot.setHPBonus(0);
		emptyItemSlot.setLevelRequirement(0);
		emptyItemSlot.setID(0);
		emptyItemSlot.setQuestItem(false);
		emptyItemSlot.setWeight(0);
		emptyItemSlot.setLongDescription("Empty Slot");
		emptyItemSlot.setShortDescription("Empty Slot");
		emptyItemSlot.setName("Empty Slot");

		int itemID;
		
		// helm
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.HELM);
			player.sethelm(emptyItemSlot);
		} else {
			player.sethelm(getItemByID(itemID).result());
			carryWeight += player.gethelm().getWeight();
		}
		
		// braces
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.BRACES);
			player.setbraces(emptyItemSlot);
		} else {
			player.setbraces(getItemByID(itemID).result());
			carryWeight += player.getbraces().getWeight();
		}		
		
		// chest
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.CHEST);
			player.setchest(emptyItemSlot);
		} else {
			player.setchest(getItemByID(itemID).result());
			carryWeight += player.getchest().getWeight();
		}
		
		// legs
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.LEGS);
			player.setlegs(emptyItemSlot);
		} else {
			player.setlegs(getItemByID(itemID).result());
			carryWeight += player.getlegs().getWeight();
		}
		
		// boots
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.BOOTS);
			player.setboots(emptyItemSlot);
		} else {
			player.setboots(getItemByID(itemID).result());
			carryWeight += player.getboots().getWeight();
		}
		
		// l_hand
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.L_HAND);
			player.setl_hand(emptyItemSlot);
		} else {
			player.setl_hand(getItemByID(itemID).result());
			carryWeight += player.getl_hand().getWeight();
		}
		
		// r_hand
		if((itemID = resultSet.getInt(index++)) == 0) {
			emptyItemSlot.setType(ItemType.R_HAND);
			player.setr_hand(emptyItemSlot);
		} else {
			player.setr_hand(getItemByID(itemID).result());
			carryWeight += player.getr_hand().getWeight();
		}
		
		player.setcarry_weight(index++);
		player.setexperience(resultSet.getInt(index));
		
		// Add the sum total of weight to the inventory
		player.getinventory().setweight((int)carryWeight);
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**************************************************************************************************
	 * 										Get Methods
	 **************************************************************************************************/
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Maps
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public GameMap getMap() {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSetMap = null;
			ResultSet resultSetMapTileIDs = null;
			
			try{
				// retrieve all attributes
				stmt = conn.prepareStatement(
						"select * " +
								"  from maps "
				);
				
				GameMap map = new GameMap();
				MapTile emptyMapTile = new MapTile();
				map.addMapTile(emptyMapTile);
				
				resultSetMap = stmt.executeQuery();
				
				// for testing that a result was returned
				boolean found = false;
				
				while(resultSetMap.next()){
					found = true;
					loadMap(map, resultSetMap);
					
					stmt = conn.prepareStatement(
							" select maptiles.* "
									+ "from maptilestomaps, maptiles "
									+ "where maptilestomaps.map_id = ?"
									+ "AND maptiles.maptile_id = maptilestomaps.maptile_id "
					);
					stmt.setInt(1, map.getID());
					resultSetMapTileIDs = stmt.executeQuery();
					
					while(resultSetMapTileIDs.next()){
						MapTile mapTile = new MapTile();
						mapTile.setID(resultSetMapTileIDs.getInt(1));
						mapTile = getMapTileByID(mapTile.getID());
						map.addMapTile(mapTile);
					}
				}
				
				// check if the maps were found
				if(!found){
					System.out.println("<maps> table is empty");
				}
				
				return map;
			}finally{
				DBUtil.closeQuietly(resultSetMapTileIDs);
				DBUtil.closeQuietly(resultSetMap);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								MapTiles 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<MapTile> getAllMapTiles() {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
			ResultSet resultSetMapTiles = null;
			ResultSet resultSetObjects = null;
			ResultSet resultSetObjectCommandResponses = null;
			ResultSet resultSetItems = null;
			
			try{
				// retrieve all attributes
				stmt = conn.prepareStatement(
						"select * "
								+ "from maptiles, maptileconnections "
								+ "where maptiles.maptile_id = maptileconnections.maptile_id"
				);
				
				ArrayList<MapTile> resultMapTiles = new ArrayList<>();
				
				resultSetMapTiles = stmt.executeQuery();
				
				// for testing that a result was returned
				boolean found = false;
				
				while(resultSetMapTiles.next()){
					found = true;
					
					MapTile mapTile = new MapTile();
					loadMapTile(mapTile, resultSetMapTiles);
					
					// remember to skip an index for the repeated maptile_id from the connections table
					loadMapTileConnections(mapTile.getConnections(), resultSetMapTiles, 7);
					
					// Now get all objects associated with the mapTile
					stmt = conn.prepareStatement(
							"select * "
									+ "from objects, objectstomaptiles "
									+ "where objectstomaptiles.maptile_id = ? "
									+ "AND objectstomaptiles.object_id = objects.object_id "
					);
					
					stmt.setInt(1, mapTile.getID());
					ArrayList<GameObject> resultObjects = new ArrayList<>();
					
					resultSetObjects = stmt.executeQuery();
					
					while(resultSetObjects.next()){
						GameObject object = new GameObject();
						loadObject(object, resultSetObjects);
						
						// Now get the commandResponses
						stmt2 = conn.prepareStatement(
								"select objectcommandresponses.command, objectcommandresponses.response "
										+ "from objectcommandresponses "
										+ "where objectcommandresponses.object_id = ?"
						);
						stmt2.setInt(1, object.getID());
						ArrayList<HashMap<String, String>> resultObjectCommandResponses = new ArrayList<>();
						
						resultSetObjectCommandResponses = stmt2.executeQuery();
						
						while(resultSetObjectCommandResponses.next()){
							HashMap<String, String> objectCommandResponse = new HashMap<>();
							loadObjectCommandResponse(objectCommandResponse, resultSetObjectCommandResponses);
							
							resultObjectCommandResponses.add(objectCommandResponse);
						}
						if(!resultObjectCommandResponses.isEmpty()){
							for(HashMap<String, String> objectCommandResponse: resultObjectCommandResponses){
								object.setCommandResponses(objectCommandResponse);
							}
						}
						
						// Now get the items in the object
						stmt = conn.prepareStatement(
								"select * " +
										"	from items, itemstoobjects" +
										"   where itemstoobjects.object_id = ?"
										+ "AND itemstoobjects.item_id = items.item_id "
						);
						
						stmt.setInt(1, object.getID());
						ArrayList<Item> resultItems = new ArrayList<>();
						
						resultSetItems = stmt.executeQuery();
						
						while(resultSetItems.next()){
							Item item = new Item();
							loadItem(item, resultSetItems);
							
							resultItems.add(item);
						}
						if(!resultItems.isEmpty()){
							for(Item item: resultItems){
								object.addItem(item);
							}
						}
						
						resultObjects.add(object);
					}
					if(!resultObjects.isEmpty()){
						mapTile.setObjects(resultObjects);
					}
					
					resultMapTiles.add(mapTile);
				}
				
				// check if the maptiles were found
				if(!found){
					System.out.println("<maptiles> table is empty");
				}
				
				return resultMapTiles;
			}finally{
				DBUtil.closeQuietly(resultSetItems);
				DBUtil.closeQuietly(resultSetObjectCommandResponses);
				DBUtil.closeQuietly(resultSetObjects);
				DBUtil.closeQuietly(resultSetMapTiles);
				DBUtil.closeQuietly(stmt2);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}

	@Override
	public MapTile getMapTileByID(int mapTileID) {
		return executeTransaction(conn -> {
			
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
			ResultSet resultSetMapTiles = null;
			ResultSet resultSetObjects = null;
			ResultSet resultSetObjectCommandResponses = null;
			ResultSet resultSetItems = null;
			try{
				// retrieve all attributes
				stmt = conn.prepareStatement(
						"select * " +
								"  from maptiles, maptileconnections "
								+ "where maptiles.maptile_id = ? "
								+ " and maptiles.maptile_id = maptileconnections.maptile_id"
				);
				stmt.setInt(1, mapTileID);
				
				resultSetMapTiles = stmt.executeQuery();
				
				MapTile mapTile = new MapTile();
				
				// for testing that a result was returned
				boolean found = false;
				
				while(resultSetMapTiles.next()){
					found = true;
					
					loadMapTile(mapTile, resultSetMapTiles);
					loadMapTileConnections(mapTile.getConnections(), resultSetMapTiles, 7);
					
					// Now get all objects associated with the mapTile
					stmt = conn.prepareStatement(
							"select * "
									+ "from objects, objectstomaptiles "
									+ "where objectstomaptiles.maptile_id = ? "
									+ "AND objectstomaptiles.object_id = objects.object_id "
					);
					
					stmt.setInt(1, mapTile.getID());
					ArrayList<GameObject> resultObjects = new ArrayList<>();
					
					resultSetObjects = stmt.executeQuery();
					
					while(resultSetObjects.next()){
						GameObject object = new GameObject();
						loadObject(object, resultSetObjects);
						
						// Now get the commandResponses
						stmt2 = conn.prepareStatement(
								"select objectcommandresponses.command, objectcommandresponses.response "
										+ "from objectcommandresponses "
										+ "where objectcommandresponses.object_id = ?"
						);
						stmt2.setInt(1, object.getID());
						ArrayList<HashMap<String, String>> resultObjectCommandResponses = new ArrayList<>();
						
						resultSetObjectCommandResponses = stmt2.executeQuery();
						
						while(resultSetObjectCommandResponses.next()){
							HashMap<String, String> objectCommandResponse = new HashMap<>();
							loadObjectCommandResponse(objectCommandResponse, resultSetObjectCommandResponses);
							
							resultObjectCommandResponses.add(objectCommandResponse);
						}
						if(!resultObjectCommandResponses.isEmpty()){
							for(HashMap<String, String> objectCommandResponse: resultObjectCommandResponses){
								object.setCommandResponses(objectCommandResponse);
							}
						}
						
						// Now get the items in the object
						stmt = conn.prepareStatement(
								"select * " +
										"	from items, itemstoobjects" +
										"   where itemstoobjects.object_id = ?"
										+ "AND itemstoobjects.item_id = items.item_id "
						);
						
						stmt.setInt(1, object.getID());
						ArrayList<Item> resultItems = new ArrayList<>();
						
						resultSetItems = stmt.executeQuery();
						
						while(resultSetItems.next()){
							Item item = new Item();
							loadItem(item, resultSetItems);
							
							resultItems.add(item);
						}
						if(!resultItems.isEmpty()){
							for(Item item: resultItems){
								object.addItem(item);
							}
						}
						
						resultObjects.add(object);
					}
					if(!resultObjects.isEmpty()){
						mapTile.setObjects(resultObjects);
					}
				}
				
				// check if the maptile was found
				if(!found){
					System.out.println("no maptiles with that id");
				}
				
				return mapTile;
			}finally{
				DBUtil.closeQuietly(resultSetItems);
				DBUtil.closeQuietly(resultSetObjectCommandResponses);
				DBUtil.closeQuietly(resultSetObjects);
				DBUtil.closeQuietly(resultSetMapTiles);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(stmt2);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Objects 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public DatabaseResult<List<GameObject>> getAllObjects(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}

	@Override
	public DatabaseResult<GameObject> getObjectByID(int id){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Items 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public DatabaseResult<List<Item>> getAllItems(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	@Override
	public DatabaseResult<Item> getItemByID(int itemID){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	@Override
	public DatabaseResult<Item> getLegendaryItem(ItemType itemType){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	@Override
	public DatabaseResult<Item> getHandheldItem(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	@Override
	public DatabaseResult<Item> getArmorItem(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Players
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Player getPlayer() {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement("select * from players");
				
				Player result = new Player();
				resultSet = stmt.executeQuery();
				
				boolean found = false;
				while(resultSet.next()){
					found = true;
					
					loadPlayer(result, resultSet);
				}
				
				if(!found){
					System.out.println("<players> table is empty");
				}
				
				return result;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//  								Characters 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<Character> getAllCharacters() {
		ArrayList<Character> characterList = new ArrayList<>();
		characterList.add(getPlayer());
		return characterList;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Enemies 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ArrayList<String> getAllEnemyRaces() {
		return executeTransaction(conn -> {
			ResultSet resultSet = null;
			PreparedStatement stmt = null;
			ArrayList<String> enemyRaceList = new ArrayList<>();
			try{
				stmt = conn.prepareStatement(
						"select enemies.race "
								+ "from enemies"
				);
				resultSet = stmt.executeQuery();
				while(resultSet.next()){
					enemyRaceList.add(resultSet.getString(1));
				}
				return enemyRaceList;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
			}
		});
	}

	public ArrayList<Enemy> getAllEnemies() {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"select * "
								+ "from enemies");
				resultSet = stmt.executeQuery();
				
				ArrayList<Enemy> enemyList = new ArrayList<>();
				
				boolean found = false;
				while(resultSet.next()){
					found = true;
					Enemy enemy = new Enemy();
					loadEnemy(enemy, resultSet);
					enemyList.add(enemy);
				}
				
				if(!found){
					System.out.println("EnemyList is empty");
				}
				
				return enemyList;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	@Override
	public Enemy getEnemyByRace(String race) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"select * "
								+ "from enemies "
								+ "where enemies.race = ?"
				);
				stmt.setString(1, race);
				resultSet = stmt.executeQuery();
				
				Enemy enemy = new Enemy();
				
				// for testing that a result was returned
				boolean found = false;
				
				while(resultSet.next()){
					found = true;
					loadEnemy(enemy, resultSet);
				}
				
				// check if the item was found
				if(!found){
					System.out.println("no enemies with that race");
				}
				
				return enemy;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	private StringPair getRandomName() {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"select names.name, names.gender "
								+ "from names "
								+ "where names.number = ?"
				);
				Random rand = new Random();
				stmt.setInt(1, rand.nextInt(8) + 1);
				
				resultSet = stmt.executeQuery();
				StringPair nameGender = new StringPair();
				
				boolean found = false;
				
				while(resultSet.next()){
					found = true;
					loadNameGender(nameGender, resultSet);
				}
				
				if(!found){
					System.out.println("no names under that number");
				}
				
				return nameGender;
			}finally{
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(conn);
			}
		});
	}

	////////////////////////////////////////////////////////////////////////////////////////
	//									Inventories
	////////////////////////////////////////////////////////////////////////////////////////
	public Inventory getInventoryByID (int inventoryID) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"select items.item_id "
								+ "from items, itemstoinventories "
								+ "where items.item_id = itemstoinventories.item_id "
								+ "AND itemstoinventories.inventory_id = ? "
				);
				stmt.setInt(1, inventoryID);
				
				resultSet = stmt.executeQuery();
				
				Inventory inventory = new Inventory();
				ArrayList<Item> itemList = new ArrayList<>();
				
				boolean found = false;
				while(resultSet.next()){
					found = true;
					
					Item item = getItemByID(resultSet.getInt(1)).result();
					itemList.add(item);
				}
				if(!found){
					System.out.println("This inventory is empty");
				}
				inventory.setitems(itemList);
				
				return inventory;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	private Inventory getInventory (String playerName, int inventoryID) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"select items.item_id "
								+ "from items, itemstoinventories" + playerName
								+ " where items.item_id = itemstoinventories" + playerName + ".item_id "
								+ " AND itemstoinventories" + playerName + ".inventory_id = ? "
				);
				stmt.setInt(1, inventoryID);
				
				resultSet = stmt.executeQuery();
				
				Inventory inventory = new Inventory();
				ArrayList<Item> itemList = new ArrayList<>();
				
				boolean found = false;
				while(resultSet.next()){
					found = true;
					
					Item item = getItemByID(resultSet.getInt(1)).result();
					itemList.add(item);
				}
				if(!found){
					System.out.println("This inventory is empty");
				}
				inventory.setitems(itemList);
				
				return inventory;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	//										Users
	////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public DatabaseResult<Boolean> doesUsernameExist(String username){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	@Override
	public DatabaseResult<Boolean> isEmailInUse(String email){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}

	public DatabaseResult<String> getUserPasswordByUsername(final String userName){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//  									Games
	///////////////////////////////////////////////////////////////////////////////////////	
	
	public ArrayList<Integer> getGameIDs(final String username) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			try{
				stmt = conn.prepareStatement(
						"select gamestousers.game_id "
								+ "from gamestousers "
								+ "where gamestousers.username = ? ");
				stmt.setString(1, username);
				resultSet = stmt.executeQuery();
				
				ArrayList<Integer> gameIDList = new ArrayList<>();
				
				boolean found = false;
				while(resultSet.next()){
					found = true;
					gameIDList.add(resultSet.getInt(1));
				}
				
				if(!found){
					System.out.println("That username was not found");
				}
				
				return gameIDList;
			}finally{
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	/******************************************************************************************************
	 * 										*Get Specific* Methods
	 ******************************************************************************************************/
	
	@Override
	public Character getCharacterByName(String characterName){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}	
	

	/*******************************************************************************************************
	*											load/save game
	********************************************************************************************************/
	// Will need to take gameID in future
	public Game loadGame(int gameID) {
		
		Game game = new Game();

		game.setmap(getMap());
		
		game.setobjects(getAllObjects().result());
		game.setitems(getAllItems().result());
		ArrayList<Character> characterList = new ArrayList<>();
		characterList.add(getPlayer());
		characterList.get(0).setinventory(getInventory(characterList.get(0).getname(), characterList.get(0).getinventory_id()));
		game.setcharacters(characterList);
		
		return game;
	}
	
	public void saveGame(Game game) {
		updateMap(game.getmap());
		updateCharacters(game.getcharacters());
	} 
	
	/**************************************************************************************************
	 * 										Init new game
	***************************************************************************************************/
	
	@Override
	public DatabaseResult<Boolean> createNewUser(String username, String password, String email){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	public Integer createNewGame(String username) { 
		return executeTransaction (conn -> {
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
			PreparedStatement stmt3 = null;
			ResultSet resultSet = null;
			
			try{
				stmt = conn.prepareStatement(
						"insert into gamestousers (username) values (?) "
				);
				stmt.setString(1, username);
				stmt.executeUpdate();
				
				Player player = createPlayer();
				
				// create userObjects -- stmt 1
				createWorldForNewGame(player.getname());
				
				// get gameID from gamestousers -- resultSet
				stmt2 = conn.prepareStatement(
						"select game_id from gamestousers where gamestousers.username = ?"
				);
				stmt2.setString(1, username);
				resultSet = stmt2.executeQuery();
				
				int gameID = 0;
				while(resultSet.next()){
					gameID = resultSet.getInt(1);
				}
				
				stmt3 = conn.prepareStatement(
						"insert into playerstogames (playername, game_id) values (?, ?) "
				);
				stmt3.setString(1, player.getname());
				stmt3.setInt(2, gameID);
				
				insertPlayer(player);
				
				return gameID;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(stmt2);
				DBUtil.closeQuietly(stmt3);
			}
		});
	}
	
	private void createWorldForNewGame(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement insertMapTile = null;
			PreparedStatement insertMapTileConnections = null;
			PreparedStatement insertObject = null;
			PreparedStatement insertItemsToObjects = null;
			PreparedStatement insertObjectCommandResponses = null;
			
			ArrayList<MapTile> mapTileList = getAllMapTiles();
			try{
				createWildcardMapTileTable(playerName);
				createWildcardMapTileConnectionsTable(playerName);
				createWildcardObjectsTable(playerName);
				createWildcardObjectCommandResponseTable(playerName);
				createWildcardItemsToObjectsTable(playerName);
				createWildcardItemsToInventoriesTable(playerName);
				
				insertMapTile = conn.prepareStatement("insert into maptiles" + playerName + " (maptilename, longdescription, shortdescription, difficulty) values (?, ?, ?, ?)");
				insertMapTileConnections = conn.prepareStatement("insert into maptileconnections" + playerName + " "
						+ "("
						+ "north,"
						+ "northeast,"
						+ "east,"
						+ "southeast,"
						+ "south,"
						+ "southwest,"
						+ "west,"
						+ "northwest)"
						+ " values (?,?,?,?,?,?,?,?)");
				
				insertObject = conn.prepareStatement("insert into objects" + playerName + " (objectname, longdescription, shortdescription) values (?, ?, ?)");
				insertItemsToObjects = conn.prepareStatement("insert into itemstoobjects" + playerName + " (item_id, object_id) values (?, ?)");
				insertObjectCommandResponses = conn.prepareStatement(" insert into objectCommandResponses" + playerName + " (object_id, command, response) values (?, ?, ?)");
				
				for(MapTile mapTile: mapTileList){
					insertMapTile.setString(1, mapTile.getName());
					insertMapTile.setString(2, mapTile.getLongDescription());
					insertMapTile.setString(3, mapTile.getShortDescription());
					insertMapTile.setInt(4, mapTile.getAreaDifficulty());
					
					// Connections for each mapTile
					int i = 1;
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("north"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("northeast"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("east"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("southeast"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("south"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("southwest"));
					insertMapTileConnections.setInt(i++, mapTile.getConnections().get("west"));
					insertMapTileConnections.setInt(i, mapTile.getConnections().get("northwest"));
					insertMapTileConnections.addBatch();
					
					// If there are objects on the maptile, add them
					if(mapTile.getObjects() != null){
						for(GameObject object: mapTile.getObjects()){
							insertObject.setString(1, object.getName());
							insertObject.setString(2, object.getLongDescription());
							insertObject.setString(3, object.getShortDescription());
							
							// If there are items in the objects, add them
							if(!object.getItems().isEmpty()){
								for(Item item: object.getItems()){
									insertItemsToObjects.setInt(1, item.getID());
									insertItemsToObjects.setInt(2, object.getID());
									
									insertItemsToObjects.addBatch();
								}
							}
							insertObject.addBatch();
						}
					}
					insertMapTile.addBatch();
				}
				insertObject.executeBatch();
				insertItemsToObjects.executeBatch();
				insertMapTile.executeBatch();
				insertMapTileConnections.executeBatch();
				insertObjectCommandResponses.executeBatch();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(insertObject);
				DBUtil.closeQuietly(insertItemsToObjects);
				DBUtil.closeQuietly(insertObjectCommandResponses);
				DBUtil.closeQuietly(insertMapTileConnections);
				DBUtil.closeQuietly(insertMapTile);
			}
		});
	}
	
	private void insertPlayer(Player player) {
		executeTransaction(conn -> {
			PreparedStatement insertPlayer = null;
			try{
				insertPlayer = conn.prepareStatement("insert into players ("
						+ "race, name, gender, level, hit_points, "
						+ "magic_points, attack, defense, sp_attack, sp_defense, "
						+ "coins, map_location, inventory_id, helm_item_id, braces_item_id, "
						+ "chest_item_id, legs_item_id, boots_item_id, l_hand_item_id, r_hand_item_id,"
						+ "experience, carry_weight) "
						+ "values("
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?)");
				
				int i = 1;
				insertPlayer.setString(i++, player.getrace());
				insertPlayer.setString(i++, player.getname());
				insertPlayer.setString(i++, player.getgender());
				insertPlayer.setInt(i++, player.getlevel());
				insertPlayer.setInt(i++, player.gethit_points());
				
				insertPlayer.setInt(i++, player.getmagic_points());
				insertPlayer.setInt(i++, player.getattack());
				insertPlayer.setInt(i++, player.getdefense());
				insertPlayer.setInt(i++, player.getspecial_attack());
				insertPlayer.setInt(i++, player.getspecial_defense());
				
				insertPlayer.setInt(i++, player.getcoins());
				insertPlayer.setInt(i++, player.getlocation());
				insertPlayer.setInt(i++, player.getinventory_id());
				insertPlayer.setInt(i++, player.gethelm().getID());
				insertPlayer.setInt(i++, player.getbraces().getID());
				
				insertPlayer.setInt(i++, player.getchest().getID());
				insertPlayer.setInt(i++, player.getlegs().getID());
				insertPlayer.setInt(i++, player.getboots().getID());
				insertPlayer.setInt(i++, player.getl_hand().getID());
				insertPlayer.setInt(i++, player.getr_hand().getID());
				
				insertPlayer.setInt(i++, player.getexperience());
				insertPlayer.setInt(i, player.getcarry_weight());
				
				insertPlayer.addBatch();
				
				insertPlayer.executeBatch();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(insertPlayer);
			}
		});
	}
	
	private void createWildcardMapTileTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			try{
				stmt = conn.prepareStatement(
						"create table maptiles" + playerName + " (" +
								"   maptile_id integer primary key " +
								"       generated always as identity (start with 1, increment by 1), " +
								"   maptilename varchar(40)," +
								"	longdescription varchar(200)," +
								"	shortdescription varchar(100)," +
								"	difficulty int" +
								")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private void createWildcardMapTileConnectionsTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"create table maptileconnections" + playerName + " (" +
								"   maptile_id integer primary key " +
								"       generated always as identity (start with 1, increment by 1), " +
								
								"   north int," +
								"	northeast int," +
								"   east int," +
								"   southeast int," +
								"   south int," +
								"   southwest int," +
								"   west int," +
								"   northwest int" +
								")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private void createWildcardObjectsTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			try{
				stmt = conn.prepareStatement(
						"create table objects" + playerName + " (" +
								"	object_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), " +
								"	objectname varchar(40)," +
								"	longdescription varchar(200)," +
								"	shortdescription varchar(100)" +
								")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private void createWildcardItemsToObjectsTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"create table itemstoobjects" + playerName + " (" +
								"   item_id int," +
								"   object_id int" +
								")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private void createWildcardObjectCommandResponseTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"create table objectcommandresponses" + playerName + " ("
								+ "object_id int, "
								+ "command varchar(10), "
								+ "response varchar (100)"
								+ ")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private void createWildcardItemsToInventoriesTable(String playerName) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"create table itemstoinventories" + playerName + " ("
								+ "item_id int, "
								+ "inventory_id int"
								+ ")"
				);
				stmt.executeUpdate();
				
				return true;
			}finally{
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	private Player createPlayer() {
		Player player = new Player();		
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Choose your name: ");
		
		player.setname(keyboard.next());
		
		String race = "Human";
		player.setrace(race);
		
		System.out.println("Choose your gender");
		player.setgender(keyboard.next());
		
		player.setlevel(1);
		player.sethit_points(100);
		
		player.setmagic_points(25);
		player.setattack(10);
		player.setdefense(10);
		player.setspecial_attack(15);
		player.setspecial_defense(10);
		
		player.setcoins(0);
		player.setlocation(1);

		int carryWeight = 0;

		Item emptyItemSlot = new Item();
		emptyItemSlot.setAttackBonus(0);
		emptyItemSlot.setDefenseBonus(0);
		emptyItemSlot.setDescriptionUpdate("You haven't equipped one");
		emptyItemSlot.setHPBonus(0);
		emptyItemSlot.setLevelRequirement(0);
		emptyItemSlot.setID(0);
		emptyItemSlot.setQuestItem(false);
		emptyItemSlot.setWeight(0);
		emptyItemSlot.setLongDescription("Empty Slot");
		emptyItemSlot.setShortDescription("Empty Slot");
		emptyItemSlot.setName("Empty Slot");
		
		// helm
		emptyItemSlot.setType(ItemType.HELM);
		player.sethelm(emptyItemSlot);
		
		// braces
		emptyItemSlot.setType(ItemType.BRACES);
		player.setbraces(emptyItemSlot);
		
		// chest
		emptyItemSlot.setType(ItemType.CHEST);
		player.setchest(emptyItemSlot);
		
		emptyItemSlot.setType(ItemType.LEGS);
		player.setlegs(emptyItemSlot);
		
		// boots
		emptyItemSlot.setType(ItemType.BOOTS);
		player.setboots(emptyItemSlot);
		
		// l_hand
		emptyItemSlot.setType(ItemType.L_HAND);
		player.setl_hand(emptyItemSlot);
		
		// r_hand
		emptyItemSlot.setType(ItemType.R_HAND);
		player.setr_hand(emptyItemSlot);
		
		player.setcarry_weight(20);
		player.setexperience(0);
		
		player.setinventory(new Inventory());
		
		// Add the sum total of weight to the inventory
		player.getinventory().setweight(carryWeight);
		
		//keyboard.close();
		
		
		
		return player;
	}
	
	/*******************************************************************************************************
	 * 										Update Database Methods
	********************************************************************************************************/
	
	private void updateMap(final GameMap map) {
		// In the future will need to update map for Editor
		// update all maptiles
		updateMapTiles(map.getMapTiles());
	}
	
	private void updateMapTiles(final ArrayList<MapTile> mapTileList) {
		
		for(MapTile mapTile : mapTileList) {
			updateMapTile(mapTile);
		}
	}
	
	private void updateMapTile(final MapTile mapTile) {
		// removes all objects from the objectstomaptile table
		removeAllObjectsFromMapTile(mapTile.getID());
		
		// adds every object in the maptile.getObjects() objectList
		// to the objectstomaptiles table
		if (mapTile.getObjects() != null) {
			for (GameObject object : mapTile.getObjects()) {
				addObjectToMapTile(object.getID(), mapTile.getID());
			}
		}
	}
	
	private void updateCharacters(ArrayList<Character> characterList) {
		//Inventory inventory = characterList.get(0).getinventory();
		updatePlayer(characterList.get(0));
		updateInventory(characterList.get(0));
		//characterList.get(0).setinventory(getInventoryByID(characterList.get(0).getinventory_id()));
	}
	
	private void updatePlayer(Character playerC) {
		executeTransaction(conn -> {
			PreparedStatement stmtRemovePlayer = null;
			PreparedStatement stmtUpdatePlayer = null;
			
			Player player = (Player) playerC;
			
			try {
				stmtRemovePlayer = conn.prepareStatement(
						"delete from players "
						+ "where players.name = ? "
				);
				stmtRemovePlayer.setString(1, player.getname());
				stmtRemovePlayer.executeUpdate();
				
				stmtUpdatePlayer = conn.prepareStatement(
						"insert into players ("
						+ "race, name, gender, level, hit_points, "
						+ "magic_points, attack, defense, sp_attack, sp_defense, "
						+ "coins, map_location, inventory_id, helm_item_id, braces_item_id, "
						+ "chest_item_id, legs_item_id, boots_item_id, l_hand_item_id, r_hand_item_id, "
						+ "experience, carry_weight) "
						+ "values("
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?, ?, ?, ?,"
						+ "?, ?)"
				);
				
					int i = 1;
					stmtUpdatePlayer.setString(i++, player.getrace());
					stmtUpdatePlayer.setString(i++, player.getname());
					stmtUpdatePlayer.setString(i++, player.getgender());
					stmtUpdatePlayer.setInt(i++, player.getlevel());
					stmtUpdatePlayer.setInt(i++, player.gethit_points());
					
					stmtUpdatePlayer.setInt(i++, player.getmagic_points());
					stmtUpdatePlayer.setInt(i++, player.getattack());
					stmtUpdatePlayer.setInt(i++, player.getdefense());
					stmtUpdatePlayer.setInt(i++, player.getspecial_attack());
					stmtUpdatePlayer.setInt(i++, player.getspecial_defense());
					
					stmtUpdatePlayer.setInt(i++, player.getcoins());
					stmtUpdatePlayer.setInt(i++, player.getlocation());
					stmtUpdatePlayer.setInt(i++, player.getinventory_id());
					stmtUpdatePlayer.setInt(i++, player.gethelm().getID());
					stmtUpdatePlayer.setInt(i++, player.getbraces().getID());
					
					stmtUpdatePlayer.setInt(i++, player.getchest().getID());
					stmtUpdatePlayer.setInt(i++, player.getlegs().getID());
					stmtUpdatePlayer.setInt(i++, player.getboots().getID());
					stmtUpdatePlayer.setInt(i++, player.getl_hand().getID());
					stmtUpdatePlayer.setInt(i++, player.getr_hand().getID());
					stmtUpdatePlayer.setInt(i++, player.getexperience());
					stmtUpdatePlayer.setInt(i, player.getcarry_weight());
					
					stmtUpdatePlayer.addBatch();
					stmtUpdatePlayer.executeBatch();
					
				return true;
			
			} finally {
				DBUtil.closeQuietly(stmtRemovePlayer);
				DBUtil.closeQuietly(stmtUpdatePlayer);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	private void updateInventory(Character player) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			PreparedStatement stmt1 = null;
			
			List<Item> itemList = player.getinventory().getitems();
			ArrayList<Integer> itemIDList = new ArrayList<>();
			for(Item item : itemList) {
				itemIDList.add(item.getID());
			}
			
			try {
				stmt = conn.prepareStatement(
						"delete from itemstoinventories" + player.getname() +
						" where inventory_id = ? "
				);
				stmt.setInt(1, player.getinventory_id());
				stmt.executeUpdate();
				
				stmt1 = conn.prepareStatement(
						"insert into itemstoinventories" + player.getname() +
						" (item_id, inventory_id) "
						+ "values (?, ?)");
				for(Integer itemID : itemIDList) {
					stmt1.setInt(1, itemID);
					stmt1.setInt(2, player.getinventory_id());
					stmt1.addBatch();
				}
				stmt1.executeBatch();
				
				return true;
			} finally {
				DBUtil.closeQuietly(conn);
				DBUtil.closeQuietly(stmt1);
				DBUtil.closeQuietly(stmt);
			}
		});
	}
	
	/*******************************************************************************************************
	 * 											addToConstruct Methods
	 *******************************************************************************************************/
	
	// Adds the association in the itemstoinventories table, adds the item to
	// the inventory's item list.
	public void addItemToInventory(final Item item, Inventory inventory) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(
						"insert into itemstoinventories (item_id, inventory_id) "
						+ "values (?, ?)");
				stmt.setInt(1, item.getID());
				stmt.setInt(2, inventory.getinventory_id());
				stmt.executeUpdate();
				
				inventory.getitems().add(item);
				return true;
			} finally {
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}	
	
	// Adds an association in the itemstoobjects table and adds the item to the 
	// object's itemList.
	public void addItemToObject(final Item item, GameObject object) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(
						"insert into itemstoobjects (item_id, object_id) "
						+ "values (?, ?)");
				stmt.setInt(1, item.getID());
				stmt.setInt(2, object.getID());
				stmt.executeUpdate();
				object.addItem(item);
				return true;
			} finally {
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	private void addObjectToMapTile(final int objectID, final int mapTileID) {
		executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try {
				stmt = conn.prepareStatement(
						"insert into objectstomaptiles (object_id, maptile_id) "
						+ "values (?, ?)");
						
				stmt.setInt(1, objectID);
				stmt.setInt(2, mapTileID);
				stmt.executeUpdate();
				
				return true;
			} finally {
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	/*******************************************************************************************************
	 * 											removeFromConstruct Methods
	 *******************************************************************************************************/
	
	// Removes the item from the inventory.  Deletes the association in the 
	// itemstoinventories table in Derby.  Returns the same item given to it
	public Item removeItemFromInventory(final Item item, Inventory inventory) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"delete from itemstoinventories "
								+ "where itemstoinventories.item_id = ? "
								+ "AND itemstoinventories.inventory_id = ? ");
				stmt.setInt(1, item.getID());
				stmt.setInt(2, inventory.getinventory_id());
				stmt.executeUpdate();
				
				inventory.getitems().remove(item);
				
				return item;
			}finally{
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	// Removes the item from the object.  Deletes the association in the
	// itemstoobjects table in Derby.  Returns the same item given to it
 	public Item removeItemFromObject(final Item item, GameObject object) {
		return executeTransaction(conn -> {
			PreparedStatement stmt = null;
			
			try{
				stmt = conn.prepareStatement(
						"delete from itemstoobjects "
								+ "where itemstoobjects.item_id = ? "
								+ "AND itemstoobjects.object_id = ? ");
				stmt.setInt(1, item.getID());
				stmt.setInt(2, object.getID());
				stmt.executeUpdate();
				
				object.getItems().remove(item);
				
				return item;
			}finally{
				DBUtil.closeQuietly(stmt);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	private void removeAllObjectsFromMapTile(final int mapTileID) {
		executeTransaction(conn -> {
			PreparedStatement removeObject = null;
			try {
				removeObject = conn.prepareStatement(
						"delete from objectstomaptiles "
						+ "where objectstomaptiles.maptile_id = ? "
				);
				removeObject.setInt(1, mapTileID);
				removeObject.executeUpdate();
				
				return true;
			} finally {
				DBUtil.closeQuietly(removeObject);
				DBUtil.closeQuietly(conn);
			}
		});
	}
	
	@Override
	public ArrayList<Quest> getAllQuests(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
}
