package fr.EHPTMMORPGSVR.dialog;

import java.awt.*;

import javax.swing.*;

import fr.EHPTMMORPGSVR.business.*;

public class NorthPanel extends JPanel {
	GameEngine game;
	Thread playerStatusThread;
	
	public NorthPanel(GameEngine game){
		this.game = game;
		playerStatusThread = new Thread(new PlayerStatusArea(game, this));
		playerStatusThread.start();	
	}
}
