package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.middle_earth.model.CombatSituation;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.aome.construct.map.MapTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * These tests are solely meant to test the setters in the Game class. Other methods are tested in other Test classes.
 */
public class GameSettersTest{
	
	// TODO: JUNIT: Test that the constructor properly gets stuff from the database?
	@Test
	public void testgetGame(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		assertEquals(gameController, gameController.getGameController());
	}
	
	@Test
	public void testsetMode(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		gameController.setmode("game");
		
		assertEquals("game", gameController.getmode());
	}
	
	@Test
	public void testResetMode(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		gameController.setmode("game");
		
		assertEquals("game", gameController.getmode());
		
		gameController.setmode("inventory");
		
		assertEquals("inventory", gameController.getmode());
	}
	
	@Test
	public void testsetCharacters(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		chars.add(player);
		
		gameController.setcharacters(chars);
		
		assertEquals(1, gameController.getcharacters().size());
		assertEquals(player, gameController.getcharacters().get(0));
	}
	
	@Test
	public void testResetCharacters(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		chars.add(player);
		
		gameController.setcharacters(chars);
		
		assertEquals(1, gameController.getcharacters().size());
		assertEquals(player, gameController.getcharacters().get(0));
		
		ArrayList<Character> chars2 = new ArrayList<>();
		chars2.add(new Player());
		chars2.add(new Player());
		
		gameController.setcharacters(chars2);
		
		assertEquals(2, gameController.getcharacters().size());
	}
	
	@Test
	public void testgetPlayer(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<Character> characters = new ArrayList<>();
		characters.add(new Player());
		
		gameController.setcharacters(characters);
		
		assertEquals(gameController.getcharacters().get(0), gameController.getplayer());
	}
	
	@Test
	public void testgetPlayer_setCharacters(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		player.setHP(100);
		player.setName("Derpkins");
		Player player2 = new Player();
		player2.setAttack(20);
		player2.setName("My Face");
		chars.add(player);
		chars.add(player2);
		
		gameController.setcharacters(chars);
		
		assertEquals(2, gameController.getcharacters().size());
		assertEquals(player, gameController.getcharacters().get(0));
		assertEquals(player2, gameController.getcharacters().get(1));
		
		assertEquals(player, gameController.getplayer());
	}
	
	@Test
	public void testsetMap(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		GameMap mappy = new GameMap();
		gameController.setmap(mappy);
		
		assertEquals(mappy, gameController.getmap());
	}
	
	@Test
	public void testResetMap(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		GameMap mappy = new GameMap();
		gameController.setmap(mappy);
		
		assertEquals(mappy, gameController.getmap());
		
		GameMap mappy2 = new GameMap();
		mappy2.addMapTile(new MapTile());
		gameController.setmap(mappy2);
		
		assertEquals(mappy2, gameController.getmap());
	}
	
	@Test
	public void testsetDialog(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<String> test_dialog = new ArrayList<>();
		test_dialog.add("This is a ");
		test_dialog.add("pretty simple ");
		test_dialog.add(" test.");
		
		gameController.setdialog(test_dialog);
		
		assertEquals(3, gameController.getdialog().size());
		assertEquals("This is a ", gameController.getdialog().get(0));
		assertEquals("pretty simple ", gameController.getdialog().get(1));
		assertEquals(" test.", gameController.getdialog().get(2));
	}
	
	@Test
	public void testResetDialog(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<String> test_dialog = new ArrayList<>();
		test_dialog.add("This is a ");
		test_dialog.add("pretty simple ");
		test_dialog.add(" test.");
		
		gameController.setdialog(test_dialog);
		
		assertEquals(3, gameController.getdialog().size());
		assertEquals("This is a ", gameController.getdialog().get(0));
		assertEquals("pretty simple ", gameController.getdialog().get(1));
		assertEquals(" test.", gameController.getdialog().get(2));
		
		ArrayList<String> test_dialog2 = new ArrayList<>();
		test_dialog2.add("Another");
		test_dialog2.add("simple");
		
		gameController.setdialog(test_dialog2);
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals("Another", gameController.getdialog().get(0));
		assertEquals("simple", gameController.getdialog().get(1));
	}
	
