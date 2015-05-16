package fr.EHPTMMORPGSVR.business;

public abstract class Consummable implements Item, CharacterConstants{
	public String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
