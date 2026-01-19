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

public class LevelThree extends GraphicsPane implements Interfaceable {
	private static final int FPS = 16; //60fps, a frame every 16ms
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean jump = false;
	private boolean grounded = true;
	private boolean water = false;
	private boolean facingRight = true;
	private World<Body> levelWorld;
	
	

	private Timer localTimer;
	private int waterCounting;

	public boolean complete;
	public Character2 player;
	public Body playerBody;
	private MainApplication program;
	private ArrayList<Obstacle> obstaclesInLevel;
	private ArrayList<Body> bodiesInLevel;
	private SoundPlayer levelTheme;

	
	private ArrayList<GRect> rectInLevel;
	private boolean paused = false;

	private WaterElement shootWater;
	public boolean movingWater = false;
	private GObject buttonCheck;
	private GObject doorCheck;
	private GImage door;
	private AnimationPlayer doorAnim;
	private AnimationPlayer button1Anim;
	private AnimationPlayer button2Anim;
	private AnimationPlayer button3Anim;
	private GImage button1;
	private GImage button2;
	private GImage button3;
	private Obstacle breakable;
	private Body breakableBody;
	private boolean interact = false;
	private boolean once = false;
	
	private PauseMenu pauseMenu;
	private int gameOverCount = 0;
	private boolean gameOver = false;
	
	private GImage bg;
	private GImage graphics;
	
	private static final GImage PLAYER_ICON = new GImage("media/sprites/character/idle/right/char_idle1.png");

	public LevelThree(MainApplication app) {
		super();
		program = app;
		player = new Character2(PLAYER_ICON, 100, 100, true);
		levelWorld = new World<>();
		pauseMenu = new PauseMenu(program);
		obstaclesInLevel = new ArrayList<>();
		rectInLevel = new ArrayList<>();
		bodiesInLevel = new ArrayList<>();
		localTimer = new Timer(FPS, gameLoop);
		shootWater = new WaterElement(program, this, player.getX(), player.getY());
		levelTheme = new SoundPlayer(0, "water_theme.wav", 0.75f, true);
		levelTheme.stop();
		bg = new GImage("media/bg.png", 0, 0);
		graphics = new GImage("media/sprites/environment/levels/level4_layout1.png", 0, 0);
		button1 = new GImage("media/sprites/environment/torch1.png", 180, 505);
		button2 = new GImage("media/sprites/environment/torch1.png", 20, 369);
		button3 = new GImage("media/sprites/environment/torch1.png", 750, 190);
		button1Anim = new AnimationPlayer(button1, AnimState.TORCH_FIRE);
		button2Anim = new AnimationPlayer(button2, AnimState.TORCH_FIRE);
		button3Anim = new AnimationPlayer(button3, AnimState.TORCH_FIRE);
		door = new GImage("media/sprites/environment/door/door1.png", 500, 106);
		doorAnim = new AnimationPlayer(door, AnimState.DOOR_CLOSED);
	}

	public void startLevel() {
		player.anim.setAnim(AnimState.IDLE_RIGHT);
		waterCounting = 50;

		obstaclesInLevel.add(createObstacle(200,465,80,128));
		obstaclesInLevel.add(createObstacle(100, 465, 24, 100));
		obstaclesInLevel.add(createObstacle(100, 518, 24, 100));
		
		obstaclesInLevel.add(createObstacle(200, 401, 24, 128));
		obstaclesInLevel.add(createObstacle(500, 301, 400, 128));
		
		obstaclesInLevel.add(createObstacle(500, 150, 24, 128));
		
		breakable = createBreakable(0, 465, 24, 100);
		obstaclesInLevel.add(breakable);
	}
	
