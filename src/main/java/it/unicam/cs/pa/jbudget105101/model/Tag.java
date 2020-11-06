/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

/**
 * <p>
 * La classe {@code Tag} rappersenta una categoria in cui &egrave; possibile
 * definire un nome e una priorit&agrave;. Lo scopo &egrave; quello di poter
 * classificare o raggruppare elementi che appartengono alla stessa categoria
 * assegnandogli un {@code Tag}.
 * </p>
 * <p>
 * La classe sovrascrive il metodo {@code toString} e il metodo {@code equals}.
 * Quest'ultimo afferma che due istanze della classe {@code Tag} sono uguali se
 * presentano lo stesso nome.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class Tag implements Category {

	/**
	 * Il nome del tag.
	 */
	private String name;

	/**
	 * La priorità, che descrive l'importanza di un tag.
	 */
	private int priority;

	/**
	 * 
	 * Costruisce un tag con un determinato nome e una determinata priorità. La
	 * proprietà non potrà essere minore di zero.
	 * 
	 * @param name     il nome del tag.
	 * @param priority la priorità del tag.
	 */
	public Tag(String name, int priority) {
		this.setName(name);
		this.setPriority(priority);
	}

	/**
	 * Restituisce il nome del tag.
	 * 
	 * @return il nome del tag.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Restituisce la priorità del tag.
	 * 
	 * @return la priorità del tag.
	 */
	@Override
	public int getPriority() {
		return priority;
	}

	/**
	 * Permette di impostare il nome del tag.
	 * 
	 * @throws NullPointerException se la stringa è {@code null}
	 */
	private void setName(String name) {
		if (name == null)
			throw new NullPointerException("");
		this.name = name;
	}

	/**
	 * Permette di inpostare la priorità del tag. Essa non potrà essere minore di
	 * zero.
	 * 
	 * @throws NumberFormatException se il valore è negativo
	 */
	private void setPriority(int priority) {
		if (priority < 0)
			throw new NullPointerException("Non è un numero valido");
		this.priority = priority;
	}

	/**
	 * Restituisce la stringa che rappesenta il tag con il seguente formato :
	 * [{@code name}, {@code priority}]
	 */
	@Override
	public String toString() {
		return "[" + name + ", " + priority + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Due istanze della classe {@code Tag} sono considerate uguali se hanno lo
	 * stesso nome.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
