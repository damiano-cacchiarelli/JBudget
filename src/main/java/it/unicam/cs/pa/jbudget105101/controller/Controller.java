/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;
import it.unicam.cs.pa.jbudget105101.model.CategoryManager;
import it.unicam.cs.pa.jbudget105101.model.Transaction;
import it.unicam.cs.pa.jbudget105101.model.Wallet;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeCashBox;
import it.unicam.cs.pa.jbudget105101.model.exceptions.ExistingElementException;

/**
 * <p>
 * Le classi che implementeranno {@code Controller} avranno la responsabilità di
 * operare sulle classi del MODEL.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public interface Controller {

	/**
	 * Dovrà permettere di caricare lo stato degli elementi.
	 * 
	 * @return {@code true} se i dati sono stati caricati correttamente;
	 *         {@code false} altrimenti.
	 */
	boolean loadData();

	/**
	 * Dovrà permettere di salvare lo stato degli elementi.
	 */
	void saveData();

	/**
	 * Dovrà costruire una categoria partendo dai dati passati come parametro e la
	 * dovrà aggiungere al {@link CategoryManager}. Se la categoria è già presente
	 * dovrà essere lanciato l'eccezione {@link ExistingElementException}.
	 * 
	 * @param name     il nome da impostare alla {@code Category}
	 * @param priority la priorità da impostare alla {@code Category}
	 * @throws ExistingElementException se l'elemento da inserire è già presente.
	 */
	void addTag(String name, int priority) throws ExistingElementException;

	/**
	 * Dovrà permettere di rimuovre una {@link Category} dal
	 * {@link CategoryManager}.
	 * 
	 * @param tag la stringa corrispondente alla {@code Category} che si vuole
	 *            eliminare.
	 * @return {@code true} la {@code Category} è stata eliminata; {@code false}
	 *         altrimenti.
	 */
	boolean removeTag(String tag);

	/**
	 * Dovrà restituire l'insieme di {@link Category} che gestisce il
	 * {@link CategoryManager}.
	 * 
	 * @return l'insieme di {@code Category}
	 */
	Set<Category> getTagList();

	/**
	 * Dovrà restituire la {@link Category} che corrisponde alla stringa.
	 * 
	 * @param s la stringa che corisponde alla {@code Category} che si vuole
	 *          ottenere.
	 * @return la {@code Category} corrispondente alla stringa.
	 */
	Category getTag(String s);

	/**
	 * Dovrà permettere di creare e aggiungere un {@link Wallet}.
	 * 
	 * @param name    il nome del {@code Wallet};
	 * @param balance il bilancio del {@code Wallet};
	 * @throws ExistingElementException se l'elemento che si vuole aggiungere è già
	 *                                  presente.
	 */
	void addWallet(String name, double balance) throws ExistingElementException;

	/**
	 * Dovrà permettere di creare e aggiungere un {@link SaveBox}.
	 * 
	 * @param name    il nome del {@code SaveBox};
	 * @param balance il bilancio del {@code SaveBox};
	 * @param goal    l'obiettivo del {@code SaveBox};
	 * @throws ExistingElementException se l'elemento che si vuole aggiungere è già
	 *                                  presente.
	 */
	void addSaveBox(String name, double balance, double goal) throws ExistingElementException;

	/**
	 * Dovrà permettere di creare e aggiungere un {@link DebitBox}.
	 * 
	 * @param name  il nome del {@code DebitBox};
	 * @param debit il debito del {@code DebitBox};
	 * @throws ExistingElementException se l'elemento che si vuole aggiungere è già
	 *                                  presente.
	 */
	void addDebitBox(String name, double debit) throws ExistingElementException;

	/**
	 * Dovrà restituire tutti i {@link CashBox} che la {@link Bank} gestisce.
	 * 
	 * @return una colleezione di {@code CashBox}.
	 */
	Collection<CashBox> getCashBoxList();

	/**
	 * Dovrà permettere di eliminare un {@link CashBox}.
	 * 
	 * @param box la stringa che corrisponde al box;
	 * @return {@code true} se il {@code CashBox} è stato eliminato correttamente;
	 *         {@code false} altrimenti.
	 */
	boolean deleteBox(String box);

	/**
	 * Dovrà restituire ttutti i {@link CashBox} con un determinato
	 * {@link TypeCashBox tipo}.
	 * 
	 * @param tc il {@code tipo} dei {@code CashBox} che si vuole ottenere.
	 * @return una collezione di {@code CashBox} con lo stesso {@code tipo}.
	 */
	Collection<CashBox> getCashBox(TypeCashBox tc);

	/**
	 * Dovrà permettere di creare e aggiungere una {@link Transaction} in un
	 * determinato {@link CashBox}.
	 * 
	 * @param box   il {@code CashBox} in cui si vuole inserire la transazione;
	 * @param value l'importo della transazione;
	 * @param ld    la data della transazione;
	 * @param i     la descrizione della transazione;
	 * @param tl    l'insime di {@link Category} della transazione.
	 * @return {@code true} se la transazione è stata inserita correttamente;
	 *         {@code false} altrimenti.
	 */
	boolean addTransaction(String box, double value, LocalDate ld, String i, Set<Category> tl);

	/**
	 * Dovrà permettere di rimuovere una {@link Transaction} in un determinato
	 * {@link CashBox}.
	 * 
	 * @param tr  la stringa che corrisponde alla transazione da voler eliminare;
	 * @param box la stringa che corrisponde al box in cui si trova la transazione
	 * @return {@code true} se la transazione è stata rimossa; {@code false}
	 *         altrimenti.
	 */
	boolean deleteTransactionInBox(String tr, String box);

	/**
	 * Dovrà restituire tutte le {@link Transaction transazioni} di tutti i
	 * {@link CashBox} che la {@link Bank} gestisce.
	 * 
	 * @return una collezione di tutte le transazioni.
	 */
	Collection<Transaction> getAllTransaction();


}
