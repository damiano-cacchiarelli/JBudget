/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

/**
 * <p>
 * Le classi che implementeranno {@code Identifier} avranno la
 * responsabilit&agrave; di definire un oggetto che rappersenter&agrave; un
 * codice identificativo, con lo scopo di identificare un elemento in un dato
 * insieme.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 * @param <T> il tipo di {@code Identifier}.
 */

public interface Identifier<T> {

	/**
	 * Dovrà restituire il codice identificativo.
	 * 
	 * @return l'{@code Identifier} di tipo generico.
	 */
	T getValue();

	/**
	 * Dovrà implementare un meccanismo che permetta di trasformare l'oggetto
	 * {@code Identifier} in una stringa.
	 * 
	 * @return la stringa che rappresenta l'{@code Identifier}.
	 */
	String getStringValue();

}
