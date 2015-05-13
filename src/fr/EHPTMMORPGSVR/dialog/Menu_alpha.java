package fr.EHPTMMORPGSVR.dialog;

import fr.EHPTMMORPGSVR.business.*;

public class Menu_alpha implements MenuConstants{
	public Menu_alpha(){
	}
	
	public int action(GameEngine game, int action){
		int actionProgress = 0;
		switch (action){
			case MOVE_LEFT:
				actionProgress = game.getMap().move(game.getCurrentPlayer(), game.LEFT);
				break;
			case MOVE_RIGHT:
				actionProgress = game.getMap().move(game.getCurrentPlayer(), game.LEFT);
				break;
			case MOVE_UP:
				actionProgress = game.getMap().move(game.getCurrentPlayer(), game.UP);
				break;
			case MOVE_DOWN:
				actionProgress = game.getMap().move(game.getCurrentPlayer(), game.DOWN);
				break;
			case ATTACK_LEFT:
			case ATTACK_RIGHT:
			case ATTACK_UP:
			case ATTACK_DOWN:
			case DISPLAY_INVENTORY:
				game.getCurrentPlayer().getInventory();
				break;
			case MOVE_INTO_INVENTORY:
				if(game.getCurrentPlayer().getInventory().moveCurrentSlot(1))
					actionProgress = game.SUCCESS;
				else
					actionProgress = game.ERROR;
				break;
			case USE_FROM_INVENTORY:
				if(game.getCurrentPlayer().getInventory().use(game.getCurrentPlayer().getInventory().getCurrentSlot()))
					actionProgress = game.SUCCESS;
				else
					actionProgress = game.ERROR;
				break;
			case DISPLAY_STUFF:
				game.getCurrentPlayer().getStuff();
				break;
			case DISPLAY_MAP:
				game.getMap();
				break;
			//case USE_FROM_STUFF:
				//game.getCurrentPlayer().getStuff().
		}
		
		return actionProgress;
	}
	
	
}
