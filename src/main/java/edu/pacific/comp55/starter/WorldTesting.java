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
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;

import acm.graphics.GOval;
import acm.graphics.GRect;
import javafx.util.Pair;


public class WorldTesting extends GraphicsApplication{
	public static final int SIZE = 25;
private static final double SHAPE_SIZE = 80;
public static World<Body> w;
private GRect rectGraphic;
private GRect rectGraphic2;
private GRect rectGraphic3;
private GRect rectGraphic4;
private Timer t1;

public static final int SPEED = 2;
private ArrayList<GOval> balls;
private ArrayList<Body> ballsPhysics;
private Pair<Body,GRect> player;
private AABB bounds;

private boolean moveRight = false;
private boolean moveLeft = false;
private boolean jump = false;
private boolean water = false;
private boolean facingLeft = false;
private boolean facingRight = true;
private boolean jumped;

	public WorldTesting() {
		w = new World<>();
		//w.setGravity(World.EARTH_GRAVITY);

		player = new Pair<>(new Body(), new GRect(32,32));
		player.getKey().addFixture(new BodyFixture(new Rectangle(32,32)));
		player.getKey().setMass(MassType.NORMAL);
		player.getKey().translate(50.0,-400.0);
		player.getValue().setLocation(50, 50);
		player.getKey().setGravityScale(1);
		player.getKey().setLinearDamping(0.1);
		player.getKey().setAtRest(false);

		w.addBody(player.getKey());
		rectGraphic = new GRect(800,5);
		 rectGraphic2 = new GRect(800,20);
		 rectGraphic3 = new GRect(20,600);
		 rectGraphic4 = new GRect(20,600);

//		BodyFixture bodyFix = new BodyFixture( new Rectangle(800.0,5.0));
//		Body b = new Body();
//		b.addFixture(bodyFix);
//		b.translate(400,-550);
//		b.setMass(MassType.INFINITE);
//		bounds = b.createAABB();
//		rectGraphic.setLocation(bounds.getMinX(),bounds.getMinY()*-1);
//		w.addBody(b);


		Body floor = new Body();
		floor.addFixture(new BodyFixture(new Rectangle(800.0,20.0)));
		floor.translate(400,-550);
		floor.setMass(MassType.INFINITE);
		bounds = floor.createAABB();
		rectGraphic2.setLocation(bounds.getMinX(),bounds.getMinY()*-1-18);
		System.out.println(bounds.getMinX() + " " + bounds.getMinY()*-1);
		rectGraphic2.setColor(Color.BLUE);
		rectGraphic2.setFilled(true);
		w.addBody(floor);
		//create right wall

		Body rightWall = new Body();
		rightWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		rightWall.translate(800,-300);
		rightWall.setMass(MassType.INFINITE);
		bounds = rightWall.createAABB();
		rectGraphic3.setLocation(bounds.getMinX(),bounds.getMaxY()*-1);
		System.out.println(bounds.getMinX() + " " + bounds.getMaxY()*-1);
		rectGraphic3.setColor(Color.red);
		w.addBody(rightWall);

		//create Left
		Body leftWall = new Body();
		leftWall.addFixture(new BodyFixture(new Rectangle(20.0,600.0)));
		leftWall.translate(-10,-300);
		leftWall.setMass(MassType.INFINITE);
		bounds = leftWall.createAABB();
		rectGraphic4.setLocation(bounds.getMinX(),bounds.getMinY()*-1);
		System.out.println(bounds.getMinX() + " " + bounds.getMinY()*-1);
		rectGraphic4.setColor(Color.GREEN);
		w.addBody(leftWall);






	//obstaclesInLevel.add(new Obstacle(app.PROG_WIDTH/2 - 32, app.PROG_HEIGHT/2 + 32, 10, true));

	}
	@Override
	public void mousePressed(MouseEvent e) {
		GOval ball = makeBall(SIZE/2, e.getY());
		add(ball);
	}


