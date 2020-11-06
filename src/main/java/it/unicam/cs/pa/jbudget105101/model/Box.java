/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import it.unicam.cs.pa.jbudget105101.App;

/**
 * <p>
 * La classe implementa le funzionalità base di un {@link CashBox}.
 * </p>
 * <p>
 * Mette a disposizione metdi per aggiungere e rimuovere {@link Transaction
 * transazioni} e per aggiornare lo stato del {@code Box} che verificano le
 * transazioni, le eseguono se necessario e aggiornano il budget.
 * </p>
 * 
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public abstract class Box implements CashBox {

	/**
	 * Il bilancio del {@code Box} che può variare nel tempo.
	 */
	private double balance;

	/**
	 * Il bilancio iniziale.
	 */
	private final double startBalance;

	/**
	 * Il nome del {@code Box}.
	 */
	private String name;

	/**
	 * La lista di {@link Transaction transazioni} contenute nel {@code Box}.
	 */
	private Stack<Transaction> listOfTransaction;

	/**
	 * 
	 * Costruisce un {@code Box} con un determinato nome e bilancio.
	 * 
	 * @param n il nome del {@code Box}.
	 * @param b il bilancio del {@code Box}.
	 */
	public Box(String n, double b) {
		this.listOfTransaction = new Stack<>();
		this.setName(n);
		this.setBalance(b);
		this.startBalance = getBalance();
	}

	/**
	 * Permette di impostare il bilancio del {@code Box}. Il valore viene
	 * arrotondato a due cifre significative dopo la virola.
	 */
	private void setBalance(double b) {
		this.balance = App.round(b);
	}

	/**
	 * Permette di impostare il nome del {@code Box}.
	 */
	private void setName(String n) {
		if (n == null)
			throw new NullPointerException();
		this.name = n;
	}

	/**
	 * Restituisce il bilancio del {@code Box}.
	 * 
	 * @return il bilancio del box.
	 */
	@Override
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Restituisce il bilancio iniziale del {@code Box}.
	 * 
	 * @return il bilancio iniziale.
	 */
	@Override
	public double getInitBalance() {
		return startBalance;
	}

	/**
	 * Restituisce il nome del {@code Box}.
	 * 
	 * @return il nome del box.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Restituisce la lista delle transazioni contenuta nel {@code Box}.
	 * 
	 * @return la lista delle transazioni.
	 */
	@Override
	public Collection<Transaction> getAllTransaction() {
		return listOfTransaction;
	}

	/**
	 * Permette di aggiungere una {@link Transaction} in questo {@code Box}. Non è
	 * possibile aggiungere una {@code Transaction} {@code null} o già presente nel
	 * {@code Box}.</br>
	 * 
	 * Una volta che la {@code Transaction} è stata aggiunta è necessario aggiornare
	 * manualmente lo stato del {@code Box} con il metodo {@link #updateBalance()
	 * updateBalance}.
	 * 
	 * @return {@code true} se la transazione è stata aggiunta; {@code false}
	 *         altrimenti.
	 */
	@Override
	public boolean addTransaction(Transaction tr) {
		if (tr == null || listOfTransaction.contains(tr))
			return false;
		return listOfTransaction.add(tr);
	}

	/**
	 * Permette di rimuovere una {@link Transaction} in questo {@code Box}. Non è
	 * possibile rimuovere una {@code Transaction} {@code null} o non presente nel
	 * {@code Box}. </br>
	 * Una volta che la {@code Transaction} è stata rimossa è necessario aggiornare
	 * manualmente lo stato del {@code Box} con il metodo {@link #updateBalance()
	 * updateBalance}.
	 * 
	 * @return {@code true} se la transazione è stata rimossa; {@code false}
	 *         altrimenti.
	 */
	@Override
	public boolean subTransaction(Transaction tr) {
		if (tr == null || !listOfTransaction.contains(tr))
			return false;
		if (tr.isExecute() && !checkTransaction(-tr.getValue())) {
			return false;
		}
		return listOfTransaction.remove(tr);
	}

	/**
	 * 
	 * Permette di aggiornare lo stato del {@code Box}. Verifica se ci sono
	 * transazioni che hanno bisogno di essere eseguite, nel caso le esegue, e
	 * contemporaneamente somma l'importo di tutte le transazioni in modo tale da
	 * calcolare il bilancio attuale del {@code Box}.</br> Se ci sono transazioni che non
	 * possono essere eseguite vengono eliminate.
	 * 
	 * @return {@code true} se non sono state eliminate transazioni; {@code false}
	 *         altrimenti.
	 */
	@Override
	public boolean updateBalance() {
		LocalDate date = LocalDate.now();
		boolean p = true;
		double tBalance = 0;
		Iterator<Transaction> iterator = listOfTransaction.iterator();
		while (iterator.hasNext()) {
			Transaction t = iterator.next();
			if (t.isExecute()) {
				tBalance += t.getValue();
			} else if (!t.getDate().isAfter(date)) {
				if (!checkTransaction(t.getValue())) {
					iterator.remove();
					p = false;
				} else {
					t.toExecute();
					tBalance += t.getValue();
				}
			}
			setBalance(startBalance + tBalance);
		}
		setBalance(startBalance + tBalance);
		return p;
	}

	/**
	 * <p>
	 * Metodo astratto. La responsabilità di implementare questo metodo è delegata
	 * alle classi che estenderanno {@code Box}.
	 * <p>
	 * Lo scopo di questo metodo è quello di verificare se una transazione può
	 * essere eseguita in quel {@code Box}.
	 * 
	 * @param t l'importo della transazione
	 * @return {@code true} se la transazione può essere eseguita; {@code false}
	 *         altrimenti.
	 */
	protected abstract boolean checkTransaction(double t);

	/**
	 * Restituisce la stringa che rappresenta il {@code Box} con il seguente
	 * formato: [name]
	 */
	@Override
	public String toString() {
		return "[" + name + "] ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Due box sono considerati uguali se hanno lo stesso nome.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Box other = (Box) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
