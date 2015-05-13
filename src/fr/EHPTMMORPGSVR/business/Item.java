package fr.EHPTMMORPGSVR.business;

public abstract class  Item implements Constants{
	private String name;
	public abstract boolean use(PlayableCharacter user);
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
}
