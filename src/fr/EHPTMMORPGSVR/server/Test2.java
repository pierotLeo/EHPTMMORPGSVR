package fr.EHPTMMORPGSVR.server;

import java.net.*;
import java.util.Scanner;
import java.io.*;

import fr.EHPTMMORPGSVR.business.*;

public class Test2 {

	public static void main(String[] args){
		try{
			Socket client = new Socket("localhost", 8080);
			ObjectOutputStream serverUpdate = new ObjectOutputStream(
					client.getOutputStream());
			ObjectInputStream clientUpdate = new ObjectInputStream(
					client.getInputStream());
			BufferedReader fromServer = new BufferedReader(
											new InputStreamReader(
													client.getInputStream()));
			PrintWriter toServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			
			
			toServer.println(ServerConstants.GET_MAP);
			for(int i=0; i<=Map.MAP_WIDTH; i++)
				System.out.println(fromServer.readLine());
			
			//Map map = (Map) clientUpdate.readObject();
			//System.out.println(map);
			
			PlayableCharacter player = new PlayableCharacter("Rolex", 0, 5, 4, 3);
			
			//toServer.println(ServerConstants.SEND_CHARACTER);
			//serverUpdate.writeObject(player);
			ServerInteraction.send(toServer, serverUpdate, player , ServerConstants.SEND_CHARACTER);
			//Map map2 = (Map)clientUpdate.readObject();
			for(int i=0; i<=Map.MAP_WIDTH; i++)
				System.out.println(fromServer.readLine());
			
			
			Scanner input = new Scanner(System.in);
			int choice = 0;
			String request;
			
			while(true){
				System.out.println("Attaquer : 12.\nSe déplacer : 13.\n\n");
				choice = input.nextInt();
				request = String.valueOf(choice);
				switch(choice){
					case ServerConstants.MOVE:
						System.out.println("Haut : 6.\nBas : 7.\nDroite : 8.\nGauche : 9.\n\n");
						choice = input.nextInt();
						request += "\n" + choice;
						//System.out.println("request" + request);
						toServer.println(request);
						
						break;
					case ServerConstants.ATTACK:
						
				}
				
				System.out.println(fromServer.readLine());
				for(int i=0; i<=Map.MAP_WIDTH; i++)
					System.out.println(fromServer.readLine());
			}
			
			
			//ServerInteraction.send(client,  ServerConstants.QUIT);
			//client.close();*/
			
		}catch(IOException e){e.printStackTrace();}
		//catch(ClassNotFoundException e){e.printStackTrace();}
	}
	
}
