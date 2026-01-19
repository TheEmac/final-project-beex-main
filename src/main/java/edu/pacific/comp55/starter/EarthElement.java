package edu.pacific.comp55.starter;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.world.World;

import acm.graphics.GImage;


public class EarthElement {
	private World<Body> levelWorld;
	private Obstacle o;
	private Body obstacle;
	private GImage earth;
	private double xPos;
	private double yPos;
	private Timer timer;
	private int numTimes = 1;
	private MainApplication program;
	private ArrayList<Obstacle> obstaclesInLevel;
	private int earthWidth = 56;
	private int earthLength = 24;
	private boolean paused;
	private SfxPlayer sfx = new SfxPlayer();

	public EarthElement(MainApplication app, double currentX, double currentY, World<Body> levelWorld) {
		program = app;
		xPos = currentX;
		yPos = currentY;
		this.levelWorld = levelWorld;
		
		earth = new GImage("media/sprites/elements/earth.png", xPos , yPos + 50);
		program.add(earth);
		timer = new Timer(16, taskPerformed);
		obstaclesInLevel = new ArrayList<>();
		obstaclesInLevel.add(createObstacle((int)xPos + 10 , (int)yPos - 30 , earthLength, earthWidth));
		timer.start();
	}

	private Obstacle createObstacle(int x, int y, int length, int width) {
		o = new Obstacle(x,y,length,width);
		obstacle = new Body();
		obstacle.addFixture(new BodyFixture(new Rectangle(width,length)));
		obstacle.setMass(MassType.INFINITE);
		obstacle.translate(x+(width/2.0), -1* ( y+(length/2.0)));
		levelWorld.addBody(obstacle);
		return o;
	}

	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	ActionListener taskPerformed = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (paused) {
				return;
			}
			
			numTimes += 1;
			if(earth.getY() >= yPos - 20) {
				earth.move(0, -10);
			} else if(numTimes >= 100){
				sfx.crumblingEarthSound();
				program.remove(earth);
				levelWorld.removeBody(obstacle);
				obstaclesInLevel.remove(o);
				timer.stop();
			}

		}
	};

}
