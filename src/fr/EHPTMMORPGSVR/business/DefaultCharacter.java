package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.sql.Date;

public class DefaultCharacter implements CharacterConstants, GlobalConstants, Serializable, MapConstants, StuffConstants{
	
	private Stat[] abilities;
	private String name;
	private int currentHp;
	private int pa;
	private int totalXp;
	private Inventory inventory;
	private long dla;
	private int lineOfSight;
	private Map map;
	private DefaultCharacter target;
	private int id;
	
	public DefaultCharacter(Map map){
		this("", 0, 0, 0, 0, 0, 0, map);
	}
	
	public DefaultCharacter (DefaultCharacter toCopy){
		this(toCopy.name, toCopy.totalXp, toCopy.abilities[INITIATIVE].getLevel(), 
				toCopy.abilities[HIT].getLevel(), toCopy.abilities[DODGE].getLevel(), 
				toCopy.abilities[DEFENSE].getLevel(), toCopy.abilities[DAMAGE].getLevel(), toCopy.getMap());
	}
	
	public DefaultCharacter(String name, int xp, Map map){
		this(name, xp, 0, 0, 0, 0, 0, map);
	}
	
	public DefaultCharacter (String name, int xp, int init, int hit, int dodge, int def, int dmg, Map map){
		this.name = name;
		this.totalXp = xp;
		
		abilities = new Stat[NUMBER_OF_ABILITIES];
		abilities[INITIATIVE] = new Stat(init, "Initiative");
		abilities[HIT] = new Stat(hit, "Toucher");
		abilities[DODGE] = new Stat(dodge, "Esquive");
		abilities[DEFENSE] = new Stat(def, "Défense");
		abilities[DAMAGE] = new Stat(dmg, "Dégâts");
		
		currentHp = MAX_HEALTH;
		pa = DEFAULT_PA;
		lineOfSight = DEFAULT_LOS;
		dla = System.currentTimeMillis();
		inventory = new Inventory(this);
		this.map = map;
		//Thread chrono = new Thread(new ChronoDLA(this));
		//chrono.start();
	}
	
	public DefaultCharacter (String name, int xp, int init, int hit, int dodge, int def, int dmg){
		this.name = name;
		this.totalXp = xp;
		
		abilities = new Stat[NUMBER_OF_ABILITIES];
		abilities[INITIATIVE] = new Stat(init, "Initiative");
		abilities[HIT] = new Stat(hit, "Toucher");
		abilities[DODGE] = new Stat(dodge, "Esquive");
		abilities[DEFENSE] = new Stat(def, "Défense");
		abilities[DAMAGE] = new Stat(dmg, "Dégâts");
		
		currentHp = MAX_HEALTH;
		pa = DEFAULT_PA;
		lineOfSight = DEFAULT_LOS;
		dla = System.currentTimeMillis();
		inventory = new Inventory(this);
		//Thread chrono = new Thread(new ChronoDLA(this));
		//chrono.start();
	}
	
	public void addToPa(int pa){
		if(this.pa + pa <= PA_LIMIT)
			this.pa += pa;
		else
			this.pa = PA_LIMIT;
	}
	
