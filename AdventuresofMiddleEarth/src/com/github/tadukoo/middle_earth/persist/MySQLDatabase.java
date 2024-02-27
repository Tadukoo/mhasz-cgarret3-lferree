package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.middle_earth.model.Characters.Character;
import com.github.tadukoo.middle_earth.model.Characters.Enemy;
import com.github.tadukoo.middle_earth.model.Characters.Inventory;
import com.github.tadukoo.middle_earth.model.Characters.Player;
import com.github.tadukoo.middle_earth.model.Constructs.Item;
import com.github.tadukoo.middle_earth.model.Constructs.Map;
import com.github.tadukoo.middle_earth.model.Constructs.MapTile;
import com.github.tadukoo.middle_earth.model.Constructs.Object;
import com.github.tadukoo.middle_earth.model.Quest;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.middle_earth.persist.pojo.UserPojo;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MySQLDatabase implements IDatabase{
	/** The backing {@link Database} for this {@link MySQLDatabase} */
	private final Database database;
	/** The {@link EasyLogger logger} to use for this {@link MySQLDatabase} */
	private final EasyLogger logger;
	
	public MySQLDatabase(DatabaseSettings settings) throws IOException{
		logger = new EasyLogger(
				LoggerUtil.createFileLogger("mysql-database.log", Level.INFO));
		database = Database.builder()
				.logger(logger)
				.host(settings.host()).port(settings.port()).databaseName(settings.databaseName())
				.username(settings.username()).password(settings.password())
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
			UserPojo userPojo = new UserPojo();
			userPojo.setUsername(username);
			List<UserPojo> results = userPojo.doSearch(database, UserPojo.class, false);
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
			UserPojo userPojo = new UserPojo();
			userPojo.setUsername(username);
			List<UserPojo> results = userPojo.doSearch(database, UserPojo.class, false);
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
			UserPojo userPojo = new UserPojo();
			userPojo.setEmail(email);
			List<UserPojo> results = userPojo.doSearch(database, UserPojo.class, false);
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
			UserPojo userPojo = new UserPojo(username, password, email);
			userPojo.storeValues(database, false);
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
	public List<Object> getAllObjects(){
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
	public Object getObjectByID(int objectID){
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
	public Item removeItemFromObject(Item item, Object object){
		return null;
	}
	
	@Override
	public void addItemToInventory(Item item, Inventory inventory){
	
	}
	
	@Override
	public void addItemToObject(Item item, Object object){
	
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
