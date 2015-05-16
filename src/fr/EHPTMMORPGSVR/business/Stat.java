package fr.EHPTMMORPGSVR.business;

import java.util.Random;

public class Stat {
	public static final int OPERATOR_PLUS = 1;
	public static final int OPERATOR_MINUS = -1;
	
	private int diceNb;
	private int addNb;
	private int xp;
	private int level;
	String name;
	
	public static Stat operation(Stat s_1, Stat s_2, int operation){
		Stat s_sum = new Stat(s_1);
		s_sum.diceNb += operation*s_2.diceNb;
		s_sum.addNb += operation*s_2.addNb;
		
		/*if(backToValue){
			while(s_sum.addNb < 0 || s_sum.addNb >= 4){
				s_sum.addNb += -operation*4;
				s_sum.diceNb += operation;
			}
		}
		else{*/
		while(s_sum.addNb < 0){
			s_sum.addNb += -operation*4;
			s_sum.diceNb += operation;
			//}
		}
		
		if(s_sum.diceNb < 0){
			s_sum.diceNb = 0;
			s_sum.addNb = 0;
		}
		return s_sum;
	}
	
	public int getXp(){
		return xp;
	}
	
	public static Stat sub(Stat s_1, Stat s_2){//, boolean backToValue){
		return operation(s_1, s_2, OPERATOR_MINUS);//, backToValue);
	}
	
	public static Stat sum(Stat s_1, Stat s_2){//, boolean backToValue){
		return operation(s_1, s_2, OPERATOR_PLUS);//, backToValue);
	}
	
	public Stat(){ 
		this(1, 0, 0, "");
	}
	
	public Stat(int value, String name){
		this(value/4, value%4, value, name);
	}
	
	public Stat(int diceNb, int addNb, int level, String name){
		this.diceNb = diceNb;
		this.addNb = addNb;
		this.xp = 0;
		this.level = level;
		this.name = name;
	}
	
	public Stat(Stat stat){
		this(stat.diceNb, stat.addNb, stat.level, stat.name);
		this.xp = stat.xp;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getLevel(){
		return level;
	}
	
	//retourne le lancé de de de la stat courante.
	public int rollDice(){
		Random rand = new Random();
		int roll = 0;
		for(int i=0; i<diceNb; i++)
			roll += (1+rand.nextInt(6));
		roll += addNb;
		return roll;
	}
	
	public void setAddNb(int addNb){
		this.addNb = addNb;
		while(this.addNb >= 4){
			diceNb++;
			this.addNb -= 4;
		}	
	}
	
	public void addToAddNb(int addNb){
		this.addNb += addNb;
		while(this.addNb >= 4){
			diceNb++;
			this.addNb -= 4;
		}	
	}
	
	public void setDiceNb(int diceNb){
		this.diceNb = diceNb;
	}
	
	public Stat sub(Stat stat){
		return sub(this, stat);
	}
	
	public Stat sum(Stat stat){
		return sum(this, stat);
	}
	
	public String toString(){
		String stat= this.diceNb+"D"+" + "+this.addNb;
		return stat;
	}
	
	public void upgrade(int xp){
		this.xp += xp;

		if(this.xp >= xpToNextLevel()){
			this.xp -= xpToNextLevel();
			level++;
			addToAddNb(1);
			//refreshLevel();
		}
	}
	
	public int xpToNextLevel(){
		return (int) Math.exp(3+(0.25*(level-1)));
	}
	
	public int intValue(){
		return 4*diceNb + addNb;
	}
	
	private void refreshLevel(){
		diceNb = 1 + level/4;
		addNb = level%4;
	}

}
