package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import fr.EHPTMMORPGSVR.dialog.GameWindow;

public class Path implements GlobalConstants{
	private Map map;
	private ArrayList<PathCell> openCells;
	private ArrayList<PathCell> pathCells;
	private ArrayList<PathCell> closedCells;
	
	public Path(Map map){
		this.map = map;
		openCells = new ArrayList<PathCell>();
		pathCells = new ArrayList<PathCell>();
		closedCells = new ArrayList<PathCell>();
	}
	
	public ArrayList<Coordinate> findPathTo(Coordinate initiator, Coordinate target){
		PathCell start = new PathCell(initiator, target);
		PathCell curCell = start;
		PathCell neighbour;
		
		openCells.add(curCell);
		while(true){
			//condition de sortie de la boucle : lorsque la cellule courante est le point d'arrivée
			if(curCell.getDistFromEnd() == 0)
				return buildPathFrom(curCell);
			
			//fermeture de la cellule courante
			openCells.remove(curCell);
			closedCells.add(curCell);
					
			
			//analyse des 4 parents
			for(int i = -1; i<2; i += 2){
				if(map.isAvailable(curCell.getCellCoordinate().getX() + i, curCell.getCellCoordinate().getY())){
				 	neighbour = new PathCell(curCell, curCell.getCellCoordinate().getX() + i, curCell.getCellCoordinate().getY(), target);
				 	if(!openCells.contains(neighbour))
				 		openCells.add(neighbour);
				 	else if(openCells.get(openCells.indexOf(neighbour)).getCellValue() > neighbour.getCellValue()){
				 		openCells.get(openCells.indexOf(neighbour)).updateFrom(neighbour);
				 	}
				}
			}
			for(int i = -1; i<2; i += 2){
				if(map.isAvailable(curCell.getCellCoordinate().getX(), curCell.getCellCoordinate().getY() + i)){
				 	neighbour = new PathCell(curCell, curCell.getCellCoordinate().getX(), curCell.getCellCoordinate().getY() + i, target);
				 	if(!openCells.contains(neighbour))
				 		openCells.add(neighbour);
				 	else if(openCells.get(openCells.indexOf(neighbour)).getCellValue() > neighbour.getCellValue()){
				 		openCells.get(openCells.indexOf(neighbour)).updateFrom(neighbour);
				 	}
				}
			}
			
			Collections.sort(openCells);
			//sélection du plus petit F parmi les openCells
			curCell = openCells.get(0);
			//System.out.println("NOUVEAU PASSAGE :\n" + openCells);
			
		}
	}
	
	public ArrayList<Coordinate> buildPathFrom(PathCell curCell){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		PathCell child = curCell.getChildCell();
		path.add(curCell.getCellCoordinate());
		//try{
			do{
				path.add(child.getCellCoordinate());
				child = child.getChildCell();
				//System.out.println(child);
			}while(child != null);
		//}catch(NullPointerException e){}
		Collections.reverse(path);
		//System.out.println(path);
		return path;
	}
	
	/*public void bestCellParents(PathCell curCell, Coordinate target){
		ArrayList<Coordinate> neighboursCoor = mob.getMap().cellsNextTo(curCell.getCellCoordinate());
		ArrayList<PathCell> neighbours = new ArrayList<PathCell>();
		for(int i=0; i<neighboursCoor.size(); i++){
			neighbours.add(new PathCell(neighboursCoor.get(i), target));
		}
		
		for(int i=0; i<neighbours.size(); i++){
			if(mob.getMap().isAvailable(neighbours.get(i).getCellCoordinate()) && !closedCells.contains(neighbours.get(i))){
				if(openCells.contains(neighbours.get(i))){
					if(openCells.get(openCells.indexOf(neighbours.get(i))).getCellValue() > neighbours.get(i).getCellValue()){
						openCells.get(openCells.indexOf(neighbours.get(i))).updateFrom(neighbours.get(i));
					}
					
				}
				else
					openCells.add(neighbours.get(i));
			}
		}
	}*/
	
