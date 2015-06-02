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

import fr.EHPTMMORPGSVR.business.Map;

public class ServerInteraction {
	
	public static void send(PrintWriter clientRequest,ObjectOutputStream serverUpdate, Object toSend, int mnemo){
		try{
			clientRequest.println(mnemo);
			serverUpdate.writeObject(toSend);
			
		} catch(IOException e){ }
	}
	
	public static void send(Socket client, int mnemo){
		try{
			PrintWriter clientRequest = new PrintWriter(
					new BufferedWriter(
						new OutputStreamWriter(
							client.getOutputStream())), true);
			BufferedReader serverResponse = new BufferedReader(
								new InputStreamReader(
									client.getInputStream()));
			
			clientRequest.println(mnemo);
			
		} catch(IOException e){ }
	}
	
	public static void buildMap(Map toBuild, String map){
		/*String[] lines = map.split("\\n");
		lines[1].substring(beginIndex, endIndex)
		for(int i=0; i<)*/
	}
}
