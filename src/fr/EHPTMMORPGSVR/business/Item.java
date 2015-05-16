package fr.EHPTMMORPGSVR.business;

/**
 * Classe abstraite Item.
 * Réunit tous les types d'objets du jeu (armes, consommables...).
 */
public interface  Item extends StuffConstants, CharacterConstants{
	
	/**
	 * Pour accéder au nom de l'item.
	 * 
	 * @return Nom de l'item.
	 */
	public abstract String getName();
	
	/**
	 * Pour changer le nom de l'item.
	 * 
	 * @param name Nouveau nom.
	 */
	public abstract void setName(String name);
	
	/**
	 * Méthode abstraite.
	 * Pour utiliser un item sur le personnage reçu en paramètre.
	 * 
	 * @param user Personnage sur lequel utiliser l'item.
	 */
	public abstract void use(PlayableCharacter user);
	
}
