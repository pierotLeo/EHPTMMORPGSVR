package fr.EHPTMMORPGSVR.business;

public class GameEngine implements MapConstants, GlobalConstants, CharacterConstants, StuffConstants{
	private Map map;
	private PlayableCharacter player;
	
	public GameEngine(PlayableCharacter player, NonPlayableCharacter[] mobs){
		this.player = player;
		this.map = player.getMap();
		this.map.setOnCharactersGrid(player, DEFAULT_LAT_POSITION, DEFAULT_LONG_POSITION);
		for(int i=0; i<mobs.length; i++){
			this.map.randomSetOnCharactersGrid(mobs[i]);
			map.activateAI();
		}
	}
	
	public Map getMap(){
		return map;
	}
	
	public PlayableCharacter getPlayer(){
		return player;
	}
	
	
}