	public void addToTotalXp(int xp){
		totalXp += xp;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Stat getAbility(int abilitie){
		return abilities[abilitie];
	}
	
	public int getCurrentHp(){
		return currentHp;
	}
	
	public long getDla(){
		return dla;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public void setDla(long dla){
		this.dla = dla;
	}
	
	public String getInjuryLevel(){
		String character = "";
		switch(injuryLevel()){
		case DEATH: 
			character+= " Shindeimasuuu. ";
			break;
		case COMA:
			character+= " Inconscient. ";
			break;
		case SERIOUS_INJURY:
			character+= " Gravement blesse. ";
			break;
		case INJURY:
			character+= " Blesse. ";
			break;
		case LIGHT_INJURY:
			character+= " Legerement blesse. ";
			break;
		case SUPERFICIAL_INJURY:
			character+= " Blessures superficielles. ";
			break;
		case NO_INJURY:
			character+= " Supa Genki! ";
			break;
		}
		return character;
	}
	
	public Map getMap(){
		return map;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public int getLineOfSight(){
		return lineOfSight;
	}
	
	public String getName(){
		return name;
	}
	
	public DefaultCharacter getTarget(){
		return target;
	}
	
	public int getPa(){
		return pa;
	}
	
	public int getTotalXp(){
		return totalXp;
	}
	
	//rajoute ou soustrait de la santé é la santé actuelle.
	public void heal(int heal){
		if(currentHp + heal < MAX_HEALTH){
			currentHp += heal;
		}
		else{
			currentHp = MAX_HEALTH;
		}
	}
	
	public void setAbility(int ability, Stat value){
		abilities[ability] = value;
	}
	
	public void setTarget(DefaultCharacter target){
		this.target = target;
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
	
	public void dropTo(DefaultCharacter winner){
		for(int i =0; i<inventory.size(); i++){
			winner.getInventory().add(inventory.get(i));
		}
	}
	
	//retourne un lancé de dé de la stat souhaitée.
	public int roll(int stat){
		return abilities[stat].rollDice();
	}
	
	public void setCurrentHp(int health){
		currentHp = health;
	}
	
	public void setLineOfSight(int lineOfSight){
		this.lineOfSight = lineOfSight;
	}
	
	public void setPa(int pa){
		this.pa = pa;
	}
	
	public void setTotalXp(int xp){
		totalXp = xp;
	}
	
	public void subToPa(int pa){
		if(this.pa - pa >= 0)
			this.pa -= pa;
	}
				
	public void takeDamage(int damage){
		if(currentHp - damage >= 0){
			currentHp -= damage;
		}
			
		else
			currentHp = 0;
	}
	
	/*public boolean isNextTo(DefaultCharacter target){
		Coordinate charaLocation = map.getCoordinate(this);
		Coordinate targetLocation = map.getCoordinate(target);
		
		return (charaLocation.getX()+1 == targetLocation.getX() && charaLocation.getY() == targetLocation.getY()) || (charaLocation.getX()-1 == targetLocation.getX() && charaLocation.getY() == targetLocation.getY()) || (charaLocation.getX() == targetLocation.getX() && charaLocation.getY()+1 == targetLocation.getY()) || (charaLocation.getX()+1 == targetLocation.getX() && charaLocation.getY()-1 == targetLocation.getY());
	}*/
	
	public String toString(){
		
		String character = "Nom : " + name + ";" +
				           //"Jauge de vie: " + currentHp + "/" + MAX_HEALTH + "\n" +
				           "Sante :" + getInjuryLevel() + ";" +
				           "Experience : " + totalXp + ";" ;
				
		return character;
	}
	
	//retourne true si la joueur courant a l'initiative. (A développer pour donner automatiquement l'initiative au joueur ennemi en cas de false ?)
	/*public boolean initiate(DefaultCharacter target){
		int allyInit = roll(INITIATIVE);
		int tarInit = target.roll(INITIATIVE);
		
		System.out.println("Initialisation joueur : " + allyInit + "\nInitialisation cible : " + tarInit + "\n");
		if( allyInit > tarInit){
			return true;
		}
		return false;
	}*/
	
	//retourne true si le joueur courant parvient é toucher sa cible.
	/*public boolean hit(DefaultCharacter target){
		if(roll(HIT) > target.roll(DODGE)){
			return true;
		}
		return false;
	}*/
	
	//retourne et inflige les damage infligés par le joueur courant é la cible aprés la réduction liée é l'armure. On fera éventuellement remonter la valeur de retour é l'utilisateur.
	/*public int dealtDamage(DefaultCharacter target){
		int trueDmg = roll(DAMAGE)-target.roll(DEFENSE);
		
		
		if(trueDmg<=0)
			return ABSORB;
		target.takeDamage(trueDmg);
		
		return trueDmg;
	}*/
	
	/*public int strike(DefaultCharacter target){
		int damage;
		
		if(roll(HIT) > target.roll(DODGE)){
			damage = roll(DAMAGE)-target.roll(DEFENSE);
			if(damage<=0)
				return ABSORB;
			target.takeDamage(damage);
			return damage;
		}
		
		return MISS;
	}*/
	
	public DefaultCharacter getOnMap(int position){
		DefaultCharacter toGet = null;
		int x = map.getCoordinate(this).getX();
		int y = map.getCoordinate(this).getY();
		
		switch(position){
			case LEFT:
				if(x-1>=0 && y>=0 && x-1<MAP_WIDTH && y<MAP_HEIGHT)
					toGet = map.getOnCharactersGrid(x-1, y);
				break;
			case RIGHT:
				if(x+1>=0 && y>=0 && x+1<MAP_WIDTH && y<MAP_HEIGHT)
					toGet = map.getOnCharactersGrid(x+1, y);
				break;
			case UP:
				if(x>=0 && y-1>=0 && x<MAP_WIDTH && y-1<MAP_HEIGHT)
					toGet = map.getOnCharactersGrid(x, y-1);
				break;
			case DOWN:
				if(x>=0 && y+1>=0 && x<MAP_WIDTH && y+1<MAP_HEIGHT)
					toGet = map.getOnCharactersGrid(x, y+1);
				break;
		}
		return toGet;
	}
	
	public DefaultCharacter[] charactersAround(){
		DefaultCharacter[] charactersAround = new DefaultCharacter[4];
		
		/*for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				try{
					if(!(Math.abs(i)==1 && Math.abs(j)==1)){
						target = getOnCharactersGrid(initiatorLocation.getX()+j, initiatorLocation.getY()+i);
						if(target != initiator){
							charactersAround[cellTab] = target;
							cellTab++;
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e){
						
				}
			}
		}
		
		System.out.println("tableau personnages");
		for(int i=0; i<4; i++){
			System.out.println(" i = " + i + " " +charactersAround[i].getName());
		}*/
		
		charactersAround[0] = this.getOnMap(LEFT);
		charactersAround[1] = this.getOnMap(UP);
		charactersAround[2] = this.getOnMap(RIGHT);
		charactersAround[3] = this.getOnMap(DOWN);
	
		return charactersAround;
	}
	
	//initialise un combat avec la cible du joueur courant. Retourne le nombre de points de dégéts infligés.
	public int attack(DefaultCharacter target){
		int damage;
		if (target.isAlive() && injuryLevel() != COMA){
			if(map.isNextTo(this, target)){
				if(pa >= PA_TO_ATTACK){	
					if(this.target != target){
						this.target = target; 
						if(target.getTarget() != this)
							if(roll(INITIATIVE) < target.roll(INITIATIVE)){
								return FAIL;
							}
					}
					else{
						if(roll(HIT) > target.roll(DODGE)){
							damage = roll(DAMAGE)-target.roll(DEFENSE);
							if(damage<=0)
								return ABSORB;
							target.takeDamage(damage);
							if(!target.isAlive()){
								target.dropTo(this);
								if(target instanceof PlayableCharacter)
									getMap().getDeadList().add(target);
								this.setTarget(null);
								getMap().setOnCharactersGrid(null, getMap().getCoordinate(target).getX(), getMap().getCoordinate(target).getY());
							}
							return damage;
						}
						return MISS;
					}
				}
				return MISSING_PA;
			}
		}		
		return ERROR;
	}

}
