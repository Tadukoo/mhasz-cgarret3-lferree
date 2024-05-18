package com.github.tadukoo.middle_earth.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.middle_earth.controller.GameController;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.character.Enemy;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.IDatabase;

public class CombatSituation{
	private ArrayList<Integer> characterIDs;
	private int currentIDsIndex;
	private Random random;
	private boolean done;
	
	/**
	 * @param game The Game this is happening in
	 * @param enemies How many Enemies in this CombatSituation
	 * @param players Any Players involved in the Combat.
	 */
	public CombatSituation(GameController game, int enemies, int ... players){
		setup(game, enemies, null, players);
	}
	
	/**
	 * @param game The Game this is happening in
	 * @param enemies How many Enemies in this CombatSituation
	 * @param races The possible races separated by commas (if more than 1 race)
	 * @param players Any Players involved in the Combat.
	 */
	public CombatSituation(GameController game, int enemies, String races, int ... players){
		setup(game, enemies, races, players);
	}
	
	// Actually does the work of the constructors
	public void setup(GameController game, int enemies, String races, int[] players){
		// Change game mode to combat
		game.setmode("combat");
		
		// Initialize combat string and character IDs ArrayList
		String combatString = "";
		characterIDs = new ArrayList<>();
		
		// Add player ids to ArrayList and names to combat string
		for(int i = 0; i < players.length; i++){
			characterIDs.add(players[i]);
			if(i == 0){
				combatString += game.getcharacters().get(i).getName();
			}else if(i != players.length - 1){
				combatString += ", " + game.getcharacters().get(i).getName();
			}else{
				combatString += " and " + game.getcharacters().get(i).getName();
			}
		}
		
		// Initialize Random
		random = new Random(System.nanoTime());
		
		// Convert races to ArrayList
		ArrayList<String> raceList = new ArrayList<String>();
		if(races != null){
			if(races.contains(",")){
				String[] raceSplit = races.split(",");
				for(String race: raceSplit){
					raceList.add(race);
				}
			}else{
				// Simply one race
				raceList.add(races);
			}
			// If null, do nothing (empty ArrayList)
		}
		
		// Figure out is/are based on how many players to add to combat string
		if(players.length > 1){
			combatString += " are";
		}else{
			combatString += " is";
		}
		
		// Add enemies to combat
		for(int i = 0; i < enemies; i++){
			Enemy enemy = createEnemy(raceList, game.getcharacters().get(0).getLocationID());
			// set enemy level to "areaDifficulty" of maptile == player location.
			// enemy.setlevel(game.map.get(player.getlocation).getAreaDifficulty);
	
			game.getcharacters().add(enemy);
			characterIDs.add(game.getcharacters().size() - 1);
			if(i == 0){
				combatString += " staring into the eyes of a " + enemy.getRace();
			}else if(i != enemies - 1){
				combatString += ", a " + enemy.getRace();
			}else{
				combatString += " and a " + enemy.getRace();
			}
		}
		
		// Add the combat string to Game's dialog
		game.add_dialog(combatString);
		
		// Set done to false
		done = false;
		
		// Initialize current ID index
		currentIDsIndex = 0;
	}
	
	public ArrayList<Integer> getCharacterIDs(){
		return characterIDs;
	}
	
	public int getCurrentIDsIndex(){
		return currentIDsIndex;
	}
	
	public Enemy createEnemy(List<String> races, int playerLocation){
		IDatabase db = DatabaseProvider.getInstance();
		
		// If races is empty, get all races from the database instead.
		if(races.isEmpty()){
			races = db.getAllEnemyRaces().result();
		}
		
		if(playerLocation == 7) {
			Enemy demon = db.getEnemyByRace("Greater Demon").result();
			demon.setName("Balrog");
			return demon;
		} else {
			// Return an enemy by a random race from the list
			if (races.size() == 1) {
				return db.getEnemyByRace(races.get(0)).result();
			} else {
				return db.getEnemyByRace(races.get(random.nextInt(races.size() - 1))).result();
			}
		}
	}
	
