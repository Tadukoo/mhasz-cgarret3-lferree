package com.github.tadukoo.middle_earth.controller;

import com.github.tadukoo.aome.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameModeChangeTest{
	private GameController gameController;
	
	@BeforeEach
	public void setup(){
		Game game = new Game();
		gameController = new GameController(game);
		gameController.setmode("game");
	}
	
	@Test
	public void testModeChangeNullCommand(){
		assertFalse(gameController.mode_change(null));
	}
	
	@Test
	public void testModeChangeEmptyCommand(){
		assertFalse(gameController.mode_change(""));
	}
	
	@Test
	public void testModeChangeGameCommand(){
		gameController.setmode("inventory");
		assertTrue(gameController.mode_change("game"));
		
		assertEquals("game", gameController.getmode());
	}
	
	@Test
	public void testModeChangeInventoryCommand(){
		assertTrue(gameController.mode_change("inventory"));
		
		assertEquals("inventory", gameController.getmode());
	}
	
	@Test
	public void testModeChangeMapCommand(){
		assertTrue(gameController.mode_change("map"));
		
		assertEquals("map", gameController.getmode());
	}
	
	@Test
	public void testModeChangeCharacterCommand(){
		assertTrue(gameController.mode_change("character"));
		
		assertEquals("character", gameController.getmode());
	}
}
