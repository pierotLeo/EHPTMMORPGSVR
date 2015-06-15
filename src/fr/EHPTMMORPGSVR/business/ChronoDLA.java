package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.Random;

public class ChronoDLA implements Runnable, CharacterConstants, MapConstants{
	private Map map;
	
	public ChronoDLA(Map map){
		this.map = map;
	}
	
	public void run(){
		long currentTimeValue;
		
		while(true){
				synchronized(this){
					/*currentTimeValue = System.currentTimeMillis();
					if(currentTimeValue >= character.getDla() + character.getMap().getPeriod()){
				 		character.setDla(currentTimeValue);
						character.addToPa(DEFAULT_PA + character.getAbility(INITIATIVE).intValue() + character.getPa()/2);
						
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					try{
						currentTimeValue = System.currentTimeMillis();
						for(int x=0; x<map.MAP_WIDTH; x++){
							for(int y=0; y<map.MAP_HEIGHT; y++){
								DefaultCharacter character = map.getOnCharactersGrid(x, y);
								if(character != null){
									if(currentTimeValue >= character.getDla() + map.getPeriod()){
								 		character.setDla(currentTimeValue);
										character.addToPa(DEFAULT_PA + character.getAbility(INITIATIVE).intValue() + character.getPa()/2);
									}
								
								}
							}
						}
						
						if(map.getNpcNumber() < MAX_NPC_NUMBER){
							Random rand = new Random();
							int randPick = rand.nextInt(5);
							NonPlayableCharacter npc;
							switch(randPick){
								case 0:
									npc = new NonPlayableCharacter("Succube amicale", 1000, 5, 8, 8, 4, 10, map);
									break;
								case 1:
									npc = new NonPlayableCharacter("Apprentie kunoichi", 60, 15, 1, 8, 0, 2, map);
									break;
								case 2:
									npc = new NonPlayableCharacter("Wolpertinger de guerre", 200, 5, 7, 4, 4, 8, map);
									break;
								default:
									npc = new NonPlayableCharacter("Sandwich mal digéré", 15, 3, 1, 1, 1, 3, map);
									break;
							}
							
							map.randomSetOnCharactersGrid(npc);
							wait(1000);
							
						}
					} catch(InterruptedException e){}
				}
		}
	}
}
