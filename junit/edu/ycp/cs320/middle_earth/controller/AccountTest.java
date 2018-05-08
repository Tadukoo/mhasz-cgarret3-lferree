package edu.ycp.cs320.middle_earth.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AccountTest{
	private Account account;
	
	@Before
	public void setup(){
		account = new Account();
		account.setusername(null);
		account.setgame_ids(null);
	}
	
	@Test
	public void testsetUsername(){
		// Set that name!
		account.setusername("Myself lol");
		
		// Check that name!
		assertEquals("Myself lol", account.getusername());
		
		// Set it again! (Checking against weird add stuff)
		account.setusername("Someone else");
		
		// Check it again!
		assertEquals("Someone else", account.getusername());
	}
	
	@Test
	public void testsetGame_ID(){
		// Set that ID!
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ints.add(1029);
		account.setgame_ids(ints);
		
		// Check that ID!
		assertEquals(1, account.getgame_ids().size());
		assertEquals(1029, (int) account.getgame_ids().get(0));
		
		// Set it again! (Checking against weird add stuff)
		ArrayList<Integer> ints2 = new ArrayList<Integer>();
		ints2.add(2938);
		ints2.add(5);
		account.setgame_ids(ints2);
		
		// Check it again!
		assertEquals(2, account.getgame_ids().size());
		assertEquals(2938, (int) account.getgame_ids().get(0));
		assertEquals(5, (int) account.getgame_ids().get(1));
	}
	
	@Test
	public void testCreate_Account(){
		// Try to create account and get response
		String response = account.create_account("New User", "New Password", "new_user@example.com");
		
		// Check that the response is a success
		assertEquals("", response);
	}
	
	@Test
	public void testCreate_AccountUsernameTaken(){
		// Try to create account and get response
		String response = account.create_account("lferree", "Imma_steal_his_acc_;)", "theREALtadukoo@something.com");
		
		// Check that the response is a failure
		assertEquals("Error", response);
	}
	
	@Test
	public void testCreate_AccountEmailTaken(){
		account.create_account("Totally New", "yeah_Im_new", "lferree@ycp.edu");
		
		// TODO: JUNIT: Test that it fails somehow? Perhaps an errorMessage in Account?
		throw new UnsupportedOperationException("Not sure how to test this yet... Doesn't matter though it's not implemented");
	}
	
	@Test
	public void testLogin(){
		// Run the login and get response
		String response = account.login("lferree", "password");
		
		// Check that response is accurate
		assertEquals("Success!", response);
		
		// TODO: JUNIT: Test that user_token and game_id are set
		//assertEquals(3, account.getuser_token());
		//assertEquals(1, account.getgame_id());
	}
	
	@Test
	public void testLoginUsernameDoesntExist(){
		// Run the login and get response
		String response = account.login("Derp Not Here", "anything_really");
		
		// Check that response is accurate
		assertEquals("Invalid Username or Password", response);
		
		// Check that user_token and game_id aren't set
		assertEquals(null, account.getusername());
		assertEquals(null, account.getgame_ids());
	}
	
	@Test
	public void testLoginIncorrectPassword(){
		// Run the login and get response
		String response = account.login("lferree", "not_my_password");
		
		// Check that response is accurate
		assertEquals("Invalid Username or Password", response);
		
		// Check that user_token and game_id aren't set
		assertEquals(null, account.getusername());
		assertEquals(null, account.getgame_ids());
	}
}
