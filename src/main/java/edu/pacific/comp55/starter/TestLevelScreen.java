package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.world.World;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class TestLevelScreen extends GraphicsPane implements Interfaceable {
	private static final int FPS = 16; //60fps, a frame every 16ms
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean jump = false;
	private boolean grounded = true;
	
	private boolean facingRight = true;
	private World<Body> levelWorld;
	

	private boolean spawnEarth;

	private Timer localTimer;
	

	public boolean complete;
	public Character2 player;
	public Body playerBody;
	private MainApplication program;
	private ArrayList<Obstacle> obstaclesInLevel;

	

	private static final GImage PLAYER_ICON = new GImage("media/sprites/character/idle/right/char_idle1.png");

	public TestLevelScreen(MainApplication app) {
		super();
		program = app;
		
		levelWorld = new World<>();



		//TEMP CODE
		player = new Character2(PLAYER_ICON, 100, 100, true);
		

		obstaclesInLevel = new ArrayList<>();

		obstaclesInLevel.add(createObstacle(100,200,10,50));
		obstaclesInLevel.add(createObstacle(200,250,10,50));
		obstaclesInLevel.add(createObstacle(100,300,10,50));
		obstaclesInLevel.add(createObstacle(200,350,10,50));
		obstaclesInLevel.add(createObstacle(100,400,10,50));
		obstaclesInLevel.add(createObstacle(200,450,10,50));
		obstaclesInLevel.add(createObstacle(100,475,10,50));
		localTimer = new Timer(FPS, gameLoop);


		//creating bottom floor






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
		levelWorld.addBody(obstacle);
		return o;
	}

	private void initializePlayerBody() {
		playerBody = new Body();
		playerBody.addFixture(new BodyFixture(new Rectangle(32.0,32.0)));
		playerBody.setMass(MassType.NORMAL);
		playerBody.translate(50.0,-200.0);
		playerBody.setGravityScale(1);
		playerBody.setLinearDamping(1);
		playerBody.setAtRest(false);

		levelWorld.addBody(playerBody);

	}


	public boolean winCheck() {
		//TODO
		return false;
	}

	public void initLevel() {
		createWorldBoundaries();
		initializePlayerBody();
		localTimer.start();

		for (Obstacle c: obstaclesInLevel) {
			program.add(c.texture);
		}
		program.add(PLAYER_ICON);
		//player.playerHitbox.addHitMarker(program);
	}

	public void movePlayer() {
		//TODO
		playerBody.setAtRest(false);
		player.updateIconLocation(playerBody.getWorldCenter().x-16,-1*playerBody.getWorldCenter().y-16);
	//	player.printPosition();
	}
	private void printPhysicsLocation() {
		System.out.println("Physics Location:" + playerBody.getLinearVelocity().x + " " + playerBody.getLinearVelocity().y);
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
		if ((e.getKeyCode() == ControlButtons.VK_RIGHT.toInt())) {
			moveRight = true;
			facingRight = true;
			player.anim.setAnim(AnimState.WALK_RIGHT);
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = true;
			facingRight = false;
			player.anim.setAnim(AnimState.WALK_LEFT);
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = true;
		}
		
		if (e.getKeyCode() == ControlButtons.VK_DOWN.toInt()) {
			spawnEarth = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == ControlButtons.VK_RIGHT.toInt()) {
			moveRight = false;
			facingRight = true;
			player.anim.setAnim(AnimState.IDLE_RIGHT);
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = false;
			facingRight = false;
			player.anim.setAnim(AnimState.IDLE_LEFT);
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = false;
		}
		if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			
		}
	}





	ActionListener gameLoop = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

		
		
			EarthElement earth;
			//GRect earth;

			if (moveRight /*&& !player.rightCollision(program)*/) {
				playerBody.setLinearVelocity(15.0,playerBody.getLinearVelocity().y);
				playerBody.setLinearDamping(1);
			}
			if (moveLeft) {
				playerBody.setLinearVelocity(-15.0,playerBody.getLinearVelocity().y );
				playerBody.setLinearDamping(1);
			}
			if (jump && grounded/*&& !player.isFalling && !player.isJumping*/) {
				//player.isJumping = true;
				//player.jump();
				playerBody.setLinearDamping(0.1);
				playerBody.setLinearVelocity(playerBody.getLinearVelocity().x ,45.0);
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
			
		     if(spawnEarth) {
					if(facingRight) {
						earth = new EarthElement(program, player.getX() + 50, player.getY(), levelWorld);
					} else {
						earth = new EarthElement(program, player.getX() - 50, player.getY(), levelWorld);
					}
					spawnEarth = false;
				}

			levelWorld.update(1000,20);
			
			movePlayer();
	
		}
	};

	public static void main(String[] args) {
		new MainApplication().start();
	}

}
