/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.Set;

/**
 * <p>
 * Le classi che implementeranno {@code CategoryManager} avranno la
 * responsabilit&agrave; di definire un gestore di {@link Category} in modo tale
 * che sia possibile tenere traccia delle {@code category} utilizzate.
 * </p>
 * <p>
 * La classe dovr&agrave; implementare metodi per aggiungere, rimuovere e
 * cercare i vari elementi all'interno dell'insieme.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 * @see Category
 */
public interface CategoryManager {

	/**
	 * Dovrà restituire l'insieme di tutte le categorie che il manager gestisce.
	 * 
	 * @return un {@link Set} di tutte le categorie.
	 */
	public Set<Category> getCategoryList();

	/**
	 * Dovrà permettere di cercare una categoria a partire dalla stringa
	 * dell'oggetto.
	 * 
	 * @param s la stringa che rappresenta la categoria.
	 * @return la {@link Category} che corrisponde alla stringa.
	 */
	public Category getCategory(String s);

	/**
	 * Dovrà permettere di aggiungere una {@link Category} al manager.
	 * 
	 * @param c la categoria da aggiungere.
	 * @return {@code true} se è stata aggiunta; {@code false} altrimenti.
	 */
	public boolean addCategory(Category c);

	/**
	 * Dovrà permettere di rimuovere una determinata {@link CAtegory} dal manager.
	 * 
	 * @param c la categoria da rimuovere.
	 * @return {@code true} se è stata rimossa; {@code false} altrimenti.
	 */
	public boolean removeCategory(Category c);

}
