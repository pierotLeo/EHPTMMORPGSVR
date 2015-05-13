package fr.EHPTMMORPGSVR.business;

import java.sql.Date;

public class DefaultCharacter implements Constants{
	
	private Stat[] abilities;
	private String name;
	private int currentHp;
	private int pa;
	private int totalXp;
	private Date dla;
	//private Map map;
	//private DefaultCharacter target;
	
	public DefaultCharacter(){//Map map){
		this("", 0, 0, 0, 0, 0, 0);//, map);
	}
	
	public DefaultCharacter(String name, int xp){// Map map){
		this(name, xp, 0, 0, 0, 0, 0);//, map);
	}
	
	public DefaultCharacter (String name, int xp, int init, int hit, int dodge, int def, int dmg){//, Map map){
		this.name = name;
		this.totalXp = xp;
		
		abilities = new Stat[NUMBER_OF_ABILITIES];
		abilities[INITIATIVE] = new Stat(init);
		abilities[HIT] = new Stat(hit);
		abilities[DODGE] = new Stat(dodge);
		abilities[DEFENSE] = new Stat(def);
		abilities[DAMAGE] = new Stat(dmg);
		
		currentHp = MAX_HEALTH;
		pa = DEFAULT_PA;
		dla = new Date(System.currentTimeMillis());
		//this.map = map;
	}
	
	public DefaultCharacter (DefaultCharacter toCopy){
		this(toCopy.name, toCopy.totalXp, toCopy.abilities[INITIATIVE].getLevel(), 
				toCopy.abilities[HIT].getLevel(), toCopy.abilities[DODGE].getLevel(), 
				toCopy.abilities[DEFENSE].getLevel(), toCopy.abilities[DAMAGE].getLevel());//, toCopy.getMap());
	}
	
	public String toString(){
		
		String character = "Nom : " + name + "\n" +
				           //"Jauge de vie: " + currentHp + "/" + MAX_HEALTH + "\n" +
				           "Sante :" + getInjuryLevel() + "\n" +
				           "Experience : " + totalXp + "\n" ;
				
		return character;
	}
		
	public String getInjuryLevel(){
		String character = "";
		switch(injuryLevel()){
		case DEATH: 
			character+= " Shindeimasuuu. \n";
			break;
		case COMA:
			character+= " Inconscient. \n";
			break;
		case SERIOUS_INJURY:
			character+= " Gravement blesse. \n";
			break;
		case INJURY:
			character+= " Blesse. \n";
			break;
		case LIGHT_INJURY:
			character+= " Legerement blesse. \n";
			break;
		case SUPERFICIAL_INJURY:
			character+= " Blessures superficielles. \n";
			break;
		case NO_INJURY:
			character+= " Supa Genki! \n";
			break;
		}
		return character;
	}
	
	public Stat getAbility(int abilitie){
		return abilities[abilitie];
	}
	
	public Date getDla(){
		return dla;
	}
	
	/*public Map getMap(){
		return map;
	}*/
	
	public int getCurrentHp(){
		return currentHp;
	}
	
	public int getPa(){
		return pa;
	}
	
	public String getName(){
		return name;
	}
	
	/*public DefaultCharacter getTarget(){
		return target;
	}*/
	
	public int getTotalXp(){
		return totalXp;
	}
	
	public void setTotalXp(int xp){
		totalXp = xp;
	}
	
	public void setPa(int pa){
		this.pa = pa;
	}
	
	/*public void setTarget(DefaultCharacter target){
		this.target = target;
	}*/
	
	public void setCurrentHp(int health){
		currentHp = health;
	}
	
	public void addToTotalXp(int xp){
		totalXp += xp;
	}
	
	public void addToPa(int pa){
		this.pa += pa;
	}
	
	public void subToPa(int pa){
		this.pa -= pa;
	}
	
	public void takeDamage(int damage){
		if(currentHp - damage >= 0){
			currentHp -= damage;
		}
			
		else
			currentHp = 0;
	}
	
