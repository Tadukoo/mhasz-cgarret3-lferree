package com.github.tadukoo.dbsetup;

import com.github.tadukoo.aome.InitialData;
import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class SetupDatabase{
	private static Database database;
	
	/**
	 * Creates the tables and loads the initial data for the database
	 *
	 * @param args Currently unused TODO: Use for settings?
	 * @throws IOException If anything goes wrong related to file reading
	 * @throws SQLException If anything goes wrong related to SQL running
	 */
	public static void main(String[] args) throws IOException, SQLException{
		// TODO: Put this configuration somewhere
		EasyLogger logger = new EasyLogger(
				LoggerUtil.createFileLogger("mysql-database.log", Level.INFO));
		database = Database.builder()
				.logger(logger)
				.host("localhost").port(3306).databaseName("middle-earth")
				.username("root").password("")
				.build();
		createTables();
		loadInitialData();
	}
	
	/**
	 * Creates the tables needed for the database
	 *
	 * @throws SQLException If anything goes wrong
	 */
	private static void createTables() throws SQLException{
		// Create Users table
		User user = new User();
		user.createTable(database);
		
		// Create Items table
		Item item = new Item();
		item.createTable(database);
		
		// Create GameObject table
		GameObject object = new GameObject();
		object.createTable(database);
		
		// Create Items to Objects table
		ItemToObjectMap itemToObject = new ItemToObjectMap();
		itemToObject.createTable(database);
		
		// Create Object Command Response table
		ObjectCommandResponse objectCommandResponse = new ObjectCommandResponse();
		objectCommandResponse.createTable(database);
	}
	
	/*
	 * Other Tables Creation to be moved
	 *
				stmt4 = conn.prepareStatement(
						"create table maptileconnections (" +
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
				stmt4.executeUpdate();
				
				stmt5 = conn.prepareStatement(
						"create table maptiles (" +
						"   maptile_id integer primary key " +
						"       generated always as identity (start with 1, increment by 1), " +
						"   maptilename varchar(40)," +
						"	longdescription varchar(200)," +
						"	shortdescription varchar(100)," +
						"	difficulty int" +
						")"
				);
				stmt5.executeUpdate();
				
				stmt6 = conn.prepareStatement(
						"create table objectstomaptiles (" +
						"	object_id int, " +
						"   maptile_id int" +
						")"
				);
				stmt6.executeUpdate();
				
				stmt7 = conn.prepareStatement(
						"create table itemstoinventories ("
						+ "item_id int, "
						+ "inventory_id int"
						+ ")"
				);
				stmt7.executeUpdate();
				
				stmt8 = conn.prepareStatement(
						"create table players ("
						+ "race varchar(40),"
						+ "name varchar(40),"
						+ "gender varchar(40),"
						+ "level int,"
						+ "hit_points int,"
						
						+ "magic_points int,"
						+ "attack int,"
						+ "defense int,"
						+ "sp_attack int,"
						+ "sp_defense int,"
						
						+ "coins int,"
						+ "map_location int,"
						+ "inventory_id int,"
						+ "helm_item_id int,"
						+ "braces_item_id int,"
						
						+ "chest_item_id int,"
						+ "legs_item_id int,"
						+ "boots_item_id int,"
						+ "l_hand_item_id int,"
						+ "r_hand_item_id int,"
						
						+ "experience int,"
						+ "carry_weight int"
						+ ")"
				);
				stmt8.executeUpdate();
				
				stmt10 = conn.prepareStatement(
						"create table maps (" +
						"   map_id integer primary key " +
						"       generated always as identity (start with 1, increment by 1), " +
						"   mapname varchar(40)," +
						"	longdescription varchar(200)," +
						"	shortdescription varchar(100)" +
						")"
				);
				stmt10.executeUpdate();
				
				stmt11 = conn.prepareStatement(
						"create table maptilestomaps ("
						+ "maptile_id int, "
						+ "map_id int "
						+ ")"
				);
				stmt11.executeUpdate();
				
				stmt12 = conn.prepareStatement(
						"create table enemies ("
						+ "race varchar(40), "
						+ "hp int, "
						+ "mp int, "
						+ "attack int, "
						+ "defense int, "
						+ "sp_atk int, "
						+ "sp_def int "
						+ ")"
				);
				stmt12.executeUpdate();
				
				stmt13 = conn.prepareStatement(
						"create table names ("
						+ "number int primary key "
						+ "		generated always as identity (start with 1, increment by 1), "
						+ "name varchar(40), "
						+ "gender varchar(10)"
						+ ")"
				);
				stmt13.executeUpdate();
				
				stmt14 = conn.prepareStatement(
						"create table gamestousers ("
						+ "game_id int primary key "
						+ "		generated always as identity (start with 1, increment by 1), "
						+ "username varchar(40)"
						+ ")"
				);
				stmt14.executeUpdate();
				
				stmt15 = conn.prepareStatement(
						"create table mapstogames ("
						+ "map_id int, "
						+ "game_id int"
						+ ")"
				);
				stmt15.executeUpdate();
				
				stmt16 = conn.prepareStatement(
						"create table playerstogames ("
						+ "playername varchar(100), "
						+ "game_id int"
						+ ")"
				);
	 */
	
	/**
	 * Loads Initial Data for the database based on {@link InitialData}
	 *
	 * @throws IOException If anything goes wrong around file reading
	 * @throws SQLException If anything goes wrong in SQL running
	 */
	private static void loadInitialData() throws IOException, SQLException{
		// Load Users
		List<User> users = InitialData.getUsers();
		for(User user: users){
			user.storeValues(database, false);
		}
		
		// Load Items
		List<Item> items = InitialData.getItems();
		for(Item item: items){
			item.storeValues(database, false);
		}
		
		// Load Objects
		List<GameObject> objects = InitialData.getObjects();
		for(GameObject object: objects){
			object.storeValues(database, false);
		}
		
		// Load Items to Objects
		List<ItemToObjectMap> itemsToObjects = InitialData.getItemsToObjects();
		for(ItemToObjectMap itemToObject: itemsToObjects){
			itemToObject.storeValues(database, false);
		}
		
		// Load Object Command Responses
		List<ObjectCommandResponse> objectCommandResponses = InitialData.getObjectCommandResponses();
		for(ObjectCommandResponse objectCommandResponse: objectCommandResponses){
			objectCommandResponse.storeValues(database, false);
		}
	}
}
