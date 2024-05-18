package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.FakeDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The movement commands are in here separate due to how many there are. It's insane.
 */
public class HandleMovementCommandsTest{
	private GameController gameController;
	private Player player;
	private GameMap map;
	private MapTile starting;
	private MapTile northOfStarting;
	private MapTile northEastOfStarting;
	private MapTile eastOfStarting;
	private MapTile southEastOfStarting;
	private MapTile southOfStarting;
	private MapTile southWestOfStarting;
	private MapTile westOfStarting;
	private MapTile northWestOfStarting;
	private static String invalidDirection = "You can't go that way";
	private static String invalidMode = "Sorry, I didn't understand that.";
	
	@BeforeEach
	public void setup(){
		DatabaseProvider.setInstance(new FakeDatabase());
		Game game = new Game();
		gameController = new GameController(game);
		// This is here just in case the Game doesn't initialize the current mode to this.
		gameController.setmode("game");
		player = new Player();
		player.setLocationID(0);
		ArrayList<Character> characters = new ArrayList<>();
		characters.add(player);
		gameController.setcharacters(characters);
		
		// MapTiles		8 1 2
		//				7 0 3
		//				6 5 4
		starting = new MapTile();
		starting.setID(0);
		starting.setName("The Starting Area");
		starting.setLongDescription("You materialize in this area. That is all.");
		
		northOfStarting = new MapTile();
		northOfStarting.setID(1);
		northOfStarting.setName("Forest");
		northOfStarting.setLongDescription("You arrive in a lush forest, complete with birds and crickets chirping.");
		
		northEastOfStarting = new MapTile();
		northEastOfStarting.setID(2);
		northEastOfStarting.setName("Wasteland");
		northEastOfStarting.setLongDescription("You arrive in a barren wasteland, complete with radiation poisoning.");
		
		eastOfStarting = new MapTile();
		eastOfStarting.setID(3);
		eastOfStarting.setName("Candyland");
		eastOfStarting.setLongDescription("You arrive in candyland, where I don't know any of the character names.");
		
		southEastOfStarting = new MapTile();
		southEastOfStarting.setID(4);
		southEastOfStarting.setName("L.A.");
		southEastOfStarting.setLongDescription("You arrive in L.A., just to get a flight to leave.");
		
		southOfStarting = new MapTile();
		southOfStarting.setID(5);
		southOfStarting.setName("CS320 2016");
		southOfStarting.setLongDescription("You arrive in CS320 in 2016, where Logan is failing to make a 2D Platformer in Erlang.");
		
		southWestOfStarting = new MapTile();
		southWestOfStarting.setID(6);
		southWestOfStarting.setName("IDK");
		southWestOfStarting.setLongDescription("You arrive in I don't know, just give it up already.");
		
		westOfStarting = new MapTile();
		westOfStarting.setID(7);
		westOfStarting.setName("CS320 3/12/18");
		westOfStarting.setLongDescription("You arrive in CS320 a week early for the milestone to realize no one has worked on it "
				+ "yet.");
		
		northWestOfStarting = new MapTile();
		northWestOfStarting.setID(8);
		northWestOfStarting.setName("Boredom");
		northWestOfStarting.setLongDescription("You arrive in... The narrator died of boredom, so we're waiting on a new one.");
		
		// Starting tile's connections
		starting.setNorthConnection(1);
		starting.setNortheastConnection(2);
		starting.setEastConnection(3);
		starting.setSoutheastConnection(4);
		starting.setSouthConnection(5);
		starting.setSouthwestConnection(6);
		starting.setWestConnection(7);
		starting.setNorthwestConnection(8);
		
		// Add tiles to Map
		map = new GameMap();
		ArrayList<MapTile> tiles = new ArrayList<>();
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
		
		gameController.setmap(map);
	}
	
