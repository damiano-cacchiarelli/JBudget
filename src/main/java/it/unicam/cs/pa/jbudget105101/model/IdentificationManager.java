/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

/**
 * <p>
 * Le classi che implementeranno {@code IdentificationManager} avranno la
 * responsabilit&agrave; di memorizzare e distribuire {@link Identifier}
 * univoci.
 * </p>
 * <p>
 * Inoltre dovranno implementare un meccanismo che permetta di restituire un
 * {@link Identifier} al manager in modo tale che possa essere riutilizzato.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 * @param <T> il tipo di {@link Identifier}
 */

public interface IdentificationManager<T extends Identifier<?>> {

	/**
	 * Dovrà generare un {@link Identifier} univoco per questo manager.
	 * 
	 * @return un nuovo identifier.
	 */
	T getNewIdentification();

	/**
	 * Dovrà permettere di restituire al manager un {@link Identifier} che non viene
	 * più utilizzato.
	 * 
	 * @param identifier l'identifier che si vuole restituire.
	 * @return {@code true} se il codice è stato ritirato. {@code false} altrimenti.
	 */
	boolean returnIdentification(T identifier);

	/**
	 * Dovrà permettere di verificare se un determinato {@link Identifier} è in uso.
	 * 
	 * @param identifier l'identifier che si vuole verificare.
	 * @return {@code true} se l'{@link Identifier} è in uso; {@code false}
	 *         altrimenti.
	 */
	boolean isUsed(T identifier);

}
