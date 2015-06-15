package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

/**
 * Interface contenant 	:- les niveaux de vie du personnage.
 * 						:- les valeurs mnémoniques des capacités du personnage.
 * 						:- les valeurs mnémoniques des caractéristiques du personnage.
 * 						:- 
 *
 */
public interface CharacterConstants{
	//health levels
	/**
	 * Vie maximale d'un personnage.
	 */
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
	
	//abilities
	public static final int NUMBER_OF_ABILITIES = 5;
	public static final int INITIATIVE = 0;
	public static final int HIT = 1;
	public static final int DODGE = 2;
	public static final int DEFENSE = 3;
	public static final int DAMAGE = 4;
	
	//playable character characteristics
	public static final int NUMBER_OF_CHARACTERISTICS = 3;
	public static final int STRENGTH=0;
	public static final int AGILITY=1;
	public static final int RESISTANCE=2;
	
	//PA 
	public static final int DEFAULT_PA = 5;
	public static final int PA_LIMIT = 100;
	public static final int PA_TO_ATTACK = 3;
		public static final int PA_TO_INITIATE = 2;
		public static final int PA_TO_HIT = 1;
	public static final int PA_TO_MOVE = 2;
	public static final int PA_TO_EQUIP = 2;
	public static final int PA_TO_USE_WEAPON = 2;
	public static final int PA_TO_USE_SHIELD = 2;
	public static final int PA_TO_USE_HEAD = 1;
	public static final int PA_TO_USE_TORSO = 3;
	public static final int PA_TO_USE_HANDS = 1;
	public static final int PA_TO_USE_LEGS = 2;
	public static final int PA_TO_USE_FEET = 1;
	public static final int PA_TO_USE_CONSUMABLE = 1;
	public static final int PA_TO_CHANGE_HAND = 3;
	
	//XP 
	public static final int XP_PER_HIT = 1;
	
	//LOS
	public static final int DEFAULT_LOS = 4;
	
	public static final boolean UNEQUIP_GEAR = true;
	public static final boolean EQUIP_GEAR = false;
}
