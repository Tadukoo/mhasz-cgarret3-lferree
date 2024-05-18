package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.aome.construct.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamePlayerActionsTest{
	private GameController gameController;
	private Player player;
	private GameObject tree;
	private GameObject ladder;
	private MapTile starting;
	private MapTile northOfStarting;
	private MapTile northEastOfStarting;
	private Item sword;
	private Item helmet;
	private Item key;
	private Item wood;
	
	@BeforeEach
	public void setup(){
		Game game = new Game();
		gameController = new GameController(game);
		player = new Player();
		player.setLocationID(0);
		List<Character> characters = new ArrayList<>();
		characters.add(player);
		gameController.setcharacters(characters);
		
		// Populate Player's inventory
		List<Item> playerItems = new ArrayList<>();
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
		
		tree = new GameObject();
		tree.setName("Tree");
		Map<String, String> responses = new HashMap<>();
		responses.put("climb", "It's high up here!");
		tree.setCommandResponses(responses);
		
		// MapTiles		8 1 2
		//				7 0 3
		//				6 5 4
		starting = new MapTile();
		starting.setID(0);
		northOfStarting = new MapTile();
		northOfStarting.setID(1);
		northOfStarting.setLongDescription("You arrive in a lush forest, complete with birds and crickets chirping.");
		northEastOfStarting = new MapTile();
		northEastOfStarting.setID(2);
		northEastOfStarting.setLongDescription("You arrive in a barren wasteland, complete with radiation poisoning.");
		
		GameMap map = new GameMap();
		map.addMapTile(starting);
		map.addMapTile(northOfStarting);
		map.addMapTile(northEastOfStarting);
		gameController.setmap(map);
		
		List<GameObject> objs = new ArrayList<>();
		objs.add(tree);
		GameObject derp = new GameObject();
		List<Item> its = new ArrayList<>();
		wood = new Item();
		wood.setName("Wood");
		its.add(wood);
		derp.setItems(its);
		objs.add(derp);
		starting.setObjects(objs);
	}
	
	/*
	 * Take(Item)
	 */
	@Test
	public void testTakeCommand(){
		assertEquals(3, player.getInventory().size());
		assertEquals(1, starting.getObjects().get(1).getItems().size());
		assertEquals(wood, starting.getObjects().get(1).getItems().get(0));
		
		gameController.take(wood.getName());
		
		assertEquals(0, starting.getObjects().get(1).getItems().size());
		assertEquals(4, player.getInventory().size());
		assertEquals(wood, player.getInventory().get(3));
		
		// Check dialog
		assertEquals(1, gameController.getdialog().size());
		assertEquals("You have taken " + wood.getName(), gameController.getdialog().get(0));
	}
	
	/*
	 * Look
	 */
	@Test
	public void testLookAtStarting(){
		assertEquals(0, player.getLocationID());
		
		gameController.look();
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals(starting.getName(), gameController.getdialog().get(0));
		// TODO: We're now doing String.valueOf here because of it being null and weirdness - maybe should handle this better though
		assertEquals(String.valueOf(starting.getLongDescription()), gameController.getdialog().get(1));
	}
	
	@Test
	public void testLookAtNorthOfStarting(){
		gameController.getcharacters().get(0).setLocationID(1);
		
		assertEquals(1, player.getLocationID());
		
		gameController.look();
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals(northOfStarting.getName(), gameController.getdialog().get(0));
		assertEquals(northOfStarting.getLongDescription(), gameController.getdialog().get(1));
	}
}
