package fr.EHPTMMORPGSVR.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

public class Map implements MapConstants, CharacterConstants, GlobalConstants{
	private DefaultCharacter[][] charactersGrid;
	private Item[][] itemGrid;
	private Obstacle[][] obstacleGrid;
	private long period;
	
	public Map(){
		charactersGrid = new DefaultCharacter[MAP_WIDTH][MAP_HEIGHT];
		itemGrid = new Item[MAP_WIDTH][MAP_HEIGHT];	
		obstacleGrid = generateObstacleGrid();
		period = DEFAULT_PERIOD;
	}
	
	public static Obstacle[][] generateObstacleGrid(){
		Obstacle[][] obstacleGrid =  new Obstacle[MAP_WIDTH][MAP_HEIGHT];
		for(int i=0; i<MAP_WIDTH; i++){
			for(int j=0; j<MAP_HEIGHT; j++){
				if(i%2==0 && j%2==0){
					obstacleGrid[i][j] = new Obstacle();
				}
			}
		}
		return obstacleGrid;
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
					mob.activateAI();
				}
			}
		}
	}
	
	public DefaultCharacter getOnCharactersGrid(int x, int y){
		return charactersGrid[x][y];
	}
	
	public boolean isEmpty(int x, int y){
		return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null && itemGrid[x][y] == null);
	}
	
	public int move(DefaultCharacter player, int direction){
		int move = 0;
		
		if(player.getPa() >= PA_TO_MOVE || dlaReached(player)){
			switch(direction){
				case UP:
					move = moveToCoordinate(player, -1, 0);
					System.out.println("HAUT hihi");
					break;
				case DOWN:
					move = moveToCoordinate(player, 1, 0);
					System.out.println("BAS hihi");
					break;
				case LEFT:
					move = moveToCoordinate(player, 0, -1);
					System.out.println("GAUCHE hihi");
					break;
				case RIGHT:
					move = moveToCoordinate(player, 0, 1);
					System.out.println("DROITE hihi");
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
	
	public boolean dlaReached(DefaultCharacter character){
		long currentTimeValue = System.currentTimeMillis();
		if(currentTimeValue >= character.getDla() + period){
	 		character.setDla(currentTimeValue);
			character.addToPa(DEFAULT_PA + character.getAbility(INITIATIVE).intValue() + character.getPa()/2);
			return true;
		}
		return false;
	}
	
	public void randomSetOnCharactersGrid(DefaultCharacter character){
		Coordinate roll = randomEmptyCoordinates();
		this.charactersGrid[roll.getX()][roll.getY()] = character;
		//activateAI();
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
					if(charactersGrid[i][j] instanceof PlayableCharacter)
						map += " p ";
					else 
						map += " n ";
				}
				else if(itemGrid[i][j] != null){
						map += " i ";
				}
				else if(obstacleGrid[i][j] != null){
					map += " x ";
				}
				else
					map += " - ";	
			}
			map += "\n";
		}
		return map;
	}
	
	public Object getOnGrid(int x, int y){
		if(charactersGrid[x][y] != null)
			return charactersGrid[x][y];
		if(itemGrid[x][y] != null)
			return itemGrid[x][y];
		if(obstacleGrid[x][y] != null)
			return obstacleGrid[x][y];
		return null;
	}
	
	public ArrayList<DefaultCharacter> charactersAround(DefaultCharacter initiator){
		DefaultCharacter target;
		ArrayList<DefaultCharacter> charactersAround = new ArrayList<DefaultCharacter>();
		Coordinate initiatorLocation = new Coordinate(this.getCoordinate(initiator));
		
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				try{
					if(!(Math.abs(i)==1 && Math.abs(j)==1)){
						target = getOnCharactersGrid(initiatorLocation.getX()+j, initiatorLocation.getY()+i);
						if(target != null && target != initiator)
							charactersAround.add(target);
					}
				}
				catch(NullPointerException e){
					
				}
				catch(ArrayIndexOutOfBoundsException e){
						
				}
			}
		}
	
		return charactersAround;
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
		boolean left = (targetLocation.getX()+1 == charaLocation.getX() && targetLocation.getY() == charaLocation.getY());
		boolean right = (targetLocation.getX()-1 == charaLocation.getX() && targetLocation.getY() == charaLocation.getY());
		boolean up = (targetLocation.getY()-1 == charaLocation.getY() && targetLocation.getX() == targetLocation.getX());
		boolean down = (targetLocation.getY()+1 == charaLocation.getY() && targetLocation.getX() == charaLocation.getX());
		
		return left || right || up || down;
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
		return (charactersGrid[x][y] == null && obstacleGrid[x][y] == null);
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
