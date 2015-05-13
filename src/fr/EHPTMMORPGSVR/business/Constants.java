package fr.EHPTMMORPGSVR.business;

public interface Constants {
	
	//abilities
	public static final int NUMBER_OF_ABILITIES = 5;
	public static final int INITIATIVE = 0;
	public static final int HIT = 1;
	public static final int DODGE = 2;
	public static final int DEFENSE = 3;
	public static final int DAMAGE = 4;
	
	//playable character characteristics
	public static final int STRENGTH=0;
	public static final int AGILITY=1;
	public static final int RESISTANCE=2;
	
	//global
	public static final int LOOT = 2;
	public static final int SUCCESS = 1;
	public static final int ABSORB = 0;
	public static final int FAIL = -1;
	public static final int MISS = -2;
	public static final int MISSING_PA = -3;
	public static final int ERROR_MOVE = -4;
	public static final int ERROR = -5;
	
	//health levels
	public static final int MAX_HEALTH = 18;
	public static final int NO_INJURY = 6;
	public static final int SUPERFICIAL_INJURY = 5;
	public static final int LIGHT_INJURY = 4;
	public static final int INJURY = 3;
	public static final int SERIOUS_INJURY = 2;
	public static final int COMA = 1;
	public static final int DEATH = 0;
	
	//inventory
	public static final int INVENTORY_CAPACITY = 20;
	
	//weapon position
	public static final int NUMBER_OF_HANDS = 2;
	public static final int LEFT_HAND = 0;
	public static final int RIGHT_HAND = 1;
	public static final int BOTH_HANDS = 2;
	
	//armor piece
	public static final int NUMBER_OF_PROTECTIONS = 5;
	public static final int HEAD = 0;
	public static final int TORSO = 1;
	public static final int HANDS = 2;
	public static final int LEGS = 3;
	public static final int FEET = 4;
	
	//PA 
	public static final int DEFAULT_PA = 5;
	public static final int PA_TO_ATTACK = 3;
		public static final int PA_TO_INITIATE = 2;
		public static final int PA_TO_HIT = 1;
	public static final int PA_TO_MOVE = 2;
	public static final int PA_TO_USE_WEAPON = 2;
	public static final int PA_TO_USE_SHIELD = 2;
	public static final int PA_TO_USE_HEAD = 1;
	public static final int PA_TO_USE_TORSO = 3;
	public static final int PA_TO_USE_HANDS = 1;
	public static final int PA_TO_USE_LEGS = 2;
	public static final int PA_TO_USE_FEET = 1;
	public static final int PA_TO_USE_CONSUMABLE = 1;
	
	//XP 
	public static final int XP_PER_HIT = 1;
	
	//fight
	public static final int FIGHTER_1 = 0;
	public static final int FIGHTER_2 = 1;
	
	//map
	public static final int MAP_HEIGHT = 20;
	public static final int MAP_WIDTH = 11;
	public static final int DEFAULT_LAT_POSITION = 5;
	public static final int DEFAULT_LONG_POSITION = 0;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int UP = 3;
}
