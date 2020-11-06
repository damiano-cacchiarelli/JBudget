/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.Collection;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;

/**
 * <p>
 * Le classi che implementeranno {@code CashBox} avranno la
 * responsabilit&agrave; di implementare un contenitore di denaro.
 * </p>
 * <p>
 * Queste classi dovranno implementare meccanismi che permettano al {@code Box}
 * di tenere traccia delle varie transazioni che potranno essere aggiunte al
 * loro interno e dovranno mettere a punto un metodo adeguato che permetta di
 * aggiornare nel tempo il suo bilancio.
 * </p>
 * <p>
 * Inoltre viene richiesto di specificare un determinato {@link TypeCashBox
 * tipo} che il {@code CashBox} rappersenta.
 * </p>
 * <p>
 * Il metodo {@link #getGoal() getGoal} &egrave; facoltativo. Ad una classe che
 * implementa questa interfaccia non &egrave; richiesto di implementare questa
 * funzionalit&agrave;. In questo caso il metodo dovr&agrave; lanciare la
 * seguente eccezione : {@link UnsupportedOperationException}.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface CashBox {

	/**
	 * Dovrà restituire l'attuale bilancio del {@code CashBox} ovvero la quantità di
	 * soldi che esso contiene.
	 * 
	 * @return il bilancio del {@code CashBox}.
	 */
	public double getBalance();
	
	/**
	 * Dovrà restituire il bilancio iniziale del {@code CashBox} ovvero la quantità di
	 * soldi che esso conteneva al momento della creazione.
	 * 
	 * @return il bilancio iniziale del {@code CashBox}.
	 */
	public double getInitBalance();

	/**
	 * Dovrà permettere di restituire l'obbiettivo del {@code CashBox}, ovvero la
	 * quantità di denaro a cui si punta. L'implementazione di questo metodo è
	 * facoltativa; in questo caso è necessario lanciare l'eccezione
	 * {@link UnsupportedOperationException}.
	 * 
	 * @return l'obbiettivo del {@code CashBox}.
	 * @throws UnsupportedOperationException quando il box non supporta tale
	 *                                       funzione.
	 */
	public double getGoal() throws UnsupportedOperationException;

	/**
	 * Dovrà restituire il nome del {@code CashBox}.
	 * 
	 * @return il nome del box.
	 */
	public String getName();

	/**
	 * Dovrà restituire il tipo del {@code CashBox}.
	 * 
	 * @return il {@link TypeCashBox tipo} del box.
	 */
	public TypeCashBox getType();

	/**
	 * Dovrà restituire una collezzione di tutte le {@link Transaction} che il box
	 * gestisce.
	 * 
	 * @return tutte le tranazioni.
	 */
	public Collection<Transaction> getAllTransaction();

	/**
	 * Dovrà permettere di aggiungere una {@link Transaction} al box.
	 * 
	 * @param tr la transazione da aggiungere.
	 * @return {@code true} se la transazione è stata aggiunta; {@code false}
	 *         altrimenti.
	 */
	public boolean addTransaction(Transaction tr);

	/**
	 * Dovrà permettere di rimuovere una {@link Transaction} dal box.
	 * 
	 * @param tr la transazioneda rimuovere.
	 * @return {@code true} se la transazione è stata rimossa; {@code false}
	 *         altrimenti.
	 */
	public boolean subTransaction(Transaction tr);

	/**
	 * Dovrà permettere di aggiornare lo stato del box.
	 * 
	 * @return {@code true} se è stato aggiornato correttamente; {@code false}
	 *         altrimenti.
	 */
	public boolean updateBalance();

}
