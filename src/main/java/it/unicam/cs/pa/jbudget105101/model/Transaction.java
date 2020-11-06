/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.time.LocalDate;
import java.util.Set;

import it.unicam.cs.pa.jbudget105101.model.enums.TypeTransaction;

/**
 * <p>
 * Le classi che implementeranno {@code Transaction} avranno la
 * responsabilit&amp;agrave; di implementare un movimento.
 * </p>
 * <p>
 * Ogni movimento dovr&agrave; permettere di poter essere impostato su un
 * determinato stato che ne descrive il suo essere eseguito oppure no. Inoltre
 * dovr&agrave; essere caratterizato da un tipo {@link TypeTransaction}.
 * </p>
 * <p>
 * Non vengono richieste funzionalit&agrave; che permettano la modifica di un
 * movimento dopo esser stato istanziato.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface Transaction {

	/**
	 * Restituir&agrave; l'importo del movimento.
	 * 
	 * @return l'importo del movimento.
	 */
	public double getValue();

	/**
	 * Restituir&agrave; la data del movimento.
	 * 
	 * @return la data del movimento.
	 */
	public LocalDate getDate();

	/**
	 * Restituir&agrave; la descrizione del movimento.
	 * 
	 * @return la descrizione del movimento.
	 */
	public String getInfo();

	/**
	 * Restituir&agrave; l'insieme dei {@link Category} della transazione.
	 * 
	 * @return l'insieme dei {@link Category} della transazione.
	 */
	public Set<Category> getCategory();

	/**
	 * L'implementazione dovr&agrave; permettere di impostare come "eseguita" questa
	 * transazione.
	 */
	public void toExecute();

	/**
	 * L'implementazione dovr&agrave; permettere di verificare se la transazione è già stata eseguita.
	 * 
	 * @return {@code true} if the transaction is execute; {@code false} altrimenti.
	 */
	public boolean isExecute();

	/**
	 * Restituir&agrave; il tipo della transazione.
	 * 
	 * @return il tipo della transazione
	 */
	public TypeTransaction getType();

}
