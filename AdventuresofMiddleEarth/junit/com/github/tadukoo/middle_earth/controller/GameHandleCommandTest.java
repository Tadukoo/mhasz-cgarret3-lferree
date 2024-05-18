package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.middle_earth.model.CombatSituation;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * These Tests are meant to solely test handle_command(String command). The methods called by handle_command are 
 * tested in other Test classes.
 * 
 * Movement Commands are in a separate class due to there being a lot of different ones.
 */
public class GameHandleCommandTest{
	private GameController gameController;
	private Player player;
	private Item sword;
	private Item helmet;
	private Item key;
	private Item wood;
	private GameObject tree;
	private GameObject ladder;
	private MapTile starting;
	private MapTile northOfStarting;
	private MapTile northEastOfStarting;
	private String noComprend;
	
	@BeforeEach
	public void setup(){
		Game game = new Game();
		gameController = new GameController(game);
		player = new Player();
		player.setLocationID(0);
		
		// In case not already in game mode
		gameController.setmode("game");
		
		// Populate Player's inventory
		ArrayList<Item> playerItems = new ArrayList<>();
		sword = new Item();
		sword.setName("Sword");
		sword.setLongDescription("A Long sword. Probably stolen from a giant golem or something.");
		sword.setWeight((float) 9.6);
		sword.setQuestItem(false);
		helmet = new Item();
		helmet.setName("Helmet");
		helmet.setLongDescription("A helmet forged in the hot, hot fires of Mordor.");
		helmet.setWeight((float) 29.3);
		helmet.setQuestItem(false);
		key = new Item();
		key.setName("Key");
		key.setLongDescription("A key to treasure too expensive to buy with Bill Gates' salary. (Believe it)");
		key.setWeight((float) 93.1);
		key.setQuestItem(true);
		playerItems.add(sword);
		playerItems.add(helmet);
		playerItems.add(key);
		player.setInventory(playerItems);
		
		// Add Player to Game
		ArrayList<Character> characters = new ArrayList<>();
		characters.add(player);
		gameController.setcharacters(characters);
		
		// MapTiles		8 1 2
		//				7 0 3
		//				6 5 4
		starting = new MapTile();
		starting.setID(0);
		starting.setName("The Starting Tile");
		starting.setLongDescription("It's not that exciting...");
		northOfStarting = new MapTile();
		northOfStarting.setID(1);
		northOfStarting.setName("Forest");
		northOfStarting.setLongDescription("You arrive in a lush forest, complete with birds and crickets chirping.");
		northEastOfStarting = new MapTile();
		northEastOfStarting.setID(2);
		northEastOfStarting.setLongDescription("You arrive in a barren wasteland, complete with radiation poisoning.");
		
		GameMap map = new GameMap();
		map.addMapTile(starting);
		map.addMapTile(northOfStarting);
		map.addMapTile(northEastOfStarting);
		gameController.setmap(map);
		
		ladder = new GameObject();
		ladder.setName("Ladder");
		HashMap<String, String> responses2 = new HashMap<>();
		responses2.put("climb", "It's not so high up here...");
		ladder.setCommandResponses(responses2);
		ArrayList<GameObject> objs2 = new ArrayList<>();
		objs2.add(ladder);
		northOfStarting.setObjects(objs2);
		
		wood = new Item();
		wood.setName("wood");
		
		ArrayList<Item> items = new ArrayList<>();
		items.add(wood);
		
		// Object burple holds wood
		GameObject burple = new GameObject();
		burple.setItems(items);
		
		tree = new GameObject();
		tree.setName("Tree");
		HashMap<String, String> responses = new HashMap<>();
		responses.put("climb", "It's high up here!");
		tree.setCommandResponses(responses);
		ArrayList<GameObject> objs = new ArrayList<>();
		objs.add(tree);
		objs.add(burple);
		starting.setObjects(objs);
		
		noComprend = "Sorry, I didn't understand that.";
	}
	
	@Test
	public void testInvalidCommand(){
		gameController.handle_command("blofjerf");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals(noComprend, gameController.getdialog().get(0));
	}
	
	@Test
	public void testTooManyArgumentsInCommand(){
		String response = gameController.handle_command("test command long");
		
		assertEquals("Too many arguments in your command", response);
	}
	
	@Test
	public void testNullCommandInModeGame(){
		String response = gameController.handle_command("");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals(noComprend, gameController.getdialog().get(0));
		assertNull(response);
	}
	
