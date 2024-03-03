package com.github.tadukoo.aome.construct;

import java.util.ArrayList;

public class GameMap extends Construct{
	private ArrayList<MapTile> mapTiles;
	
	public GameMap(){
		mapTiles = new ArrayList<>();
	}
	
	@Override
	public String getTableName(){
		return "Maps";
	}
	
	public ArrayList<MapTile> getMapTiles(){
		return mapTiles;
	}
	
	public MapTile getMapTileByID(int id){
		for(MapTile m: mapTiles){
			if(m.getID() == id){
				return m;
			}
		}
		return null;
	}
	
	public void addMapTile(MapTile tile){
		mapTiles.add(tile);
	}
	
	public void setMapTiles(ArrayList<MapTile> mapTiles){
		this.mapTiles = mapTiles;
	}
}
