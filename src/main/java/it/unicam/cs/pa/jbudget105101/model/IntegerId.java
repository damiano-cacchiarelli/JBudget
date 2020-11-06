/**
 * 
 */
package it.unicam.cs.pa.jbudget105101.model;

/**
 * <p>
 * La classe {@code IntegerId} rappesenta un {@link Identifier} costruito a
 * partire da un valore di tipo Integer.
 * </p>
 * <p>
 * {@code IntegerId} sovrascrive il metodo {@code equals} e definisce due
 * istanze della classe come uguali se hanno lo stesso valore.
 * </p>
 * 
 * @author Damiano Cacchiarelli - damiano.cacchiarelli@studenti.unicam.it
 *
 */
public class IntegerId implements Identifier<Integer> {

	/**
	 * Il valore del codice identificativo.
	 */
	private final int id;

	/**
	 * Permette di costruire un codice identificativo a partire da un intero.
	 * 
	 * @param d numero intero che rappesenta l'{@code IntegerId}.
	 */
	public IntegerId(int d) {
		this.id = d;
	}

	/**
	 * Restituisce il valore del codice identificativo.
	 * 
	 * @return l'{@code Integer} che rappersenta il {@code IntegerId}.
	 */
	@Override
	public Integer getValue() {
		return id;
	}

	/**
	 * Restituisce la stringa che rappesenta l'{@code IntegerId} con il seguente
	 * formato : [ID: {@code id}]
	 * 
	 * @return a string that represent an identifier
	 */
	@Override
	public String getStringValue() {
		return "[ID: " + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Due istanze della classe {@code IntegerId} sono considerate uguali se hanno
	 * lo stesso integer che le rappesenta
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerId other = (IntegerId) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
