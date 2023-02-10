package es.espublico.ejercicio.negocio;

import java.io.Serializable;

/**
 * Clase específica para la consulta del actor que ha pilotado la nave que más aparece en un conjunto de películas
 * @Author Manuel León
 * since 07/02/2023
 */
public class ResultadoPilotoNaveMasApareceEnPeliculas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String personName;
	private String starshipName;
	private Long totalApariciones;
	
	public String getPersonName() {
		
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getStarshipName() {
		return starshipName;
	}
	public void setStarshipName(String starshipName) {
		this.starshipName = starshipName;
	}
	public Long getTotalApariciones() {
		return totalApariciones;
	}
	public void setTotalApariciones(Long totalApariciones) {
		this.totalApariciones = totalApariciones;
	}
	

	
}
