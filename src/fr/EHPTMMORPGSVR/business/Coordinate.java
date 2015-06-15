package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

public class Coordinate implements Comparable, MapConstants{
	private int x;
	private int y;
	
	public Coordinate(){
	}
	
	public Coordinate(int x, int y){
		if(x>=0)
			this.x = x;
		else 
			this.x = 0;
		if(y >= 0)
			this.y = y;
		else
			this.y = 0;
	}
	
	public Coordinate(Coordinate coordinate){
		x = coordinate.getX();
		y = coordinate.getY();
	}
	
	public int compareTo(Object o){
		if(o instanceof Coordinate){
			Coordinate toCompare = (Coordinate) o;
			if(toCompare.getFullValue() > this.getFullValue())
				return -1;
			else if(toCompare.getFullValue() == this.getFullValue())
				return 0;
			else
				return 1;
		}
		
		return 0;
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
	
	public int getFullValue(){
		return x*MAP_WIDTH + y;
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
	
	public boolean equals(Object o){
		if(o instanceof Coordinate){
			Coordinate toCompare = (Coordinate) o;
			if(this.x == toCompare.x && this.y == toCompare.y)
				return true;
		}
		return false;
	}

	public String toString(){
		return "Coordonnées : \n       x = " + x + "\n       y = " + y;
	}
	
}
