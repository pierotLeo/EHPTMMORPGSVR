package fr.EHPTMMORPGSVR.business;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;




public class Test implements Constants{
	public static double xpPerFightVsBalancedClone(){
		//nouveau set d'armes neutre...
		Weapon[] weapons = new Weapon[2];
		weapons[0] = new Weapon();
		weapons[1] = new Weapon();
		
		//nouveau set d'armure neutre...
		Armor[] armorPieces = new Armor[5];
		for(int i=0; i<5; i++){
			armorPieces[i] = new Armor();
		}
		
		//->nouveau stuff neutre
		Stuff stuff = new Stuff(weapons, armorPieces);
		
		double xpPerFight = 0;
		
		//crï¿½ation de deux personnages identiques.
		PlayableCharacter Roger = new PlayableCharacter("Roger", 1, 6, 6, 6, stuff);
		DefaultCharacter Abel= new DefaultCharacter("Ver solitaire", 1,6,6,6,6,6);
		System.out.println(Roger);
		
		//boucle permettant d'ammasser l'xp accumulï¿½ uniquement en frappant l'adversaire sur 10 000 combats.
		for(int i=0; i<10000; i++){
			Abel.setCurrentHp(18);
			Roger.setAvailableXp(0);
			do{
				Roger.attack(Abel, LEFT_HAND);
			}while(Abel.isAlive());
			xpPerFight += Roger.getAvailableXp();
		}
		//retour de la moyenne.
		return xpPerFight/=10000;
	}

	public static int xpCalculation(){
		//nouveau set d'armes neutre...
		Weapon[] weapons = new Weapon[2];
		weapons[0] = new Weapon();
		weapons[1] = new Weapon();
		
		//nouveau set d'armure neutre...
		Armor[] armorPieces = new Armor[5];
		for(int i=0; i<5; i++){
			armorPieces[i] = new Armor();
		}
		
		//->nouveau stuff neutre
		Stuff stuff = new Stuff(weapons, armorPieces);
		
		PlayableCharacter Roger = new PlayableCharacter("Roger", 1, 6, 6, 6, stuff);
		NonPlayableCharacter ver1= new NonPlayableCharacter("Ver solitaire", 1,18,18,18,18,18);
		NonPlayableCharacter ver2= new NonPlayableCharacter("Raton camionneur mutant", 100,6,6,6,6,6);
		
		String details = "";
		int attack;
		System.out.println(Roger);
		System.out.println(ver1);
		
		while(ver1.isAlive() && Roger.isAlive()){
			System.out.println("Roger attaque !");
			attack = Roger.attack(ver1, LEFT_HAND);
			Roger.setPa(5);
			details = "Details sur l'attaque :\n" + "     -Resultats de l'attaque : " ;
			switch (attack){
				case ABSORB:
					details += "l'attaque a ete absorbe par ver(0 degats). Vous remportez 1 point d'xp.\n";
					break;
				case FAIL:
					details += "l'attaque a echoue(0 degats).\n";
					break;
				case MISS:
					details += "Vous ratez la cible (0 degats).\n";
					break;
				default: 
					details += "l'attaque est un succes !" + attack + " degats infliges a ver. Sante de Ver Solitaire: " + ver1.getCurrentHp() + "/18.\n";
					break;
				}
			details += "xp disponible : " + Roger.getAvailableXp() + "\n";
			details += "xp totale : " + Roger.getTotalXp() + "\n";
			details += "xp from hit : " + Roger.getXpFromCurrentFight() + "\n";
			details += "Roger se repose !\n\n";
			System.out.println(details);
			
			System.out.println("ver attaque !");
			attack = ver1.attack(Roger);
			details = "Details sur l'attaque :\n" + "     -Resultats de l'attaque : " ;
			switch (attack){
				case ABSORB:
					details += "l'attaque a ete absorbe par Roger(0 degats).\n";
					break;
				case FAIL:
					details += "l'attaque a echoue(0 degats).\n";
					break;
				case MISS:
					details += "Vous ratez la cible (0 degats).\n";
					break;
				default: 
					details += "l'attaque est un succes !" + attack + " degats infliges a Roger. Sante de Roger: " + Roger.getCurrentHp() + "/18.\n";
					break;
				}
			details += "Ver se repose !\n\n";
			System.out.println(details);
		}
		
		Roger.setCurrentHp(18);
		System.out.println("LLLLLLLLLLEEEEEEEEEEEELLLLLLL\n");
		
		while(ver2.isAlive() && Roger.isAlive()){
			System.out.println("Roger attaque !");
			attack = Roger.attack(ver2, LEFT_HAND);
			Roger.setPa(5);
			details = "Details sur l'attaque :\n" + "     -Resultats de l'attaque : " ;
			switch (attack){
				case ABSORB:
					details += "l'attaque a ete absorbe par ver(0 degats). Vous remportez 1 point d'xp.\n";
					break;
				case FAIL:
					details += "l'attaque a echoue(0 degats).\n";
					break;
				case MISS:
					details += "Vous ratez la cible (0 degats).\n";
					break;
				default: 
					details += "l'attaque est un succes !" + attack + " degats infliges a ver. Sante de Ver Solitaire: " + ver2.getCurrentHp() + "/18.\n";
					break;
				}
			details += "xp disponible : " + Roger.getAvailableXp() + "\n";
			details += "xp totale : " + Roger.getTotalXp() + "\n";
			details += "xp from hit : " + Roger.getXpFromCurrentFight() + "\n";
			details += "Roger se repose !\n\n";
			System.out.println(details);
			
			System.out.println("ver attaque !");
			attack = ver2.attack(Roger);
			details = "Details sur l'attaque :\n" + "     -Resultats de l'attaque : " ;
			switch (attack){
				case ABSORB:
					details += "l'attaque a ete absorbe par Roger(0 degats).\n";
					break;
				case FAIL:
					details += "l'attaque a echoue(0 degats).\n";
					break;
				case MISS:
					details += "Vous ratez la cible (0 degats).\n";
					break;
				default: 
					details += "l'attaque est un succes !" + attack + " degats infliges a Roger. Sante de Roger: " + Roger.getCurrentHp() + "/18.\n";
					break;
				}
			details += "Ver se repose !\n\n";
			System.out.println(details);
		}
		
		return Roger.getAvailableXp();
	}
	
