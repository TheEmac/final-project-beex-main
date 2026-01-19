package edu.pacific.comp55.starter;

public enum AnimState {
	IDLE_LEFT, IDLE_RIGHT, WALK_LEFT, WALK_RIGHT,
	WATER_RIGHT, WATER_LEFT,
	DOOR_OPEN, DOOR_OPENED, DOOR_CLOSE, DOOR_CLOSED,
	TORCH_FIRE, TORCH_DEAD;

	@Override
	public String toString() {
		switch (this) {
			case IDLE_LEFT: return "media/sprites/character/idle/left/char_idle";
			case IDLE_RIGHT: return "media/sprites/character/idle/right/char_idle";
			case WALK_LEFT: return "media/sprites/character/walk/left/char_walk";
			case WALK_RIGHT: return "media/sprites/character/walk/right/char_walk";

			case WATER_RIGHT: return "media/sprites/elements/water/right/water";
			case WATER_LEFT: return "media/sprites/elements/water/left/water";
			
			case DOOR_OPEN: return "media/sprites/environment/door/door";
			case DOOR_OPENED: return "media/sprites/environment/door/door_opened";
			case DOOR_CLOSE: return "media/sprites/environment/door/close/door";
			case DOOR_CLOSED: return "media/sprites/environment/door/door_closed";
			
			case TORCH_FIRE: return "media/sprites/environment/torch";
			case TORCH_DEAD: return "media/sprites/environment/deadtorch";
		}
		return null;
	}

	public int toInt() {
		switch (this) {
			case IDLE_LEFT: return 32;
			case IDLE_RIGHT: return 32;
			case WALK_LEFT: return 8;
			case WALK_RIGHT: return 8;

			case WATER_RIGHT: return 9;
			case WATER_LEFT: return 9;
			
			case DOOR_OPEN: return 5;
			case DOOR_OPENED: return 1;
			case DOOR_CLOSE: return 5;
			case DOOR_CLOSED: return 1;
			
			case TORCH_FIRE: return 3;
			case TORCH_DEAD: return 1;
		}
		return 0;
	}

}
