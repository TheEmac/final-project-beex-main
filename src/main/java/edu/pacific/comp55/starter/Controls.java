package edu.pacific.comp55.starter;

import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Controls extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage back;
	private GImage controls;

	public Controls(MainApplication app) {
		super();
		program = app;
		back = new GImage("media/sprites/menus/levelselect/back.png", 10, 10);

		controls = new GImage("media/sprites/menus/options/insides/controls2.png", 200, 100);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(back);
		program.add(controls);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(back);
		program.remove(controls);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.sfx.chooseMenuOption();
			program.switchToOptions();
		}
	}

}
