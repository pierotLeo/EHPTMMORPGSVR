package fr.EHPTMMORPGSVR.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.EHPTMMORPGSVR.dialog.PlayableCharacterView;
import fr.EHPTMMORPGSVR.menu.SelectionMenu.MenuButtonListener;

public class CharacterCreationWindow extends JFrame{
	public static final int AGILITY = 2;
	public static final int RESISTANCE = 1;
	public static final int STRENGTH = 0;
	public static final int MINUS = 0;
	public static final int PLUS = 1;
	
	public static final int DEFAULT_COMPENTCY_POINTS = 18;
	
	private SelectionMenu owner;
	private JButton strengthValue;
		private JButton strMinus;
		private JButton strPlus;
	private JButton resistanceValue;
		private JButton resistMinus;
		private JButton resistPlus;
	private JButton agilityValue;
		private JButton agiMinus;
		private JButton agiPlus;
	private JTextField nameCaption;
	private JButton pointsValue;
	private JButton create;
	private PlayableCharacterView player;
	
	public CharacterCreationWindow(SelectionMenu owner){
		super("Création de personnage");
		this.owner = owner;
		Toolkit tkt = Toolkit.getDefaultToolkit();
		Dimension itemWindowDim = tkt.getScreenSize();
		int height = (int)itemWindowDim.getHeight();
		int width = (int)itemWindowDim.getWidth();
		this.setBounds(width/2-width/4, height/2-height/4, width/2, height/2);
		initiate();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initiate(){
		add(getNorthPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
	}
	
	public JPanel getNorthPanel(){
		JPanel northPanel = new JPanel();
		
		JLabel title = new JLabel("Création de personnage");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		
		northPanel.add(title);
		return northPanel;
	}
	
	class CreateActionListener implements ActionListener{
		private CharacterCreationWindow owner;
		private SelectionMenu superOwner;
		
		public CreateActionListener(CharacterCreationWindow owner, SelectionMenu superOwner){
			this.owner = owner;
			this.superOwner = superOwner;
		}
		
		public void actionPerformed(ActionEvent e){
			String value = e.getActionCommand();
			if(value.matches("Créer")){
				if(!nameCaption.getText().matches("")){
					player = new PlayableCharacterView(Integer.parseInt(strengthValue.getText()), Integer.parseInt(resistanceValue.getText()), Integer.parseInt(agilityValue.getText()),nameCaption.getText());
					FileHandler.saveCharacter(player);
					new CharacterSelectionWindow(superOwner);
					owner.dispose();
				}
				else{
					JOptionPane.showMessageDialog(owner, "Vous devez donner un nom à votre personnage!");
				}
			
			}
		}
	}
	
	class CharacActionListener implements ActionListener{
		int charac;
		int operation;
		
		public CharacActionListener(int charac, int operation){
			this.charac = charac;
			this.operation = operation;
		}
		
		public void actionPerformed(ActionEvent e){
			int remainingPoints = Integer.parseInt(pointsValue.getText());
			switch(charac){
				case STRENGTH:
					int newStrengthValue = Integer.parseInt(strengthValue.getText());
					switch(operation){
						case MINUS:
							newStrengthValue -= 1;
							remainingPoints += 1;
							if(newStrengthValue == 0)
								strMinus.setEnabled(false);
							if(remainingPoints > 0){
								strPlus.setEnabled(true);
								resistPlus.setEnabled(true);
								agiPlus.setEnabled(true);
							}
							if(remainingPoints > 0){
								create.setEnabled(false);
							}
							
							strengthValue.setText(String.valueOf(newStrengthValue));
							break;
						case PLUS:
							newStrengthValue += 1;
							remainingPoints -= 1;
							if(newStrengthValue > 0)
								strMinus.setEnabled(true);
							if(remainingPoints == DEFAULT_COMPENTCY_POINTS){
								strMinus.setEnabled(false);
								resistMinus.setEnabled(false);
								agiMinus.setEnabled(false);
							}
							if(remainingPoints == 0){
								strPlus.setEnabled(false);
								resistPlus.setEnabled(false);
								agiPlus.setEnabled(false);
								create.setEnabled(true);
							}
							
							
							strengthValue.setText(String.valueOf(newStrengthValue));
							break;
					}
					break;
				case RESISTANCE:
					int newResistanceValue = Integer.parseInt(resistanceValue.getText());
					switch(operation){
						case MINUS:
							newResistanceValue -= 1;
							remainingPoints += 1;
							if(newResistanceValue == 0)
								resistMinus.setEnabled(false);
							if(remainingPoints > 0){
								strPlus.setEnabled(true);
								resistPlus.setEnabled(true);
								agiPlus.setEnabled(true);
							}
							if(remainingPoints > 0){
								create.setEnabled(false);
							}
							
							resistanceValue.setText(String.valueOf(newResistanceValue));
							break;
						case PLUS:
							newResistanceValue += 1;
							remainingPoints -= 1;
							if(newResistanceValue > 0)
								resistMinus.setEnabled(true);	
							if(remainingPoints == DEFAULT_COMPENTCY_POINTS){
								strMinus.setEnabled(false);
								resistMinus.setEnabled(false);
								agiMinus.setEnabled(false);
							}
							if(remainingPoints == 0){
								strPlus.setEnabled(false);
								resistPlus.setEnabled(false);
								agiPlus.setEnabled(false);
								create.setEnabled(true);
							}
							resistanceValue.setText(String.valueOf(newResistanceValue));
							break;
					}
					break;
				case AGILITY:
					int newAgilityValue = Integer.parseInt(agilityValue.getText());
					switch(operation){
						case MINUS:
							newAgilityValue -= 1;
							remainingPoints += 1;
							if(newAgilityValue == 0)
								agiMinus.setEnabled(false);
							if(remainingPoints > 0){
								strPlus.setEnabled(true);
								resistPlus.setEnabled(true);
								agiPlus.setEnabled(true);
							}
							if(remainingPoints > 0){
								create.setEnabled(false);
							}
							
							agilityValue.setText(String.valueOf(newAgilityValue));
							break;
						case PLUS:
							newAgilityValue += 1;
							remainingPoints -= 1;
							if(newAgilityValue > 0)
								agiMinus.setEnabled(true);	
							if(remainingPoints == DEFAULT_COMPENTCY_POINTS){
								strMinus.setEnabled(false);
								resistMinus.setEnabled(false);
								agiMinus.setEnabled(false);
							}
							if(remainingPoints == 0){
								strPlus.setEnabled(false);
								resistPlus.setEnabled(false);
								agiPlus.setEnabled(false);
								create.setEnabled(true);
							}
							agilityValue.setText(String.valueOf(newAgilityValue));
							break;
					}
					break;
			}
			pointsValue.setText(String.valueOf(remainingPoints));
		}
	}
	
	private JPanel getCenterPanel(){
		JPanel centerPanel = new JPanel(new GridLayout(3,3));
		JPanel inCenterPanel = new JPanel(new GridLayout(2,1));
		
		inCenterPanel.add(new JLabel());
		inCenterPanel.add(getNameCaptionZone());
		
		centerPanel.add(new JPanel());
		centerPanel.add(inCenterPanel);	
		centerPanel.add(getPointsPanel());
		
		centerPanel.add(getCharacPanel("         Force", STRENGTH));
		centerPanel.add(getCharacPanel("    Résistance", RESISTANCE));
		centerPanel.add(getCharacPanel("        Agilité", AGILITY));
		 
		centerPanel.add(getValuePanel(STRENGTH));
		centerPanel.add(getValuePanel(RESISTANCE));
		centerPanel.add(getValuePanel(AGILITY));
		
		return centerPanel;
		
	}
	
	public JPanel getPointsPanel(){
		JPanel pointsPanel = new JPanel(new GridLayout(2,1));
		JPanel pointsLabelZone = new JPanel(new GridLayout(2,1));
		JPanel pointsValueZone = new JPanel(new GridLayout(3,3));
		
		JLabel pointsLabel = new JLabel("                  Points de compétences restants");
		pointsLabelZone.add(new JPanel());
		pointsLabelZone.add(pointsLabel, BorderLayout.SOUTH);
		
		pointsValue = new JButton("18");
		pointsValue.setEnabled(false);
		pointsValueZone.add(new JLabel());
		pointsValueZone.add(new JLabel());
		pointsValueZone.add(new JLabel());
		
		pointsValueZone.add(new JLabel());
		pointsValueZone.add(pointsValue);
		pointsValueZone.add(new JLabel());
		
		pointsValueZone.add(new JLabel());
		pointsValueZone.add(new JLabel());
		pointsValueZone.add(new JLabel());
		
		pointsPanel.add(pointsLabelZone);
		pointsPanel.add(pointsValueZone);
		return pointsPanel;
		
	}
	
	public JPanel getValuePanel(int type){
		JPanel valuePanel = new JPanel(new GridLayout(4,3));
		
		valuePanel.add(new JPanel());
		valuePanel.add(new JPanel());
		valuePanel.add(new JPanel());
		
		valuePanel.add(new JPanel());
		switch(type){
			case STRENGTH:
				strengthValue = new JButton("0");
				strengthValue.setEnabled(false);
				valuePanel.add(strengthValue);
				break;
			case RESISTANCE:
				resistanceValue = new JButton("0");
				resistanceValue.setEnabled(false);
				valuePanel.add(resistanceValue);
				break;
			case AGILITY:
				agilityValue = new JButton("0");
				agilityValue.setEnabled(false);
				valuePanel.add(agilityValue);
				break;
		}
		valuePanel.add(new JPanel());
		
		valuePanel.add(new JPanel());
		valuePanel.add(new JPanel());
		valuePanel.add(new JPanel());
		
		valuePanel.add(new JPanel());
		if(type == RESISTANCE){
			create = new JButton("Créer");
			create.setEnabled(false);
			create.addActionListener(new CreateActionListener(this, owner));
			valuePanel.add(create);
		}
		else
			valuePanel.add(new JPanel());
		valuePanel.add(new JPanel());
		
		return valuePanel;
	}
	
	public JPanel getCharacPanel(String characName, int type){
		JPanel characPanel = new JPanel(new GridLayout(2,1));
		JPanel characLabelZone = new JPanel(new GridLayout(1,3));
		JPanel characButtons = new JPanel(new GridLayout(1,5));
		
		JButton plus = null;
		JButton minus = null;
		
		JLabel characLabel = new JLabel(characName);
		switch(type){
			case STRENGTH:
				strMinus = new JButton("-");
				strMinus.addActionListener(new CharacActionListener(STRENGTH, MINUS));
				strMinus.setEnabled(false);
				strPlus = new JButton("+");
				strPlus.addActionListener(new CharacActionListener(STRENGTH, PLUS));
				minus = strMinus;
				plus = strPlus;
				break;
			case RESISTANCE:
				resistMinus = new JButton("-");
				resistMinus.addActionListener(new CharacActionListener(RESISTANCE, MINUS));
				resistMinus.setEnabled(false);
				resistPlus = new JButton("+");
				resistPlus.addActionListener(new CharacActionListener(RESISTANCE, PLUS));
				minus = resistMinus;
				plus = resistPlus;
				break;
			case AGILITY:
				agiMinus = new JButton("-");
				agiMinus.addActionListener(new CharacActionListener(AGILITY, MINUS));
				agiMinus.setEnabled(false);
				agiPlus = new JButton("+");
				agiPlus.addActionListener(new CharacActionListener(AGILITY, PLUS));
				minus = agiMinus;
				plus = agiPlus;
				break;
		}
		
		characLabelZone.add(new JPanel());
		characLabelZone.add(characLabel);
		characLabelZone.add(new JPanel());
		
		characButtons.add(new JPanel());
		characButtons.add(minus);
		characButtons.add(new JPanel());
		characButtons.add(plus);
		characButtons.add(new JPanel());
		
		characPanel.add(characLabelZone);
		characPanel.add(characButtons);
		
		return characPanel;
		
	}
	
	public JPanel getNameCaptionZone(){
		JPanel nameCaptionZone = new JPanel(new GridLayout(3,1));
		nameCaption = new JTextField("");
		JLabel nameLabel = new JLabel("Nom de vous");
		JPanel nameLabelZone = new JPanel(new GridLayout(1,3));
		nameLabelZone.add(new JLabel());
		nameLabelZone.add(nameLabel);
		nameLabelZone.add(new JLabel());
		
		nameCaptionZone.add(nameLabelZone);
		nameCaptionZone.add(nameCaption);
		nameCaptionZone.add(new JLabel());
		
		return nameCaptionZone;
	}
	
}
