package fr.EHPTMMORPGSVR.business;

public class Shield implements OffensiveGear, DefensiveGear{
	private Stat burden;
	private Stat solidity;
	private String name;
	
	public Shield(String name, int burden, int solidity){
		setName(name);
		this.burden = new Stat(burden, "Encombrement");
		this.solidity = new Stat(solidity, "Solidité");
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
	
	public void use(PlayableCharacter user){
		Shield userLeftHand = (Shield) user.getStuff().getOffHand();
		if(userLeftHand != null)
			user.getInventory().add(userLeftHand);
		
		if(!this.equals(userLeftHand)){
			user.getInventory().remove(this);
			user.getStuff().setOffHand(this);
		}
		else{
			user.getStuff().setOffHand(null);
		}
	}
	
	public String toString(){
		String shield = getName() + "\n" + 
					"    Statistiques: \n" +
					"        Encombrement: " + burden + "\n" +
					"        Solidite: " + solidity + "\n\n" + 
					"    Bouclier";

		return shield;
	}
}
	

