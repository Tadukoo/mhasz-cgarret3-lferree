package com.github.tadukoo.middle_earth.controller;

import com.github.tadukoo.aome.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameModeChangeTests{
	private GameController gameController;
	
	@BeforeEach
	public void setup(){
		Game game = new Game();
		gameController = new GameController(game);
		gameController.setmode("game");
	}
	
	/*
	 * Check null command
	 */
	@Test
	public void testNullCommand(){
		// Check that it simply returns false
		assertFalse(gameController.mode_change(null));
	}
	
	/*
	 * Check Inventory Command
	 */
	@Test
	public void testCheckInventoryCommand(){
		// Change mode
		assertTrue(gameController.mode_change("inventory"));
		
		// Check that mode was changed
		assertEquals("inventory", gameController.getmode());
	}
	
	@Test
	public void testCheckInventoryCommandWEiRdCAsE(){
		// Change mode
		assertTrue(gameController.mode_change("iNVenTOrY"));
		
		// Check that mode was changed
		assertEquals("inventory", gameController.getmode());
	}
	
	/*
	 * Check Character Sheet Command
	 */
	@Test
	public void testCheckCharacterSheetCommand(){
		// Change mode
		assertTrue(gameController.mode_change("character"));
		
		// Check that mode was changed
		assertEquals("character", gameController.getmode());
	}
	
	@Test
	public void testCheckCharacterSheetCommandWeirdCase(){
		// Change mode
		assertTrue(gameController.mode_change("ChARActEr"));
		
		// Check that mode was changed
		assertEquals("character", gameController.getmode());
	}
	
	/*
	 * Check Map Command
	 */
	@Test
	public void testMapCommand(){
		// Change mode
		assertTrue(gameController.mode_change("map"));
		
		// Check that mode was changed
		assertEquals("map", gameController.getmode());
	}
	
	@Test
	public void testMapCommandWEirDcASe(){
		// Change mode
		assertTrue(gameController.mode_change("mAP"));
		
		// Check that mode was changed
		assertEquals("map", gameController.getmode());
	}
	
	/*
	 * Back to Game Command
	 */
	@Test
	public void testGameCommand(){
		// Set mode to character
		gameController.setmode("character");
		
		// Run command
		assertTrue(gameController.mode_change("game"));
		
		// Check that mode was changed
		assertEquals("game", gameController.getmode());
	}
	
	@Test
	public void testGameCommandWEiRdCAsE(){
		// Set mode to character
		gameController.setmode("character");
		
		// Run command
		assertTrue(gameController.mode_change("gAmE"));
		
		// Check that mode was changed
		assertEquals("game", gameController.getmode());
	}
	
	/*
	 * Non-Command
	 */
	@Test
	public void testNonCommand(){
		// Check that it simply returns false
		assertFalse(gameController.mode_change("Derptiy doodly"));
	}
	
	@Test
	public void testEmptyCommand(){
		// Check that it simply returns false
		assertFalse(gameController.mode_change(""));
	}
}
