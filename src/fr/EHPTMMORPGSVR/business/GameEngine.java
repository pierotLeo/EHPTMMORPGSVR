package fr.EHPTMMORPGSVR.business;

import java.sql.Date;

public class GameEngine implements Constants{
	private Map map;
	private PlayableCharacter player1;
	private PlayableCharacter player2;
	private PlayableCharacter currentPlayer;
	private Date period;
	
	public GameEngine(PlayableCharacter player1, Map map){
		this.player1 = player1;
		this.map = map;
		this.map.setOnCharactersGrid(player1, DEFAULT_LAT_POSITION, DEFAULT_LONG_POSITION);
	}
	
	public Map getMap(){
		return map;
	}
	
	public PlayableCharacter getCurrentPlayer(){
		return currentPlayer;
	}
	
	public Date getPeriod(){
		return period;
	}
	
	public PlayableCharacter getPlayer1(){
		return player1;
	}
	
	
}
