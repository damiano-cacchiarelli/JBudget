/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.util.Collection;

import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeScheduler;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * <p>
 * Le classi che implementeranno {@code MasterScheduler} avranno la
 * responsabilità di gestire degli {@link SchedulerTransaction}.
 * </p>
 * <p>
 * Dovranno implementare funzionalità che permetteranno di aggiungere e
 * rimuovere {@code Scheduler} e di restituire lo {@code Scheduler}
 * corrispondente al {@link TypeScheduler tipo} richiesto. Ogni
 * {@code Scheduler} che il manager gestisce dovrà avere {@code tipo} diverso.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 * @param <T> tipo delle {@link Transaction} che gli {@code Scheduler}
 *            gestiscono.
 */

public interface MasterScheduler<T extends Transaction> {

	/**
	 * Dovrà restituire lo {@link SchedulerTransaction} che ha lo stesso
	 * {@link TypeScheduler tipo} del tipo richiesto.
	 * 
	 * @param uniqueType il tipo dello {@code Scheduler} richiesto.
	 * @return lo {@code Scheduler} richiesto.
	 */
	SchedulerTransaction<T> getScheduler(TypeScheduler uniqueType);

	/**
	 * Dovrà restituire una collezione di tutti gli {@link SchedulerTransaction
	 * Scheduler} che il manager gestisce.
	 * 
	 * @return tutti gli {@code Scheduler}.
	 */
	Collection<SchedulerTransaction<T>> getAllScheduler();

	/**
	 * Dovrà restituire una collezione di tutti {@link TypeScheduler tipi} di
	 * {@code Scheduler} che il manager gestisce.
	 * 
	 * @return tutti i {@code tipi} di {@code Scheduler}.
	 */
	Collection<TypeScheduler> getAllSchedulerType();

	/**
	 * Dovrà permettere di aggiungere un nuovo {@link SchedulerTransaction
	 * Scheduler} che dovrà avere un {@link TypeScheduler tipo} univoco rispetto
	 * agli {@code Scheduler} che il manager gestisce. Se si tenta di inserire uno
	 * {@code Scheduler} il cui {@code tipo} è già presente nel
	 * {@code MasterScheduler} dovrà essere lanciata la seguente eccezione :
	 * {@link ExistingElementException}.
	 * 
	 * @param st lo {@code Scheduler} che si vuole aggiungere.
	 * @throws ExistingElementException se il {@code tipo} dello {@code Scheduler} è
	 *                                  già presente.
	 */
	void addScheduler(SchedulerTransaction<T> st) throws ExistingElementException;

	/**
	 * Dovrà permettere di rimuovere un {@link SchedulerTransaction Scheduler} con
	 * un determinato {@link TypeScheduler tipo}.
	 * 
	 * @param ts il {@code tipo} che corrisponde allo {@code Scheduler} che si vuole
	 *           eliminare.
	 */
	void subScheduler(TypeScheduler ts);
}
