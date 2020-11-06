/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

/**
 * <p>
 * Le classi che implementeranno {@code Category} avranno la
 * responsabilit&agrave; di rappresentare una categoria in cui un elemento
 * pu&ograve; far parte.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface Category {

	/**
	 * Dovrà restituire il nome che rappesenta la categoria.
	 * 
	 * @return il nome della categoria.
	 */
	public String getName();

	/**
	 * Dovrà restituire la priorità di questa categoria.
	 * 
	 * @return la priorità della categoria
	 */
	public int getPriority();
}
