package fr.EHPTMMORPGSVR.business;

public class Battle implements Constants{
	private DefaultCharacter assailant;
	private DefaultCharacter defender;
	private boolean assailantRound;
	
	public Battle(DefaultCharacter assailant, DefaultCharacter defender){
		this.assailant = assailant;
		this.defender = defender;
		assailantRound = true;
	}
	
	public boolean getAssailantRound(){
		return assailantRound;
	}
	
	public DefaultCharacter getAssailant(){
		return assailant;
	}
	
	public DefaultCharacter getDefender(){
		return defender;
	}
	
	public void invertRole(){
		DefaultCharacter tmp;
		
		tmp = defender;
		defender = assailant;
		assailant = tmp;
	}
	
	public void initiate(){
		if(assailant.roll(INITIATIVE) < defender.roll(INITIATIVE)){
			invertRole();
			assailantRound = false;
		}		
	}
	
	public int battle(){
		//while(assailant.isNextTo(defender) && defender.isAlive() && assailant.isAlive() && assailant.getPa()>=PA_TO_ATTACK && !assailant.flee()){
		return 0;
		//}
	}
	
	public boolean battleBetween(DefaultCharacter fighter1, DefaultCharacter fighter2){
		if((assailant.equals(fighter1) && defender.equals(fighter2)) || (assailant.equals(fighter2) && defender.equals(fighter2)))
				return true;
		return false;
	}
}
