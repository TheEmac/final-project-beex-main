package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.world.World;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;


public class LevelTwo extends GraphicsPane implements Interfaceable {
	private static final int FPS = 16; //60fps, a frame every 16ms
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean jump = false;
	private boolean grounded = true;
	
	private boolean facingRight = true;
	private World<Body> levelWorld;
	

	private boolean spawnEarth;

	private Timer localTimer;
	private int earthCooldown = 101;


	public boolean complete;
	public Character2 player;
	public Body playerBody;
	private MainApplication program;
	private ArrayList<Obstacle> obstaclesInLevel;
	private ArrayList<Body> bodiesInLevel;
	private SoundPlayer levelTheme;
	EarthElement earth;
	private boolean paused = false;
	
	private GImage bg;
	private GImage graphics;
	
	private GObject doorCheck;
	private GImage door;
	private AnimationPlayer doorAnim;
	private boolean once = false;
	private boolean interact = false;
	
	private PauseMenu pauseMenu;

	private static final GImage PLAYER_ICON = new GImage("media/sprites/character/idle/right/char_idle1.png");

	public LevelTwo(MainApplication app) {
		super();
		program = app;
		player = new Character2(PLAYER_ICON, 100, 100, true);
		levelWorld = new World<>();
		pauseMenu = new PauseMenu(program);
		obstaclesInLevel = new ArrayList<>();
		bodiesInLevel = new ArrayList<>();
		localTimer = new Timer(FPS, gameLoop);
		levelTheme = new SoundPlayer(0, "earth_theme.wav", 0.75f, true);
		levelTheme.stop();
		bg = new GImage("media/bg.png", 0, 0);
		graphics = new GImage("media/sprites/environment/levels/level3_layout.png", 0, 0);
		door = new GImage("media/sprites/environment/door/door1.png", 8, 32);
		doorAnim = new AnimationPlayer(door, AnimState.DOOR_CLOSED);
	}
	
	public void startLevel() {
		player.anim.setAnim(AnimState.IDLE_RIGHT);
		initializeLevel();
	}

	private void createWorldBoundaries() {
		GRect rectGraphic1 = new GRect(800,20);
		GRect rectGraphic2 = new GRect(800,20);
		GRect rectGraphic3 = new GRect(20,600);
		GRect rectGraphic4 = new GRect(20,600);
		AABB bounds = new AABB(0);

		Body floor = new Body();
		floor.addFixture(new BodyFixture(new Rectangle(800.0,20.0)));
		floor.translate(400,-550);
		floor.setMass(MassType.INFINITE);
		bounds = floor.createAABB();
		rectGraphic2.setLocation(bounds.getMinX(),bounds.getMinY()*-1-18);
		System.out.println(bounds.getMinX() + " " + bounds.getMinY()*-1);
		rectGraphic2.setColor(Color.BLUE);
		rectGraphic2.setFilled(true);
		levelWorld.addBody(floor);
		//create right wall

		Body rightWall = new Body();
		rightWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		rightWall.translate(800,-300);
		rightWall.setMass(MassType.INFINITE);
		bounds = rightWall.createAABB();
		rectGraphic3.setLocation(bounds.getMinX(),bounds.getMaxY()*-1);
		System.out.println(bounds.getMinX() + " " + bounds.getMaxY()*-1);
		rectGraphic3.setColor(Color.red);
		levelWorld.addBody(rightWall);

		//create Leftwall
		Body leftWall = new Body();
		leftWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		leftWall.translate(-10,-300);
		leftWall.setMass(MassType.INFINITE);
		bounds = leftWall.createAABB();
		rectGraphic4.setLocation(bounds.getMinX(),bounds.getMinY()*-1);
		System.out.println(bounds.getMinX() + " " + bounds.getMinY()*-1);
		rectGraphic4.setColor(Color.GREEN);
		levelWorld.addBody(leftWall);

		Body roof = new Body();
		roof.addFixture(new BodyFixture(new Rectangle(800.0,20.0)));
		roof.translate(400,20);
		roof.setMass(MassType.INFINITE);
		bounds = roof.createAABB();
		rectGraphic1.setLocation(bounds.getMinX(),bounds.getMinY()* -1 );
		rectGraphic1.setColor(Color.gray);
		levelWorld.addBody(roof);

//		program.add(rectGraphic4);
//		program.add(rectGraphic3);
		program.add(rectGraphic2);
//		program.add(rectGraphic1);
	}
	private Obstacle createObstacle(int x, int y, int length, int width) {
		Obstacle o = new Obstacle(x,y,length,width);
		Body obstacle = new Body();
		obstacle.addFixture(new BodyFixture(new Rectangle(width,length)));
		obstacle.setMass(MassType.INFINITE);
		obstacle.translate(x+(width/2.0),-1* ( y+(length/2.0)));
		bodiesInLevel.add(obstacle);
		levelWorld.addBody(obstacle);
		return o;
	}
	private void initializeLevel() {
		obstaclesInLevel.add(createObstacle(150,425,120,64));
		obstaclesInLevel.add(createObstacle(350,425, 24,64));
		obstaclesInLevel.add(createObstacle(550,425,120,64));
		obstaclesInLevel.add(createObstacle(720,325, 24,64));
		obstaclesInLevel.add(createObstacle(720,200, 24,64));
		obstaclesInLevel.add(createObstacle(550,150, 24,64));
		obstaclesInLevel.add(createObstacle(400,150, 24,64));
		obstaclesInLevel.add(createObstacle(250,250, 24,64));
		obstaclesInLevel.add(createObstacle(125,150, 24,64));
		obstaclesInLevel.add(createObstacle(0 ,75, 24,64));
		
	}

