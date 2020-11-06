/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

import java.time.LocalDate;
import java.util.Set;

import it.unicam.cs.pa.jbudget105101.App;
import it.unicam.cs.pa.jbudget105101.model.enums.TypeTransaction;

/**
 * <p>
 * La classe {@code BasicTransaction} rappresenta un singolo movimento.
 * </p>
 * <p>
 * Di un movimento &egrave; possibile inserire un importo negativo o positivo
 * che verr&agrave; poi utilizzato per impostare il {@link TypeTransaction} che
 * determiner&agrave; se questo movimento rappresenta un'uscita o un'entrata.
 * </p>
 * <p>
 * Di un movimento &egrave; possbile inserire una data che terr&agrave; conto
 * solo del giorno, del mese e dell'anno che verranno inseriti: un movimento
 * inserito alle 10.10 e uno alle 11.11 entrambi nel giorno 03/12/1999, avranno
 * la stessa data di inserimento.
 * </p>
 * <p>
 * L'importo di un movimento viene automaticamente arrotondato a due cifre
 * decimali dopo la virgola.
 * </p>
 * <p>
 * Ogni istanza di {@code BasicTransaction} dovr&agrave; avere un
 * {@link Identifier} possibilmente univoco tra tutte le transazioni utilizzate.
 * La classe non mette a disposizione metodi per ottenere questo codice che
 * verr&agrave; invece utilizzato per generare la stringa che rappresenta
 * l'oggetto {@code BasicTransaction}; infatti sovrascrive il metodo
 * {@code toString}.
 * </p>
 * <p>
 * {@code BasicTransaction} inoltre sovrascrive i metodi {@code equals} e
 * {@code hashCode} definendo come uguali due transazioni con stesso: id, data,
 * importo, descrizione e insieme di {@link Category}.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 * 
 */

public class BasicTransaction implements Transaction {

	/**
	 * Codice identificativo.
	 */
	private Identifier<?> id;

	/**
	 * L'importo del movimento.
	 */
	private double value;

	/**
	 * La data in cui è avvenuta o avverrà la transazione.
	 */
	private LocalDate date;

	/**
	 * La descrizione del movimento.
	 */
	private String info;

	/**
	 * L'insieme dei tag del movimento.
	 */
	private Set<Category> tagList;

	/**
	 * Il tipo del movimento.
	 */
	private TypeTransaction type;

	/**
	 * Lo stato del movimento.
	 */
	private boolean execute = false;

	/**
	 * Costruisce un movimento senza specificare la data; questa verr&agrave;
	 * impostata nel giorno in cui viene crato il movimento.
	 * 
	 * @param value        l'importo del movimento;
	 * @param info         la descrizione del movimento;
	 * @param listCategory l'insieme dei tag;
	 * @param id           il codice identificativo.
	 */
	public BasicTransaction(double value, String info, Set<Category> listCategory, Identifier<?> id) {
		this(value, LocalDate.now(), info, listCategory, id);

	}

	/**
	 * Costruisce un movimento in cui &egrave possibile specificare la data in cui
	 * esso. avverr&agrave;.
	 * 
	 * @param value        l'importo del movimento;
	 * @param date         la data in cui avviene o avverrà il movimento;
	 * @param info         la descrizione del movimento;
	 * @param listCategory l'insieme dei tag;
	 * @param id           il codice identificativo.
	 */
	public BasicTransaction(double value, LocalDate date, String info, Set<Category> listCategory, Identifier<?> id) {
		this.setValue(value);
		this.setInfo(info);
		this.setTag(listCategory);
		this.setLocalDate(date);
		this.setId(id);
		setType();

	}

	/**
	 * Restistisce l'importo del movimento.
	 * 
	 * @return l'importo dela transazione.
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * Restistisce la data del movimento.
	 * 
	 * @return the date of a transaction.
	 */
	@Override
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Restistisce la descrizione del movimento.
	 * 
	 * @return the info of a transaction.
	 */
	@Override
	public String getInfo() {
		return info;
	}

