package fr.EHPTMMORPGSVR.server;

import fr.EHPTMMORPGSVR.business.CharacterConstants;
import fr.EHPTMMORPGSVR.business.GlobalConstants;
import fr.EHPTMMORPGSVR.business.MapConstants;
import fr.EHPTMMORPGSVR.business.StuffConstants;

public interface ServerConstants extends GlobalConstants, CharacterConstants, StuffConstants, MapConstants{
	public static final int SEND_CHARACTER = 0;
	public static final int GET_CHARACTER = 1;
	public static final int GET_MAP = 2;
	public static final int ATTACK = 3;
	public static final int MOVE = 4;
		public static final int UP = 0;
		public static final int DOWN = 1;
		public static final int RIGHT = 2;
		public static final int LEFT = 3;
	public static final int GET_INVENTORY = 5;
		public static final int GET_ITEM_AT = 0;
		public static final int USE_ITEM_AT = 1;
		public static final int DELETE_ITEM_AT = 2;
	public static final int GET_STUFF = 6;
	public static final int CHANGE_HAND = 7;
	public static final int UPGRADE = 8;
	public static final int SEND_MAP = 9;
	public static final int REFRESH_ALL = 10;
	
	public static final int REFRESH_ALL_MAP = 0;
	public static final int REFRESH_PERSONNAL_STATUS = 1;
		public static final int HEALTH = 0;
		public static final int ALL = 1;
	
	public static final int DEFAULT_INCREMENTATION = 1;
	
	
	
	public static final int STUFF = 0;
	public static final int INVENTORY = 1;
	
	public static final int ARMOR = 1;
	public static final int OFF_HAND = 2;
	public static final int MAIN_HAND = 3;

	
	public static final int QUIT = 9;
	
	public static final int REFRESH_MAP = 0;
	
	public static final int HEADER = 0;
	public static final int MAIN_DATA = 1;
	public static final int MISC_DATA = 2;
	public static final int TAIL = 3;
	public static final int EXTRA_TAIL = 4;
	public static final int SUPA_EXTRA_TAIL = 5;
	public static final int MEGA_SUPA_EXTRA_TAIL = 6;
	public static final int ULTRA_MEGA_SUPA_EXTRA_TAIL = 7;
	public static final int BEYOND_THE_TAIL = 8;
	public static final int OVER_THE_BEYOND_OF_THE_TAIL = 9;
	
	public static final String NULL = "null";
	
	
}
