package com.github.tadukoo.aome.construct;

import com.github.tadukoo.aome.ManyToManyDatabasePojo;

/**
 * A ManyToMany mapping of Objects to Map Tiles
 *
 * @author Logan Ferree (Tadukoo)
 * @version 2.0
 */
public class ObjectToMapTileMap extends ManyToManyDatabasePojo<GameObject, MapTile>{
	
	/** The Column Name for the Object ID Column */
	private static final String OBJECT_ID_COLUMN_NAME = "object_id";
	/** The Column Name for the Map Tile ID Column */
	private static final String MAP_TILE_ID_COLUMN_NAME = "map_tile_id";
	
	/**
	 * Creates a new blank Object to Map Tile mapping pojo
	 */
	public ObjectToMapTileMap(){
	
	}
	
	/**
	 * Creates a new Object to Map Tile mapping with the given IDs
	 *
	 * @param objectID The ID of the Object for this mapping
	 * @param mapTileID The ID of the Map Tile for this mapping
	 */
	public ObjectToMapTileMap(int objectID, int mapTileID){
		setObjectID(objectID);
		setMapTileID(mapTileID);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "ObjectsToMapTiles";
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<GameObject> getType1Class(){
		return GameObject.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignTableName(){
		return new GameObject().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1IDColumnName(){
		return OBJECT_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType1ForeignIDColumnName(){
		return GameObject.ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public Class<MapTile> getType2Class(){
		return MapTile.class;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignTableName(){
		return new MapTile().getTableName();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2IDColumnName(){
		return MAP_TILE_ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getType2ForeignIDColumnName(){
		return MapTile.ID_COLUMN_NAME;
	}
	
	/**
	 * @return The Object ID for this Object to Map Tile mapping
	 */
	public int getObjectID(){
		return (int) getItem(OBJECT_ID_COLUMN_NAME);
	}
	
	/**
	 * @param objectID The Object ID to set for this Object to Map Tile mapping
	 */
	public void setObjectID(int objectID){
		setItem(OBJECT_ID_COLUMN_NAME, objectID);
	}
	
	/**
	 * @return The Map Tile ID for this Object to Map Tile mapping
	 */
	public int getMapTileID(){
		return (int) getItem(MAP_TILE_ID_COLUMN_NAME);
	}
	
	/**
	 * @param mapTileID The Map Tile ID for this Object to Map Tile mapping
	 */
	public void setMapTileID(int mapTileID){
		setItem(MAP_TILE_ID_COLUMN_NAME, mapTileID);
	}
}