	ActionListener taskPerformed = new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(player.getKey().get);
		//System.out.println(player.getKey().getLinearVelocity().y);
		//w.step(1);
		if (moveRight /*&& !player.rightCollision(program)*/) {
			//player.moveForward();
			player.getKey().setLinearVelocity(30.0,player.getKey().getLinearVelocity().y);
		}
		if (moveLeft) {
			player.getKey().setLinearVelocity(-30.0,player.getKey().getLinearVelocity().y);
		}
		if (jump) {
			//player.isJumping = true;
			player.getKey().setLinearVelocity(player.getKey().getLinearVelocity().x,35.0);
			//player.jump();
			jump = false;

		}
		w.update(1000,20);
		for(int i = 0; i < balls.size() ; i++ ) {
			balls.get(i).setLocation( ballsPhysics.get(i).getWorldCenter().x-25/2, ballsPhysics.get(i).getWorldCenter().y *-1   );
			//System.out.println( "Graphic Location: " + balls.get(i).getX() + " " + balls.get(i).getY());
			//System.out.println( "Physics Location: " + ballsPhysics.get(i).getWorldCenter().x + " " +  -1 *ballsPhysics.get(i).getWorldCenter().y);

		}
		player.getKey().setAtRest(false);
		player.getValue().setLocation(player.getKey().getWorldCenter().x-16,-1*player.getKey().getWorldCenter().y-16);
	}
	};
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		/*switch( e.getKeyChar()  ) {
		case ('d')://right
			player.getKey().setLinearVelocity(30.0,player.getKey().getLinearVelocity().y);
			break;
		case ('w')://up
			player.getKey().setLinearVelocity(player.getKey().getLinearVelocity().x,35.0);
			player.getKey();
			break;
		case ('a')://left
			player.getKey().setLinearVelocity(-30.0,player.getKey().getLinearVelocity().y);
			break;
		}
			*/

		if ((e.getKeyCode() == ControlButtons.VK_RIGHT.toInt())) {
			moveRight = true;
			facingRight = true;
			//TODO
			//player.anim.setAnim(AnimState.WALK_RIGHT);
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = true;
			facingRight = false;
			//TODO
			//player.anim.setAnim(AnimState.WALK_LEFT);
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = true;
			jumped = true;
			/*
			if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {

				if(airCooldown >= 20) {
					System.out.println("used air");
					useAir = true;
					airCooldown = 0;
				}
			}*/
		}
		/*if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt() && !player.isFalling) {

			if(airCooldown >= 20) {
				//System.out.println("used air");
				useAir = true;
				//jump = true;
				airCooldown = 0;
				jumped = false;
			}
		}
		if (e.getKeyCode() == ControlButtons.VK_DOWN.toInt() && waterCounting >= 25) {
			water = true;
			waterCounting = 0;
		}*/
	}




	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == ControlButtons.VK_RIGHT.toInt()) {
			moveRight = false;
			facingRight = true;
			//player.anim.setAnim(AnimState.IDLE_RIGHT);
		}
		if (e.getKeyCode() == ControlButtons.VK_LEFT.toInt()) {
			moveLeft = false;
			facingRight = false;
			//player.anim.setAnim(AnimState.IDLE_LEFT);
		}
		if (e.getKeyCode() == ControlButtons.VK_UP.toInt()) {
			jump = false;
		}
		//TODO
		/*if (e.getKeyCode() == ControlButtons.VK_SPACE.toInt()) {
			useAir = false;
		}*/
	}


	public static void main(String[] args) {
		new WorldTesting().start();

	}



	public GOval makeBall(double x, double y) {
		GOval temp = new GOval(x-SIZE/2, y-SIZE/2, SIZE, SIZE);
		temp.setColor(Color.RED);
		temp.setFilled(true);
		balls.add(temp);
		Body circle = new Body();
		circle.addFixture(Geometry.createCircle(25.0));
		circle.setMass(MassType.NORMAL);
		circle.translate(temp.getX(),-1*temp.getY());
		circle.setLinearVelocity(new Vector2(20.0,0));
		circle.setAtRest(false);


		w.addBody(circle);
		ballsPhysics.add(circle);
		return temp;
	}
	@Override
	public void run() {
		t1 = new Timer(16,taskPerformed);
		t1.start();
		addMouseListeners();
		addKeyListeners();
		balls = new ArrayList<>();
		ballsPhysics = new ArrayList<>();
		add(rectGraphic);
		add(rectGraphic2);
		add(rectGraphic3);
		add(rectGraphic4);
		add(player.getValue());
	}

	@Override
	public void init() {
		setSize(800,600);
		requestFocus();
	}
}
