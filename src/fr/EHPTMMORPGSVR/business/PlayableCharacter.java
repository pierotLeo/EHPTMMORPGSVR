package fr.EHPTMMORPGSVR.business;

public class PlayableCharacter extends DefaultCharacter implements StuffConstants{
	private Stat[] characteristics;
	private Stuff stuff;
	private int availableXp;
	private int xpFromCurrentFight;
	
	
	public PlayableCharacter(String name, int level, int strength, int agility, int resistance, Map map){
		super(name,level, agility, agility, agility, resistance, strength, map);
		characteristics = new Stat[3];
		characteristics[STRENGTH]=new Stat(strength, "Force");
		characteristics[AGILITY]=new Stat(agility, "Agilité");
		characteristics[RESISTANCE]=new Stat(resistance, "Résistance");
		stuff = new Stuff(this);
	}
	
	public PlayableCharacter (String name, int xp, int init, int hit, int dodge, int def, int dmg, Map map){
		super(name, xp, init, hit, dodge, def, dmg, map);
	}
	
	public PlayableCharacter(String name, int level, int strength, int agility, int resistance, Stuff stuff, Map map){
		this(name, level, strength, agility, resistance, map);
		this.stuff = new Stuff(stuff, this);
		setPa(DEFAULT_PA);
		availableXp = getTotalXp();
	} 
	
	public void addToAllXp(int xp){
		availableXp += xp;
		addToTotalXp(xp);
	}
	
	public void addToXpFromCurrentFight(int xp){
		xpFromCurrentFight += xp;
	}
	
	public Stat getStuffAlteredAbility(int ability){
		Stat tmp = null;

		switch(ability){
			case INITIATIVE:
				tmp = super.getAbility(INITIATIVE).sub(stuff.getTotalBurden());
				break;
			case DODGE:
				tmp = super.getAbility(DODGE).sub(stuff.getTotalBurden());
				break;
			case DEFENSE:
				tmp = super.getAbility(DEFENSE).sum(stuff.getTotalSolidity());
				break;
			case HIT:
				tmp = super.getAbility(HIT);
				break;
			case DAMAGE:
				tmp = super.getAbility(DAMAGE);
				break;
		}
		return tmp;
	}
	