	/**
	 * This method is to be used by this class. It ensures that a move command functions in valid conditions
	 */
	public void checkValidMoveUpdates(GameController game, MapTile original, MapTile destination, String command){
		checkValidMovePreconditions(game, original);
		
		// Run the command.
		game.handle_command(command);
		
		checkValidMovePostConditions(game, destination);
	}
	
	/**
	 * This method is to be used by any class that checks that movement preconditions are setup correctly.
	 */
	public static void checkValidMovePreconditions(GameController game, MapTile original){
		// Ensure the player is in the correct starting location.
		assertEquals(original.getID(), game.getplayer().getLocationID());
	}
	
	/**
	 * This method is to be used by any class that checks that movement has worked correctly.
	 * It ensures that conditions after the movement are correct.
	 */
	public static void checkValidMovePostConditions(GameController game, MapTile destination){
		// Ensure the player moved to the correct destination.
		assertEquals(destination.getID(), game.getplayer().getLocationID());
		
		// Ensure 2 lines have been added to the dialog.
		// Note: Cannot check that it's 2 lines exactly because of Combat now
		// Combat can add another line that you encountered an enemy.
		//assertEquals(2, game.getdialog().size());
		// Ensure the first line is the new tile's name.
		assertEquals(destination.getName(), game.getdialog().get(0));
		// Ensure the second line is the new tile's longDescription.
		assertEquals(destination.getLongDescription(), game.getdialog().get(1));
	}
	
	/**
	 * This method is to be used by this class. It ensures that move commands behave properly in invalid conditions.
	 */
	public static void checkInvalidMoveUpdates(GameController game, MapTile setup, String command){
		setupInvalidMovePreConditions(game, setup);
		
		// Run the command
		game.handle_command(command);
		
		checkInvalidMovePostConditions(game, setup);
	}
	
	/**
	 * This method is meant to be used by any class that checks invalid movement conditions.
	 * It sets up the location for the player.
	 */
	public static void setupInvalidMovePreConditions(GameController game, MapTile setup){
		// Set the player's location to the requested maptile.
		game.getplayer().setLocationID(setup.getID());
		// Ensure the player's location is properly set.
		assertEquals(setup.getID(), game.getplayer().getLocationID());
	}
	
	/**
	 * This method is meant to be used by any class that checks invalid movement conditions.
	 * It ensures the method has responded properly to not being able to move that direction.
	 */
	public static void checkInvalidMovePostConditions(GameController game, MapTile setup){
		// Ensure the player's location hasn't changed.
		assertEquals(setup.getID(), game.getplayer().getLocationID());
		// Ensure the game's dialog got one new line.
		assertEquals(1, game.getdialog().size());
		// Ensure that new line is the invalidDirection string.
		assertEquals(invalidDirection, game.getdialog().get(0));
	}
	
	/**
	 * This method is to be used by any class that checks movement commands when not in mode = Game.
	 */
	public static void checkNotInModeGame(GameController game, MapTile original, String command){
		// Set mode to anything other than game
		game.setmode("inventory");
		
		// Ensure before the command that the player is in the proper location.
		assertEquals(original.getID(), game.getplayer().getLocationID());
		
		// Run the command
		String response = game.handle_command(command);
		
		// Ensure that after the command, the player hasn't moved (since not in mode = game)
		assertEquals(original.getID(), game.getplayer().getLocationID());
		// Ensure that 1 line is the invalidMode string
		assertEquals(invalidMode, response);
	}
	
	/*
	 * That one weird issue
	 */
	@Test
	public void testInvalidDirection(){
		// Run command
		gameController.handle_command("move deprirber0oger");
		
		// Check that correct error stuff is set
		assertEquals(1, gameController.getdialog().size());
		assertEquals("I don't understand that direction.", gameController.getdialog().get(0));
	}
	
	/*
	 * North Command Working
	 */
	@Test
	public void testNCommand(){
		checkValidMoveUpdates(gameController, starting, northOfStarting, "n");
	}
	
