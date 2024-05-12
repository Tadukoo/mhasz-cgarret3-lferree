package com.github.tadukoo.aome.construct;

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
	/** Column name for Difficulty */
	private static final String DIFFICULTY_COLUMN_NAME = "difficulty";
	
	private List<GameObject> objects;
	private final Map<String, Integer> connections;
	private boolean visited;
	private String enemyString;
	
	public MapTile(){
		objects = new ArrayList<>();
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
		return "map_tiles";
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
	
	public Map<String, Integer> getConnections(){
		return connections;
	}
	
	// Note: Having setConnections(HashMap) causes us to lose the default values of 0.
	public void setConnection(String direction, int weight){
		connections.put(direction, weight);
	}
	
	public int getMoveValue(String direction) {
		return connections.get(direction);
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
