package com.github.tadukoo.middle_earth.images;

import java.io.*;

import javax.imageio.ImageIO;

import com.github.tadukoo.aome.construct.map.MapTile;
import com.github.tadukoo.util.StringUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class used for displaying the Map using tiles
 *
 * @author Logan Ferree (Tadukoo)
 * @author Matt Hasz (mhasz239)
 * @version 2.0
 * @since 1.0 or earlier
 */
public class TileMap {
	private int x;
	private int y;
	
	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	private int player_x;
	private int player_y;
	
	public TileMap(String s, int tileSize, MapTile tile) {
		this.tileSize = tileSize;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(s));
			player_y = Integer.parseInt(br.readLine());
			player_x = Integer.parseInt(br.readLine());
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];
			
			String delimiters = " ";
			for (int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for (int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			String imgFile = getMapTileString(tile);
			map[player_y][player_x] = Integer.parseInt(imgFile);
			
			
			br.close();
			
		} catch (Exception e) {}
		
		
		}
		
	public void update(String direction, MapTile tile) {
		if (direction == "north") {
			player_y--;
		} else if (direction == "south") {
			player_y++;
		} else if (direction == "east") {
			player_x++;
		} else if (direction == "west") {
			player_x--;
		} else if (direction == "northeast") {
			player_y--;
			player_x++;
		} else if (direction == "northwest") {
			player_y--;
			player_x--;
		} else if (direction == "southeast") {
			player_y++;
			player_x++;
		} else if (direction == "southwest") {
			player_y++;
			player_x--;
		}
		map[player_y][player_x] = Integer.parseInt(getMapTileString(tile));
			
	}

	public void draw(Graphics2D g) {
		for (int row = 0; row < mapHeight; row++){
			for (int col = 0; col < mapWidth; col++) {
				int rc = map[row][col];
				if (rc == 0) {
					g.setColor(Color.BLACK);
					g.fillRect(x + col * tileSize,  y + row * tileSize,  tileSize,  tileSize);
				}
				else {
					File currentDirFile = new File(".");
					String path = currentDirFile.getAbsolutePath();
					path = path.substring(0, path.length()-1);
					String mapPath = path + "src/edu/ycp/cs320/middle_earth/images/mapTiles/"+rc+".png";
					String playerPath = path + "src/edu/ycp/cs320/middle_earth/images/player.png";
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(mapPath));
					 
						g.drawImage(img, x + col * tileSize, y + row * tileSize, x + (col+1) * tileSize, y + (row+1) * tileSize, 0, 0, 100, 100, null);
						
						if (col == player_x && row == player_y) {
							img = ImageIO.read(new File(playerPath));
							g.drawImage(img, x + col * tileSize, y + row * tileSize, x + (col+1) * tileSize, y + (row+1) * tileSize, 0, 0, 100, 100, null);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}
	}
	
	public void save(String fileLocation){
		try {
			PrintWriter write = new PrintWriter(fileLocation);
			write.println(player_y);
			write.println(player_x);
			write.println(mapWidth);
			write.println(mapHeight);
			for (int row = 0; row < mapHeight; row++){
				for (int col = 0; col < mapWidth; col++) {
					if (col == mapWidth - 1) {
						write.print(map[row][col]);
					} else {
						write.print(map[row][col] + " ");
					}
				}
				if(row != mapHeight - 1){
					write.println();
				}
			}
			write.close();
		} catch (FileNotFoundException e) {
			String newFolder = fileLocation.substring(0, fileLocation.length()-12);
			boolean success = new File(newFolder).mkdir();
			if (success) {
				save(fileLocation);
			}
		}
		
	}
	
	private String getMapTileString(MapTile tile){
		String connection = "";
		if(tile.getNorthwestConnection() == null){
			connection = connection + "1";
		}
		if(tile.getNorthConnection() == null){
			connection = connection + "2";
		}
		if(tile.getNortheastConnection() == null){
			connection = connection + "3";
		}
		if(tile.getEastConnection() == null){
			connection = connection + "4";
		}
		if(tile.getSoutheastConnection() == null){
			connection = connection + "5";
		}
		if(tile.getSouthConnection() == null){
			connection = connection + "6";
		}
		if(tile.getSouthwestConnection() == null){
			connection = connection + "7";
		}
		if(tile.getWestConnection() == null){
			connection = connection + "8";
		}
		if(StringUtil.isBlank(connection)){
			connection = "0";
		}
		return connection;
	}
}
