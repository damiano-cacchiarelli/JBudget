/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.Collection;

import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * 
 * <p>
 * Le classi che implementeranno {@code Bank} avranno la responsabilit&agrave;
 * di implementare un gestore di {@code CashBox}.
 * </p>
 * <p>
 * Lo scopo di una {@code Bank} sar&agrave; quello di tenere traccia dello stato
 * di diversi {@code CashBox} e aggiornare quindi il bilancio. Infatti si
 * dovr&agrave; implementare un meccanismo che permetta di calcolare il bilancio
 * della banca a partire dallo stato dei {@code CashBox} che gestisce. Il
 * bilancio rappresenta l'insime di denaro che l'utente possiede.
 * </p>
 * <p>
 * Inoltre, oltre a un bilancio, una {@code Bank} dovra mettere a disposizione
 * un budget che rappresenta una certa quantit&agrave; di denaro e un meccanismo
 * per modificarlo.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */

public interface Bank {

	/**
	 * Restituir&agrave; il bilancio della {@code Bank}
	 * 
	 * @return il bilancio
	 */
	public double getBalance();

	/**
	 * Restituir&agrave; il {@code budget} della {@code Bank}
	 * 
	 * @return il budget
	 */
	public double getBudget();

	/**
	 * L'implementazione dovr&agrave; permettere di modificare il {@code budget}
	 * 
	 * @param b the new balance
	 */
	public void changeBudget(double b);

	/**
	 * L'implementazione dovr&agrave; permettere di ottenere il {@link CashBox}
	 * corrispondente alla stringa
	 * 
	 * @param s la stringa che rappresenta un {@code CashBox}
	 * @return il {@code CashBox} rappresentato dalla stringa
	 */
	public CashBox getCashBox(String s);

	/**
	 * Restituir&agrave; una {@link Collection} di tutti i {@link CashBox} che
	 * gestisce
	 * 
	 * @return a collection of all box
	 */
	public Collection<CashBox> getAllBox();

	/**
	 * L'implementazione dovr&agrave; permettere di aggiungere un nuovo
	 * {@link CashBox}
	 * 
	 * @param cb il {@link CashBox} da aggiungere
	 * @throws ExistingElementException se il {@link CashBox} che si vuole
	 *                                  aggiungere &egrave; gi&agrave; presente
	 * 
	 */
	public void addCashBox(CashBox cb) throws ExistingElementException;

	/**
	 * L'implementazione dovr&agrave; permettere di rimuovere un {@link CashBox}
	 * 
	 * @param cb il {@code CashBox} che si vuole eliminare
	 */
	public void subCashBox(CashBox cb);

	/**
	 * * L'implementazione dovr&agrave; permettere di aggiornare il bilancio della {@code Bank}
	 */
	public void updateBalance();

}