	@Test
	public void testNorthCommand(){
		checkValidMoveUpdates(gameController, starting, northOfStarting, "north");
	}
	
	@Test
	public void testMoveNorthCommand(){
		checkValidMoveUpdates(gameController, starting, northOfStarting, "move north");
	}
	
	/*
	 * Northeast Command Working
	 */
	@Test
	public void testNECommand(){
		checkValidMoveUpdates(gameController, starting, northEastOfStarting, "ne");
	}
	
	@Test
	public void testNorthEastCommand(){
		checkValidMoveUpdates(gameController, starting, northEastOfStarting, "northeast");
	}
	
	@Test
	public void testMoveNorthEastCommand(){
		checkValidMoveUpdates(gameController, starting, northEastOfStarting, "move northeast");
	}
	
	/*
	 * East Command Working
	 */
	@Test
	public void testECommand(){
		checkValidMoveUpdates(gameController, starting, eastOfStarting, "e");
	}
	
	@Test
	public void testEastCommand(){
		checkValidMoveUpdates(gameController, starting, eastOfStarting, "east");
	}
	
	@Test
	public void testMoveEastCommand(){
		checkValidMoveUpdates(gameController, starting, eastOfStarting, "move east");
	}
	
	/*
	 * Southeast Command Working
	 */
	@Test
	public void testSECommand(){
		checkValidMoveUpdates(gameController, starting, southEastOfStarting, "se");
	}
	
	@Test
	public void testSouthEastCommand(){
		checkValidMoveUpdates(gameController, starting, southEastOfStarting, "southeast");
	}
	
	@Test
	public void testMoveSouthEastCommand(){
		checkValidMoveUpdates(gameController, starting, southEastOfStarting, "move southeast");
	}
	
	/*
	 * South Command Working
	 */
	@Test
	public void testSCommand(){
		checkValidMoveUpdates(gameController, starting, southOfStarting, "s");
	}
	
	@Test
	public void testSouthCommand(){
		checkValidMoveUpdates(gameController, starting, southOfStarting, "south");
	}
	
	@Test
	public void testMoveSouthCommand(){
		checkValidMoveUpdates(gameController, starting, southOfStarting, "move south");
	}
	
	/*
	 * Southwest Command Working
	 */
	@Test
	public void testSWCommand(){
		checkValidMoveUpdates(gameController, starting, southWestOfStarting, "sw");
	}
	
	@Test
	public void testSouthWestCommand(){
		checkValidMoveUpdates(gameController, starting, southWestOfStarting, "southwest");
	}
	
	@Test
	public void testMoveSouthWestCommand(){
		checkValidMoveUpdates(gameController, starting, southWestOfStarting, "move southwest");
	}
	
	/*
	 * West Command Working
	 */
	@Test
	public void testWCommand(){
		checkValidMoveUpdates(gameController, starting, westOfStarting, "w");
	}
	
	@Test
	public void testWestCommand(){
		checkValidMoveUpdates(gameController, starting, westOfStarting, "west");
	}
	
	@Test
	public void testMoveWestCommand(){
		checkValidMoveUpdates(gameController, starting, westOfStarting, "move west");
	}
	
	/*
	 * Northwest Command Working
	 */
	@Test
	public void testNWCommand(){
		checkValidMoveUpdates(gameController, starting, northWestOfStarting, "nw");
	}
	
	@Test
	public void testNorthWestCommand(){
		checkValidMoveUpdates(gameController, starting, northWestOfStarting, "northwest");
	}
	
	@Test
	public void testMoveNorthWestCommand(){
		checkValidMoveUpdates(gameController, starting, northWestOfStarting, "move northwest");
	}
	
	/*
	 * ******************
	 * Invalids
	 * ******************
	 * TODO: JUNIT: Unique messages for invalid directions?
	 */
	
