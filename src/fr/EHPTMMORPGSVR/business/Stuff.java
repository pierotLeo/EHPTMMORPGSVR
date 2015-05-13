package fr.EHPTMMORPGSVR.business;

public class Stuff implements Constants{
	private Weapon[] weapons;
	private Armor[] armors;
	private PlayableCharacter user;
	
	public Stuff(PlayableCharacter user){
		weapons = new Weapon[2];
		armors = new Armor[5];
		this.user = user;
		
	}
	
	public Stuff(Stuff stuff, PlayableCharacter user){
		this(user);
		for(int weaponPosition=0; weaponPosition < NUMBER_OF_HANDS; weaponPosition++){
			this.weapons[weaponPosition] = new Weapon(stuff.weapons[weaponPosition]);
		}
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			this.armors[armorPiece] = new Armor(stuff.armors[armorPiece]);
		}
	}
	
	public Stuff(Weapon[] weapons, Armor[] armors, PlayableCharacter user){
		this(user);
		for(int weaponPosition=0; weaponPosition < NUMBER_OF_HANDS; weaponPosition++){
			this.weapons[weaponPosition] = weapons[weaponPosition];
		}
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			this.armors[armorPiece] = armors[armorPiece];
		}
	}
	
	public boolean use(Item gear){
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
	}
	
	public void remove(Item gear){
		for(int i=0; i<5; i++)
			if(armors[i] != null)
				if(armors[i].equals(gear))
					armors[i] = null;
		
		for(int i=0; i<2; i++)
			if(weapons[i] != null)
				if(weapons[i].equals(gear))
					weapons[i] = null;
			
	}
	
	public Weapon getWeapons(int handedWeapon){
		return this.weapons[handedWeapon];
	}
	
	public Armor getArmors(int armorPiece){
		return this.armors[armorPiece];
	}
	
	public Stat getTotalBurden(){
		Stat sumBurden = new Stat(0);
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			if(armors[armorPiece]!=null){
				sumBurden = sumBurden.sum(this.armors[armorPiece].getBurden());
			}
		}
		
		return sumBurden;
	}
	
	public Stat getTotalSolidity(){
		Stat sumSolidity = new Stat(0);
		
		for(int armorPiece=0; armorPiece < NUMBER_OF_PROTECTIONS; armorPiece++){
			if(armors[armorPiece]!=null)
				sumSolidity = sumSolidity.sum(this.armors[armorPiece].getSolidity());
		}
		
		return sumSolidity;
	}
	
	public void setWeapons(Weapon weapon, int hand){
		this.weapons[hand] = weapon;
	}
	
	public void setArmors(Armor armor, int armorPiece){
		this.armors[armorPiece] = armor;
	}
}