	//rajoute ou soustrait de la santï¿½ ï¿½ la santï¿½ actuelle.
	public void heal(int heal){
		if(currentHp + heal < MAX_HEALTH){
			currentHp += heal;
		}
		else{
			currentHp = MAX_HEALTH;
		}
	}
	
	//retourne le niveau de blessure du personnage en fonction de ses points de vie restants.
	public int injuryLevel(){
		if(currentHp / 3 > 0)
			return currentHp/3;
		else if(currentHp / 3 == 0 && currentHp > 0)
			return currentHp/3+1;
		return DEATH;
		
	}
	
	//retourne true si le joueur est toujours en vie.
	public boolean isAlive(){
		return injuryLevel() > DEATH;
	}
				
	//retourne un lancï¿½ de dï¿½ de la stat souhaitï¿½e.
	public int roll(int stat){
		return abilities[stat].rollDice();
	}
	
	/*public boolean isNextTo(DefaultCharacter target){
		Coordinate charaLocation = map.getCoordinate(this);
		Coordinate targetLocation = map.getCoordinate(target);
		
		return (charaLocation.getX()+1 == targetLocation.getX() && charaLocation.getY() == targetLocation.getY()) || (charaLocation.getX()-1 == targetLocation.getX() && charaLocation.getY() == targetLocation.getY()) || (charaLocation.getX() == targetLocation.getX() && charaLocation.getY()+1 == targetLocation.getY()) || (charaLocation.getX()+1 == targetLocation.getX() && charaLocation.getY()-1 == targetLocation.getY());
	}*/
	
	public void flee(Battle battle, int direction, int nbMove){
		//if(getPa()>)
	}
	//!\ Combat est désormais une classe à part entière et plus une méthode de personnage.
	
	//retourne true si la joueur courant a l'initiative. (A dï¿½velopper pour donner automatiquement l'initiative au joueur ennemi en cas de false ?)
	/*public boolean initiate(DefaultCharacter target){
		int allyInit = roll(INITIATIVE);
		int tarInit = target.roll(INITIATIVE);
		
		System.out.println("Initialisation joueur : " + allyInit + "\nInitialisation cible : " + tarInit + "\n");
		if( allyInit > tarInit){
			return true;
		}
		return false;
	}*/
	
	//retourne true si le joueur courant parvient ï¿½ toucher sa cible.
	/*public boolean hit(DefaultCharacter target){
		if(roll(HIT) > target.roll(DODGE)){
			return true;
		}
		return false;
	}*/
	
	/*//retourne et inflige les damage infligï¿½s par le joueur courant ï¿½ la cible aprï¿½s la rï¿½duction liï¿½e ï¿½ l'armure. On fera ï¿½ventuellement remonter la valeur de retour ï¿½ l'utilisateur.
	public int dealtDamage(DefaultCharacter target){
		int trueDmg = roll(DAMAGE)-target.roll(DEFENSE);
		
		
		if(trueDmg<=0)
			return ABSORBED;
		target.takeDamage(trueDmg);
		
		return trueDmg;
	}*/
	
	/*public int strike(DefaultCharacter target){
		int damage;
		
		if(roll(HIT) > target.roll(DODGE)){
			damage = roll(DAMAGE)-target.roll(DEFENSE);
			if(damage<=0)
				return ABSORBED;
			target.takeDamage(damage);
			return damage;
		}
		
		return MISSED;
	}*/
	
	//initialise un combat avec la cible du joueur courant. Retourne le nombre de points de dï¿½gï¿½ts infligï¿½s.
	/*public int attack(DefaultCharacter target){
		if (target.isAlive()){
			if(pa >= PA_TO_ATTACK){	
				if(getTarget() != target){
					setTarget(target); 
					if(target.getTarget() != this)
						if(roll(INITIATIVE) < target.roll(INITIATIVE))
							return FAILED;
				}
				return strike(target);
			}	
		}		
		return MISSING_PA;
	}*/

}
