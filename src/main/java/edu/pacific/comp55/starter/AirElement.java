package edu.pacific.comp55.starter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GImage;

public class AirElement {
	//public boolean doubleJump;
	private GImage placeHolder;
	private double xPos = 0;
	private double yPos = 0;
	private Timer timer;
	private int numTimes = 1;
	private MainApplication program;
	

	public AirElement(MainApplication app, double currentX, double currentY, Character character) {
		xPos = currentX;
		yPos = currentY;
		placeHolder = new GImage("media/character.png", xPos, yPos + 30);
		program = app;
		program.add(placeHolder);
		
		//move();
	}

	public void doubleJump(boolean jump) {
		timer = new Timer(30, taskPerformed);
		timer.start();
	}

	ActionListener taskPerformed = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			numTimes += 1;
			if(numTimes == 10) {
				//placeHolder.setVisible(false);
				program.remove(placeHolder);
				timer.stop();
			}
			placeHolder.move(0, -10);

		}
	};
}
