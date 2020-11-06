/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;

import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;

/**
 * <p>
 * Le classi che implementeranno {@code SchedulerTransaction} dovranno la
 * responsabilità di implementare metodi che permettano di generare una lista di
 * transazioni.
 * </p>
 * In particolare viene richiesto di implementare un metodo che permetta di
 * generare una lista di {@link Transaction transazioni} partendo da una di
 * esempio e seguendo un determinato schema.</br>
 * Viene inoltre chiesto di implementare un metodo che permetta di generare due
 * liste in cui una delle due ha {@code transazioni} con importi opposti
 * all'altra lista in modo tale che venghi rappresentato uno spostamento di
 * soldi tra due {@link CashBox}.
 * <p>
 * Ogni {@code SchedulerTransaction} dovrà essere caratterizzato da un
 * {@link TypeScheduler tipo} che ne descriva il funzionamento.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface SchedulerTransaction<T extends Transaction> {

	/**
	 * Dovrà permettere di generare due liste in cui ogni {@link Transaction
	 * transazion} avrà un determinato stato generato applicando alla transazione di
	 * base determinate operazioni di cambiamento.</br>
	 * Le due liste generate verranno poi inserite in una {@code Map} in cui una ha
	 * {@code transazioni} con importi opposti all'altra lista, in modo tale che sia
	 * possibile aggiungere le due liste in due {@link CashBox} e simulare uno
	 * spostamento di denaro.
	 * 
	 * @param i  numero di {@code transazioni} da generare;
	 * @param t  {@code transazioni} di base;
	 * @param c1 {@code CashBox} dove inserire la lista con importi corretti;
	 * @param c2 {@code CashBox} dove inserire la lista con importi inversi;
	 * @param f  funzione che descrive il modo in cui viene modificata la data della
	 *           {@code transazione} di base.
	 * @return una {@code Map} che ha come chiavi i {@code CashBox} e come argomenti
	 *         le liste.
	 */
	public Map<CashBox, Collection<T>> generateShiftTransactionList(int i, T t, CashBox c1, CashBox c2,
			BiFunction<Integer, LocalDate, LocalDate> f);

	/**
	 * Dovrà permettere di generare una lista in cui ogni {@link Transaction
	 * transazion} avrà un determinato stato generato applicando alla transazione di
	 * base determinate operazioni di cambiamento.
	 * 
	 * @param i numero di {@code transazioni} da generare;
	 * @param t {@code transazioni} di base;
	 * @param f funzione che descrive il modo in cui viene modificata la data della
	 *          {@code transazione} di base.
	 * @return una lista di transazioni.
	 */
	public Collection<T> generateTransactionList(int i, T t, BiFunction<Integer, LocalDate, LocalDate> f);

	/**
	 * Dovrà restituire il tipo di {@code SchedulerTransaction}.
	 * 
	 * @return il tipo di {@code SchedulerTransaction}
	 */
	public TypeScheduler getType();

	/**
	 * Dovrà restituire una stringa che descriva le funzionalità che implementa lo {@code SchedulerTransaction}.
	 * @return descrizione delle funzionalità.
	 */
	public String getDescription();
}