	//Retourne la valeur en degrés (xD + y) de la capacité souhaitée, en fonction de l'arme choisie.
	public Stat getAbility(int ability){
		Stat tmp = new Stat();
		
		switch(ability){
		case HIT:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					if(stuff.getMainHand() != null)
						tmp = getStuffAlteredAbility(HIT).sum(stuff.getMainHand().getMastery());
					else
						tmp = getStuffAlteredAbility(HIT);
					break;
				case LEFT_HAND:
					tmp = getStuffAlteredAbility(HIT);
					break;

			}
			break;
		case DAMAGE:
			switch(stuff.getAttackWeapon()){
			case RIGHT_HAND:
				if(stuff.getMainHand() != null)
					tmp = getStuffAlteredAbility(DAMAGE).sum(stuff.getMainHand().getImpact());				
				else
					tmp = getStuffAlteredAbility(DAMAGE);
				break;
			case LEFT_HAND:
				tmp = getStuffAlteredAbility(DAMAGE);
				break;

			}
			break;
		case INITIATIVE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(INITIATIVE);
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null){
						tmp = getStuffAlteredAbility(INITIATIVE).sub(stuff.getOffHand().getBurden());//-getStuff().getWeapons(LEFT_HAND).get				
					}
					else
						tmp = getStuffAlteredAbility(INITIATIVE);
					break;
			}
			break;
		case DODGE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(DODGE);
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null){
						tmp = getStuffAlteredAbility(DODGE).sub(stuff.getOffHand().getBurden());//-getStuff().getWeapons(LEFT_HAND).get	
					}
					else
						tmp = getStuffAlteredAbility(DODGE);
					break;
			}
			break;
		case DEFENSE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(DEFENSE);
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null)
						tmp = getStuffAlteredAbility(DEFENSE).sum(stuff.getOffHand().getSolidity());
					else 
						tmp = getStuffAlteredAbility(DEFENSE);
					break;
			}
			break;
		}
		
		return tmp;
	}
		
		/*Stat tmp = null;

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
	}*/
	
	public int getAvailableXp(){
		return availableXp;
	}
	
	public Stat getCharacteristic(int characteristic){
		return characteristics[characteristic];
	}
	
	public Stuff getStuff(){
		return stuff;
	}
	
	public int getXpFromCurrentFight(){
		return xpFromCurrentFight;
	}
	
	public double ratioXp(DefaultCharacter target){
		return ((double)target.getTotalXp())/((double)getTotalXp());
	}
	
	//redéfinition de la méthode mére pour prendre en compte l'armure du personnage joueur dans le lancé de dés
	/*public int roll(int ability){
		int tmp = 0;
		
		switch(ability){
		case INITIATIVE:
			try{
				tmp = characteristics[AGILITY].rollDice() - stuff.getOffHand().getBurden().rollDice();//-getStuff().getWeapons(LEFT_HAND).get				
			}
			catch(NullPointerException e){
				tmp = characteristics[AGILITY].rollDice();
			}
			break;
		case DODGE:
			try{
				tmp = characteristics[AGILITY].rollDice() - stuff.getOffHand().getBurden().rollDice();//-stuff.getTotalBurden().rollDice();
			}
			catch(NullPointerException e){
				tmp = characteristics[AGILITY].rollDice();
			}
			break;
		case DEFENSE:
			tmp = characteristics[RESISTANCE].rollDice();//+stuff.getTotalSolidity().rollDice();
			break;
		default:
			tmp = FAIL;
			break;
		}
		
		return tmp;
	}
	*/
	//Surcharge permettant d'obtenir un lancé de dé de la stat souhaitée, adapté en fonction de l'arme choisie(ici pour attaquer).
	public int roll(int abilitie){
		int tmp = 0;
		
		switch(abilitie){
		case HIT:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					if(stuff.getMainHand() != null)
						tmp = getStuffAlteredAbility(HIT).rollDice() + stuff.getMainHand().getMastery().rollDice();
					else
						tmp = getStuffAlteredAbility(HIT).rollDice();
					break;
				case LEFT_HAND:
					tmp = getStuffAlteredAbility(HIT).rollDice();
					break;

			}
			break;
		case DAMAGE:
			switch(stuff.getAttackWeapon()){
			case RIGHT_HAND:
				if(stuff.getMainHand() != null)
					tmp = getStuffAlteredAbility(DAMAGE).rollDice() + stuff.getMainHand().getImpact().rollDice();
				else
					tmp = getStuffAlteredAbility(DAMAGE).rollDice();
				break;
			case LEFT_HAND:
				tmp = getStuffAlteredAbility(DAMAGE).rollDice();
				break;

			}
			break;
		case INITIATIVE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(INITIATIVE).rollDice();
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null)
						tmp = getStuffAlteredAbility(INITIATIVE).rollDice() - stuff.getOffHand().getBurden().rollDice();//-getStuff().getWeapons(LEFT_HAND).get				
					else
						tmp = getStuffAlteredAbility(INITIATIVE).rollDice();
					break;
			}
			break;
		case DODGE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(DODGE).rollDice();
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null)
						tmp = getStuffAlteredAbility(DODGE).rollDice() - stuff.getOffHand().getBurden().rollDice();//-getStuff().getWeapons(LEFT_HAND).get				
					else
						tmp = getStuffAlteredAbility(DODGE).rollDice();
					break;
			}
			break;
		case DEFENSE:
			switch(stuff.getAttackWeapon()){
				case RIGHT_HAND:
					tmp = getStuffAlteredAbility(DEFENSE).rollDice();
					break;
				case LEFT_HAND:
					if(stuff.getOffHand() != null)
						tmp = getStuffAlteredAbility(DEFENSE).rollDice() + stuff.getOffHand().getSolidity().rollDice();
					else 
						tmp = getStuffAlteredAbility(DEFENSE).rollDice();
					break;
			}
			break;
		}
		
		return tmp;
	}
	
	public void setAvailableXp(int xp){
		availableXp = xp;
		setTotalXp(getTotalXp() + xp);
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
	
	
	//Surcharge de la méthode mére prenant en compte l'arme du joueur. 
	/*public int dealtDamage(DefaultCharacter target, int handedWeapon){
		int trueDmg = roll(DAMAGE, handedWeapon)-target.roll(DEFENSE);
		
		if(trueDmg<=0)
			return ABSORB;
		target.takeDamage(trueDmg);
		
		return trueDmg;
	}*/
	
	public int strike(DefaultCharacter target){
		int damage = 0;
		
		if(roll(HIT) > target.roll(DODGE)){
			
			subToPa(PA_TO_HIT);
			addToXpFromCurrentFight(XP_PER_HIT);
			addToAllXp(XP_PER_HIT);
			
			damage = roll(DAMAGE)-target.roll(DEFENSE);
			
			if(damage<=0)
				return ABSORB;
			target.takeDamage(damage);;
			
			if(!target.isAlive()){
				addToAllXp(xpFromVictory(target, xpFromCurrentFight));
				getMap().setOnCharactersGrid(null, getMap().getCoordinate(target).getX(), getMap().getCoordinate(target).getY());
			}
			return damage;
		}
		
		return MISS;
	}
	
	public int attack(DefaultCharacter target){
		if (target.isAlive())
			if(getMap().isNextTo(this, target)){
				if(getPa() >= PA_TO_ATTACK || getMap().dlaReached(this)){
					subToPa(PA_TO_INITIATE);
					if(getTarget() != target){
						setTarget(target);
						xpFromCurrentFight = 0;
						if(target.getTarget() != this){
							if(roll(INITIATIVE) < target.roll(INITIATIVE))
								return FAIL;
						}
					}
					return strike(target);
				}
						
				return MISSING_PA;
			}
		return ERROR;
	}
	
	public String toString(){
		String PC=super.toString() + "\n";
		
		if(stuff != null){
				PC+="Capacites:\n" + 
           		"    Score d'initiative: " + getAbility(INITIATIVE) + "\n" +
           		"    Score de toucher: " + getAbility(HIT) + "\n" +
           		"    Score d'esquive: " + getAbility(DODGE) + "\n" +
           		"    Score de defense: " +getAbility(DEFENSE) + "\n" +
           		"    Score d'attaque: " + getAbility(DAMAGE) + "\n" + 
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
				"    Main droite: " + stuff.getMainHand() + "\n" + 
				"    Main Gauche: " + stuff.getOffHand() + "\n\n";
		}
		return PC;
	}
	
	public void use(Item item){
		if(getInventory().contains(item) || stuff.contains(item)){
			item.use(this);
			this.subToPa(PA_TO_EQUIP);
		}
	}
	
	public void upgrade(int stat, int xp){
		if(availableXp - xp >= 0){
			switch(stat){
				case RESISTANCE:
					characteristics[RESISTANCE].upgrade(xp);
					super.getAbility(DEFENSE).upgrade(xp);
					break;
				case AGILITY:
					characteristics[AGILITY].upgrade(xp);
					super.getAbility(INITIATIVE).upgrade(xp);
					super.getAbility(HIT).upgrade(xp);
					super.getAbility(DODGE).upgrade(xp);
					break;
				case STRENGTH:
					characteristics[STRENGTH].upgrade(xp);
					super.getAbility(DAMAGE).upgrade(xp);
					break;
			}	
			availableXp -= xp;
		}
		
	}
	
	public boolean canUpgrade(){
		return availableXp > 0;
	}
	
	public int xpFromVictory(DefaultCharacter target, int xpFromHit){
		return ((int)((xpFromHit*ratioXp(target))));
		
	}
	
}