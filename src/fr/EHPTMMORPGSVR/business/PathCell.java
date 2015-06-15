package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.ArrayList;

public class PathCell implements Comparable, CharacterConstants{
	private int costToCur;
	private int distFromEnd;
	private Coordinate cellCoordinate;
	private PathCell childCell;
	
	public PathCell(Coordinate cellCoordinate, Coordinate targetCoordinate){
		this.cellCoordinate = cellCoordinate;
		this.costToCur = 0;
		this.distFromEnd = ((Math.abs(cellCoordinate.getX() - targetCoordinate.getX()) + Math.abs(cellCoordinate.getY() - targetCoordinate.getY())) - 1) * PA_TO_MOVE;
		this.childCell = null;
	}
	
	public PathCell(PathCell child, int x, int y, Coordinate target){
		this.cellCoordinate = new Coordinate(x, y);
		this.costToCur = child.costToCur + PA_TO_MOVE;
		this.distFromEnd = ((Math.abs(x - target.getX()) + Math.abs(y - target.getY())) - 1) * PA_TO_MOVE;
		this.childCell = child;
	}
	
	public PathCell(Coordinate targetCoordinate){
		this.cellCoordinate = targetCoordinate;
		this.costToCur = 0;
		this.distFromEnd = 0;
		this.childCell = null;
	}
	
	public PathCell(PathCell toCopy){
		this.costToCur = toCopy.costToCur;
		this.distFromEnd = toCopy.distFromEnd;
		this.cellCoordinate = new Coordinate(toCopy.cellCoordinate);
		if(toCopy.childCell!=null)
			this.childCell = new PathCell(toCopy.childCell);
	}
	
	public PathCell(PathCell childCell, Coordinate cellCoordinate, Coordinate targetCoordinate){
		this.childCell = childCell;
		this.cellCoordinate = cellCoordinate;
		this.costToCur = childCell.getCostToCur() + PA_TO_MOVE;
		this.distFromEnd = ((Math.abs(cellCoordinate.getX() - targetCoordinate.getX()) + Math.abs(cellCoordinate.getY() - targetCoordinate.getY())) - 1) * PA_TO_MOVE;
		//System.out.println(this);
	}
	
	public int getDistFromEnd(){
		return distFromEnd;
	}
	
	public int getCellValue(){
		return costToCur + distFromEnd;
	}
	
	public int getCostToCur(){
		return costToCur;
	}

	public Coordinate getCellCoordinate(){
		return cellCoordinate;
	}
	
	public int compareTo(Object o){
		if(o instanceof PathCell){
			PathCell toCompare = (PathCell) o;
			if(toCompare.getCellValue() > this.getCellValue())
				return -1;
			else if(toCompare.getCellValue() == this.getCellValue())
				return 0;
			else
				return 1;
		}
		return 0;
	}
	
	public boolean equals(Object o){
		if(o instanceof PathCell){
			PathCell toCompare = (PathCell) o ;
			if(this.cellCoordinate.equals(toCompare.cellCoordinate))
				return true;
		}
		return false;
	}
	
	public PathCell getChildCell(){
		return this.childCell;
	}
	
	public void updateFrom(PathCell newCell){
		this.costToCur = newCell.getCostToCur() + PA_TO_MOVE;
		this.childCell = newCell;
	}
	
	public String toString(){
		return "Coût à partir du départ : " + costToCur + "\n" +
				"Distance jusqu'à la cible : " + distFromEnd + "\n" +
				cellCoordinate;
				
	}
}