	/**
	 * Restistisce l'insieme dei {@link Category} del movimento.
	 * 
	 * @return the tag list of transaction.
	 */
	@Override
	public Set<Category> getCategory() {
		return tagList;
	}

	/**
	 * Permette di impostare come eseguito il movimento.
	 */
	@Override
	public void toExecute() {
		this.execute = true;
	}

	/**
	 * Verifica se il movimento è eseguito oppure no.
	 *
	 * @return {@code true} se è eseguito; {@code false} altrimenti.
	 */
	@Override
	public boolean isExecute() {
		return execute;
	}

	/**
	 * Restistisce il {@link TypeTransaction} del movimento.
	 * 
	 * @return il {@code TypeTransaction} del movimento.
	 */
	@Override
	public TypeTransaction getType() {
		return type;
	}

	/**
	 * Imposta l'insime di {@link Category}.
	 * 
	 * @param listCategory l'insieme dei tag.
	 * 
	 * @throws NullPointerException se l'insime è vuoto o {@code null}.
	 */
	private void setTag(Set<Category> listCategory) {
		if (listCategory == null || listCategory.isEmpty()) {
			throw new NullPointerException("La lista dei tag non e' valida");
		}
		this.tagList = listCategory;
	}

	/**
	 * Imposta la descrizione del movimento.
	 * 
	 * @param info la descrizione del movimento.
	 * @throws NullPointerException se la scringa è {@code null}.
	 */
	private void setInfo(String info) {
		if (info == null) {
			throw new NullPointerException("Dscrizione null");
		}
		this.info = info;
	}

	/**
	 * Imposta la data del movimento.
	 * 
	 * @param date la data.
	 * @throws NullPointerException se la data è {@code null}.
	 */
	private void setLocalDate(LocalDate d) {
		if (d == null) {
			throw new NullPointerException("Data null");
		}
		this.date = d;
	}

	/**
	 * Imposta l'importo del movimento. Esso viene automaticamente arrotondato a due
	 * cifre decimali dopo la virgola.
	 * 
	 * @param value l'importo.
	 */
	private void setValue(double v) {
		this.value = App.round(v);
	}

	/**
	 * Imposta il codice identificativo.
	 * 
	 * @param id il codice identificativo.
	 * @throws NullPointerException se id è {@code null}.
	 */
	private void setId(Identifier<?> id) {
		if (id == null)
			throw new NullPointerException("Id null");
		this.id = id;
	}

	/**
	 * Imposta il tipo del movimento in Debito o Credito valutando l'importo.
	 */
	private void setType() {
		if (value < 0) {
			this.type = TypeTransaction.DEBIT;
		} else {
			this.type = TypeTransaction.CREDITS;
		}
	}

	/**
	 * Restistisce la stringa che rappresenta il movimento con il seguente formato :
	 * > info [date][value][Execute : execute] Tags : TagList id
	 * 
	 * @return la stringa che rappresenta il movimento.
	 */
	@Override
	public String toString() {
		return "> " + info + " [" + date + "][" + value + "][Execute : " + execute + "] Tags :" + StringListTag() + " "
				+ id.getStringValue();
	}

	/**
	 * Restituisce la stringa costruita concatenando tutte le stringhe che
	 * rappresentano un tag.
	 * 
	 * @return la stringa della lista di tag
	 */
	private String StringListTag() {
		String tListTag = "";
		for (Category c : getCategory()) {
			tListTag += c.toString();
		}
		return tListTag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Due istanze della classe {@code BasicTransaction} sono uguali se presentano
	 * lo stesso importo, lo stesso id, gli stessi tag, la stessa data e la stessa
	 * descrizione.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicTransaction other = (BasicTransaction) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (tagList == null) {
			if (other.tagList != null)
				return false;
		} else if (!tagList.equals(other.tagList))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

}
