package fr.EHPTMMORPGSVR.business;

public class HealScroll extends Consummable {
	private int heal;
	
	public HealScroll(int heal){
		this.heal = heal;
	}
	
	public void use(PlayableCharacter user){
		if(user.getCurrentHp() < MAX_HEALTH){
			user.heal(heal);
		}
	}
	

}
