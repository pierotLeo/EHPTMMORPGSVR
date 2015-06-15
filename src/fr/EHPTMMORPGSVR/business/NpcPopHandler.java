package fr.EHPTMMORPGSVR.business;

public class NpcPopHandler implements Runnable{
	public static final int MAX_NPC_NUMBER = 1;
	private Map map;
	
	public NpcPopHandler(Map map){
		this.map = map;
	}
	
	synchronized public void run(){
		while(true){
			try{
				if(map.getNpcNumber() < MAX_NPC_NUMBER){
					map.randomSetOnCharactersGrid(new NonPlayableCharacter("Gobelin", 600, 6, 6, 6, 6, 6, map));
					wait(1000);
					
				}
			}catch(InterruptedException e){}
		}
	}

}
