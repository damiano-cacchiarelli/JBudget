/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;

import it.unicam.cs.pa.jbudget105101.model.BasicTransaction;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.IdentificationManager;
import it.unicam.cs.pa.jbudget105101.model.Identifier;
import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;

/**
 * <p>
 * La classe {@code TemporalScheduler} implementa uno {@code Scheduler} che
 * permette di generare transazioni in date diverse.
 * </p>
 * Vengono forniti due metodi. Uno permette di generare una lista di
 * {@link Transaction} in cui ogni {@code transazione} ha una data generata a
 * partire da una {@link BiFunction} che modifica la data della
 * {@code transazione} di base. L'alto invece genera due liste in cui in una
 * delle due le {@code transazioni} hanno importo inverso rispetto alla
 * transazione base.
 * </p>
 * Ogniuna di queste {@code Transaction} avrà un codice identificativo nuovo.
 * </p>
 * <p>
 * Gli altri campi della {@code transazione} non vengono modificati rispetto
 * alla transazione base.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class TemporalScheduler implements SchedulerTransaction<BasicTransaction> {

	/**
	 * Il tipo dello {@code Scheduler}
	 */
	private final TypeScheduler ts = TypeScheduler.TEMPORAL;

	/**
	 * Stringa che descrive le funzionalità del {@code TemporalScheduler}.
	 */
	private String description = description();

	/**
	 * L'{@link IdentificationManager} utilizzato per generare gli {@link Identifier
	 * id} per le nuove transazioni.
	 */
	private IdentificationManager<? extends Identifier<?>> im;

	/**
	 * La descrizione della transazione di base.
	 */
	private String info;

	/**
	 * L'importo della transazione di base.
	 */
	private double value;

	/**
	 * L'insieme di tag della transazione di base
	 */
	private Set<Category> taglist;

	/**
	 * La data della transazione di base.
	 */
	private LocalDate date;

	/**
	 * Permette di costruire un {@code TemporalScheduler} con un determinato
	 * {@link IdentificationManager}.
	 * 
	 * @param im il manager per generare gli {@link Identifier id}.
	 */
	public TemporalScheduler(IdentificationManager<? extends Identifier<?>> im) {
		if (im == null) {
			throw new NullPointerException();
		}
		this.im = im;
	}

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
	@Override
	public Map<CashBox, Collection<BasicTransaction>> generateShiftTransactionList(int i, BasicTransaction t,
			CashBox c1, CashBox c2, BiFunction<Integer, LocalDate, LocalDate> f) {
		HashMap<CashBox, Collection<BasicTransaction>> map = new HashMap<>();
		map.put(c1, generateTransactionList(i, t, f));
		map.put(c2, generateTransactionList(i, new BasicTransaction(-t.getValue(), t.getDate(), t.getInfo(),
				t.getCategory(), im.getNewIdentification()), f));
		return map;
	}

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
	@Override
	public Collection<BasicTransaction> generateTransactionList(int i, BasicTransaction t,
			BiFunction<Integer, LocalDate, LocalDate> f) {
		init(t);
		Stack<BasicTransaction> list = new Stack<>();

		for (int j = 0; j < i; j++) {
			list.add(new BasicTransaction(value, f.apply(j, date), info, taglist, im.getNewIdentification()));
		}
		return list;
	}

	/**
	 * Imposta le variabili di istanza della classe utilizzando i valori della
	 * transazione di base.
	 * 
	 * @param t la transazione di base.
	 */
	private void init(BasicTransaction t) {

		value = t.getValue();
		info = t.getInfo();
		taglist = t.getCategory();
		date = t.getDate();
	}

	/**
	 * Restituisce il tipo di {@code SchedulerTransaction}.
	 * 
	 * @return il tipo di {@code SchedulerTransaction}
	 */
	@Override
	public TypeScheduler getType() {
		return ts;

	}

	/**
	 * Restituisce una stringa che descrive le funzionalità che implementa lo {@code SchedulerTransaction}.
	 * @return la descrizione delle funzionalità.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Restituisce la stringa di default che descrive le funzionalità del {@code TemporalScheduler}
	 * @return la descrizione delle funzionalità.
	 */
	private String description() {

		return ts + " Scheduler : " + "\nA TemporalScheduler allows you to automatically create a series of repeated "
				+ "transactions in the selected time period.\nIn particular: \n "
				+ "-DAILY: generates transactions one per day;\n\n"
				+ "-WEEKLY: generates transactions each seven days apart;\n\n"
				+ "-MONTHLY: generates transactions once every month on the chosen day. "
				+ "[EXAMPLE: Transaction of 01/01/2020, it is repeated on 01/02/2020.] "
				+ "A transaction on the last day of the month will always be repeated on the last day of the month. "
				+ "[EXAMPLE: Transaction of 01/31/2020, repeated on 02/29/2020]\n\n"
				+ "Each transaction will have the same amount, the same description and will be inserted in the same Box.";
	}

}