	@Test
	public void testNullCommandInModeInventory(){
		gameController.setmode("inventory");
		
		String response = gameController.handle_command("");
		
		assertEquals(noComprend, response);
	}
	
	@Test
	public void testSpaceCommandInModeGame(){
		String response = gameController.handle_command(" ");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals(noComprend, gameController.getdialog().get(0));
		assertNull(response);
	}
	
	@Test
	public void testSpaceCommandInModeInventory(){
		gameController.setmode("inventory");
		
		String response = gameController.handle_command(" ");
		
		assertEquals(noComprend, response);
	}
	
	/*
	 * Game-Based Commands (Not Specific to Characters)
	 * TODO: JUNIT: Save Command
	 */
	
	/*
	 * Inventory Mode Commands
	 * Item Command
	 */
	@Test
	public void testItemCommandNoNumber(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item");
		
		// Check that response is correct
		assertEquals("Please designate the item # you want to view more details of.", response);
	}
	
	@Test
	public void testItemCommandNotInInventoryMode(){
		gameController.handle_command("item");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Sorry, I didn't understand that.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testItemCommandNotANumber(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item derpykinsmcgee");
		
		// Check that response is correct
		assertEquals("Invalid item selection. Example: 'item 1' to see the item at position 1", response);
	}
	
	@Test
	public void testItemCommandInvalidNumber0(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item 0");
		
		// Check that response is correct
		assertEquals("Sorry you dont have an item at that index", response);
	}
	
	@Test
	public void testItemCommandInvalidNumberAboveRange(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item 4");
		
		// Check that response is correct
		assertEquals("Sorry you dont have an item at that index", response);
	}
	
