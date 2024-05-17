package com.github.tadukoo.aome.construct.map;

import com.github.tadukoo.aome.ManyToManyDatabasePojo;

import java.util.Map;

public class MapTileToMapMap extends ManyToManyDatabasePojo<MapTile, GameMap>{
	/** The Column Name for the Map Tile ID Column */
	private static final String MAP_TILE_ID_COLUMN_NAME = "map_tile_id";
	/** The Column Name for the Map ID Column */
	private static final String MAP_ID_COLUMN_NAME = "map_id";
	
	/**
	 * Creates a new blank Map Tile to Map mapping pojo
	 */
	public MapTileToMapMap(){
	
	}
	
	/**
	 * Creates a new Map Tile to Map mapping with the given IDs
	 *
	 * @param mapTileID The ID of the Object for this mapping
	 * @param mapID The ID of the Map Tile for this mapping
	 */
	public MapTileToMapMap(int mapTileID, int mapID){
		setMapTileID(mapTileID);
		setMapID(mapID);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "MapTilesToMaps";
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<MapTile> getType1Class(){
		return MapTile.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignTableName(){
		return new MapTile().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1IDColumnName(){
		return MAP_TILE_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignIDColumnName(){
		return new MapTile().getIDColumnName();
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<GameMap> getType2Class(){
		return GameMap.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignTableName(){
		return new GameMap().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2IDColumnName(){
		return MAP_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignIDColumnName(){
		return new GameMap().getIDColumnName();
	}
	
	/**
	 * @return The Map Tile ID for this Map Tile to Map mapping
	 */
	public int getMapTileID(){
		return (int) getItem(MAP_TILE_ID_COLUMN_NAME);
	}
	
	/**
	 * @param mapTileID The Map Tile ID to set for this Map Tile to Map mapping
	 */
	public void setMapTileID(int mapTileID){
		setItem(MAP_TILE_ID_COLUMN_NAME, mapTileID);
	}
	
	/**
	 * @return The Map ID for this Map Tile to Map mapping
	 */
	public int getMapID(){
		return (int) getItem(MAP_ID_COLUMN_NAME);
	}
	
	/**
	 * @param mapID The Map ID for this Map Tile to Map mapping
	 */
	public void setMapID(int mapID){
		setItem(MAP_ID_COLUMN_NAME, mapID);
	}
}
