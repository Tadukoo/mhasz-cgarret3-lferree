package com.github.tadukoo.aome.construct.map;

import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.pojo.DatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;

/**
 * A {@link DatabasePojo} used for connections between {@link MapTile MapTiles}
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class MapTileConnections extends AbstractDatabasePojo{
	
	private static final String MAP_TILE_ID_COLUMN_NAME = "map_tile_id";
	private static final String NORTH_TILE_ID_COLUMN_NAME = "north_tile_id";
	private static final String NORTHEAST_TILE_ID_COLUMN_NAME = "northeast_tile_id";
	private static final String EAST_TILE_ID_COLUMN_NAME = "east_tile_id";
	private static final String SOUTHEAST_TILE_ID_COLUMN_NAME = "southeast_tile_id";
	private static final String SOUTH_TILE_ID_COLUMN_NAME = "south_tile_id";
	private static final String SOUTHWEST_TILE_ID_COLUMN_NAME = "southwest_tile_id";
	private static final String WEST_TILE_ID_COLUMN_NAME = "west_tile_id";
	private static final String NORTHWEST_TILE_ID_COLUMN_NAME = "northwest_tile_id";
	
	public MapTileConnections(){
	
	}
	
	public MapTileConnections(
			int mapTileID, Integer northTileID, Integer northeastTileID, Integer eastTileID,
			Integer southeastTileID, Integer southTileID, Integer southwestTileID,
			Integer westTileID, Integer northwestTileID){
		setMapTileID(mapTileID);
		setNorthTileID(northTileID);
		setNortheastTileID(northeastTileID);
		setEastTileID(eastTileID);
		setSoutheastTileID(southeastTileID);
		setSouthTileID(southTileID);
		setSouthwestTileID(southwestTileID);
		setWestTileID(westTileID);
		setNorthwestTileID(northwestTileID);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "MapTileConnections";
	}
	
	/** {@inheritDoc} */
	@Override
	public String getIDColumnName(){
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		String mapTileTableName = MapTile.TABLE_NAME;
		String mapTileIDColumnName = MapTile.ID_COLUMN_NAME;
		
		// Map Tile ID Column
		ColumnDefinition mapTileIDCol = ColumnDefinition.builder()
				.columnName(MAP_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.unique()
				.build();
		addColumnDef(mapTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(mapTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// North Tile ID Column
		ColumnDefinition northTileIDCol = ColumnDefinition.builder()
				.columnName(NORTH_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(northTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(northTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// Northeast Tile ID Column
		ColumnDefinition northeastTileIDCol = ColumnDefinition.builder()
				.columnName(NORTHEAST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(northeastTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(northeastTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// East Tile ID Column
		ColumnDefinition eastTileIDCol = ColumnDefinition.builder()
				.columnName(EAST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(eastTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(eastTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// Southeast Tile ID Column
		ColumnDefinition southeastTileIDCol = ColumnDefinition.builder()
				.columnName(SOUTHEAST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(southeastTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(southeastTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// South Tile ID Column
		ColumnDefinition southTileIDCol = ColumnDefinition.builder()
				.columnName(SOUTH_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(southTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(southTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// Southwest Tile ID Column
		ColumnDefinition southwestTileIDCol = ColumnDefinition.builder()
				.columnName(SOUTHWEST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(southwestTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(southwestTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// West Tile ID Column
		ColumnDefinition westTileIDCol = ColumnDefinition.builder()
				.columnName(WEST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(westTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(westTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
		
		// Northwest Tile ID Column
		ColumnDefinition northwestTileIDCol = ColumnDefinition.builder()
				.columnName(NORTHWEST_TILE_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(northwestTileIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(northwestTileIDCol)
				.references(mapTileTableName)
				.referenceColumnNames(mapTileIDColumnName)
				.build());
	}
	
	public int getMapTileID(){
		return (Integer) getItem(MAP_TILE_ID_COLUMN_NAME);
	}
	
	public void setMapTileID(int mapTileID){
		setItem(MAP_TILE_ID_COLUMN_NAME, mapTileID);
	}
	
	public Integer getNorthTileID(){
		return (Integer) getItem(NORTH_TILE_ID_COLUMN_NAME);
	}
	
	public void setNorthTileID(Integer northTileID){
		setItem(NORTH_TILE_ID_COLUMN_NAME, northTileID);
	}
	
	public Integer getNortheastTileID(){
		return (Integer) getItem(NORTHEAST_TILE_ID_COLUMN_NAME);
	}
	
	public void setNortheastTileID(Integer northeastTileID){
		setItem(NORTHEAST_TILE_ID_COLUMN_NAME, northeastTileID);
	}
	
	public Integer getEastTileID(){
		return (Integer) getItem(EAST_TILE_ID_COLUMN_NAME);
	}
	
	public void setEastTileID(Integer eastTileID){
		setItem(EAST_TILE_ID_COLUMN_NAME, eastTileID);
	}
	
	public Integer getSoutheastTileID(){
		return (Integer) getItem(SOUTHEAST_TILE_ID_COLUMN_NAME);
	}
	
	public void setSoutheastTileID(Integer southeastTileID){
		setItem(SOUTHEAST_TILE_ID_COLUMN_NAME, southeastTileID);
	}
	
	public Integer getSouthTileID(){
		return (Integer) getItem(SOUTH_TILE_ID_COLUMN_NAME);
	}
	
	public void setSouthTileID(Integer southTileID){
		setItem(SOUTH_TILE_ID_COLUMN_NAME, southTileID);
	}
	
	public Integer getSouthwestTileID(){
		return (Integer) getItem(SOUTHWEST_TILE_ID_COLUMN_NAME);
	}
	
	public void setSouthwestTileID(Integer southwestTileID){
		setItem(SOUTHWEST_TILE_ID_COLUMN_NAME, southwestTileID);
	}
	
	public Integer getWestTileID(){
		return (Integer) getItem(WEST_TILE_ID_COLUMN_NAME);
	}
	
	public void setWestTileID(Integer westTileID){
		setItem(WEST_TILE_ID_COLUMN_NAME, westTileID);
	}
	
	public Integer getNorthwestTileID(){
		return (Integer) getItem(NORTHWEST_TILE_ID_COLUMN_NAME);
	}
	
	public void setNorthwestTileID(Integer northwestTileID){
		setItem(NORTHWEST_TILE_ID_COLUMN_NAME, northwestTileID);
	}
}
