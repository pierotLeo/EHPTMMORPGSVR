package fr.EHPTMMORPGSVR.business;

public class Chrono implements Runnable, CharacterConstants{
	private DefaultCharacter character;
	
	public Chrono(DefaultCharacter character){
		this.character = character;
	}
	
	public void run(){
		long currentTimeValue;
		
		while(character.isAlive()){
				synchronized(this){
					currentTimeValue = System.currentTimeMillis();
					if(currentTimeValue >= character.getDla() + character.getMap().getPeriod()){
				 		character.setDla(currentTimeValue);
						character.addToPa(DEFAULT_PA + character.getAbility(INITIATIVE).intValue() + character.getPa()/2);
						
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	}
}
