package fr.EHPTMMORPGSVR.business;

public class Weapon extends Gear {
	private int hand;
	private Stat mastery;
	private Stat impact;
	
	public Weapon(){
		mastery= new Stat(0);
		impact= new Stat(0);
		setName("");
	}
	
	public Weapon (Weapon weapon){
		mastery= new Stat(weapon.mastery);
		impact= new Stat(weapon.impact);
		setName(weapon.getName());
		hand = weapon.hand;
	}
	
	public Weapon(String name, Stat mastery, Stat impact, int hand){
		this.mastery= mastery;
		this.impact= impact;
		setName(name);
		this.hand = hand;
	}
	
	public Weapon(String name, Stat mastery, Stat impact){
		this.mastery= mastery;
		this.impact= impact;
		setName(name);
		this.hand = BOTH_HANDS;
	}
	
	public Weapon(String name, int mastery, int impact, int hand){
		this.mastery= new Stat(mastery);
		this.impact= new Stat(impact);
		setName(name);
		this.hand = hand;
	}
	
	public Weapon(String name, int mastery, int impact){
		this.mastery= new Stat(mastery);
		this.impact= new Stat(impact);
		setName(name);
		this.hand = BOTH_HANDS;
	}
	
	public String toString(){
		String weapon = getName() + "\n" + 
						"    Statistiques: \n" +
						"        Maniabilite: " + mastery + "\n" +
						"        Impact: " + impact + "\n" + 
						"        Main: ";
		switch(hand){
			default:
				weapon += "cette arme n'a pas de restrictions d'utilisation.\n";
				break;
			case RIGHT_HAND:
				weapon += "main droite.\n";
				break;
			case LEFT_HAND:
				weapon += "main gauche.\n";
				break;
		};
		
		return weapon;
				
	}
	
	public Stat getMastery(){
		return mastery;
	}
	
	public Stat getImpact(){
		return impact;
	}
	
	public boolean use(PlayableCharacter user){
		if(!this.equals(user.getStuff().getWeapons(hand))){
			user.getInventory().add(user.getStuff().getWeapons(hand));
			user.getStuff().setWeapons(this, hand);
			return true;
		}
		return false;
	}

	
}
