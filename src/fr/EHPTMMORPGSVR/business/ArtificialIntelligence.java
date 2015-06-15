package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ArtificialIntelligence implements Runnable, MapConstants, GlobalConstants{
	private NonPlayableCharacter npc;
	
	public ArtificialIntelligence(NonPlayableCharacter npc){
		this.npc = npc;
	}
	
	/*public int findPath(int coord, int count){
		int pathValue = 0;
		
		if(npc.getMap().getOnCharactersGrid(coord/npc.MAP_WIDTH, coord%npc.MAP_HEIGHT) != null && npc.getMap().getOnCharactersGrid(coord/npc.MAP_WIDTH, coord%npc.MAP_HEIGHT) instanceof PlayableCharacter){
			pathValue = count;
		}
		
		if((npc.getMap().getOnObstacleGrid(coord/npc.MAP_WIDTH +1, coord%npc.MAP_HEIGHT) == null || npc.getMap().getOnCharactersGrid(coord/npc.MAP_WIDTH +1, coord%npc.MAP_HEIGHT ) == null) && ){
			Coordinate nextCell = new Coordinate(x+1, y);
			cellPath.add(nextCell);
			findPath(x + 1, y, count + 1);
		}
		
		else if(npc.getMap().getOnObstacleGrid(x - 1, y) == null || npc.getMap().getOnCharactersGrid(x - 1, y) == null){
			Coordinate nextCell = new Coordinate(x-1, y);
			cellPath.add(nextCell);
			findPath(x - 1, y, count + 1);
		}
		
		else if(npc.getMap().getOnObstacleGrid(x, y + 1) == null || npc.getMap().getOnCharactersGrid(x, y + 1) == null){
			Coordinate nextCell = new Coordinate(x, y+1);
			cellPath.add(nextCell);
			findPath(x, y + 1, count + 1);
		}
		
		else if(npc.getMap().getOnObstacleGrid(x, y - 1) == null || npc.getMap().getOnCharactersGrid(x, y - 1) == null){
			Coordinate nextCell = new Coordinate(x, y-1);
			cellPath.add(nextCell);
			findPath(x, y - 1, count + 1);
		}
		
		
	}*/
	
	public void run(){
		int direction;
		int move;
		while(npc.isAlive()){
			synchronized(this){
				try{
					if(npc.playerNear()){
						while(!npc.getMap().isNextTo(npc, npc.getFocusedTarget())){
							Path pathFinding = new Path(npc.getMap());
							ArrayList<Coordinate> path = new ArrayList<Coordinate>();
							
							
							path = pathFinding.findPathTo(npc.getMap().getCoordinate(npc), npc.getMap().getCoordinate(npc.getFocusedTarget()));
							if(path != null){
								direction = (npc.getMap().getCoordinate(npc).getFullValue() - path.get(1).getFullValue());
								move = 0;
								switch(direction){
									case LEFT:
										move = npc.getMap().move(npc, LEFT);
										break;
									case RIGHT:
										move = npc.getMap().move(npc, RIGHT);
										break;
									case UP:
										move = npc.getMap().move(npc, UP);
										break;
									case DOWN:
										move = npc.getMap().move(npc, DOWN);
										break;
								}
							}
							wait(500);
						}
						while(npc.getMap().isNextTo(npc, npc.getFocusedTarget()) && npc.getFocusedTarget().isAlive()){
							npc.attack(npc.getFocusedTarget());
							wait(500);
						}
						
					}
					else{
						Random rand = new Random();
						direction = rand.nextInt(4);
						switch(direction){
						case 0:
							npc.getMap().move(npc, LEFT);
							break;
						case 1:
							npc.getMap().move(npc, RIGHT);
							break;
						case 2:
							npc.getMap().move(npc, UP);
							break;
						case 3:
							npc.getMap().move(npc, DOWN);
							break;
						}
						
						wait (500);
					}
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/*public void run(){
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
		
	}*/
}