	@Test
	public void testItemCommandLowEndOf1(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item 1");
		
		// Set item to sword (for copy-pasting)
		Item item = sword;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getWeight() +
				";Quest item: " + item.isQuestItem(), response);
	}
	
	@Test
	public void testItemCommandMidRangeOf2(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item 2");
		
		// Set item to helmet (for copy-pasting)
		Item item = helmet;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getWeight() +
				";Quest item: " + item.isQuestItem(), response);
	}
	
	@Test
	public void testItemCommandHighEndOf3(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("item 3");
		
		// Set item to key (for copy-pasting)
		Item item = key;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getWeight() +
				";Quest item: " + item.isQuestItem(), response);
	}
	
	/* 
	 * Player-Specific Commands
	 * Climb(Object) Commands
	 */
	@Test
	public void testClimbTreeCommand(){
		gameController.handle_command("climb tree");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals(tree.getCommandResponses().get("climb"), gameController.getdialog().get(0));
	}
	
	@Test
	public void testClimbTreeCommandNoClimbablePresent(){
		gameController.getcharacters().get(0).setLocationID(2);
		
		gameController.handle_command("climb tree");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Sorry, I didn't understand that.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testClimbTreeCommandOtherClimbablePresent(){
		gameController.getcharacters().get(0).setLocationID(1);
		
		gameController.handle_command("climb tree");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Sorry, I didn't understand that.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testClimbLadderCommand(){
		gameController.getcharacters().get(0).setLocationID(1);
		gameController.handle_command("climb ladder");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals(ladder.getCommandResponses().get("climb"), gameController.getdialog().get(0));
	}
	
	@Test
	public void testClimbLadderCommandNoClimbable(){
		gameController.getcharacters().get(0).setLocationID(2);
		
		gameController.handle_command("climb ladder");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Sorry, I didn't understand that.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testClimbLadderCommandOtherClimbablePresent(){
		gameController.handle_command("climb ladder");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Sorry, I didn't understand that.", gameController.getdialog().get(0));
	}
	
	/*
	 * Player-Specific Commands
	 * Take(Item) Command
	 */
	@Test
	public void testTakeCommand(){
		assertEquals(3, player.getInventory().size());
		assertEquals(1, starting.getObjects().get(1).getItems().size());
		assertEquals(wood, starting.getObjects().get(1).getItems().get(0));
		
		gameController.handle_command("take wood");
		
		assertEquals(0, starting.getObjects().get(1).getItems().size());
		assertEquals(4, player.getInventory().size());
		assertEquals(wood, player.getInventory().get(3));
		
		// Check dialog
		assertEquals(1, gameController.getdialog().size());
		assertEquals("You have taken " + wood.getName(), gameController.getdialog().get(0));
	}
	
	@Test
	public void testTakeCommandItemNotOnTile(){
		// Set location of wood to a different tile than starting.
		starting.setObjects(null);
		ArrayList<GameObject> objs = new ArrayList<>();
		GameObject derp = new GameObject();
		ArrayList<Item> items = new ArrayList<>();
		items.add(wood);
		derp.setItems(items);
		objs.add(derp);
		northOfStarting.setObjects(objs);
		
		assertEquals(3, player.getInventory().size());
		assertEquals(wood, northOfStarting.getObjects().get(0).getItems().get(0));
		
		gameController.handle_command("take wood");
		
		assertEquals(1, northOfStarting.getObjects().get(0).getItems().size());
		assertEquals(wood, northOfStarting.getObjects().get(0).getItems().get(0));
		assertEquals(3, player.getInventory().size());
		
		// Check dialog
		assertEquals(1, gameController.getdialog().size());
		assertEquals("There is nothing to take here.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testTakeCommandItemDoesntExist(){
		assertEquals(3, player.getInventory().size());
		
		gameController.handle_command("take cheese");
		
		assertEquals(3, player.getInventory().size());
		assertEquals(1, gameController.getdialog().size());
		assertEquals("You cannot take cheese here.", gameController.getdialog().get(0));
	}
	
	@Test
	public void testTakeCommandNotInModeGame(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Check that stuff is like this before command run
		assertEquals(3, player.getInventory().size());
		
		// Run command and get response
		String response = gameController.handle_command("take wood");
		
		// Make sure stuff wasn't changed
		assertEquals(3, player.getInventory().size());
		
		// Check that response is correct
		assertEquals("Sorry, I didn't understand that.", response);
	}
	
	/*
	 * Player-Specific Commands
	 * Look Command
	 */
	@Test
	public void testLookCommandAtStarting(){
		gameController.handle_command("look");
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals(starting.getName(), gameController.getdialog().get(0));
		assertEquals(starting.getLongDescription(), gameController.getdialog().get(1));
	}
	
	@Test
	public void testLookCommandAtNorthOfStarting(){
		gameController.getcharacters().get(0).setLocationID(1);
		
		gameController.handle_command("look");
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals(northOfStarting.getName(), gameController.getdialog().get(0));
		assertEquals(northOfStarting.getLongDescription(), gameController.getdialog().get(1));
	}
	
	@Test
	public void testLookCommandNotInModeGame(){
		// Set mode to inventory
		gameController.setmode("inventory");
		
		// Run command and get response
		String response = gameController.handle_command("look");
		
		// Check that response is correct
		assertEquals("Sorry, I didn't understand that.", response);
	}
	
	/*
	 * Player-Specific Commands
	 * Attack Tests
	 */
	@Test
	public void testAttackNotInCombat(){
		// Run command
		gameController.handle_command("attack");
		
		// Check proper error stuff
		assertEquals(1, gameController.getdialog().size());
		assertEquals("You're not in combat!", gameController.getdialog().get(0));
	}
	
	/*@Test			No Longer a Valid Test
	public void testAttackOnBossTile(){
		// Set up pre-conditions
		game.getplayer().setlocation(7);
		
		// Run command
		game.handle_command("attack");
		
		// Check proper messages
		assertEquals(5, game.getdialog().size());
		assertEquals("You take the pointy stick and throw it at the troll.", game.getdialog().get(0));
		assertEquals("It manages to poke him in the eye and knock him off balance.", game.getdialog().get(1));
		assertEquals("As he falls, he drops his sword and you quickly spring into action.", game.getdialog().get(2));
		assertEquals("You grab his sword off the ground and lay waste to the foul beast.", game.getdialog().get(3));
		assertEquals("!!!CONGRATULATIONS!!! You have conquered this small land and laid waste to the evil plaguing it!", 
				game.getdialog().get(4));
	}*/
	
	@Test
	public void testAttackNoTarget(){
		// Set up pre-conditions
		gameController.setBattle(new CombatSituation(gameController, 1, 0));
		gameController.setdialog(new ArrayList<>());
		
		// Run command
		gameController.handle_command("attack");
		
		// Check error
		assertEquals(1, gameController.getdialog().size());
		assertEquals("What do you want to attack? (use name or race)", gameController.getdialog().get(0));
	}
	
	@Test
	public void testNonAttackCommandInCombat(){
		// Set up pre-conditions
		gameController.setBattle(new CombatSituation(gameController, 1, 0));
		gameController.setdialog(new ArrayList<>());
		
		// Run command
		gameController.handle_command("boogledee boo");
		
		// Check error
		assertEquals(1, gameController.getdialog().size());
		assertEquals("You're in combat!", gameController.getdialog().get(0));
	}
}
