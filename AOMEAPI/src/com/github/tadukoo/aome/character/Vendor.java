package com.github.tadukoo.aome.character;

import java.util.HashMap;

import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.construct.Item;

/**
 * Vendor represents a shop {@link Character} in the game
 *
 * @author Logan Ferree (Tadukoo)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class Vendor extends Character{
	private HashMap<Item, Integer> item_prices;
	
	public Vendor(){
		item_prices = new HashMap<Item, Integer>();
	};
	
	/** {@inheritDoc} */
	@Override
	public String getTableName(){
		return "Vendors";
	}
	
	public int get_item_price(Item item){
		return item_prices.get(item);
	}
	
	public void add_item_price(Item item, int price){
		item_prices.put(item, price);
	}
	
	// Player buys item from Vendor
	public void buy_item(Item item){
		// TODO Implement
		throw new UnsupportedOperationException("Not implemented yet!");
	}
	
	// Player sells item to Vendor
	public void sell_item(Item item){
		// TODO Implement
		throw new UnsupportedOperationException("Not implemented yet!");
	}
}
