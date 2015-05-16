package fr.EHPTMMORPGSVR.business;

public class DeprecatedInventory {
	private Item[] content;
	private int currentSlot;
	private PlayableCharacter player;
	
	public DeprecatedInventory(int size, PlayableCharacter player){
		content = new Item[size];
		currentSlot = 0;
		this.player = player;
	}
	
	public DeprecatedInventory (Item[] toCopy){
		content = toCopy;
	}
	
	public void addToInventory(Item item){
		content[currentSlot] = item;
		currentSlot++;
	}
	
	public Item getContent(int location){
		return content[location];
	}
	
	public String toString(){
		String inventory = "";
		
		for(int i=0; i<content.length; i++){
			if(content[i] != null)
				inventory+="| " + content[i].getName() + " |";
			else
				inventory+="| vide |";
		}
		
		return inventory;
	}

	
	public void use(Item item){
		for(int i=0; i<content.length; i++){
			if(content[i].equals(item)){
				content[i].use(player);
			}
		}
	}
}
