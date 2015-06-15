package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import fr.EHPTMMORPGSVR.dialog.GameWindow;

public class NonPlayableCharacter extends DefaultCharacter implements MapConstants{
	private NpcBrain npcBrain;
	private PlayableCharacter focusedTarget;
	
	public NonPlayableCharacter(String name, int xp, int init, int hit, int dodge, int def, int dmg, Map map){//, Coordinate lineOfSight){
		super(name, xp, init, hit, dodge, def, dmg, map);
		this.setMap(map);
		//setLineOfSight(lineOfSight);
		setId(5000);
		buildLootTable();
		npcBrain = new NpcBrain(new ArtificialIntelligence(this));
		npcBrain.start();
	}
	
	public void buildLootTable(){
		Random rand = new Random();
		int randItem = rand.nextInt(15);
		Item item;
		
		switch(randItem){
			default:
				item = new HealScroll(rand.nextInt(7));
				break;
			case 0:
				item = new Weapon("LokLak, ténèbres des temps anciens", 4, 3);
				break;
			case 1:
				item = new Weapon("Pongk'dohr, le marteleur planétaire", 2, 7);
				break;
			case 2: 
				item = new Shield("Vieux sage endormi", 6,3); 
				break;
			case 3:
				item = new Armor("Hanabi, yukata aux milles étoiles", 3,5, TORSO);
				break;
			case 4:
				item = new Armor("Chaussons aux pommes", 1, 1, FEET);
				break;
			case 5:
				item = new Armor("Gantelets du gitan de la baleine échouée suspects", 5, 0, HANDS);
				break;
			case 6:
				item = new Armor("Vieux poulet mexicain", 4, 3, HEAD);
				break;
			case 7:
				item = new Armor("Pyjama du Nidus", 2, 2, LEGS);
				break;
			case 8:
				item = new Armor("Mouffles en intestin de banane", 3, 2, HANDS);
				break;
		}
		this.getInventory().add(item);
	}
	
	public Thread getNpcBrain(){
		return npcBrain;
	}
	
	public PlayableCharacter getFocusedTarget(){
		return focusedTarget;
	}
	
	public void setNpcBrain(NpcBrain npcBrain){
		this.npcBrain = npcBrain;
	}
	
	
	public boolean playerNear(){
		boolean playerNear = false;
		PlayableCharacter player = null;
		Coordinate npc = this.getMap().getCoordinate(this);
		
		for(int los = 0; los<getLineOfSight(); los++){
			for(int line = -los; line<los; line++){
				for(int col = -los; col<los; col++){
					//System.out.println("col : " + col + "\n line : " + line + "\nlos : " + los);
					if(npc.getX() + line < MAP_WIDTH && npc.getX() + line >= 0 && npc.getY() + col < MAP_HEIGHT && npc.getY() + col >= 0)
						if(getMap().getOnCharactersGrid(npc.getX() + line, npc.getY() + col) != null && getMap().getOnCharactersGrid(npc.getX() + line, npc.getY() + col) instanceof PlayableCharacter){
							player = (PlayableCharacter) getMap().getOnCharactersGrid(npc.getX() + line, npc.getY() + col);
							focusedTarget = player;
							playerNear = true;
							break;
						}
						
				}
				if(playerNear)
					break;
			}
			if(playerNear)
				break;
		}
		
		return playerNear;
	}
	
	public ArrayList<Coordinate> possibleMovments(){
		ArrayList<Coordinate> possibleMovments = new ArrayList<Coordinate>();
		for(int i=0; i<MAP_HEIGHT; i++){
			for(int j=0; j<MAP_WIDTH; j++){
				if(getMap().isAvailable(i, j)){
					possibleMovments.add(new Coordinate(i, j));
				}
			}
		}
		return possibleMovments;
	}
	
	
	/*public int evaluate(){
		int distance;
		ArrayList<Coordinate> possibleMovments = possibleMovments();
		
		
		
		
	}*/
	
	/*public void act(){
		if(playerNear()){
			aggroPlayer();
		}
		else
			randomMove();
	}
	
	public PlayableCharacter checkAround(Coordinate area){
		for(int i=-area.getX(); i<area.getX(); i++){
			for(int j=-area.getY(); j<area.getY(); j++)
				if(map.getOnCharactersGrid(map.getCoordinate(this).getX()+j, map.getCoordinate(this).getY()+i) != null);
					
		}
	ArrayList<Path> path = new ArrayList<Path>();
	
	}
	
	public void moveTo(DefaultCharacter target){
		Coordinate targetLoc = map.getCoordinate(target);
		Coordinate thisLoc = map.getCoordinate(this);
		if(targetLoc.getX()>thisLoc.getX()){
			map.move(this, RIGHT);
		}
	
	}
	//public PlayableCharacter playerNear(){
		/*Coordinate area = new Coordinate();
		while(getLineOfSight().contains(area)){
			for(int i=0; i<area.getX(); i++){
				for(int j=0; j<area.getY(); j++){
					return areaAroundContainsPlayer();
				}
			}
			area.growsInto(getLineOfSight(), 2);
		}
		Coordinate mob = map.getCoordinate(this);
		Coordinate area = new Coordinate();
		while(getLineOfSight().contains(area)){
			
		}
		
	}*/
	

}
