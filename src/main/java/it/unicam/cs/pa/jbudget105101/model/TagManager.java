/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * La classe {@code TagManager} implementa un gestore di {@link Category} in cui
 * &egrave; possibile raccogliere i vari elementi.
 * </p>
 * <p>
 * Le diverse {@link Category} vengono gestite per mezzo di un {@link HashSet},
 * pertanto non sar&agrave; possibile inserire due {@link Category} uguali ed
 * inoltre non sar&agrave; possibile inserire lementi {@code null}.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class TagManager implements CategoryManager {

	/**
	 * Il set utilizzato per gestire le {@link Category}.
	 */
	private HashSet<Category> tagList;

	/**
	 * Permette di costruire un manager vuoto.
	 */
	public TagManager() {
		tagList = new HashSet<>();
	}

	/**
	 * Pemette di ottenere l'insieme di tutte le {@link Category} che il manager
	 * gestisce.
	 * 
	 * @return una {@code view} non modificabile di {@link Category}
	 */
	@Override
	public Set<Category> getCategoryList() {
		return Collections.unmodifiableSet(tagList);
	}

	/**
	 * Pemette di cercare una {@link Category} in questo manager partendo dalla
	 * stringa generata dall'oggeto.
	 * 
	 * @param s la stringa della categoria.
	 * @return la {@link Category} con quel nome.
	 */
	@Override
	public Category getCategory(String s) {
		for (Category c : tagList) {
			if (c.toString().equals(s)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Permette di aggiungere una {@link Category} al manager. Non è possibile
	 * aggiungere una categoria già presente o {@code null}.
	 * 
	 * @param c la {@link Category} da aggiungere.
	 * @return {@code true} se è stata aggiunta; {@code false} altrimenti.
	 * @throws NullPointerException se si prova ad aggiungere una {@link Category}
	 *                              {@code null}
	 */
	@Override
	public boolean addCategory(Category c) {
		if (c == null)
			throw new NullPointerException("Valore Null");
		return tagList.add(c);
	}

	/**
	 * Permette di rimuovere una {@link Category} dal manager.
	 * 
	 * @param c la {@link Category} da rimuovere
	 * @return {@code true} se è stata rimossa; {@code false} altrimenti.
	 * @throws NullPointerException se si prova ad eleminare una {@link Category}
	 *                              {@code null}
	 */
	@Override
	public boolean removeCategory(Category c) {
		if (c == null)
			throw new NullPointerException("Valore Null");
		return tagList.remove(c);
	}

}
