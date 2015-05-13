package fr.EHPTMMORPGSVR.business;

import java.util.ArrayList;
import java.util.Random;

public class Map implements Constants{
	private DefaultCharacter[][] charactersGrid;
	private Item[][] itemGrid;
	private Obstacle[][] obstacleGrid;
	private DefaultCharacter currentPlayer;
	
	public Map(){
		charactersGrid = new DefaultCharacter[MAP_WIDTH][MAP_HEIGHT];
		itemGrid = new Item[MAP_WIDTH][MAP_HEIGHT];	
		obstacleGrid = new Obstacle[MAP_WIDTH][MAP_HEIGHT];
	}
	
	public String toString(){
		String map = "";
		
		for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null){
					if(charactersGrid[i][j] instanceof PlayableCharacter)
						map += " p ";
					else 
						map += " m ";
				}
				else if(itemGrid[i][j] != null){
						map += " i ";
				}
				else if(obstacleGrid[i][j] != null){
					map += " X ";
				}
				else
					map += " - ";	
			}
			map += "\n";
		}
		return map;
	}
	
	public void setOnCharactersGrid(DefaultCharacter character, int x, int y){
		this.charactersGrid[x][y] = character;
	}
	
	private Coordinate randomEmptyCoordinates(){
		ArrayList<Coordinate> availableBoxes = new ArrayList<Coordinate>();
		Random rand = new Random();
		Coordinate roll;
		
		for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(isEmpty(i, j)){
					availableBoxes.add(new Coordinate(i, j));
				}
			}
		}
		roll = availableBoxes.get(rand.nextInt(availableBoxes.size()));
		availableBoxes.clear();
		return roll;
	}
	
	public void randomSetOnCharactersGrid(Item item){
		Coordinate roll = randomEmptyCoordinates();
		this.itemGrid[roll.getX()][roll.getY()] = item;
	}
	
	public void setOnItemGrid(Item item, int x, int y){
		this.itemGrid[x][y] = item;
	}
	
	public void randomSetOnItemGrid(Item item){
		Coordinate roll = randomEmptyCoordinates();
		this.itemGrid[roll.getX()][roll.getY()] = item;
	}
	
	public boolean isEmpty(int x, int y){
		return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null && itemGrid[x][y] == null);
	}
	
	private boolean isAvailable(int x, int y){
		return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null);
	}

	//A surcharger
	public int moveToCoordinate(DefaultCharacter player, int x, int y){
		int move =0;
		for(int i =0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null && i+x>=0 && j+y>=0 && i+x<MAP_WIDTH && j+y<MAP_HEIGHT){
					if(charactersGrid[i][j].equals(player) && isAvailable(i+x, j+y)){
						charactersGrid[i+x][j+y] = player;
						charactersGrid[i][j] = null;
						if(itemGrid[i+x][j+y] != null && charactersGrid[i+x][j+y] instanceof PlayableCharacter){
							 PlayableCharacter humanPlayer = (PlayableCharacter) player;	 
								 if(!humanPlayer.getInventory().isFull()){
									 humanPlayer.getInventory().add(itemGrid[i+x][j+y]);
									 itemGrid[i+x][j+y] = null;
									 move = LOOT;
									 break;
								 }
								 move = SUCCESS;
								 break;
						}
						else{
							move = SUCCESS;
							break;
						}
					}
					else 
						move = ERROR_MOVE;
				}
				else
					move = ERROR_MOVE;
			}
			if(move == LOOT || move == SUCCESS){
				player.subToPa(PA_TO_MOVE);
				break;
			}
		}
		return move;
	}
	
	public int move(DefaultCharacter player, int direction){
		int move = 0;
		
		//if(player.getPa() >= PA_TO_MOVE){
			switch(direction){
				case UP:
					move = moveToCoordinate(player, -1, 0);
					break;
				case DOWN:
					move = moveToCoordinate(player, 1, 0);
					break;
				case LEFT:
					move = moveToCoordinate(player, 0, -1);
					break;
				case RIGHT:
					move = moveToCoordinate(player, 0, 1);
					break;
			}
		//}
		return move;
	}

	public Coordinate getCoordinate(DefaultCharacter target){
		Coordinate targetLocation = new Coordinate();
		for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null)
					if(charactersGrid[i][j].equals(target)){
						targetLocation.setX(i);
						targetLocation.setY(j);
					}
			}
		}

		return targetLocation;
	}
	
}
