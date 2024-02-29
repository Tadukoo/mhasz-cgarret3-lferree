package com.github.tadukoo.middle_earth.servlet;

import com.github.tadukoo.middle_earth.controller.Game;
import com.github.tadukoo.middle_earth.model.Constructs.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
        System.out.println("Inventory Servlet: doGet");
		
        //Load data for the initial call to the inventory jsp
		
        Game game = (Game) req.getSession().getAttribute("game");
        String command = (String) req.getSession().getAttribute("command");
        int counter = 1;
        
        List<Item> itemlist = game.getplayer().getinventory().getitems();
        ArrayList<Item> cleanList = new ArrayList<>();
	    for(Item item: itemlist){
		    cleanList.add(new Item(item.getItemWeight(), item.getattack_bonus(), item.getdefense_bonus(),
				    item.gethp_bonus(), item.getlvl_requirement(), item.getItemType(),
				    item.getName(), item.getID(), item.getShortDescription(), item.getLongDescription(),
				    item.getName().replaceAll(" ", "_")));
	    }
        
        req.setAttribute(("itemTest"), cleanList);
        
        req.setAttribute("numItems", counter);
        
        game.setmode("inventory");
        List<Item> inventory_list =  game.getplayer().getinventory().getitems();
        StringBuilder inventory_display_list = new StringBuilder();
	    for(Item item: inventory_list){
		    inventory_display_list.append(item.getName()).append(": ")
				    .append(item.getShortDescription()).append(";");
	    }
        	
        if(command == null){
        	req.setAttribute("inventory", inventory_display_list.toString());
        }else{
	        //Parses the command line and calls appropriate commands, returns the information requested in inventory, or the error messages associated with wrong calls.
        	String inventory_dialog = game.handle_command(command);
			
			req.setAttribute("inventory", inventory_display_list.toString());
        	req.setAttribute("inventory_dialog", inventory_dialog);
        }
        	// call JSP to generate the inventory page
        	req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("Inventory Servlet: doPost");
		
		Game game = (Game) req.getSession().getAttribute("game");
		game.setmode("inventory");
		// Gets the inventory of the player
		List<Item> inventory_list =  game.getplayer().getinventory().getitems();
		
		//Inventory is split into two display sections, the inventory list, then the responses to the commands in inventory
		StringBuilder inventory_display_list = new StringBuilder();
	    
	    for(Item item: inventory_list){
		    inventory_display_list.append(item.getName()).append(": ")
				    .append(item.getShortDescription()).append(";");
		}
		
		String command = (String) req.getSession().getAttribute("command");
		
		// Parses the command line and calls appropriate commands, returns the information requested in inventory, or the error messages associated with wrong calls.
		String inventory_dialog = game.handle_command(command);
		
		switch(game.getmode()){
			case "game":
				ArrayList<String> dialog = new ArrayList<>();
				dialog.add(game.getmapTile_name());
				dialog.add(game.getmapTile_longDescription());
				game.setdialog(dialog);
				req.setAttribute("dialog", game.getdialog());
				
				req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
				break;
			case "map":
				// TODO Implement
				throw new UnsupportedOperationException("Not implemented yet!");
				//req.getRequestDispatcher("/_view/map.jsp").forward(req, resp);
			case "character":
				// TODO Implement
				throw new UnsupportedOperationException("Not implemented yet!");
				//req.getRequestDispatcher("/_view/character.jsp").forward(req, resp);
			default:
				req.setAttribute("inventory", inventory_display_list.toString());
				req.setAttribute("inventory_dialog", inventory_dialog);
				// now call the JSP to render the new page
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
		}
    }
}