package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class GameEngine implements MapConstants, GlobalConstants, CharacterConstants, StuffConstants{
	private Map map;
	private ArrayList<PlayableCharacter> players;
	private Vector<Integer> idList;
	
	//voué à disparaître
	private PlayableCharacter player;
	
	
	public GameEngine(PlayableCharacter player, NonPlayableCharacter[] mobs){
		this.player = player;
		this.map = player.getMap();
		this.map.randomSetOnCharactersGrid(player);
		for(int i=0; i<mobs.length; i++){
			this.map.randomSetOnCharactersGrid(mobs[i]);
		}
		
		//pour test serveur
		
		
	}
	
	public GameEngine(){
		this.map = new Map();
		players = new ArrayList<PlayableCharacter>();
		idList = new Vector<Integer>();
		for(int i=1; i<MAX_PLAYERS_PER_MAP; i++){
			idList.add(i);
		}
	}
	
	public int addPlayer(PlayableCharacter player){
		players.add(player);
		map.randomSetOnCharactersGrid(player);
		player.setMap(map);
		
		int id = idList.get(0);
		idList.remove(0);
		
		return id;
		
	}
	
	public void removePlayer(PlayableCharacter player){
		players.remove(player);
		map.setOnCharactersGrid(null, map.getCoordinate(player).getX(), map.getCoordinate(player).getY());
		player.setMap(null);
		
		idList.add(player.getId());
		player.setId(0);
	}
	
	public Map getMap(){
		return map;
	}
	
	public PlayableCharacter getPlayer(){
		return player;
	}
	
	
	public void setPlayer(PlayableCharacter player){
		this.player = player;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
}
