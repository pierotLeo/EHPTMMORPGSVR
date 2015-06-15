package fr.EHPTMMORPGSVR.business;

public class Weapon implements OffensiveGear{
	//private int hand;
	private Stat mastery;
	private Stat impact;
	private String name;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMastery(Stat mastery) {
		this.mastery = mastery;
	}

	public void setImpact(Stat impact) {
		this.impact = impact;
	}

	public Weapon(){
		mastery= new Stat(0, "");
		impact= new Stat(0, "");
		setName("");
	}
	
	public Weapon(String name, int mastery, int impact){
		this.mastery= new Stat(mastery, "Maniabilité");
		this.impact= new Stat(impact, "Impacte");
		setName(name);
		this.type = "Arme.";
		//this.hand = BOTH_HANDS;
	}
	
	public String getName(){
		return name;
	}
	
	/*public Weapon(String name, int mastery, int impact, int hand){
		this.mastery= new Stat(mastery, "Maniabilité");
		this.impact= new Stat(impact, "Impacte");
		setName(name);
		//this.hand = hand;
	}*/
	
	public void setName(String name){
		this.name = name;
	}
	
	public Weapon(String name, Stat mastery, Stat impact){
		this.mastery= mastery;
		this.impact= impact;
		setName(name);
		//this.hand = BOTH_HANDS;
	}
	
	/*public Weapon(String name, Stat mastery, Stat impact, int hand){
		this.mastery= mastery;
		this.impact= impact;
		setName(name);
		//this.hand = hand;
	}*/
	
	public Weapon (Weapon weapon){
		mastery= new Stat(weapon.mastery);
		impact= new Stat(weapon.impact);
		setName(weapon.getName());
		//hand = weapon.hand;
	}
	
	public Stat getImpact(){
		return impact;
	}
	
	public Stat getMastery(){
		return mastery;
	}
	
	public String toString(){
		String weapon = getName() + ";" + 
						"    Statistiques: ;" +
						"        Maniabilite: " + mastery + ";" +
						"        Impact: " + impact + ";" +
						"    Arme.";
						/*"        Main: ";
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
		};*/
		
		return weapon;
				
	}
	
	public int use(PlayableCharacter user){
		Weapon userRightHand = (Weapon) user.getStuff().getMainHand();
		if(user.getPa() - PA_TO_EQUIP >= 0){
			if(userRightHand != null)
				user.getInventory().add(userRightHand);
			
			if(!this.equals(userRightHand)){
				user.getInventory().remove(this);
				user.getStuff().setMainHand(this);
			}
			else{
				user.getStuff().setMainHand(null);
			}
			user.subToPa(PA_TO_EQUIP);
			return SUCCESS;
		}
		return MISSING_PA;
	}

	
}
