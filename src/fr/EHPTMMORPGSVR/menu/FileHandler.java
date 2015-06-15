package fr.EHPTMMORPGSVR.menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import fr.EHPTMMORPGSVR.dialog.PlayableCharacterView;
import fr.EHPTMMORPGSVR.dialog.WindowUpdate;

public class FileHandler {
	
	public static final int NAME = 0;
	public static final int STRENGTH = 1;
	public static final int RESISTANCE = 2;
	public static final int AGILITY = 3;
	public static final int ID = 4;
	
	/*public static void saveCharacter(PlayableCharacterView character){
		ObjectOutputStream toFile = null;
		try{
			toFile = new ObjectOutputStream(new FileOutputStream("characters.txt", true));
			toFile.writeObject(character);
			toFile.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			try{
				if(toFile != null){
					toFile.flush();
					toFile.close();
				}
			} catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		
	}*/
	
	public static void replaceCharacter(PlayableCharacterView character){
		try{
			BufferedReader fromFile = new BufferedReader(new InputStreamReader(new FileInputStream("characters.txt")));
			PrintWriter toFile = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("characters.txt", true))));
			
			ArrayList<String> fileContent = new ArrayList<String>();
			String[] refinedCharac = new String[5];
			String rawCharac = "";
			for(int i=0; i<5; i++){		
				rawCharac = fromFile.readLine();
				if(rawCharac != null){
					refinedCharac = rawCharac.split("#");
					if(!refinedCharac[NAME].matches(character.getName())){
						fileContent.add(rawCharac);
					}
					else{
						fileContent.add(character.toString());
						fromFile.readLine();
					}
				}
			}
			
			PrintWriter delete = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("characters.txt"))));
			
			for(int i=0; i<fileContent.size(); i++){
				toFile.println(fileContent.get(i));
			}
			
			toFile.close();
			delete.close();
			fromFile.close();
			
		} catch(IOException e){
			
		}
		
	}
	
	
	public static void deleteCharacter(int characLocation){
		
	try{
			BufferedReader fromFile = new BufferedReader(new InputStreamReader(new FileInputStream("characters.txt")));
			PrintWriter toFile = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("characters.txt", true))));	
			
			ArrayList<String> fileContent = new ArrayList<String>();
			for(int i=0; i<5; i++){				
				fileContent.add(fromFile.readLine());	
			}
			
			PrintWriter delete = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("characters.txt"))));
			
			for(int i=0; i<fileContent.size(); i++){
				if(fileContent.get(i)!=null && i!=characLocation)
					toFile.println(fileContent.get(i));
			}
			toFile.close();
			delete.close();
			fromFile.close();
			
		}catch(NullPointerException e){
			
		}
		catch(IOException e){
			
		}
	}
	
	public static void saveCharacter(PlayableCharacterView character){
		PrintWriter toFile = null;
		
		try{
			toFile = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("characters.txt", true))));	
			toFile.println(character.toString());
			toFile.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			if(toFile != null){
				toFile.flush();
				toFile.close();
			}
		}
	}
	
	public static PlayableCharacterView loadPlayer(int characLocation){
		PlayableCharacterView toLoad = null;
		try{
			BufferedReader fromFile = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("characters.txt")));
			String[] toLoadFrom = new String[5];
			String rawData = "";
			try{
				int i=0;
				do{
					rawData = fromFile.readLine();
					//System.out.println(rawData);
					i++;
				}while(i<=characLocation);
				
				toLoadFrom = rawData.split("#");
				
				
				toLoad = new PlayableCharacterView(toLoadFrom[NAME],Integer.parseInt(toLoadFrom[STRENGTH]),Integer.parseInt(toLoadFrom[RESISTANCE]), Integer.parseInt(toLoadFrom[AGILITY]),Integer.parseInt(toLoadFrom[ID]));
			}catch(NullPointerException e){
				
			}
			
			fromFile.close();
		} catch(IOException ioe){
			return null;
		}
		return toLoad;
	}
	
	/*public static PlayableCharacterView loadPlayer(){
		PlayableCharacterView toLoad = null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("characters.txt"));
			toLoad = (PlayableCharacterView) in.readObject();
			in.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		return toLoad;
	}*/
	
	/*public static PlayableCharacterView loadPlayer(int characNumber){
		PlayableCharacterView toLoad = null;
		ObjectInputStream in = null;
		try{
			in = new ObjectInputStream(new FileInputStream("characters.txt"));
			for(int position = 0; position < characNumber; position++)
				toLoad = (PlayableCharacterView) in.readObject();
			
			in.close();
		} catch(IOException ioe){
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;		
		} catch(ClassNotFoundException nfe){
			nfe.printStackTrace();
		}
		
		return toLoad;
	}*/
		
		
		
	
}