	private void initializePlayerBody() {
		playerBody = new Body();
		playerBody.addFixture(new BodyFixture(new Rectangle(32.0,32.0)));
		playerBody.setMass(MassType.NORMAL);
		playerBody.translate(50.0,-500.0);
		playerBody.setGravityScale(1);
		playerBody.setLinearDamping(1);
		playerBody.setAtRest(false);

		levelWorld.addBody(playerBody);

	}


	public boolean winCheck() {
		doorCheck = program.getElementAt(player.getX(), player.getY() - 2);
		if (doorCheck == door) {
			if (once == false) {
				player.sfx.doorOpen();
				once = true;
			}
			return true;
		}
		return false;
	}
	
	public void endLevel() {
		obstaclesInLevel.clear();
		bodiesInLevel.clear();
		levelWorld.removeAllBodies();
		levelTheme.stop();
		localTimer.stop();
		program.removeAll();
	}
	
	public void win() {
		endLevel();
		program.switchToVictoryScreen3();
	}
	
	public void pauseGame() {
		player.anim.stopAnim();
		if (earth != null) {
			earth.pause();
		}
		levelTheme.pause();
		paused = true;
		pauseMenu.pause();
	}
	
	public void resumeGame() {
		player.anim.startAnim();
		if (earth != null) {
			earth.resume();
		}
		levelTheme.resume();
		paused = false;
		pauseMenu.resume();
	}

	public void initLevel() {
		startLevel();
		createWorldBoundaries();
		initializePlayerBody();
		localTimer.start();
		levelTheme.start();

		program.add(bg);
		
		for (Obstacle c: obstaclesInLevel) {
			program.add(c.texture);
		}
		program.add(graphics);
		program.add(door);
		program.add(PLAYER_ICON);
		
	}

	public void movePlayer() {
		//TODO
		playerBody.setAtRest(false);
		player.updateIconLocation(playerBody.getWorldCenter().x-16,-1*playerBody.getWorldCenter().y-16);
	}

	@Override
	public void showContents() {
		//Since showContents is the first thing that's called when the level screen is created, I just had it call initLevel -Perez
		initLevel();
	}

	@Override
	public void hideContents() {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == ControlButtons.ESCAPE.toInt()) {
			if (!paused) {
				pauseGame();
				return;
			}
			resumeGame();
		}
		
		if ((e.getKeyCode() == ControlButtons.VK_RIGHT.toInt())) {
			moveRight = true;
			facingRight = true;
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = true;
			facingRight = false;
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = true;
		}
		if (e.getKeyCode() == ControlButtons.VK_DOWN.toInt()) {
			spawnEarth = true;
		}
		
		if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			interact = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == ControlButtons.VK_RIGHT.toInt()) {
			moveRight = false;
			facingRight = true;
			player.anim.setAnim(AnimState.IDLE_RIGHT);
			player.sfx.sfxCount = 0;
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = false;
			facingRight = false;
			player.anim.setAnim(AnimState.IDLE_LEFT);
			player.sfx.sfxCount = 0;
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = false;
		}
		
		if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			interact  = false;
		}
		
	}

	private int directionFacing() {
		if (facingRight) {
			return 1;
		}
		return -1;
	}



	ActionListener gameLoop = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (paused) {
				return;
			}
			
			if (winCheck()) {
				doorAnim.setAnim(AnimState.DOOR_OPEN, false);
			}
			
			if (winCheck() && interact) {
				win();
			}
			
			earthCooldown++;
			if (earthCooldown > 500) {
				earthCooldown = 101;
			}

			if (moveRight) {
				playerBody.setLinearVelocity(15.0,playerBody.getLinearVelocity().y);
				playerBody.setLinearDamping(1);
				if (grounded) {
					player.anim.setAnim(AnimState.WALK_RIGHT);
					player.sfx.walkSound();
				}
			}
			if (moveLeft) {
				playerBody.setLinearVelocity(15.0 * directionFacing(),playerBody.getLinearVelocity().y );
				playerBody.setLinearDamping(1);
				if (grounded) {
					player.anim.setAnim(AnimState.WALK_LEFT);
					player.sfx.walkSound();
				}
			}
			if (jump && grounded) {
				playerBody.setLinearDamping(0.1);
				playerBody.setLinearVelocity(playerBody.getLinearVelocity().x ,45.0);
				player.sfx.jumpSound();
				jump = false;

			}
			if (playerBody.getLinearVelocity().y > -2 && playerBody.getLinearVelocity().y < 1) {
				playerBody.setLinearDamping(1);
				grounded = true;
			}
			else {
				playerBody.setLinearDamping(0.1);
				grounded = false;
			}
			
			if(spawnEarth && earthCooldown >= 100) {
				earth = new EarthElement(program, player.getX() + (70* directionFacing()), player.getY(), levelWorld);
				player.sfx.earthBendSound();
				spawnEarth = false;
				earthCooldown = 0;
			}

			levelWorld.update(1000,20);
			playerBody.setAngularVelocity(0);
			movePlayer();

		}
	};
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == pauseMenu.resume) {
			resumeGame();
		} 
		if(obj == pauseMenu.menu) {
			resumeGame();
			endLevel();
			program.add(program.bg);
			program.menuTheme.start();
			program.switchToMenu();
		}
	}

	public static void main(String[] args) {
		new MainApplication().start();
	}

}
