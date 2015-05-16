package fr.EHPTMMORPGSVR.business;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> implements CharacterConstants{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maximumCapacity;	
	public Inventory(DefaultCharacter character){
		super();
		maximumCapacity = INVENTORY_CAPACITY;
	}
	
	public boolean add(Item item){
		if(size() < maximumCapacity){
			return super.add(item);
		}
		return false;
	}
	
	/*public boolean use(Item item){
		if(contains(item) && item.use(character)){
			remove(item);
			return true;
		}
		return false;
	}*/
	
	public boolean isFull(){
		return size()==INVENTORY_CAPACITY;
	}
	
	/*public boolean use(int slot){
		if(get(slot).use(character)){
			remove(get(slot));
			return true;
		}
		return false;
	}*/
	
	public String toString(){
		String inventory = "";
		
		for(int i=0; i<size(); i++){
			if(i%4 == 0)
				inventory += "\n";
			try{
				inventory += "|" + get(i).getName() + "|";
			}
			catch (IndexOutOfBoundsException e){
				inventory += "|     |";
			}
			catch(NullPointerException e){
				inventory += "|     |";
			}
		}
		return inventory;
	}
}
