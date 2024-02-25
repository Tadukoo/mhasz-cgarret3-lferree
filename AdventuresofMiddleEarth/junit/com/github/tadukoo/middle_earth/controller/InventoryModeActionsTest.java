package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;

import com.github.tadukoo.middle_earth.model.Characters.Character;
import com.github.tadukoo.middle_earth.model.Characters.Inventory;
import com.github.tadukoo.middle_earth.model.Characters.Player;
import com.github.tadukoo.middle_earth.model.Constructs.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryModeActionsTest{
	private Game game;
	private Player player;
	private Item sword;
	private Item helmet;
	private Item key;
	
	@BeforeEach
	public void setup(){
		game = new Game();
		player = new Player();
		
		// Populate Player's inventory
		ArrayList<Item> playerItems = new ArrayList<>();
		sword = new Item();
		sword.setName("Sword");
		sword.setLongDescription("A Long sword. Probably stolen from a giant golem or something.");
		sword.setItemWeight((float) 9.6);
		sword.setIsQuestItem(false);
		helmet = new Item();
		helmet.setName("Helmet");
		helmet.setLongDescription("A helmet forged in the hot, hot fires of Mordor.");
		helmet.setItemWeight((float) 29.3);
		helmet.setIsQuestItem(false);
		key = new Item();
		key.setName("Key");
		key.setLongDescription("A key to treasure too expensive to buy with Bill Gates' salary. (Believe it)");
		key.setItemWeight((float) 93.1);
		key.setIsQuestItem(true);
		playerItems.add(sword);
		playerItems.add(helmet);
		playerItems.add(key);
		Inventory inventory = new Inventory();
		inventory.setitems(playerItems);
		player.setinventory(inventory);
		
		// Setup Character Array
		ArrayList<Character> chars = new ArrayList<>();
		chars.add(player);
		
		// Put Player into Game
		game.setcharacters(chars);
	}
	
	/*
	 * Item Details
	 * Note: handle_command checks that the number is valid and such
	 */
	@Test
	public void testItemDetailsLowEndOf0(){
		// Run and get response
		String response = game.item_details(0);
		
		// Set item to sword for easier stuff
		Item item = sword;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getItemWeight() + 
				";Quest item: " + item.getIsQuestItem(), response);
	}
	
	@Test
	public void testItemDetailsMidIndexOf1(){
		// Run and get response
		String response = game.item_details(1);
		
		// Set item to helmet for easiness
		Item item = helmet;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getItemWeight() + 
				";Quest item: " + item.getIsQuestItem(), response);
	}
	
	@Test
	public void testItemDetailsHighEndOf2(){
		// Run and get response
		String response = game.item_details(2);
		
		// Set item to key for easy sauce
		Item item = key;
		
		// Check that response is correct
		assertEquals(item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getItemWeight() + 
				";Quest item: " + item.getIsQuestItem(), response);
	}
}
