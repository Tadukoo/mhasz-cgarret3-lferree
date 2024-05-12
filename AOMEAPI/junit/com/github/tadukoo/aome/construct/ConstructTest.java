package com.github.tadukoo.aome.construct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * These tests are meant to test solely Construct.
 */
public class ConstructTest{
	private Construct construct;
	
	private static class ConstructImpl extends Construct{
		
		@Override
		public String getTableName(){
			return "Test";
		}
	}
	
	@BeforeEach
	public void setup(){
		construct = new ConstructImpl();
	}
	
	@Test
	public void testSetID(){
		assertFalse(construct.hasItem(Construct.ID_COLUMN_NAME));
		
		construct.setID(1);
		
		assertEquals(1, construct.getID());
	}
	
	@Test
	public void testSetName(){
		assertNull(construct.getName());
		
		construct.setName("Millennium Falcon Cockpit");
		
		assertEquals("Millennium Falcon Cockpit", construct.getName());
	}
	
	@Test
	public void testSetShortDescription(){
		assertNull(construct.getShortDescription());
		
		construct.setShortDescription("What do you think it is?");
		
		assertEquals("What do you think it is?", construct.getShortDescription());
	}
	
	@Test
	public void testSetLongDescription(){
		assertNull(construct.getLongDescription());
		
		construct.setLongDescription("Here you are: The cockpit of Han Solo's Millennium Falcon.");
		
		assertEquals("Here you are: The cockpit of Han Solo's Millennium Falcon.", construct.getLongDescription());
	}
}