package fr.EHPTMMORPGSVR.business;
/**
 * Classe héritant de Gear.
 * Définit contenu et comportement d'une armure équipable par un personnage joueur.
 *
 */
public class Armor implements DefensiveGear{
	private int armorPiece;
	private Stat burden;
	private Stat solidity;
	private String name;
	
	/**
	 * Constructeur par défaut d'armure. Peu utile.
	 */
	public Armor(){
		setName("");
		burden= new Stat(0, "Encombrement");
		solidity= new Stat(0, "Solidité");
	}

	/**
	 * Constructeur par recopie d'une armure. Peu pertinent.
	 * 
	 * @param armor : Armure servant de modèle à l'objet courant.
	 */
	public Armor (Armor armor){
		this.burden = new Stat(armor.burden);
		this.solidity = new Stat(armor.solidity);
		setName(armor.getName());
	}
	
	/**
	 * Constructeur champs à champs initialisant les Stats burden et solidity avec des entiers. 
	 * 
	 * @param name : nom à recopier
	 * @param burden : encombrement servant de modèle.
	 * @param solidity : solidité servant de modèle.
	 * @param armorPiece : emplacement d'armure à recopier.
	 */
	public Armor(String name, int burden, int solidity, int armorPiece){
		this.burden= new Stat(burden, "Encombrement");
		this.solidity= new Stat(solidity, "Solidité");
		setName(name);
		this.armorPiece = armorPiece;
	}
	
	/**
	 * Constructeur champs à champs initialisant les Stats burden et solidity avec des Stats.
	 * 
	 * @param name : nom à recopier.
	 * @param burden : encombrement à recopier.
	 * @param solidity : solidité à recopier.
	 * @param armorPiece : emplacement à recopier.
	 */
	public Armor(String name, Stat burden, Stat solidity, int armorPiece){
		this.burden = burden;
		this.solidity = solidity;
		setName(name);
		this.armorPiece = armorPiece;
	}
	
	public String getName(){
		return name;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Pour récupérer l'encombrement.
	 * 
	 * @return Encombrement de l'armure.
	 */
	public Stat getBurden(){
		return burden;
	};

	/**
	 * Pour récupérer la solidité.
	 * 
	 * @return Solidité de l'armure.
	 */
	public Stat getSolidity(){
		return solidity;
	}
	
	/**
	 * Pour obtenir une définition textuelle de l'objet.
	 * 
	 */
	public String toString(){
		String armor = getName() + "\n" + 
					"    Statistiques: \n" +
					"        Encombrement: " + burden + "\n" +
					"        Solidite: " + solidity + "\n\n" ;
		switch(armorPiece){
			case HEAD:
				armor += "    Casque.";
				break;
			case TORSO:
				armor += "    Torse.";
				break;
			case HANDS:
				armor += "    Gants.";
				break;
			case LEGS:
				armor += "    Jambières.";
				break;
			case FEET:
				armor += "    Bottes.";
				break;
		}
		return armor;
	}
	
	/**
	 * Pour utiliser une armure sur un personnage.
	 * 
	 * @param user : personnage sur lequel utiliser l'armure.
	 */
	public void use(PlayableCharacter user){
		Armor armor = user.getStuff().getArmors(armorPiece);
		if(armor!=null){
			user.getInventory().add(user.getStuff().getArmors(armorPiece));
		}
		
		if(!this.equals(user.getStuff().getArmors(armorPiece))){
			user.getInventory().remove(this);
			user.getStuff().setArmors(this, armorPiece);
			//user.setAbility(INITIATIVE, user.getAbility(INITIATIVE).sub(burden, EQUIP_GEAR));
			//user.setAbility(DODGE, user.getAbility(DODGE).sub(burden, EQUIP_GEAR));
			//user.setAbility(DEFENSE, user.getAbility(DEFENSE).sum(solidity, EQUIP_GEAR));
		}
		else{
			user.getStuff().setArmors(null, armorPiece);
			//user.setAbility(INITIATIVE, user.getAbility(INITIATIVE).sum(burden, UNEQUIP_GEAR));
			//user.setAbility(DODGE, user.getAbility(DODGE).sum(burden, UNEQUIP_GEAR));
			//user.setAbility(DEFENSE, user.getAbility(DEFENSE).sub(solidity, UNEQUIP_GEAR));
		}
	}
	

}
