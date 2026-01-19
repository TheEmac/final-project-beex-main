package edu.pacific.comp55.starter;

import java.awt.Color;

import acm.graphics.GRect;
public class Obstacle {
		private int xPos;
		private int yPos;
		
		public GRect texture;

		public Obstacle(int xPos, int yPos,int length){
			this.xPos = xPos;
			this.yPos = yPos;
			
			texture = new GRect(xPos,yPos,length,length);
			texture.setFilled(true);
			//Just setting the color to blue so it's more easily visible -Perez
			texture.setColor(Color.BLUE);
			}
		public Obstacle(int xPos, int yPos,int length, int width){
			this.xPos = xPos;
			this.yPos = yPos;
			
			texture = new GRect(xPos,yPos,width,length);
			texture.setFilled(true);
			//Just setting the color to blue so it's more easily visible -Perez
			texture.setColor(Color.BLUE);
			}
		public void setLocation(int x, int y) {
			xPos = x;
			yPos = y;
			texture.setLocation(xPos, yPos);
		}
		
		public GRect getObstacle() {
			return texture;
		}
		public void addObstacletoScreen(MainApplication program) {
			program.add(texture);
		}
		public void removeObstacletoScreen(MainApplication program) {
			program.remove(texture);
		}





}