	/*public ArrayList<Coordinate> findPathTo(Coordinate target){
		Coordinate start = mob.getMap().getCoordinate(mob);
		PathCell curCell = new PathCell(start, target);
		
		openCells.add(curCell);
		while(true){
			/*for(PathCell cell: openCells){
				if(openCells.indexOf(cell) >= 1){
					if(cell.getCellValue() < openCells.get(openCells.indexOf(cell) - 1).getCellValue()){
						curCell = cell;
					}
				}
			}
			//System.out.println(openCells);
			for(int i = 0; i<openCells.size(); i++)
				if(openCells.get(i).getCellValue() < curCell.getCellValue())
					curCell = openCells.get(i);
			
			if(curCell != null){
				openCells.remove(curCell);
				closedCells.add(curCell);
			}

			if(curCell.equals(new PathCell(target, target))){
				return buildPathFrom(curCell);
			}
			
			bestCellParents(curCell, target);
			
		}
		//bestCellParents(openCells.get(0));
	}*/
	
	/*
	public int bestCellParent(PathCell curCell){
		ArrayList<PathCell> analyzedCells = new ArrayList<PathCell>();
		PathCell nextCell;
		PathCell bestCell;
		int end = 0;
		
		if(mob.getMap().isNextTo(pathCells.get(pathCells.size()-1).getCellCoordinate(),targetPosition))
			return SUCCESS;
		
		
		for(int i = -1; i<2; i+=2){
			Coordinate nextCellCoord = new Coordinate(curCell.getCellCoordinate().getX() + i, curCell.getCellCoordinate().getY());
			nextCell = new PathCell(curCell, nextCellCoord, targetPosition);
			if(mob.getMap().isAvailable(nextCellCoord.getX(), nextCellCoord.getY()) && !pathCells.contains(nextCell) && !closedCells.contains(nextCell)){
				if(!openCells.contains(nextCell))
					openCells.add(nextCell);
				else
					for(int j=0; j<openCells.size(); j++){
						if(openCells.get(j).equals(nextCell)){
							openCells.get(j).updateFrom(nextCell);
						}
					}
				analyzedCells.add(nextCell);
			}
		}
		for(int i = -1; i<2; i+=2){
			Coordinate nextCellCoord = new Coordinate(curCell.getCellCoordinate().getX(), curCell.getCellCoordinate().getY() + i);
			nextCell = new PathCell(curCell, nextCellCoord, targetPosition);
			if(mob.getMap().isAvailable(nextCellCoord.getX(), nextCellCoord.getY()) && !pathCells.contains(nextCell) && !closedCells.contains(nextCell)){
				if(!openCells.contains(nextCell))
					openCells.add(nextCell);
				else
					for(int j=0; j<openCells.size(); j++){
						if(openCells.get(j).equals(nextCell)){
							openCells.get(j).updateFrom(nextCell);
						}
					}
				analyzedCells.add(nextCell);
			}
		}
		
		if(analyzedCells.size() > 0){
			bestCell = analyzedCells.get(0);
			for(int i=0; i<analyzedCells.size(); i++){
				if(analyzedCells.get(i).getCellValue() < bestCell.getCellValue())
					bestCell = analyzedCells.get(i);
				System.out.println(analyzedCells.get(i));
			}
			if(openCells.contains(bestCell) && openCells.get(openCells.indexOf(bestCell)).getCellValue() < bestCell.getCellValue()){
				pathCells.remove(curCell);
				closedCells.add(curCell);
				end = bestCellParent(curCell.getChildCell());
			}
			else{
				pathCells.add(bestCell);
				end = bestCellParent(bestCell);
			}
		}
		else{
			closedCells.add(curCell);
			pathCells.remove(curCell);
			if(curCell.getChildCell() != null)
				end = bestCellParent(curCell.getChildCell());
		}
	
		return end;
	}*/
	
	public ArrayList<PathCell> getPathCells(){
		return this.pathCells;
	}
	
	
	/*public ArrayList<Coordinate> getPathTo(DefaultCharacter target){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();

		this.targetPosition = mob.getMap().getCoordinate(target);
		PathCell startCell = new PathCell(startPosition, targetPosition);
		pathCells.add(startCell);
		
		if(bestCellParent(startCell) == SUCCESS){
			System.out.println("NOUVEAU PASSAGE :");
			for(int i=0; i<pathCells.size(); i++){
				path.add(pathCells.get(i).getCellCoordinate());
				//System.out.println("Coordonnées : \n     x = " + path.get(i).getX() + "\n     y = " + path.get(i).getY());
			}
		}
		
		return path;
		
	}*/
	//public ArrayList<Coordinate> findPath(){
		//closedCells.add(new PathCell())
	//}
	
}