	public static String xp_simulation(int nb_simulations){
		String s = "";
		//nouveau set d'armes neutre...
		Weapon[] weapons = new Weapon[2];
		weapons[0] = new Weapon();
		weapons[1] = new Weapon();
		
		//nouveau set d'armure neutre...
		Armor[] armorPieces = new Armor[5];
		for(int i=0; i<5; i++){
			armorPieces[i] = new Armor();
		}
		
		//->nouveau stuff neutre
		Stuff stuff = new Stuff(weapons, armorPieces);
		
		PlayableCharacter Roger = new PlayableCharacter("Roger", 78, 6, 6, 6, stuff);
		DefaultCharacter ver= new DefaultCharacter("Ver solitaire", 78,6,6,6,6,6);
		
		int old_xp;
		for(int i=0; i<nb_simulations; i++){
			Roger.setCurrentHp(18);
			ver.setCurrentHp(18);
			old_xp = Roger.getTotalXp();
			do{
				//if(Roger.hit(ver, LEFT_HAND)){
					Roger.setTotalXp(Roger.getTotalXp()+1);
					//Roger.dealtDamage(ver, LEFT_HAND);
				
			}while(ver.isAlive());
			Roger.getCharacteristic(STRENGTH).upgrade(Roger.getTotalXp() - old_xp);
			//s += (int)((double)(Roger.getTotalXp() - old_xp)*((double)ver.getTotalXp()/(double)Roger.getTotalXp()))+"\n";
			Roger.setTotalXp(Roger.getTotalXp() + (int)((double)(Roger.getTotalXp() - old_xp)*((double)ver.getTotalXp()/(double)Roger.getTotalXp())));
			s += Roger.getTotalXp()+"\n";
		}
		
		return s;
	}
	
	public static void export(int nb_niveaux){
		FileDialog nav = new FileDialog(new Frame(), "CHOAZY OU TU SAUVEGARD XDAY", FileDialog.SAVE);
		nav.setVisible(true);
		
		if(nav.getFile()!=null){
			String fileName = nav.getDirectory()+nav.getFile().replaceAll("\\..*", "");
	    	File f_save = new File(fileName+".leoRPG"); 
			try {
				f_save.createNewFile();
				FileWriter w_save = new FileWriter(f_save);
				try {
					w_save.write(""+xp_simulation(nb_niveaux));
				} finally {
					w_save.close();
				}
			} catch (Exception e) { }
		}
	}

