package com.github.tadukoo.middle_earth.model.Characters;

import com.github.tadukoo.middle_earth.model.Constructs.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest{
	private Vendor vendor;
	
	@BeforeEach
	public void setup(){
		vendor = new Vendor();
	}
	
	@Test
	public void testAdd_Item_Price(){
		Item sword = new Item();
		sword.setName("Sword");
		int price = 50;
		
		vendor.add_item_price(sword, price);
		
		assertEquals(price, vendor.get_item_price(sword));
	}
}
