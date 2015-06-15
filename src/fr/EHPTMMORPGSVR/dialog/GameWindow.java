package fr.EHPTMMORPGSVR.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.EHPTMMORPGSVR.business.*;

public class GameWindow extends JFrame implements GameInterfaceConstants{
	/**
	 * 
	 */
	public static final int STUFF = 0;
	public static final int INVENTORY = 1;
	
	public static final int NULL = -50;
	public static final int ARMOR = 1;
	public static final int OFF_HAND = 2;
	public static final int MAIN_HAND = 3;
	
	public static final int HEAD = 0;
	public static final int TORSO = 1;
	public static final int HANDS = 2;
	public static final int LEGS = 3;
	public static final int FEET = 4;
	public static final int LEFT_HAND_STUFF = 5;
	public static final int RIGHT_HAND_STUFF = 6;
	
	public static final int RIGHT = 3;
	public static final int DOWN = 2;
	public static final int LEFT = 1;
	public static final int UP = 0;
	
	private static final long serialVersionUID = 1L;
	private PlayableCharacterView player;
	private JTabbedPane tabbedMenu;
	private JTextArea playerStatus;
	private JPanel mapArea;
	private JPanel mapPanel;
	private JPanel playerStats;
	private DefaultListModel historyModel; 
	private JList historyList;
	private JButton[][] map;
	private JButton[] inventory;
	private JButton[] upperArmor;;
	private JButton[] lowerArmor;
	private JButton[] gloves;
	private JTextArea playerArea;
	private JTextField hand;
	private JTextArea[][] playerAbilities;
	private JButton[] playerCharacteristicsButtons;
	private JTextField[] targetName;
	private JButton[] attackButton;
	private Thread update;
	
	
	private PrintWriter toServer;
	private ObjectOutputStream serverUpdate;

	public Thread getUpdate(){
		return update;
	}
	
	public void preInitiate(){
		playerArea = new JTextArea();
		map = new JButton[WindowUpdate.MAP_WIDTH][WindowUpdate.MAP_HEIGHT];
		inventory = new JButton[WindowUpdate.INVENTORY_CAPACITY];
		upperArmor = new JButton[3];
		lowerArmor = new JButton[3];
		gloves = new JButton[2];
		playerAbilities = new JTextArea[WindowUpdate.NUMBER_OF_ABILITIES][2];
		playerCharacteristicsButtons = new JButton[WindowUpdate.NUMBER_OF_CHARACTERISTICS];
		playerStatus = new JTextArea();
	}
	
	public JTabbedPane getTabbedMenu() {
		return tabbedMenu;
	}

	public void setTabbedMenu(JTabbedPane tabbedMenu) {
		this.tabbedMenu = tabbedMenu;
	}

	public JTextArea getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(JTextArea playerStatus) {
		this.playerStatus = playerStatus;
	}

	public JPanel getMapArea() {
		return mapArea;
	}

	public void setMapArea(JPanel mapArea) {
		this.mapArea = mapArea;
	}

	public JPanel getMap() {
		return mapPanel;
	}

	public void setMap(JPanel map) {
		this.mapPanel = map;
	}

	public JPanel getPlayerStats() {
		return playerStats;
	}

	public void setPlayerStats(JPanel playerStats) {
		this.playerStats = playerStats;
	}

	public DefaultListModel getHistoryModel() {
		return historyModel;
	}

	public void setHistoryModel(DefaultListModel historyModel) {
		this.historyModel = historyModel;
	}

	public JList getHistoryList() {
		return historyList;
	}

	public void setHistoryList(JList historyList) {
		this.historyList = historyList;
	}

	public PrintWriter getToServer() {
		return toServer;
	}

	public void setToServer(PrintWriter toServer) {
		this.toServer = toServer;
	}

	public ObjectOutputStream getServerUpdate() {
		return serverUpdate;
	}

