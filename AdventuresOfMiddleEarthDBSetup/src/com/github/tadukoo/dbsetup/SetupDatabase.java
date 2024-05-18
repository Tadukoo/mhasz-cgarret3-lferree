package com.github.tadukoo.dbsetup;

import com.github.tadukoo.aome.InitialData;
import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.ItemToPlayerMap;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.aome.construct.map.MapTileConnections;
import com.github.tadukoo.aome.construct.map.MapTileToMapMap;
import com.github.tadukoo.aome.construct.map.ObjectToMapTileMap;
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
		
		// Create Map Tiles table
		MapTile mapTile = new MapTile();
		mapTile.createTable(database);
		
		// Create Objects to Map Tiles table
		ObjectToMapTileMap objectToMapTile = new ObjectToMapTileMap();
		objectToMapTile.createTable(database);
		
		// Create Map Tile Connections table
		MapTileConnections mapTileConnections = new MapTileConnections();
		mapTileConnections.createTable(database);
		
		// Create Maps table
		GameMap map = new GameMap();
		map.createTable(database);
		
		// Create Map Tiles to Maps table
		MapTileToMapMap mapTileToMapMap = new MapTileToMapMap();
		mapTileToMapMap.createTable(database);
		
		// Create Players table
		Player player = new Player();
		player.createTable(database);
		
		// Create Items to Players table
		ItemToPlayerMap itemToPlayerMap = new ItemToPlayerMap();
		itemToPlayerMap.createTable(database);
		
		// Create Enemies table
		Enemy enemy = new Enemy();
		enemy.createTable(database);
	}
	
	/*
	 * Other Tables Creation to be moved
	 *
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
		
		// Load Map Tiles
		List<MapTile> mapTiles = InitialData.getMapTiles();
		for(MapTile mapTile: mapTiles){
			mapTile.storeValues(database, false);
		}
		
		// Load Objects to Map Tiles
		List<ObjectToMapTileMap> objectsToMapTiles = InitialData.getObjectsToMapTiles();
		for(ObjectToMapTileMap objectToMapTile: objectsToMapTiles){
			objectToMapTile.storeValues(database, false);
		}
		
		// Load Map Tile Connections
		List<MapTileConnections> mapTileConnections = InitialData.getMapTileConnections();
		for(MapTileConnections mapTileConnection: mapTileConnections){
			mapTileConnection.storeValues(database, false);
		}
		
		// Load Maps
		List<GameMap> maps = InitialData.getMaps();
		for(GameMap map: maps){
			map.storeValues(database, false);
		}
		
		// Load Map Tile to Maps
		List<MapTileToMapMap> mapTilesToMaps = InitialData.getMapTilesToMaps();
		for(MapTileToMapMap mapTileToMap: mapTilesToMaps){
			mapTileToMap.storeValues(database, false);
		}
		
		// Load Players
		List<Player> players = InitialData.getPlayers();
		for(Player player: players){
			player.storeValues(database, false);
		}
		
		// Load Items to Players
		List<ItemToPlayerMap> itemsToPlayers = InitialData.getItemsToPlayers();
		for(ItemToPlayerMap itemToPlayer: itemsToPlayers){
			itemToPlayer.storeValues(database, false);
		}
		
		// Load Enemies
		List<Enemy> enemies = InitialData.getEnemies();
		for(Enemy enemy: enemies){
			enemy.storeValues(database, false);
		}
	}
}
