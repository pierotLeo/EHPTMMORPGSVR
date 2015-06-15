package fr.EHPTMMORPGSVR.dialog;

import java.io.Serializable;

public class PlayableCharacterView implements Serializable{
	public static final int STRENGTH = 0;
	public static final int RESISTANCE = 1;
	public static final int AGILITY = 2;
	public static final int NUMBER_OF_CHARACTERISTICS = 3;
	
	private int[] characteristics;
	private int id;
	private String name;
	
	public PlayableCharacterView(int str, int resist, int agi, String name){
		characteristics = new int[NUMBER_OF_CHARACTERISTICS];
		characteristics[STRENGTH] = str;
		characteristics[RESISTANCE] = resist;
		characteristics[AGILITY] = agi;
		
		this.name = name;
	}
	
	public PlayableCharacterView( String name, int str, int resist, int agi, int id){
		this(str, resist, agi, name);
		this.id = id;
	}

	public int[] getCharacteristics() {
		return characteristics;
	}

	public int getCharacteristic(int charac){
		return this.characteristics[charac];
	}
	
	public void setCharacteristics(int[] characteristics) {
		this.characteristics = characteristics;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name + "#" + this.getCharacteristic(WindowUpdate.STRENGTH) +"#" + this.getCharacteristic(WindowUpdate.RESISTANCE) +"#" + this.getCharacteristic(WindowUpdate.AGILITY) +"#" + id;
	}
}
