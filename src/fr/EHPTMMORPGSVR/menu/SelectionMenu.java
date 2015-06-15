package fr.EHPTMMORPGSVR.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.EHPTMMORPGSVR.business.*;

public class SelectionMenu extends JFrame implements MenuConstants{
	PlayableCharacter player;
	
	class MenuButtonListener implements ActionListener{
		int action; 
		SelectionMenu owner;
		
		public MenuButtonListener(int action, SelectionMenu owner){
			this.action = action;
			this.owner = owner;
		}
		
		public void actionPerformed(ActionEvent e){
			switch(action){
				case NEW_CHARACTER:
					new CharacterCreationWindow(owner);
					break;
				case SELECT_CHARACTER:
					new CharacterSelectionWindow(owner);
					break;
			}
		}
	}
	
	public SelectionMenu(String title){
		super(title);
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		initiate();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void initiate(){
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getSouthPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getCenterPanel(){
		JPanel centerPanel = new JPanel(new GridLayout(3,3));
		JPanel inCenterPanel = new JPanel(new GridLayout(2,1,50,50));
		
		
		JButton newCharacter = new JButton("Nouveau personnage");
		newCharacter.addActionListener(new MenuButtonListener(NEW_CHARACTER, this));
		JButton selectCharacter = new JButton("Charger personnage");
		selectCharacter.addActionListener(new MenuButtonListener(SELECT_CHARACTER, this));
		
		inCenterPanel.add(newCharacter);
		inCenterPanel.add(selectCharacter);
		
		centerPanel.add(new JPanel());
		centerPanel.add(new JPanel());	
		centerPanel.add(new JPanel());
		centerPanel.add(new JPanel());
		centerPanel.add(inCenterPanel);
		centerPanel.add(new JPanel());
		centerPanel.add(new JPanel());
		centerPanel.add(new JPanel());
		centerPanel.add(new JPanel());
		
		return centerPanel;
		
	}
	
	private JPanel getNorthPanel(){
		JPanel northPanel = new  JPanel();
		//JLabel gameTitle = new JLabel("Watashi wa genkiii!");

		
		//northPanel.add(gameTitle);
		return northPanel;
	}
	
	private JPanel getSouthPanel(){
		JPanel southPanel = new  JPanel();
		return southPanel;
	}
	
	public static void main(String[] args){
		SelectionMenu window = new SelectionMenu("watashi wa genki");
	}
}
