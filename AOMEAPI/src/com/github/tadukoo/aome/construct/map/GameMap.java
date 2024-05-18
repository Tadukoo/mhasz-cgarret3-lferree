package com.github.tadukoo.aome.construct.map;

import com.github.tadukoo.aome.construct.Construct;

import java.util.ArrayList;
import java.util.List;

public class GameMap extends Construct{
	private List<MapTile> mapTiles;
	
	public GameMap(){
		mapTiles = new ArrayList<>();
	}
	
	public GameMap(Integer mapID, String name, String shortDescription, String longDescription){
		super(mapID, name, shortDescription, longDescription);
		mapTiles = new ArrayList<>();
	}
	
	@Override
	public String getTableName(){
		return "Maps";
	}
	
	public List<MapTile> getMapTiles(){
		return mapTiles;
	}
	
	public MapTile getMapTileByID(int id){
		for(MapTile mapTile: mapTiles){
			if(mapTile.getID() == id){
				return mapTile;
			}
		}
		return null;
	}
	
	public void addMapTile(MapTile tile){
		mapTiles.add(tile);
	}
	
	public void setMapTiles(List<MapTile> mapTiles){
		this.mapTiles = mapTiles;
	}
}
