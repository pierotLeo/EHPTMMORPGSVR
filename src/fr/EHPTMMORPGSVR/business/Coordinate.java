package fr.EHPTMMORPGSVR.business;

public class Coordinate {
	int x;
	int y;
	
	public Coordinate(){
	}
	
	public Coordinate(int x, int y){
		this.x = x; 
		this.y = y;
	}
	
	public Coordinate(Coordinate coordinate){
		x = coordinate.getX();
		y = coordinate.getY();
	}
	
	public boolean contains(Coordinate content){
		if(x >= content.getX() && y >= content.getY())
			return true;
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getIncrementedX(int value){
		return x + value;
	}
	
	public int getIncrementedY(int value){
		return y + value;
	}
	
	public void growsInto(Coordinate container, int value){
		while(container.contains(this)){
			if(x+value <= container.getX())
				x+=value;
			if(y+value <= container.getY())
				y+=value;
		}
	}
	
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
}
