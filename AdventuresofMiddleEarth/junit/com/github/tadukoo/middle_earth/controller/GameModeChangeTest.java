package com.github.tadukoo.middle_earth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameModeChangeTest{
	private Game game;
	
	@BeforeEach
	public void setup(){
		game = new Game();
		game.setmode("game");
	}
	
	@Test
	public void testModeChangeNullCommand(){
		assertFalse(game.mode_change(null));
	}
	
	@Test
	public void testModeChangeEmptyCommand(){
		assertFalse(game.mode_change(""));
	}
	
	@Test
	public void testModeChangeGameCommand(){
		game.setmode("inventory");
		assertTrue(game.mode_change("game"));
		
		assertEquals("game", game.getmode());
	}
	
	@Test
	public void testModeChangeInventoryCommand(){
		assertTrue(game.mode_change("inventory"));
		
		assertEquals("inventory", game.getmode());
	}
	
	@Test
	public void testModeChangeMapCommand(){
		assertTrue(game.mode_change("map"));
		
		assertEquals("map", game.getmode());
	}
	
	@Test
	public void testModeChangeCharacterCommand(){
		assertTrue(game.mode_change("character"));
		
		assertEquals("character", game.getmode());
	}
}
