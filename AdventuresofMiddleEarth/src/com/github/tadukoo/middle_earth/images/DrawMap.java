package com.github.tadukoo.middle_earth.images;

import javax.swing.JFrame;

import com.github.tadukoo.aome.construct.map.MapTile;

public class DrawMap {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Map");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapTile tile = new MapTile();
		tile.setNorthConnection(3);
		tile.setNortheastConnection(4);
		tile.setEastConnection(1);
		window.setContentPane(new MapPanel(tile));
		window.pack();
		window.setVisible(true);
		
	}
	
}
