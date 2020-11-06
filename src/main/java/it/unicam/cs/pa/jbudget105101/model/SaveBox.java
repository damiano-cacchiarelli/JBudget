/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;

/**
 * La classe {@code SaveBox} estende le funzionalità di un {@link LoadBox}
 * implementando un box che ha la funzionalità di poter impostare come obiettivo
 * da raggiungere un valore positivo.
 * 
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class SaveBox extends LoadBox {

	/**
	 * Il {@link TypeCashBox tipo} di {@code SaveBox}.
	 */
	private final TypeCashBox type = TypeCashBox.SAVEBOX;

	/**
	 * 
	 * Costruisce un {@code SaveBox}. Viene verificato che il bilancio non sia
	 * negativo e che l'obiettivo non sia minore del bilancio.
	 * 
	 * @param name    il nome del {@code SaveBox}
	 * @param balance il bilancio del {@code SaveBox}
	 * @param goal    l'obiettivo del {@code SaveBox}
	 */
	public SaveBox(String name, double goal, double balance) {
		super(name, balance, goal);
		checkGoal(goal, balance);

	}

	/**
	 * Verifica che il bilancio non sia negativo e che l'obiettivo non sia minore
	 * del bilancio.
	 * 
	 * @throws IllegalArgumentException se il valore del bilancio o dell'obiettivo
	 *                                  non soddisfa i parametri richiesti.
	 */
	private void checkGoal(double goal, double balance) {
		if (balance < 0 || goal <= 0)
			throw new IllegalArgumentException("Non e' possibile inserire valori negativi");
		if (balance >= goal)
			throw new IllegalArgumentException("Il bilancio non puo essere maggiore del goal");
	}

	/**
	 * Restituisce il tipo di {@code CashBox}, definiti in {@link TypeCashBox}.
	 */
	@Override
	public TypeCashBox getType() {
		return type;
	}

	/**
	 * Restituisce la stringa che rappesenta il {@code SaveBox} con il seguente
	 * formato: [name][type]
	 */
	public String toString() {
		return super.toString() + type.name();
	}
}
