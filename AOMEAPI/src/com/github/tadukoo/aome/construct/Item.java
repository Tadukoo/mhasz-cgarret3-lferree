package com.github.tadukoo.aome.construct;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

/**
 * Item represents an item in the game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class Item extends Construct{
	/** The name of the table */
	public static final String TABLE_NAME = "Items";
	/** The column name for the description update column */
	public static final String DESCRIPTION_UPDATE_COLUMN_NAME = "description_update";
	/** The column name for the type column */
	public static final String TYPE_COLUMN_NAME = "type";
	/** The column name for the level requirement column */
	public static final String LEVEL_REQUIREMENT_COLUMN_NAME = "level_requirement";
	/** The column name for the attack bonus column */
	public static final String ATTACK_BONUS_COLUMN_NAME = "attack_bonus";
	/** The column name for the defense bonus column */
	public static final String DEFENSE_BONUS_COLUMN_NAME = "defense_bonus";
	/** The column name for the HP bonus column */
	public static final String HP_BONUS_COLUMN_NAME = "hp_bonus";
	/** The column name for the weight column */
	public static final String WEIGHT_COLUMN_NAME = "weight";
	private boolean isQuestItem;
	
	// TODO: Figure out how to put location in here???
	
	/**
	 * Creates a new {@link Item} with no parameters
	 */
	public Item(){
		super();
	}
	
	/**
	 * Creates a new {@link Item} with the given parameters
	 *
	 * @param id The ID of the {@link Item}
	 * @param name The name of the {@link Item}
	 * @param shortDescription The short description of the {@link Item}
	 * @param longDescription The long description of the {@link Item}
	 * @param descriptionUpdate The description update of the {@link Item}
	 * @param type The {@link ItemType type} of the {@link Item}
	 * @param levelRequirement The level requirement of the {@link Item}
	 * @param attackBonus The attack bonus of the {@link Item}
	 * @param defenseBonus The defense bonus of the {@link Item}
	 * @param hpBonus The HP bonus of the {@link Item}
	 * @param weight The weight of the {@link Item}
	 */
	public Item(
			int id, String name, String shortDescription, String longDescription,
			String descriptionUpdate, ItemType type, int levelRequirement,
			int attackBonus, int defenseBonus, int hpBonus, float weight){
		super(id, name, shortDescription, longDescription);
		setDescriptionUpdate(descriptionUpdate);
		setType(type);
		setLevelRequirement(levelRequirement);
		setAttackBonus(attackBonus);
		setDefenseBonus(defenseBonus);
		setHPBonus(hpBonus);
		setWeight(weight);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setDefaultColumnDefs(){
		super.setDefaultColumnDefs();
		
		// Description Update Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(DESCRIPTION_UPDATE_COLUMN_NAME)
				.varchar()
				.length(100)
				.build());
		
		// Type Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(TYPE_COLUMN_NAME)
				.varchar()
				.length(20)
				.build());
		
		// Level Requirement Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(LEVEL_REQUIREMENT_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Attack Bonus Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(ATTACK_BONUS_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Defense Bonus Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(DEFENSE_BONUS_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// HP Bonus Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(HP_BONUS_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Weight Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(WEIGHT_COLUMN_NAME)
				.floatType()
				.defaultSizeAndDigits()
				.build());
	}
	
	/**
	 * @return The description update for this {@link Item}
	 */
	public String getDescriptionUpdate(){
		return (String) getItem(DESCRIPTION_UPDATE_COLUMN_NAME);
	}
	
	/**
	 * @param descriptionUpdate The description update to be set on the {@link Item}
	 */
	public void setDescriptionUpdate(String descriptionUpdate){
		setItem(DESCRIPTION_UPDATE_COLUMN_NAME, descriptionUpdate);
	}
	
	/**
	 * @return The {@link ItemType type} of this {@link Item}
	 */
	public ItemType getType(){
		Object obj = getItem(TYPE_COLUMN_NAME);
		if(obj instanceof String s){
			return ItemType.valueOf(s);
		}else if(obj instanceof ItemType type){
			return type;
		}else{
			return null;
		}
	}
	
	/**
	 * @param type The {@link ItemType type} to be set on the {@link Item}
	 */
	public void setType(ItemType type){
		setItem(TYPE_COLUMN_NAME, type);
	}
	
	/**
	 * @return The level requirement of this {@link Item}
	 */
	public int getLevelRequirement(){
		return (int) getItem(LEVEL_REQUIREMENT_COLUMN_NAME);
	}
	
	/**
	 * @param levelRequirement The level requirement to be set on the {@link Item}
	 */
	public void setLevelRequirement(int levelRequirement){
		setItem(LEVEL_REQUIREMENT_COLUMN_NAME, levelRequirement);
	}
	
	/**
	 * @return The attack bonus of this {@link Item}
	 */
	public int getAttackBonus(){
		return (int) getItem(ATTACK_BONUS_COLUMN_NAME);
	}
	
	/**
	 * @param attackBonus The attack bonus to be set for the {@link Item}
	 */
	public void setAttackBonus(int attackBonus){
		setItem(ATTACK_BONUS_COLUMN_NAME, attackBonus);
	}
	
	/**
	 * @return The defense bonus of this {@link Item}
	 */
	public int getDefenseBonus(){
		return (int) getItem(DEFENSE_BONUS_COLUMN_NAME);
	}
	
	/**
	 * @param defenseBonus The defense bonus to be set for the {@link Item}
	 */
	public void setDefenseBonus(int defenseBonus){
		setItem(DEFENSE_BONUS_COLUMN_NAME, defenseBonus);
	}
	
	/**
	 * @return The HP bonus of this {@link Item}
	 */
	public int getHPBonus(){
		return (int) getItem(HP_BONUS_COLUMN_NAME);
	}
	
	/**
	 * @param hpBonus The HP bonus to be set for the {@link Item}
	 */
	public void setHPBonus(int hpBonus){
		setItem(HP_BONUS_COLUMN_NAME, hpBonus);
	}
	
	/**
	 * @return The weight of this {@link Item}
	 */
	public float getWeight(){
		return (float) getItem(WEIGHT_COLUMN_NAME);
	}
	
	/**
	 * @param weight The weight to be set for the {@link Item}
	 */
	public void setWeight(float weight){
		setItem(WEIGHT_COLUMN_NAME, weight);
	}
	
	public boolean isQuestItem(){
		return this.isQuestItem;
	}
	
	public void setQuestItem(boolean isQuestItem){
		this.isQuestItem = isQuestItem;
	}
}
