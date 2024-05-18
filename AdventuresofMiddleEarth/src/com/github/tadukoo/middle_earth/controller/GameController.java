package com.github.tadukoo.middle_earth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.github.tadukoo.aome.game.Game;
import com.github.tadukoo.aome.construct.GameObject;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.middle_earth.images.MapPanel;
import com.github.tadukoo.middle_earth.model.CombatSituation;
import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.map.GameMap;
import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.IDatabase;

/**
 * The main Game Controller for playing the Game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class GameController implements Engine{
	private Game game;
	private GameMap map;
	private List<Quest> quests;
	private List<Character> characters;
	private List<String> dialog;
	private String mode;
	private CombatSituation battle;
	private JFrame mapIMG = new JFrame("Map");
	private MapPanel mapPanel = new MapPanel();
	private Account user;
	
	public GameController(Game game){
		this.game = game;
		// dialog and mode are passed back and forth with each servlet/jsp call
		dialog = new ArrayList<>();
		mode = "game";
		
		//####################################################
		/* This does not work for us, because it resets the database everytime a new page is called
		 * this means that we reset the location of the player, reset his inventory, reset the entire
		 * game everytime that we refresh the page.
		 * I cannot for the life of me figure out how to get the database to only initialize once.
		 * Does it have to be intialized elsewhere? and passed in?
		 * 
		 * I think when we have the real database, it'll be fine, because then we'll be loading the 
		 * current database each time rather than re-initializing it? That it's just bad like this 
		 * for the fake database, since data can't be saved to it persistently.
		 */
		
		//Fake Database is rebuilt each time and populated into the respective fields.
		//DatabaseProvider.setInstance(new DerbyDatabase());
		//db = DatabaseProvider.getInstance();
		//######################################################
		
		//map.getMapTiles().get(getplayer().getlocation()).setVisited(true);
	}
	
	public GameController getGameController() {
		return this;
	}

	public String getmode() {
		return this.mode;
	}

	public void setmode(String mode) {
		this.mode = mode;
	}
	
	public List<String> getdialog() {
		return dialog;
	}
	
	public void setuser(Account user) {
		this.user = user;
	}
	
	public Account getuser(){
		return user;
	}

	public void setdialog(ArrayList<String> dialog) {
		this.dialog = dialog;
	}
	
	public void add_dialog(String line){
		dialog.add(line);
		if (dialog.size() > 35){
			dialog.remove(0);
		}
	}

	public GameMap getmap(){
		return map;
	}
	
	public void setmap(GameMap map){
		this.map = map;
	}

	public List<Quest> getquests(){
		return quests;
	}
	
	public void setquests(ArrayList<Quest> quests){
		this.quests = quests;
	}
	
	public List<Character> getcharacters(){
		return characters;
	}
	
	public Character getplayer(){
		//Player is assigned to index 0 of Characters List. ###Will have to update for multiplayer###
		return characters.get(0);
	}
	
	public void setcharacters(List<Character> characters){
		this.characters = characters;
	}
	
	public String getmapTile_longDescription(){
		List<GameObject> objects = map.getMapTiles().get(getplayer().getLocationID()).getObjects();
		String objectUpdate = "";
		if (objects != null) {
			for (GameObject object : objects) {
				if (object.getDescriptionUpdate() != null) {
					objectUpdate = object.getDescriptionUpdate();
				}
			}
		}
		return map.getMapTiles().get(getplayer().getLocationID()).getLongDescription() + objectUpdate;
	}
	
	public String getmapTile_name(){
		return map.getMapTiles().get(getplayer().getLocationID()).getName();
	}
	
	public CombatSituation getBattle(){
		return battle;
	}
	
	public void setBattle(CombatSituation battle){
		this.battle = battle;
	}

	public String getcombat_text(){
		String combat_text = "";
		if (this.dialog.size() > 10) {
			for (int i = this.dialog.size()-11; i < this.dialog.size(); i++) {
				// Is it supposed to have a \n before the first line? (Not sure)
				if (i == 0) {
					combat_text = this.dialog.get(i);
				} else {
					combat_text = combat_text+";"+this.dialog.get(i);
				}
			}
		} else {
			for (int i = 0; i < this.dialog.size(); i++) {
				// Is it supposed to have a \n before the first line? (Not sure)
				if (i == 0) {
					combat_text = this.dialog.get(i);
				} else {
					combat_text = combat_text+";"+this.dialog.get(i);
				}
			}
		}
		
		return combat_text;
	}
	
	public String getdisplay_text(){
		String display_text = "";
		for (int i = 0; i < this.dialog.size(); i++) {
			// Is it supposed to have a \n before the first line? (Not sure)
			if (i == 0) {
				display_text = this.dialog.get(i);
			} else {
				display_text = display_text+";"+this.dialog.get(i);
			}
		}
		return display_text;
	}

	public boolean mode_change(String command){
		if(command == null){
			return false;
		}else if(command.equalsIgnoreCase("inventory")){
			check_inventory();
			return true;
		}else if(command.equalsIgnoreCase("character")){
			check_character_sheet();
			return true;
		}else if(command.equalsIgnoreCase("map")){
			check_map();
			return true;
		}else if(command.equalsIgnoreCase("game")){
			return_to_game();
			return true;
		}
		return false;
	}
		
	
	@Override
	public String handle_command(String commandStr){
		String returnMessage = null;
		String command = "";
		String arg = null;
		String[] args = commandStr.split(" ");
		if(args.length > 2){
			return "Too many arguments in your command";
		}else if(args.length == 2){
        	command = args[0];
        	arg = args[1];
		}else if(args.length == 1){
        	command = commandStr;
        }
		
		if (commandStr.equalsIgnoreCase("save")){
			save();
		} else if(commandStr.equalsIgnoreCase("exit")) {
			
		}else if (mode.equalsIgnoreCase("combat")){
			if(battle != null && !battle.isDone()){
				if(command.equalsIgnoreCase("attack")){
					if(arg == null){
						add_dialog("What do you want to attack? (use name or race)");
					}else{
						// TODO: Currently Player index is 0, need to find it based on current player (for multiplayer)
						battle.playerAttackEnemy(this, 0, arg);
					}
				}else{
					add_dialog("You're in combat!");
				}
			} else {
				mode_change("game");
			}
		} else if(mode.equalsIgnoreCase("game")){
			if(battle != null && !battle.isDone()){
				if(command.equalsIgnoreCase("attack")){
					if(arg == null){
						add_dialog("What do you want to attack? (use name or race)");
					}else{
						// TODO: Currently Player index is 0, need to find it based on current player (for multiplayer)
						battle.playerAttackEnemy(this, 0, arg);
					}
				}else if (command.equalsIgnoreCase("combat")){
					
				} else {
					add_dialog("You're in combat!");
				}
			}else{
				if(command.equalsIgnoreCase("move")){
					if(arg.equalsIgnoreCase("north") || arg.equalsIgnoreCase("south") || 
							arg.equalsIgnoreCase("east") || arg.equalsIgnoreCase("west") ||
							arg.equalsIgnoreCase("northwest") || arg.equalsIgnoreCase("northeast") ||
							arg.equalsIgnoreCase("southwest") || arg.equalsIgnoreCase("southeast")){
						move(arg);
					}else{
						add_dialog("I don't understand that direction.");
					}
				}else if((command.equalsIgnoreCase("north") || command.equalsIgnoreCase("N"))){
					move("north");
				}else if((command.equalsIgnoreCase("south") || command.equalsIgnoreCase("S"))){
					move("south");
				}else if((command.equalsIgnoreCase("east") || command.equalsIgnoreCase("E"))){
					move("east");
				}else if((command.equalsIgnoreCase("west") || command.equalsIgnoreCase("W"))){
					move("west");
				}else if((command.equalsIgnoreCase("northeast") || command.equalsIgnoreCase("NE"))){
					move("northeast");
				}else if((command.equalsIgnoreCase("northwest") || command.equalsIgnoreCase("NW"))){
					move("northwest");
				}else if((command.equalsIgnoreCase("southeast") || command.equalsIgnoreCase("SE"))){
					move("southeast");
				}else if((command.equalsIgnoreCase("southwest") || command.equalsIgnoreCase("SW"))){
					move("southwest");
				}else if(command.equalsIgnoreCase("take")) {
					if (arg != null) {
						take(arg);
					} else {
						
					}
				}else if(command.equalsIgnoreCase("look")){
					look();
				}else if(command.equalsIgnoreCase("attack")){
					add_dialog("You're not in combat!");
				}else{
					if(!handle_object_commands(commandStr)){
						// Changed this to add_dialog due to our conversation about having mode = game have all text 
						// in dialog instead of having a separate error/response message (while other modes still use 
						// the response message though)
						add_dialog("Sorry, I didn't understand that.");
					}
				}
			}
		}else if(mode.equalsIgnoreCase("inventory")){
			if(command.equalsIgnoreCase("item")){
				if (arg != null) {
					try {
						int Item_num = Integer.parseInt(arg);
						//if (getplayer().getinventory().getitems().size() < Item_num || Item_num < 1 ) {
						if (getplayer().getInventory().size() < Item_num || Item_num < 1 ) {
							returnMessage = "Sorry you dont have an item at that index";
						} else  {
							returnMessage = item_details(Item_num-1);
						}
					} catch (NumberFormatException nfe) {
						returnMessage = "Invalid item selection. Example: 'item 1' to see the item at position 1";
					}
				} else {
					returnMessage = "Please designate the item # you want to view more details of.";
				}
			} else if(command.equalsIgnoreCase("equip")) {
				if (arg != null)
					try {
						int Item_num = Integer.parseInt(arg) - 1;
						if (getplayer().getInventory().size() - 1 < Item_num || Item_num < 0 ) {
							returnMessage = "Sorry you dont have an item at that index";
						} else {
							Item item = getplayer().getInventory().get(Item_num);
							if (item.getType() == ItemType.HELM) {
								getplayer().setHelm(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.BOOTS) {
								getplayer().setBoots(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.BRACES) {
								getplayer().setBraces(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.CHEST) {
								getplayer().setChest(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.L_HAND) {
								getplayer().setLeftHand(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.R_HAND) {
								getplayer().setRightHand(item);
								returnMessage = "You have equiped " + item.getName();
							} else if (item.getType() == ItemType.LEGS) {
								getplayer().setLegs(item);
								returnMessage = "You have equiped " + item.getName();
							}
						}
				} catch (NumberFormatException nfe) {
					returnMessage = "Invalid item selection. Example: 'equip 1' to see the equip item at position 1";
				}
			} else {
				// Checking if command isn't empty, since it can't be null -> initialized in here to "";
				// Simply changed to else... I may have lost the null command message.
				// Not sure if this message is still okay for a null command error?
				returnMessage = "Sorry, I didn't understand that.";
			}
		} else if(mode.equalsIgnoreCase("character")){
			
		}
		return returnMessage;
	}
	
	private boolean handle_object_commands(String commandStr){
		boolean isObjectCommand = false;
		String[] commands = commandStr.split(" ");
		String command = "";
		String arg = null;
		if (commands.length == 2){
			command = commands[0];
			arg = commands[1];
		} else {
			return false;
		}
		
		GameObject action_object = null;
		List<GameObject> objects = map.getMapTiles().get(getplayer().getLocationID()).getObjects();
		if(objects == null){
			return false;
		}
		for (GameObject object : objects){
			// TODO: NOTE: This doesn't account for capitals (e.g. if someone types Climb instead of climb)
			// I would change it, but it's 9:11 PM the night before Milestone 3 so I'm afraid of breaking stuff
			command = command.toLowerCase();
			if (object.getCommandResponses().containsKey(command)) {
				if (object.getName().toLowerCase().contains(arg.toLowerCase())) {
					action_object = object;
					isObjectCommand = true;
				}
			}
		}
		if (action_object != null) {
			String string = action_object.getCommandResponses().get(command);
			for (Item item : action_object.getItems()) {
				string = string + " " + item.getDescriptionUpdate();
			}
			dialog.add(string);
		}
		return isObjectCommand;
	}
	
	@Override
	public void check_character_sheet(){
		mode = "character";
	}
	
	@Override
	public void check_inventory(){
		mode = "inventory";
	}
	
	@Override
	public void check_map(){
		mode = "map";
	}
	
	@Override
	public void return_to_game(){
		mode = "game";
	}
	
	@Override
	public void save(){
		IDatabase db = DatabaseProvider.getInstance();
		db.saveGame(this);
		mapPanel.save(user.getusername(), user.getcurrent_game());
	}

	
	public String item_details(int item_num){
		Item item = getplayer().getInventory().get(item_num);
		//Item item = items.get(item_num);
		return item.getName() + ": " + item.getLongDescription() + ";Weight: " + item.getWeight() + ";Quest item: " + String.valueOf(item.isQuestItem());
	}
	/*
	 * Player-Specific Actions
	 */
	
	@Override
	public void take(String name){
		int location = getplayer().getLocationID();
		GameObject holder = new GameObject();
		if (map.getMapTiles().get(location).getObjects() != null) {
			Item lookFor = null;
			for (GameObject object : map.getMapTiles().get(location).getObjects()) {
				List<Item> items = object.getItems();
				for (Item item : items) {
					if (item.getName().toLowerCase().contains(name.toLowerCase())) {
						lookFor = item;
						holder = object;
						getplayer().getInventory().add(item);
					}
				}
			}
			if (lookFor != null) {
				add_dialog("You have taken " + lookFor.getName());
				holder.removeItem(lookFor);
			} else {
				add_dialog("You cannot take " + name + " here.");
			}
		} else {
			add_dialog("There is nothing to take here.");
		}
		
	}
	
	@Override
	public void look(){
		add_dialog(getmapTile_name());
    	add_dialog(getmapTile_longDescription());
	}
	
	/*
	 * Character-Specific Actions (outside of Player)
	 */
	
	@Override
	public void move(String direction){
		Character player = characters.get(0);
		MapTile currentTile = map.getMapTiles().get(player.getLocationID());
		Integer newTileID = switch(direction.toLowerCase()){
			case "north" -> currentTile.getNorthConnection();
			case "northeast" -> currentTile.getNortheastConnection();
			case "east" -> currentTile.getEastConnection();
			case "southeast" -> currentTile.getSoutheastConnection();
			case "south" -> currentTile.getSouthConnection();
			case "southwest" -> currentTile.getSouthwestConnection();
			case "west" -> currentTile.getWestConnection();
			case "northwest" -> currentTile.getNorthwestConnection();
			default -> null;
		};
		if(newTileID != null){
			if (player.getLocationID() == 8 && direction.equalsIgnoreCase("west")) {
				boolean key = false;
				for (Item item : player.getInventory()) {
					if (item.getName() != null && item.getName().equalsIgnoreCase("Ornate Key")) {
						key = true;
					}
				}
				if (key) {
					add_dialog("You use the Ornate Key and open the gate.");
					player.setLocationID(newTileID);
					MapTile mapTile = map.getMapTileByID(newTileID);
					add_dialog(mapTile.getLongDescription());
					mapPanel.setDirection(direction);
					mapPanel.setMapTile(mapTile);
					battle = new CombatSituation(this, 1, "Greater Demon", 0);
				} else {
					add_dialog("You seem to be missing something to be able to go that direction.");
				}
			} else {
				player.setLocationID(newTileID);
				MapTile mapTile = map.getMapTileByID(newTileID);
				add_dialog(mapTile.getName());
				String string = mapTile.getLongDescription();
				
				if (mapTile.getObjects() != null) {
					for (GameObject object : mapTile.getObjects()){
						string += " " + object.getLongDescription();
					}
				}
				add_dialog(string);
				
				mapPanel.setDirection(direction);
				mapPanel.setMapTile(mapTile);
				// TODO: Check if on the same tile as another player to trigger pvp combat (and thus not do an encounter check)
				
				Random rand = new Random(System.currentTimeMillis());
				int encounterCheck = rand.nextInt(5);
				if(encounterCheck == 0){
					// TODO: Currently Player index is 0, need to find it based on current player (for multiplayer)
					battle = new CombatSituation(this, 1, 0);
				}
			}
		} else {
			add_dialog("You can't go that way");
		}
		map.getMapTiles().get(player.getLocationID()).setVisited(true);
		
	}
	
	
	public void startMap(String username, int gameID){
		mapPanel.setMapTile(map.getMapTileByID(getplayer().getLocationID()));
		mapPanel.setusername(username);
		mapPanel.setgameID(gameID);
		mapIMG.setContentPane(mapPanel);
		mapIMG.pack();
		mapIMG.setVisible(false);
	}
	
	public List<Item> cheatcode(){
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getAllItems().result();
	}
	
}