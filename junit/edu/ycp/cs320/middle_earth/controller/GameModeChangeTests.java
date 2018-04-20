package edu.ycp.cs320.middle_earth.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameModeChangeTests{
	private Game game;
	
	@Before
	public void setup(){
		game = new Game();
		game.set_mode("game");
	}
	
	/*
	 * Check null command
	 */
	@Test
	public void testNullCommand(){
		// Check that it simply returns false
		assertFalse(game.mode_change(null));
	}
	
	/*
	 * Check Inventory Command
	 */
	@Test
	public void testCheckInventoryCommand(){
		// Change mode
		assertTrue(game.mode_change("inventory"));
		
		// Check that mode was changed
		assertEquals("inventory", game.get_mode());
	}
	
	@Test
	public void testCheckInventoryCommandWEiRdCAsE(){
		// Change mode
		assertTrue(game.mode_change("iNVenTOrY"));
		
		// Check that mode was changed
		assertEquals("inventory", game.get_mode());
	}
	
	/*
	 * Check Character Sheet Command
	 */
	@Test
	public void testCheckCharacterSheetCommand(){
		// Change mode
		assertTrue(game.mode_change("character"));
		
		// Check that mode was changed
		assertEquals("character", game.get_mode());
	}
	
	@Test
	public void testCheckCharacterSheetCommandWeirdCase(){
		// Change mode
		assertTrue(game.mode_change("ChARActEr"));
		
		// Check that mode was changed
		assertEquals("character", game.get_mode());
	}
	
	/*
	 * Check Map Command
	 */
	@Test
	public void testMapCommand(){
		// Change mode
		assertTrue(game.mode_change("map"));
		
		// Check that mode was changed
		assertEquals("map", game.get_mode());
	}
	
	@Test
	public void testMapCommandWEirDcASe(){
		// Change mode
		assertTrue(game.mode_change("mAP"));
		
		// Check that mode was changed
		assertEquals("map", game.get_mode());
	}
	
	/*
	 * Back to Game Command
	 */
	@Test
	public void testGameCommand(){
		// Set mode to character
		game.set_mode("character");
		
		// Run command
		assertTrue(game.mode_change("game"));
		
		// Check that mode was changed
		assertEquals("game", game.get_mode());
	}
	
	@Test
	public void testGameCommandWEiRdCAsE(){
		// Set mode to character
		game.set_mode("character");
		
		// Run command
		assertTrue(game.mode_change("gAmE"));
		
		// Check that mode was changed
		assertEquals("game", game.get_mode());
	}
	
	/*
	 * Non-Command
	 */
	@Test
	public void testNonCommand(){
		// Check that it simply returns false
		assertFalse(game.mode_change("Derptiy doodly"));
	}
	
	@Test
	public void testEmptyCommand(){
		// Check that it simply returns false
		assertFalse(game.mode_change(""));
	}
}
