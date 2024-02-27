package com.github.tadukoo.aome;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.middle_earth.persist.DatabaseSettings;
import com.github.tadukoo.middle_earth.persist.InitialData;
import com.github.tadukoo.middle_earth.persist.MySQLDatabase;
import com.github.tadukoo.middle_earth.persist.pojo.UserPojo;
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
		DatabaseSettings settings = new DatabaseSettings(
				"localhost", 3306, "middle-earth",
				"root", "");
		EasyLogger logger = new EasyLogger(
				LoggerUtil.createFileLogger("mysql-database.log", Level.INFO));
		database = Database.builder()
				.logger(logger)
				.host(settings.host()).port(settings.port()).databaseName(settings.databaseName())
				.username(settings.username()).password(settings.password())
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
		UserPojo userPojo = new UserPojo();
		userPojo.createTable(database);
	}
	
	/**
	 * Loads Initial Data for the database based on {@link InitialData}
	 *
	 * @throws IOException If anything goes wrong around file reading
	 * @throws SQLException If anything goes wrong in SQL running
	 */
	private static void loadInitialData() throws IOException, SQLException{
		// Load Users
		List<UserPojo> userPojos = InitialData.getUserPojos();
		for(UserPojo userPojo: userPojos){
			userPojo.storeValues(database, false);
		}
	}
}
