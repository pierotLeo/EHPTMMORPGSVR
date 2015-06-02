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
	private Integer request;
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
	}
	
	synchronized public void run(){
		while(true){
			try{
				String response = "";
				int request = Integer.parseInt(clientRequest.readLine());
				System.out.println("Action_request : " + request);
				wait(2000);
				
				switch(request){
					case SEND_CHARACTER:
						/*while(clientEntity == null){
							if(serverUpdate.readObject() instanceof PlayableCharacter){
								clientEntity = (PlayableCharacter) serverUpdate.readObject();
								
							}
						}*/
						clientEntity = (PlayableCharacter) serverUpdate.readObject();
						engine.addPlayer(clientEntity);
						clientEntity.setMap(engine.getMap());
						//clientUpdate.writeObject(clientEntity.getMap());
						requestResponse.println(engine.getMap().toString());
						break;
					case GET_MAP:
						requestResponse.println(engine.getMap().toString());
						break;
					case ATTACK:
						DefaultCharacter target = (DefaultCharacter)serverUpdate.readObject();
						int attack = clientEntity.attack(target);
						boolean hit = false;
						
						switch(attack){
							case ABSORB:
								response = "0\nLe coup de " + clientEntity.getName() + " a été absorbé par " + clientEntity.getTarget().getName() + "(1PA).";
								break;
							case FAIL:
								response = "1\n" + clientEntity.getName() + " manque l'initiative sur " + clientEntity.getTarget().getName() + "(1PA).";
								break;
							case MISS:
								response = "2\n" + clientEntity.getName() + " rate son attaque sur " + clientEntity.getTarget().getName() + ".";
								break;
							case MISSING_PA:
								response = "3\n" + clientEntity.getName() + " n'a pas assez de points d'actions pour attaquer " + clientEntity.getTarget().getName() + ".";
								break;
							case ERROR:
								response = "5\n" + clientEntity.getName() + " ne peut pas attaquer " + clientEntity.getTarget().getName() + ".";
								break;
							default:
								response = "1\n" + clientEntity.getName() + " touche " + clientEntity.getTarget().getName() + " pour " + String.valueOf(attack) +" dégâts(3PA).";
								hit = true;
								break;
						}
						
						if(hit || attack == ABSORB || attack == FAIL)
							clientUpdate.writeObject(clientEntity.getTarget());
						requestResponse.println(response);
						requestResponse.println(engine.getMap().toString());
						break;
						
					case MOVE:
						int move = 0;
						request = Integer.parseInt(clientRequest.readLine());
						System.out.println("Move_request : " + request);
						wait(2000);
						switch(request){
							case UP:
								response = String.valueOf(engine.getMap().move(clientEntity, engine.UP));
								if(Integer.parseInt(response) == SUCCESS || Integer.parseInt(response) == LOOT){
									response += "\n" + clientEntity.getName() + " se déplace vers le nord (2PA)";
									//clientUpdate.writeObject(engine.getMap());
									
								}
								break;
							case DOWN:
								response = String.valueOf(engine.getMap().move(clientEntity, engine.DOWN));
								if(Integer.parseInt(response) == SUCCESS || Integer.parseInt(response) == LOOT){
									response += "\n" + clientEntity.getName() + " se déplace vers le sud (2PA)";
									//clientUpdate.writeObject(engine.getMap());
								}
								break;
							case RIGHT:
								response = String.valueOf(engine.getMap().move(clientEntity, engine.RIGHT));
								if(Integer.parseInt(response) == SUCCESS || Integer.parseInt(response) == LOOT){
									response += "\n" + clientEntity.getName() + " se déplace vers l'est (2PA)";
									//clientUpdate.writeObject(engine.getMap());
								}
								break;
							case LEFT:
								response = String.valueOf(engine.getMap().move(clientEntity, engine.LEFT));
								if(Integer.parseInt(response) == SUCCESS || Integer.parseInt(response) == LOOT){
									response += "\n" + clientEntity.getName() + " se déplace vers l'ouest (2PA)";
									//clientUpdate.writeObject(engine.getMap());
								}
								break;
							default:
								response = "prout";
								break;
						}
						
						
						String[] responses = response.split("\\n");
						
						
						switch(Integer.parseInt(responses[0])){
							case LOOT:
								responses[1] += "  " + clientEntity.getName() + " ramasse " + clientEntity.getInventory().get(clientEntity.getInventory().size() - 1) + ".";
								break;
							case ERROR_MOVE:
								responses[1] += "  " + clientEntity.getName() + " ne peut se déplacer dans cette direction.";
								break;
							case IMMOBILIZED:
								responses[1] += "  " + clientEntity.getName() + " ne peut se déplacer pour le moment.";
								break;
						}
						
						requestResponse.println(responses[1]);
						requestResponse.println(engine.getMap().toString());
						break;
					case USE:
						break;
					case QUIT:
						//System.out.println("Le client est fermé");
						//clientThread.interrupt();
						break;
				}
				
				
				
			} catch(IOException e){
				
			} catch(ClassNotFoundException e){
				
			}catch(NumberFormatException e){
				
			}
			catch(InterruptedException e){}
		}
	}
	
}
