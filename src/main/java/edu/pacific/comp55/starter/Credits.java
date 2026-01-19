package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Credits extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage back;
	private GImage credits;

	public Credits(MainApplication app) {
		super();
		program = app;

		back = new GImage("media/sprites/menus/levelselect/back.png", 10, 10);

		credits = new GImage("media/sprites/menus/official_credits.png", 0, 0);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(credits);
		program.add(back);
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(credits);
		program.remove(back);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.sfx.chooseMenuOption();
			program.switchToMenu();
		}
	}

}
