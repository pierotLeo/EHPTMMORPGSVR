package fr.EHPTMMORPGSVR.business;

import java.util.Random;

public class Stat {
	public static final int OPERATOR_PLUS = 1;
	public static final int OPERATOR_MINUS = -1;
	
	private int diceNb;
	private int addNb;
	private int xp;
	private int level;
	
	public Stat(){ 
		this(1, 0, 0);
	}
	
	public Stat(int value){
		this(value/3, value%3, value);
	}
	
	public Stat(int diceNb, int addNb, int level){
		this.diceNb = diceNb;
		this.addNb = addNb;
		this.xp = (int) Math.exp(3+(0.25*(level-1)));
		this.level = level;
	}
	
	public Stat(Stat stat){
		this(stat.diceNb, stat.addNb, stat.level);
	}
	
	public String toString(){
		String stat= this.diceNb+"D"+" + "+this.addNb;
		return stat;
	}
	
	public void setDiceNb(int diceNb){
		this.diceNb = diceNb;
	}
	
	public void setAddNb(int addNb){
		while(addNb > 3 && addNb > 0){
			this.diceNb++;
			addNb -= 3;
		}
		this.addNb = addNb;
		
	}
	
	public int getLevel(){
		return level;
	}
	
	public int xpToNextLevel(){
		return (int) Math.exp(3+(0.25*(level-1)));
	}
	
	private void refreshLevel(){
		diceNb = 1 + level/3;
		addNb = level%3;
	}
	
	public void upgrade(int xp){
		this.xp += xp;
		
		while(this.xp >= xpToNextLevel()){
			this.xp -= xpToNextLevel();
			level++;
			refreshLevel();
		}
	}
	
	//retourne le lanc� de de de la stat courante.
	public int rollDice(){
		Random rand = new Random();
		int roll = 0;
		for(int i=0; i<diceNb; i++)
			roll += (1+rand.nextInt(6));
		roll += addNb;
		return roll;
	}
	
	//retourne la somme de la stat courante avec la stat passee en parametre.
	public Stat sum(Stat stat){
		return sum(this, stat);
	}
	
	public Stat sub(Stat stat){
		return sub(this, stat);
	}
	
	public static Stat sum(Stat s_1, Stat s_2){
		return operation(s_1, s_2, OPERATOR_PLUS);
	}
	
	public static Stat sub(Stat s_1, Stat s_2){
		return operation(s_1, s_2, OPERATOR_MINUS);
	}
	
	public static Stat operation(Stat s_1, Stat s_2, int operation){
		Stat s_sum = new Stat(s_1);
		s_sum.diceNb += operation*s_2.diceNb;
		s_sum.addNb += operation*s_2.addNb;
		
		while(s_sum.addNb < 0 || s_sum.addNb >= 3){
			s_sum.addNb += -operation*3;
			s_sum.diceNb += operation;
		}
		return s_sum;
	}

}
