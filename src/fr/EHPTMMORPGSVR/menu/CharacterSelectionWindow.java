package fr.EHPTMMORPGSVR.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.EHPTMMORPGSVR.business.PlayableCharacter;
import fr.EHPTMMORPGSVR.dialog.GameWindow;
import fr.EHPTMMORPGSVR.dialog.PlayableCharacterView;
import fr.EHPTMMORPGSVR.server.ServerConstants;

public class CharacterSelectionWindow extends JFrame implements ServerConstants{
	private SelectionMenu superOwner;
	private JPanel characPanel;
	private JPanel centerPanel;
	
	class DeleteActionListener implements ActionListener{
		private int characLocation;
		private CharacterSelectionWindow owner;
		private PlayableCharacterView player;
		
		public DeleteActionListener(int characLocation, PlayableCharacterView player, CharacterSelectionWindow owner){
			this.characLocation = characLocation;
			this.owner = owner;
			this.player = player;
		}
		
		public void actionPerformed(ActionEvent e){
			FileHandler.deleteCharacter(characLocation);
			new CharacterSelectionWindow(superOwner);
			owner.dispose();
		}
	}
	
	class ConnectActionListener implements ActionListener{
		private PlayableCharacterView player;
		private CharacterSelectionWindow owner;
		
		public ConnectActionListener(PlayableCharacterView player, CharacterSelectionWindow owner){
			this.player = player;
			this.owner = owner;
		}
		
		public void actionPerformed(ActionEvent e){
			new GameWindow("Bilibilibibi", player);
			superOwner.dispose();
			owner.dispose();				
			
		}
	}
	
	public CharacterSelectionWindow(SelectionMenu owner){
		super("Sélection de personnage");
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		initiate();
		this.superOwner = owner;
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initiate(){
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.SOUTH);
	}
	
	public JPanel getNorthPanel(){
		JPanel northPanel = new JPanel();
		
		JLabel title = new JLabel("Sélection de personnage");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		
		northPanel.add(title);
		return northPanel;
	}
	
	public JPanel getCenterPanel(){
		centerPanel = new JPanel(new GridLayout(5,1,50,50));
		
		for(int i=0; i<5; i++){
			centerPanel.add(loadCharacPanel(i));
		}
		
		return centerPanel;
	}
	
	public JPanel loadCharacPanel(int characNumber){
		characPanel = new JPanel(new GridLayout(1,2));
		
		JPanel characInfoZone = new JPanel(new GridLayout());
		JPanel connectButtonZone = new JPanel();
		PlayableCharacterView currentCharacter = FileHandler.loadPlayer(characNumber);
		
		if(currentCharacter != null){
			//System.out.println(currentCharacter);
			JTextField characInfo = new JTextField("nom : " + currentCharacter.getName());
			characInfo.setEditable(false);
			characInfo.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 15));
			characInfoZone.setBorder(BorderFactory.createTitledBorder("Personnage " + (characNumber)));
			characInfoZone.add(characInfo);
			
			JButton connect = new JButton("connexion");
			JButton delete = new JButton("Supprimer");
			connect.addActionListener(new ConnectActionListener(currentCharacter, this));
			delete.addActionListener(new DeleteActionListener(characNumber,currentCharacter,  this));
			connectButtonZone.add(connect);
			connectButtonZone.add(delete);
		}
		else{
			characInfoZone.add(new JPanel());
			connectButtonZone.add(new JPanel());
		}
		
		
	
		characPanel.add(characInfoZone);
		characPanel.add(connectButtonZone);
		//characPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return characPanel;
	}
}
