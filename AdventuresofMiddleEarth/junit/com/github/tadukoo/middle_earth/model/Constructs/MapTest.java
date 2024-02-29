package com.github.tadukoo.middle_earth.model.Constructs;

import com.github.tadukoo.aome.construct.Map;
import com.github.tadukoo.aome.construct.MapTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapTest{
	private Map map;
	
	@BeforeEach
	public void setup(){
		// Create a blank map.... self-explanatory
		map = new Map();
	}
	
	@Test
	public void testAddMapTile(){
		// Create 2 MapTiles (that have a difference ;) )
		MapTile tile1 = new MapTile();
		MapTile tile2 = new MapTile();
		tile2.setName("Derpy");
		
		// Ensure that the Map currently has no tiles
		assertEquals(0, map.getMapTiles().size());
		
		// Add tile1 to the Map and check that it was added successfully
		map.addMapTile(tile1);
		assertEquals(1, map.getMapTiles().size());
		assertEquals(tile1, map.getMapTiles().get(0));
		
		// Add tile2 to the Map and check that it was added successfully
		map.addMapTile(tile2);
		assertEquals(2, map.getMapTiles().size());
		assertEquals(tile1, map.getMapTiles().get(0));
		assertEquals(tile2, map.getMapTiles().get(1));
	}
	
	@Test
	public void testSetMapTiles(){
		// Ensure that the Map is currently empty
		assertEquals(0, map.getMapTiles().size());
		
		// Create an ArrayList with one expertly crafted MapTile
		ArrayList<MapTile> mapTiles = new ArrayList<>();
		MapTile tile = new MapTile();
		mapTiles.add(tile);
		
		// Add the ArrayList of MapTiles to the Map
		map.setMapTiles(mapTiles);
		
		// Ensure the ArrayList was properly added
		assertEquals(1, map.getMapTiles().size());
		assertEquals(tile, map.getMapTiles().get(0));
	}
	
	@Test
	public void testResetMapTiles(){
		// Ensure that the Map is currently empty
		assertEquals(0, map.getMapTiles().size());
		
		// Create an ArrayList with one expertly crafted MapTile
		ArrayList<MapTile> mapTiles = new ArrayList<>();
		MapTile tile = new MapTile();
		mapTiles.add(tile);
		
		// Add the ArrayList of MapTiles to the Map
		map.setMapTiles(mapTiles);
		
		// Ensure the ArrayList was properly added
		assertEquals(1, map.getMapTiles().size());
		assertEquals(tile, map.getMapTiles().get(0));
		
		// Create a new ArrayList with 2 Obviously different MapTiles
		ArrayList<MapTile> mapTiles2 = new ArrayList<>();
		MapTile tile1 = new MapTile();
		tile1.setName("Basic");
		MapTile tile2 = new MapTile();
		tile2.setName("Other Basic");
		mapTiles2.add(tile1);
		mapTiles2.add(tile2);
		
		// Set the Map to have the new ArrayList
		map.setMapTiles(mapTiles2);
		
		// Ensure that the ArrayList was set correctly
		assertEquals(2, map.getMapTiles().size());
		assertEquals(tile1, map.getMapTiles().get(0));
		assertEquals(tile2, map.getMapTiles().get(1));
	}
	
	@Test
	public void testGetMapTileByID(){
		// Create 2 obviously different MapTiles
		MapTile tile = new MapTile();
		tile.setID(52);
		MapTile tile2 = new MapTile();
		tile2.setID(509);
		
		// Add the MapTiles to an ArrayList
		ArrayList<MapTile> tiles = new ArrayList<>();
		tiles.add(tile);
		tiles.add(tile2);
		
		// Put the tiles into Map
		map.setMapTiles(tiles);
		
		// Ensure the MapTiles can be retrieved using getMapTileByID(id)
		assertEquals(tile, map.getMapTileByID(52));
		assertEquals(tile2, map.getMapTileByID(509));
	}
	
	@Test
	public void testGetMapTileByIDWhenIDIsNotPresent(){
		// Create 2 obviously different MapTiles
		MapTile tile = new MapTile();
		tile.setID(52);
		MapTile tile2 = new MapTile();
		tile2.setID(509);
		
		// Add the MapTiles to an ArrayList
		ArrayList<MapTile> tiles = new ArrayList<>();
		tiles.add(tile);
		tiles.add(tile2);
		
		// Put the tiles into Map
		map.setMapTiles(tiles);
		
		// Ensure that no MapTile is returned from an invalid ID (1234)
		assertNull(map.getMapTileByID(1234));
	}
}
