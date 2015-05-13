package fr.EHPTMMORPGSVR.business;

public class PlayableCharacter extends DefaultCharacter{
	private Stat[] characteristics;
	private Stuff stuff;
	private int availableXp;
	private int xpFromCurrentFight;
	private Inventory inventory;
	
	public PlayableCharacter (String name, int xp, int init, int hit, int dodge, int def, int dmg){//, Map map){
		super(name, xp, init, hit, dodge, def, dmg);//, map);
	}
	
	public PlayableCharacter(String name, int level, int strength, int agility, int resistance, Stuff stuff){//, Map map){
		this(name, level, strength, agility, resistance);//, map);
		this.stuff = new Stuff(stuff, this);
		setPa(DEFAULT_PA);
		availableXp = getTotalXp();
	}
	
	public PlayableCharacter(String name, int level, int strength, int agility, int resistance){//, Map map){
		super(name,level, agility, agility, agility, resistance, strength);//, map);
		characteristics = new Stat[3];
		characteristics[STRENGTH]=new Stat(strength);
		characteristics[AGILITY]=new Stat(agility);
		characteristics[RESISTANCE]=new Stat(resistance);
		inventory = new Inventory(this);
		stuff = new Stuff(this);
	} 
	
	public String toString(){
		String PC=super.toString() + "\n";
		
		if(stuff != null){
				PC+="Capacites:\n" + 
           		"    Score d'initiative: " + getAbility(INITIATIVE) + "\n" +
           		"    Score de toucher: " +
           				"\n        -Main gauche: " + getAbility(HIT, LEFT_HAND) +
           				"\n        -Main droite: " + getAbility(HIT, RIGHT_HAND) + "\n" +
           		"    Score d'esquive: " + getAbility(DODGE) + "\n" +
           		"    Score de defense: " + getAbility(DEFENSE) + "\n" +
           		"    Score d'attaque: " + 
   						"\n        -Main gauche: " + getAbility(DAMAGE, LEFT_HAND) +
   						"\n        -Main droite: " + getAbility(DAMAGE, RIGHT_HAND) + "\n\n" +
					"Caracteristiques:\n" +
						"    Score de force: " + characteristics[STRENGTH] + "\n" +
						"    Score d'adresse: " + characteristics[AGILITY] + "\n" +
						"    Score de resistance: " + characteristics[RESISTANCE] + "\n\n" +
				"Armure : \n" +
				"    Tete: " + stuff.getArmors(HEAD) + "\n" + 
				"    Torse: " + stuff.getArmors(TORSO) + "\n" +
				"    Mains: " + stuff.getArmors(HANDS) + "\n" +
				"    Jambes: " + stuff.getArmors(LEGS) + "\n" + 
				"    Pieds: " + stuff.getArmors(FEET) + "\n\n" +
				"Armes : \n" + 
				"    Main droite: " + stuff.getWeapons(RIGHT_HAND) + "\n" + 
				"    Main Gauche: " + stuff.getWeapons(LEFT_HAND) + "\n\n";
		}
		return PC;
	}
	
	//Retourne la valeur en degr�s (xD + y) de la capacit� souhait�e, en fonction de l'arme choisie.
	public Stat getAbility(int ability, int handedWeapon){
		Stat tmp = null;

		switch(ability){
			case HIT:
				try{
					tmp = super.getAbility(HIT).sum(stuff.getWeapons(handedWeapon).getMastery());
				}
				catch (NullPointerException e){
					tmp = super.getAbility(HIT);
				}
				break;
			case DAMAGE:
				try{
					tmp = super.getAbility(DAMAGE).sum(stuff.getWeapons(handedWeapon).getImpact());
				}
				catch (NullPointerException e){
					tmp = super.getAbility(DAMAGE);
				}
				break;
		}
		return tmp;
	}
	
	public Stat getAbility(int ability){
		Stat tmp = null;

		switch(ability){
			case INITIATIVE:
					tmp = super.getAbility(INITIATIVE).sub(stuff.getTotalBurden());
					System.out.println("totalBurden = " + stuff.getTotalBurden());
				break;
			case DODGE:
		
					tmp = super.getAbility(DODGE).sub(stuff.getTotalBurden());
				
			
				break;
			case DEFENSE:
			
					tmp = super.getAbility(DEFENSE).sum(stuff.getTotalSolidity());
				
				
				break;
		}
		return tmp;
	}
	