	public static void testUseWeapon(){
		//nouveau set d'armes neutre...
			Stat hugu = new Stat();
			hugu.setDiceNb(2);
			hugu.setAddNb(4);
				Weapon[] weapons = new Weapon[2];
				weapons[0] = new Weapon();
				weapons[1] = new Weapon();
				Weapon chatonDeFeu = new Weapon("Magic Wand", hugu, hugu, RIGHT_HAND);
				
				//nouveau set d'armure neutre...
				Armor[] armorPieces = new Armor[5];
				for(int i=0; i<5; i++){
					armorPieces[i] = new Armor();
				}
				
				//->nouveau stuff neutre
				Stuff stuff = new Stuff(weapons, armorPieces);
				
				PlayableCharacter Roger = new PlayableCharacter("Roger", 1, 6, 6, 6, stuff);
				System.out.println(Roger);
				Roger.getInventory().addToInventory(chatonDeFeu);
				Roger.getInventory().getContent()[0].use(Roger, Roger);
				System.out.println(Roger);

				System.out.println(Roger.getInventory());
	}
	
	public static void testBattleClass(){
		DefaultCharacter gobelin = new DefaultCharacter("Xhi'ss", 0, 6, 6, 0, 0, 6);
		DefaultCharacter joueur = new DefaultCharacter("Zertracs", 0, 6, 6, 0, 0, 6);
		
		Battle gobelinVsJoueur = new Battle(joueur, gobelin);
		gobelinVsJoueur.initiate();
		for(int i=0; i<3; i++){
			int attack = gobelinVsJoueur.battle();
			switch (attack){
				case MISS:
					System.out.println("raté");
					break;
				case FAIL:
					System.out.println("Echec");
					break;
				case ABSORB:
					System.out.println("Absorbé");
					break;
				case MISSING_PA:
					System.out.println("Plus assez de points d'actions.");
					break;
				default:
					System.out.println(attack);
					break;
			}
			System.out.println(joueur + "\n\n\n" + gobelin);
			System.out.println(gobelinVsJoueur.getAssailantRound());
			System.out.println(gobelinVsJoueur.getAssailant());
		}
		
		
		
		
	}
	
	public static void testMap(){
		Map map = new Map();
		NonPlayableCharacter gobelin = new NonPlayableCharacter("Xhi'ss", 0, 6, 6, 0, 0, 6, map);
		PlayableCharacter joueur = new PlayableCharacter("Zertracs", 0,6,6,6, map);
		NonPlayableCharacter yeti = new NonPlayableCharacter("Jeune yeti noroit", 0, 6, 6, 0, 0, 6, map);
		Weapon idc = new Weapon("IReallyDontCare", 6, 6, RIGHT_HAND);
		
		
		Scanner input = new Scanner(System.in);
		
		map.setOnCharactersGrid(joueur, 5, 8);
		map.setOnCharactersGrid(yeti, 5, 11);
		map.setOnItemGrid(idc, 0, 0);
		System.out.println(map);
		joueur.getInventory().add(idc);
		
		while(true){
			int test = 0;
			int choice1;
			int choice2;
			switch(input.nextInt()){
				case 2:
					test = map.move(joueur, DOWN);
					System.out.println(test);
					break;
				case 4:
					test = map.move(joueur, LEFT);
					System.out.println(test);
					break;
				case 6:
					test = map.move(joueur, RIGHT);
					System.out.println(test);
					break;
				case 8:
					test = map.move(joueur, UP);
					System.out.println(test);
					break;
				case 1:
					System.out.println(joueur.getInventory());
					System.out.println("-Utiliser ? 1\n-Sortir ? 2\n Voir equipement ? 3");
					choice1 = input.nextInt();
					if(choice1 == 1){
						System.out.println("Quel objet souhaiter vous utiliser ?");
						choice2 = input.nextInt();
						if(choice2 >= 0 && choice2 < 20 && joueur.getInventory().get(choice2)!=null){
							joueur.getInventory().use(joueur.getInventory().get(choice2));
						}
					}
					else if(choice1 == 3){
						if(joueur.getStuff().getWeapons(RIGHT_HAND)!=null)
							System.out.println(joueur.getStuff().getWeapons(RIGHT_HAND));
						else
							System.out.println("Vous n'avez rien d'equipe.");
					}
					break;
				
			}
			
			System.out.println(map);
		}
		
	}
	
	public static void main(String[] args) {
		//double xp = xpPerFightVsBalancedClone();
		//xpCalculation();
		//export(2500);
		//testUseWeapon();
		//testBattleClass();
		testMap();
		
	}
}
