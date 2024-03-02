package com.github.tadukoo.aome.construct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameObjectTest{
	private GameObject object;
	
	@BeforeEach
	public void setup(){
		object = new GameObject();
	}
	
	@Test
	public void testSetCommandResponses(){
		// Create a HashMap
		HashMap<String, String> responses = new HashMap<>();
		responses.put("open", "what'd you say open?");
		
		// Set it in object
		object.setCommandResponses(responses);
		
		// Check it's right
		assertEquals("what'd you say open?", object.getCommandResponses().get("open"));
		
		// Make another HashMap
		HashMap<String, String> responses2 = new HashMap<>();
		responses2.put("climb", "Climb? Are you crazy?");
		
		// Reset it in object (to ensure no crazy stuff)
		object.setCommandResponses(responses2);
		
		// Check it gucci
		assertEquals("Climb? Are you crazy?", object.getCommandResponses().get("climb"));
		assertNull(object.getCommandResponses().get("open"));
	}
	
	@Test
	public void testAddItem(){
		Item sword = new Item();
		sword.setName("Sword");
		sword.setType(ItemType.R_HAND);
		Item helmet = new Item();
		helmet.setName("Helmet");
		helmet.setType(ItemType.HELM);
		
		assertEquals(0, object.getItems().size());
		
		object.addItem(sword);
		assertEquals(1, object.getItems().size());
		assertEquals(sword, object.getItems().get(0));
		
		object.addItem(helmet);
		assertEquals(2, object.getItems().size());
		assertEquals(sword, object.getItems().get(0));
		assertEquals(helmet, object.getItems().get(1));
	}
	
	@Test
	public void testSetItems(){
		ArrayList<Item> items = new ArrayList<>();
		Item derp = new Item();
		derp.setName("Derpster");
		items.add(derp);
		
		object.setItems(items);
		
		assertEquals(1, object.getItems().size());
		assertEquals(derp, object.getItems().get(0));
	}
	
	@Test
	public void testResetItems(){
		ArrayList<Item> items = new ArrayList<>();
		Item derp = new Item();
		derp.setName("Derpster");
		items.add(derp);
		
		object.setItems(items);
		
		assertEquals(1, object.getItems().size());
		assertEquals(derp, object.getItems().get(0));
		
		ArrayList<Item> items2 = new ArrayList<>();
		Item somethingElse = new Item();
		somethingElse.setName("This is different from the other");
		items2.add(somethingElse);
		
		object.setItems(items2);
		
		assertEquals(1, object.getItems().size());
		assertEquals(somethingElse, object.getItems().get(0));
	}
}
