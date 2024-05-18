package com.github.tadukoo.middle_earth.servlet;

import com.github.tadukoo.middle_earth.controller.GameController;
import com.github.tadukoo.aome.character.Character;
import com.github.tadukoo.aome.construct.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet{
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        System.out.println("Game Servlet: doGet");
        
        GameController game = (GameController) req.getSession().getAttribute("game");
        String command = (String) req.getSession().getAttribute("command");
        
        game.setmode("game");
        if(command != null){
        	// holds the error message text, if there is any
        	String errorMessage = game.handle_command(command);
        	if(errorMessage != null){
        		game.add_dialog(errorMessage);
        	}
        }else{
        	game.add_dialog(game.getmapTile_name());
        	game.add_dialog(game.getmapTile_longDescription());
        }
        req.setAttribute("mode", game.getmode());
        req.setAttribute("dialog", game.getdisplay_text());
        // call JSP to generate empty form
        
        if(game.getmode().equalsIgnoreCase("combat")){
        	req.setAttribute("dialog", game.getcombat_text());
        	// Set the players in combat mode
    		
    		/* Need to split get enemyID's and getPlayerID's in CombatMode
    		Player p1 = null;
    		Player p2 = null;
    		ArrayList<Integer> players = game.getBattle().getPlayerIDs()
    		for (int i = 0; i < players.size(); i++) {
    			if (i == 0) {
    				p1 = (Player) game.getcharacters().get(players.get(i));
    			} else if (i == 1) {
    				p2 = (Player) game.getcharacters().get(players.get(i));
    			} 
    		}
    		
    		if (p1 != null) {
    			req.setAttribute("p1", p1);
    			req.setAttribute("p1Name", p1.getname());
    			req.setAttribute("p1Health", p1.gethit_points());
    		}
    		if (p2 != null) {
    			req.setAttribute("e2", p2);
    			req.setAttribute("e2Name", p2.getname());
    			req.setAttribute("e2Health", p2.gethit_points());
    		}
    		*/
    		
    		//Set the Enemies in combat mode
    		Character e1 = null;
    		Character e2 = null;
    		Character e3 = null;
    		ArrayList<Integer> enemy = game.getBattle().getCharacterIDs();
    		for(int i = 0; i < enemy.size(); i++){
    			if(i == 0){
    				req.setAttribute("p1", "player1");
    				req.setAttribute("p1Name", game.getplayer().getName());
    				req.setAttribute("p1Health", game.getplayer().getHP());
    			}else if(i == 1){
    				e1 = game.getcharacters().get(enemy.get(i));
    			}else if(i == 2){
    				e2 = game.getcharacters().get(enemy.get(i));
    			}else if(i == 3){
    				e3 = game.getcharacters().get(enemy.get(i));
    			}
    		}
			
    		if(e1 != null){
    			req.setAttribute("e1", e1);
    			req.setAttribute("e1Name", e1.getName());
    			req.setAttribute("e1Health", e1.getHP());
    			req.setAttribute("e1Race", e1.getRace().replaceAll(" ", ""));
    		}
    		if(e2 != null){
    			req.setAttribute("e2", e2);
    			req.setAttribute("e2Name", e2.getName());
    			req.setAttribute("e2Health", e2.getHP());
    			req.setAttribute("e2Race", e2.getRace().replaceAll(" ", ""));
    		}
    		if(e3 != null){
    			req.setAttribute("e3", e3);
    			req.setAttribute("e3Name", e3.getName());
    			req.setAttribute("e3Health", e3.getHP());
    			req.setAttribute("e3Race", e3.getRace().replaceAll(" ", ""));
    		}
        	req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
        }else{
        	req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        System.out.println("Game Servlet: doPost");
        
        GameController game = (GameController) req.getSession().getAttribute("game");
        game.setmode("game");
        
        // holds the error message text, if there is any
        String errorMessage = null;
        String display_text = null;
        String command = req.getParameter("command");
        errorMessage = game.handle_command(command);
        if (errorMessage != null) {
        	game.add_dialog(errorMessage);
        }
        
        display_text = game.getdisplay_text();
        
		switch(game.getmode()){
			case "inventory":
				List<Item> inventory_list =  game.getplayer().getInventory();
				
				StringBuilder inventory_display_list = new StringBuilder();
				for(Item item: inventory_list){
					inventory_display_list.append(item.getName()).append(": ")
							.append(item.getShortDescription()).append(";");
				}
				
				req.setAttribute("inventory", inventory_display_list.toString());
				req.setAttribute("mode", game.getmode());
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
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
				req.setAttribute("dialog", display_text);
				req.setAttribute("mode", game.getmode());
				// now call the JSP to render the new page
				req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		}
    }
}
