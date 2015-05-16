package fr.EHPTMMORPGSVR.business;

public class Stuff implements StuffConstants{
	//private OffensiveGear[] weapons;
	private OffensiveGear mainHand;
	private DefensiveGear offHand;
	private Armor[] armors;
	private int usedHand;
	DefaultCharacter user;
	
	public Stuff(PlayableCharacter user){
		//weapons = new OffensiveGear[2];
		armors = new Armor[5];
		this.user = user;
		usedHand = RIGHT_HAND;
	}
	
	public Stuff(Stuff stuff, PlayableCharacter user){
		this(user);
		/*for(int weaponPosition=0; weaponPosition < NUMBER_OF_HANDS; weaponPosition++){
			this.weapons[weaponPosition] = stuff.weapons[weaponPosition];
		}*/
		
		this.mainHand = stuff.mainHand;
		this.offHand = stuff.offHand;
		this.usedHand = stuff.usedHand;
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			this.armors[armorPiece] = stuff.armors[armorPiece];
		}
	}
	
	public Stuff(OffensiveGear mainHand, DefensiveGear offHand, Armor[] armors, PlayableCharacter user){
		this(user);
		/*for(int weaponPosition=0; weaponPosition < NUMBER_OF_HANDS; weaponPosition++){
			this.weapons[weaponPosition] = weapons[weaponPosition];
		}*/
		
		this.mainHand = mainHand;
		this.offHand = offHand;
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			this.armors[armorPiece] = armors[armorPiece];
		}
	}
	
	/*public boolean use(Item gear){
		boolean use = false;
		for(int i=0; i<5; i++)
			if(armors[i] != null)
				if(armors[i].equals(gear))
					if(!armors[i].use(user)){
						armors[i] = null;
						user.getInventory().add(gear);
						user.getInventory().trimToSize();
						use = true;
					}
		
		for(int i=0; i<2; i++)
			if(weapons[i] != null)
				if(weapons[i].equals(gear))
					if(!weapons[i].use(user)){
						weapons[i] = null;
						user.getInventory().add(gear);
						user.getInventory().trimToSize();
						use = true;
					}
		return use;
	}*/
	
	public int getAttackWeapon(){
		return usedHand;
	}
	
	public DefensiveGear getOffHand(){
		return offHand;
	}
	
	public OffensiveGear getMainHand(){
		return mainHand;
	}
	
	public void setOffHand(DefensiveGear offHand){
		this.offHand = offHand;
	}
	
	public void setMainHand(OffensiveGear mainHand){
		this.mainHand = mainHand;
	}
	
	public void setAttackWeapon(int attackWeapon){
		this.usedHand = attackWeapon;
	}
	
	public boolean contains(Item gear){
		boolean contains = false;
		
		/*for(int i=0; i<NUMBER_OF_HANDS; i++){
			if(weapons[i] != null)
				if(weapons[i].equals(item)){
					contains = true;
				}
		}*/
		if(mainHand != null)
			if(mainHand.equals(gear))
				contains = true;
		if(offHand != null)
			if(offHand.equals(gear))
				contains = true;
		
		for(int i=0; i<NUMBER_OF_PROTECTIONS; i++){
			if(armors[i] != null)
				if(armors[i].equals(gear)){
					contains = true;
				}
		}
		
		return contains;
	}
	
	public Armor getArmors(int armorPiece){
		return this.armors[armorPiece];
	}
	
	public Stat getTotalBurden(){
		Stat sumBurden = new Stat(0, "Encombrement");
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			if(armors[armorPiece]!=null){
				sumBurden = sumBurden.sum(this.armors[armorPiece].getBurden());
			}
		}
		
		return sumBurden;
	}
	
	public Stat getTotalSolidity(){
		Stat sumSolidity = new Stat(0, "Solidité");
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			if(armors[armorPiece]!=null)
				sumSolidity = sumSolidity.sum(this.armors[armorPiece].getSolidity());
		}
		
		return sumSolidity;
	}
	
	/*public OffensiveGear getWeapons(int handedWeapon){
		return this.weapons[handedWeapon];
	}*/
	
	public void remove(Item gear){
		for(int i=0; i<5; i++)
			if(armors[i] != null)
				if(armors[i].equals(gear))
					armors[i] = null;
		
		if(offHand.equals(gear))
			offHand = null;
		
		if(mainHand.equals(gear))
			mainHand = null;
		/*for(int i=0; i<2; i++)
			if(weapons[i] != null)
				if(weapons[i].equals(gear))
					weapons[i] = null;
			*/
	}
	
	public void setArmors(Armor armor, int armorPiece){
		this.armors[armorPiece] = armor;
	}
	
	/*public void setWeapons(OffensiveGear weapon, int hand){
		this.weapons[hand] = weapon;
	}*/
}
