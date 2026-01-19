package edu.pacific.comp55.starter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Timer;

import acm.graphics.GImage;


public class WaterElement {
	//public boolean activate;
	public GImage water;
	private AnimationPlayer anim;
	private double xPos = 0;
	private double yPos = 0;
	

	
	private Timer timer;
	private int timerLength = 1;
	private MainApplication program;
	private boolean right;
	private int velocity = 10;
	private LevelThree levelData;

	private static final String DIR = "media/sprites/elements/water/";
	private static final String WATER = "/water1.png";
	private String lor = "right";
	private AnimState lorAnim = AnimState.WATER_RIGHT;

	public WaterElement(MainApplication app, LevelThree level, double currentX, double currentY) {
		xPos = currentX;
		yPos = currentY;
		program = app;
		levelData = level;
		timer = new Timer(16, taskPerformed);
		water = new GImage(DIR + lor + WATER, xPos, yPos - 8);
		anim = new AnimationPlayer(water, lorAnim);
		//move();
	}
	
	public void setPos(double x, double y) {
		xPos = x;
		yPos = y;
		water.setLocation(xPos, yPos - 8);
	}
	
	public void move(boolean right) {
		timer.start();
		this.right = right;
	}

	public void createWater(boolean right) {
		if (!right) {
			System.out.println("Left");
			lor = "left";
			lorAnim = AnimState.WATER_LEFT;
			velocity *= -1;
		}
		else {
			lor = "right";
			lorAnim = AnimState.WATER_RIGHT;
			velocity = 10;
		}
		anim.setAnim(lorAnim);
		program.add(water);
		return;
	}  
	
	public double returnX() {
		return xPos;
	}
	
	public double returnY() {
		return yPos - 8;
	}

	ActionListener taskPerformed = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (timerLength == 1) {
				createWater(right);
			}
			else if(timerLength == 30) {
				//water.setVisible(false);
				program.remove(water);
				timerLength = 0;
				levelData.movingWater = false;
				timer.stop();
			}

			water.move(velocity, 0);
			xPos = water.getX();
			yPos = water.getY();
			timerLength += 1;
		}
	};
}
