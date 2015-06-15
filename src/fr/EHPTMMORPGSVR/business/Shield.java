package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public class Shield implements OffensiveGear, DefensiveGear{
	private Stat burden;
	private Stat solidity;
	private String name;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setBurden(Stat burden) {
		this.burden = burden;
	}

	public void setSolidity(Stat solidity) {
		this.solidity = solidity;
	}

	public Shield(String name, int burden, int solidity){
		setName(name);
		this.burden = new Stat(burden, "Encombrement");
		this.solidity = new Stat(solidity, "Solidité");
		this.type = "Bouclier.";
	}
	
	public String getName(){
		return name;
	}
	
	public Stat getBurden(){
		return this.burden;
	}
	
	public Stat getSolidity(){
		return this.solidity;
	}
	
	public Stat getMastery(){
		return null;
	}
	
	public Stat getImpact(){
		return null;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int use(PlayableCharacter user){
		Shield userLeftHand = (Shield) user.getStuff().getOffHand();
		if(user.getPa() - PA_TO_EQUIP >= 0){
			if(userLeftHand != null)
				user.getInventory().add(userLeftHand);
			
			if(!this.equals(userLeftHand)){
				user.getInventory().remove(this);
				user.getStuff().setOffHand(this);
			}
			else{
				user.getStuff().setOffHand(null);
			}
			user.subToPa(PA_TO_EQUIP);
			return SUCCESS;
		}
		return MISSING_PA;
	}
	
	public String toString(){
		String shield = getName() + ";" + 
					"    Statistiques: ;" +
					"        Encombrement: " + burden + ";" +
					"        Solidite: " + solidity + ";" + 
					"    Bouclier";

		return shield;
	}
}
	

