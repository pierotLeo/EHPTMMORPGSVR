package fr.EHPTMMORPGSVR.dialog;

import fr.EHPTMMORPGSVR.business.Armor;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Map;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.business.Weapon;

public class Application {
	public static void main(String[] args){
		Map map = new Map();
		PlayableCharacter player = new PlayableCharacter("Rolex", 6, 5, 4, 3);
		GameEngine game = new GameEngine(player, map);
		Weapon loklak = new Weapon("LokLak, ténèbres des temps anciens", 6, 6, game.RIGHT_HAND);
		Weapon pongdoh = new Weapon("Pong'Doh, le marteleur planétaire", 9, 9, game.RIGHT_HAND);
		Armor chitine = new Armor("Ecaille de l'éventreur de mondes", 1,2, game.TORSO); 
		map.randomSetOnItemGrid(loklak);
		map.randomSetOnItemGrid(pongdoh);
		map.randomSetOnItemGrid(chitine);
		
		GameWindow window = new GameWindow("MMORPG", game);
	}
}
