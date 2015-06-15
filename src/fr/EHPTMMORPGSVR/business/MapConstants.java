package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public interface MapConstants{
	//map
	public static final int MAP_HEIGHT = 10;
	public static final int MAP_WIDTH = 10;
	public static final int DEFAULT_LAT_POSITION = 0;
	public static final int DEFAULT_LONG_POSITION = 5;
	public static final int DEFAULT_PERIOD = 5000;
	public static final int LEFT = 1;
	public static final int RIGHT = -1;
	public static final int DOWN = -10;
	public static final int UP = 10;
	
	public static final int MAX_NPC_NUMBER = 2;
	public static final int MAX_SURROUNDING_ENNEMIES = 4;
	public static final int MAX_PLAYERS_PER_MAP = 10;
}
