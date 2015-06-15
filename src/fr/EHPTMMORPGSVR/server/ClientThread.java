package fr.EHPTMMORPGSVR.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import fr.EHPTMMORPGSVR.business.DefaultCharacter;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.MapConstants;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.business.Stat;

public class ClientThread implements ServerConstants, Runnable{
	private Thread clientThread;
	private Socket client;
	private ObjectInputStream serverUpdate;
	private ObjectOutputStream clientUpdate;
	private PrintWriter requestResponse;
	private BufferedReader clientRequest;
	private String[] request;
	private EngineServer engineServer;
	private GameEngine engine;
	private PlayableCharacter clientEntity;
	
	public ClientThread(Socket client, EngineServer engineServer, GameEngine engine){
		try{
		this.engineServer = engineServer;
		this.client = client;
		System.out.println("Connecté en tant que Pc.");
		this.engine = engine;
		this.clientEntity = null;
		
			requestResponse = new PrintWriter(
						new BufferedWriter(
							new OutputStreamWriter(
								this.client.getOutputStream())), true);
			clientRequest = new BufferedReader(
							new InputStreamReader(
								this.client.getInputStream()));
			clientUpdate = new ObjectOutputStream(
					this.client.getOutputStream());
			serverUpdate = new ObjectInputStream(
					this.client.getInputStream());
		} catch(IOException e){ }
	
		clientThread = new Thread(this);
		clientThread.start();
		engineServer.addClient(this);
	}
	
	public Thread getClientThread() {
		return clientThread;
	}

