package com.github.tadukoo.middle_earth.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.NPC;
import com.github.tadukoo.aome.construct.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuestTest{
	private Quest quest;
	
	@BeforeEach
	public void setup(){
		quest = new Quest();
	}
	
	@Test
	public void testSetRewardItems(){
		ArrayList<Item> items = new ArrayList<>();
		Item stick = new Item();
		stick.setName("Stick");
		Item chestplate = new Item();
		chestplate.setName("Chestplate");
		items.add(stick);
		items.add(chestplate);
		
		quest.setRewardItems(items);
		
		assertEquals(2, quest.getRewardItems().size());
		assertEquals(stick, quest.getRewardItems().get(0));
		assertEquals(chestplate, quest.getRewardItems().get(1));
	}
	
	@Test
	public void testResetRewardItems(){
		ArrayList<Item> items = new ArrayList<>();
		Item stick = new Item();
		stick.setName("Stick");
		Item chestplate = new Item();
		chestplate.setName("Chestplate");
		items.add(stick);
		items.add(chestplate);
		
		quest.setRewardItems(items);
		
		assertEquals(2, quest.getRewardItems().size());
		assertEquals(stick, quest.getRewardItems().get(0));
		assertEquals(chestplate, quest.getRewardItems().get(1));
		
		ArrayList<Item> items2 = new ArrayList<>();
		Item sword = new Item();
		sword.setName("Sword");
		Item helmet = new Item();
		helmet.setName("Helmet");
		items2.add(sword);
		items2.add(helmet);
		
		quest.setRewardItems(items2);
		
		assertEquals(2, quest.getRewardItems().size());
		assertEquals(sword, quest.getRewardItems().get(0));
		assertEquals(helmet, quest.getRewardItems().get(1));
	}
	
	@Test
	public void testSetRewardCoins(){
		quest.setRewardCoins(100);
		
		assertEquals(100, quest.getRewardCoins());
	}
	
	@Test
	public void testResetRewardCoins(){
		quest.setRewardCoins(100);
		
		assertEquals(100, quest.getRewardCoins());
		
		quest.setRewardCoins(2938);
		
		assertEquals(2938, quest.getRewardCoins());
	}
	
	@Test
	public void testSetDialogue(){
		HashMap<String, NPC> dialogue = new HashMap<>();
		NPC npc1 = new NPC();
		npc1.setname("Derpkins");
		dialogue.put("My name is Derpkins.", npc1);
		
		quest.setDialogue(dialogue);
		
		assertEquals(npc1, quest.getDialogue().get("My name is Derpkins."));
	}
	
	@Test
	public void testResetDialogue(){
		HashMap<String, NPC> dialogue = new HashMap<>();
		NPC npc1 = new NPC();
		npc1.setname("Derpkins");
		dialogue.put("My name is Derpkins.", npc1);
		
		quest.setDialogue(dialogue);
		
		assertEquals(npc1, quest.getDialogue().get("My name is Derpkins."));
		
		HashMap<String, NPC> dialogue2 = new HashMap<>();
		NPC npc2 = new NPC();
		npc2.setname("Derpy Derp");
		dialogue2.put("Derp derp derp.", npc2);
		NPC npc3 = new NPC();
		npc3.setname("No Name");
		dialogue2.put("Invalid NPC", npc3);
		
		quest.setDialogue(dialogue2);
		
		assertNull(quest.getDialogue().get("My name is Derpkins."));
		assertEquals(npc2, quest.getDialogue().get("Derp derp derp."));
		assertEquals(npc3, quest.getDialogue().get("Invalid NPC"));
	}
}
