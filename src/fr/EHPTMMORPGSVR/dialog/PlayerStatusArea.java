package fr.EHPTMMORPGSVR.dialog;

import fr.EHPTMMORPGSVR.business.*;

import javax.swing.*;

public class PlayerStatusArea extends JTextArea implements Runnable{
	GameEngine game;
	
	public PlayerStatusArea(GameEngine game, JPanel pane){
		super("nom : " + game.getPlayer().getName() + " \nSanté : " + game.getPlayer().getInjuryLevel() + " Expérience dépensée: " + (game.getPlayer().getTotalXp()-game.getPlayer().getAvailableXp()) + "\nExpérience disponible : " + game.getPlayer().getAvailableXp() + "\nPoints d'action : " + game.getPlayer().getPa());
		this.game = game;
		this.setEditable(false);
		pane.add(this);
	}
	
	public void run(){
		while(game.getPlayer().isAlive())
			this.setText("nom : " + game.getPlayer().getName() + " \nSanté : " + game.getPlayer().getInjuryLevel() + " Expérience dépensée: " + (game.getPlayer().getTotalXp()-game.getPlayer().getAvailableXp()) + "\nExpérience disponible : " + game.getPlayer().getAvailableXp() + "\nPoints d'action : " + game.getPlayer().getPa());
	}

}
