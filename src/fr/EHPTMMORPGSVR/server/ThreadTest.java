package fr.EHPTMMORPGSVR.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadTest implements Runnable{
	BufferedReader fromServer;
	public ThreadTest(BufferedReader fromServer){
		this.fromServer = fromServer;
	}
	
	public void run(){
		try {
			System.out.println(fromServer.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