	public Stat getCharacteristic(int characteristic){
		return characteristics[characteristic];
	}
	
	public Stuff getStuff(){
		return stuff;
	}
	
	public int getAvailableXp(){
		return availableXp;
	}
	
	public int getXpFromCurrentFight(){
		return xpFromCurrentFight;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public void setAvailableXp(int xp){
		availableXp = xp;
	}
	
	public void setCharactersistic(Stat[] characteristics){
		this.characteristics = characteristics;
	}
	
	public void setStuff(Stuff stuff){
		this.stuff = stuff;
	}
	
	public void setXpFromCurrentFight(int xp){
		xpFromCurrentFight = xp;
	}
	
	public void setInventory(Inventory inventory){
		this.inventory = inventory; 
	}
	
	public void addToAllXp(int xp){
		availableXp += xp;
		addToTotalXp(xp);
	}
	
	public void addToXpFromCurrentFight(int xp){
		xpFromCurrentFight += xp;
	}
	
	//red�finition de la m�thode m�re pour prendre en compte l'armure du personnage joueur dans le lanc� de d�s
	public int roll(int ability){
		int tmp = 0;
		
		switch(ability){
		case INITIATIVE:
			tmp = characteristics[AGILITY].rollDice()-stuff.getTotalBurden().rollDice();
			break;
		case DODGE:
			tmp = characteristics[AGILITY].rollDice()-stuff.getTotalBurden().rollDice();
			break;
		case DEFENSE:
			tmp = characteristics[RESISTANCE].rollDice()+stuff.getTotalSolidity().rollDice();
			break;
		default:
			tmp = FAIL;
			break;
		}
		
		return tmp;
	}
	
	//Surcharge permettant d'obtenir un lanc� de d� de la stat souhait�e, adapt� en fonction de l'arme choisie(ici pour attaquer).
	public int roll(int abilitie, int handedWeapon){
		int tmp = 0;
		
		switch(abilitie){
		case HIT:
			tmp = characteristics[AGILITY].rollDice()+stuff.getWeapons(handedWeapon).getMastery().rollDice();
			break;
		case DAMAGE:
			tmp = characteristics[STRENGTH].rollDice()+stuff.getWeapons(handedWeapon).getImpact().rollDice();
			break;
		default:
			tmp = FAIL;
			break;
		}
		
		return tmp;
	}
	
	
	//Surcharge de la m�thode m�re prenant en compte l'arme du joueur. 
	/*public int dealtDamage(DefaultCharacter target, int handedWeapon){
		int trueDmg = roll(DAMAGE, handedWeapon)-target.roll(DEFENSE);
		
		if(trueDmg<=0)
			return ABSORBED;
		target.takeDamage(trueDmg);
		
		return trueDmg;
	}*/
	
	/*public int strike(DefaultCharacter target, int handedWeapon){
		int damage = 0;
		
		if(roll(HIT, handedWeapon) > target.roll(DODGE)){
			
			subToPa(PA_TO_HIT);
			addToXpFromCurrentFight(XP_PER_HIT);
			addToAllXp(XP_PER_HIT);
			
			damage = roll(DAMAGE, handedWeapon)-target.roll(DEFENSE);
			
			if(damage<=0)
				return ABSORBED;
			target.takeDamage(damage);;
			
			if(!target.isAlive()){
				addToAllXp(xpFromVictory(target, xpFromCurrentFight));
			}
			return damage;
		}
		
		return MISSED;
	}
	
	//Surcharge de la m�thode m�re prenant en compte l'arme du joueur et calculant l'xp gagn�e. 
	public int attack(DefaultCharacter target, int handedWeapon){
		if (target.isAlive())
			if(getPa() >= PA_TO_ATTACK){
				subToPa(PA_TO_INITIATE);
				if(getTarget() != target){
					setTarget(target);
					xpFromCurrentFight = 0;
					if(target.getTarget() != this){
						if(roll(INITIATIVE) < target.roll(INITIATIVE))
							return FAILED;
					}
				}
				return strike(target, handedWeapon);
			}
					
		return MISSING_PA;
	}*/
	
	public void use(Weapon weapon, int hand){
		stuff.setWeapons(weapon, hand);
	}
	
	public double ratioXp(DefaultCharacter target){
		return ((double)target.getTotalXp())/((double)getTotalXp());
	}
	
	public int xpFromVictory(DefaultCharacter target, int xpFromHit){
		return ((int)((xpFromHit*ratioXp(target))));
		
	}
	
}