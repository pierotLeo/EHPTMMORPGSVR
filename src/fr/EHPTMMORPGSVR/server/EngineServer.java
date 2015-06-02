package fr.EHPTMMORPGSVR.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.io.*;

import fr.EHPTMMORPGSVR.business.DefaultCharacter;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Item;

public class EngineServer {
	public static final int PC_DEFAULT_PORT = 8080;
	public static final int NPC_DEFAULT_PORT = 4040;
	private Vector<PrintWriter> tabClients;
	private int nbClients;
	
	public static void main(String[] args){
		EngineServer engineServer = new EngineServer();
		try{
			Integer pcPort;
			Integer npcPort;
			
			pcPort = new Integer(PC_DEFAULT_PORT);
			npcPort = new Integer(NPC_DEFAULT_PORT);
			
			GameEngine engine = new GameEngine();
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
	
	synchronized public int addClient(PrintWriter toClient){
		nbClients++;
		tabClients.addElement(toClient);
		return tabClients.size()-1;
	}
}
