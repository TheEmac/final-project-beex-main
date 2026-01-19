package edu.pacific.comp55.starter;
import javax.swing.Timer;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private static final int FPS = 41;

	public Timer globalTimer;
	public SoundPlayer menuTheme;
	public GImage bg;
	private Sound sound;
	private Controls control;
	private Video video;
	private MenuPane menu;
	private Options option;
	private Credits credits;
	private LevelSelect levelSelect;
	private LevelOne level2;
	private LevelTwo level3;
	private LevelThree level4;
	
	private VictoryScreen victoryScreen2;
	private VictoryScreen victoryScreen3;
	private VictoryScreen victoryScreen4;
	
	public GImage victory = new GImage("media/sprites/menus/victory/victory.png", 230, 100);
	public GImage nextLevel = new GImage("media/sprites/menus/victory/next_level.png", 200, 225);
	public GImage mainMenu = new GImage("media/sprites/menus/victory/back_to_menu.png", 200, 350);
	public SfxPlayer sfx = new SfxPlayer();

	@Override
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	@Override
	public void run() {
		globalTimer = new Timer(FPS, this);
		option = new Options(this);
		sound = new Sound(this);
		control = new Controls(this);
		video = new Video(this);
		credits = new Credits(this);
		levelSelect = new LevelSelect(this);
		level2 = new LevelOne(this);
		level3 = new LevelTwo(this);
		level4 = new LevelThree(this);
		bg = new GImage("media/bg.png", 0, 0);
		add(bg);
		
		victoryScreen2 = new VictoryScreen(this, 2);
		victoryScreen3 = new VictoryScreen(this, 3);
		victoryScreen4 = new VictoryScreen(this, 4);
		
		menu = new MenuPane(this);
		menuTheme = new SoundPlayer(0, "menu_theme.wav", 0.8f, true);
		setupInteractions();
		switchToMenu();
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}

	public void switchToLevelScreen() {
		menuTheme.stop();
		remove(bg);
		switchToScreen(level2);
	}

	public void switchToLevelSelect() {
		switchToScreen(levelSelect);
	}
	
	public void switchToSecondLevel() {
		menuTheme.stop();
		remove(bg);
		switchToScreen(level2);
	}
	
	public void switchToThirdLevel() {
		menuTheme.stop();
		remove(bg);
		switchToScreen(level3);
	}
	
	public void switchToFourthLevel() {
		menuTheme.stop();
		remove(bg);
		switchToScreen(level4);
	}

	public void switchToSound() {
		switchToScreen(sound);
	}

	public void switchToControls() {
		switchToScreen(control);
	}
	
	public void switchToVictoryScreen2() {
		switchToScreen(victoryScreen2);
	}
	public void switchToVictoryScreen3() {
		switchToScreen(victoryScreen3);
	}
	public void switchToVictoryScreen4() {
		switchToScreen(victoryScreen4);
	}

	public void switchToVideo() {
		switchToScreen(video);
	}

	public void switchToOptions() {
		switchToScreen(option);
	}
	
	public void switchToCredits() {
		switchToScreen(credits);
	}

	public static void main(String[] args) {
		new MainApplication().start();
	}
}
