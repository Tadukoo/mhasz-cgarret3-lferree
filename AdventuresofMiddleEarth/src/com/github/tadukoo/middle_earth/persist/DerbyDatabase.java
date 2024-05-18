package com.github.tadukoo.middle_earth.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.aome.StringPair;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;

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
	
	private void loadEnemy(Enemy enemy, ResultSet resultSet) throws SQLException {
		int index = 1;
		enemy.setRace(resultSet.getString(index++));
		enemy.setHP(resultSet.getInt(index++));
		enemy.setMP(resultSet.getInt(index++));
		
		StringPair randNameGender = getRandomName();
		
		enemy.setName(randNameGender.getString1());
		enemy.setGender(randNameGender.getString2());
		enemy.setAttack(resultSet.getInt(index++));
		enemy.setDefense(resultSet.getInt(index++));
		enemy.setSpecialAttack(resultSet.getInt(index++));
		enemy.setSpecialDefense(resultSet.getInt(index));
	}
	
	private void loadNameGender(StringPair nameGender, ResultSet resultSet) throws SQLException{
		int index = 1;
		nameGender.setString1(resultSet.getString(index++));
		nameGender.setString2(resultSet.getString(index));
	}
	
	/**************************************************************************************************
	 * 										Get Methods
	 **************************************************************************************************/
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								Maps
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public DatabaseResult<GameMap> getMapByID(int id){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//  								MapTiles 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public DatabaseResult<List<MapTile>> getAllMapTiles(){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
	}

	@Override
	public DatabaseResult<MapTile> getMapTileByID(int mapTileID) {
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
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
	public DatabaseResult<Player> getPlayerByID(int id){
		throw new UnsupportedOperationException("DerbyDatabase not supported anymore!");
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
	

	/*******************************************************************************************************
	*											load/save game
	********************************************************************************************************/
	// Will need to take gameID in future
	public Game loadGame(int gameID) {
		
		Game game = new Game();

		game.setmap(getMapByID(-1).result());
		
		game.setobjects(getAllObjects().result());
		game.setitems(getAllItems().result());
		ArrayList<Character> characterList = new ArrayList<>();
		characterList.add(getPlayerByID(1).result());
		//characterList.get(0).setInventory(getInventory(characterList.get(0).getName(), characterList.get(0).getinventory_id()));
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
				createWorldForNewGame(player.getName());
				
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
				stmt3.setString(1, player.getName());
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
			
			List<MapTile> mapTileList = getAllMapTiles().result();
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
					insertMapTile.setInt(4, mapTile.getDifficulty());
					
					// Connections for each mapTile
					int i = 1;
					/*
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("north"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("northeast"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("east"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("southeast"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("south"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("southwest"));
					insertMapTileConnections.setInt(i++, mapTile.getOldConnections().get("west"));
					insertMapTileConnections.setInt(i, mapTile.getOldConnections().get("northwest"));
					insertMapTileConnections.addBatch();
					 */
					
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
				insertPlayer.setString(i++, player.getRace());
				insertPlayer.setString(i++, player.getName());
				insertPlayer.setString(i++, player.getGender());
				insertPlayer.setInt(i++, player.getLevel());
				insertPlayer.setInt(i++, player.getHP());
				
				insertPlayer.setInt(i++, player.getMP());
				insertPlayer.setInt(i++, player.getAttack());
				insertPlayer.setInt(i++, player.getDefense());
				insertPlayer.setInt(i++, player.getSpecialAttack());
				insertPlayer.setInt(i++, player.getSpecialDefense());
				
				insertPlayer.setInt(i++, player.getCoins());
				insertPlayer.setInt(i++, player.getLocationID());
				//insertPlayer.setInt(i++, player.getinventory_id());
				insertPlayer.setInt(i++, player.getHelm().getID());
				insertPlayer.setInt(i++, player.getBraces().getID());
				
				insertPlayer.setInt(i++, player.getChest().getID());
				insertPlayer.setInt(i++, player.getLegs().getID());
				insertPlayer.setInt(i++, player.getBoots().getID());
				insertPlayer.setInt(i++, player.getLeftHand().getID());
				insertPlayer.setInt(i++, player.getRightHand().getID());
				
				insertPlayer.setInt(i++, player.getExperience());
				insertPlayer.setInt(i, player.getCarryWeight());
				
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
		
		player.setName(keyboard.next());
		
		String race = "Human";
		player.setRace(race);
		
		System.out.println("Choose your gender");
		player.setGender(keyboard.next());
		
		player.setLevel(1);
		player.setHP(100);
		
		player.setMP(25);
		player.setAttack(10);
		player.setDefense(10);
		player.setSpecialAttack(15);
		player.setSpecialDefense(10);
		
		player.setCoins(0);
		player.setLocationID(1);

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
		player.setHelm(emptyItemSlot);
		
		// braces
		emptyItemSlot.setType(ItemType.BRACES);
		player.setBraces(emptyItemSlot);
		
		// chest
		emptyItemSlot.setType(ItemType.CHEST);
		player.setChest(emptyItemSlot);
		
		emptyItemSlot.setType(ItemType.LEGS);
		player.setLegs(emptyItemSlot);
		
		// boots
		emptyItemSlot.setType(ItemType.BOOTS);
		player.setBoots(emptyItemSlot);
		
		// l_hand
		emptyItemSlot.setType(ItemType.L_HAND);
		player.setLeftHand(emptyItemSlot);
		
		// r_hand
		emptyItemSlot.setType(ItemType.R_HAND);
		player.setRightHand(emptyItemSlot);
		
		player.setCarryWeight(20);
		player.setExperience(0);
		
		//player.setInventory(new Inventory());
		
		// Add the sum total of weight to the inventory
		//player.getInventory().setweight(carryWeight);
		
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
	
	private void updateMapTiles(final List<MapTile> mapTileList) {
		
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
				stmtRemovePlayer.setString(1, player.getName());
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
					stmtUpdatePlayer.setString(i++, player.getRace());
					stmtUpdatePlayer.setString(i++, player.getName());
					stmtUpdatePlayer.setString(i++, player.getGender());
					stmtUpdatePlayer.setInt(i++, player.getLevel());
					stmtUpdatePlayer.setInt(i++, player.getHP());
					
					stmtUpdatePlayer.setInt(i++, player.getMP());
					stmtUpdatePlayer.setInt(i++, player.getAttack());
					stmtUpdatePlayer.setInt(i++, player.getDefense());
					stmtUpdatePlayer.setInt(i++, player.getSpecialAttack());
					stmtUpdatePlayer.setInt(i++, player.getSpecialDefense());
					
					stmtUpdatePlayer.setInt(i++, player.getCoins());
					stmtUpdatePlayer.setInt(i++, player.getLocationID());
					//stmtUpdatePlayer.setInt(i++, player.getinventory_id());
					stmtUpdatePlayer.setInt(i++, player.getHelm().getID());
					stmtUpdatePlayer.setInt(i++, player.getBraces().getID());
					
					stmtUpdatePlayer.setInt(i++, player.getChest().getID());
					stmtUpdatePlayer.setInt(i++, player.getLegs().getID());
					stmtUpdatePlayer.setInt(i++, player.getBoots().getID());
					stmtUpdatePlayer.setInt(i++, player.getLeftHand().getID());
					stmtUpdatePlayer.setInt(i++, player.getRightHand().getID());
					stmtUpdatePlayer.setInt(i++, player.getExperience());
					stmtUpdatePlayer.setInt(i, player.getCarryWeight());
					
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
			
			List<Item> itemList = player.getInventory();
			ArrayList<Integer> itemIDList = new ArrayList<>();
			for(Item item : itemList) {
				itemIDList.add(item.getID());
			}
			
			try {
				stmt = conn.prepareStatement(
						"delete from itemstoinventories" + player.getName() +
						" where inventory_id = ? "
				);
				//stmt.setInt(1, player.getinventory_id());
				stmt.executeUpdate();
				
				stmt1 = conn.prepareStatement(
						"insert into itemstoinventories" + player.getName() +
						" (item_id, inventory_id) "
						+ "values (?, ?)");
				for(Integer itemID : itemIDList) {
					stmt1.setInt(1, itemID);
					//stmt1.setInt(2, player.getinventory_id());
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
