/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * <p>
 * La classe {@code FamilyBank} rappresenta un gestore di {@link CashBox}.
 * </p>
 * <p>
 * Il {code bilancio} viene calcolato sommando i bilanci di tutti i
 * {@code CashBox} che la {@code FamilyBank} gestisce ad eccezzioni dei
 * {@link CashBox} di tipo {@link TypeCashBox}.{@code DEBITBOX} il cui bilancio
 * non viene considerato.
 * </p>
 * <p>
 * Il {@code budget} di una {@code FamilyBank} rappresenta la massima
 * quantit&agrave; di denaro che l'utente vuole cercare di spendere ed &egrave;
 * implementato per essere un budget mensile non vincolante: infatti &egrave;
 * possibile spendere pi&ugrave; di quanto previsto, ovviamente rispettando i
 * vincoli dei {@link CashBox}. Viene rappresentato con un valore double.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class FamilyBank implements Bank {

	/**
	 * Il bilancio e il budget di questa banca.
	 */
	private double balance, budget;

	/**
	 * La lista di {@link CashBox} che questa banca gestisce.
	 */
	private Stack<CashBox> listOfBox;

	/**
	 * Costruisce una {@code FamilyBank} con un specifico {@code budget} e una lista
	 * vuota di {@link CashBox}.
	 * 
	 * @param budget il budget che si vuole assegnare alla banca.
	 */
	public FamilyBank(double budget) {
		this.setBudget(budget);
		this.listOfBox = new Stack<>();
	}

	/**
	 * Restituisce il {@code bilancio} della banca.
	 * 
	 */
	@Override
	public double getBalance() {
		return balance;
	}

	/**
	 * Restituisce il {@code budget} della banca.
	 */
	@Override
	public double getBudget() {
		return budget;
	}

	/**
	 * Questo metodo permettere di modificare il {@code budget}. Il budget
	 * potr&agrave; essere un qualsiasi numero di tipo double non negativo. Se il
	 * valore presenta più di due cifre dopo la virgola verra arrotondato.
	 * 
	 * @throws IllegalArgumentException se il valore del {@code budget} è negativo.
	 */
	@Override
	public void changeBudget(double b) {
		setBudget(App.round(b));
	}

	/**
	 * Questo metodo permette di ottenere il {@link CashBox} corrispondente alla
	 * stringa.
	 * 
	 * @return il {@link} CashBox} rappresentato dalla stringa; {@code null} se non
	 *         viene trovato alcun {@link CashBox} corrispondente a quella stringa.
	 */
	@Override
	public CashBox getCashBox(String s) {
		for (CashBox cb : listOfBox) {
			if (cb.toString().equals(s))
				return cb;
		}
		return null;
	}

	/**
	 * Questo metodo retituisce una vista non modificabile della lista di
	 * {@link CashBox} che la banca gestisce.
	 */
	@Override
	public Collection<CashBox> getAllBox() {
		return Collections.unmodifiableCollection(listOfBox);
	}

	/**
	 * Questo metodo permette di aggiungere un {@link CashBox} alla banca. Non è
	 * possibile aggiungere due {@link CashBox} uguali.
	 * 
	 * @throws ExistingElementException se il {@link CashBox} che si vuole inserire
	 *                                  &egrave; già presente.
	 * @throws NullPointerException     se si tenta di inserire un elemento
	 *                                  {@code null}.
	 */
	@Override
	public void addCashBox(CashBox cb) throws ExistingElementException {
		if (cb == null)
			throw new NullPointerException("Impossibile aggiungere un Box null");
		if (listOfBox.contains(cb))
			throw new ExistingElementException("Esiste un " + cb.getType() + " con lo stesso nome");
		listOfBox.add(cb);
		updateBalance();

	}

	/**
	 * Questo metodo permette di eliminare un {@link CashBox} della banca.
	 * 
	 * @throws NullPointerException se si tenta di rimuovere un elemento non
	 *                              presente o {@code null}.
	 */
	@Override
	public void subCashBox(CashBox cb) {
		if (cb == null)
			throw new NullPointerException("Impossibile eliminare un Box null");
		if (!listOfBox.remove(cb))
			throw new NullPointerException("Il CashBox che si vuole rimuovere non è presente");
		updateBalance();

	}

	/**
	 * Questo metodo permette di aggiornare il bilancio della banca. Vengono sommati
	 * i bilanci di tutti i {@link CashBox} che essa gestisce, ad eccezione di
	 * quelli con tipo {@link TypeCashBox}. {@code DEBITBOX}.
	 */
	@Override
	public void updateBalance() {
		double tempB = 0;
		for (CashBox bb : listOfBox) {
			if (bb.getType() != TypeCashBox.DEBITBOX) {
				tempB += bb.getBalance();
			}
		}
		setBalance(tempB);
	}

	/**
	 * Imposta il bilancio della banca
	 * 
	 * @param b il bilancio da impostare
	 */
	private void setBalance(double b) {
		this.balance = b;

	}

	/**
	 * Imposta il budget della banca. Il valore non pu&ograve; essere negativo.
	 * 
	 * @param bd il budget da impostare
	 * @throws IllegalArgumentException se il valore del {@code budget} è negativo.
	 */
	private void setBudget(double bd) {
		if (bd < 0)
			throw new IllegalArgumentException("Non e' possibile inserire valori negativi");
		this.budget = App.round(bd);
	}

}
