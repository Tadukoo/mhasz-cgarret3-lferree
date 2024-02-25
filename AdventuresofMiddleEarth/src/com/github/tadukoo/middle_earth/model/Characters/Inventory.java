package com.github.tadukoo.middle_earth.model.Characters;

import java.util.ArrayList;
import java.util.List;

import com.github.tadukoo.middle_earth.model.Constructs.Item;

public class Inventory{
	private List<Item> items;
	private int weight;
	private int inventory_id;
	
	public Inventory(){
		items = new ArrayList<>();
	};
	
	public List<Item> getitems(){
		return items;
	}
	
	public void setitems(List<Item> items){
		this.items = items;
	}
	
	public int getweight(){
		return weight;
	}
	
	public void setweight(int weight){
		this.weight = weight;
	}
	
	public int getinventory_id() {
		return this.inventory_id;
	}
	
	public void setinventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}
}
