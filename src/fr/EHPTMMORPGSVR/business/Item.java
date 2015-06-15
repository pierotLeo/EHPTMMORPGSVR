package fr.EHPTMMORPGSVR.business;

import java.io.Serializable;

/**
 * Classe abstraite Item.
 * Réunit tous les types d'objets du jeu (armes, consommables...).
 */
public interface  Item extends StuffConstants, CharacterConstants, GlobalConstants, Serializable{
	
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
	
	
	public abstract String getType();
	
	public abstract void setType(String type);
	/**
	 * Méthode abstraite.
	 * Pour utiliser un item sur le personnage reçu en paramètre.
	 * 
	 * @param user Personnage sur lequel utiliser l'item.
	 */
	public abstract int use(PlayableCharacter user);
	
}
