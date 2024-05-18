package com.github.tadukoo.aome.character;

import java.util.ArrayList;

import com.github.tadukoo.aome.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest{
	private Player player;
	
	@BeforeEach
	public void setup(){
		player = new Player();
	}
	
	@Test
	public void testSetExperience(){
		player.setExperience(6);
		
		assertEquals(6, player.getExperience());
		
		player.setExperience(42);
		
		assertEquals(42, player.getExperience());
	}
	
	@Test
	public void testSetCarry_Weight(){
		player.setCarryWeight(837);
		
		assertEquals(837, player.getCarryWeight());
		
		player.setCarryWeight(29384);
		
		assertEquals(29384, player.getCarryWeight());
	}
	
	@Test
	public void testSetQuests(){
		ArrayList<Quest> quests = new ArrayList<>();
		Quest quest = new Quest();
		quests.add(quest);
		
		player.setquests(quests);
		
		assertEquals(1, player.getquests().size());
		assertEquals(quest, player.getquests().get(0));
		
		ArrayList<Quest> quests2 = new ArrayList<>();
		Quest quest1 = new Quest();
		Quest quest2 = new Quest();
		quests2.add(quest1);
		quests2.add(quest2);
		
		player.setquests(quests2);
		
		assertEquals(2, player.getquests().size());
		assertEquals(quest1, player.getquests().get(0));
		assertEquals(quest2, player.getquests().get(1));
	}
	
	@Test
	public void testAdd_Quest(){
		Quest quest1 = new Quest();
		Quest quest2 = new Quest();
		quest2.setRewardCoins(20);
		
		assertEquals(0, player.getquests().size());
		
		player.add_quest(quest1);
		assertEquals(1, player.getquests().size());
		assertEquals(quest1, player.getquests().get(0));
		
		player.add_quest(quest2);
		assertEquals(2, player.getquests().size());
		assertEquals(quest1, player.getquests().get(0));
		assertEquals(quest2, player.getquests().get(1));
	}
}
