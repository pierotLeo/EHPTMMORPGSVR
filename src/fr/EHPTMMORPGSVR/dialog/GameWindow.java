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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.EHPTMMORPGSVR.business.DefaultCharacter;
import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Item;
import fr.EHPTMMORPGSVR.business.Obstacle;
import fr.EHPTMMORPGSVR.business.Weapon;
import fr.EHPTMMORPGSVR.business.OffensiveGear;
import fr.EHPTMMORPGSVR.business.*;

public class GameWindow extends JFrame implements MenuConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameEngine game;
	private JTabbedPane tabbedMenu;
	private JTextArea playerStatus;
	private JTextArea history;
	private JPanel mapArea;
	private JPanel map;
	private JPanel playerStats;
	
	class ItemButtonListener implements ActionListener{
		Item item;
		JFrame contentPane;
		
		public ItemButtonListener (Item item, JFrame contentPane){
			this.item = item;
			this.contentPane = contentPane;
		}
		
		public void actionPerformed(ActionEvent e){
			ItemWindow itemWindow = new ItemWindow(contentPane, item.getName(), item, game);
		}
	}

	class AttackButtonListener implements ActionListener{
		DefaultCharacter target;
		
		public AttackButtonListener (DefaultCharacter target){
			this.target = target;
		}
		
		public void actionPerformed(ActionEvent e){
			game.getPlayer().attack(target);
			update();
		}
	}
	
	class MapKeyListener implements KeyListener{

		public void keyPressed(KeyEvent e){
			int action = e.getKeyCode();
			int move = 0;
			
			switch(action){
				case KeyEvent.VK_DOWN:
					move = game.getMap().move(game.getPlayer(), game.DOWN);
					//history.append(game.getPlayer().getName() + " se déplace vers le sud (2PA).\n");
					break;
				case KeyEvent.VK_UP:
					move = game.getMap().move(game.getPlayer(), game.UP);
					//history.append(game.getPlayer().getName() + " se déplace vers le nord (2PA).\n");
					break;
				case KeyEvent.VK_LEFT:
					move = game.getMap().move(game.getPlayer(), game.LEFT);
					//history.append(game.getPlayer().getName() + " se déplace vers l'ouest (2PA).\n");
					break;
				case KeyEvent.VK_RIGHT:
					move = game.getMap().move(game.getPlayer(), game.RIGHT);
					//history.append( game.getPlayer().getName() + " se déplace vers l'est (2PA).\n");
					break;		
			}
			
			//game.getMap().activateAI();
			update();
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
			game.getPlayer().upgrade(characteristic, 1);
			update();
		}
	}
	
	class ChangeWeaponActionListener implements ActionListener{
		int hand;
		
		public ChangeWeaponActionListener(int hand){
			this.hand = hand;
		}
		
		public void actionPerformed(ActionEvent e){
			game.getPlayer().getStuff().setAttackWeapon(hand);
			update();
		}
	}
	
	public GameWindow(String title, GameEngine game){
		super(title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		this.game = game;
		initiate();
		mapArea.requestFocusInWindow();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void update(){
		tabbedMenu.setComponentAt(0, drawMapArea());
		tabbedMenu.setComponentAt(1, drawInventory());
		tabbedMenu.setComponentAt(3, drawStuff());
		tabbedMenu.setComponentAt(4, drawPlayerStats());
		
		if(tabbedMenu.getSelectedComponent().equals(tabbedMenu.getComponentAt(0))){
			mapArea.requestFocusInWindow();
		}
		
		add(getNorthPanel(), BorderLayout.NORTH);
		
	}
	
	private JPanel drawInventory(){
		JPanel inventory = new JPanel (new GridLayout(4,5,5,5));
		
		JButton slot;
		for(int i=0; i<game.INVENTORY_CAPACITY; i++){
			try{
				slot = new JButton(game.getPlayer().getInventory().get(i).getName());
				slot.addActionListener(new ItemButtonListener(game.getPlayer().getInventory().get(i), this));
			}
			catch(NullPointerException e){
				slot = new JButton();
				slot.setEnabled(false);	
			}
			catch(IndexOutOfBoundsException e){
				slot = new JButton();
				slot.setEnabled(false);
			}
				inventory.add(slot);
		}
		return inventory;
	}
	
	private JPanel drawStuff(){
		JPanel stuff = new JPanel(new GridLayout(1,3,5,5));
		
		JPanel upperArmor = new JPanel(new GridLayout(3,1,5,5));
		JPanel lowerArmor = new JPanel(new GridLayout(3,1,5,5));
		JPanel center = new JPanel(new GridLayout(2,1,5,5));
		JScrollPane playerDescription= new JScrollPane(new JTextArea(game.getPlayer().toString()));
		JPanel weapons = new JPanel(new GridLayout(1,2,5,5));
		
		for(int i=0; i<3; i++){
			switch(i){
				case HEAD_OR_LEGS:
					JButton head = new JButton("Tête");
					JButton legs = new JButton("jambes");
				
					if(game.getPlayer().getStuff().getArmors(game.HEAD) != null)
						head.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getArmors(game.HEAD), this));
					else
						head.setEnabled(false);
					
					if(game.getPlayer().getStuff().getArmors(game.LEGS) != null)
						legs.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getArmors(game.LEGS), this));
					else
						legs.setEnabled(false);
					
					upperArmor.add(head);
					lowerArmor.add(legs);
					break;
				case TORSO_OR_FEET:
					JButton torso = new JButton("Torse");
					JButton feet = new JButton("Pieds");
					
					if(game.getPlayer().getStuff().getArmors(game.TORSO) != null)
						torso.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getArmors(game.TORSO), this));
					else
						torso.setEnabled(false);
					
					if(game.getPlayer().getStuff().getArmors(game.FEET) != null)
						feet.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getArmors(game.FEET), this));
					else
						feet.setEnabled(false);
					
					upperArmor.add(torso);
					lowerArmor.add(feet);
					break;
				case HANDS:
					JButton hands = new JButton("Mains");
					JButton empty = new JButton ();
					
					if(game.getPlayer().getStuff().getArmors(game.HANDS) != null)
						hands.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getArmors(game.HANDS), this));
					else
						hands.setEnabled(false);
					
					empty.setEnabled(false);
					
					upperArmor.add(hands);
					lowerArmor.add(empty);
					break;
			}
		}
		center.add(playerDescription);
		for(int i=0; i<2; i++){
			switch(i){
				 case RIGHT_HAND:
					 JButton rightHand = new JButton("Main droite");
					 if(game.getPlayer().getStuff().getMainHand() != null)
						 rightHand.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getMainHand(), this));
					 else
						 rightHand.setEnabled(false);
					 
					 weapons.add(rightHand);
					 break;
				
				case LEFT_HAND:
				 JButton leftHand = new JButton("Main gauche");

				 if(game.getPlayer().getStuff().getOffHand() != null)
					 leftHand.addActionListener(new ItemButtonListener(game.getPlayer().getStuff().getOffHand(), this));
				 else
					 leftHand.setEnabled(false);
				 
				 weapons.add(leftHand);
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
		JPanel attackPanel = new JPanel(new GridLayout(8,1,5,5));
		attackPanel.setFocusable(false);
		JTextField targetName = null;
		JButton attackButton = null;
		JPanel attackField = null;
		
		for(int i=0; i<4 ; i++){
			try{
				attackField = new JPanel(new GridLayout(1,2,15,15));
				targetName = new JTextField(game.getMap().charactersAround(game.getPlayer()).get(i).getName() + "   Santé : " + game.getMap().charactersAround(game.getPlayer()).get(i).getInjuryLevel());
				targetName.setEditable(false);
				attackButton = new JButton("Attaquer");
				attackButton.addActionListener(new AttackButtonListener(game.getMap().charactersAround(game.getPlayer()).get(i)));
			}
			catch(NullPointerException e){
				attackField = new JPanel(new GridLayout(1,2,15,15));
				targetName = new JTextField();
				targetName.setEditable(false);
				attackButton = new JButton();
				attackButton.setEnabled(false);
			}
			catch(IndexOutOfBoundsException e){
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
			}
		}
		
		JPanel southAttackPanel = new JPanel (new GridLayout(1,2, 5, 5));
		//JTextField idclol = new JTextField("Main sélectionnée : ");
		TitledBorder mainHand = new TitledBorder("Main principale");
		//idclol.setEditable(false);
		JTextField hand = new JTextField();
		hand.setEditable(false);
		JButton changeWeapon = new JButton("Changer de main");
		switch(game.getPlayer().getStuff().getAttackWeapon()){
			case RIGHT_HAND:
				hand.setText("\n   Main droite  ");
				changeWeapon.addActionListener(new ChangeWeaponActionListener(game.LEFT_HAND));
				break;
			case LEFT_HAND:
				hand.setText("\n   Main gauche  ");
				changeWeapon.addActionListener(new ChangeWeaponActionListener(game.RIGHT_HAND));
				break;
		}
		//southAttackPanel.add(idclol);
		southAttackPanel.setBorder(mainHand);
		southAttackPanel.add(hand);
		southAttackPanel.add(changeWeapon);
		
		attackPanel.add(southAttackPanel);
		
		return attackPanel;
	}
	
	private JPanel drawMap(){
		JPanel mapPanel = new JPanel(new GridLayout(game.MAP_WIDTH, game.MAP_HEIGHT));
		JButton[][] map = new JButton[game.MAP_WIDTH][game.MAP_HEIGHT];
		Object visibleTile = null;
		String tileValue = "";
		
		for(int i = 0; i<game.MAP_WIDTH; i++){
			for(int j = 0; j<game.MAP_HEIGHT; j++){
				JButton mapTile = new JButton();
				mapTile.setEnabled(false);

				if(game.getMap().isInSight(game.getPlayer(), i, j) && game.getPlayer().isAlive()){
					visibleTile = game.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = "p";//currentTile.getName();
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
				}
				//if(!game.getPlayer().isAlive()){
					visibleTile = game.getMap().getOnGrid(i, j);
					if(visibleTile instanceof DefaultCharacter){
						DefaultCharacter currentTile = (DefaultCharacter) visibleTile;
						tileValue = "p";//currentTile.getName();
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
				//}
					map[i][j] = mapTile;
					mapPanel.add(map[i][j]);
					
			}
		}
		//System.out.println(game.getMap());
		return mapPanel;
		
	}
	
	private JPanel drawMapArea(){
		mapArea = new JPanel(new GridLayout(1,2,5,5));
		/*if(map == null){
			map = new JTextArea(game.getMap().toString());
			map.setEditable(false);
			map.addKeyListener(new MapKeyListener());
		}
		else
			map.setText(game.getMap().toString());
		*/
		JPanel attackPanel = drawAttackPanel();
		map = drawMap();
		mapArea.addKeyListener(new MapKeyListener());
		mapArea.setFocusable(true);
		
		
		mapArea.add(attackPanel);
		mapArea.add(map);	
		
		return mapArea;
	}
	
	private JPanel drawPlayerStats(){
		JPanel playerStats = new JPanel(new GridLayout(1,2));
		
		JPanel abilities = new JPanel(new GridLayout(5,1));
		JPanel characteristics = new JPanel(new GridLayout(5,1));
		
		String name;
		String lvl;
		String value;

		for(int i = 0; i<game.NUMBER_OF_ABILITIES; i++){
			JPanel stat = new JPanel(new GridLayout(1,2));
		
			name = game.getPlayer().getAbility(i).getName();
			lvl = String.valueOf(game.getPlayer().getAbility(i).getXp() + " / " + game.getPlayer().getAbility(i).xpToNextLevel());
			value  = game.getPlayer().getAbility(i).toString();
		
			JTextArea statName = new JTextArea(name + "\n" + lvl);
			statName.setEditable(false);
			JTextArea statValue = new JTextArea(value);
			statValue.setEditable(false);
			
			stat.add(statName);
			stat.add(statValue);
			abilities.add(stat);
		}
		for(int i=0; i<game.NUMBER_OF_CHARACTERISTICS; i++){
			JPanel stat = new JPanel(new GridLayout(1,2));
			try{
				name = game.getPlayer().getCharacteristic(i).getName();
			}
			catch(NullPointerException e){
				name ="";
			}
			JTextField statName = new JTextField(name);
			statName.setEditable(false);
			JButton upgrade = new JButton("+");
			if(!game.getPlayer().canUpgrade())
				upgrade.setEnabled(false);
			upgrade.addActionListener(new UpgradeListener(i));
			
			stat.add(statName);
			stat.add(upgrade);
			characteristics.add(stat);
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
		history = new JTextArea("");
		history.setEditable(false);
		playerStats = drawPlayerStats();
		
		
		tabbedMenu.addTab("Carte", mapArea);
		tabbedMenu.addTab("Inventaire", inventory);
		tabbedMenu.addTab("Historique", history);
		tabbedMenu.addTab("Equipement", stuff);
		tabbedMenu.addTab("Caractéristiques", playerStats);

		//history.addKeyListener();
		
		return tabbedMenu;
	}
	
	private JPanel getEastPanel(){
		JPanel eastPanel = new JPanel(new BorderLayout());
		JTextArea chatView = new JTextArea("");
		chatView.setEditable(false);
		JTextField chatBox = new JTextField("                                                     ");
		
		eastPanel.add(chatView);
		eastPanel.add(chatBox, BorderLayout.SOUTH);
		
		return eastPanel;
	}
	
	private JPanel getNorthPanel(){
		JPanel northPanel = new JPanel();
		playerStatus = new JTextArea("nom : " + game.getPlayer().getName() + " \nSanté : " + game.getPlayer().getInjuryLevel() + " Expérience dépensée: " + (game.getPlayer().getTotalXp()-game.getPlayer().getAvailableXp()) + "\nExpérience disponible : " + game.getPlayer().getAvailableXp() + "\nPoints d'action : " + game.getPlayer().getPa());
		playerStatus.setEditable(false);
		
		
		northPanel.add(playerStatus);

		
		return northPanel;
	}
	
	
	private JPanel getSouthPanel(){
		JPanel southPanel = new JPanel ();
		JButton save = new JButton("Sauvegarder personnage");		
		JButton quit = new JButton("Quitter partie en cours");
		
		southPanel.add(save);
		southPanel.add(quit);
		
		return southPanel;
	}
	
	private void initiate(){
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getSouthPanel(), BorderLayout.SOUTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		//add(getWestPanel(), BorderLayout.WEST);
		add(getEastPanel(), BorderLayout.EAST);

	}
}
