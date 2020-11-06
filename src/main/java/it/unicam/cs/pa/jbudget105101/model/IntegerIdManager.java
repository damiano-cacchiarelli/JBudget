/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.LinkedList;

/**
 * <p>
 * La classe {@code Integer Manager} rappesenta un manager che gestisce istanze
 * di {@link IntegerId}.
 * </p>
 * <p>
 * Mette a disposizione un meccanismo che permette di richiedere un
 * {@link IntegerId} univoco, che potr&agrave; essere sia un nuovo codice o uno
 * che &egrave; stato ritirato in precedenza. Inoltre &egrave; possibile
 * verificare se un determinato {@link IntegerId} &egrave; in uso.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class IntegerIdManager implements IdentificationManager<IntegerId> {

	/**
	 * La lista di tutti i {@link IntegerId} in uso.
	 */
	private LinkedList<IntegerId> listId = new LinkedList<>();

	/**
	 * La lista di tutti i {@link IntegerId} che sono stati ritirati.
	 */
	private LinkedList<IntegerId> listOldId = new LinkedList<>();

	/**
	 * Permette di generare un {@link IntegerId} univoco. è possibile che venga
	 * restituito un {@link IntegerId} ritirato in precedenza se diponibile.
	 * 
	 * @return un nuovo {@link IntegerId} o uno ritirato.
	 */
	@Override
	public IntegerId getNewIdentification() {
		if (!listOldId.isEmpty())
			return listOldId.pollFirst();
		return generateNewId();
	}

	/**
	 * Ritira un {@link IntegerId} che non viene più utilizzato. Non è possibile
	 * restituire un codice identificativo che è già stato ritirato.
	 * 
	 * @param identification l'integerId che si vuole restituire
	 * @return {@code true} se è stato ritirato. {@code false} altrimenti.
	 */
	@Override
	public boolean returnIdentification(IntegerId identification) {
		if (identification == null || listOldId.contains(identification))
			return false;
		return listOldId.add(identification);
	}

	/**
	 * Permette di verificare se un determinato {@link IntegerId} è stato generato
	 * ed è in uso.
	 * 
	 * @param identification il codice identificativo che si vuole verificare
	 * @return {@code true} se il codice identificativo è in uso; {@code false}
	 *         altrimenti.
	 */
	@Override
	public boolean isUsed(IntegerId identification) {
		return listId.contains(identification);
	}

	/**
	 * Verifica l'ultimo id creato e ne genera uno nuovo.
	 * 
	 * @return un nuovo {@link IntegerId}.
	 */
	private IntegerId generateNewId() {
		int valueId = 0;
		if (!listId.isEmpty()) {
			valueId = listId.getLast().getValue() + 1;
		}
		IntegerId id = new IntegerId(valueId);
		listId.add(id);
		return id;
	}

}
