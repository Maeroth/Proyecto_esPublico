package es.espublico.ejercicio.negocio;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que va a almacenando cada lectura a la API de STAR WARS. 
 * @author Manuel León
 * @param <T>
 * @since 06/02/2023
 *
 */
public abstract class EntityManagerStarWars<T> {
	  private int count; //Número total de registros
	  private String next; //Indica la siguiente página a consultar
	  private String previous; //Indica la anterior página a consultar
	  private Set<T> results = new HashSet<T>(); //Va a ir guardando cada entidad
	  
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	public Set<T> getResults() {
		return results;
	}
	public void setResults(Set<T> results) {
		this.results = results;
	}


	}