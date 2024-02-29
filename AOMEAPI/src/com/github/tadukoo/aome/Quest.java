package com.github.tadukoo.aome;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.tadukoo.aome.character.NPC;
import com.github.tadukoo.aome.construct.Item;

public class Quest{
	private ArrayList<Item> rewardItems;
	private int rewardCoins;
	private HashMap<String, NPC> dialogue;
	
	public Quest() {
		rewardItems = new ArrayList<>();
		dialogue = new HashMap<>();
	}
	
	public void setRewardItems(ArrayList<Item> rewardItems) {
		this.rewardItems = rewardItems;		
	}
	
	public ArrayList<Item> getRewardItems() {
		return this.rewardItems;
	}
	
	public void setRewardCoins (int rewardCoins) {
		this.rewardCoins = rewardCoins;
	}
	
	public int getRewardCoins() {
		return this.rewardCoins;
	}
	
	public void setDialogue(HashMap<String, NPC> dialogue) {
		this.dialogue = dialogue;
	}
	
	public HashMap<String, NPC> getDialogue() {
		return this.dialogue;
	}
}
