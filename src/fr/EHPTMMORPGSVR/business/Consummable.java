package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public abstract class Consummable implements Item, CharacterConstants{
	public String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
