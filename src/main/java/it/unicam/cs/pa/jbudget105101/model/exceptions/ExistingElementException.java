/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model.exceptions;

/**
 * Utilizzata quando si tenta di inserire un elemento che è già presente.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class ExistingElementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2927223341884415375L;

	public ExistingElementException() {
		super();
	}

	public ExistingElementException(String s) {
		super(s);
	}
}
