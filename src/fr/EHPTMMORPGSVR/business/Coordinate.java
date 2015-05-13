package fr.EHPTMMORPGSVR.business;

public class Coordinate {
	int x;
	int y;
	
	public Coordinate(int x, int y){
		this.x = x; 
		this.y = y;
	}
	
	public Coordinate(){
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
		
}
