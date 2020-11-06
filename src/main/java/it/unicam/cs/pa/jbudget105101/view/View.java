/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.view;

/**
 * <p>
 * Le classi che implementeranno {@code View} avranno la responsabilità di
 * mettere a disposizione dell'utente una vista con cui interagire.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface View {

	/**
	 * Dovrà permettere di avviare la vista.
	 * 
	 */
	void open();

	/**
	 * Dovrà permettere di chiudere la vista.
	 */
	void close();

}
