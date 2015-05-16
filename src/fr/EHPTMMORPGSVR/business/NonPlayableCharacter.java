package fr.EHPTMMORPGSVR.business;

import java.util.ArrayList;
import java.util.Random;

public class NonPlayableCharacter extends DefaultCharacter implements MapConstants{
	public NonPlayableCharacter(String name, int xp, int init, int hit, int dodge, int def, int dmg, Map map){//, Coordinate lineOfSight){
		super(name, xp, init, hit, dodge, def, dmg, map);
		this.setMap(map);
		//setLineOfSight(lineOfSight);
		Thread npcBrain = new Thread(new ArtificialIntelligence(this));
		//npcBrain.start();
	}
	
	public void activateAI(){
		
			
		
	}
	
	public ArrayList<Coordinate> possibleMovments(){
		ArrayList<Coordinate> possibleMovments = new ArrayList<Coordinate>();
		for(int i=0; i<MAP_HEIGHT; i++){
			for(int j=0; j<MAP_WIDTH; j++){
				if(getMap().isAvailable(i, j)){
					possibleMovments.add(new Coordinate(i, j));
				}
			}
		}
		return possibleMovments;
	}
	
	public void dropItem(){
		
	}
	
	/*public int evaluate(){
		int distance;
		ArrayList<Coordinate> possibleMovments = possibleMovments();
		
		
		
		
	}*/
	
	/*public void act(){
		if(playerNear()){
			aggroPlayer();
		}
		else
			randomMove();
	}
	
	public PlayableCharacter checkAround(Coordinate area){
		for(int i=-area.getX(); i<area.getX(); i++){
			for(int j=-area.getY(); j<area.getY(); j++)
				if(map.getOnCharactersGrid(map.getCoordinate(this).getX()+j, map.getCoordinate(this).getY()+i) != null);
					
		}
	ArrayList<Path> path = new ArrayList<Path>();
	
	}
	
	public void moveTo(DefaultCharacter target){
		Coordinate targetLoc = map.getCoordinate(target);
		Coordinate thisLoc = map.getCoordinate(this);
		if(targetLoc.getX()>thisLoc.getX()){
			map.move(this, RIGHT);
		}
	
	}
	//public PlayableCharacter playerNear(){
		/*Coordinate area = new Coordinate();
		while(getLineOfSight().contains(area)){
			for(int i=0; i<area.getX(); i++){
				for(int j=0; j<area.getY(); j++){
					return areaAroundContainsPlayer();
				}
			}
			area.growsInto(getLineOfSight(), 2);
		}
		Coordinate mob = map.getCoordinate(this);
		Coordinate area = new Coordinate();
		while(getLineOfSight().contains(area)){
			
		}
		
	}*/
	
}
