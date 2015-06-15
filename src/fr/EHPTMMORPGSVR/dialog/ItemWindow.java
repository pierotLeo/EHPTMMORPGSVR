package fr.EHPTMMORPGSVR.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.EHPTMMORPGSVR.business.GameEngine;
import fr.EHPTMMORPGSVR.business.Item;
import fr.EHPTMMORPGSVR.business.PlayableCharacter;

public class ItemWindow extends JDialog implements GameInterfaceConstants{
	private String item;
	private GameWindow owner;
	private int index;
	private int itemContainer;
	private int itemSubContainer;
	
	class SouthButtonListener implements ActionListener{
		int action;
		JDialog itemWindow;
		
		public SouthButtonListener(int action, JDialog itemWindow){
			this.action = action;
			this.itemWindow = itemWindow;
		}
		
		public void actionPerformed(ActionEvent e){
			String response = "";
			switch(action){
				case USE:
					switch(itemContainer){
						case WindowUpdate.INVENTORY:
							response = WindowUpdate.GET_INVENTORY + "#" + WindowUpdate.USE_ITEM_AT + "#" + index;
							break;
						case WindowUpdate.STUFF:
							response = WindowUpdate.GET_STUFF + "#" + WindowUpdate.USE_ITEM_AT + "#" + itemSubContainer + "#" + index;
							break;
					}
					break;
			/*	case DELETE:
					if(!player.getInventory().remove(item)){
						player.getStuff().remove(item);
						//owner.getHistoryModel().addElement("Vous venez de supprimer " + item.getName() + " de votre inventaire.");
					}
					owner.update();
					itemWindow.dispose();
					break;*/
			}
			owner.getToServer().println(response);
			itemWindow.dispose();
		}
	}
	
	public ItemWindow(Frame owner, String title, String item, int index, int itemContainer, int itemSubContainer){
		super(owner, title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		this.item = item;
		this.index = index;
		this.itemContainer = itemContainer;
		this.itemSubContainer = itemSubContainer;
		this.owner = (GameWindow)owner;
		initiate();
		setModal(true);
		setVisible(true);
	}
	
	public ItemWindow(Frame owner, String title, String item, int index, int itemContainer){
		super(owner, title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		this.item = item;
		this.index = index;
		this.itemContainer = itemContainer;
		this.owner = (GameWindow)owner;
		initiate();
		setModal(true);
		setVisible(true);
	}
	
	public JPanel getCenterPanel(){
		JPanel centerPanel = new JPanel(new GridLayout(1,2,5,5));
		JPanel centerWestPanel = new JPanel(new BorderLayout());
		JTextArea statsField = new JTextArea(item);
		statsField.append("\n sous conteneur :" + itemSubContainer);
		statsField.setEditable(false);
		JPanel centerEastPanel = new JPanel(new GridLayout(2, 1));
		JLabel itemDesign = new JLabel(new ImageIcon("POULET.png"));
		JTextArea itemDescription = new JTextArea("Texte descriptif de l'objet");
		
		centerEastPanel.add(itemDesign);
		centerEastPanel.add(itemDescription);

		centerWestPanel.add(statsField);
		
		centerPanel.add(centerWestPanel);
		centerPanel.add(centerEastPanel);
		return centerPanel;
	}
	
	public JPanel getEastPanel(){
		JPanel eastPanel = new JPanel(new GridLayout(1, 2));
		JLabel itemDesign = new JLabel(new ImageIcon("POULET.png"));
		JTextArea itemDescription = new JTextArea("jsp");
		
		eastPanel.add(itemDesign);
		eastPanel.add(itemDescription);
		return eastPanel;
	}
	
	public JPanel getSouthPanel(){
		JPanel southPanel = new JPanel();
		JButton use = new JButton("Utiliser");
		use.addActionListener(new SouthButtonListener(USE, this));
		JButton delete = new JButton("Supprimer");
		delete.addActionListener(new SouthButtonListener(DELETE, this));
		
		southPanel.add(use);
		southPanel.add(delete);
		return southPanel;
	}               
	
	public void initiate(){
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getSouthPanel(), BorderLayout.SOUTH);
	}
}
