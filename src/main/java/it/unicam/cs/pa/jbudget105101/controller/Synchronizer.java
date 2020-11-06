/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.controller;

import it.unicam.cs.pa.jbudget105101.model.Bank;
import it.unicam.cs.pa.jbudget105101.model.CategoryManager;
import it.unicam.cs.pa.jbudget105101.model.IdentificationManager;

/**
 * Le classi che implementeranno {@code Synchronizer} avranno la responsabilità
 * di salvare e caricare gli stati di una {@link Bank}, di un
 * {@link CategoryManager} e di un {@link IdentificationManager}.
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 * @param <B> l'elemento che ha tipo una classe che estende {@code Bank} che
 *            dovrà essere gestito.
 * @param <C> l'elemento che ha tipo una classe che estende
 *            {@code CategoryManager} che dovrà essere gestito.
 * @param <I> l'elemento che ha tipo una classe che estende
 *            {@code IdentificationManager} che dovrà essere gestito.
 */
public interface Synchronizer<B extends Bank, C extends CategoryManager, I extends IdentificationManager<?>> {

	/**
	 * Dovrà restistuire un oggetto {@code Bank} costruito partendo dai dati salvati
	 * in precedenza. Se non è presente nessun salvataggio il metodo deve restituire
	 * un oggetto {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	B loadBank();

	/**
	 * Dovrà restistuire un oggetto {@code CategoryManager} costruito partendo dai
	 * dati salvati in precedenza. Se non è presente nessun salvataggio il metodo
	 * deve restituire un oggetto {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	C loadCategoryManager();

	/**
	 * Dovrà restistuire un oggetto {@code IdentificationManager} costruito partendo
	 * dai dati salvati in precedenza. Se non è presente nessun salvataggio il
	 * metodo deve restituire un oggetto {@code null}.
	 * 
	 * @return l'oggetto caricato o {@code null}.
	 */
	I loadIdManager();

	/**
	 * Dovrà permettere il salvataggio dello stato delle tre istanze delle classi
	 * che estendono {@code Bank}, {@code CategoryManager},
	 * {@code IdentificationManager}.
	 * 
	 * @param bank                  la bank che si vuole salvare;
	 * @param categoryManager       il manager di categorie che si vuole salvare;
	 * @param identificationManager il manager id che si vuole salvare.
	 */
	void saveData(B bank, C categoryManager, I identificationManager);

}
