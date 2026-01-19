package edu.pacific.comp55.starter;

import acm.graphics.GImage;


public class Character2{

		public GImage Cicon;
		public double xPos;
		public double yPos;
		
		public AnimationPlayer anim;
		public SfxPlayer sfx;


		public Character2 (GImage icon, int x, int y, boolean gravity) {
			Cicon = icon;
			xPos = x;
			yPos = y;

			Cicon.setLocation(xPos, yPos);

			anim = new AnimationPlayer(Cicon, AnimState.IDLE_RIGHT);
			sfx = new SfxPlayer();
		}
		public void updateIconLocation(double x,double y) {
			xPos = x;
			yPos = y;

			Cicon.setLocation(xPos, yPos);
		}

		public double getX() {
			return xPos;
		}

		public double getY() {
			return yPos;
		}
		public void useElement() {
			System.out.println("Element used");
		}
		public void printPosition() {
			System.out.println("Graphic Location: " + xPos + " " + yPos);
		}

		public static void main(String[] args) {
			//TODO
		}


}
