package com.github.tadukoo.middle_earth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * These tests are solely for testing the Game-Based Actions (not specific to Characters) in Game. 
 * Other methods are tested in other classes.
 */
public class GameBasedActionsTest{
	private Game game;
	
	@BeforeEach
	public void setup(){
		game = new Game();
	}
	
	/*
	 * Check Character Sheet
	 */
	@Test
	public void testCheckCharacterSheetCommand(){
		game.check_character_sheet();
		
		assertEquals("character", game.getmode());
	}
	
	/* TODO: JUNIT: Remove? Note: Commented out these in Game.
	@Test
	public void testAlreadyInCharacter(){
		game.setmode("character");
		
		game.check_character_sheet();
		
		assertEquals("character", game.getmode());
		assertEquals(1, game.getdialog().size());
		assertEquals("You're already in it!", game.getdialog().get(0));
	}
	*/
	
	/*
	 * Check Inventory
	 */
	@Test
	public void testCheckInventoryCommand(){
		game.check_inventory();
		
		assertEquals("inventory", game.getmode());
	}
	
	/* TODO: JUNIT: Remove? Note: Commented out these in Game.
	@Test
	public void testAlreadyInInventory(){
		game.setmode("inventory");
		
		game.check_inventory();
		
		assertEquals("inventory", game.getmode());
		assertEquals(1, game.getdialog().size());
		assertEquals("You're already in it!", game.getdialog().get(0));
	}
	*/
	
	/*
	 * Check Map
	 */
	@Test
	public void testMapCommand(){
		game.check_map();
		
		assertEquals("map", game.getmode());
	}
	
	/* TODO: JUNIT: Remove? Note: Commented out these in Game.
	@Test
	public void testAlreadyInMap(){
		game.setmode("map");
		
		game.check_map();
		
		assertEquals("map", game.getmode());
		assertEquals(1, game.getdialog().size());
		assertEquals("You're already in it!", game.getdialog().get(0));
	}
	*/
	
	/*
	 * Return to Game
	 */
	@Test
	public void testBackToGameFromCharacterSheet(){
		game.setmode("character");
		
		game.return_to_game();
		
		assertEquals("game", game.getmode());
	}
	
	@Test
	public void testBackToGameFromInventory(){
		game.setmode("inventory");
		
		game.return_to_game();
		
		assertEquals("game", game.getmode());
	}
	
	@Test
	public void testBackToGameFromMap(){
		game.setmode("map");
		
		game.return_to_game();
		
		assertEquals("game", game.getmode());
	}
	
	/* TODO: JUNIT: Remove? Note: Commented out these in Game.
	@Test
	public void testAlreadyInGame(){
		game.setmode("game");
		
		game.return_to_game();
		
		assertEquals("game", game.getmode());
		assertEquals(1, game.getdialog().size());
		assertEquals("You're playing it!", game.getdialog().get(0));
	}
	*/
	
	/*
	 * Save
	 * TODO: JUNIT: Save Tests
	 */
}
