package com.github.tadukoo.middle_earth.model.Constructs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * These tests are meant to test solely Construct.
 */
public class ConstructTest{
	private Construct construct;
	
	@BeforeEach
	public void setup(){
		construct = new Construct();
	}
	
	@Test
	public void testSetID(){
		assertEquals(0, construct.getID());
		
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
