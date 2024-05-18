package com.github.tadukoo.aome.character;

import com.github.tadukoo.aome.Quest;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Player represents a player {@link Character} in the game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Chris Garrety (cgarret3)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class Player extends Character{
	/** The table name */
	public static final String TABLE_NAME = "Players";
	/** The column name of the experience column */
	public static final String EXPERIENCE_COLUMN_NAME = "experience";
	/** The column name of the carry weight column */
	public static final String CARRY_WEIGHT_COLUMN_NAME = "carry_weight";
	
	private List<Quest> quests;
	private int skill_points = 0;
	private Map<Integer, Integer> lvl_up = new HashMap<>();
	
	public Player(){
		quests = new ArrayList<>();
		for (int i = 1; i <= 20; i ++) {
			lvl_up.put(i, i*50);
		}
		setExperience(0);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return TABLE_NAME;
	}
	
	@Override
	public void setDefaultColumnDefs(){
		super.setDefaultColumnDefs();
		
		// Experience Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(EXPERIENCE_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
		
		// Carry Weight Column
		addColumnDef(ColumnDefinition.builder()
				.columnName(CARRY_WEIGHT_COLUMN_NAME)
				.integer()
				.defaultSize()
				.build());
	}
	
	/**
	 * @return The experience of this {@link Player}
	 */
	public int getExperience(){
		return (int) getItem(EXPERIENCE_COLUMN_NAME);
	}
	
	/**
	 * @param experience The experience to be set on this {@link Player}
	 */
	public void setExperience(int experience){
		setItem(EXPERIENCE_COLUMN_NAME, experience);
		while(experience > lvl_up.get(getLevel())){
			experience -= lvl_up.get(getLevel());
			setItem(EXPERIENCE_COLUMN_NAME, experience);
			setLevel(getLevel() + 1);
			setHP(getHP() + 10);
			setAttack(getAttack()+1);
			setDefense(getDefense()+1);
			
			setskill_points(3);
		}
	}
	
	/**
	 * @return The carry weight of this {@link Player}
	 */
	public int getCarryWeight(){
		return (int) getItem(CARRY_WEIGHT_COLUMN_NAME);
	}
	
	/**
	 * @param carryWeight The carry weight to be set on this {@link Player}
	 */
	public void setCarryWeight(int carryWeight){
		setItem(CARRY_WEIGHT_COLUMN_NAME, carryWeight);
	}
	
	public void add_quest(Quest quest){
		quests.add(quest);
	}
	
	public void setquests(ArrayList<Quest> quests) {
		this.quests = quests;
	}
	
	public List<Quest> getquests(){
		return quests;
	}

	public int getskill_points() {
		return skill_points;
	}

	public void setskill_points(int skill_points) {
		this.skill_points += skill_points;
	}
}
