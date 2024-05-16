package com.github.tadukoo.aome.construct.map;

import com.github.tadukoo.aome.construct.Construct;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Map Tile in the Game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class MapTile extends Construct{
	public static final String TABLE_NAME = "MapTiles";
	/** Column name for Difficulty */
	private static final String DIFFICULTY_COLUMN_NAME = "difficulty";
	
	private List<GameObject> objects;
	private MapTileConnections mapTileConnections;
	private final Map<String, Integer> connections;
	private boolean visited;
	private String enemyString;
	
	public MapTile(){
		objects = new ArrayList<>();
		mapTileConnections = new MapTileConnections();
		connections = new HashMap<>();
		connections.put("north", 0);
		connections.put("northeast", 0);
		connections.put("east", 0);
		connections.put("southeast", 0);
		connections.put("south", 0);
		connections.put("southwest", 0);
		connections.put("west", 0);
		connections.put("northwest", 0);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		super.setDefaultColumnDefs();
		
		// Add difficulty
		addColumnDef(ColumnDefinition.builder()
				.columnName(DIFFICULTY_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
	}
	
	public int getDifficulty(){
		return (int) getItem(DIFFICULTY_COLUMN_NAME);
	}
	
	public void setDifficulty(int difficulty){
		setItem(DIFFICULTY_COLUMN_NAME, difficulty);
	}
	
	public List<GameObject> getObjects(){
		return objects;
	}
	
	public void setObjects(List<GameObject> objects){
		this.objects = objects;
	}
	
	public void addObject(GameObject object){
		objects.add(object);
	}
	
	public void removeObject(GameObject object){
		GameObject remove = new GameObject();
		for(GameObject check: objects){
			if(check == object){
				remove = object;
				break;
			}
		}
		objects.remove(remove);
	}
	
	public MapTileConnections getConnections(){
		return mapTileConnections;
	}
	
	public Integer getNorthConnection(){
		return mapTileConnections.getNorthTileID();
	}
	
	public Integer getNortheastConnection(){
		return mapTileConnections.getNortheastTileID();
	}
	
	public Integer getEastConnection(){
		return mapTileConnections.getEastTileID();
	}
	
	public Integer getSoutheastConnection(){
		return mapTileConnections.getSoutheastTileID();
	}
	
	public Integer getSouthConnection(){
		return mapTileConnections.getSouthTileID();
	}
	
	public Integer getSouthwestConnection(){
		return mapTileConnections.getSouthwestTileID();
	}
	
	public Integer getWestConnection(){
		return mapTileConnections.getWestTileID();
	}
	
	public Integer getNorthwestConnection(){
		return mapTileConnections.getNorthwestTileID();
	}
	
	public void setConnections(MapTileConnections mapTileConnections){
		this.mapTileConnections = mapTileConnections;
	}
	
	public void setNorthConnection(int northID){
		mapTileConnections.setNorthTileID(northID);
	}
	
	public void setNortheastConnection(int northeastID){
		mapTileConnections.setNortheastTileID(northeastID);
	}
	
	public void setEastConnection(int eastID){
		mapTileConnections.setEastTileID(eastID);
	}
	
	public void setSoutheastConnection(int southeastID){
		mapTileConnections.setSoutheastTileID(southeastID);
	}
	
	public void setSouthConnection(int southID){
		mapTileConnections.setSouthTileID(southID);
	}
	
	public void setSouthwestConnection(int southwestID){
		mapTileConnections.setSouthwestTileID(southwestID);
	}
	
	public void setWestConnection(int westID){
		mapTileConnections.setWestTileID(westID);
	}
	
	public void setNorthwestConnection(int northwestID){
		mapTileConnections.setNorthwestTileID(northwestID);
	}
	
	public void setVisited(boolean visited){
		this.visited = visited;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public String getEnemyString(){
		return enemyString;
	}
	
	public void setEnemyString(String enemyString){
		this.enemyString = enemyString;
	}
	
	public ArrayList<Integer> getEnemyIDs(){
		ArrayList<Integer> ids = new ArrayList<>();
		for(String s: enemyString.split(",")){
			ids.add(Integer.parseInt(s));
		}
		return ids;
	}
}
