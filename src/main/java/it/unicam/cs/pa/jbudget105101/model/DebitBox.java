/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeTransaction;

/**
 * <p>
 * La classe {@code DebitBox} estende le funzionalità di un {@link LoadBox}
 * implementando un box che permette di definire un intervallo negativo in cui
 * esso opera.
 * </p>
 * L'obiettivo di un {@code DebitBox} è sempre raggiungere lo zero, mentre il
 * suo bilancio iniziale è sempre negativo.</br>
 * Inoltre non è permesso inserire transazioni con {@link TypeTransaction tipo}
 * {@code DEBIT}
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class DebitBox extends LoadBox {

	/**
	 * Il {@link TypeCashBox tipo} di {@code DebitBox}.
	 */
	private final TypeCashBox type = TypeCashBox.DEBITBOX;

	/**
	 * Costruisce un {@code DebitBox} che ha come obiettivo 0.0 . Viene controllato
	 * che il bilancio non sia negativo.
	 * 
	 * @param n     il nome del {@code DebitBox}.
	 * @param debit il debito del {@code DebitBox}.
	 */
	public DebitBox(String n, double debit) {
		super(n, debit, 0.0);
		checkDebit(debit);
	}

	/**
	 * Verifica se il bilancio rispetta le condizioni previste.
	 * 
	 * @throws IllegalArgumentException se il bilancio è positivo.
	 */
	private void checkDebit(double debit) {
		if (debit >= 0)
			throw new IllegalArgumentException("Il debito non puo essere positivo");

	}

	/**
	 * Restituisce il tipo di {@code CashBox}, definiti in {@link TypeCashBox}.
	 */
	@Override
	public TypeCashBox getType() {
		return type;
	}

	/**
	 * 
	 * Permette di inserire una transazione nel {@code DebitBox}. Se il
	 * {@code DebitBox} è pieno, la transazione non potrà essere aggiunta. Inoltre
	 * non è possibile inserire una transazione con tipo {@code DEBIT}.
	 * 
	 * @throws IllegalArgumentException se la transazione ha importo negativo.
	 */
	@Override
	public boolean addTransaction(Transaction tr) {
		if (tr == null)
			return false;
		if (tr.getType() == TypeTransaction.DEBIT)
			throw new IllegalArgumentException("Non e' possibile aggiungere una transazione negativa a un DebitBox");
		return super.addTransaction(tr);
	}

	/**
	 * Restituisce la stringa che rappesenta il {@code DebitBox} con il seguente
	 * formato: [name][type]
	 */
	public String toString() {
		return super.toString() + type.name();
	}

}
