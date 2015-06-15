package fr.EHPTMMORPGSVR.business;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileHandler {
	
	public static void saveMap(Map map){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maps.rx"));
			out.writeObject(map);
			out.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static void saveCharacter(DefaultCharacter character){
		try{
			ObjectOutputStream toFile = new ObjectOutputStream(new FileOutputStream("characters.rx"));
			toFile.writeObject(character);
			toFile.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static Map loadMap(){
		Map toLoad = null;
		try{
			ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream("maps.rx"));
			toLoad = (Map) fromFile.readObject();
			fromFile.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}
	
	public static Map loadMap(int mapNumber){
		Map toLoad = null;
		try{
			ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream("maps.rx"));
			for(int position = 0; position < mapNumber; position++)
				toLoad = (Map) fromFile.readObject();
				
			fromFile.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}
	
	public static PlayableCharacter loadPlayer(){
		PlayableCharacter toLoad = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("characters.rx"));
			toLoad = (PlayableCharacter) in.readObject();
			in.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}
	
	public static PlayableCharacter loadPlayer(int characNumber){
		PlayableCharacter toLoad = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("characters.rx"));
			for(int position = 0; position < characNumber; position++)
				toLoad = (PlayableCharacter) in.readObject();
			
			in.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}
	
	/*
	public static NonPlayableCharacter loadNPC(){
		NonPlayableCharacter toLoad = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("characters.rx"));
			toLoad = (NonPlayableCharacter) in.readObject();
			in.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}*/
}
