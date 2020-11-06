/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import java.time.LocalDate;

import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.CashBox;
import it.unicam.cs.pa.jbudget105101.model.Category;

/**
 * Le classi che implementeranno {@code StatisticsOfBank} avranno la
 * responsabilità di implementare metodi che permettano di calcolare dei dati
 * statistici sullo stato di una determinata {@link Bank}.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 * @param <T> il tipo di {@link Bank}
 */
public interface StatisticsOfBank<T extends Bank> {

	/**
	 * Dovrà permettere di ottenere la quantità di denaro spesa in un determinato
	 * mese dell'anno.
	 * 
	 * @param date la data in cui verrà calcolata la spesa.
	 * @return la quantità di denaro spesa in quel periodo.
	 */
	public double outlayOfMonth(LocalDate date);

	/**
	 * Dovrà permettere di ottenere la quantità di denaro spesa per una determinata
	 * {@link Category}.
	 * 
	 * @param c la {@link Category} cui cercare le {@link Transaction};
	 * @return la quantità di denaro spesa.
	 */
	public double outlayOfCategory(Category c);

	/**
	 * Dovrà permettere di ottenere la percentuale di denaro spesa per una
	 * {@link Category}.
	 * 
	 * @param c la {@link Category} cui ottenere la percentuale;
	 * @return la percentuale di spesa.
	 */
	public double outlayPercentOfCategory(Category c);

	/**
	 * Dovrà permettere di ottenere la quantità di denaro spesa in un determinato
	 * giorno.
	 * 
	 * @param date la data in cui verrà calcolata la spesa.
	 * @return la quantità di denaro spesa in quel periodo.
	 */
	public double outlayOfDay(LocalDate date);

	/**
	 * Dovrà permettere di ottenere il numero di {@link Transaction} effettuate in
	 * un determinato giorno.
	 * 
	 * @param date la data in cui verranno contate le {@link Transaction};
	 * @return il numero di {@link Transaction} in una data.
	 */
	public double numberOfTransactionInDay(LocalDate date);

	/**
	 * Dovrà permettere di ottenere la precentuale di completamento di un
	 * determinato {@link CashBox}.
	 * 
	 * @param cb il {@link CashBox} che si vuole analizzare.
	 * @return la precentuale di completamento.
	 */
	double getBalancePercent(CashBox cb);
}
