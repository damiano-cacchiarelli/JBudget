/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import it.unicam.cs.pa.jbudget105101.App;

/**
 * La classe {@code LoadBox} estende le funzionalità di un {@link Box}
 * permettendo di definire un intervallo in cui il bilancio deve essere incluso.
 * Lo scopo di questa tipologia di {@link Box} è quello di raggiungere
 * l'obiettivo impostato. Una volta raggiunto tale obiettivo il {@code LoadBox}
 * verrà chiuso e non sarà possibile più possibile aggiungere o rimuovere
 * transazioni.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public abstract class LoadBox extends Box {

	/**
	 * Rappresenta l'obiettivo che si deve raggiungere.
	 */
	private double toLoad;

	/**
	 * Rappresenta lo stato del {@code LoadBox}. Se questa variabile è impostata a
	 * {@code true} il {@code LoadBox} viene considerato pieno.
	 */
	private boolean isFull = false;

	/**
	 * Permette di costruire un {@code LoadBox} con un determinato nome, bilancio e
	 * un obiettivo.
	 * 
	 * @param n       nome del {@code LoadBox}.
	 * @param balance il bilancio del {@code LoadBox}.
	 * @param toLoad  l'obiettivo da raggiungere.
	 */
	public LoadBox(String n, double balance, double toLoad) {
		super(n, balance);
		setToLoad(toLoad);
	}

	/**
	 * Restituisce la quantità di denaro che bisogna caricare nel {@code LoadBox}.
	 * 
	 * @return restituisce l'obiettivo.
	 */
	@Override
	public double getGoal() {
		return toLoad;
	}

	/**
	 * Permette di aggiungere una transazione al {@code LoadBox}. Se il
	 * {@code LoadBox} è pieno, non sarà possibile aggiungere una nuova transazione.
	 */
	@Override
	public boolean addTransaction(Transaction tr) {
		if (tr != null && !isFull && checkTransaction(tr.getValue())) {
			super.addTransaction(tr);
			if (isFull())
				isFull = true;
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Permette di rimuovere una transazione al {@code LoadBox}. Se il
	 * {@code LoadBox} è pieno, non sarà possibile rimuovere la transazione.
	 */
	@Override
	public boolean subTransaction(Transaction tr) {
		if (isFull)
			return false;
		return super.subTransaction(tr);
	}

	/** Set the amount of money to be charged */
	private void setToLoad(double toLoad) {
		this.toLoad = App.round(toLoad);
	}

	/**
	 * Viene verificato se il valore assoluto dell'obiettivo è minore del valore
	 * assoluto del bilancio. Se ciò è vero significa che il {@code LoadBox} è
	 * pieno.
	 * 
	 * @return {@code true} se il {@code LoadBox} è pieno; {@code false} altrimenti.
	 */
	private boolean isFull() {
		return Math.abs(toLoad) <= Math.abs(getBalance());
	}

	/**
	 * Verifica se una transazione può essere eseguita.
	 */
	@Override
	protected boolean checkTransaction(double t) {
		double tVal = getBalance() + t;
		return isIncluded(tVal, getInitBalance(), toLoad);

	}

	/**
	 * Viene verificato se il valore temporaneo del bilancio del {@code LoadBox} è
	 * compreso tra il bilancio iniziale e l'obbiettivo prefissato. Se il valore è
	 * compreso significa che la transazione può essere eseguita.
	 * 
	 * @param tVal     il valore da verificare
	 * @param infimum  estremo inferiore
	 * @param supremum estremo superiore
	 * @return {@code true} se il valore è compreso; {@code false} altrimento.
	 */
	private boolean isIncluded(double value, double infimum, double supremum) {
		return (value >= infimum & value <= supremum);
	}
}
