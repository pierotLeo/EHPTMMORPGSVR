package fr.EHPTMMORPGSVR.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.io.*;

import fr.EHPTMMORPGSVR.business.Armor;
import fr.EHPTMMORPGSVR.business.DefaultCharacter;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Item;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.business.Shield;
import fr.EHPTMMORPGSVR.business.Weapon;

public class EngineServer implements ServerConstants{
	public static final int PC_DEFAULT_PORT = 8080;
	public static final int NPC_DEFAULT_PORT = 4040;
	//private Vector<PrintWriter> tabClients;
	private Vector<ClientThread> tabClients = new Vector<ClientThread>();
	private int nbClients;
	
	public static void main(String[] args){
		EngineServer engineServer = new EngineServer();
		try{
			Integer pcPort;
			Integer npcPort;
			
			pcPort = new Integer(PC_DEFAULT_PORT);
			npcPort = new Integer(NPC_DEFAULT_PORT);
			
			
			GameEngine engine = new GameEngine();
			
			//lel
			Weapon loklak = new Weapon("LokLak, ténèbres des temps anciens", 7, 7);///, game.LEFT_HAND);
			Weapon pongdoh = new Weapon("Pongk'dohr, le marteleur planétaire", 9, 9);//, game.RIGHT_HAND);
			Shield chitine = new Shield("Ecaille de l'éventreur de mondes", 5,5); 
			Armor skin = new Armor("Artère du pilier-Monde", 5,5, fr.EHPTMMORPGSVR.business.StuffConstants.TORSO);
			
			engine.getMap().randomSetOnItemGrid(loklak);
			engine.getMap().randomSetOnItemGrid(pongdoh);
			engine.getMap().randomSetOnItemGrid(chitine);
			engine.getMap().randomSetOnItemGrid(skin);
			//lel
			
			
			ServerSocket pcServerSocket = new ServerSocket(pcPort);
			ServerSocket npcServerSocket = new ServerSocket(npcPort);
			while(true){
				new ClientThread(pcServerSocket.accept(), engineServer, engine);
			}
		} catch(IOException e){ }
	}
	
	
	synchronized public void delClient(int clientID){
		nbClients--;
		if(tabClients.elementAt(clientID) != null){
			tabClients.removeElementAt(clientID);
		}
	}
	
	synchronized public int addClient(ClientThread toClient){
		nbClients++;
		tabClients.addElement(toClient);
		return tabClients.size()-1;
	}
	
	synchronized public void broadcast(DefaultCharacter target){
		String information = "";
		
		if(target instanceof PlayableCharacter){
			PlayableCharacter humanTarget = (PlayableCharacter) target;
			for(int i=0; i<tabClients.size(); i++){
				if(tabClients.get(i).getClientEntity().equals(target)){
					information = REFRESH_PERSONNAL_STATUS + "#" + humanTarget.getPlayerStatus();
					//System.out.println("Client " + tabClients.get(i).getClientEntity().hashCode() + " : " + information);
					tabClients.get(i).getRequestResponse().println(information);
				}
			}			
		}
	}
	
	synchronized public void broadcast(String information){
		String[] splitInformation = information.split("#");
		String informationSave = information.toString();
		
		switch(Integer.parseInt(splitInformation[HEADER])){
			case REFRESH_MAP:
				for(int i=0; i<tabClients.size(); i++){
					information = informationSave;
					information += "#" + tabClients.get(i).charactersAround() + "# " ;
					tabClients.get(i).getRequestResponse().println(information);
				}
				break;
		}
		
	}
	
	synchronized public void updateClients(){
		
		try{
			for(int i=0; i<tabClients.size(); i++){
				tabClients.get(i).getClientUpdate().writeObject(tabClients.get(i).getClientEntity());
			}
		} catch(IOException e){}
	}
}
