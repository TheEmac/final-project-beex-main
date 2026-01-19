package edu.pacific.comp55.starter;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

	private static final String MUSIC_FILEPATH = "media/music/";
	private static final String SFX_FILEPATH = "media/sfx/";
	private String filePath;
	private String fileName;
	private long timePosition;
	private Clip audio;
	private static ArrayList<Clip> allSounds = new ArrayList<Clip>();
	
//	Level is the volume percentage and is always represented as a float.
//	1.0 = 100% volume
//	0.5 = 50% volume
//	0.0 = 0% volume
//	and so on.
	private float level;
	
//	Sound type is represented by a binary
//	value of 1 or 0.
//	1 = SFX
//	0 = Music
	private int soundType;
	
	
//	As the name implies, determins whether the sound loops.
	private boolean loop;
	
	public SoundPlayer() {
		return;
	}
	
	public SoundPlayer(int type, String soundFile, float volumeLevel, boolean loops) {
		level = volumeLevel;
		soundType = type;
		fileName = soundFile;
		loop = loops;
		if (soundType == 0) {
			filePath = MUSIC_FILEPATH;
			playSound(filePath + soundFile);
			return;
		}
		filePath = SFX_FILEPATH;
		playSound(filePath + soundFile);
		return;
	}
	
	public void newSound(int type, String newFile, float volumeLevel, boolean loops) {
		level = volumeLevel;
		soundType = type;
		fileName = newFile;
		loop = loops;
		String location;
		if (soundType == 0) {
			filePath = MUSIC_FILEPATH;
			location = filePath + newFile;
		}
		else {
			filePath = SFX_FILEPATH;
			location = filePath + newFile;
		}
		
		playSound(location);
	}
	
	public void playSound(String location) {
		
		try {
			File soundFile = new File(location);
			
			if (soundFile.exists()) {
				allSounds.add(audio);
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
				audio = AudioSystem.getClip();
				audio.open(audioInput);
				setVolume(audio, level);
				if (loop) {
					audio.loop(Clip.LOOP_CONTINUOUSLY);
				}
				audio.start();
				
			}
			else {
				System.out.println("Audio file " + fileName + " could not be found!");
				System.out.println("Is " + filePath + " the right file path?");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void pause() {
		timePosition = audio.getMicrosecondPosition();
		audio.stop();
	}
	
	public void resume() {
		audio.setMicrosecondPosition(timePosition);
		audio.start();
	}
	
	public void stop() {
		audio.stop();
	}
	
	public void start() {
		audio.setMicrosecondPosition(0);
		audio.start();
	}
	
	public static void setVolume(Clip sound, float level) {
		FloatControl volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
		float range = volume.getMaximum() - volume.getMinimum();
		float gain = (range * level) + volume.getMinimum(); 
		volume.setValue(gain);
	}
	
}
