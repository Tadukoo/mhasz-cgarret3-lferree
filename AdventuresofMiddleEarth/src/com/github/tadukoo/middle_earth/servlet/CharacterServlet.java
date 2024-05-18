package com.github.tadukoo.middle_earth.servlet;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.aome.character.Player;
import com.github.tadukoo.aome.construct.Item;
import com.github.tadukoo.aome.construct.ItemType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("Character Servlet: doGet");
		
		// Load data for the initial call to the inventory jsp
		Game game = (Game) req.getSession().getAttribute("game");
		String command = (String) req.getSession().getAttribute("command");
		Player player = (Player) game.getplayer();
		
		if(command != null && command.equalsIgnoreCase("cheatcodes!")){
			List<Item> allItems = game.cheatcode();
			player.setInventory(allItems);
		}
		
		req.setAttribute("sp", player.getskill_points());
		
		List<Item> itemlist = game.getplayer().getInventory();
		List<Item> cleanList = new ArrayList<>();
		for(Item item: itemlist){
			cleanList.add(new Item(
					item.getID(), item.getName(), item.getShortDescription(), item.getLongDescription(),
					item.getName().replaceAll(" ", "_"),
					item.getType(), item.getLevelRequirement(),
					item.getAttackBonus(), item.getDefenseBonus(), item.getHPBonus(), item.getWeight()));
		}
		
		req.setAttribute(("itemTest"), cleanList);
		
		game.setmode("character");
		
		req.setAttribute("attack", player.getAttack());
		req.setAttribute("coins", player.getCoins());
		req.setAttribute("defense", player.getDefense());
		req.setAttribute("experience", player.getExperience());
		req.setAttribute("gender", player.getGender());
		req.setAttribute("hp", player.getHP());
		req.setAttribute("level", player.getLevel());
		req.setAttribute("magic", player.getMP());
		req.setAttribute("name", player.getName());
		req.setAttribute("race", player.getRace());
		req.setAttribute("specialAttack", player.getSpecialAttack());
		req.setAttribute("specialDefense", player.getSpecialDefense());
		
		req.getRequestDispatcher("/_view/character.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("Character: doPost");
		
		Game game = (Game) req.getSession().getAttribute("game");
		Player player = (Player) game.getplayer();
		
		if(req.getParameter("remove") != null){
			req.getSession().setAttribute(req.getParameter("remove"), "");
			System.out.println(req.getParameter("remove"));
			String type = req.getParameter("remove").substring(0, req.getParameter("remove").length()-3);
			player.remove(type);
			
			req.setAttribute(req.getParameter("remove"), null);
		}
		
		if(req.getParameter("updateskillpoints")!=null &&
				req.getParameter("updateskillpoints").equalsIgnoreCase("true")){
			player.setAttack(Integer.parseInt(req.getParameter("attack")));
			player.setDefense(Integer.parseInt(req.getParameter("defense")));
			player.setSpecialAttack(Integer.parseInt(req.getParameter("specialattack")));
			player.setSpecialDefense(Integer.parseInt(req.getParameter("specialdefense")));
			player.setMP(Integer.parseInt(req.getParameter("magic")));
			player.setHP(Integer.parseInt(req.getParameter("hitpoints")));
			player.setskill_points(-(player.getskill_points()-Integer.parseInt(req.getParameter("skillpoints"))));
			req.setAttribute("updateskillpoints", "false");
		}
		
		req.setAttribute("sp", player.getskill_points());
		
		List<Item> itemlist = game.getplayer().getInventory();
		List<Item> cleanList = new ArrayList<>();
		for(Item item: itemlist){
			cleanList.add(new Item(
					item.getID(), item.getName(), item.getShortDescription(), item.getLongDescription(),
					item.getName().replaceAll(" ", "_"),
					item.getType(), item.getLevelRequirement(),
					item.getAttackBonus(), item.getDefenseBonus(), item.getHPBonus(), item.getWeight()));
		}
		
		for(Item item: cleanList){
			if(item.getName().equals(req.getParameter("head"))){
				if(item.getType() == ItemType.HELM){
					game.getplayer().setHelm(item);
					req.getSession().setAttribute("headIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only a Hemlet can be equiped there.");
				}
			}else if(item.getName().equals(req.getParameter("chest"))){
				if(item.getType() == ItemType.CHEST){
					game.getplayer().setChest(item);
					req.getSession().setAttribute("chestIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only a Chestplate can be equiped there.");
				}
			}else if(item.getName().equals(req.getParameter("arms"))){
				if(item.getType() == ItemType.BRACES){
					game.getplayer().setBraces(item);
					req.getSession().setAttribute("armsIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only arm braces can be equiped there.");
				}
			}else if (item.getName().equals(req.getParameter("lhand"))){
				if(item.getType() == ItemType.L_HAND){
					game.getplayer().setLeftHand(item);
					req.getSession().setAttribute("lhandIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only left handed items can be equiped there.");
				}
			}else if (item.getName().equals(req.getParameter("rhand"))){
				if(item.getType() == ItemType.R_HAND){
					game.getplayer().setRightHand(item);
					req.getSession().setAttribute("rhandIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only right handed items can be equiped there.");
				}
			}else if(item.getName().equals(req.getParameter("legs"))){
				if(item.getType() == ItemType.LEGS){
					game.getplayer().setLegs(item);
					req.getSession().setAttribute("legsIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only leg guards can be equiped there.");
				}
			}else if(item.getName().equals(req.getParameter("boots"))){
				if(item.getType() == ItemType.BOOTS){
					game.getplayer().setBoots(item);
					req.getSession().setAttribute("bootsIMG", item.getDescriptionUpdate());
				}else{
					req.setAttribute("errorMessage", "Only boots can be equiped there.");
				}
			}
		}
		
		req.setAttribute(("itemTest"), cleanList);
		
		game.setmode("character");
		
		req.setAttribute("attack", player.getAttack());
		req.setAttribute("coins", player.getCoins());
		req.setAttribute("defense", player.getDefense());
		req.setAttribute("experience", player.getExperience());
		req.setAttribute("gender", player.getGender());
		req.setAttribute("hp", player.getHP());
		req.setAttribute("level", player.getLevel());
		req.setAttribute("magic", player.getMP());
		req.setAttribute("name", player.getName());
		req.setAttribute("race", player.getRace());
		req.setAttribute("specialAttack", player.getSpecialAttack());
		req.setAttribute("specialDefense", player.getSpecialDefense());
		
		req.getRequestDispatcher("/_view/character.jsp").forward(req, resp);
	}
	
}
