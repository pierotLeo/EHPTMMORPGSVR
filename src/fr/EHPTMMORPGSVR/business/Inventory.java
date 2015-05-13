package fr.EHPTMMORPGSVR.business;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> implements Constants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maximumCapacity;
	private PlayableCharacter character;
	private int currentSlot;
	
	public Inventory(PlayableCharacter character){
		super();
		maximumCapacity = INVENTORY_CAPACITY;
		this.character = character;
	}
	
	public String toString(){
		String inventory = "";
		
		for(int i=0; i<size(); i++){
			if(i%4 == 0)
				inventory += "\n";
			try{
				inventory += "|" + get(i).getName() + "|";
			}
			catch (IndexOutOfBoundsException | NullPointerException e){
				inventory += "|     |";
			}
		}
		return inventory;
	}
	
	public boolean add(Item item){
		if(size() < maximumCapacity){
			trimToSize();
			return super.add(item);
		}
			
		return false;
	}
	
	public boolean use(Item item){
		if(contains(item) && item.use(character)){
			remove(item);
			trimToSize();
			return true;
		}
		return false;
	}
	
	public boolean isFull(){
		return size()==INVENTORY_CAPACITY;
	}
	
	public boolean use(int slot){
		if(get(slot).use(character)){
			remove(get(slot));
			return true;
		}
		return false;
	}
	
	public int getCurrentSlot(){
		return currentSlot;
	}
	
	public boolean moveCurrentSlot(int value){
		if(currentSlot < maximumCapacity && currentSlot >= 0){
			currentSlot += value;
			return true;
		}
		return false;
	}
}
