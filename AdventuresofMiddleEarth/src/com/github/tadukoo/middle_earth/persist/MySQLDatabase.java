package com.github.tadukoo.middle_earth.persist;

import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLConjunctiveOperator;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
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
import java.util.Random;
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
	/** Randomness */
	private final Random random;
	
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
		random = new Random(System.currentTimeMillis());
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
	
	/*
	 * Item Related Queries
	 */
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<List<Item>> getAllItems(){
		List<Item> results = null;
		String error = null;
		try{
			Item item = new Item();
			results = item.doSearch(database, Item.class, false);
		}catch(SQLException e){
			error = "Get All Items SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(results, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getItemByID(int id){
		Item item = null;
		String error = null;
		try{
			Item search = new Item();
			search.setID(id);
			List<Item> results = search.doSearch(database, Item.class, false);
			if(ListUtil.isBlank(results)){
				error = "No items match that ID number";
			}else{
				item = results.get(0);
			}
		}catch(SQLException e){
			error = "Get Item By ID SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(item, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getLegendaryItem(ItemType type){
		Item item = null;
		String error = null;
		try{
			Item search = new Item();
			search.setShortDescription("LEGENDARY");
			search.setType(type);
			List<Item> results = search.doSearch(database, Item.class, false);
			if(ListUtil.isBlank(results)){
				error = "No legendary items of that type";
			}else if(results.size() == 1){
				item = results.get(0);
			}else{
				item = results.get(random.nextInt(results.size()));
			}
		}catch(SQLException e){
			error = "Get Legendary Item SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(item, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getHandheldItem(){
		Item item = null;
		String error = null;
		try{
			Item search = new Item();
			List<ColumnRef> columnRefs = SQLSyntaxUtil.makeColumnRefs(search.getColumnDefKeys());
			TableRef tableRef = TableRef.builder().tableName(search.getTableName()).build();
			ColumnRef typeCol = SQLSyntaxUtil.makeColumnRef(Item.TYPE_COLUMN_NAME);
			ColumnRef shortDescCol = SQLSyntaxUtil.makeColumnRef(Item.SHORT_DESCRIPTION_COLUMN_NAME);
			Conditional condition = Conditional.builder()
					.firstCond(Conditional.builder()
							.firstCondStmt(ConditionalStatement.builder()
									.column(typeCol)
									.operator(SQLOperator.EQUAL)
									.value(ItemType.L_HAND)
									.build())
							.operator(SQLConjunctiveOperator.OR)
							.secondCondStmt(ConditionalStatement.builder()
									.column(typeCol)
									.operator(SQLOperator.EQUAL)
									.value(ItemType.R_HAND)
									.build())
							.build())
					.operator(SQLConjunctiveOperator.AND)
					.secondCondStmt(ConditionalStatement.builder()
							.column(shortDescCol)
							.operator(SQLOperator.NOT_EQUAL)
							.value("LEGENDARY")
							.build())
					.build();
			List<Item> results = database.executeQuery("Get Handheld Item",
					SQLSelectStatement.builder()
							.returnColumns(columnRefs)
							.fromTables(tableRef)
							.whereStatement(condition)
							.build().toString(),
					search.getResultSetListFunc(Item.class));
			if(ListUtil.isBlank(results)){
				error = "No handheld items found";
			}else if(results.size() == 1){
				item = results.get(0);
			}else{
				item = results.get(random.nextInt(results.size()));
			}
		}catch(SQLException e){
			error = "Get Handheld Item SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(item, error);
	}
	
	/** {@inheritDoc} */
	@Override
	public DatabaseResult<Item> getArmorItem(){
		Item item = null;
		String error = null;
		try{
			Item search = new Item();
			List<ColumnRef> columnRefs = SQLSyntaxUtil.makeColumnRefs(search.getColumnDefKeys());
			TableRef tableRef = TableRef.builder().tableName(search.getTableName()).build();
			ColumnRef typeCol = SQLSyntaxUtil.makeColumnRef(Item.TYPE_COLUMN_NAME);
			ColumnRef shortDescCol = SQLSyntaxUtil.makeColumnRef(Item.SHORT_DESCRIPTION_COLUMN_NAME);
			Conditional condition = Conditional.builder()
					.firstCond(Conditional.builder()
							.firstCond(Conditional.builder()
									.firstCondStmt(ConditionalStatement.builder()
											.column(typeCol)
											.operator(SQLOperator.EQUAL)
											.value(ItemType.HELM)
											.build())
									.operator(SQLConjunctiveOperator.OR)
									.secondCondStmt(ConditionalStatement.builder()
											.column(typeCol)
											.operator(SQLOperator.EQUAL)
											.value(ItemType.BRACES)
											.build())
									.build())
							.operator(SQLConjunctiveOperator.OR)
							.secondCond(Conditional.builder()
									.firstCondStmt(ConditionalStatement.builder()
											.column(typeCol)
											.operator(SQLOperator.EQUAL)
											.value(ItemType.LEGS)
											.build())
									.operator(SQLConjunctiveOperator.OR)
									.secondCondStmt(ConditionalStatement.builder()
											.column(typeCol)
											.operator(SQLOperator.EQUAL)
											.value(ItemType.BOOTS)
											.build())
									.build())
							.build())
					.operator(SQLConjunctiveOperator.AND)
					.secondCondStmt(ConditionalStatement.builder()
							.column(shortDescCol)
							.operator(SQLOperator.NOT_EQUAL)
							.value("LEGENDARY")
							.build())
					.build();
			List<Item> results = database.executeQuery("Get Armor Item",
					SQLSelectStatement.builder()
							.returnColumns(columnRefs)
							.fromTables(tableRef)
							.whereStatement(condition)
							.build().toString(),
					search.getResultSetListFunc(Item.class));
			if(ListUtil.isBlank(results)){
				error = "No armor items found";
			}else if(results.size() == 1){
				item = results.get(0);
			}else{
				item = results.get(random.nextInt(results.size()));
			}
		}catch(SQLException e){
			error = "Get Armor Item SQL Error";
			logger.logError(error, e);
		}
		return new DatabaseResult<>(item, error);
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
}
