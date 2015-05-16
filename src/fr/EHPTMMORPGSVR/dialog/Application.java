package fr.EHPTMMORPGSVR.dialog;

import fr.EHPTMMORPGSVR.business.Armor;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Map;
import fr.EHPTMMORPGSVR.business.NonPlayableCharacter;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.business.Shield;
import fr.EHPTMMORPGSVR.business.Weapon;

public class Application {
	public static void main(String[] args){
		Map map = new Map();
		PlayableCharacter player = new PlayableCharacter("Rolex", 0, 5, 4, 3, map);
		player.setAvailableXp(200);
		NonPlayableCharacter mob1 = new NonPlayableCharacter("Gobelin", 600, 6, 6, 6, 6, 6, map);
		NonPlayableCharacter[] mobs = new NonPlayableCharacter[1];
		mobs[0] = mob1;
		GameEngine game = new GameEngine(player, mobs);
		Weapon loklak = new Weapon("LokLak, ténèbres des temps anciens", 7, 7);///, game.LEFT_HAND);
		Weapon pongdoh = new Weapon("Pongk'dohr, le marteleur planétaire", 9, 9);//, game.RIGHT_HAND);
		Shield chitine = new Shield("Ecaille de l'éventreur de mondes", 5,5); 
		Armor skin = new Armor("Artère du pilier-Monde", 5,5, game.TORSO);
		
		map.randomSetOnItemGrid(loklak);
		map.randomSetOnItemGrid(pongdoh);
		map.randomSetOnItemGrid(chitine);
		map.randomSetOnItemGrid(skin);
		
		GameWindow window = new GameWindow("Meuporg", game);
	}
}
