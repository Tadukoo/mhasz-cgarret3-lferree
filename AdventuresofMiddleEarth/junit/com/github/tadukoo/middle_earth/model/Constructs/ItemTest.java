package com.github.tadukoo.middle_earth.model.Constructs;

import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemTest{
	private Item item;
	
	@BeforeEach
	public void setup(){
		item = new Item();
	}
	
	@Test
	public void testSetWeight(){
		// Set the item weight
		float weight = (float) 5.6;
		item.setWeight(weight);
		
		// Ensure it was set correctly
		assertEquals(weight, item.getWeight(), 0.001);
		
		// Reset it to ensure it doesn't get added or anything crazy
		float newWeight = (float) 39.2;
		item.setWeight(newWeight);
		
		assertEquals(newWeight, item.getWeight(), 0.001);
	}
	
	@Test
	public void testSetIsQuestItem(){
		item.setQuestItem(true);
		
		assertTrue(item.isQuestItem());
		
		item.setQuestItem(true);
		
		assertTrue(item.isQuestItem());
		
		item.setQuestItem(false);
		
		assertFalse(item.isQuestItem());
		
		item.setQuestItem(false);
		
		assertFalse(item.isQuestItem());
	}
	
	@Test
	public void testSetDescriptionUpdate(){
		// Set the Description Update (Please tell me you knew that by reading the below line)
		item.setDescriptionUpdate("I'm a derp");
		
		// Ensure it was set correctly
		assertEquals("I'm a derp", item.getDescriptionUpdate());
		
		// Set it again (to ensure not adding to the string)
		item.setDescriptionUpdate("I'm also a derp of some sort.");
		
		// Ensure again it was set correctly
		assertEquals("I'm also a derp of some sort.", item.getDescriptionUpdate());
	}
	
	@Test
	public void testSetAttackBonus(){
		// Set the Attack Bonus
		item.setAttackBonus(5093);
		
		// Ensure it was set correctly
		assertEquals(5093, item.getAttackBonus());
		
		// Reset it (to ensure not being crazy and adding or anything)
		item.setAttackBonus(19284);
		
		// Ensure again correct
		assertEquals(19284, item.getAttackBonus());
	}
	
	@Test
	public void testSetDefenseBonus(){
		// Set the Defense Bonus
		item.setDefenseBonus(53);
		
		// Ensure it was set correctly
		assertEquals(53, item.getDefenseBonus());
		
		// Reset it (to ensure not being crazy and adding or anything)
		item.setDefenseBonus(283749);
		
		// Ensure again correct
		assertEquals(283749, item.getDefenseBonus());
	}
	
	@Test
	public void testSetHPBonus(){
		// Set the HP Bonus
		item.setHPBonus(3928);
		
		// Ensure it was set correctly
		assertEquals(3928, item.getHPBonus());
		
		// Reset it (to ensure not being crazy and adding or anything)
		item.setHPBonus(209);
		
		// Ensure again correct
		assertEquals(209, item.getHPBonus());
	}
	
	@Test
	public void testSetLvlRequirement(){
		// Set the Level Requirement
		item.setLevelRequirement(1000000);
		
		// Ensure it was set correctly
		assertEquals(1000000, item.getLevelRequirement());
		
		// Reset it (to ensure not being crazy and adding or anything)
		item.setLevelRequirement(1);
		
		// Ensure again correct
		assertEquals(1, item.getLevelRequirement());
	}
	
	@Test
	public void testSetType(){
		// Set the Type
		item.setType(ItemType.QUEST);
		
		// Check it's right
		assertEquals(ItemType.QUEST, item.getType());
	}
}
