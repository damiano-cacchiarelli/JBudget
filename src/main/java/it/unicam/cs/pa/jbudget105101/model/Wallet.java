/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;

/**
 * <p>
 * Un {@code Wallet} avrà la funzione di gestire un elenco di Transazioni
 * memorizzando il saldo, i soldi spesi e i soldi guadagnati. Un oggetto di tipo
 * Wallet rappresenta un account senza una capacità massima in cui le
 * transazioni possono essere aggiunte e rimosse liberamente.
 * 
 * Non implementa il metodo {@link #getGoal() getGoal}. Se viene utilizzato,
 * come da contratto verrà lanciata la seguente eccezione:
 * {@link UnsupportedOperationException}.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class Wallet extends Box {

	/**
	 * Il {@link TypeCashBox tipo} di {@code Wallet}.
	 */
	private final TypeCashBox type = TypeCashBox.WALLET;

	/**
	 * 
	 * Costruisce un {@code Wallet}.
	 * 
	 * @param name    il nome del {@code Wallet};
	 * @param balance il bilancio del {@code Wallet};
	 * @throws IllegalArgumentException se il bilancio è negativo.
	 */
	public Wallet(String name, double balance) {
		super(name, balance);
		checkBalance(balance);
	}

	/**
	 * La classe {@code Wallet} non implementa il metodo {@link #getGoal() getGoal}.
	 * Se viene utilizzato, come da contratto verrà lanciata la seguente eccezione:
	 * {@link UnsupportedOperationException}.
	 */
	@Override
	public double getGoal() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 */
	@Override
	public boolean addTransaction(Transaction tr) {
		return super.addTransaction(tr);
	}

	/**
	 * Restituisce il tipo di {@code CashBox}, definiti in {@link TypeCashBox}.
	 */
	@Override
	public TypeCashBox getType() {
		return type;
	}

	/**
	 * Viene verificato se una transazione può essere aggiunta al {@code Wallet}. In
	 * particolare viene controllato il valore della somma dell'attuale budget del
	 * {@code Wallet} e l'importo della transazione. Se questo valore è positivo
	 * significa che la transazione può essere eseguita.
	 * 
	 * @return {@code true} se la transazione può essere eseguita; {@code false}
	 *         altrimenti.
	 */
	@Override
	protected boolean checkTransaction(double t) {
		return (getBalance() + t >= 0);
	}

	/**
	 * Restituisce la stringa che rappesenta il {@code Wallet} con il seguente
	 * formato: [name] type
	 */
	@Override
	public String toString() {
		return super.toString() + type.name();
	}

	/**
	 * Metodo utilizzato nel costruttore per vedere se il bilancio che si vuole
	 * inserire è negativo. Nel caso viene lanciata {@link IllegalArgumentException}.
	 * 
	 * @param balance il bilancio che si vuole verificare.
	 */
	private void checkBalance(double balance) {
		if (balance < 0)
			throw new IllegalArgumentException();
	}

}
