package edu.pacific.comp55.starter;

public enum ControlButtons {
	ESCAPE, VK_SPACE, VK_LEFT, VK_UP, VK_RIGHT, VK_DOWN;

	public int toInt() {
		switch(this) {
			case ESCAPE: return 27;
			case VK_SPACE: return 32;
			case VK_LEFT: return 37;
			case VK_UP: return 38;
			case VK_RIGHT: return 39;
			case VK_DOWN: return 40;
		}
		return 0;
	}

}