	public void playerAttackEnemy(GameController game, int playerIndex, String target){
		// Check that it's the player's turn
		if(characterIDs.get(characterIDs.get(currentIDsIndex)) == playerIndex){
			Character enemy = null;
			int enemyIndex = -1;
			for(int characterIndex: characterIDs){
				// Find the enemy the Player specified
				Character chara = game.getcharacters().get(characterIndex);
				if(characterIndex != playerIndex && (chara.getName().equalsIgnoreCase(target) ||
						chara.getRace().equalsIgnoreCase(target))){
					enemy = chara;
					enemyIndex = characterIndex;
				}
			}
			if(enemy == null){
				// Enemy not found
				game.add_dialog("No one by the name/race of " + target + " was found in combat with you!");
			}else{
				// Do attack against enemy
				int enemyHP = enemy.getHP();
				int damage = calculateDamage(game, playerIndex, enemyIndex);
				enemy.setHP(enemyHP - damage);
				game.add_dialog("You attacked " + enemy.getName() + " for " + damage + " damage.");
				
				// Check if the enemy is dead
				if(enemy.getHP() <= 0){
					enemy.setHP(0);
					doPlayerWon(game, playerIndex, enemyIndex);
				}
				
				// Advance to next turn and check if it's an enemy to do their turn.
				advanceTurn(game);
			}
		}else{
			// Not player's turn
			game.add_dialog("It's not your turn!");
		}
	}
	
	public void advanceTurn(GameController game){
		if(!done){
			// Advance index
			currentIDsIndex++;
			
			// Loop index if too high
			if(currentIDsIndex >= characterIDs.size()){
				currentIDsIndex = 0;
			}
			
			// Check if it's now a non-player turn (Enemy)
			if(!(game.getcharacters().get(characterIDs.get(currentIDsIndex)) instanceof Player)){
				// Do enemy turn
				enemyAttackPlayer(game);
			}
		}
	}
	
	public void enemyAttackPlayer(GameController game){
		// Get number of Players
		ArrayList<Integer> playerIndices = new ArrayList<Integer>();
		for(int characterIndex: characterIDs){
			if(game.getcharacters().get(characterIndex) instanceof Player){
				playerIndices.add(characterIndex);
			}
		}
		
		// Determine target Player
		int playerNum = random.nextInt(playerIndices.size());
		int playerIndex = playerIndices.get(playerNum);
		
		// Get player
		Character player = game.getcharacters().get(playerIndex);
		
		// Get Enemy
		int enemyIndex = characterIDs.get(currentIDsIndex);
		Character enemy = game.getcharacters().get(enemyIndex);
		
		// Do damage to player
		int playerHP = player.getHP();
		int damage = calculateDamage(game, characterIDs.get(currentIDsIndex), playerIndex);
		player.setHP(playerHP - damage);
		game.add_dialog(enemy.getName() + " attacked you for " + damage + " damage.");
		//game.add_dialog("You have " + player.gethit_points() + " HP left.");
		
		// Check if player has died
		if(player.getHP() <= 0){
			player.setHP(0);
			doPlayerDied(game, playerIndex);
		}
		
		// Advance to next turn and check if it's an enemy to do their turn.
		advanceTurn(game);
	}
	
	public int calculateDamage(GameController game, int attacker, int defender){
		int attackDamage = calculateAttack(game, attacker);
		int defense = calculateDefense(game, defender);
		int damage = attackDamage - defense;
		if(damage < 0){
			damage = 0;
		}
		return damage;
	}
	
	// Encounter chance
	// MapTile Level (in title)
	// Experience
	// Turns
	// Victory + Defeat Calls
	// Escape/Flee (chance on enemy)
	// Only 1 Enemy for now (grab possibilities from MapTile)
	