	@Test
	public void testAdd_Dialog(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		assertEquals(0, gameController.getdialog().size());
		
		gameController.add_dialog("Test");
		
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Test", gameController.getdialog().get(0));
		
		gameController.add_dialog("Testy 2");
		
		assertEquals(2, gameController.getdialog().size());
		assertEquals("Test", gameController.getdialog().get(0));
		assertEquals("Testy 2", gameController.getdialog().get(1));
	}
	
	@Test
	public void testAdd_Dialog_Over25(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		assertEquals(0, gameController.getdialog().size());
		
		for(int i = 0; i < 35; i++){
			gameController.add_dialog("Test: " + i);
			assertEquals(i+1, gameController.getdialog().size());
			for(int j = 0; j < i; j++){
				assertEquals("Test: " + j, gameController.getdialog().get(j));
			}
		}
		assertEquals(35, gameController.getdialog().size());
		
		gameController.add_dialog("Derp");
		
		// 35 = max dialog length
		assertEquals(35, gameController.getdialog().size());
		
		for(int i = 0; i < 34; i++){
			assertEquals("Test: " + (i+1), gameController.getdialog().get(i));
		}
		assertEquals("Derp", gameController.getdialog().get(34));
	}
	
	@Test
	public void testgetDisplay_Text(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<String> test_dialog = new ArrayList<>();
		test_dialog.add("This is a ");
		test_dialog.add("pretty simple ");
		test_dialog.add(" test.");
		
		gameController.setdialog(test_dialog);
		
		assertEquals("This is a ;pretty simple ; test.", gameController.getdisplay_text());
	}
	
	@Test
	public void testsetQuests(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<Quest> quests = new ArrayList<>();
		gameController.setquests(quests);
		
		assertEquals(quests, gameController.getquests());
	}
	
	@Test
	public void testResetQuests(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<Quest> quests = new ArrayList<>();
		gameController.setquests(quests);
		
		assertEquals(quests, gameController.getquests());
		
		ArrayList<Quest> quests2 = new ArrayList<>();
		quests2.add(new Quest());
		gameController.setquests(quests2);
		
		assertEquals(quests2, gameController.getquests());
	}
	
	@Test
	public void testgetMapTile_LongDescription(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		player.setLocationID(0);
		chars.add(player);
		gameController.setcharacters(chars);
		
		GameMap map = new GameMap();
		MapTile starting = new MapTile();
		starting.setName("Derp");
		starting.setLongDescription("Just a long description here. Nothing more.");
		map.addMapTile(starting);
		gameController.setmap(map);
		
		assertEquals(starting.getLongDescription(), gameController.getmapTile_longDescription());
	}
	
	@Test
	public void testgetMapTile_Name(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		player.setLocationID(0);
		chars.add(player);
		gameController.setcharacters(chars);
		
		GameMap map = new GameMap();
		MapTile starting = new MapTile();
		starting.setName("Derp");
		starting.setLongDescription("Just a long description here. Nothing more.");
		map.addMapTile(starting);
		gameController.setmap(map);
		
		assertEquals(starting.getName(), gameController.getmapTile_name());
	}
	
	@Test
	public void testsetBattle(){
		Game game = new Game();
		GameController gameController = new GameController(game);
		
		ArrayList<Character> chars = new ArrayList<>();
		Player player = new Player();
		player.setLocationID(1);
		chars.add(player);
		gameController.setcharacters(chars);
		
		CombatSituation sitch = new CombatSituation(gameController, 1, 0);
		gameController.setBattle(sitch);
		
		assertEquals(sitch, gameController.getBattle());
		
		// Do it again (for weird adding)
		CombatSituation burp = new CombatSituation(gameController, 2, 0);
		gameController.setBattle(burp);
		
		assertEquals(burp, gameController.getBattle());
	}
}
