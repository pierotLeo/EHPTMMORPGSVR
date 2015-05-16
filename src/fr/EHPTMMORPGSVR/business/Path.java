package fr.EHPTMMORPGSVR.business;

import java.util.ArrayList;

public class Path {
	private ArrayList<Coordinate> steps = new ArrayList<Coordinate>();
	
	public Path(){
	}
	
	public void addStep(Coordinate step){
		steps.add(step);
	}
	
	
}
