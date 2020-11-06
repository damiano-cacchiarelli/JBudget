/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.util.Collection;
import java.util.HashMap;

import it.unicam.cs.pa.jbudget105101.model.BasicTransaction;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * <p>
 * La classe {@code MasterSchedulerBasicTransaction} implementa un
 * {@link MasterScheduler} che lavora con {@link SchedulerTransaction Scheduler}
 * di {@link BasicTransaction}.
 * </p>
 * <p>
 * Ogni {@code Scheduler} gestito deve avere un {@link TypeScheduler tipo}
 * univoco; non sarà infatti possibile aggiungere {@code Scheduler} con lo
 * stesso {@code tipo}.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class MasterSchedulerBasicTransaction implements MasterScheduler<BasicTransaction> {

	/**
	 * {@code HashMap} che conterrà tutti gli {@code Scheduler}.
	 */
	private HashMap<TypeScheduler, SchedulerTransaction<BasicTransaction>> mapScheduler = new HashMap<>();

	/**
	 * Restituisce lo {@link SchedulerTransaction} che ha lo stesso
	 * {@link TypeScheduler tipo} del tipo richiesto.
	 * 
	 * @param uniqueType il tipo dello {@code Scheduler} richiesto.
	 * @return lo {@code Scheduler} richiesto.
	 */
	@Override
	public SchedulerTransaction<BasicTransaction> getScheduler(TypeScheduler ts) {
		return mapScheduler.get(ts);
	}

	/**
	 * Restituisce una collezione di tutti gli {@link SchedulerTransaction
	 * Scheduler} che il manager gestisce.
	 * 
	 * @return tutti gli {@code Scheduler}.
	 */
	@Override
	public Collection<SchedulerTransaction<BasicTransaction>> getAllScheduler() {
		return mapScheduler.values();
	}

	/**
	 * Permette di aggiungere un nuovo {@link SchedulerTransaction Scheduler} che
	 * dovrà avere un {@link TypeScheduler tipo} univoco rispetto agli
	 * {@code Scheduler} che il manager gestisce. Se si tenta di inserire uno
	 * {@code Scheduler} il cui {@code tipo} è già presente nel controller viene
	 * lanciata ExistingElementException.
	 * 
	 * @param st lo {@code Scheduler} che si vuole aggiungere.
	 * @throws ExistingElementException se il {@code tipo} dello {@code Scheduler} è
	 *                                  già presente.
	 */
	@Override
	public void addScheduler(SchedulerTransaction<BasicTransaction> st) throws ExistingElementException {
		if (st == null)
			throw new NullPointerException("Impossibile inseririre uno Scheduler null");
		if (mapScheduler.containsKey(st.getType()))
			throw new ExistingElementException("Elemento già presente");
		mapScheduler.put(st.getType(), st);

	}

	/**
	 * Restituisce una collezione di tutti {@link TypeScheduler tipi} di
	 * {@code Scheduler} che il manager gestisce.
	 * 
	 * @return tutti i {@code tipi} di {@code Scheduler}.
	 */
	@Override
	public Collection<TypeScheduler> getAllSchedulerType() {
		return mapScheduler.keySet();
	}

	/**
	 * Permette di rimuovere un {@link SchedulerTransaction Scheduler} con
	 * un determinato {@link TypeScheduler tipo}.
	 * 
	 * @param ts il {@code tipo} che corrisponde allo {@code Scheduler} che si vuole
	 *           eliminare.
	 */
	@Override
	public void subScheduler(TypeScheduler ts) {
		mapScheduler.remove(ts);

	}

}
