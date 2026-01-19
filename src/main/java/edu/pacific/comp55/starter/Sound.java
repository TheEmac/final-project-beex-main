package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class Sound extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage back;
	private GImage sound;
	private GImage increase;
	private GImage decrease;
	private GButton volumeText;
	private int volume;
	
	

	public Sound(MainApplication app) {
		super();
		program = app;

		volume = 100;

		back = new GImage("media/sprites/menus/levelselect/back.png", 10, 10);

		volumeText = new GButton("Volume: "  + volume, 350, 300, 100, 100);

		sound = new GImage("media/sprites/menus/options/insides/sounds2.png", 200, 100);

		increase = new GImage("media/sprites/menus/options/insides/plus.png", 600, 300);

		decrease = new GImage("media/sprites/menus/options/insides/minus.png", 100, 300);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(back);
		program.add(sound);
		program.add(increase);
		program.add(decrease);
		program.add(volumeText);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(back);
		program.remove(sound);
		program.remove(increase);
		program.remove(decrease);
		program.remove(volumeText);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.sfx.chooseMenuOption();
			program.switchToOptions();
		} else if(obj == increase && volume < 100) {
			program.sfx.chooseMenuOption();
			volume += 10;
			System.out.println(volume);
			program.remove(volumeText);
			volumeText = new GButton("Volume: "  + volume, 350, 300, 100, 100);
			program.add(volumeText);
		} else if(obj == decrease && volume > 0) {
			program.sfx.chooseMenuOption();
			volume -= 10;
			System.out.println(volume);
			program.remove(volumeText);
			volumeText = new GButton("Volume: "  + volume, 350, 300, 100, 100);
			program.add(volumeText);
		}
	}

}
