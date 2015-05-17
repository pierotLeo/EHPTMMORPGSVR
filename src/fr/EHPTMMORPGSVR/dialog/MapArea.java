package fr.EHPTMMORPGSVR.dialog;

import java.awt.*;

import javax.swing.*;

import fr.EHPTMMORPGSVR.business.*;

public class MapArea implements Runnable{
	private GameEngine game;
	GameWindow owner;
	
	
	public MapArea(GameEngine game, GameWindow owner){
		this.game = game;
		this.owner = owner;
	}
	
	public void run(){
		while(true){
			synchronized(this){
				owner.update();
				try{
					wait(1000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
