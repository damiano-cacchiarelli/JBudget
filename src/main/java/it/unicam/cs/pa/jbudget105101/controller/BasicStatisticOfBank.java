/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeTransaction;

/**
 * <p>
 * La classe implementa funzionalità statistiche.
 * </p>
 * <p>
 * &Egrave; possibile calcolare la quantità di denaro spesa in un mese o in un
 * giorno, la spesa di una categoria e la sua percentuale in relazione alle
 * latre; è possibile ottenere il numero di transazioni avvenute in un giorno e
 * infine la percentuale di completamento di un determinato Box.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 * @param <T> il tipo che rappresenta una {@link Bank}
 */
public class BasicStatisticOfBank<T extends Bank> implements StatisticsOfBank<T> {

	/**
	 * La {@link Bank} su cui verranno effettuate le operazioni.
	 */
	private final T bank;

	/**
	 * Costruisce un {@code BasicStatisticOfBank} su una determinata {@link Bank}.
	 * 
	 * @param bank la {@code Bank} che si vuole utilizzare;
	 */
	public BasicStatisticOfBank(T bank) {
		this.bank = bank;
	}

	/**
	 * Permette di ottenere la quantità di denaro spesa in un determinato mese
	 * dell'anno.</br>
	 * Della data non verrà preso in considerazione il giorno. Richiamare il metodo
	 * con una data come 02/12/1999 oppure 25/12/1999 porta allo stesso
	 * risultato.</br>
	 * 
	 * @param date la data in cui verrà calcolata la spesa.
	 * @return la quantità di denaro spesa in quel periodo.
	 */
	@Override
	public double outlayOfMonth(LocalDate date) {

		int month = date.getMonthValue(), year = date.getYear();
		double outlay = bank.getAllBox().parallelStream()
				.mapToDouble(x -> x.getAllTransaction().parallelStream()
						.filter(z -> z.getType() == TypeTransaction.DEBIT && z.isExecute())
						.filter(z -> z.getDate().getMonthValue() == month).filter(z -> z.getDate().getYear() == year)
						.mapToDouble(z -> z.getValue()).sum())
				.sum();
		return -outlay;
	}

	/**
	 * Permette di ottenere la quantità di denaro spesa per una determinata
	 * {@link Category}.
	 * 
	 * @param c la {@link Category} cui cercare le {@link Transaction};
	 * @return la quantità di denaro spesa.
	 */
	@Override
	public double outlayOfCategory(Category c) {
		double tVal = 0;
		tVal = bank.getAllBox().stream()
				.mapToDouble(x -> x.getAllTransaction().stream()
						.filter(z -> (z.getType() == TypeTransaction.DEBIT))
						.filter(z -> z.isExecute())
						.filter(z -> z.getCategory().contains(c))
						.mapToDouble(z -> z.getValue()).sum())
				.sum();

		return -tVal;
	}

	/**
	 * Permette di ottenere la percentuale di denaro spesa per una {@link Category}.
	 * 
	 * @param c la {@link Category} cui ottenere la percentuale;
	 * @return la percentuale di spesa.
	 */
	@Override
	public double outlayPercentOfCategory(Category c) {
		double tVal = outlayOfCategory(c);
		double tValTot = bank.getAllBox().stream()
				.mapToDouble(x -> x.getAllTransaction().stream()
						.filter(z -> (z.getType() == TypeTransaction.DEBIT))
						.filter(z -> z.isExecute())
						.mapToDouble(z -> z.getValue()).sum())
				.sum();

		return App.round(tVal * 100 / -tValTot);
	}

	/**
	 * Permette di ottenere la quantità di denaro spesa in un determinato giorno.
	 * 
	 * @param date la data in cui verrà calcolata la spesa.
	 * @return la quantità di denaro spesa in quel periodo.
	 */
	@Override
	public double outlayOfDay(LocalDate date) {
		double tVal = bank.getAllBox().stream()
				.mapToDouble(x -> x.getAllTransaction().stream()
						.filter(z -> (z.getDate().equals(date) ))
						.filter(z -> z.getType() == TypeTransaction.DEBIT)
						.mapToDouble(z -> z.getValue()).sum())
				.sum();
		return -tVal;
	}

	/**
	 * Permette di ottenere il numero di {@link Transaction} effettuate in un
	 * determinato giorno.
	 * 
	 * @param date la data in cui verranno contate le {@link Transaction};
	 * @return il numero di {@link Transaction} in una data.
	 */
	@Override
	public double numberOfTransactionInDay(LocalDate date) {
		double tVal = bank.getAllBox().stream()
				.mapToDouble(x -> x.getAllTransaction().stream()
						.filter(z -> (z.getDate().equals(date)))
						.count())
				.sum();
		return tVal;
	}

	/**
	 * Dovrà permettere di ottenere la precentuale di completamento di un
	 * determinato {@link CashBox}. La percentuale viene rappresentata con un
	 * nuovero che va da 0.0 a 1.0.
	 * 
	 * Se il {@code CashBox} non supporta tale funzione la percentuale sarà 1.0.
	 * 
	 * @param cb il {@code CashBox} che si vuole analizzare.
	 * @return la precentuale di completamento.
	 */
	@Override
	public double getBalancePercent(CashBox cb) {
		if (cb != null)
			try {
				return getPercent(cb.getInitBalance(), cb.getBalance(), cb.getGoal());
			} catch (UnsupportedOperationException e) {
				App.logger.info("A WALLET type CashBox was selected : a FunctionNotSupportedException was caught");
				return 1.0;
			}
		return 0.0;

	}

	/**
	 * <p>
	 * Restituisce la percentuale del {@code bilancio} sul {@code goal}. Il bilancio
	 * iniziale viene sottratto da entrambi i dati.
	 * </p>
	 * <p>
	 * Nel caso in cui il bilancio iniziale è negativo viente richiamato il metodo
	 * effettuando delle modifiche sui parametri in modo che sia possibile calcolare
	 * la percentuale anche con numeri negativi.
	 * </p>
	 * 
	 * @param initBalance bilancio iniziale;
	 * @param balance     bilancio attuale;
	 * @param goal        valore totale.
	 * @return la percentuale di {@code balance} su {@code goal}.
	 */
	private double getPercent(double initBalance, double balance, double goal) {
		if (initBalance < 0) {
			return getPercent(0.0, -initBalance + balance, -initBalance);
		}
		return App.round((balance - initBalance) / (goal - initBalance));

	}

}
