package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage title;
	private GImage newGame;
	private GImage options;
	private GImage levelSelect;
	private GImage credits;
	private SfxPlayer sfx;

	public MenuPane(MainApplication app) {
		super();
		program = app;

		sfx = new SfxPlayer();
		
		title = new GImage("media/sprites/menus/title.png", 200, 50);

		newGame = new GImage("media/sprites/menus/new_game.png", 200, 200);

		options = new GImage("media/sprites/menus/options.png", 200, 400);

		levelSelect = new GImage("media/sprites/menus/select_level.png", 200, 300);
		
		credits = new GImage("media/sprites/menus/credits.png", 20, 500);
	}

	@Override
	public void showContents() {
		program.add(title);
		program.add(newGame);
		program.add(options);
		program.add(levelSelect);
		program.add(credits);
		//program.setColor();
	}

	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(newGame);
		program.remove(options);
		program.remove(levelSelect);
		program.remove(credits);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == newGame) {
			program.switchToLevelScreen();
		} else if(obj == options) {
			program.switchToOptions();
			sfx.chooseMenuOption();
		} else if(obj == levelSelect) {
			program.switchToLevelSelect();
			sfx.chooseMenuOption();
		}
		else if(obj == credits) {
			program.switchToCredits();
			sfx.chooseMenuOption();
		}
	}
}
