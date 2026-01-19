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

public class LevelOne extends GraphicsPane implements Interfaceable {
	private static final int FPS = 16; //60fps, a frame every 16ms
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean jump = false;
	private boolean grounded = true;
	private boolean interact = false;
	

	private World<Body> levelWorld;
	
	private boolean airjump;
	private Timer localTimer;
	public boolean complete;
	public Character2 player;
	public Body playerBody;
	private MainApplication program;
	private ArrayList<Obstacle> obstaclesInLevel;
	private ArrayList<Body> bodiesInLevel;
	
	private SoundPlayer levelTheme;
	private GImage bg;
	private GImage graphics;

	
	private boolean paused = false;
	private boolean once = false;
	
	private GObject doorCheck;
	private GImage door;
	private AnimationPlayer doorAnim;
	
	private PauseMenu pauseMenu;
	private boolean gameOver = false;
	private int gameOverCount = 0;

	private static final GImage PLAYER_ICON = new GImage("media/sprites/character/idle/right/char_idle1.png");

	public LevelOne(MainApplication app) {
		super();
		program = app;
		player = new Character2(PLAYER_ICON, 100, 100, true);
		pauseMenu = new PauseMenu(program);
		obstaclesInLevel = new ArrayList<>();
		bodiesInLevel = new ArrayList<>();
		localTimer = new Timer(FPS, gameLoop);
		levelWorld = new World<>();
		levelTheme = new SoundPlayer(0, "wind_theme.wav", 0.75f, true);
		levelTheme.stop();
		bg = new GImage("media/bg.png", 0, 0);
		graphics = new GImage("media/sprites/environment/levels/level2_layout.png", 0, 0);
		door = new GImage("media/sprites/environment/door/door1.png", 724, 57);
		doorAnim = new AnimationPlayer(door, AnimState.DOOR_CLOSED);
	}
	
	public void startLevel() {
		interact = false;
		
		
		//TEMP CODE
		player.anim.setAnim(AnimState.IDLE_RIGHT);
	
		
		//floor tiles
		obstaclesInLevel.add(createObstacle(0,550,25,200));
		obstaclesInLevel.add(createObstacle(350,550,25,150));
		obstaclesInLevel.add(createObstacle(650,550,25,150));


		//1st floor pillar
		obstaclesInLevel.add(createObstacle(562,375,140,25));

		//2nd floor access platform
		obstaclesInLevel.add(createObstacle(750,450,25,50));


		//2nd floor
		obstaclesInLevel.add(createObstacle(0,350,25,100));
		obstaclesInLevel.add(createObstacle(175,350,25,75));
		obstaclesInLevel.add(createObstacle(500,350,25,150));


		//2nd floor middle wall
		obstaclesInLevel.add(createObstacle(300,175,100,25));

		//2nd floor middle platform
		obstaclesInLevel.add(createObstacle(375,250,25,100));

		//3rd floor access
		obstaclesInLevel.add(createObstacle(0,250,25,50));


		//3rd floor
		obstaclesInLevel.add(createObstacle(0,150,25,50));
		obstaclesInLevel.add(createObstacle(300,150,25,300));
		obstaclesInLevel.add(createObstacle(150,50,25,50));


		//end platform
		obstaclesInLevel.add(createObstacle(700,100,25,100));
	}

