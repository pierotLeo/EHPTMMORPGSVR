package fr.EHPTMMORPGSVR.dialog;

import java.awt.*;

import javax.swing.*;

import fr.EHPTMMORPGSVR.business.*;

public class MapPanel extends JPanel{
	private GameEngine game;
	private JButton[][] map;
	
	public MapPanel(GameEngine game){
		super(new GridLayout(game.MAP_WIDTH, game.MAP_HEIGHT));
		this.game = game;
		map = new JButton[game.MAP_WIDTH][game.MAP_HEIGHT];
		buildMapPanel();
		//Thread drawMap = new Thread(new MapArea(game, map));
		//drawMap.start();
		
	}
	
	public void buildMapPanel(){
		Object visibleTile = null;
		String tileValue = "";
		
		for(int i = 0; i<game.MAP_WIDTH; i++){
			for(int j = 0; j<game.MAP_HEIGHT; j++){
				JButton mapTile = new JButton();
				mapTile.setEnabled(false);

				if(game.getMap().isInSight(game.getPlayer(), i, j) && game.getPlayer().isAlive()){
					visibleTile = game.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = currentTile.getName();
					}
					if(visibleTile instanceof Item){
						tileValue = "i";
					}
					mapTile.setText(tileValue);
					mapTile.setBackground(Color.WHITE);
					if(visibleTile instanceof Obstacle){
						mapTile.setText("");
						mapTile.setBackground(Color.BLACK);
					}
					if(visibleTile == null){
						mapTile.setText("");
					}
					
				}
				else{
					mapTile.setBackground(Color.GRAY);
				}
				//if(!game.getPlayer().isAlive()){
					visibleTile = game.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = currentTile.getName();
					}
					if(visibleTile instanceof Item){
						tileValue = "i";
					}
					mapTile.setText(tileValue);
					mapTile.setBackground(Color.WHITE);
					if(visibleTile instanceof Obstacle){
						mapTile.setText("");
						mapTile.setBackground(Color.BLACK);
					}
					if(visibleTile == null){
						mapTile.setText("");
					}
				//}
					map[i][j] = mapTile;
					this.add(map[i][j]);
					
			}
		}
		//System.out.println(game.getMap());
	}
	
}
