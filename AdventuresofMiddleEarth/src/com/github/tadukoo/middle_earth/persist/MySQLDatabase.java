package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.aome.User;
import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Inventory;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.Map;
import com.github.tadukoo.aome.construct.MapTile;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Represents a MySQL database implementation of {@link IDatabase}
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class MySQLDatabase implements IDatabase{
	/** The backing {@link Database} for this {@link MySQLDatabase} */
	private final Database database;
	/** The {@link EasyLogger logger} to use for this {@link MySQLDatabase} */
	private final EasyLogger logger;
	
	/**
	 * Creates a new MySQL Database with the given {@link ServletContext}
	 *
	 * @param servletContext The {@link ServletContext} to use to find the properties file for settings
	 * @throws IOException If anything goes wrong around setting up the {@link EasyLogger logger}
	 */
	public MySQLDatabase(ServletContext servletContext) throws IOException{
		Properties prop = new Properties();
		InputStream is = servletContext.getResourceAsStream("WEB-INF/config/database.properties");
		prop.load(is);
		logger = new EasyLogger(
				LoggerUtil.createFileLogger("mysql-database.log", Level.INFO));
		database = Database.builder()
				.logger(logger)
				.host(prop.getProperty("host"))
				.port(Integer.parseInt(prop.getProperty("port")))
				.databaseName(prop.getProperty("database-name"))
				.username(prop.getProperty("username"))
				.password(prop.getProperty("password"))
				.build();
	}
	
	/*
	 * Account related queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<String> getUserPasswordByUsername(String username){
		String password = null, error = null;
		try{
			// Find password for given username by doing search
			User user = new User();
			user.setUsername(username);
			List<User> results = user.doSearch(database, User.class, false);
			// unique constraint on username prevents multiple users
			password = results.get(0).getPassword();
		}catch(SQLException e){
			// Log the error and have it set for the result
			error = "Get User Password By Username SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(password, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> doesUsernameExist(String username){
		boolean usernameFound = false;
		String error = null;
		try{
			// Find username by doing search
			User user = new User();
			user.setUsername(username);
			List<User> results = user.doSearch(database, User.class, false);
			usernameFound = ListUtil.isNotBlank(results);
		}catch(SQLException e){
			// Log the error and have it set for the result
			error = "Does Username Exist SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(usernameFound, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> isEmailInUse(String email){
		boolean emailFound = false;
		String error = null;
		try{
			// Find email by doing search
			User user = new User();
			user.setEmail(email);
			List<User> results = user.doSearch(database, User.class, false);
			emailFound = ListUtil.isNotBlank(results);
		}catch(SQLException e){
			// Log the error and have it set for the result
			error = "Is Email In Use SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(emailFound, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Boolean> createNewUser(String username, String password, String email){
		try{
			User user = new User(username, password, email);
			user.storeValues(database, false);
			return new DatabaseResult<>(true, null);
		}catch(SQLException e){
			// Log the error and have it set for the result
			String error = "Create New User SQL Error";
			logger.logError(error, e);
			return new DatabaseResult<>(false, error);
		}
	}
	
	@Override
	public Map getMap(){
		return null;
	}
	
	@Override
	public Player getPlayer(){
		return null;
	}
	
	@Override
	public List<Item> getAllItems(){
		return null;
	}
	
	@Override
	public List<GameObject> getAllObjects(){
		return null;
	}
	
	@Override
	public List<MapTile> getAllMapTiles(){
		return null;
	}
	
	@Override
	public List<Character> getAllCharacters(){
		return null;
	}
	
	@Override
	public List<Quest> getAllQuests(){
		return null;
	}
	
	@Override
	public Item getItemByID(int itemID){
		return null;
	}
	
	@Override
	public GameObject getObjectByID(int objectID){
		return null;
	}
	
	@Override
	public MapTile getMapTileByID(int mapTileID){
		return null;
	}
	
	@Override
	public Inventory getInventoryByID(int inventoryID){
		return null;
	}
	
	@Override
	public Character getCharacterByName(String characterName){
		return null;
	}
	
	@Override
	public Item removeItemFromInventory(Item item, Inventory inventory){
		return null;
	}
	
	@Override
	public Item removeItemFromObject(Item item, GameObject object){
		return null;
	}
	
	@Override
	public void addItemToInventory(Item item, Inventory inventory){
	
	}
	
	@Override
	public void addItemToObject(Item item, GameObject object){
	
	}
	
	@Override
	public Game loadGame(int gameID){
		return null;
	}
	
	@Override
	public void saveGame(Game game){
	
	}
	
	@Override
	public Integer createNewGame(String username){
		return null;
	}
	
	@Override
	public ArrayList<Integer> getGameIDs(String username){
		return null;
	}
	
	@Override
	public Enemy getEnemyByRace(String race){
		return null;
	}
	
	@Override
	public ArrayList<Enemy> getAllEnemies(){
		return null;
	}
	
	@Override
	public ArrayList<String> getAllEnemyRaces(){
		return null;
	}
	
	@Override
	public Item getLegendaryItem(){
		return null;
	}
	
	@Override
	public Item getLegendaryItem(String itemType){
		return null;
	}
	
	@Override
	public Item getHandHeldItem(){
		return null;
	}
	
	@Override
	public Item getHandHeldItem(String whichHand){
		return null;
	}
	
	@Override
	public Item getArmorItem(){
		return null;
	}
	
	@Override
	public Item getArmorItem(String armorType){
		return null;
	}
}
