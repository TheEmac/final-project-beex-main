package edu.pacific.comp55.starter;



import acm.graphics.GImage;


public class PauseMenu extends GraphicsPane {
	
	private MainApplication program;
	public GImage pane;
	public GImage resume;
	public GImage options;
	public GImage menu;

	public PauseMenu(MainApplication app) {
		program = app;
		pane = new GImage("media/sprites/menus/pause/pause.png", 0, 0);
		resume = new GImage("media/sprites/menus/pause/resume.png", 312, 238);
		menu = new GImage("media/sprites/menus/pause/menu.png", 338, 299);
	}
	
	public void pause() {
		showContents();
	}
	
	public void resume() {
		hideContents();
	}

	@Override
	public void showContents() {
		program.add(pane);
		program.add(resume);
		program.add(menu);
	}

	@Override
	public void hideContents() {
		program.remove(pane);
		program.remove(resume);
		program.remove(menu);
	}
	
}
