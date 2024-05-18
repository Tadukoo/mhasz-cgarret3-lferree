package com.github.tadukoo.middle_earth.model;

import java.util.ArrayList;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.middle_earth.controller.GameController;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.FakeDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombatSituationTest{
	private GameController gameController;
	private Player player;
	private CombatSituation battle;
	
	@BeforeEach
	public void setup(){
		DatabaseProvider.setInstance(new FakeDatabase());
		// Create Game
		Game game = new Game();
		gameController = new GameController(game);
		
		// Create Player
		player = new Player();
		player.setLocationID(1);
		player.setName("Me");
		player.setHP(100);
		player.setAttack(40);
		player.setDefense(5);
		
		// Add Player to Game
		ArrayList<Character> characters = new ArrayList<>();
		characters.add(player);
		gameController.setcharacters(characters);
		
		// Create CombatSituation
		battle = new CombatSituation(gameController, 1, 0);
	}
	
	@Test
	public void testConstructor(){
		// Check that 2 Characters are involved in the CombatSituation
		assertEquals(2, battle.getCharacterIDs().size());
		
		// Check that Player is present
		assertEquals(player, gameController.getcharacters().get(battle.getCharacterIDs().get(0)));
		
		// Check that dialog was updated appropriately
		assertEquals(1, gameController.getdialog().size());
		assertEquals("Me is staring into the eyes of a " + gameController.getcharacters().get(battle.getCharacterIDs().get(1)).getRace(),
				gameController.getdialog().get(0));
    
		// Check that done is false
		assertFalse(battle.isDone());
	}
	
	@Test
	public void testCreateEnemy(){
		// Create races list
		ArrayList<String> races = new ArrayList<>();
		races.add("Goblin");
		
		// Create the Enemy on mapTile (playerLocation) 4
		Enemy enemy = battle.createEnemy(races, 4);
		System.out.println(enemy.getAttack() + "  " + enemy.getDefense() +  " " + enemy.getHP() + " " + enemy.getLevel() + " " + enemy.getName() + " " + enemy.getRace());
		// Check that Stats and Stuff are correct (based on current setup)
		assertEquals(16, enemy.getAttack());
		assertEquals(0, enemy.getDefense());
		assertEquals(50, enemy.getHP());
		assertEquals(1, enemy.getLevel());
		assertNotNull(enemy.getName());
		assertEquals("Goblin", enemy.getRace());
	}
	
	@Test
	public void testCalculateAttackEnemy(){
		int mins = 0;
		int mids = 0;
		int maxs = 0;
		
		for(int i = 0; i < 300; i++){
			int result = battle.calculateAttack(gameController, 1);
			if(result == 14 || result == 15){
				mins++;
			}else if(result == 16){
				mids++;
			}else if(result == 18 || result == 17){
				maxs++;
			}else{
				// Should never get here!
				assertEquals(1, 0);
			}
		}
		
		System.out.println("CalculateAttack for Enemy Distribution");
		System.out.println("Min's(14/15):  " + mins);
		System.out.println("Mid(16):  " + mids);
		System.out.println("Max's(17/18):  " + maxs);
	}
	
	@Test
	public void testCalculateAttackPlayerNoArmor(){
		// Min and Max based off of 40 as base player attack
		int min = 36;
		int max = 44;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerHelmet(){
		// Create Helmet
		Item helmet = new Item();
		helmet.setType(ItemType.HELM);
		helmet.setName("Generic Helmet");
		helmet.setAttackBonus(10);
		
		// Give the Player the Helmet
		player.setHelm(helmet);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerBraces(){
		// Create Braces
		Item braces = new Item();
		braces.setType(ItemType.BRACES);
		braces.setName("Generic Braces");
		braces.setAttackBonus(10);
		
		// Give the Player the Braces
		player.setBraces(braces);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerChest(){
		// Create Chest
		Item chest = new Item();
		chest.setType(ItemType.CHEST);
		chest.setName("Generic Chest");
		chest.setAttackBonus(10);
		
		// Give the Player the Chest
		player.setChest(chest);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerLegs(){
		// Create Legs
		Item legs = new Item();
		legs.setType(ItemType.LEGS);
		legs.setName("Generic Legs");
		legs.setAttackBonus(10);
		
		// Give the Player the Legs
		player.setLegs(legs);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerBoots(){
		// Create Boots
		Item boots = new Item();
		boots.setType(ItemType.BOOTS);
		boots.setName("Generic Boots");
		boots.setAttackBonus(10);
		
		// Give the Player the Boots
		player.setBoots(boots);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerLHand(){
		// Create Shield
		Item shield = new Item();
		shield.setType(ItemType.L_HAND);
		shield.setName("Generic Shield");
		shield.setAttackBonus(10);
		
		// Give the Player the Shield
		player.setLeftHand(shield);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerRHand(){
		// Create Sword
		Item sword = new Item();
		sword.setType(ItemType.R_HAND);
		sword.setName("Generic Sword");
		sword.setAttackBonus(10);
		
		// Give the Player the Sword
		player.setRightHand(sword);
		
		// These based off of 50 as base attack (40 + 10)
		int min = 45;
		int max = 55;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateAttackPlayerFullArmorAndHands(){
		// Create Helmet
		Item helmet = new Item();
		helmet.setType(ItemType.HELM);
		helmet.setName("Generic Helmet");
		helmet.setAttackBonus(10);
		
		// Give the Player the Helmet
		player.setHelm(helmet);
		
		// Create Braces
		Item braces = new Item();
		braces.setType(ItemType.BRACES);
		braces.setName("Generic Braces");
		braces.setAttackBonus(10);
		
		// Give the Player the Braces
		player.setBraces(braces);
		
		// Create Chest
		Item chest = new Item();
		chest.setType(ItemType.CHEST);
		chest.setName("Generic Chest");
		chest.setAttackBonus(10);
		
		// Give the Player the Chest
		player.setChest(chest);
		
		// Create Legs
		Item legs = new Item();
		legs.setType(ItemType.LEGS);
		legs.setName("Generic Legs");
		legs.setAttackBonus(10);
		
		// Give the Player the Legs
		player.setLegs(legs);
		
		// Create Boots
		Item boots = new Item();
		boots.setType(ItemType.BOOTS);
		boots.setName("Generic Boots");
		boots.setAttackBonus(10);
		
		// Give the Player the Boots
		player.setBoots(boots);
		
		// Create Shield
		Item shield = new Item();
		shield.setType(ItemType.L_HAND);
		shield.setName("Generic Shield");
		shield.setAttackBonus(10);
		
		// Give the Player the Shield
		player.setLeftHand(shield);

		// Create Sword
		Item sword = new Item();
		sword.setType(ItemType.R_HAND);
		sword.setName("Generic Sword");
		sword.setAttackBonus(10);
		
		// Give the Player the Sword
		player.setRightHand(sword);
		
		// These based off of 110 as base attack (40 + 10*7 pieces)
		int min = 99;
		int max = 121;
		
		// Get attack calculated and ensure it falls inside range
		int attack = battle.calculateAttack(gameController, 0);
		assertTrue(attack >= min && attack <= max);
	}
	
	@Test
	public void testCalculateDefenseEnemy(){
		// Default Enemy's defense is 0
		assertEquals(0, battle.calculateDefense(gameController, 1));
	}
	
	@Test
	public void testCalculateDefensePlayerNoArmor(){
		// Default Player's defense is 5
		assertEquals(5, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithHelmet(){
		// Create Helmet
		Item helmet = new Item();
		helmet.setType(ItemType.HELM);
		helmet.setName("Generic Helmet");
		helmet.setDefenseBonus(10);
		
		// Give Helmet to Player
		player.setHelm(helmet);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithBraces(){
		// Create Braces
		Item braces = new Item();
		braces.setType(ItemType.BRACES);
		braces.setName("Generic Braces");
		braces.setDefenseBonus(10);
		
		// Give Braces to Player
		player.setBraces(braces);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithChest(){
		// Create Chest
		Item chest = new Item();
		chest.setType(ItemType.CHEST);
		chest.setName("Generic Chest");
		chest.setDefenseBonus(10);
		
		// Give Chest to Player
		player.setChest(chest);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithLegs(){
		// Create Legs
		Item legs = new Item();
		legs.setType(ItemType.LEGS);
		legs.setName("Generic Legs");
		legs.setDefenseBonus(10);
		
		// Give Legs to Player
		player.setLegs(legs);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithBoots(){
		// Create Boots
		Item boots = new Item();
		boots.setType(ItemType.BOOTS);
		boots.setName("Generic Boots");
		boots.setDefenseBonus(10);
		
		// Give Boots to Player
		player.setBoots(boots);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithShield(){
		// Create Shield
		Item shield = new Item();
		shield.setType(ItemType.L_HAND);
		shield.setName("Generic Shield");
		shield.setDefenseBonus(10);
		
		// Give Shield to Player
		player.setLeftHand(shield);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerWithSword(){
		// Create Sword
		Item sword = new Item();
		sword.setType(ItemType.R_HAND);
		sword.setName("Generic Sword");
		sword.setDefenseBonus(10);
		
		// Give Sword to Player
		player.setRightHand(sword);
		
		// Check that defense is 15
		assertEquals(15, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testCalculateDefensePlayerFullArmor(){
		// Create Helmet
		Item helmet = new Item();
		helmet.setType(ItemType.HELM);
		helmet.setName("Generic Helmet");
		helmet.setDefenseBonus(10);
		
		// Give Helmet to Player
		player.setHelm(helmet);
		
		// Create Braces
		Item braces = new Item();
		braces.setType(ItemType.BRACES);
		braces.setName("Generic Braces");
		braces.setDefenseBonus(10);
		
		// Give Braces to Player
		player.setBraces(braces);
		
		// Create Chest
		Item chest = new Item();
		chest.setType(ItemType.CHEST);
		chest.setName("Generic Chest");
		chest.setDefenseBonus(10);
		
		// Give Chest to Player
		player.setChest(chest);
		
		// Create Legs
		Item legs = new Item();
		legs.setType(ItemType.LEGS);
		legs.setName("Generic Legs");
		legs.setDefenseBonus(10);
		
		// Give Legs to Player
		player.setLegs(legs);
		
		// Create Boots
		Item boots = new Item();
		boots.setType(ItemType.BOOTS);
		boots.setName("Generic Boots");
		boots.setDefenseBonus(10);
		
		// Give Boots to Player
		player.setBoots(boots);
		
		// Create Shield
		Item shield = new Item();
		shield.setType(ItemType.L_HAND);
		shield.setName("Generic Shield");
		shield.setDefenseBonus(10);
		
		// Give Shield to Player
		player.setLeftHand(shield);

		// Create Sword
		Item sword = new Item();
		sword.setType(ItemType.R_HAND);
		sword.setName("Generic Sword");
		sword.setDefenseBonus(10);
		
		// Give Helmet to Player
		player.setRightHand(sword);
		
		// Check that defense is 75
		assertEquals(75, battle.calculateDefense(gameController, 0));
	}
	
	@Test
	public void testDoPlayerWon(){
		// Set Bob's HP to 0 (to make sure combat will be done at the end)
		gameController.getcharacters().get(1).setHP(0);
		
		// Check that Player has 0 experience (to confirm 10 was added later)
		assertEquals(0, player.getExperience());
		
		// Run doPlayerWon
		battle.doPlayerWon(gameController, 0, 1);
		
		// Check that dialog was added to appropriately
		assertEquals(5, gameController.getdialog().size());
		assertEquals("You killed " + gameController.getcharacters().get(battle.getCharacterIDs().get(1)).getName() + "!",
				gameController.getdialog().get(1));
		assertEquals("You have been awarded 300 experience!", gameController.getdialog().get(2));
		// Skip getting items line
		assertEquals("You have killed everyone! (in this combat situation here)", gameController.getdialog().get(4));
		
		// Check that Player got 300 experience (levels work it out to 150)
		assertEquals(150, player.getExperience());
		
		// Check that Battle is Done
		assertTrue(battle.isDone());
	}
	
	@Test
	public void testDoPlayerDied(){
		// Run doPlayerDied
		battle.doPlayerDied(gameController, 0);
		
		// Check that dialog was added to appropriately
		assertEquals(3, gameController.getdialog().size());
		assertEquals("You have died!", gameController.getdialog().get(1));
		assertEquals("Restart if you think you can do better!", gameController.getdialog().get(2));
		
		// Check that Battle is Done
		assertTrue(battle.isDone());
	}
	
	@Test
	public void testCalculateDamageEnemyToPlayer(){
		// Enemy can do 9-12, considering Player has 5 defense
		int min = 9;
		int max = 12;
		
		// Get damage calculated
		int damage = battle.calculateDamage(gameController, 1, 0);
		
		// Check that damage is calculated properly
		assertTrue(damage >= min && damage <= max);
	}
	
	@Test
	public void testCalculateDamagePlayerToEnemy(){
		// Player can do 36-44, Enemy has 0 defense
		int min = 36;
		int max = 44;
		
		// Get damage calculated
		int damage = battle.calculateDamage(gameController, 0, 1);
		
		// Check that damage is calculated properly
		assertTrue(damage >= min && damage <= max);
	}
	
	@Test
	public void testCalculateDamageOfLessThan0(){
		// Set Player defense to 20
		player.setDefense(20);
		
		// Enemy can do 13-17, so 20 defense = 0 damage
		assertEquals(0, battle.calculateDamage(gameController, 1, 0));
	}
}
