package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;

import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.FakeDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * These tests are solely for the Character-Specific (excluding Player) action methods in Game.
 * Other methods in Game are tested elsewhere.
 */
public class GameCharacterActionsTest{
	private Game game;
	private GameMap map;
	private Player player;
	private MapTile starting;
	private MapTile northOfStarting;
	private MapTile northEastOfStarting;
	private MapTile eastOfStarting;
	private MapTile southEastOfStarting;
	private MapTile southOfStarting;
	private MapTile southWestOfStarting;
	private MapTile westOfStarting;
	private MapTile northWestOfStarting;
	
	@BeforeEach
	public void setup(){
		DatabaseProvider.setInstance(new FakeDatabase());
		game = new Game();
		// This is here in case Game doesn't set mode to game by default.
		game.setmode("game");
		player = new Player();
		player.setLocationID(0);
		ArrayList<Character> characters = new ArrayList<Character>();
		characters.add(player);
		game.setcharacters(characters);
		starting = new MapTile();
		starting.setID(0);
		northOfStarting = new MapTile();
		northOfStarting.setID(1);
		northOfStarting.setLongDescription("You arrive in a lush forest, complete with birds and crickets chirping.");
		northEastOfStarting = new MapTile();
		northEastOfStarting.setID(2);
		northEastOfStarting.setLongDescription("You arrive in a barren wasteland, complete with radiation poisoning.");
		eastOfStarting = new MapTile();
		eastOfStarting.setID(3);
		eastOfStarting.setLongDescription("You arrive in candyland, where I don't know any of the character names.");
		southEastOfStarting = new MapTile();
		southEastOfStarting.setID(4);
		southEastOfStarting.setLongDescription("You arrive in L.A., just to get a flight to leave.");
		southOfStarting = new MapTile();
		southOfStarting.setID(5);
		southOfStarting.setLongDescription("You arrive in CS320 in 2016, where Logan is failing to make a 2D Platformer in Erlang.");
		southWestOfStarting = new MapTile();
		southWestOfStarting.setID(6);
		southWestOfStarting.setLongDescription("You arrive in I don't know, just give it up already.");
		westOfStarting = new MapTile();
		westOfStarting.setID(7);
		westOfStarting.setLongDescription("You arrive in CS320 a week early for the milestone to realize no one has worked on it "
				+ "yet.");
		northWestOfStarting = new MapTile();
		northWestOfStarting.setID(8);
		northWestOfStarting.setLongDescription("You arrive in... The narrator died of boredom, so we're waiting on a new one.");
		
		// Set Connections
		starting.setNorthConnection(1);
		starting.setNortheastConnection(2);
		starting.setEastConnection(3);
		starting.setSoutheastConnection(4);
		starting.setSouthConnection(5);
		starting.setSouthwestConnection(6);
		starting.setWestConnection(7);
		starting.setNorthwestConnection(8);
		
		// Create Map
		map = new GameMap();
		
		// Populate Map
		ArrayList<MapTile> tiles = new ArrayList<MapTile>();
		tiles.add(starting);
		tiles.add(northOfStarting);
		tiles.add(northEastOfStarting);
		tiles.add(eastOfStarting);
		tiles.add(southEastOfStarting);
		tiles.add(southOfStarting);
		tiles.add(southWestOfStarting);
		tiles.add(westOfStarting);
		tiles.add(northWestOfStarting);
		map.setMapTiles(tiles);
		
		game.setmap(map);
	}
	
	public void testValidMove(Game game, MapTile original, MapTile destination, String direction){
		HandleMovementCommandsTest.checkValidMovePreconditions(game, original);
		
		game.move(direction);
		
		HandleMovementCommandsTest.checkValidMovePostConditions(game, destination);
	}
	
	public void testInvalidMove(Game game, MapTile setup, String direction){
		HandleMovementCommandsTest.setupInvalidMovePreConditions(game, setup);
		
		game.move(direction);
		
		HandleMovementCommandsTest.checkInvalidMovePostConditions(game, setup);
	}
	
	
	/*
	 * Move Player
	 */
	
	@Test
	public void testMoveNorth(){
		testValidMove(game, starting, northOfStarting, "north");
	}
	
	@Test
	public void testMoveNorthEast(){
		testValidMove(game, starting, northEastOfStarting, "northeast");
	}
	
	@Test
	public void testMoveEast(){
		testValidMove(game, starting, eastOfStarting, "east");
	}
	
	@Test
	public void testMoveSouthEast(){
		testValidMove(game, starting, southEastOfStarting, "southeast");
	}
	
	@Test
	public void testMoveSouth(){
		testValidMove(game, starting, southOfStarting, "south");
	}
	
	@Test
	public void testMoveSouthWest(){
		testValidMove(game, starting, southWestOfStarting, "southwest");
	}
	
	@Test
	public void testMoveWest(){
		testValidMove(game, starting, westOfStarting, "west");
	}
	
	@Test
	public void testMoveNorthWest(){
		testValidMove(game, starting, northWestOfStarting, "northwest");
	}
	
	/*
	 * Invalid Move Commands
	 */
	
	@Test
	public void testMoveNorthInvalid(){
		testInvalidMove(game, northOfStarting, "north");
	}
	
	@Test
	public void testMoveNorthEastInvalid(){
		testInvalidMove(game, northOfStarting, "northeast");
	}
	
	@Test
	public void testMoveEastInvalid(){
		testInvalidMove(game, northOfStarting, "east");
	}
	
	@Test
	public void testMoveSouthEastInvalid(){
		testInvalidMove(game, northOfStarting, "southeast");
	}
	
	@Test
	public void testMoveSouthInvalid(){
		testInvalidMove(game, northOfStarting, "south");
	}
	
	@Test
	public void testMoveSouthWestInvalid(){
		testInvalidMove(game, northOfStarting, "southwest");
	}
	
	@Test
	public void testMoveWestInvalid(){
		testInvalidMove(game, northOfStarting, "west");
	}
	
	@Test
	public void testMoveNorthWestInvalid(){
		testInvalidMove(game, northOfStarting, "northwest");
	}
}
