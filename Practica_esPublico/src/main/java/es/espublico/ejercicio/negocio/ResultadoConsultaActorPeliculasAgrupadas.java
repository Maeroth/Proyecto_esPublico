package es.espublico.ejercicio.negocio;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Clase específica para la consulta del número de películas y título de cada actor en BD
 * @Author Manuel León
 * since 07/02/2023
 */

public class ResultadoConsultaActorPeliculasAgrupadas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private BigInteger numTitles;
	private String titles;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getNumTitles() {
		return numTitles;
	}
	public void setNumTitles(BigInteger numTitles) {
		this.numTitles = numTitles;
	}
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	
	
	
}