	/*
	 * North Command Invalid
	 */
	@Test
	public void testNCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "n");
	}
	
	@Test
	public void testNorthCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "north");
	}
	
	@Test
	public void testMoveNorthCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move north");
	}
	
	/*
	 * Northeast Command Invalid
	 */
	@Test
	public void testNECommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "ne");
	}
	
	@Test
	public void testNorthEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "northeast");
	}
	
	@Test
	public void testMoveNorthEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move northeast");
	}
	
	/*
	 * East Command Invalid
	 */
	@Test
	public void testECommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "e");
	}
	
	@Test
	public void testEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "east");
	}
	
	@Test
	public void testMoveEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move east");
	}
	
	/*
	 * Southeast Command Invalid
	 */
	@Test
	public void testSECommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "se");
	}
	
	@Test
	public void testSouthEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "southeast");
	}
	
	@Test
	public void testMoveSouthEastCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move southeast");
	}
	
	/*
	 * South Command Invalid
	 */
	@Test
	public void testSCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "s");
	}
	
	@Test
	public void testSouthCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "south");
	}
	
	@Test
	public void testMoveSouthCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move south");
	}
	
	/*
	 * Southwest Command Invalid
	 */
	@Test
	public void testSWCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "sw");
	}
	
	@Test
	public void testSouthWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "southwest");
	}
	
	@Test
	public void testMoveSouthWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move southwest");
	}
	
	/*
	 * West Command Invalid
	 */
	@Test
	public void testWCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "w");
	}
	
	@Test
	public void testWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "west");
	}
	
	@Test
	public void testMoveWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move west");
	}
	
	/*
	 * Northwest Command Invalid
	 */
	@Test
	public void testNWCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "nw");
	}
	
	@Test
	public void testNorthWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "northwest");
	}
	
	@Test
	public void testMoveNorthWestCommandInvalid(){
		checkInvalidMoveUpdates(gameController, northOfStarting, "move northwest");
	}
	
	/*
	 * ******************
	 * Not in Mode = Game
	 * ******************
	 */
	
	/*
	 * North Command Invalid
	 */
	@Test
	public void testNCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "n");
	}
	
	@Test
	public void testNorthCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "north");
	}
	
	@Test
	public void testMoveNorthCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move north");
	}
	
	/*
	 * Northeast Command Invalid
	 */
	@Test
	public void testNECommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "ne");
	}
	
	@Test
	public void testNorthEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "northeast");
	}
	
	@Test
	public void testMoveNorthEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move northeast");
	}
	
	/*
	 * East Command Invalid
	 */
	@Test
	public void testECommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "e");
	}
	
	@Test
	public void testEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "east");
	}
	
	@Test
	public void testMoveEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move east");
	}
	
	/*
	 * Southeast Command Invalid
	 */
	@Test
	public void testSECommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "se");
	}
	
	@Test
	public void testSouthEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "southeast");
	}
	
	@Test
	public void testMoveSouthEastCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move southeast");
	}
	
	/*
	 * South Command Invalid
	 */
	@Test
	public void testSCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "s");
	}
	
	@Test
	public void testSouthCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "south");
	}
	
	@Test
	public void testMoveSouthCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move south");
	}
	
	/*
	 * Southwest Command Invalid
	 */
	@Test
	public void testSWCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "sw");
	}
	
	@Test
	public void testSouthWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "southwest");
	}
	
	@Test
	public void testMoveSouthWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move southwest");
	}
	
	/*
	 * West Command Invalid
	 */
	@Test
	public void testWCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "w");
	}
	
	@Test
	public void testWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "west");
	}
	
	@Test
	public void testMoveWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move west");
	}
	
	/*
	 * Northwest Command Invalid
	 */
	@Test
	public void testNWCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "nw");
	}
	
	@Test
	public void testNorthWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "northwest");
	}
	
	@Test
	public void testMoveNorthWestCommandNotInModeGame(){
		checkNotInModeGame(gameController, starting, "move northwest");
	}
}
