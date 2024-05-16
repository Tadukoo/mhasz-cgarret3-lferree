package com.github.tadukoo.aome.construct;

import com.github.tadukoo.aome.construct.map.MapTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTileTest{
	private MapTile tile;
	
	@BeforeEach
	public void setup(){
		tile = new MapTile();
	}
	
	@Test
	public void testDefaultConnections(){
		// They all need to be null by default
		assertNull(tile.getConnections().getNorthTileID());
		assertNull(tile.getConnections().getNortheastTileID());
		assertNull(tile.getConnections().getEastTileID());
		assertNull(tile.getConnections().getSoutheastTileID());
		assertNull(tile.getConnections().getSouthTileID());
		assertNull(tile.getConnections().getSouthwestTileID());
		assertNull(tile.getConnections().getWestTileID());
		assertNull(tile.getConnections().getNorthwestTileID());
		
		// Also test the specific get methods
		assertNull(tile.getNorthConnection());
		assertNull(tile.getNortheastConnection());
		assertNull(tile.getEastConnection());
		assertNull(tile.getSoutheastConnection());
		assertNull(tile.getSouthConnection());
		assertNull(tile.getSouthwestConnection());
		assertNull(tile.getWestConnection());
		assertNull(tile.getNorthwestConnection());
	}
	
	@Test
	public void testSetConnection(){
		// Set north to be 10
		tile.setNorthConnection(10);
		// Check north is 10
		assertEquals(10, tile.getNorthConnection());
		
		// Set north to be 93
		tile.setNorthConnection(93);
		// Check north to be 93
		assertEquals(93, tile.getNorthConnection());
	}
	
	@Test
	public void testSetObjects(){
		// Create an ArrayList of Objects
		ArrayList<GameObject> objs = new ArrayList<>();
		GameObject derp = new GameObject();
		derp.setName("Derpykins");
		GameObject pling = new GameObject();
		pling.setName("Mine");
		objs.add(derp);
		objs.add(pling);
		
		// Set it to MapTile
		tile.setObjects(objs);
		
		// Ensure it's right
		assertEquals(2, tile.getObjects().size());
		assertEquals(derp, tile.getObjects().get(0));
		assertEquals(pling, tile.getObjects().get(1));
		
		// Create another ArrayList
		ArrayList<GameObject> objs2 = new ArrayList<>();
		GameObject ploppy = new GameObject();
		ploppy.setName("IDK");
		GameObject sloppy = new GameObject();
		sloppy.setName("Hopefully not too much");
		GameObject eh = new GameObject();
		eh.setName("Nah");
		objs2.add(ploppy);
		objs2.add(sloppy);
		objs2.add(eh);
		
		// Reset it to MapTile (in case of crazy adding)
		tile.setObjects(objs2);
		
		// Check that right
		assertEquals(3, tile.getObjects().size());
		assertEquals(ploppy, tile.getObjects().get(0));
		assertEquals(sloppy, tile.getObjects().get(1));
		assertEquals(eh, tile.getObjects().get(2));
	}
	
	@Test
	public void testSetVisited(){
		// Check that by default it's not
		assertFalse(tile.getVisited());
		
		// Set it true
		tile.setVisited(true);
		
		// Check it true
		assertTrue(tile.getVisited());
		
		// Try setting true again (to ensure not just a flip)
		tile.setVisited(true);
		
		// Check it true (again)
		assertTrue(tile.getVisited());
	}
	
	@Test
	public void testSetEnemyString(){
		// Set it
		tile.setEnemyString("2,39,1,980394,120");
		
		// Check it
		assertEquals("2,39,1,980394,120", tile.getEnemyString());
		
		// Reset it (to ensure no crazies)
		tile.setEnemyString("1,2,3,4,65");
		
		// Check it again
		assertEquals("1,2,3,4,65", tile.getEnemyString());
	}
	
	@Test
	public void testGetEnemyIDs(){
		// Set the EnemyString
		tile.setEnemyString("2,39,1,980394,120");
		
		// Get the ArrayList
		ArrayList<Integer> ids = tile.getEnemyIDs();
		
		// Check it's right
		assertEquals(5, ids.size());
		assertEquals(2, (int) ids.get(0));
		assertEquals(39, (int) ids.get(1));
		assertEquals(1, (int) ids.get(2));
		assertEquals(980394, (int) ids.get(3));
		assertEquals(120, (int) ids.get(4));
	}
}
