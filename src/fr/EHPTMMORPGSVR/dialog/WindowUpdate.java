package fr.EHPTMMORPGSVR.dialog;

import java.io.*;
import java.net.*;

import fr.EHPTMMORPGSVR.business.Map;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.menu.FileHandler;
import fr.EHPTMMORPGSVR.menu.SelectionMenu;
import fr.EHPTMMORPGSVR.server.ServerConstants;

public class WindowUpdate implements Runnable, ServerConstants{
	/*public static final int HEADER = 0;
	public static final int MAIN_DATA = 1;
	public static final int MISC_DATA = 2;
	public static final int TAIL = 3;
	
	public static final String NULL = "null";
	public static final int REFRESH_MAP = 0;
	public static final int MOVE = 4;*/
	
	public static final int UPGRADE_INFORMATION_QUANTITY = 4;
	public static final int SURROUNDING_INFORMATION_QUANTITY = 2;
	
	private String[] response;
	private BufferedReader fromServer;
	private ObjectInputStream clientUpdate;
	private GameWindow owner;
	
	
	
	public WindowUpdate(GameWindow owner, Socket client){
		try{
			this.owner = owner;
			this.fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.clientUpdate = new ObjectInputStream(client.getInputStream());			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		String[][] charactersAround;
		String[][] map;
		
		while(true){
			try{
				String rawResponse = fromServer.readLine();
				System.out.println(rawResponse);
				response = rawResponse.split("#");
				
				switch(Integer.parseInt(response[HEADER])){
				
					case MOVE:
						owner.getHistoryModel().addElement(response[MAIN_DATA]);
						owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
						if(!response[MISC_DATA].matches(NULL)){
							String[] inventory = buildInventoryFrom(response[MISC_DATA]);
							owner.refreshInventory(inventory);							
						}
								
						if(!response[TAIL].matches(NULL)){
							map = buildMapFrom(response[TAIL]);
							charactersAround = buildCharactersAroundFrom(response[EXTRA_TAIL]);
							owner.refreshMap(map, charactersAround);
						}
						
						owner.refreshPlayerStatus(response[SUPA_EXTRA_TAIL].replaceAll(";", "\n"));
						
						break;
						
					case REFRESH_MAP:
						map = buildMapFrom(response[MAIN_DATA]);
						charactersAround = buildCharactersAroundFrom(response[MISC_DATA]);
						owner.refreshMap(map, charactersAround);
						if(!response[TAIL].matches(" ")){
							//owner.getPlayer().setId(Integer.parseInt(response[TAIL]));
						}
						break;
					case REFRESH_PERSONNAL_STATUS:
						owner.refreshPlayerStatus(response[MAIN_DATA].replaceAll(";", "\n"));
						break;
						
					case GET_INVENTORY:
						switch(Integer.parseInt(response[MAIN_DATA])){
							case GET_ITEM_AT:
								String item = response[MISC_DATA].replaceAll(";", "\n");
								int index = Integer.parseInt(response[TAIL]);
								ItemWindow itemWindow = new ItemWindow(owner, "Item", item, index, INVENTORY);
								break;
							case USE_ITEM_AT:
								if(response[MEGA_SUPA_EXTRA_TAIL].matches(NULL)){
									String historyUpdate = "Vous utilisez " + response[MISC_DATA] + "(2PA).";
									owner.getHistoryModel().addElement(historyUpdate);
									owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
									
									String[] inventory = buildInventoryFrom(response[TAIL]);
									owner.refreshInventory(inventory);
									
									String[] stuff = buildStuffFrom(response[EXTRA_TAIL]);
									String player = response[SUPA_EXTRA_TAIL].replaceAll(";", "\n");
									owner.refreshStuff(stuff, player);
								}
								else{
									String historyUpdate = "Vous n'avez pas assez de points d'action pour utiliser " + response[MISC_DATA] + ".";
									owner.getHistoryModel().addElement(historyUpdate);
									owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
								}
								
								owner.refreshPlayerStatus(response[ULTRA_MEGA_SUPA_EXTRA_TAIL].replaceAll(";", "\n"));
								
								break;		
						}
						break;
						
					case GET_STUFF:
						switch(Integer.parseInt(response[MAIN_DATA])){
						case GET_ITEM_AT:
							String item = response[MISC_DATA].replaceAll(";", "\n");
							int index = 0;
							if(!response[TAIL].matches(NULL))
								index = Integer.parseInt(response[TAIL]);
							int subContainer = Integer.parseInt(response[EXTRA_TAIL]);
							ItemWindow itemWindow = new ItemWindow(owner, "Item", item, index, STUFF, subContainer);
							break;	
						case USE_ITEM_AT:
							if(!response[MEGA_SUPA_EXTRA_TAIL].matches(NULL)){
								String historyUpdate = "Vous déséquipez " + response[MISC_DATA] + "(2PA).";
								owner.getHistoryModel().addElement(historyUpdate);
								owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
								
								String[] inventory = buildInventoryFrom(response[TAIL]);
								owner.refreshInventory(inventory);
								
								String[] stuff = buildStuffFrom(response[EXTRA_TAIL]);
								String player = response[SUPA_EXTRA_TAIL].replaceAll(";", "\n");
								owner.refreshStuff(stuff, player);
							}
							else{
								String historyUpdate = "Vous n'avez pas assez de points d'action pour utiliser " + response[MISC_DATA] + ".";
								owner.getHistoryModel().addElement(historyUpdate);
								owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
							}
							
							owner.refreshPlayerStatus(response[ULTRA_MEGA_SUPA_EXTRA_TAIL].replaceAll(";", "\n"));
							
							break;
							
						}
						break;
					
					case CHANGE_HAND:
						owner.getHistoryModel().addElement(response[MISC_DATA]);
						owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
						
						if(Integer.parseInt(response[MAIN_DATA]) != MISSING_PA){
							switch(Integer.parseInt(response[TAIL])){
								case RIGHT_HAND:
									owner.getHand().setText("\n   Main droite  ");
									break;
								case LEFT_HAND:
									owner.getHand().setText("\n   Main gauche  ");
									break;
															
							}
							owner.refreshPlayerStatus(response[EXTRA_TAIL].replaceAll(";", "\n"));
						}
						break;
					case UPGRADE:
						//System.out.println(response[MAIN_DATA]);
						String[][] abilities = buildAbilitiesFrom(response[MAIN_DATA]);
						owner.refreshUpgradePanel(abilities, Boolean.parseBoolean(response[MISC_DATA]));
						owner.refreshPlayer(response[TAIL].replaceAll(";", "\n"));
						owner.refreshPlayerStatus(response[EXTRA_TAIL].replaceAll(";", "\n"));
						break;

					case ATTACK:
						owner.getHistoryModel().addElement(response[MAIN_DATA]);
						owner.getHistoryList().ensureIndexIsVisible(owner.getHistoryModel().size()-1);
						
						String player = response[MISC_DATA].replaceAll(";", "\n");
						owner.refreshPlayer(player);
						
						owner.refreshPlayerStatus(response[TAIL].replaceAll(";", "\n"));
						
						map = buildMapFrom(response[SUPA_EXTRA_TAIL]);
						charactersAround = buildCharactersAroundFrom(response[EXTRA_TAIL]);
						owner.refreshMap(map, charactersAround);
						owner.refreshUpgradePanel(buildAbilitiesFrom(response[MEGA_SUPA_EXTRA_TAIL]), Boolean.parseBoolean(response[ULTRA_MEGA_SUPA_EXTRA_TAIL]));
						break;	
				
					case REFRESH_ALL:
						owner.refreshMap(buildMapFrom(response[MAIN_DATA]), buildCharactersAroundFrom(response[MISC_DATA]));
						owner.refreshPlayerStatus(response[TAIL].replaceAll(";", "\n"));
						owner.refreshStuff(response[EXTRA_TAIL].split(";"), response[SUPA_EXTRA_TAIL].replaceAll(";", "\n"));
						owner.refreshInventory(response[MEGA_SUPA_EXTRA_TAIL].split(";"));
						System.out.println(response[ULTRA_MEGA_SUPA_EXTRA_TAIL]);
						owner.refreshUpgradePanel(buildAbilitiesFrom(response[ULTRA_MEGA_SUPA_EXTRA_TAIL]), Boolean.parseBoolean(response[BEYOND_THE_TAIL]));
						owner.getPlayer().setId(Integer.parseInt(response[OVER_THE_BEYOND_OF_THE_TAIL]));
						FileHandler.replaceCharacter(owner.getPlayer());
						System.out.println("ID : " + owner.getPlayer().getId());
						break;
					case QUIT:
						owner.dispose();
						new SelectionMenu("Menu");
						owner.getUpdate().interrupt();
						break;
						
				}
				
			} catch(IOException e){
				e.printStackTrace();
			}
			
			
		}
	}
	
	public String[][] buildCharactersAroundFrom(String charactersAroundToString){
		String[] row = charactersAroundToString.split(";");
		String[][] charactersAround = new String[MAX_SURROUNDING_ENNEMIES][SURROUNDING_INFORMATION_QUANTITY];
		for(int i=0; i<MAX_SURROUNDING_ENNEMIES; i++){
			charactersAround[i] = row[i].split("\\.");
		}
		
		return charactersAround;
	}
	
	public String[][] buildAbilitiesFrom(String abilitiesList){
		String[] row = abilitiesList.split(";");
		String[][] abilities = new String[NUMBER_OF_ABILITIES][UPGRADE_INFORMATION_QUANTITY];
		for(int i=0; i<this.NUMBER_OF_ABILITIES; i++){
			abilities[i] = row[i].split("\\.");
		}
		
		return abilities;
	}
	
	public String[] buildStuffFrom(String stuffToString){
		return stuffToString.split(";");
	}
	
	public String[] buildInventoryFrom(String inventoryToString){
		return inventoryToString.split(";");
	}
	
	public String[][] buildMapFrom(String mapToString){
		String[] row = mapToString.split(";");
		int mapHeight = row.length;
		int mapWidth = row[0].split(",").length;
		String[][] map = new String[mapWidth][mapHeight];
		
		for(int i=0; i<mapWidth; i++){
			for(int j=0; j<mapHeight; j++){
				map[j] = row[j].split(",");
			}
		}
		
		return map;
		
	}
}
