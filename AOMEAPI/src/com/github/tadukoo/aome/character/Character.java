package com.github.tadukoo.aome.character;

import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.database.mysql.pojo.AbstractDatabasePojo;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Character is a base class for characters in the game, e.g. {@link Player players} and
 * {@link Enemy enemies}
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public abstract class Character extends AbstractDatabasePojo{
	/** The column name of the ID column */
	public static final String ID_COLUMN_NAME = "id";
	/** The column name of the name column */
	public static final String NAME_COLUMN_NAME = "name";
	/** The column name of the race column */
	public static final String RACE_COLUMN_NAME = "race";
	/** The column name of the gender column */
	public static final String GENDER_COLUMN_NAME = "gender";
	/** The column name of the level column */
	public static final String LEVEL_COLUMN_NAME = "level";
	/** The column name of the HP column */
	public static final String HP_COLUMN_NAME = "hp";
	/** The column name of the MP column */
	public static final String MP_COLUMN_NAME = "mp";
	/** The column name of the attack column */
	public static final String ATTACK_COLUMN_NAME = "attack";
	/** The column name of the defense column */
	public static final String DEFENSE_COLUMN_NAME = "defense";
	/** The column name of the special attack column */
	public static final String SPECIAL_ATTACK_COLUMN_NAME = "special_attack";
	/** The column name of the special defense column */
	public static final String SPECIAL_DEFENSE_COLUMN_NAME = "special_defense";
	/** The column name of the location ID column */
	public static final String LOCATION_ID_COLUMN_NAME = "location_id";
	/** The column name of the helm ID column */
	public static final String HELM_ID_COLUMN_NAME = "helm_id";
	/** The column name of the braces ID column */
	public static final String BRACES_ID_COLUMN_NAME = "braces_id";
	/** The column name of the chest ID column */
	public static final String CHEST_ID_COLUMN_NAME = "chest_id";
	/** The column name of the legs ID column */
	public static final String LEGS_ID_COLUMN_NAME = "legs_id";
	/** The column name of the boots ID column */
	public static final String BOOTS_ID_COLUMN_NAME = "boots_id";
	/** The column name of the left hand ID column */
	public static final String LEFT_HAND_ID_COLUMN_NAME = "left_hand_id";
	/** The column name of the right hand ID column */
	public static final String RIGHT_HAND_ID_COLUMN_NAME = "right_hand_id";
	/** The column name of the coins column */
	public static final String COINS_COLUMN_NAME = "coins";
	
	private Item helm;
	private Item braces;
	private Item chest;
	private Item legs;
	private Item boots;
	private Item leftHand;
	private Item rightHand;
	private List<Item> inventory;
	
	/**
	 * Creates a {@link Character} with no parameters
	 */
	public Character(){
		super();
		setLevel(1);
		inventory = new ArrayList<>();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getIDColumnName(){
		return ID_COLUMN_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		// ID Primary Key Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build());
		
		// Name Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(NAME_COLUMN_NAME)
				.varchar()
				.length(40)
				.build());
		
		// Race Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(RACE_COLUMN_NAME)
				.varchar()
				.length(40)
				.build());
		
		// Gender Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(GENDER_COLUMN_NAME)
				.varchar()
				.length(40)
				.build());
		
		// Level Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(LEVEL_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// HP Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(HP_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// MP Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(MP_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Attack Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(ATTACK_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Defense Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(DEFENSE_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Special Attack Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(SPECIAL_ATTACK_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Special Defense Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(SPECIAL_DEFENSE_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Location ID Column
		ColumnDefinition locationIDCol = ColumnDefinition.builder()
				.columnName(LOCATION_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(locationIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(locationIDCol)
				.references(MapTile.TABLE_NAME)
				.referenceColumnNames(MapTile.ID_COLUMN_NAME)
				.build());
		
		// Helm ID Column
		ColumnDefinition helmIDCol = ColumnDefinition.builder()
				.columnName(HELM_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(helmIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(helmIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Braces ID Column
		ColumnDefinition bracesIDCol = ColumnDefinition.builder()
				.columnName(BRACES_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(bracesIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(bracesIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Chest ID Column
		ColumnDefinition chestIDCol = ColumnDefinition.builder()
				.columnName(CHEST_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(chestIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(chestIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Legs ID Column
		ColumnDefinition legsIDCol = ColumnDefinition.builder()
				.columnName(LEGS_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(legsIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(legsIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Boots ID Column
		ColumnDefinition bootsIDCol = ColumnDefinition.builder()
				.columnName(BOOTS_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(bootsIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(bootsIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Left Hand ID Column
		ColumnDefinition leftHandIDCol = ColumnDefinition.builder()
				.columnName(LEFT_HAND_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(leftHandIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(leftHandIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Right Hand ID Column
		ColumnDefinition rightHandIDCol = ColumnDefinition.builder()
				.columnName(RIGHT_HAND_ID_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build();
		addColumnDef(rightHandIDCol);
		addForeignKey(ForeignKeyConstraint.builder()
				.columnDefs(rightHandIDCol)
				.references(Item.TABLE_NAME)
				.referenceColumnNames(Item.ID_COLUMN_NAME)
				.build());
		
		// Coins Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(COINS_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
	}
	
	/**
	 * @return The ID of this {@link Character}
	 */
	public int getID(){
		return (int) getItem(ID_COLUMN_NAME);
	}
	
	/**
	 * @param id The ID to be set on the {@link Character}
	 */
	public void setID(int id){
		setItem(ID_COLUMN_NAME, id);
	}
	
	/**
	 * @return The name of this {@link Character}
	 */
	public String getName(){
		return (String) getItem(NAME_COLUMN_NAME);
	}
	
	/**
	 * @param name The name to be set on the {@link Character}
	 */
	public void setName(String name){
		setItem(NAME_COLUMN_NAME, name);
	}
	
	/**
	 * @return The race of this {@link Character}
	 */
	public String getRace(){
		return (String) getItem(RACE_COLUMN_NAME);
	}
	
	/**
	 * @param race The race to be set on this {@link Character}
	 */
	public void setRace(String race){
		setItem(RACE_COLUMN_NAME, race);
	}
	
	/**
	 * @return The gender of this {@link Character}
	 */
	public String getGender(){
		return (String) getItem(GENDER_COLUMN_NAME);
	}
	
	/**
	 * @param gender The gender to be set on this {@link Character}
	 */
	public void setGender(String gender){
		setItem(GENDER_COLUMN_NAME, gender);
	}
	
	/**
	 * @return The level of this {@link Character}
	 */
	public int getLevel(){
		return (int) getItem(LEVEL_COLUMN_NAME);
	}
	
	/**
	 * @param level The level to be set on this {@link Character}
	 */
	public void setLevel(int level){
		setItem(LEVEL_COLUMN_NAME, level);
	}
	
	/**
	 * @return The HP of this {@link Character}
	 */
	public int getHP(){
		return (int) getItem(HP_COLUMN_NAME);
	}
	
	/**
	 * @param hp The HP to be set on this {@link Character}
	 */
	public void setHP(int hp){
		setItem(HP_COLUMN_NAME, hp);
	}
	
	/**
	 * @return The MP of this {@link Character}
	 */
	public int getMP(){
		return (int) getItem(MP_COLUMN_NAME);
	}
	
	/**
	 * @param mp The MP to be set on this {@link Character}
	 */
	public void setMP(int mp){
		setItem(MP_COLUMN_NAME, mp);
	}
	
	/**
	 * @return The attack of this {@link Character}
	 */
	public int getAttack(){
		return (int) getItem(ATTACK_COLUMN_NAME);
	}
	
	/**
	 * @param attack The attack to be set on this {@link Character}
	 */
	public void setAttack(int attack){
		setItem(ATTACK_COLUMN_NAME, attack);
	}
	
	/**
	 * @return The defense of this {@link Character}
	 */
	public int getDefense(){
		return (int) getItem(DEFENSE_COLUMN_NAME);
	}
	
	/**
	 * @param defense The defense to be set on this {@link Character}
	 */
	public void setDefense(int defense){
		setItem(DEFENSE_COLUMN_NAME, defense);
	}
	
	/**
	 * @return The special attack of this {@link Character}
	 */
	public int getSpecialAttack(){
		return (int) getItem(SPECIAL_ATTACK_COLUMN_NAME);
	}
	
	/**
	 * @param specialAttack The special attack to be set on this {@link Character}
	 */
	public void setSpecialAttack(int specialAttack){
		setItem(SPECIAL_ATTACK_COLUMN_NAME, specialAttack);
	}
	
	/**
	 * @return The special defense of this {@link Character}
	 */
	public int getSpecialDefense(){
		return (int) getItem(SPECIAL_DEFENSE_COLUMN_NAME);
	}
	
	/**
	 * @param specialDefense The special defense to be set on this {@link Character}
	 */
	public void setSpecialDefense(int specialDefense){
		setItem(SPECIAL_DEFENSE_COLUMN_NAME, specialDefense);
	}
	
	/**
	 * @return The location ID of this {@link Character}
	 */
	public int getLocationID(){
		return (int) getItem(LOCATION_ID_COLUMN_NAME);
	}
	
	/**
	 * @param locationID The location ID to be set for this {@link Character}
	 */
	public void setLocationID(int locationID){
		setItem(LOCATION_ID_COLUMN_NAME, locationID);
	}
	
	/**
	 * @return The helm ID of this {@link Character}
	 */
	public Integer getHelmID(){
		return (Integer) getItem(HELM_ID_COLUMN_NAME);
	}
	
	/**
	 * @param helmID The helm ID to be set on this {@link Character}
	 */
	public void setHelmID(Integer helmID){
		setItem(HELM_ID_COLUMN_NAME, helmID);
	}
	
	/**
	 * @return The helm of this {@link Character}
	 */
	public Item getHelm(){
		return helm;
	}
	
	/**
	 * @param helm The helm to be set on this {@link Character}
	 */
	public void setHelm(Item helm){
		if(helm.getType() != ItemType.HELM){
			throw new IllegalArgumentException("This must be a helm!");
		}
		this.helm = helm;
	}
	
	/**
	 * @return The braces ID of this {@link Character}
	 */
	public Integer getBracesID(){
		return (Integer) getItem(BRACES_ID_COLUMN_NAME);
	}
	
	/**
	 * @param bracesID The braces ID to be set on this {@link Character}
	 */
	public void setBracesID(Integer bracesID){
		setItem(BRACES_ID_COLUMN_NAME, bracesID);
	}
	
	/**
	 * @return The braces of this {@link Character}
	 */
	public Item getBraces(){
		return braces;
	}
	
	/**
	 * @param braces The braces to be set on this {@link Character}
	 */
	public void setBraces(Item braces){
		if(braces.getType() != ItemType.BRACES){
			throw new IllegalArgumentException("This must be braces!");
		}
		this.braces = braces;
	}
	
	/**
	 * @return The chest ID of this {@link Character}
	 */
	public Integer getChestID(){
		return (Integer) getItem(CHEST_ID_COLUMN_NAME);
	}
	
	/**
	 * @param chestID The chest ID to be set on this {@link Character}
	 */
	public void setChestID(Integer chestID){
		setItem(CHEST_ID_COLUMN_NAME, chestID);
	}
	
	/**
	 * @return The chest of this {@link Character}
	 */
	public Item getChest(){
		return chest;
	}
	
	/**
	 * @param chest The chest to be set on this {@link Character}
	 */
	public void setChest(Item chest){
		if(chest.getType() != ItemType.CHEST){
			throw new IllegalArgumentException("This must be a chest!");
		}
		this.chest = chest;
	}
	
	/**
	 * @return The legs ID of this {@link Character}
	 */
	public Integer getLegsID(){
		return (Integer) getItem(LEGS_ID_COLUMN_NAME);
	}
	
	/**
	 * @param legsID The legs ID to be set on this {@link Character}
	 */
	public void setLegsID(Integer legsID){
		setItem(LEGS_ID_COLUMN_NAME, legsID);
	}
	
	/**
	 * @return The legs of this {@link Character}
	 */
	public Item getLegs(){
		return legs;
	}
	
	/**
	 * @param legs The legs to be set on this {@link Character}
	 */
	public void setLegs(Item legs){
		if(legs.getType() != ItemType.LEGS){
			throw new IllegalArgumentException("This must be legs!");
		}
		this.legs = legs;
	}
	
	/**
	 * @return The boots ID of this {@link Character}
	 */
	public Integer getBootsID(){
		return (Integer) getItem(BOOTS_ID_COLUMN_NAME);
	}
	
	/**
	 * @param bootsID The boots ID to be set on this {@link Character}
	 */
	public void setBootsID(Integer bootsID){
		setItem(BOOTS_ID_COLUMN_NAME, bootsID);
	}
	
	/**
	 * @return The boots of this {@link Character}
	 */
	public Item getBoots(){
		return boots;
	}
	
	/**
	 * @param boots The boots to be set on this {@link Character}
	 */
	public void setBoots(Item boots){
		if(boots.getType() != ItemType.BOOTS){
			throw new IllegalArgumentException("This must be boots!");
		}
		this.boots = boots;
	}
	
	/**
	 * @return The left hand ID of this {@link Character}
	 */
	public Integer getLeftHandID(){
		return (Integer) getItem(LEFT_HAND_ID_COLUMN_NAME);
	}
	
	/**
	 * @param leftHandID The left hand ID to be set on this {@link Character}
	 */
	public void setLeftHandID(Integer leftHandID){
		setItem(LEFT_HAND_ID_COLUMN_NAME, leftHandID);
	}
	
	/**
	 * @return The left hand of this {@link Character}
	 */
	public Item getLeftHand(){
		return leftHand;
	}
	
	/**
	 * @param leftHand The left hand to be set on this {@link Character}
	 */
	public void setLeftHand(Item leftHand){
		if(leftHand.getType() != ItemType.L_HAND){
			// TODO: Maybe just HAND?
			throw new IllegalArgumentException("This must be a HAND type!");
		}
		this.leftHand = leftHand;
	}
	
	/**
	 * @return The right hand ID of this {@link Character}
	 */
	public Integer getRightHandID(){
		return (Integer) getItem(RIGHT_HAND_ID_COLUMN_NAME);
	}
	
	/**
	 * @param rightHandID The right hand ID to be set on this {@link Character}
	 */
	public void setRightHandID(Integer rightHandID){
		setItem(RIGHT_HAND_ID_COLUMN_NAME, rightHandID);
	}
	
	/**
	 * @return The right hand of this {@link Character}
	 */
	public Item getRightHand(){
		return rightHand;
	}
	
	/**
	 * @param rightHand The right hand to be set on this {@link Character}
	 */
	public void setRightHand(Item rightHand){
		if(rightHand.getType() != ItemType.R_HAND){
			// TODO: Maybe just HAND?
			throw new IllegalArgumentException("This must be a HAND type!");
		}
		this.rightHand = rightHand;
	}
	
	public void remove(String type){
		// TODO: Perhaps remove this, allow for setting null, and have the defaults from here
		Item emptyItemSlot = new Item();
		emptyItemSlot.setAttackBonus(0);
		emptyItemSlot.setDefenseBonus(0);
		emptyItemSlot.setDescriptionUpdate("You haven't equipped one");
		emptyItemSlot.setHPBonus(0);
		emptyItemSlot.setLevelRequirement(0);
		emptyItemSlot.setID(0);
		emptyItemSlot.setQuestItem(false);
		emptyItemSlot.setWeight(0);
		emptyItemSlot.setLongDescription("Empty Slot");
		emptyItemSlot.setShortDescription("Empty Slot");
		emptyItemSlot.setName("Empty Slot");
		
		if(type.equalsIgnoreCase("head")){
			emptyItemSlot.setType(ItemType.HELM);
			this.helm = emptyItemSlot;
		}else if(type.equalsIgnoreCase("chest")){
			emptyItemSlot.setType(ItemType.CHEST);
			this.chest = emptyItemSlot;
		}else if(type.equalsIgnoreCase("arms")){
			emptyItemSlot.setType(ItemType.BRACES);
			this.braces = emptyItemSlot;
		}else if(type.equalsIgnoreCase("lhand")){
			emptyItemSlot.setType(ItemType.L_HAND);
			this.leftHand = emptyItemSlot;
		}else if(type.equalsIgnoreCase("rhand")){
			emptyItemSlot.setType(ItemType.R_HAND);
			this.rightHand = emptyItemSlot;
		}else if(type.equalsIgnoreCase("legs")){
			emptyItemSlot.setType(ItemType.LEGS);
			this.legs = emptyItemSlot;
		}else if(type.equalsIgnoreCase("boots")){
			emptyItemSlot.setType(ItemType.BOOTS);
			this.boots = emptyItemSlot;
		}
	}
	
	public List<Item> getInventory(){
		return inventory;
	}
	
	public void setInventory(List<Item> inventory){
		this.inventory = inventory;
	}
	
	/**
	 * @return The coins of this {@link Character}
	 */
	public int getCoins(){
		return (int) getItem(COINS_COLUMN_NAME);
	}
	
	/**
	 * @param coins The coins to be set on this {@link Character}
	 */
	public void setCoins(int coins){
		setItem(COINS_COLUMN_NAME, coins);
	}
}
