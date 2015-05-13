package fr.EHPTMMORPGSVR.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Item;
import fr.EHPTMMORPGSVR.business.Weapon;

public class GameWindow extends JFrame implements MenuConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu_alpha menu;
	private GameEngine game;
	private JTabbedPane tabbedMenu;
	private JTextField playerStatus;
	private JTextField targetStatus;
	private JTextArea history;
	private JTextArea map;
	
	public GameWindow(String title, GameEngine game){
		super(title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		menu = new Menu_alpha();
		this.game = game;
		initiate();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void initiate(){
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getSouthPanel(), BorderLayout.SOUTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		//add(getWestPanel(), BorderLayout.WEST);
		add(getEastPanel(), BorderLayout.EAST);
	}
	
	public JPanel getNorthPanel(){
		JPanel northPanel = new JPanel();
		playerStatus = new JTextField("nom : " + game.getPlayer1().getName() + " Santé : " + game.getPlayer1().getInjuryLevel() + " Experience : " + game.getPlayer1().getTotalXp());
		playerStatus.setEditable(false);
		targetStatus = new JTextField();
		targetStatus.setVisible(false);
		
		northPanel.add(playerStatus);
		northPanel.add(targetStatus);
		
		return northPanel;
	}
	
	public JPanel getSouthPanel(){
		JPanel southPanel = new JPanel ();
		JButton save = new JButton("Sauvegarder personnage");		
		JButton quit = new JButton("Quitter partie en cours");
		
		southPanel.add(save);
		southPanel.add(quit);
		
		return southPanel;
	}
	
	private JPanel drawInventory(){
		JPanel inventory = new JPanel (new GridLayout(4,5,5,5));
		JButton slot;
		for(int i=0; i<game.INVENTORY_CAPACITY; i++){
			try{
				slot = new JButton(game.getPlayer1().getInventory().get(i).getName());
				slot.addActionListener(new ItemButtonListener(game.getPlayer1().getInventory().get(i), this));
			}
			catch(IndexOutOfBoundsException|NullPointerException e){
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
		JTextArea playerDescription = new JTextArea(game.getPlayer1().toString());
		JPanel weapons = new JPanel(new GridLayout(1,2,5,5));
		
		for(int i=0; i<3; i++){
			switch(i){
				case HEAD_OR_LEGS:
					JButton head = new JButton("Tête");
					JButton legs = new JButton("jambes");
				
					if(game.getPlayer1().getStuff().getArmors(game.HEAD) != null)
						head.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getArmors(game.HEAD), this));
					else
						head.setEnabled(false);
					
					if(game.getPlayer1().getStuff().getArmors(game.LEGS) != null)
						legs.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getArmors(game.LEGS), this));
					else
						legs.setEnabled(false);
					
					upperArmor.add(head);
					lowerArmor.add(legs);
					break;
				case TORSO_OR_FEET:
					JButton torso = new JButton("Torse");
					JButton feet = new JButton("Pieds");
					
					if(game.getPlayer1().getStuff().getArmors(game.TORSO) != null)
						torso.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getArmors(game.TORSO), this));
					else
						torso.setEnabled(false);
					
					if(game.getPlayer1().getStuff().getArmors(game.FEET) != null)
						feet.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getArmors(game.FEET), this));
					else
						feet.setEnabled(false);
					
					upperArmor.add(torso);
					lowerArmor.add(feet);
					break;
				case HANDS:
					JButton hands = new JButton("Mains");
					JButton empty = new JButton ();
					
					if(game.getPlayer1().getStuff().getArmors(game.HANDS) != null)
						hands.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getArmors(game.HANDS), this));
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
					 if(game.getPlayer1().getStuff().getWeapons(game.RIGHT_HAND) != null)
						 rightHand.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getWeapons(game.RIGHT_HAND), this));
					 else
						 rightHand.setEnabled(false);
					 
					 weapons.add(rightHand);
					 break;
				
				case LEFT_HAND:
				 JButton leftHand = new JButton("Main gauche");

				 if(game.getPlayer1().getStuff().getWeapons(game.LEFT_HAND) != null)
					 leftHand.addActionListener(new ItemButtonListener(game.getPlayer1().getStuff().getWeapons(game.LEFT_HAND), this));
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
	
	public void update(){
		tabbedMenu.setComponentAt(1, drawInventory());
		tabbedMenu.setComponentAt(2, new JTextArea(game.getPlayer1().toString()));
		tabbedMenu.setComponentAt(3, drawStuff());
		
	}
	
	public JTabbedPane getCenterPanel(){
		tabbedMenu = new JTabbedPane();
		JPanel inventory = drawInventory();
		JPanel stuff = drawStuff();
		map = new JTextArea(game.getMap().toString());
		map.setEditable(false);
		map.addKeyListener(new MapKeyListener());
		history = new JTextArea("");
		history.setEditable(false);
		JTextArea playerDescription = new JTextArea(game.getPlayer1().toString());

		
		tabbedMenu.addTab("Carte", map);
		tabbedMenu.addTab("Inventaire", inventory);
		tabbedMenu.add("Personnage", playerDescription);
		tabbedMenu.add("Equipement", stuff);

		//history.addKeyListener();
		
		return tabbedMenu;
	}
	
	public JPanel getEastPanel(){
		JPanel eastPanel = new JPanel(new BorderLayout());
		JTextArea chatView = new JTextArea("");
		chatView.setEditable(false);
		JTextField chatBox = new JTextField("                                                     ");
		
		eastPanel.add(chatView);
		eastPanel.add(chatBox, BorderLayout.SOUTH);
		
		return eastPanel;
	}
	
	
	class MapKeyListener implements KeyListener{

		public void keyPressed(KeyEvent e){
			int action = e.getKeyCode();
			int move = 0;
			
			switch(action){
				case KeyEvent.VK_DOWN:
					move = game.getMap().move(game.getPlayer1(), game.DOWN);
					break;
				case KeyEvent.VK_UP:
					move = game.getMap().move(game.getPlayer1(), game.UP);
					break;
				case KeyEvent.VK_LEFT:
					move = game.getMap().move(game.getPlayer1(), game.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					move = game.getMap().move(game.getPlayer1(), game.RIGHT);
					break;		
			}
			if(move == game.LOOT){
				tabbedMenu.setComponentAt(1, drawInventory());
			}
			map.setText(game.getMap().toString());
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		
		public void keyTyped(KeyEvent e){

		}
	}
	
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
}
