package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class LevelSelect extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage back;
	private GImage first;
	private GImage second;
	private GImage third;
	private SfxPlayer sfx;
	
	public LevelSelect(MainApplication app) {
		super();
		program = app;
		sfx = new SfxPlayer();

		back = new GImage("media/sprites/menus/levelselect/back.png", 10, 10);

		first = new GImage("media/sprites/menus/levelselect/first.png", 200, 100);

		second = new GImage("media/sprites/menus/levelselect/second.png", 200, 200);

		third = new GImage("media/sprites/menus/levelselect/third.png", 200, 300);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(back);
		program.add(first);
		program.add(second);
		program.add(third);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(back);
		program.remove(first);
		program.remove(second);
		program.remove(third);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == first) {
			program.switchToSecondLevel();
		} else if(obj == second) {
			program.switchToThirdLevel();
		} else if(obj == third) {
			program.switchToFourthLevel();
		} else if(obj == back) {
			program.switchToMenu();
			sfx.chooseMenuOption();
		}

	}

}

