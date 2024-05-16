package com.github.tadukoo.dbsetup;

import com.github.tadukoo.aome.User;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemToObjectMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.ObjectCommandResponse;
import com.github.tadukoo.aome.construct.map.MapTileConnections;
import com.github.tadukoo.aome.construct.map.ObjectToMapTileMap;
import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.pojo.DatabasePojo;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class CleanupDatabase{
	private static Database database;
	
	public static void main(String[] args) throws IOException, SQLException{
		// TODO: Put this configuration somewhere
		EasyLogger logger = new EasyLogger(
				LoggerUtil.createFileLogger("mysql-database.log", Level.INFO));
		database = Database.builder()
				.logger(logger)
				.host("localhost").port(3306).databaseName("middle-earth")
				.username("root").password("")
				.build();
		deleteTables();
	}
	
	private static void deleteTables() throws SQLException{
		deleteTable(new Item());
		deleteTable(new User());
		deleteTable(new GameObject());
		deleteTable(new ItemToObjectMap());
		deleteTable(new ObjectCommandResponse());
		deleteTable(new MapTile());
		deleteTable(new ObjectToMapTileMap());
		deleteTable(new MapTileConnections());
	}
	
	private static void deleteTable(DatabasePojo pojo) throws SQLException{
		database.executeUpdate("Delete " + pojo.getTableName() + " table",
				SQLDropStatement.builder()
						.table()
						.ifExists()
						.name(pojo.getTableName())
						.build()
						.toString());
	}
}
