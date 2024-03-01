package com.github.tadukoo.middle_earth.persist;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.aome.InitialData;
import com.github.tadukoo.aome.ReadCSV;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitialDataTest{
	
	private ArrayList<Item> getActualItems() throws Exception{
		ArrayList<Item> items = new ArrayList<>();
		
		ReadCSV itemsReader = new ReadCSV("items.csv");
		List<String> tuple = itemsReader.next();
		while(tuple != null){
			Item item = new Item();
			item.setName(tuple.get(0));
			item.setLongDescription(tuple.get(1));
			item.setShortDescription(tuple.get(2));
			item.setDescriptionUpdate(tuple.get(3));
			item.setAttackBonus(Integer.parseInt(tuple.get(4)));
			item.setDefenseBonus(Integer.parseInt(tuple.get(5)));
			item.setHPBonus(Integer.parseInt(tuple.get(6)));
			item.setWeight(Integer.parseInt(tuple.get(7)));
			item.setType(ItemType.valueOf(tuple.get(8)));
			item.setLevelRequirement(Integer.parseInt(tuple.get(9)));
			
			tuple = itemsReader.next();
		}
		itemsReader.close();
		
		return items;
	}
	
	@Test
	public void testGetItems() throws Exception{
		List<Item> actualItems = getActualItems();
		List<Item> testItems = InitialData.getItems();
		for(int i = 0; i < actualItems.size(); i++){
			assertEquals(actualItems.get(i), testItems.get(i));
		}
	}
}
