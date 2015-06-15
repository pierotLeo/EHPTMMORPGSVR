package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

public class Map implements MapConstants, CharacterConstants, GlobalConstants, Serializable{
	private DefaultCharacter[][] charactersGrid;
	private Item[][] itemGrid;
	private Obstacle[][] obstacleGrid;
	private long period;
	private int npcNumber;
	private ArrayList<DefaultCharacter> deadList;

	
	public Map(){
		charactersGrid = new DefaultCharacter[MAP_WIDTH][MAP_HEIGHT];
		itemGrid = new Item[MAP_WIDTH][MAP_HEIGHT];	
		obstacleGrid = generateObstacleGrid();
		period = DEFAULT_PERIOD;
		deadList = new ArrayList<DefaultCharacter>();
		npcNumber = 0;
		//Thread npcPop = new Thread(new NpcPopHandler(this));
		//npcPop.start();
		Thread chronoDla = new Thread(new ChronoDLA(this));
		chronoDla.start();
	}
	
	public ArrayList<DefaultCharacter> getDeadList(){
		return deadList;
	}
	
	public Item[][] getItemGrid(){
		return itemGrid;
	}
	
	public int getNpcNumber(){
		return npcNumber;
	}
	
	public DefaultCharacter[][] getCharactersGrid(){
		return charactersGrid;
	}
	
	public long getPeriod(){
		return period;
	}
	
	public static Obstacle[][] generateObstacleGrid(){
		Obstacle[][] obstacleGrid =  new Obstacle[MAP_WIDTH][MAP_HEIGHT];
		/*for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(i%2==0 && j%2==0){
					obstacleGrid[i][j] = new Obstacle();
				}
			}
		}*/
		obstacleGrid[4][5] = new Obstacle();
		obstacleGrid[3][5] = new Obstacle();
		obstacleGrid[2][5] = new Obstacle();
		obstacleGrid[1][5] = new Obstacle();
		obstacleGrid[5][4] = new Obstacle();
		obstacleGrid[5][5] = new Obstacle();
		obstacleGrid[5][6] = new Obstacle();
		obstacleGrid[5][7] = new Obstacle();
		obstacleGrid[5][8] = new Obstacle();
		return obstacleGrid;
	}
	
	public Obstacle getOnObstacleGrid(int x, int y){
		return obstacleGrid[x][y];
	}
	
	public boolean contains(DefaultCharacter toCheck){
		boolean contains = false;
		
		for(int x = 0; x < MAP_WIDTH; x++){
			for(int y = 0; y < MAP_HEIGHT; y++){
				if(charactersGrid[x][y] != null){
					if(charactersGrid[x][y].equals(toCheck)){
						contains = true;
						break;
					}
				}
				if(contains)
					break;
			}
			if(contains)
				break;
		}
		
		return contains;
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
	
	public void activateAI(){
		for(int i = 0; i < MAP_WIDTH; i++){
			for(int j = 0; j < MAP_HEIGHT; j++){
				if(charactersGrid[i][j] instanceof NonPlayableCharacter){
					NonPlayableCharacter mob = (NonPlayableCharacter) charactersGrid[i][j];
					mob.getNpcBrain().start();
				}
			}
		}
	}
	
	public DefaultCharacter getOnCharactersGrid(int x, int y){
		return charactersGrid[x][y];
	}
	
	public DefaultCharacter getOnCharactersGrid(DefaultCharacter toGet){
		DefaultCharacter update = null;
		
		for(int x = 0; x < MAP_WIDTH; x++){
			for(int y = 0; y < MAP_HEIGHT; y++){
				if(charactersGrid[x][y] != null){
					if(charactersGrid[x][y].equals(toGet)){
						update = charactersGrid[x][y];
						break;
					}
				}
				if(update != null)
					break;
			}
			if(update != null)
				break;
		}
		
		return update;
	}
	
	public boolean isEmpty(int x, int y){
		return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null && itemGrid[x][y] == null);
	}
	
	public int move(DefaultCharacter player, int direction){
		int move = IMMOBILIZED;
		
		if(player.getPa() >= PA_TO_MOVE && player.injuryLevel() != COMA){
			
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
		}
		return move;
	}
	
