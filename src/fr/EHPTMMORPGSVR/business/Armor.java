package fr.EHPTMMORPGSVR.business;

public class Armor extends Gear{
	private String name;
	private int armorPiece;
	private Stat burden;
	private Stat solidity;
	
	public Armor(){
		name = "";
		burden= new Stat(0);
		solidity= new Stat(0);
	}

	public Armor (Armor armor){
		this.burden = new Stat(armor.burden);
		this.solidity = new Stat(armor.solidity);
		this.name = armor.name;
	}
	
	public Armor(String name, Stat burden, Stat solidity, int armorPiece){
		this.burden = burden;
		this.solidity = solidity;
		this.name = name;
		this.armorPiece = armorPiece;
	}
	
	public Armor(String name, int burden, int solidity, int armorPiece){
		this.burden= new Stat(burden);
		this.solidity= new Stat(solidity);
		this.name= name;
		this.armorPiece = armorPiece;
	}
	
	public String toString(){
		String armor = name + "\n" + 
					"    Statistiques: \n" +
					"        Encombrement: " + burden + "\n" +
					"        Solidite: " + solidity + "\n\n" ;

		return armor;
	};

	
	public Stat getBurden(){
		return burden;
	}
	
	public Stat getSolidity(){
		return solidity;
	}
	
	public boolean use(PlayableCharacter user){
		if(!this.equals(user.getStuff().getArmors(armorPiece))){
			user.getInventory().add(user.getStuff().getArmors(armorPiece));
			user.getStuff().setArmors(this, armorPiece);
			return true;
		}
		return false;
	}
	

}
