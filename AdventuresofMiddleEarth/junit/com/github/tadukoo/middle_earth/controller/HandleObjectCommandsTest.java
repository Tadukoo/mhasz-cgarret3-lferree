package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandleObjectCommandsTest{
	private Game game;
	
	@BeforeEach
	public void setup(){
		// Create the Game
		game = new Game();
		
		// Create Player
		Player player = new Player();
		player.setLocationID(0);
		
		// Create Characters Array and Put in Player
		ArrayList<Character> chars = new ArrayList<>();
		chars.add(player);
		
		// Set Characters Array in Game
		game.setcharacters(chars);
		
		// Create a generic MapTile
		MapTile tile = new MapTile();
		tile.setName("Derp");
		tile.setID(0);
		
		// Create a MapTiles array and add the Tile to it
		ArrayList<MapTile> tiles = new ArrayList<>();
		tiles.add(tile);
		
		// Create the Map and set the MapTiles Array to it
		GameMap map = new GameMap();
		map.setMapTiles(tiles);
		
		// Set the Map to the Game
		game.setmap(map);
		
		// Create a tree
		GameObject IDontKnowAnymore = new GameObject();
		IDontKnowAnymore.setName("Tree");
		HashMap<String, String> commandResponses = new HashMap<>();
		commandResponses.put("climb", "Hello now");
		IDontKnowAnymore.setCommandResponses(commandResponses);
		
		// Create an Objects Array with the Tree included
		ArrayList<GameObject> objs = new ArrayList<>();
		objs.add(IDontKnowAnymore);
		
		// Set the Objects Array to the MapTile
		tile.setObjects(objs);
	}
	
	@Test
	public void testClimbTreeCommand(){
		// Run the command
		game.handle_command("climb tree");
		
		// Check that dialog was updated correctly
		assertEquals(1, game.getdialog().size());
		assertEquals("Hello now", game.getdialog().get(0));
	}
	
	@Test
	public void testClimbTreeCommandWeirdCapitals(){
		// Run the command
		game.handle_command("cLImB TReE");
		
		// Check that dialog was updated correctly
		assertEquals(1, game.getdialog().size());
		assertEquals("Hello now", game.getdialog().get(0));
	}
}