	public int moveToCoordinate(DefaultCharacter player, int x, int y){
		int move =0;
		for(int i =0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null && i+x>=0 && j+y>=0 && i+x<MAP_WIDTH && j+y<MAP_HEIGHT){
					if(charactersGrid[i][j].equals(player) && isAvailable(i+x, j+y)){
						charactersGrid[i+x][j+y] = player;
						charactersGrid[i][j] = null;
						if(itemGrid[i+x][j+y] != null){	 
							 if(!player.getInventory().isFull()){
								 player.getInventory().add(itemGrid[i+x][j+y]);
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
	/*
	public boolean dlaReached(DefaultCharacter character){
		long currentTimeValue = System.currentTimeMillis();
		if(currentTimeValue >= character.getDla() + period){
	 		character.setDla(currentTimeValue);
			character.addToPa(DEFAULT_PA + character.getAbility(INITIATIVE).intValue() + character.getPa()/2);
			return true;
		}
		return false;
	}*/
	
	public void randomSetOnCharactersGrid(PlayableCharacter character){
		Coordinate roll = randomEmptyCoordinates();
		//System.out.println("x = " + roll.getX() + "   y = " + roll.getY());
		this.charactersGrid[roll.getX()][roll.getY()] = character;
		//activateAI();
	}
	
	public void randomSetOnCharactersGrid(NonPlayableCharacter character){
		Coordinate roll = randomEmptyCoordinates();
		this.charactersGrid[roll.getX()][roll.getY()] = character;
		npcNumber++;
	}
	
	public void randomSetOnItemGrid(Item item){
		Coordinate roll = randomEmptyCoordinates();
		this.itemGrid[roll.getX()][roll.getY()] = item;
	}
	
	public void setOnCharactersGrid(DefaultCharacter character, int x, int y){
		this.charactersGrid[x][y] = character;
	}
	
	public void setOnItemGrid(Item item, int x, int y){
		this.itemGrid[x][y] = item;
	}

	public String toString(){
		String map = "";
		
		for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null){
					map += charactersGrid[i][j].getName() + ",";
				}
				else if(itemGrid[i][j] != null){
						map += "i,";
				}
				else if(obstacleGrid[i][j] != null){
					map += "x,";
				}
				else
					map += "-,";	
			}
			map += ";";
		}
		return map;
	}
	
	public Object getOnGrid(int x, int y){
		
		if(x>=0 && x<MAP_WIDTH && y>=0 && y<MAP_HEIGHT){
			if(charactersGrid[x][y] != null)
				return charactersGrid[x][y];
			if(itemGrid[x][y] != null)
				return itemGrid[x][y];
			if(obstacleGrid[x][y] != null)
				return obstacleGrid[x][y];
		}
		return null;
	}
	
	
	
	/*public DefaultCharacter characterAround(DefaultCharacter initiator){
		DefaultCharacter target = null;
		Coordinate initiatorLocation = new Coordinate(this.getCoordinate(initiator));
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
					try{
						target = getOnCharactersGrid(initiatorLocation.getX()+j, initiatorLocation.getY()+i);
						if(target!=null && target!=initiator)
							break;
						else
							target = null;
					}
					catch(NullPointerException|ArrayIndexOutOfBoundsException e){
						
					}
				
			}	
			if(target != null)
				break;
		}
		return target;
		
	}*/
	
	public boolean isNextTo(DefaultCharacter initiator, DefaultCharacter target){
		Coordinate charaLocation = getCoordinate(initiator);
		Coordinate targetLocation = getCoordinate(target);
		boolean left = (targetLocation.getX()-1 == charaLocation.getX() && targetLocation.getY() == charaLocation.getY());
		boolean right = (targetLocation.getX()+1 == charaLocation.getX() && targetLocation.getY() == charaLocation.getY());
		boolean up = (targetLocation.getY()+1 == charaLocation.getY() && targetLocation.getX() == charaLocation.getX());
		boolean down = (targetLocation.getY()-1 == charaLocation.getY() && targetLocation.getX() == charaLocation.getX());
		
		return left || right || up || down;
	}
	
	public boolean isNextTo(Coordinate initiator, Coordinate target){
		boolean left = (target.getX()-1 == initiator.getX() && target.getY() == initiator.getY());
		boolean right = (target.getX()+1 == initiator.getX() && target.getY() == initiator.getY());
		boolean up = (target.getY()+1 == initiator.getY() && target.getX() == initiator.getX());
		boolean down = (target.getY()-1 == initiator.getY() && target.getX() == initiator.getX());
		
		return left || right || up || down;
	}
	
	public ArrayList<Coordinate> cellsNextTo(Coordinate curCell){
		ArrayList<Coordinate> nextTo = new ArrayList<Coordinate>();
		
		for(int i=-1; i<2; i+=2){
			nextTo.add(new Coordinate(curCell.getX() + i, curCell.getY()));
		}
		for(int i=-1; i<2; i+=2){
			nextTo.add(new Coordinate(curCell.getX(), curCell.getY() + i));
		}
		
		return nextTo;
	}
	
	//!\deprecated
	/*public int moveToCoordinate(NonPlayableCharacter player, int x, int y){
		int move =0;
		for(int i =0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(charactersGrid[i][j] != null && i+x>=0 && j+y>=0 && i+x<MAP_WIDTH && j+y<MAP_HEIGHT){
					if(charactersGrid[i][j].equals(player) && isAvailable(i+x, j+y)){
						charactersGrid[i+x][j+y] = player;
						charactersGrid[i][j] = null;	
						move = SUCCESS;
						break;
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
	*/
	/*public int move(PlayableCharacter player, int direction){
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
	}*/
	
	public boolean isAvailable(int x, int y){
		if(x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT)
			return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null);
		return false;
	}
	
	public boolean isAvailable(Coordinate coordinate){
		if(coordinate.getX() >= 0 && coordinate.getX() < MAP_WIDTH && coordinate.getY() >= 0 && coordinate.getY() < MAP_HEIGHT){
			return(charactersGrid[coordinate.getX()][coordinate.getY()] == null && obstacleGrid[coordinate.getX()][coordinate.getY()] == null);
		}
		return false;
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
	
	public boolean isInSight(DefaultCharacter viewer, int x, int y){
		boolean isInSight = false;
		for(int i=-viewer.getLineOfSight(); i<=viewer.getLineOfSight(); i++){
			for(int j=-viewer.getLineOfSight(); j<=viewer.getLineOfSight(); j++){
				if(this.getCoordinate(viewer).getIncrementedX(i) == x && this.getCoordinate(viewer).getIncrementedY(j) == y){
					isInSight = true;
					break;
				}
			}
			if(isInSight)
				break;
		}
		return isInSight;
	}
	
}
