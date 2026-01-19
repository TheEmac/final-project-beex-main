package edu.pacific.comp55.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GImage;

public class AnimationPlayer {

	private static final int FPS = 82; //animations play at 12 fps

	private GImage currentFrame;
	private AnimState state;
	private Timer animTimer;
	private int currentFrameNum = 1;
	private boolean loops = true;

	private static final String PNG = ".png";


	public AnimationPlayer(GImage firstFrame, AnimState firstState) {
		currentFrame = firstFrame;
		state = firstState;
		animTimer = new Timer(FPS, taskPerformed);
		animTimer.start();
	}
	
	public AnimationPlayer() {
		animTimer.start();
		return;
	}

	public void playAnim(AnimState currentState, int frame) {
		currentFrame.setImage(currentState.toString() + frame + PNG);
	}

	public void startAnim() {
		animTimer.start();
	}

	public void stopAnim() {
		animTimer.stop();
	}

	public void setAnim(AnimState newState) {
		if (state == newState) {
			return;
		}
		state = newState;
		currentFrameNum = 1;
	}
	
	public void setAnim(AnimState newState, boolean loop) {
		if (state == newState) {
			return;
		}
		loops = loop;
		state = newState;
		currentFrameNum = 1;
	}

	ActionListener taskPerformed = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentFrameNum > state.toInt() && loops) {
				currentFrameNum = 1;
			}
			else if (currentFrameNum > state.toInt() && !loops) {
				stopAnim();
				return;
			}
			playAnim(state, currentFrameNum);
			currentFrameNum++;

		}
	};

}
