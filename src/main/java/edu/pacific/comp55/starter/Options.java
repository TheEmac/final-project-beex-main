package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Options extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage sound;
	private GImage controls;
	private GImage video;
	private GImage back;
	private GImage option;
	private SfxPlayer sfx;

	public Options(MainApplication app) {
		super();
		program = app;
		sfx = new SfxPlayer();
		
		sound = new GImage("media/sprites/menus/options/sounds.png", 200, 200);

		controls = new GImage("media/sprites/menus/options/controls.png", 200, 300);

		video = new GImage("media/sprites/menus/options/video.png", 200, 400);

		back = new GImage("media/sprites/menus/levelselect/back.png", 10, 10);

		option = new GImage("media/sprites/menus/options/options.png", 200, 50);
	}

	@Override
	public void showContents() {
		program.add(sound);
		program.add(controls);
		program.add(video);
		program.add(back);
		program.add(option);
	}

	@Override
	public void hideContents() {
		program.remove(sound);
		program.remove(controls);
		program.remove(video);
		program.remove(back);
		program.remove(option);
	}


	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == sound) {
			program.switchToSound();
			sfx.chooseMenuOption();
		} else if(obj == controls) {
			program.switchToControls();
			sfx.chooseMenuOption();
		} else if(obj == video) {
			program.switchToVideo();
			sfx.chooseMenuOption();
		} else if(obj == back) {
			program.switchToMenu();
			sfx.chooseMenuOption();
		}
	}

}