	private void createWorldBoundaries() {
		GRect rectGraphic1 = new GRect(800,20);		
		GRect rectGraphic2 = new GRect(20,600);
		GRect rectGraphic3 = new GRect(20,600);

		AABB bounds = new AABB(0);

		Body rightWall = new Body();
		rightWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		rightWall.translate(810,-300);
		rightWall.setMass(MassType.INFINITE);
		bounds = rightWall.createAABB();
		rectGraphic2.setLocation(bounds.getMinX(),bounds.getMaxY()*-1);
		rectGraphic2.setColor(Color.red);
		levelWorld.addBody(rightWall);

		//create Leftwall
		Body leftWall = new Body();
		leftWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		leftWall.translate(-10,-300);
		leftWall.setMass(MassType.INFINITE);
		bounds = leftWall.createAABB();
		rectGraphic3.setLocation(bounds.getMinX(),bounds.getMinY()*-1);
		//System.out.println(bounds.getMinX() + " " + bounds.getMinY()*-1);
		rectGraphic3.setColor(Color.GREEN);
		levelWorld.addBody(leftWall);

		Body roof = new Body();
		roof.addFixture(new BodyFixture(new Rectangle(800.0,20.0)));
		roof.translate(400,20);
		roof.setMass(MassType.INFINITE);
		bounds = roof.createAABB();
		rectGraphic1.setLocation(bounds.getMinX(),bounds.getMinY()* -1 );
		rectGraphic1.setColor(Color.gray);
		levelWorld.addBody(roof);
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

	private void initializePlayerBody() {
		playerBody = new Body();
		playerBody.addFixture(new BodyFixture(new Rectangle(32.0,32.0)));
		playerBody.setMass(MassType.NORMAL);
		playerBody.translate(50.0,-534.0);
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
	
	public void endLevel(boolean restart) {
		obstaclesInLevel.clear();
		bodiesInLevel.clear();
		levelWorld.removeAllBodies();
		levelTheme.stop();
		localTimer.stop();
		program.removeAll();
		if (restart) {
			initLevel();
		}
	}
	
	public void win() {
		endLevel(false);
		program.switchToVictoryScreen2();
	}
	
	public void pauseGame() {
		player.anim.stopAnim();
		levelTheme.pause();
		paused = true;
		pauseMenu.pause();
	}
	
	public void resumeGame() {
		player.anim.startAnim();
		levelTheme.resume();
		paused = false;
		pauseMenu.resume();
	}

	public void initLevel() {
		startLevel();
		gameOver = false;
		once = false;
		createWorldBoundaries();
		initializePlayerBody();
		levelTheme.start();
		localTimer.start();
		
		program.add(bg);
		for (Obstacle c: obstaclesInLevel) {
			program.add(c.texture);
		}
		program.add(graphics);
		program.add(door);
		program.add(PLAYER_ICON);
	}

	public void movePlayer() {
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
		// TODO Auto-generated method stub

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
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = true;
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = true;
			
		}
		
		if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			interact = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == ControlButtons.VK_RIGHT.toInt()) {
			moveRight = false;
			player.anim.setAnim(AnimState.IDLE_RIGHT);
			player.sfx.sfxCount = 0;
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = false;
			player.anim.setAnim(AnimState.IDLE_LEFT);
			player.sfx.sfxCount = 0;
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = false;
		}
		
		if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			interact = false;
		}
		
	}





	ActionListener gameLoop = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (paused) {
				return;
			}

			if(playerBody.getWorldCenter().y < -650) {

				if (gameOverCount == 0) {
					levelTheme.stop();
					player.sfx.gameOverJingle();
				}
				gameOverCount++;
				if (gameOverCount == 150) {
					gameOverCount = 0;
					gameOver = true;
					endLevel(gameOver);
				}
			}
			
			if (winCheck()) {
				doorAnim.setAnim(AnimState.DOOR_OPEN, false);
			}
			
			if (winCheck() && interact) {
				win();
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
				playerBody.setLinearVelocity(-15.0,playerBody.getLinearVelocity().y );
				playerBody.setLinearDamping(1);
				if (grounded) {
					player.anim.setAnim(AnimState.WALK_LEFT);
					player.sfx.walkSound();
				}
			}
			if (jump && grounded) {
				playerBody.setLinearDamping(0.1);
				playerBody.setLinearVelocity(playerBody.getLinearVelocity().x ,45.0);
				jump = false;
				player.sfx.jumpSound();

			}
			if (jump && !grounded && airjump) {
				playerBody.setLinearDamping(0.1);
				playerBody.setLinearVelocity(playerBody.getLinearVelocity().x ,45.0);
				airjump = false;
				player.sfx.doubleJumpSound();

			}
			if (playerBody.getLinearVelocity().y > -2 && playerBody.getLinearVelocity().y < 1) {
				playerBody.setLinearDamping(1);
				grounded = true;
				airjump = true;
			}
			else {
				playerBody.setLinearDamping(0.1);
				grounded = false;
			}


			levelWorld.update(1000,20);
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
			endLevel(false);
			program.add(program.bg);
			program.menuTheme.start();
			program.switchToMenu();
		}
	}

	public static void main(String[] args) {
		new MainApplication().start();
	}

}
