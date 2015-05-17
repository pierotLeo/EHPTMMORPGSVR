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

public class ItemWindow extends JDialog implements GameInterfaceConstants{
	private Item item;
	private GameEngine game;
	private GameWindow owner;
	
	class SouthButtonListener implements ActionListener{
		int action;
		JDialog itemWindow;
		
		public SouthButtonListener(int action, JDialog itemWindow){
			this.action = action;
			this.itemWindow = itemWindow;
		}
		
		public void actionPerformed(ActionEvent e){
			switch(action){
				case USE:
					game.getPlayer().use(item);
					owner.update();
					itemWindow.dispose();
					break;
				case DELETE:
					if(!game.getPlayer().getInventory().remove(item))
						game.getPlayer().getStuff().remove(item);
					owner.update();
					itemWindow.dispose();
					break;
			}
		}
	}
	
	public ItemWindow(Frame owner, String title, Item item, GameEngine game){
		super(owner, title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		this.item = item;
		this.game = game;
		this.owner = (GameWindow)owner;
		initiate();
		setModal(true);
		setVisible(true);
	}
	
	public JPanel getCenterPanel(){
		JPanel centerPanel = new JPanel(new GridLayout(1,2,5,5));
		JPanel centerWestPanel = new JPanel(new BorderLayout());
		JTextArea statsField = new JTextArea(item.toString());
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
