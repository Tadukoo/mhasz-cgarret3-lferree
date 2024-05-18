package com.github.tadukoo.aome.character;

import java.util.ArrayList;

import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CharacterTest{
	private Character character;
	private static class Derp extends Character{
		@Override
		public String getTableName(){
			return null;
		}
	}
	
	@BeforeEach
	public void setup(){
		character = new Derp();
	}
	
	@Test
	public void testSetRace(){
		// Set that race
		character.setRace("Manticorn");
		
		// Check that race (Manticorn privilege, anyone?)
		assertEquals("Manticorn", character.getRace());
		
		// Reset race in case of crazy adding
		character.setRace("Monkey");
		
		// Check it again (Trans-Ethnic?)
		assertEquals("Monkey", character.getRace());
	}
	
	@Test
	public void testSetName(){
		// Set my name
		character.setName("Tadukoo");
		
		// Check my name
		assertEquals("Tadukoo", character.getName());
		
		// Then Matt
		character.setName("mhasz");
		
		// Is Matt
		assertEquals("mhasz", character.getName());
	}
	
	@Test
	public void testSetGender(){
		// The fairer gender
		character.setGender("Female");
		
		// Yep, she's got the right parts
		assertEquals("Female", character.getGender());
		
		// The other gender
		character.setGender("Male");
		
		// I don't wanna check that
		assertEquals("Male", character.getGender());
	}
	
	@Test
	public void testSetLevel(){
		// Set my level super high!
		character.setLevel(10293);
		
		// It's over 9000!
		assertEquals(10293, character.getLevel());
		
		// Check again!
		character.setLevel(293);
		
		// Guess it's not off the scales after all...
		assertEquals(293, character.getLevel());
	}
	
	@Test
	public void testSetHit_Points(){
		// Set those hps
		character.setHP(10293);
		
		// Check them hps
		assertEquals(10293, character.getHP());
		
		/// Set the health
		character.setHP(293);
		
		// Check the health
		assertEquals(293, character.getHP());
	}
	
	@Test
	public void testSetMagic_Points(){
		// Set them mps
		character.setMP(10293);
		
		// Check those mps
		assertEquals(10293, character.getMP());
		
		// Set that magic
		character.setMP(293);
		
		// Check that magic
		assertEquals(293, character.getMP());
	}
	
	@Test
	public void testSetAttack(){
		// Set that att
		character.setAttack(10293);
		
		// Check that att
		assertEquals(10293, character.getAttack());
		
		// I'm sensing a pattern
		character.setAttack(293);
		
		// Is it a pattern?
		assertEquals(293, character.getAttack());
	}
	
	@Test
	public void testSetDefense(){
		// Set that def
		character.setDefense(10293);
		
		// Check that def
		assertEquals(10293, character.getDefense());
		
		// These numbers...
		character.setDefense(293);
		
		// They seem familiar...
		assertEquals(293, character.getDefense());
	}
	
	@Test
	public void testSetSpecial_Attack(){
		// Set that spatt
		character.setSpecialAttack(10293);
		
		// Check that spatt
		assertEquals(10293, character.getSpecialAttack());
		
		// No really
		character.setSpecialAttack(293);
		
		// They're the same numbers
		assertEquals(293, character.getSpecialAttack());
	}
	
	@Test
	public void testSetSpecial_Defense(){
		// Set that spdef
		character.setSpecialDefense(10293);
		
		// Check that spdef
		assertEquals(10293, character.getSpecialDefense());
		
		// Why would anyone be
		character.setSpecialDefense(293);
		
		// That lazy?
		assertEquals(293, character.getSpecialDefense());
	}
	
	@Test
	public void testSetCoins(){
		// Set that coinage
		character.setCoins(10293);
		
		// Cheque that coinage
		assertEquals(10293, character.getCoins());
		
		// It's much simpler
		character.setCoins(293);
		
		// to do it
		assertEquals(293, character.getCoins());
	}
	
	@Test
	public void testSetLocation(){
		// Set that locashe
		character.setLocationID(10293);
		
		// Check that locashe
		assertEquals(10293, character.getLocationID());
		
		// this
		character.setLocationID(293);
		
		// way
		assertEquals(293, character.getLocationID());
	}
	
	@Test
	public void testSetInventory(){
		// Setup an Inventory
		Item derp = new Item();
		derp.setName("Derpy Derp");
		ArrayList<Item> items = new ArrayList<>();
		items.add(derp);
		
		// Set it to the Character
		character.setInventory(items);
		
		// Ensure it's right
		assertEquals(items, character.getInventory());
		
		// Setup another Inventory
		Item derp2 = new Item();
		derp2.setName("Not the Same Derp");
		ArrayList<Item> items2 = new ArrayList<>();
		items2.add(derp2);
		
		// reset the character's inventory to it
		character.setInventory(items2);
		
		// Ensure it's again right
		assertEquals(items2, character.getInventory());
	}
	
	@Test
	public void testSetHelm(){
		// Create Helmet
		Item helmet = new Item();
		helmet.setType(ItemType.HELM);
		helmet.setName("Iron Helm");
		
		// Set the Helmet
		character.setHelm(helmet);
		
		// Ensure it's right
		assertEquals(helmet, character.getHelm());
		
		// Create another Helmet
		Item helmet2 = new Item();
		helmet2.setType(ItemType.HELM);
		helmet2.setName("Golden Helmet");
		
		// Set the new helmet
		character.setHelm(helmet2);
		
		// Check it again
		assertEquals(helmet2, character.getHelm());
	}
	
	@Test
	public void testSetHelmNotHelm(){
		// Create fake helmet
		Item notHelmet = new Item();
		notHelmet.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setHelm(notHelmet);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetBraces(){
		// Create Braces
		Item braces = new Item();
		braces.setType(ItemType.BRACES);
		braces.setName("Iron Braces");
		
		// Set the Braces
		character.setBraces(braces);
		
		// Ensure it's right
		assertEquals(braces, character.getBraces());
		
		// Create another Braces
		Item braces2 = new Item();
		braces2.setType(ItemType.BRACES);
		braces2.setName("Golden Braces");
		
		// Set the new braces
		character.setBraces(braces2);
		
		// Check it again
		assertEquals(braces2, character.getBraces());
	}
	
	@Test
	public void testSetBracesNotBraces(){
		// Create fake braces
		Item notBraces = new Item();
		notBraces.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setBraces(notBraces);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetChest(){
		// Create Chest
		Item chest = new Item();
		chest.setType(ItemType.CHEST);
		chest.setName("Iron Chest");
		
		// Set the Chest
		character.setChest(chest);
		
		// Ensure it's right
		assertEquals(chest, character.getChest());
		
		// Create another Chest
		Item chest2 = new Item();
		chest2.setType(ItemType.CHEST);
		chest2.setName("Golden Chest");
		
		// Set the new chest
		character.setChest(chest2);
		
		// Check it again
		assertEquals(chest2, character.getChest());
	}
	
	@Test
	public void testSetChestNotChest(){
		// Create fake chest
		Item notChest = new Item();
		notChest.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setChest(notChest);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetLegs(){
		// Create Legs
		Item legs = new Item();
		legs.setType(ItemType.LEGS);
		legs.setName("Iron Legs");
		
		// Set the Legs
		character.setLegs(legs);
		
		// Ensure it's right
		assertEquals(legs, character.getLegs());
		
		// Create another Legs
		Item legs2 = new Item();
		legs2.setType(ItemType.LEGS);
		legs2.setName("Golden Legs");
		
		// Set the new legs
		character.setLegs(legs2);
		
		// Check it again
		assertEquals(legs2, character.getLegs());
	}
	
	@Test
	public void testSetLegsNotLegs(){
		// Create fake legs
		Item notLegs = new Item();
		notLegs.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setLegs(notLegs);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetBoots(){
		// Create Boots
		Item boots = new Item();
		boots.setType(ItemType.BOOTS);
		boots.setName("Iron Boots");
		
		// Set the Boots
		character.setBoots(boots);
		
		// Ensure it's right
		assertEquals(boots, character.getBoots());
		
		// Create another Boots
		Item boots2 = new Item();
		boots2.setType(ItemType.BOOTS);
		boots2.setName("Golden Boots");
		
		// Set the new boots
		character.setBoots(boots2);
		
		// Check it again
		assertEquals(boots2, character.getBoots());
	}
	
	@Test
	public void testSetBootsNotBoots(){
		// Create fake boots
		Item notBoots = new Item();
		notBoots.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setBoots(notBoots);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetL_Hand(){
		// Create hand
		Item hand = new Item();
		hand.setType(ItemType.L_HAND);
		hand.setName("Iron Shield");
		
		// Set the Hand
		character.setLeftHand(hand);
		
		// Ensure it's right
		assertEquals(hand, character.getLeftHand());
		
		// Create another hand
		Item hand2 = new Item();
		hand2.setType(ItemType.L_HAND);
		hand2.setName("Golden Shield");
		
		// Set the new hand
		character.setLeftHand(hand2);
		
		// Check it again
		assertEquals(hand2, character.getLeftHand());
	}
	
	@Test
	public void testSetL_HandNotHand(){
		// Create fake hand
		Item notHand = new Item();
		notHand.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setLeftHand(notHand);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
	
	@Test
	public void testSetR_Hand(){
		// Create hand
		Item hand = new Item();
		hand.setType(ItemType.R_HAND);
		hand.setName("Iron Sword");
		
		// Set the Hand
		character.setRightHand(hand);
		
		// Ensure it's right
		assertEquals(hand, character.getRightHand());
		
		// Create another hand
		Item hand2 = new Item();
		hand2.setType(ItemType.R_HAND);
		hand2.setName("Golden Sword");
		
		// Set the new hand
		character.setRightHand(hand2);
		
		// Check it again
		assertEquals(hand2, character.getRightHand());
	}
	
	@Test
	public void testSetR_HandNotHand(){
		// Create fake hand
		Item notHand = new Item();
		notHand.setType(ItemType.MISC);
		
		// Ensure an IllegalArgumentException
		try{
			character.setRightHand(notHand);
			fail();
		}catch(IllegalArgumentException e){
			// Expected
		}
	}
}