	public void setClientThread(Thread clientThread) {
		this.clientThread = clientThread;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public ObjectInputStream getServerUpdate() {
		return serverUpdate;
	}

	public void setServerUpdate(ObjectInputStream serverUpdate) {
		this.serverUpdate = serverUpdate;
	}

	public ObjectOutputStream getClientUpdate() {
		return clientUpdate;
	}

	public void setClientUpdate(ObjectOutputStream clientUpdate) {
		this.clientUpdate = clientUpdate;
	}

	public PrintWriter getRequestResponse() {
		return requestResponse;
	}

	public void setRequestResponse(PrintWriter requestResponse) {
		this.requestResponse = requestResponse;
	}

	public BufferedReader getClientRequest() {
		return clientRequest;
	}

	public void setClientRequest(BufferedReader clientRequest) {
		this.clientRequest = clientRequest;
	}

	public String[] getRequest() {
		return request;
	}

	public void setRequest(String[] request) {
		this.request = request;
	}

	public EngineServer getEngineServer() {
		return engineServer;
	}

	public void setEngineServer(EngineServer engineServer) {
		this.engineServer = engineServer;
	}

	public GameEngine getEngine() {
		return engine;
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	public PlayableCharacter getClientEntity() {
		return clientEntity;
	}

	public void setClientEntity(PlayableCharacter clientEntity) {
		this.clientEntity = clientEntity;
	}

	public String charactersAround(){
		String surrounding = "";
		DefaultCharacter[] charactersAround = clientEntity.charactersAround();
		
		for(int i=0; i<this.MAX_SURROUNDING_ENNEMIES; i++){
			if(charactersAround[i] != null){
				surrounding += charactersAround[i].getName() + "." + charactersAround[i].getInjuryLevel() + ";";								
			}
			else{
				surrounding += NULL + "." + NULL + ";";
			}
		}
		
		return surrounding;
	}
	
	synchronized public void run(){
		String response;
		String broadcast;
		
		while(true){
			try{
				String surrounding = "";
				response = "";
				broadcast = "";
				request = clientRequest.readLine().split("#");
				
				switch(Integer.parseInt(request[HEADER])){
				
					case SEND_CHARACTER:
						boolean alreadyOnMap = false;
						for(int i=0; i<MAP_WIDTH; i++){
							for(int j=0; j<MAP_HEIGHT; j++){
								if(engine.getMap().getOnCharactersGrid(i, j) != null){
									if(engine.getMap().getOnCharactersGrid(i, j).getId() == Integer.parseInt(request[MAIN_DATA])){
										clientEntity = (PlayableCharacter) engine.getMap().getOnCharactersGrid(i, j);
										alreadyOnMap = true;
									}
								}
							}
						}
						
						if(!alreadyOnMap){
							clientEntity = new PlayableCharacter(request[MISC_DATA], 0, Integer.parseInt(request[TAIL]), Integer.parseInt(request[EXTRA_TAIL]), Integer.parseInt(request[SUPA_EXTRA_TAIL]));
							clientEntity.setId(engine.addPlayer(clientEntity));
						}
												
						response = REFRESH_ALL + "#" + engine.getMap().toString() + "#";
						
						surrounding = charactersAround();
						response += surrounding + "#" +  clientEntity.getPlayerStatus() + "#" + clientEntity.getStuff() + "#" + clientEntity.toString() + "#" + clientEntity.getInventory() + "#";
						for(int i=0; i<NUMBER_OF_ABILITIES; i++){
							response += clientEntity.getAbility(i).getName() + "." + clientEntity.getAbility(i).getXp()  + "." + clientEntity.getAbility(i).xpToNextLevel() + "." + clientEntity.getAbility(i) + ";";
						}
						
						response += "#" + clientEntity.canUpgrade() + "#" + clientEntity.getId();
						
						requestResponse.println(response);
						
						broadcast = REFRESH_MAP + "#" + clientEntity.getMap();
						engineServer.broadcast(broadcast);
						//System.out.println("client " + clientEntity.hashCode());
						
						break;
						
					case GET_MAP:
						//requestResponse.println(SEND_MAP);
						//clientUpdate.writeObject(engine.getMap());
						break;
						
					case ATTACK:
						DefaultCharacter target = clientEntity.getOnMap(Integer.parseInt(request[MAIN_DATA]));
						//System.out.println(clientEntity.getOnMap(GameEngine.LEFT));
						int attack = clientEntity.attack(target);
						
						surrounding = charactersAround();
						response = ATTACK + "#";
						
						switch(attack){
							case ABSORB:
								response += "Le coup de " + clientEntity.getName() + " a été absorbé par " + target.getName() + "(1PA).#";
								break;
							case FAIL:
								response += clientEntity.getName() + " manque l'initiative sur " + target.getName() + "(1PA).#";
								break;
							case MISS:
								response += clientEntity.getName() + " rate son attaque sur " + target.getName() + ".#";
								break;
							case MISSING_PA:
								response += clientEntity.getName() + " n'a pas assez de points d'actions pour attaquer " + target.getName() + ".#";
								break;
							case ERROR:
								response += clientEntity.getName() + " ne peut pas attaquer " + target.getName() + ".#";
								break;
							default:
								response += clientEntity.getName() + " touche " + target.getName() + " pour " + String.valueOf(attack) +" dégâts(3PA).#";
								break;
						}
						response += clientEntity.toString() + "#" + clientEntity.getPlayerStatus() + "#" + surrounding + "#" + clientEntity.getMap().toString() + "#";
						for(int i=0; i<NUMBER_OF_ABILITIES; i++){
							response += clientEntity.getAbility(i).getName() + "." + clientEntity.getAbility(i).getXp()  + "." + clientEntity.getAbility(i).xpToNextLevel() + "." + clientEntity.getAbility(i) + ";";
						}
						response += "#" + clientEntity.canUpgrade();
						
						System.out.println(response);
						requestResponse.println(response);
						
						broadcast = REFRESH_MAP + "#" + clientEntity.getMap();
						engineServer.broadcast(broadcast);
						engineServer.broadcast(target);
						
						break;
						
					case MOVE:
						int move = 0;
						response = MOVE + "#";
						switch(Integer.parseInt(request[MAIN_DATA])){
							case UP:
								move =engine.getMap().move(clientEntity, engine.UP);
								if(move == SUCCESS){
									response += clientEntity.getName() + " se déplace vers le nord (2PA).#" + NULL + "#";
								}
								else if(move == LOOT){
									response += clientEntity.getName() + " se déplace vers le nord (2PA).";
								}
								
								break;
							case DOWN:
								move = engine.getMap().move(clientEntity, engine.DOWN);
								if(move == SUCCESS){
									response += clientEntity.getName() + " se déplace vers le sud (2PA).#" + NULL + "#";
								}
								else if(move == LOOT){
									response += clientEntity.getName() + " se déplace vers le sud (2PA).";
								}
								break;
							case RIGHT:
								move = engine.getMap().move(clientEntity, engine.RIGHT);
								if(move == SUCCESS){
									response += clientEntity.getName() + " se déplace vers l'est (2PA).#" + NULL + "#";
								}
								else if(move == LOOT){
									response += clientEntity.getName() + " se déplace vers l'est (2PA).";
								}
								break;
							case LEFT:
								move = engine.getMap().move(clientEntity, engine.LEFT);
								if(move == SUCCESS){
									response += clientEntity.getName() + " se déplace vers l'ouest (2PA).#" + NULL + "#";
								}
								else if(move == LOOT){
									response += clientEntity.getName() + " se déplace vers l'ouest (2PA). ";
								}
								break;
								
						}
						
						switch(move){
							case LOOT:
								response += clientEntity.getName() + " ramasse " + clientEntity.getInventory().get(clientEntity.getInventory().size() - 1).getName() + ".#" + clientEntity.getInventory() + "#" + engine.getMap().toString();
								break;
							case ERROR_MOVE:
								response += clientEntity.getName() + " ne peut se déplacer dans cette direction.#" + NULL + "#" + NULL;
								break;
							case IMMOBILIZED:
								response += clientEntity.getName() + " ne peut se déplacer pour le moment.#" + NULL + "#" + NULL;
								break;
							default:
								response += engine.getMap().toString();
								break;
						}
						response += "#";
						surrounding = charactersAround();
						response += surrounding + "#" + clientEntity.getPlayerStatus();
						requestResponse.println(response);
						
						broadcast = REFRESH_ALL_MAP + "#" + clientEntity.getMap();
						engineServer.broadcast(broadcast);
						
						break;
						
					case GET_INVENTORY:
						response = GET_INVENTORY + "#";
						switch(Integer.parseInt(request[MAIN_DATA])){
							case GET_ITEM_AT:
								response += GET_ITEM_AT + "#" + clientEntity.getInventory().get(Integer.parseInt(request[MISC_DATA])).toString() + "#" + request[MISC_DATA];
								break;
							case USE_ITEM_AT:
								String itemName = clientEntity.getInventory().get(Integer.parseInt(request[MISC_DATA])).getName();
								int use = clientEntity.getInventory().get(Integer.parseInt(request[MISC_DATA])).use(clientEntity);
								response += USE_ITEM_AT + "#" + itemName + "#" + clientEntity.getInventory() + "#" + clientEntity.getStuff() + "#" + clientEntity.toString();
								switch(use){
									case MISSING_PA:
										response += "#" + use;
										break;
									case SUCCESS:
										response += "#" + NULL;
										break;
								}
								response += "#" + clientEntity.getPlayerStatus();
								break;
								
						}
						requestResponse.println(response);
						break;
						
					case GET_STUFF:
						response = GET_STUFF + "#";
						switch(Integer.parseInt(request[MAIN_DATA])){
							case GET_ITEM_AT:
								response += GET_ITEM_AT + "#";
								switch(Integer.parseInt(request[MISC_DATA])){
									case ARMOR:
										response += clientEntity.getStuff().getArmors(Integer.parseInt(request[TAIL])).toString() + "#" + request[TAIL] + "#" + ARMOR;
										break;
									case OFF_HAND:
										response += clientEntity.getStuff().getOffHand().toString() + "#" + NULL + "#" + OFF_HAND;
										break;
									case MAIN_HAND:
										response += clientEntity.getStuff().getMainHand().toString() + "#" + NULL + "#" + MAIN_HAND;
										break;
								}
								break;
							case USE_ITEM_AT:
								String itemName = "";
								int use = 0;
								response += USE_ITEM_AT + "#";
								switch(Integer.parseInt(request[MISC_DATA])){
									case ARMOR:
										itemName = clientEntity.getStuff().getArmors(Integer.parseInt(request[TAIL])).getName();
										use = clientEntity.getStuff().getArmors(Integer.parseInt(request[TAIL])).use(clientEntity);
										break;
									case OFF_HAND:
										itemName = clientEntity.getStuff().getOffHand().getName();
										use = clientEntity.getStuff().getOffHand().use(clientEntity);
										break;
									case MAIN_HAND:
										itemName = clientEntity.getStuff().getMainHand().getName();
										use = clientEntity.getStuff().getMainHand().use(clientEntity);
										break;
								}
								response += itemName + "#" + clientEntity.getInventory() + "#" + clientEntity.getStuff() + "#" + clientEntity.toString();
								switch(use){
									case MISSING_PA:
										response += "#" + NULL;
										break;
									case SUCCESS:
										response += "#" + use;
										break;
								}	
								response += "#" + clientEntity.getPlayerStatus();
							break;	
						}
						System.out.println(response);
						requestResponse.println(response);
						break;
						
					case CHANGE_HAND:
						response = CHANGE_HAND + "#";
						int change = clientEntity.getStuff().changeWeapon();
						switch(change){
							case MISSING_PA:
								response += MISSING_PA + "#" + "Vous n'avez pas assez de points d'action pour changer de posture." + "#" + NULL + "#" + NULL;
								break;
							case RIGHT_HAND:
								response += SUCCESS + "#" + "Vous passez en posture offensive.(3PA)" + "#" + RIGHT_HAND + "#" + clientEntity.getPlayerStatus();
								break;
							case LEFT_HAND:
								response += SUCCESS + "#" + "Vous passez en posture défensive.(3PA)" + "#" + LEFT_HAND + "#" + clientEntity.getPlayerStatus();
								break;
						}
						requestResponse.println(response);
						break;
						
					case UPGRADE:
						response = UPGRADE + "#";
						int stat = Integer.parseInt(request[MAIN_DATA]);
						int xp = Integer.parseInt(request[MISC_DATA]);
						clientEntity.upgrade(stat, xp);
						
						for(int i=0; i<NUMBER_OF_ABILITIES; i++){
							response += clientEntity.getAbility(i).getName() + "." + clientEntity.getAbility(i).getXp()  + "." + clientEntity.getAbility(i).xpToNextLevel() + "." + clientEntity.getAbility(i) + ";";
						}
						
						response += "#" + clientEntity.canUpgrade() + "#" + clientEntity.toString() + "#" + clientEntity.getPlayerStatus();
						System.out.println(response);
						requestResponse.println(response);
						break;
					case QUIT:
						response = String.valueOf(QUIT);
						requestResponse.println(response);
						this.clientThread.interrupt();
						break;
				}
								
			} catch(IOException e){
				
			//} catch(ClassNotFoundException e){
				
			}catch(NumberFormatException e){
				
			}
			//catch(InterruptedException e){}
		}
	}
	
}
