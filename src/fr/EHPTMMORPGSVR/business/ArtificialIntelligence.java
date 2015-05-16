package fr.EHPTMMORPGSVR.business;

import java.util.Random;

public class ArtificialIntelligence implements Runnable {
	private NonPlayableCharacter npc;
	public ArtificialIntelligence(NonPlayableCharacter npc){
		this.npc = npc;
	}
	
	public void run(){
		/*Random rand = new Random();
		int direction = rand.nextInt(4);
		DefaultCharacter target = null;
		int timeLaps = (rand.nextInt(5));
		long currentTimeValue = System.nanoTime();
		if(npc.getMap().charactersAround(npc).size()>0)
			target = npc.getMap().charactersAround(npc).get(0);
		long timeSpent = currentTimeValue - npc.getDla();
		
		while(npc.isAlive()){
			if(timeSpent == timeLaps){
				if(target != null){
					npc.attack(target);
					
				}
				else {
					npc.getMap().move(npc, direction);
				}
				timeLaps = (rand.nextInt(5));
				System.out.println("timelaps : " + timeLaps);
			}
			currentTimeValue = System.nanoTime();
			timeSpent = currentTimeValue - npc.getDla();
			System.out.println( timeSpent);
			}
				currentTimeValue = System.nanoTime();
				long prout = currentTimeValue - System.nanoTime();
				System.out.println(prout);
				if(prout==0){
					System.out.println("hihi");
		}
	}*/
		
		
		
		
		while(npc.isAlive()){
			Random rand = new Random();
			int direction = rand.nextInt(4);
			DefaultCharacter target = null;
			if(npc.getMap().charactersAround(npc).size()>0)
				target = npc.getMap().charactersAround(npc).get(0);
			
			if(target != null){
				npc.attack(target);	
			}
			else {
				npc.getMap().move(npc, direction);
			}
			npc.getMap().dlaReached(npc);
		}
		
	}
}