	private void createWorldBoundaries() {		
		GRect rectGraphic1 = new GRect(800,20);
		GRect rectGraphic2 = new GRect(800,20);
		GRect rectGraphic3 = new GRect(20,600);
		GRect rectGraphic4 = new GRect(20,600);
		AABB bounds = new AABB(0);

		Body floor = new Body();
		floor.addFixture(new BodyFixture(new Rectangle(656.0,20.0)));
		floor.translate(-144,-550);
		floor.setMass(MassType.INFINITE);
		bounds = floor.createAABB();
		rectGraphic2.setLocation(bounds.getMinX(),bounds.getMinY()*-1-18);
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
		rectGraphic3.setColor(Color.red);
		levelWorld.addBody(rightWall);

		//create Leftwall
		Body leftWall = new Body();
		leftWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		leftWall.translate(-10,-300);
		leftWall.setMass(MassType.INFINITE);
		bounds = leftWall.createAABB();
		rectGraphic4.setLocation(bounds.getMinX(),bounds.getMinY()*-1);
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

		program.add(rectGraphic4);
		program.add(rectGraphic3);
		program.add(rectGraphic2);
		program.add(rectGraphic1);
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
	
	private Obstacle createBreakable(int x, int y, int length, int width) {
		Obstacle o = new Obstacle(x,y,length,width);
		breakableBody = new Body();
		breakableBody.addFixture(new BodyFixture(new Rectangle(width,length)));
		breakableBody.setMass(MassType.INFINITE);
		breakableBody.translate(x+(width/2.0),-1* ( y+(length/2.0)));
		bodiesInLevel.add(breakableBody);
		levelWorld.addBody(breakableBody);
		return o;
	}

	private void initializePlayerBody() {
		playerBody = new Body();
		playerBody.addFixture(new BodyFixture(new Rectangle(32.0,32.0)));
		playerBody.setMass(MassType.NORMAL);
		playerBody.translate(50.0,-525.0);
		playerBody.setGravityScale(1);
		playerBody.setLinearDamping(1);
		playerBody.setAtRest(false);

		levelWorld.addBody(playerBody);

	}


	public boolean winCheck() {
		doorCheck = program.getElementAt(player.getX(), player.getY() - 2);
		if (doorCheck == door) {
			if (once  == false) {
				player.sfx.doorOpen();
				once = true;
			}
			return true;
		}
		return false;
	}
	
	public void endLevel(boolean restart) {
		obstaclesInLevel.clear();
		rectInLevel.clear();
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
		program.switchToVictoryScreen4();
	}
	
	public void pauseGame() {
		player.anim.stopAnim();
		levelTheme.pause();
		paused  = true;
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
		createWorldBoundaries();
		initializePlayerBody();
		localTimer.start();
		levelTheme.start();
		
		graphics = new GImage("media/sprites/environment/levels/level4_layout1.png", 0, 0);
		button1Anim.setAnim(AnimState.TORCH_FIRE);
		button2Anim.setAnim(AnimState.TORCH_FIRE);
		button3Anim.setAnim(AnimState.TORCH_FIRE);
		program.add(bg);

		for (Obstacle c: obstaclesInLevel) {
			program.add(c.texture);
		}
		program.add(graphics);
		program.add(door);
		program.add(button1);
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
		
		if (e.getKeyCode() == ControlButtons.VK_DOWN.toInt() && waterCounting >= 25) {
			shootWater.setPos(player.getX(), player.getY());
			movingWater = true;
			waterCounting = 0;
			player.sfx.waterShotSound();
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
			interact  = true;
		}
	}

	public void buttons() {
		buttonCheck = program.getElementAt(shootWater.returnX() + shootWater.water.getWidth() + 5, shootWater.returnY() + shootWater.water.getHeight() - 12);
		
		if (buttonCheck == button1) {
			program.remove(shootWater.water);
			button1Anim.setAnim(AnimState.TORCH_DEAD);
			player.sfx.torchBurn();
			program.add(button2);
			bodiesInLevel.remove(breakableBody);
			levelWorld.removeBody(breakableBody);
			obstaclesInLevel.remove(breakable);
			program.remove(breakable.texture);
			graphics.setImage("media/sprites/environment/levels/level4_layout2.png");
		}
		if (buttonCheck == button2) {
			program.remove(shootWater.water);
			button2Anim.setAnim(AnimState.TORCH_DEAD);
			player.sfx.torchBurn();
			program.add(button3);
			createObstacle(395, 350, 24, 32);
			graphics.setImage("media/sprites/environment/levels/level4_layout3.png");
		}
		if (buttonCheck == button3) {
			program.remove(shootWater.water);
			button3Anim.setAnim(AnimState.TORCH_DEAD);
			player.sfx.torchBurn();
			createObstacle(650, 250, 24, 32);
			createObstacle(700, 200, 24, 32);
			graphics.setImage("media/sprites/environment/levels/level4_layout4.png");
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

			waterCounting += 1;
			if (moveRight) {
				playerBody.setLinearVelocity(15.0,playerBody.getLinearVelocity().y);
				playerBody.setLinearDamping(1);
				if (grounded) {
					player.sfx.walkSound();
					player.anim.setAnim(AnimState.WALK_RIGHT);
				}
			}
			if (moveLeft) {
				playerBody.setLinearVelocity(-15.0,playerBody.getLinearVelocity().y );
				playerBody.setLinearDamping(1);
				if (grounded) {
					player.sfx.walkSound();
					player.anim.setAnim(AnimState.WALK_LEFT);
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

		    playerBody.setAngularVelocity(0);
			levelWorld.update(1000,20);
			movePlayer();
			
			if (movingWater) {
				shootWater.move(facingRight);
				water = true;
			}
			
			if(water) {
				buttons();
			}
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
