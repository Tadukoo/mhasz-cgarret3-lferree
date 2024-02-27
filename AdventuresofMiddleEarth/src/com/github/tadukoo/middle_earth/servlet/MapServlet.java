package com.github.tadukoo.middle_earth.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MapServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("Map Servlet: doGet");
		
		//Load data for the initial call to the inventory jsp
		// Game game = (Game) req.getSession().getAttribute("game");
		
		/*
		for( MapTile tile : game.get_map().getMapTiles()) {
			String attrName = "tile"+tile.getID();
			req.setAttribute(attrName, tile.getVisited());
			System.out.println(attrName + ": " + tile.getVisited());
		}
		*/
		
		req.getRequestDispatcher("/_view/map.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("Map Servlet: doPost");
	}
}
