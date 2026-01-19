package edu.pacific.comp55.starter;


import java.awt.event.MouseEvent;


import acm.graphics.GObject;

public class VictoryScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls

	private int level;

	public VictoryScreen(MainApplication app, int level) {
		super();
		program = app;
		this.level = level;
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.sfx.victoryJingle();
		program.add(program.bg);
		program.add(program.victory);
		if (level != 4) {
			program.add(program.nextLevel);
			program.add(program.mainMenu);
			return;
		}
		program.add(program.mainMenu);
		program.mainMenu.setLocation(200, 225);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(program.victory);
		program.remove(program.nextLevel);
		program.remove(program.mainMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == program.nextLevel) {
			if(level == 1) {
				program.sfx.chooseMenuOption();
				program.switchToSecondLevel();
			} else if (level == 2) {
				program.sfx.chooseMenuOption();
				program.switchToThirdLevel();
			} else if (level == 3) {
				program.sfx.chooseMenuOption();
				program.switchToFourthLevel();
			} else {
				program.sfx.chooseMenuOption();
				program.menuTheme.start();
				program.switchToMenu();
			}
		} else if(obj == program.mainMenu) {
			program.sfx.chooseMenuOption();
			program.menuTheme.start();
			program.switchToMenu();
		} 
	}

}
