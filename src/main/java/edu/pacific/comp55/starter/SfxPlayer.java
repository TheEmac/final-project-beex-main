package edu.pacific.comp55.starter;

public class SfxPlayer {
	
	private SoundPlayer sfx;
	public int sfxCount = 0;
	
	public SfxPlayer() {
		sfx = new SoundPlayer();
	}
	
	public void walkSound() {
		if (sfxCount % 13 == 0 || sfxCount == 0) {
			sfx.newSound(1, "footstep.wav", 0.8f, false);
		}
		sfxCount++;
	}
	public void jumpSound() {
		sfxCount = 0;
		sfx.newSound(1, "jump.wav", 0.8f, false);
	}
	public void doubleJumpSound() {
		sfx.newSound(1, "airjump.wav", 0.8f, false);
	}
	public void waterShotSound() {
		sfx.newSound(1, "watershoot.wav", 0.75f, false);
	}
	public void earthBendSound() {
		sfx.newSound(1, "earthbend.wav", 0.75f, false);
	}
	public void crumblingEarthSound() {
		sfx.newSound(1, "earthcrumble.wav", 0.75f, false);
	}
	
	
	public void doorOpen() {
		sfx.newSound(1, "dooropen.wav", 0.75f, false);
	}
	public void torchBurn() {
		sfx.newSound(1, "torchon.wav", 0.75f, false);
	}
	
	public void victoryJingle() {
		sfx.newSound(0, "victory.wav", 0.75f, false);
	}
	public void gameOverJingle() {
		sfx.newSound(0, "game_over.wav", 0.75f, false);
	}
	
	public void chooseMenuOption() {
		sfx.newSound(1, "menu_hover.wav", 0.75f, false);
	}
	public void menuImportant() {
		sfx.newSound(1, "menu_select.wav", 0.75f, false);
	}
	
}
