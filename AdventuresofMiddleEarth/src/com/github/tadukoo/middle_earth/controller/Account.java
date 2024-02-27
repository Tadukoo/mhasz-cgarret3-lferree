package com.github.tadukoo.middle_earth.controller;

import com.github.tadukoo.middle_earth.persist.DatabaseProvider;
import com.github.tadukoo.middle_earth.persist.IDatabase;
import com.github.tadukoo.middle_earth.persist.pojo.DatabaseResult;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Account{
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
			"[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
	private String username;
	private ArrayList<Integer> game_ids;
	private int current_game;
	
	
	
	public Account(String username){
		this.username = username;
	}
	
	public Account() {
	}

	public String getusername(){
		return username;
	}
	
	public void setusername(String username){
		this.username = username;
	}
	
	public ArrayList<Integer> getgame_ids(){
		return game_ids;
	}
	
	public void setgame_ids(ArrayList<Integer> game_ids){
		this.game_ids = game_ids;
	}
	
	public int getcurrent_game(){
		return current_game;
	}
	
	public void setcurrent_game(int id) {
		this.current_game = id;
	}
	
	public String usernameCheck(String username, IDatabase db){
		DatabaseResult<Boolean> result = db.doesUsernameExist(username);
		if(result.success() && result.result()){
			return "\nSorry that Username is already taken.";
		}else if(!result.success()){
			return "\n" + result.errorMessage();
		}else{
			return "Passes";
		}
	}
	
	public String emailCheck(String email, IDatabase db){
		// Check email is not empty
		if(StringUtil.isBlank(email)){
			return "\nPlease enter an email address.";
		}
		
		// Check it's a valid email address
		if(!EMAIL_PATTERN.matcher(email).matches()){
			return "\nPlease enter a valid email address.";
		}
		
		// Check if email is in use
		DatabaseResult<Boolean> emailInUse = db.isEmailInUse(email);
		if(!emailInUse.success()){
			return "\n" + emailInUse.errorMessage();
		}else if(emailInUse.result()){
			return "\nSorry it seems you've already have an account with that email.";
		}else{
            return "Passes";
        }
    }
	
	public String passwordCheck(String password){
		boolean numCheck = false;
		for (int i = 0; i < password.length(); i++) {
			try{
				Integer.parseInt(password.substring(i, i+1));
				numCheck = true;
			} catch (NumberFormatException e) {
				// Do nothing needs to only pass once. 
			}
		}
		
		if (password.length() < 6 || !numCheck) {
			return "\nPassword needs to be atleast 6 characters long and atleast 1 alpha-numeric value.";
		} else {
			return "Passes";
		}
	}
	
	public String create_account(String username, String password, String email){
		// Grab the database and run checks
		IDatabase db = DatabaseProvider.getInstance();
		String error = "";
		String usernameCheck = usernameCheck(username, db);
		String passwordCheck = passwordCheck(password);
		String emailCheck = emailCheck(email, db);
		
		// Combine any errors hit
		if(!usernameCheck.equals("Passes")){
			error += usernameCheck;
		}
		if(!passwordCheck.equals("Passes")){
			error += passwordCheck;
		}
		if(!emailCheck.equals("Passes")){
			error += emailCheck;
		}
		
		// Create a New User if all checks pass, return errors if anything goes wrong
		if(StringUtil.isBlank(error)){
			DatabaseResult<Boolean> result = db.createNewUser(username, password, email);
			if(result.success()){
				return "Successful";
			}else{
				return result.errorMessage();
			}
		}else{
			return error;
		}
	}
	
	public String login(String username, String password){
		IDatabase db = DatabaseProvider.getInstance();
		
		DatabaseResult<String> passwordResult = db.getUserPasswordByUsername(username);
		if(passwordResult.success() && password.equals(passwordResult.result())){
			setusername(username);
			return "Success!";
		}else{
			return "Invalid Username or Password";
		}
	}
}