	public int calculateAttack(GameController game, int character){
		Character chr = game.getcharacters().get(character);
		int attack = chr.getAttack();
		if(chr.getHelm() != null){
			attack += chr.getHelm().getAttackBonus();
		}
		if(chr.getBraces() != null){
			attack += chr.getBraces().getAttackBonus();
		}
		if(chr.getChest() != null){
			attack += chr.getChest().getAttackBonus();
		}
		if(chr.getLegs() != null){
			attack += chr.getLegs().getAttackBonus();
		}
		if(chr.getBoots() != null){
			attack += chr.getBoots().getAttackBonus();
		}
		if(chr.getLeftHand() != null){
			attack += chr.getLeftHand().getAttackBonus();
		}
		if(chr.getRightHand() != null){
			attack += chr.getRightHand().getAttackBonus();
		}
		int range = (int) (attack*0.2);
		attack = (int) (attack + (random.nextInt(range+1) - range/2.0));
		return attack;
	}
	
	public int calculateDefense(GameController game, int character){
		Character chr = game.getcharacters().get(character);
		int defense = chr.getDefense();
		if(chr.getHelm() != null){
			defense += chr.getHelm().getDefenseBonus();
		}
		if(chr.getBraces() != null){
			defense += chr.getBraces().getDefenseBonus();
		}
		if(chr.getChest() != null){
			defense += chr.getChest().getDefenseBonus();
		}
		if(chr.getLegs() != null){
			defense += chr.getLegs().getDefenseBonus();
		}
		if(chr.getBoots() != null){
			defense += chr.getBoots().getDefenseBonus();
		}
		if(chr.getLeftHand() != null){
			defense += chr.getLeftHand().getDefenseBonus();
		}
		if(chr.getRightHand() != null){
			defense += chr.getRightHand().getDefenseBonus();
		}
		return defense;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void doPlayerWon(GameController game, int playerIndex, int killedIndex){
		// Let player know what they have done.
		game.add_dialog("You killed " + game.getcharacters().get(killedIndex).getName() + "!");
		
		// Get the player
		Player player = ((Player) game.getcharacters().get(playerIndex));
		
		// Change player exp
		int currentXP = player.getExperience();
		player.setExperience(currentXP + 300);
		System.out.println(player.getLevel() + " " + player.getskill_points());
		// Let player know what they have earned.
		game.add_dialog("You have been awarded 300 experience!");
		
		// Get Database
		IDatabase db = DatabaseProvider.getInstance();
		
		// Determine if boss fight or not (for legendary drop or not)
		if(game.getcharacters().get(killedIndex).getRace().equalsIgnoreCase("Greater Demon")){
			// Get a legendary hand
			Item handReward = db.getLegendaryItem(ItemType.R_HAND).result();
			
			// Give player the hand
			player.getInventory().add(handReward);
			game.add_dialog("The Greater Demon dropped a " + handReward.getName() + "!");
		}else{
			// Get an armor and a hand
			Item armorReward = db.getArmorItem().result();
			Item handReward = db.getHandheldItem().result();
			
			// Give items to player
			player.getInventory().add(armorReward);
			player.getInventory().add(handReward);
			game.add_dialog("You got a " + armorReward.getName() + " and a " + handReward.getName() + "!");
		}
		
		// Default is done
		done = true;
		game.setmode("game");
		
		// Check for alive combatant that isn't the player.
		for(int characterIndex: characterIDs){
			if(characterIndex != playerIndex && game.getcharacters().get(characterIndex).getHP() > 0){
				// If any aren't dead, combat isn't over.
				done = false;
			}
		}
		
		if(done){
			// Let player know
			game.add_dialog("You have killed everyone! (in this combat situation here)");
		}
	}
	
	public void doPlayerDied(GameController game, int playerIndex){
		// TODO: Change this for multiple players and stuff
		// Not sure how to notify correct player or whatever
		
		// Let player know they died
		game.add_dialog("You have died!");
		game.add_dialog("Restart if you think you can do better!");
		
		// Default is done
		done = true;
		
		// Check for alive players yet
		for(int characterIndex: characterIDs){
			if(characterIndex != playerIndex && game.getcharacters().get(characterIndex) instanceof Player &&
					game.getcharacters().get(characterIndex).getHP() > 0){
				done = false;
			}
		}
	}
}
