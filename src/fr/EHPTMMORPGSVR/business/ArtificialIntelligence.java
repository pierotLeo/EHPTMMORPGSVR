package fr.EHPTMMORPGSVR.business;

import java.util.Random;

public class ArtificialIntelligence implements Runnable {
	private NonPlayableCharacter npc;
	
	public ArtificialIntelligence(NonPlayableCharacter npc){
		this.npc = npc;
	}
	
	public void run(){
	int direction;
	int sleepTime;
		
		while(npc.isAlive()){
			synchronized(this){
				Random rand = new Random();
				direction = rand.nextInt(4);
				sleepTime = 1000 + rand.nextInt(2000);
				
				DefaultCharacter target = null;
				if(npc.getMap().charactersAround(npc).size()>0)
					target = npc.getMap().charactersAround(npc).get(0);
				
				if(target != null){
					npc.attack(target);	
				}
				else {
					npc.getMap().move(npc, direction);
				}
				try{
					wait(sleepTime);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
	}
}