	public void setServerUpdate(ObjectOutputStream serverUpdate) {
		this.serverUpdate = serverUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	class SouthButtonListener implements ActionListener{
		private int choice;
		
		public SouthButtonListener(int choice){
			this.choice = choice;
		}
		
		public void actionPerformed(ActionEvent e){
			switch(choice){
			case SAVE:
				
				
				break;
			case QUIT:
				String request = String.valueOf(WindowUpdate.QUIT);
				toServer.println(request);
				break;
			}
			//update();
		}
	}
	
	class ItemButtonListener implements ActionListener{
		
		private int container;
		private int subContainer;
		private int index;
		private JFrame contentPane;
		
		public ItemButtonListener (JFrame contentPane, int container, int subContainer, int index){
			this.contentPane = contentPane;
			this.index = index;
			this.container = container;
			this.subContainer = subContainer;
		}
		
		public ItemButtonListener (JFrame contentPane, int container, int index){
			this.contentPane = contentPane;
			this.index = index;
			this.container = container;
		}
		
		public void actionPerformed(ActionEvent e){
			String request = "";
			System.out.println(subContainer);
			switch(container){
				case INVENTORY:
					request = WindowUpdate.GET_INVENTORY + "#" + WindowUpdate.GET_ITEM_AT + "#" + index ;
					break;
				case STUFF:
					request = WindowUpdate.GET_STUFF + "#" + WindowUpdate.GET_ITEM_AT +"#" + subContainer + "#" + index;
					break;	
			} 
			toServer.println(request);
		}
	}

	class AttackButtonListener implements ActionListener{
		private int position;
		
		public AttackButtonListener (int position){
			this.position = position;
		}
		
		public void actionPerformed(ActionEvent e){	
			String request = WindowUpdate.ATTACK + "#";
			switch(position){
				case RIGHT:
					request += fr.EHPTMMORPGSVR.business.GameEngine.DOWN;
					break;
				case LEFT:
					request += fr.EHPTMMORPGSVR.business.GameEngine.UP;
					break;
				case UP:
					request += fr.EHPTMMORPGSVR.business.GameEngine.LEFT;
					break;
				case DOWN:
					request += fr.EHPTMMORPGSVR.business.GameEngine.RIGHT;
					break;
			}//desho?
			
			toServer.println(request);
		}
	}
	
	class MapKeyListener implements KeyListener{

		public void keyPressed(KeyEvent e){
			int action = e.getKeyCode();
			String request = fr.EHPTMMORPGSVR.server.ServerConstants.MOVE + "#";
			
			switch(action){
				case KeyEvent.VK_DOWN:
					request += fr.EHPTMMORPGSVR.server.ServerConstants.DOWN;
					break;
				case KeyEvent.VK_UP:
					request += fr.EHPTMMORPGSVR.server.ServerConstants.UP;
					break;
				case KeyEvent.VK_LEFT:
					request += fr.EHPTMMORPGSVR.server.ServerConstants.LEFT;
					break;
				case KeyEvent.VK_RIGHT:
					request += fr.EHPTMMORPGSVR.server.ServerConstants.RIGHT;
					break;		
			}
			toServer.println(request);
			
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		
		public void keyTyped(KeyEvent e){

		}
	}
	
	class UpgradeListener implements ActionListener{
		int characteristic;
		
		public UpgradeListener(int characteristic){
			this.characteristic = characteristic;
		}
		
		public void actionPerformed(ActionEvent e){
			String request = WindowUpdate.UPGRADE + "#" + characteristic + "#" + WindowUpdate.DEFAULT_INCREMENTATION;
			toServer.println(request);
		}
	}
	
	class ChangeWeaponActionListener implements ActionListener{
		
		
		public ChangeWeaponActionListener(){
		}
		
		public void actionPerformed(ActionEvent e){
			String request = WindowUpdate.CHANGE_HAND + "#";
			toServer.println(request);
		}
	}
	
	public GameWindow(String title, PlayableCharacterView player){
		super(title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		this.player = player;
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try{
			Socket client = new Socket("192.168.1.72", 8080);
			serverUpdate = new ObjectOutputStream(
					client.getOutputStream());
			
			toServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);			
			update = new Thread(new WindowUpdate(this, client));
			update.start();
			preInitiate();
			initiate();
			
			String request = WindowUpdate.SEND_CHARACTER + "#" + player.getId() + "#" + player.getName() + "#" + player.getCharacteristic(WindowUpdate.STRENGTH) + "#" + player.getCharacteristic(WindowUpdate.AGILITY) + "#" + player.getCharacteristic(WindowUpdate.RESISTANCE);
			toServer.println(request);
			//serverUpdate.writeObject(player);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		mapArea.requestFocusInWindow();
		
	}
	
	/*synchronized public void update(){
		tabbedMenu.setComponentAt(0, drawMapArea());
		tabbedMenu.setComponentAt(1, drawInventory());
		tabbedMenu.setComponentAt(2, drawStuff());
		tabbedMenu.setComponentAt(3, drawPlayerStats());
		if(tabbedMenu.getSelectedComponent().equals(tabbedMenu.getComponentAt(0))){
			mapArea.requestFocusInWindow();
		}
		
		historyList.ensureIndexIsVisible(historyModel.size()-1);
		
		playerStatus.setText("nom : " + player.getName() + " \nSanté : " + player.getInjuryLevel() + " Expérience dépensée: " + (player.getTotalXp()-player.getAvailableXp()) + "\nExpérience disponible : " + player.getAvailableXp() + "\nPoints d'action : " + player.getPa());
		
		
		
	}*/
	
	private JPanel drawInventory(){
		JPanel inventoryPanel = new JPanel (new GridLayout(4,5,5,5));
		//inventory = new JButton[WindowUpdate.INVENTORY_CAPACITY];
		JButton slot;
		
		for(int i=0; i<WindowUpdate.INVENTORY_CAPACITY; i++){
			try{
				slot = new JButton("");
				slot.setEnabled(false);
			}
			catch(NullPointerException e){
				slot = new JButton();
				slot.setEnabled(false);	
			}
			catch(IndexOutOfBoundsException e){
				slot = new JButton();
				slot.setEnabled(false);
			}
			slot.addActionListener(new ItemButtonListener(this, INVENTORY, i));
			inventory[i] = slot;
			inventoryPanel.add(inventory[i]);
		}
		return inventoryPanel;
	}

	
	private JPanel drawStuff(){
		JPanel stuff = new JPanel(new GridLayout(1,3,5,5));
		
		JPanel upperArmor = new JPanel(new GridLayout(3,1,5,5));
		JPanel lowerArmor = new JPanel(new GridLayout(3,1,5,5));
		JPanel center = new JPanel(new GridLayout(2,1,5,5));
		//playerArea = new JTextArea("");
		JScrollPane playerDescription= new JScrollPane(playerArea);
		JPanel weapons = new JPanel(new GridLayout(1,2,5,5));
		
		//this.upperArmor = new JButton[3];
		//this.lowerArmor = new JButton[3];
		//gloves = new JButton[2];
		
		for(int i=0; i<3; i++){
			switch(i){
				case HEAD_OR_LEGS:
					JButton head = new JButton("Heaume");
					JButton legs = new JButton("Pantalon");
				
					head.addActionListener(new ItemButtonListener(this, STUFF, ARMOR, WindowUpdate.HEAD));
					head.setEnabled(false);
				
					legs.addActionListener(new ItemButtonListener(this, STUFF, ARMOR, WindowUpdate.LEGS));
					legs.setEnabled(false);
					
					this.upperArmor[i] = head;
					this.lowerArmor[i] = legs;
					
					upperArmor.add(head);
					lowerArmor.add(legs);
					break;
				case TORSO_OR_FEET:
					JButton torso = new JButton("Plastron");
					JButton feet = new JButton("Bottes");
					
					torso.addActionListener(new ItemButtonListener(this, STUFF, ARMOR, WindowUpdate.TORSO));
					torso.setEnabled(false);
				
					feet.addActionListener(new ItemButtonListener(this, STUFF, ARMOR, WindowUpdate.FEET));
					feet.setEnabled(false);
					
					this.upperArmor[i] = torso;
					this.lowerArmor[i] = feet;
					
					upperArmor.add(torso);
					lowerArmor.add(feet);
					break;
				case HANDS:
					JButton hands = new JButton("Gants");
					JButton empty = new JButton ();
					
					hands.addActionListener(new ItemButtonListener(this, STUFF, ARMOR, WindowUpdate.HANDS));
					hands.setEnabled(false);
					
					empty.setEnabled(false);
					
					this.upperArmor[i] = hands;
					this.lowerArmor[i] = empty;
					
					upperArmor.add(hands);
					lowerArmor.add(empty);
					break;
			}
		}
		center.add(playerDescription);
		for(int i=0; i<2; i++){
			switch(i){
				 case LEFT_HAND:
					 JButton leftHand = new JButton("Main gauche");
					 leftHand.addActionListener(new ItemButtonListener(this, STUFF, OFF_HAND, NULL));
					 leftHand.setEnabled(false);
					 
					 this.gloves[i] = leftHand;
					 
					 weapons.add(leftHand);
					 break;
				
				case RIGHT_HAND:
				 JButton rightHand = new JButton("Main droite");

				 rightHand.addActionListener(new ItemButtonListener(this, STUFF, MAIN_HAND, NULL));
				 rightHand.setEnabled(false);
				 
				 this.gloves[i] = rightHand;				 
				 weapons.add(rightHand);
				 break;
			}
		}
		center.add(weapons);
		stuff.add(upperArmor);
		stuff.add(center);
		stuff.add(lowerArmor);
		
		return stuff;
		
	}
	
	private JPanel drawAttackPanel(){
		JPanel attackPanelContainer = new JPanel(new GridLayout(2,1,5,5));
		JPanel attackPanel = new JPanel(new GridLayout(5,1,5,5));
		attackPanel.setFocusable(false);
		targetName = new JTextField[WindowUpdate.MAX_SURROUNDING_ENNEMIES];
		attackButton = new JButton[WindowUpdate.MAX_SURROUNDING_ENNEMIES];
		JPanel attackField = null;
		
		
		for(int i=0; i<4 ; i++){
			/*try{
				attackField = new JPanel(new GridLayout(1,2,15,15));
				targetName = new JTextField(game.getMap().charactersAround(game.getPlayer())[i].getName() + "   Santé : " + game.getMap().charactersAround(game.getPlayer())[i].getInjuryLevel());
				targetName.setEditable(false);
				attackButton = new JButton("Attaquer");
				attackButton.addActionListener(new AttackButtonListener(game.getMap().charactersAround(game.getPlayer())[i]));
			}
			catch(NullPointerException e){
				attackField = new JPanel(new GridLayout(1,2,15,15));
				targetName = new JTextField();
				targetName.setEditable(false);
				attackButton = new JButton();
				attackButton.setEnabled(false);
			}
			finally{
				attackField.add(targetName);
				attackField.add(attackButton);
				attackPanel.add(attackField);
			}*/
			targetName[i] = new JTextField();
			targetName[i].setEditable(false);
			attackButton[i] = new JButton("Attaquer");
			attackButton[i].addActionListener(new AttackButtonListener(i));
			attackField = new JPanel(new GridLayout(1,2,15,15));
			
			/*if(player.charactersAround()[i] != null){
				targetName[i].setText(player.charactersAround()[i].getName() + "   Santé : " + player.charactersAround()[i].getInjuryLevel());
				targetName[i].setVisible(true);
				attackButton[i].setEnabled(true);
				attackButton[i].setVisible(true);
			}
			else{*/
				targetName[i].setText("");
				targetName[i].setVisible(false);
				attackButton[i].setEnabled(false);
				attackButton[i].setVisible(false);	
			//}
			
			attackField.add(targetName[i]);
			attackField.add(attackButton[i]);
			attackPanel.add(attackField);
			
		}
		
		JPanel southAttackPanel = new JPanel (new GridLayout(1,2, 5, 5));
		//JTextField idclol = new JTextField("Main sélectionnée : ");
		TitledBorder mainHand = new TitledBorder("Main principale");
		//idclol.setEditable(false);
		hand = new JTextField();
		hand.setEditable(false);
		JButton changeWeapon = new JButton("Changer de main");
		/*switch(player.getStuff().getAttackWeapon()){
			case RIGHT_HAND:
				hand.setText("\n   Main droite  ");
				break;
			case LEFT_HAND:
				hand.setText("\n   Main gauche  ");
				break;
		}*/
		hand.setText("\n   Main droite  ");
		
		changeWeapon.addActionListener(new ChangeWeaponActionListener());
		//southAttackPanel.add(idclol);
		southAttackPanel.setBorder(mainHand);
		southAttackPanel.add(hand);
		southAttackPanel.add(changeWeapon);
		
		attackPanel.add(southAttackPanel);
		
		if(historyModel == null){
			historyModel = new DefaultListModel();
			historyList = new JList(historyModel);
			
		}
		JTabbedPane supaSouthAttackPanel = new JTabbedPane();
		JScrollPane history = new JScrollPane(historyList);
		supaSouthAttackPanel.add("Hisotrique", history);
		
		attackPanelContainer.add(supaSouthAttackPanel);
		attackPanelContainer.add(attackPanel);
		
		return attackPanelContainer;
	}
	
	public JTextField getHand() {
		return hand;
	}

	public void setHand(JTextField hand) {
		this.hand = hand;
	}
	
	public void refreshPlayerStatus(String playerStatus){
		this.playerStatus.setText(playerStatus);
	}
	
	public void refreshPlayer(String player){
		this.playerArea.setText(player);
	}
	
	public void refreshUpgradePanel(String[][] abilities, boolean canUpgrade){
		
		for(int i=0; i<WindowUpdate.NUMBER_OF_CHARACTERISTICS; i++){
			//System.out.println("n° : " +  i + " = " + playerCharacteristicsButtons[i]);
			playerCharacteristicsButtons[i].setEnabled(canUpgrade);
		}
		
		for(int i=0; i<WindowUpdate.NUMBER_OF_ABILITIES; i++){
			playerAbilities[i][0].setText(abilities[i][0] + "\n" + abilities[i][1] + " / " + abilities[i][2]);
			playerAbilities[i][1].setText(abilities[i][3]);
		}
	
	}

	public void refreshStuff(String[] stuff, String player){
		
		for(int i=0; i<3; i++){
			if(!stuff[i].matches(" ")){
				this.upperArmor[i].setText(stuff[i]);
				this.upperArmor[i].setEnabled(true);
			}
			else{
				switch(i){
					case 0:
						this.upperArmor[i].setText("Heaume");
						break;
					case 1:
						this.upperArmor[i].setText("Plastron");
						break;
					case 2:
						this.upperArmor[i].setText("Gants");
						break;
				}
				
				this.upperArmor[i].setEnabled(false);
			}
		}

		for(int i=0; i<2; i++){
			if(!stuff[i+3].matches(" ")){
				this.lowerArmor[i].setText(stuff[i+3]);
				this.lowerArmor[i].setEnabled(true);
			}
			else{
				switch(i){
					case 0:
						this.lowerArmor[i].setText("Pantalon");
						break;
					case 1:
						this.lowerArmor[i].setText("Bottes");
						break;
				}
				
				this.lowerArmor[i].setEnabled(false);
			}
		}
		
		for(int i=0; i<2; i++){
			if(!stuff[i+5].matches(" ")){
				this.gloves[i].setText(stuff[i+5]);
				this.gloves[i].setEnabled(true);
			}
			else{
				switch(i){
					case 0:
						this.gloves[i].setText("Main gauche");
						break;
					case 1:
						this.gloves[i].setText("Main droite");
						break;
				}
				
				this.gloves[i].setEnabled(false);
			}
		}
		
		this.playerArea.setText(player);
		
	}
	
	public void refreshInventory(String[] inventory){
		for(int i=0; i<WindowUpdate.INVENTORY_CAPACITY; i++){
			if(!inventory[i].matches(" ")){
				this.inventory[i].setText(inventory[i]);
				this.inventory[i].setEnabled(true);
			}
			else{
				this.inventory[i].setText("");
				this.inventory[i].setEnabled(false);
			}
		}
	}
	
	public void refreshMap(String[][] map, String[][] charactersAround){
		for(int i =0; i<WindowUpdate.MAP_WIDTH; i++){
			for(int j=0; j<WindowUpdate.MAP_HEIGHT; j++){
				if(map[i][j].matches("x")){
					this.map[i][j].setBackground(Color.BLACK);
					this.map[i][j].setText("");
				}
				else if(map[i][j].matches("-")){
					this.map[i][j].setBackground(Color.WHITE);
					this.map[i][j].setText("");
				}
				else{
					this.map[i][j].setBackground(Color.WHITE);
					this.map[i][j].setText(map[i][j]);
				}
			}
		}
		
		for(int i=0; i<WindowUpdate.MAX_SURROUNDING_ENNEMIES; i++){
			if(!charactersAround[i][0].matches(WindowUpdate.NULL)){
				targetName[i].setText(charactersAround[i][0] + "   Santé : " + charactersAround[i][1]);
				targetName[i].setVisible(true);
				attackButton[i].setEnabled(true);
				attackButton[i].setVisible(true);
			}
			else{
				targetName[i].setText("");
				targetName[i].setVisible(false);
				attackButton[i].setEnabled(false);
				attackButton[i].setVisible(false);
			}
		}
	}
	
	public JPanel drawMap(){
		JPanel mapPanel = new JPanel(new GridLayout(WindowUpdate.MAP_WIDTH, WindowUpdate.MAP_HEIGHT));
		//map = new JButton[WindowUpdate.MAP_WIDTH][WindowUpdate.MAP_HEIGHT];
		Object visibleTile = null;
		String tileValue = "";
		
		for(int i = 0; i<WindowUpdate.MAP_WIDTH; i++){
			for(int j = 0; j<WindowUpdate.MAP_HEIGHT; j++){
				JButton mapTile = new JButton();
				mapTile.setEnabled(false);

				/*if(player.getMap().isInSight(player, i, j) && player.isAlive()){
					visibleTile = player.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = currentTile.getName();
					}
					if(visibleTile instanceof Item){
						tileValue = "i";
					}
					mapTile.setText(tileValue);
					mapTile.setBackground(Color.WHITE);
					if(visibleTile instanceof Obstacle){
						mapTile.setText("");
						mapTile.setBackground(Color.BLACK);
					}
					if(visibleTile == null){
						mapTile.setText("");
					}
					
				}
				else{
					mapTile.setBackground(Color.GRAY);
				}*/
				//if(!player.isAlive()){
					/*visibleTile = player.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = currentTile.getName();
					}
					if(visibleTile instanceof Item){
						tileValue = "i";
					}*/
					mapTile.setText(tileValue);
					mapTile.setBackground(Color.WHITE);
					if(visibleTile instanceof Obstacle){
						mapTile.setText("");
						mapTile.setBackground(Color.BLACK);
					}
					if(visibleTile == null){
						mapTile.setText("");
					}
				//}
					map[i][j] = mapTile;
					mapPanel.add(map[i][j]);
					
			}
		}
		
		
		return mapPanel;
		
	}
	
	public void saveCharacter(){
		
	}
	
	public PlayableCharacterView getPlayer() {
		return player;
	}

	public void setPlayer(PlayableCharacterView player) {
		this.player = player;
	}

	private JPanel drawMapArea(){
		mapArea = new JPanel(new GridLayout(1,2,5,5));
		
		JPanel attackPanel = drawAttackPanel();
		mapPanel = drawMap();
		mapArea.addKeyListener(new MapKeyListener());
		mapArea.setFocusable(true);
		
		
		mapArea.add(attackPanel);
		mapArea.add(mapPanel);	
		
		return mapArea;
	}
	
	private JPanel drawPlayerStats(){
		JPanel playerStats = new JPanel(new GridLayout(1,2));
		
		JPanel abilities = new JPanel(new GridLayout(5,1));
		JPanel characteristics = new JPanel(new GridLayout(5,1));
		
		//playerAbilities = new JTextArea[WindowUpdate.NUMBER_OF_ABILITIES][2];
		//playerCharacteristicsButtons = new JButton[WindowUpdate.NUMBER_OF_CHARACTERISTICS];
		
		String name = "";
		String lvl = "";
		String value = "";

		for(int i = 0; i<WindowUpdate.NUMBER_OF_ABILITIES; i++){
			JPanel stat = new JPanel(new GridLayout(1,2));
		
			/*
			name = player.getAbility(i).getName();
			lvl = String.valueOf(player.getAbility(i).getXp() + " / " + player.getAbility(i).xpToNextLevel());
			value  = player.getAbility(i).toString();*/
			
		
			JTextArea statName = new JTextArea(name + "\n" + lvl);
			statName.setEditable(false);
			playerAbilities[i][0] = statName;

			JTextArea statValue = new JTextArea(value);
			statValue.setEditable(false);
			playerAbilities[i][1] = statValue;
			
			stat.add(playerAbilities[i][0]);
			stat.add(playerAbilities[i][1]);
			abilities.add(stat);
		}
		for(int i=0; i<WindowUpdate.NUMBER_OF_CHARACTERISTICS; i++){
			JPanel stat = new JPanel(new GridLayout(1,2));
			
			
			
			switch(i){
				case 0:
					name = "Force";
					break;
				case 1:
					name = "Agilité";
					break;
				case 2:
					name = "Résistance";
					break;
			}
			
			JTextField statName = new JTextField(name);
			statName.setEditable(false);		
			
			JButton upgrade = new JButton("+");
			upgrade.addActionListener(new UpgradeListener(i));
			playerCharacteristicsButtons[i] = upgrade;
			
			stat.add(statName);
			stat.add(playerCharacteristicsButtons[i]);
			characteristics.add(stat);
			//System.out.println("Initialisation :\n n° " + i + " = " + playerCharacteristicsButtons[i]);
		}
		
		playerStats.add(abilities);
		playerStats.add(characteristics);
		
		return playerStats;
	}
	
	private JTabbedPane getCenterPanel(){
		tabbedMenu = new JTabbedPane();
		JPanel inventory = drawInventory();
		JPanel stuff = drawStuff();
		JPanel mapArea = drawMapArea();
		
		
		playerStats = drawPlayerStats();
		//Thread Map = new Thread(new MapArea(game, this));
		//Map.start();
		
		tabbedMenu.addTab("Carte", mapArea);
		tabbedMenu.addTab("Inventaire", inventory);
		tabbedMenu.addTab("Equipement", stuff);
		tabbedMenu.addTab("Caractéristiques", playerStats);
		
		return tabbedMenu;
	}
	
	private JPanel getEastPanel(){
		JPanel eastPanel = new JPanel(new BorderLayout());
		JTextArea chatView = new JTextArea("");
		chatView.setEditable(false);
		JTextField chatBox = new JTextField("                                                     ");
		chatBox.setText("");
		
		eastPanel.add(chatView);
		eastPanel.add(chatBox, BorderLayout.SOUTH);
		
		return eastPanel;
	}
	
	private JPanel getNorthPanel(){
		//JPanel northPanel = new NorthPanel(game);
		JPanel northPanel = new JPanel();
		//playerStatus = new JTextArea();//("nom : " + player.getName() + " \nSanté : " + player.getInjuryLevel() + " Expérience dépensée: " + (player.getTotalXp()-player.getAvailableXp()) + "\nExpérience disponible : " + player.getAvailableXp() + "\nPoints d'action : " + player.getPa());
		playerStatus.setEditable(false);
		
		
		northPanel.add(playerStatus);

		
		return northPanel;
	}
	
	
	private JPanel getSouthPanel(){
		JPanel southPanel = new JPanel ();
		JButton quit = new JButton("Se déconnecter");		
		//JButton load = new JButton("Charger un personnage");
		
		quit.addActionListener(new SouthButtonListener(QUIT));
		//load.addActionListener(new SouthButtonListener(LOAD));
		
		southPanel.add(quit);
		//southPanel.add(load);
		
		return southPanel;
	}
	
	synchronized private void initiate(){
		
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getSouthPanel(), BorderLayout.SOUTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		//add(getWestPanel(), BorderLayout.WEST);
		add(getEastPanel(), BorderLayout.EAST);

	}
}
