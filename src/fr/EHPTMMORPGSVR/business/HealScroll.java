package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public class HealScroll extends Consummable{
	private int heal;
	private String type;
	
	public int getHeal() {
		return heal;
	}

	public void setHeal(int heal) {
		this.heal = heal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HealScroll(int heal){
		this.heal = heal;
	}
	
	public int use(PlayableCharacter user){
		if(user.getPa() - PA_TO_EQUIP >= 0){
			if(user.getCurrentHp() < MAX_HEALTH){
				user.heal(heal);
			}
			user.subToPa(PA_TO_USE_CONSUMABLE);
			return SUCCESS;
		}
		return MISSING_PA;
	}
	

}
